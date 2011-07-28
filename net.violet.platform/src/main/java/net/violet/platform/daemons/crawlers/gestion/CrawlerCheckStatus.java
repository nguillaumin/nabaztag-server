package net.violet.platform.daemons.crawlers.gestion;

import java.util.List;

import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.VObjectImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

// permet de savoir quand le lapin devra se mettre en veille et quand il devra se mettre active

public class CrawlerCheckStatus extends AbstractCrawler {


	private static final Logger LOGGER = Logger.getLogger(CrawlerCheckStatus.class);

	/**
	 * Walker sur les objets.
	 */
	private final VObjectImpl.RecordWalker<VObject> mWalkerWakeUp = new RecordWalker<VObject>() {

		public void process(VObject inObject) {
			processWakeUpObject(inObject);
		}
	};
	private final VObjectImpl.RecordWalker<VObject> mWalkerSleep = new RecordWalker<VObject>() {

		public void process(VObject inObject) {
			processSleepObject(inObject);
		}
	};

	public CrawlerCheckStatus(String[] inArgs) {
		super(inArgs);
	}

	/**
	 * @param inObject : objet
	 * @param inModeStatus : etat dans lequel l'objet va passé
	 * @param inMessagePacketMode :
	 */
	protected void sendXmppStatus(VObject inObject, int inModeStatus, int inMessagePacketMode) {
		final MessageDraft theMessage = new MessageDraft(inObject);
		theMessage.setTTLInSecond(Constantes.QUEUE_TTL_STATUS);
		theMessage.setStatus(inModeStatus);
		theMessage.setEars(inObject.getObject_left(), inObject.getObject_right());
		theMessage.setSourceUpdate(true);
		MessageServices.sendUsingXmpp(theMessage, inMessagePacketMode);
	}

	@Override
	protected void process() {
		Factories.TIMEZONE.walkDistincts(new RecordWalker<Timezone>() {

			public void process(Timezone inTimezone) {
				CrawlerCheckStatus.this.process(inTimezone, false);
				CrawlerCheckStatus.this.process(inTimezone, true);
			}

		});
	}

	/**
	 * Traitement pour une timezone & un état.
	 * 
	 * @param inTimezoneId timezone
	 * @param inState état (<code>true</code> pour active, <code>false</code>
	 *            pour asleep).
	 */
	int process(Timezone inTimezone, boolean inState) {
		final int nbProcessed = Factories.VOBJECT.walkObjectsStatus(((inState) ? this.mWalkerWakeUp : this.mWalkerSleep), inTimezone, inState);
		CrawlerCheckStatus.LOGGER.info(" total go " + (inState ? "active" : "asleep") + ": " + nbProcessed);
		return nbProcessed;
	}

	/**
	 * Traitement pour un objet.
	 */
	protected void processSleepObject(VObject inObject) {
		boolean doXmppStatus = true;
		final boolean isXmppObject = inObject.isXMPP();

		//Special case for xmpp object, we will check the resources
		String theRessource = null;
		if (isXmppObject) {
			final List<String> resources = inObject.getResources(); //IQResourcesQuery.getClientResources(inObject.getXmppAddress());
			if (resources.isEmpty()) {
				inObject.setState(VObject.STATUS_VEILLE);
			} else if (resources.contains("asleep")) {
				theRessource = resources.get(0);
				doXmppStatus = false;
			}
		}

		final int inState = inObject.getObject_state();
		CrawlerCheckStatus.LOGGER.info("Processing sleep for " + inObject.getObject_serial() + " / " + inState + " / " + theRessource);

		//all STATUS_WILLBE_* are used only for xmpp objects
		switch (inState) {
		case VObject.STATUS_VEILLE: // special case because cache notification is slow/missing so i override this value in db.
			inObject.overrideState(VObject.STATUS_VEILLE);
			sendXmppStatus(inObject, Message.MODE_VEILLE, JabberMessageFactory.IQ_STATUS_IDLE_MODE);
			break;
		case VObject.STATUS_ACTIF:
		case VObject.STATUS_FORCE_VEILLE:
		case VObject.STATUS_WILLBE_ACTIF:
		case VObject.STATUS_WILLBE_VEILLE:
		case VObject.STATUS_WILLBE_FORCE_VEILLE:
			if (isXmppObject && doXmppStatus) {
				sendXmppStatus(inObject, Message.MODE_VEILLE, JabberMessageFactory.IQ_STATUS_IDLE_MODE);
			} else {
				inObject.setState(VObject.STATUS_VEILLE);
			}
			break;
		case VObject.STATUS_WILLBE_FORCE_ACTIF: //normally, object ping do not enter in this condition
			if (isXmppObject && !doXmppStatus) {
				sendXmppStatus(inObject, Message.MODE_FORCE_ACTIF, JabberMessageFactory.IQ_STATUS_ASLEEP_MODE);
			} else {
				inObject.setState(VObject.STATUS_FORCE_ACTIF);
			}
			break;
		}
	}

	/**
	 * Traitement pour un objet.
	 */
	protected void processWakeUpObject(VObject inObject) {
		boolean doXmppStatus = true;
		final boolean isXmppObject = inObject.isXMPP();

		//Special case for xmpp object, we will check the resources
		String theRessource = null;
		if (isXmppObject) {
			final List<String> resources = inObject.getResources(); //IQResourcesQuery.getClientResources(inObject.getXmppAddress());
			if (resources.isEmpty()) {
				inObject.setState(VObject.STATUS_ACTIF);
			} else if (resources.contains("idle") || resources.contains("streaming") || resources.contains("itmode") || resources.contains("busy")) {
				theRessource = resources.get(0);
				doXmppStatus = false;
			}
		}

		final int inState = inObject.getObject_state();
		CrawlerCheckStatus.LOGGER.info("Processing wakeup for " + inObject.getObject_serial() + " / " + inState + " / " + theRessource);

		//all STATUS_WILLBE_* are used only for xmpp objects
		switch (inState) {
		case VObject.STATUS_ACTIF: // special case because cache notification is slow/missing so i override this value in db. 
			inObject.overrideState(VObject.STATUS_ACTIF);
			sendXmppStatus(inObject, Message.MODE.ACTIF.getId(), JabberMessageFactory.IQ_STATUS_ASLEEP_MODE);
			break;
		case VObject.STATUS_VEILLE:
		case VObject.STATUS_FORCE_ACTIF:
		case VObject.STATUS_WILLBE_VEILLE:
		case VObject.STATUS_WILLBE_ACTIF:
		case VObject.STATUS_WILLBE_FORCE_ACTIF:
			if (inObject.isXMPP() && doXmppStatus) {
				sendXmppStatus(inObject, Message.MODE.ACTIF.getId(), JabberMessageFactory.IQ_STATUS_ASLEEP_MODE);
			} else {
				inObject.setState(VObject.STATUS_ACTIF);
			}
			break;
		case VObject.STATUS_WILLBE_FORCE_VEILLE: //normally, object ping do not enter in this condition
			if (inObject.isXMPP() && !doXmppStatus) {
				sendXmppStatus(inObject, Message.MODE.FORCE_VEILLE.getId(), JabberMessageFactory.IQ_STATUS_IDLE_MODE);
			} else {
				inObject.setState(VObject.STATUS_FORCE_VEILLE);
			}
			break;
		}
	}

}
