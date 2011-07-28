package net.violet.platform.ping;

import java.util.List;
import java.util.concurrent.Semaphore;

import javax.servlet.http.HttpServletRequest;

import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.EventImpl;
import net.violet.platform.datamodel.InterruptionLogImpl;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageSerializer;
import net.violet.platform.message.StoredMessage;
import net.violet.platform.object.EarMng;
import net.violet.platform.object.Provisionning;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.StringShop;
import net.violet.probes.ProbesHandler;

import org.apache.log4j.Logger;

/**
 * Classe pour générer le message suite à un ping.
 */
public final class EventPing {

	private static final Logger LOGGER = Logger.getLogger(EventPing.class);

	private static final Semaphore SEMAPHORE = new Semaphore(100, true);

	/**
	 * Version du firmware (utilisé pour calculer la fréquence de ping).
	 */
	private final String mFirmwareVersion;

	/**
	 * Données envoyées par le v1 et la daldal (même type d'hardware)
	 */
	private final int tramesend;

	/**
	 * Trame (événement) courante. 0x7fffffff ou 0x7ffffffe ou trame.
	 */
	private final int tramecurrent;

	/**
	 * Trame (événement) suivante v1 et daldal.
	 */
	private final int tramenext;

	/**
	 * Niveau de priorité de l'événement à télécharger (v1 et daldal).
	 */
	private int downp;

	// Cache des informations relatives à l'objet qui sont volatiles
	private int cached_n1;
	private int cached_n2;

	/**
	 * Événement à supprimer (v1 et daldal).
	 */
	private int tokill;

	/**
	 * Données envoyées par le lapin (paramètre sd). Pour les V2, vaut: 0:
	 * acquittement implicite, valeur par défaut 1: clic double pendant la
	 * lecture ou en attente (dit "ackall"). 2: fin du message courant 3: clic
	 * simple pendant l'attente (dit "relire") 4: ex. long clic simple ou double
	 * pendant l'attente (dit "back ou record"), plus utilisé. 5: clic simple
	 * pendant la lecture (dit "pause") 0x80XY: mouvement d'oreilles (pendant
	 * l'attente), avec X la position de l'oreille 1 et Y la position de
	 * l'oreille 2
	 */
	private final int senddata;

	/**
	 * Référence sur l'objet.
	 */
	private final VObject object;

	/**
	 * Réference sur le type d'hardware
	 */
	private final HARDWARE mHardware;
	/**
	 * Mode de l'objet (ping ou xmpp). {@link VObject}
	 */
	private final int mode;

	private final boolean stateReboot;

	private MessageDraft theMessageDraft;

	private RABBIT_STATE theNbNewMessages;

	/**
	 * Accesseur sur l'objet.
	 * 
	 * @return l'objet qui ping.
	 */
	protected VObject getObject() {
		return this.object;
	}

	/**
	 * Accesseur sur le mode de l'objet
	 * 
	 * @return le mode de l'objet.
	 */
	protected int getMode() {
		return this.mode;
	}

	/**
	 * Retourne l'événement pour dormir.
	 */
	private int setSleep() {
		int theResult;
		if (getCurrentTrame() != Message.EV_ASLEEP) {
			theResult = Message.EV_ASLEEP;
		} else {
			theResult = 0; // Pas de bytecode.
		}
		return theResult;
	}

	/**
	 * Détermine l'ID du message à envoyer.
	 * 
	 * @return le message à envoyer.
	 */
	private int solveevent() {
		final long before = System.currentTimeMillis();
		final int theResult; // événement à télécharger.
		final int object_state = this.object.getObject_state();

		if ((object_state == VObject.STATUS_FORCE_VEILLE) || (object_state == VObject.STATUS_VEILLE)) {
			theResult = setSleep();
		} else {
			theResult = nextsolveevent();
		}

		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventPing.LOGGER.info("TIME LEAK EventPing.solveevent " + (after - before) + StringShop.SPACE + this.object.getObject_serial());
		}
		return theResult;
	}

	private boolean ack(int event, int ack) {
		final long before = System.currentTimeMillis();
		Enum<InterruptionLogImpl.WHAT_OPTION> inValue = null;
		boolean reCountNbMessage = false;
		if (ack == 1) // tout acquitter/acquitter un
		{
			if (event != Message.EV_DUMMY) {
				EventMng.ackOneByEvent(this.object, event);
				inValue = InterruptionLogImpl.WHAT_OPTION.BUTTON_DOUBLE_CLIC_ONREAD;
			} else {
				EventMng.ackAll(this.object);
				inValue = InterruptionLogImpl.WHAT_OPTION.BUTTON_DOUBLE_CLIC;
			}
			reCountNbMessage = true;
		} else if ((ack == 2) || (ack == 4)) {
			// Simple pression pendant la lecture (v2 & v1), fin de lecture (v2
			// & v1) et clic long (ack == 4; v1)
			EventMng.played(this.object, event, true);
			reCountNbMessage = true;
		} else if (ack == 3) { // relire les messages en attente
			EventMng.reRead(this.object);
			inValue = InterruptionLogImpl.WHAT_OPTION.BUTTON_SIMPLE_CLIC;
		} else if (ack == 5) { // "pause" utilisé lors d'un stream de webradio EventMng.pause(object,event);
			EventMng.played(this.object, event, true);
			reCountNbMessage = true;
		} else if ((ack & 0x8000) != 0) // position d'oreilles
		{
			int earl = (ack >> 8) & 127;
			int earr = ack & 127;
			if (earl >= 17) {
				earl = 0;
			}
			if (earr >= 17) {
				earr = 0;
			}
			inValue = InterruptionLogImpl.WHAT_OPTION.EARS;
			EarMng.earNotify(this.object, earl, earr);
		}

		//computes the number of message only if object played a message earlier
		if (reCountNbMessage) {
			this.theNbNewMessages = Factories.MESSAGE_COUNTER.getRabbitStateByRecipient(Factories.MESSENGER.getByObject(this.object));
			if ((this.theNbNewMessages != null) && (RABBIT_STATE.INVALIDE != this.theNbNewMessages)) {
				this.object.setNbMsg(this.theNbNewMessages.getValue());
			}
		}

		if (inValue != null) {
			// Loggue des interruptions sur les objects ping
			InterruptionLogImpl.insert(this.object, inValue, null);
		}

		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventPing.LOGGER.info("TIME LEAK EventPing.ack " + (after - before) + StringShop.SPACE + this.object.getObject_serial());
		}
		return true;
	}

	protected byte[] solve() {
		final long before = System.currentTimeMillis();
		byte[] result = null;
		if (this.object == null) {
			// L'objet est en phase d'inscription ou erreur
			if (this.theMessageDraft != null) {
				result = generatePingPacket(this.theMessageDraft);
			}
		} else {
			// reset quand reboot demandé
			if (this.stateReboot) {
				result = MessageSerializer.REBOOT_PACKET;
				this.object.resetRebootState();
			} else {
				if (this.senddata != 0) {
					if ((this.senddata != 3) || !EventImpl.hasEvent(this.object)) {
						ack(getAckTrame(), this.senddata);
						refreshObjectCache();
					}
				}

				final int theEventId = solveevent();
				final StoredMessage theMessage = new StoredMessage(this.object, theEventId);
				//when object's status is sleep, do not compute sources and number of message.
				if (Message.EV_ASLEEP == getCurrentTrame()) {
					theMessage.setEars(this.object.getObject_left(), this.object.getObject_right());
				} else {
					theMessage.setNbMessages(RABBIT_STATE.getState(this.object.getObject_nbmsg()));
					theMessage.loadSourcesFromDatabase();
				}
				result = generatePingPacket(theMessage);
				GroupUpdate.update(this.object.getId());
			}
		}
		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventPing.LOGGER.info("TIME LEAK EventPing.solve " + (after - before) + StringShop.SPACE + this.object.getObject_serial());
		}
		return result;
	}

	private EventPing(String ip, int serverport, String inSerial, String version, int state, int senddata, int tramesend, int tramecurrent, int tramenext, HARDWARE inHardware) {
		final String computedSerial = EventPing.computeSerial(inSerial, serverport);
		final long before = System.currentTimeMillis();
		this.senddata = senddata;
		this.mHardware = inHardware;

		final VObject theObject = Factories.VOBJECT.findBySerial(computedSerial);
		// Est-ce que l'objet existe dans la base?
		if (theObject == null) {
			// On n'a pas inscrit le lapin, on récupère le message soit
			// d'attente ou de 'va t'inscrire'
			this.theMessageDraft = Provisionning.addObjectInQueue(ip, computedSerial, inHardware);
		}

		// this.object vaut:
		// l'objet (s'il est inscrit)
		// un lapin fictif
		// null en cas d'erreur.
		this.object = theObject;

		if (this.object != null) {
			if (this.object.getObject_test() == -1) {
				this.stateReboot = true;
			} else {
				this.stateReboot = false;
			}
			final int theMode = this.object.getObject_mode();

			// si l'objet était inactif on le repasse en ping normal
			if (theMode == VObject.MODE_PING_INACTIVE) {
				this.mode = VObject.MODE_PING;
				this.object.setMode(this.mode);
			} else {
				this.mode = theMode;
			}
			//Allows to migrate the recalculation of state (sleep or wake) to the crawler in zero downtime
			final int theStatus = this.object.getObject_state();
			if ((theStatus == VObject.STATUS_NORMAL)) {
				final boolean isSleeping = ObjectSleep.ObjectSleepCommon.asleep(this.object);
				this.object.setState((isSleeping ? VObject.STATUS_VEILLE : VObject.STATUS_ACTIF));
			}

			this.cached_n1 = this.object.getObject_n1();
			this.cached_n2 = this.object.getObject_n2();

		} else {
			this.mode = 0;
			this.stateReboot = false;
		}
		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventPing.LOGGER.info("TIME LEAK EventPing.<init> " + (after - before) + StringShop.SPACE + computedSerial);
		}

		this.mFirmwareVersion = version;
		this.tramesend = tramesend;
		this.tramecurrent = tramecurrent;
		this.tramenext = tramenext;
		this.downp = 0; // niveau de priorite de l'evenement a telecharger
		this.tokill = 0; // evenement a supprimer
	}

	private static String computeSerial(String inSerial, int inPort) {
		final String theResult;
		if (inPort == 80) {
			theResult = inSerial;
		} else {
			theResult = inSerial + inPort;
		}
		return theResult;
	}

	protected void refreshObjectCache() {
		final VObject theObject = getObject();
		this.cached_n1 = theObject.getObject_n1();
		this.cached_n2 = theObject.getObject_n2();
	}

	private void updatePingCache() {
		final VObject theObject = getObject();
		this.cached_n1 = this.cached_n2 = 0;

		final List<Event> theEventList = EventImpl.findTwoLastReceivedMessages(theObject);

		for (final Event theEvent : theEventList) {
			if (this.cached_n1 == 0) {// le premier event
				this.cached_n1 = theEvent.getId().intValue();
			} else if (this.cached_n2 == 0) {// le deuxième event
				this.cached_n2 = theEvent.getId().intValue();
			}
		}

		if (this.cached_n1 == 0) {
			this.cached_n1 = Message.EV_DUMMY;
		}
		if (this.cached_n2 == 0) {
			this.cached_n2 = Message.EV_DUMMY;
		}
		if (this.cached_n1 == Message.EV_DUMMY) {
			theObject.setPingCache(this.cached_n1, this.cached_n2);
		}
	}

	/**
	 * Calcule le prochain événement.
	 * 
	 * @return l'id du prochain événement.
	 */
	protected int nextsolveevent() {
		// Pour les V1:
		// 0 -> on ne télécharge pas de bytecode
		// EV_DUMMY -> téléchargement de wait.vasm
		// autre -> téléchargement d'un message à lire

		int theResult = 0;

		// On recalcule n1 si:
		// - on a forcé avec cached_n1 = 0
		if (this.cached_n1 == 0) {
			updatePingCache();
		}

		if (this.cached_n1 == this.tramecurrent) {
			// ok, chercher le suivant
			if (this.cached_n2 != this.tramenext) {
				theResult = this.cached_n2;
			}
		} else {
			if (this.cached_n1 == this.tramenext) {
				// n1 == DUMMY == tramenext
				// tc != DUMMY (autre bytecode).
				// NB: on ne rentre jamais là????
				this.tokill = this.tramecurrent;
			} else {
				theResult = this.cached_n1;
				this.downp = 1;
			}
		}

		return theResult;
	}

	/**
	 * Retourne le paquet.
	 */
	protected byte[] generatePingPacket(Message inMessage) {
		if (inMessage.getEventID().longValue() == Message.EV_ASLEEP) {
			this.downp = 1;
		}
		return MessageSerializer.generatePingPacket(inMessage, this.downp, this.mFirmwareVersion, this.tokill, this.mHardware);
	}

	/**
	 * Retourne la trame courante.
	 * 
	 * @return tramecurrent.
	 */
	protected int getCurrentTrame() {
		return this.tramecurrent;
	}

	/**
	 * Retourne la trame courante.
	 * 
	 * @return ((tramesend!=0)?tramesend:tramecurrent).
	 */
	protected int getAckTrame() {
		final VObject theObject = getObject();
		theObject.clearPingCache();
		return this.tramesend != 0 ? this.tramesend : this.tramecurrent;
	}

	public static byte[] solve(String ip, int serverport, String serial, String version, int state, int senddata, int tramesend, int tramecurrent, int tramenext, HARDWARE inHardware) throws InterruptedException {
		final long before = System.currentTimeMillis();
		final byte[] theResult;
		EventPing.SEMAPHORE.acquire();
		final long afterAcquire = System.currentTimeMillis();
		try {
			final EventPing theObject = new EventPing(ip, serverport, serial, version, state, senddata, tramesend, tramecurrent, tramenext, inHardware);
			theResult = theObject.solve();
		} finally {
			EventPing.SEMAPHORE.release();
			final long after = System.currentTimeMillis();
			// LOG.
			final long duration = after - before;
			final long wait = afterAcquire - before;
			EventPing.LOGGER.info("PING " + ip + StringShop.SPACE + serial + StringShop.SPACE + duration + " ms (" + (duration - wait) + " ms)");
			ProbesHandler.EVENT.solve(duration, wait);
		}
		return theResult;
	}

	private final static String SN_PARAM = "sn";
	private final static String V_PARAM = "v";
	private final static String ST_PARAM = "st";
	private final static String SD_PARAM = "sd";
	private final static String TS_PARAM = "ts";
	private final static String TC_PARAM = "tc";
	private final static String TN_PARAM = "tn";

	/**
	 * Point d'entrée des servlet de ping PingV1EntryPoint et
	 * PingDalDalEntryPoint.
	 * 
	 * @param inRequest : récupération de la request
	 * @param inHardware : Type d'hardware ( V1 ou daldal)
	 * @return
	 */
	public static byte[] processPing(HttpServletRequest inRequest, HARDWARE inHardware) {
		byte[] theResult = null;
		final String theSerial = inRequest.getParameter(EventPing.SN_PARAM);

		if (inHardware.checkIdentifier(theSerial)) {
			try {
				theResult = EventPing.solve(inRequest.getRemoteAddr(), inRequest.getServerPort(), theSerial, inRequest.getParameter(EventPing.V_PARAM), ConvertTools.htoi(inRequest.getParameter(EventPing.ST_PARAM)), ConvertTools.htoi(inRequest.getParameter(EventPing.SD_PARAM)), ConvertTools.htoi(inRequest.getParameter(EventPing.TS_PARAM)), ConvertTools.htoi(inRequest.getParameter(EventPing.TC_PARAM), false), ConvertTools.htoi(inRequest.getParameter(EventPing.TN_PARAM), false), inHardware);
			} catch (final InterruptedException e) {
				EventPing.LOGGER.fatal(e, e);
			}
		}
		return theResult;
	}
}
