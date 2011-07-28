package net.violet.platform.applets.interactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.events.InteractionEvent;
import net.violet.platform.events.TriggerEvent;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.ping.EventMng;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;

/**
 * Classe pour l'application répondeur
 */
public class VoiceMailApplet extends AbstractInteractiveApplet {

	private static final Logger LOGGER = Logger.getLogger(VoiceMailApplet.class);

	// TODO: à dégager dans la table applet
	public static final long APPLET_ID = 2;

	/**
	 * Identifiant pour l'application VoiceMail
	 */
	public static final String APPLET_NAME = "VoiceMail";

	public VoiceMailApplet() {
		// This space for rent.
	}

	/**
	 * Accesseur sur l'id de l'applet.
	 */
	public Long getAppletID() {
		return VoiceMailApplet.APPLET_ID;
	}

	/**
	 * Génération d'un message correspondant au ZtampImpl lu.
	 * 
	 * @param inAction : Trigger sur l'action liée au ZtampImpl.
	 * @param inObject : objet concerné.
	 * @param inRfidSerial : serial du ZtampImpl lu.
	 * @return prépare un MessageImpl qui sera effectué par l'objet
	 */
	public void processTrigger(TriggerEvent inEvent) {
		// Pas de RFID ici.
		final MessageDraft theMessage = new MessageDraft(((InteractionEvent) inEvent.getEvent()).getTarget().getReference());
		theMessage.addEndInteractive();
		theMessage.setTTLInSecond(inEvent.getTTL());
		MessageServices.send(theMessage);
	}

	/**
	 * Lancement du mode répondeur sur le lapin
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 * @throws ConversionException
	 * @throws InvalidParameterException
	 */
	@Override
	protected List<Message> process_start(VObject inObject, Subscription inSubscription, String inCookie) throws InvalidParameterException, ConversionException {

		final MessageDraft theMessage = new MessageDraft(inObject);
		theMessage.addSetting("record.enabled=false");
		theMessage.addSetting("int.mask=6"); // interruption oreille gauche/droite désactivé

		long maxIdMessage = 0;
		final net.violet.platform.datamodel.Message theLastMessage = Factories.MESSAGE.findLastMessageReceived(inObject);
		if (theLastMessage != null) {
			maxIdMessage = theLastMessage.getId();
		}
		fillNewMessageReceived(theMessage, inObject, maxIdMessage);

		return Arrays.asList((Message) theMessage);
	}

	/**
	 * Le bouton du lapin est appuyé pendant la lecture d'un mp3, si simple clic
	 * : on marque le message comme lu et on passe au prochain si il y a si
	 * double clic : on marque le message comme archivé et on passe au prochain
	 * si il y a
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @param cookie : afin de garder le contexte
	 * @param pos : nombre d'octets lus dans le flux
	 * @param idx : index de la commande musicale qui est interrompue
	 * @param btn : 1 si simple clic, 2 si double clic
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 * @throws ConversionException
	 * @throws InvalidParameterException
	 */
	@Override
	protected List<Message> process_button(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx, int btn) throws InvalidParameterException, ConversionException {

		final MessageDraft theMessage = new MessageDraft(inObject);

		final Cookie theCookie = new Cookie(cookie);
		final net.violet.platform.datamodel.Message theResultMessage = Factories.MESSAGE.find(theCookie.getIdMessage());
		if (theResultMessage != null) {
			if (Message.CLIC_SIMPLE == btn) {
				EventMng.played(inObject, theResultMessage, false);
			} else if (Message.CLIC_DOUBLE == btn) {
				EventMng.ackOne(inObject, theResultMessage, false);
			}
		}
		fillNewMessageReceived(theMessage, inObject, theCookie.getLastIdMessage());

		return Arrays.asList((Message) theMessage);
	}

	/**
	 * les oreilles du lapin ont été bougés pendant la lecture d'un mp3 , pour
	 * l'instant impossible dans le mode répondeur
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @param cookie : afin de garder le contexte
	 * @param earl : oreille gauche
	 * @param earr : oreille droite
	 * @param pos : nombre d'octets lus dans le flux
	 * @param idx : index de la commande musicale qui est interrompue
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 */
	@Override
	protected List<Message> process_ear(VObject inObject, Subscription inSubscription, String cookie, int earl, int earr, int pos, int idx) {

		// Les oreilles ne sont pas utilisés pour ce type de lecture pour
		// l'instant
		final MessageDraft theMessage = new MessageDraft(inObject);

		theMessage.addEndInteractive();

		return Arrays.asList((Message) theMessage);
	}

	/**
	 * Il y a un appuie long sur le bouton du lapin pendant la lecture d'un mp3
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @param cookie : afin de garder le contexte
	 * @param pos : nombre d'octets lus dans le flux
	 * @param idx : index de la commande musicale qui est interrompue
	 * @param btn : 1 si simple clic, 2 si double clic
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 */
	@Override
	protected List<Message> process_reco(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx, int btn, byte[] recoFile) {

		// La reco n'est pas utilisé pour ce type de lecture
		final MessageDraft theMessage = new MessageDraft(inObject);

		theMessage.addEndInteractive();

		return Arrays.asList((Message) theMessage);
	}

	/**
	 * Le lapin a fini de lire un message recu
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @param cookie : afin de garder le contexte
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 * @throws ConversionException
	 * @throws InvalidParameterException
	 */
	@Override
	protected List<Message> process_finished(VObject inObject, Subscription inSubscription, String cookie) throws InvalidParameterException, ConversionException {

		final MessageDraft theMessage = new MessageDraft(inObject);

		final Cookie theCookie = new Cookie(cookie);
		final net.violet.platform.datamodel.Message theResultMessage = Factories.MESSAGE.find(theCookie.getIdMessage());
		if (theResultMessage != null) {
			EventMng.played(inObject, theResultMessage, false);
		}

		fillNewMessageReceived(theMessage, inObject, theCookie.getLastIdMessage());

		return Arrays.asList((Message) theMessage);

	}

	@Override
	protected List<Message> process_rfid(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx) {
		return new ArrayList<Message>();
	}

	/**
	 * retourne soit le prochain message recu ou sinon termine le mode répondeur
	 * 
	 * @param inMessageDraft
	 * @param inObject : Objet concerné
	 * @param inIdLastMessage : id du dernier message récupèrer dans le
	 *            répondeur
	 * @throws ConversionException
	 * @throws InvalidParameterException
	 */
	private void fillNewMessageReceived(MessageDraft inMessageDraft, VObject inObject, long inIdLastMessage) throws InvalidParameterException, ConversionException {

		final Cookie theCookie = new Cookie();
		boolean newMessage = false;

		final net.violet.platform.datamodel.Message theFirstMessage = Factories.MESSAGE.findFirstMessageReceived(inObject);
		if (theFirstMessage != null) {
			final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theFirstMessage.getId());
			if (theMessageReceived != null) {

				if (inIdLastMessage > 0) { // on récupère le dernier message du répondeur
					theCookie.setLastIdMessage(inIdLastMessage);// on le met dans le cookie on ne prend pas en compte les message qui arrivent pendant le mode répondeur
					if (theFirstMessage.getId() <= inIdLastMessage) {
						theCookie.setIdMessage(theFirstMessage.getId());
						inMessageDraft.addSetting(theCookie.getFromCookie());
						MessageServices.fillMessageDraftWithUserMessage(inMessageDraft, theFirstMessage, theMessageReceived.getSender());
						newMessage = true;
					}
				}
			}
		}

		/**
		 * recalcule à la fin du mode répondeur le nombre de message en attente
		 * cas du message qui arrive pendant le mode répondeur, il sera lu une
		 * fois et donc sera tjs dans la inbox.
		 */
		if (!newMessage) {
			EventMng.refreshCountMessagesAfterPlaying(inObject);
			inMessageDraft.addEndInteractive();
		}

	}

	private static class Cookie {

		private long mIdMessage;
		private long mLastIdMessage;

		public Cookie(String settings) {
			this.mIdMessage = 0;
			this.mLastIdMessage = 0;
			if (settings != null) { // on garde ce code au cas où on voudrait passer plus d'info dans le cookie
				final StringTokenizer thecookieList = new StringTokenizer(settings, ";");
				while (thecookieList.hasMoreTokens()) {
					final String[] cookieValues = thecookieList.nextToken().split(":");
					if (cookieValues.length == 2) {
						if ("id".equals(cookieValues[0].toLowerCase())) { //l'id du message
							this.mIdMessage = ConvertTools.atoi(cookieValues[1]);
						} else if ("lid".equals(cookieValues[0].toLowerCase())) { // l 'id du dernier message recu dans le
							// répondeur
							this.mLastIdMessage = ConvertTools.atoi(cookieValues[1]);
						}
						VoiceMailApplet.LOGGER.debug(cookieValues[0] + ":" + cookieValues[1]);
					}
				}
			}
		}

		public Cookie() {
			this.mIdMessage = 0;
			this.mLastIdMessage = 0;
		}

		public String getFromCookie() {
			final String result = "cookie=id:" + getIdMessage() + ";" + "lid:" + getLastIdMessage() + ";";
			return result;
		}

		public long getIdMessage() {
			return this.mIdMessage;
		}

		public void setIdMessage(long id) {
			this.mIdMessage = id;
		}

		public long getLastIdMessage() {
			return this.mLastIdMessage;
		}

		public void setLastIdMessage(long id) {
			this.mLastIdMessage = id;
		}
	}

}
