package net.violet.platform.ping;

import java.sql.SQLException;
import java.util.List;
import java.util.SortedMap;
import java.util.Map.Entry;

import net.violet.platform.applets.interactive.VoiceMailApplet;
import net.violet.platform.applications.BilanHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Event;
import net.violet.platform.datamodel.EventImpl;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageReceivedImpl;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.Templates;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

public class EventMng {

	private static final Logger LOGGER = Logger.getLogger(EventMng.class);

	/**
	 * Nombre maximum de messages relus si on appuie sur le bouton.
	 */
	private static final int MAX_REREAD = 30;

	/**
	 * Envoie un paquet de mise à jour du nombre de message en attente
	 * 
	 * @param inRecipient
	 */
	public static void refreshCountMessagesAfterPlaying(VObject inObjet) {
		EventMng.refreshCountMessagesAfterPlaying(Factories.MESSENGER.getByObject(inObjet));
	}

	/**
	 * Envoie un paquet de mise à jour du nombre de message en attente
	 * 
	 * @param inRecipient
	 */
	public static void refreshCountMessagesAfterPlaying(Messenger inRecipient) {
		MessageReceived.CommonMessageReceived.invalidateDecrement(inRecipient);
		if (inRecipient.getObject() != null) {
			EventMng.sendMessage(inRecipient);
		} else {
			EventMng.LOGGER.fatal("cannot match VObject with Messenger!!");
		}

	}

	/**
	 * Envoie un paquet de mise à jour du nombre de message en attente
	 * 
	 * @param inRecipient
	 */
	public static void refreshCountMessagesAfterSending(VObject inObject) {
		EventMng.refreshCountInboxMessagesAfterSending(Factories.MESSENGER.getByObject(inObject));
	}

	/**
	 * Envoie un paquet de mise à jour du nombre de message en attente
	 * 
	 * @param inRecipient
	 */
	public static void refreshCountInboxMessagesAfterSending(Messenger inRecipient) {

		MessageReceived.CommonMessageReceived.invalidateIncrement(inRecipient);

		if (inRecipient.getObject() != null) {
			EventMng.sendMessage(inRecipient);
		} else {
			EventMng.LOGGER.fatal("cannot match VObject with Messenger!!");
		}
	}

	/**
	 * Sends the updating message
	 * 
	 * @param inRecipient
	 */
	private static void sendMessage(Messenger inRecipient) {
		final VObject theObject = inRecipient.getObject();
		if (theObject.isXMPP()) {
			final MessageDraft theMessage = new MessageDraft(theObject);
			// GET STATE
			theMessage.setNbMessages(Factories.MESSAGE_COUNTER.getRabbitStateByRecipient(inRecipient));
			theMessage.setSourceUpdate(true);
			theMessage.setTTLInSecond(Constantes.QUEUE_TTL_SOURCES);
			MessageServices.sendUsingXmpp(theMessage, JabberMessageFactory.SOURCES_MODE);
		} else {
			theObject.clearPingCache();
		}
	}

	/**
	 * Envoie tous les messages de la boîte de réception dans les archives
	 * 
	 * @param object
	 */
	public static void ackAll(VObject inObject) {
		final long before = System.currentTimeMillis();
		final Messenger theRecipient = Factories.MESSENGER.getByObject(inObject);
		MessageReceivedImpl.ackAll(theRecipient);

		MessageReceived.CommonMessageReceived.resetCount(theRecipient);
		EventMng.sendMessage(theRecipient);
		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventMng.LOGGER.info("TIME LEAK EventMng.ackall " + (after - before) + net.violet.common.StringShop.SPACE + inObject.getObject_serial());
		}
	}

	/**
	 * Incrémente le compteur de lecture sur un message de la boite de réception
	 * et archive si au delà de la deuxième lecture
	 * 
	 * @param object : objet concerné
	 * @param eventId : id de l'evenement (table event)
	 * @param inUpdateNbrMessage : envoi un paquet de mise à jour du nombre de
	 *            message en attente
	 */
	public static void ackOneByMessage(VObject object, long messageId) {
		final long before = System.currentTimeMillis();
		final Message theMessage = Factories.MESSAGE.find(messageId);

		EventMng.ackOne(object, theMessage, true);

		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventMng.LOGGER.info("TIME LEAK EventMng.ackOne " + (after - before) + net.violet.common.StringShop.SPACE + object.getObject_serial());
		}
	}

	/**
	 * Incrémente le compteur de lecture sur un message de la boite de réception
	 * et archive si au delà de la deuxième lecture
	 * 
	 * @param object : objet concerné
	 * @param eventId : id de l'evenement (table event)
	 * @param inUpdateNbrMessage : envoi un paquet de mise à jour du nombre de
	 *            message en attente
	 */
	public static void ackOneByEvent(VObject object, int eventId) {
		final long before = System.currentTimeMillis();
		final Message theMessage = Factories.MESSAGE.findByEventID(eventId);

		EventMng.ackOne(object, theMessage, true);

		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventMng.LOGGER.info("TIME LEAK EventMng.ackOne " + (after - before) + net.violet.common.StringShop.SPACE + object.getObject_serial());
		}
	}

	/**
	 * Incrémente le compteur de lecture sur un message de la boite de réception
	 * et archive si au delà de la deuxième lecture
	 * 
	 * @param object : objet concerné
	 * @param inMessage : MessageImpl concerné
	 * @param inUpdateNbrMessage : envoi un paquet de mise à jour du nombre de
	 *            message en attente
	 */
	public static void ackOne(VObject inObject, Message inMessage, boolean updateNbrMessage) {

		if (inMessage != null) {
			final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(inMessage.getId());

			if (theMessageReceived != null) {
				theMessageReceived.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED);
				if (updateNbrMessage) {
					EventMng.refreshCountMessagesAfterPlaying(theMessageReceived.getRecipient());
				}
			}

			inMessage.setCount(inMessage.getCount() + 1);
			try {
				inMessage.deleteEvent();
			} catch (final SQLException e) {
				EventMng.LOGGER.fatal(e, e);
			}
		} else {
			inObject.clearPingCache();
		}
	}

	/**
	 * Envoie tout le contenu de la boîte de réception au lapin pour être relu
	 * pour les lapins ping sinon on passe en mode répondeur
	 * 
	 * @param inObjet
	 */
	public static void reRead(VObject inObjet) {
		if (!inObjet.isXMPP()) {
			final long before = System.currentTimeMillis();
			final Messenger theRecipient = Factories.MESSENGER.getByObject(inObjet);

			try {
				final SortedMap<Message, MessageReceived> theMessagesReceived = theRecipient.getInboxMessagesSorted();

				int nbMessages = 0;
				for (final Entry<Message, MessageReceived> theEntry : theMessagesReceived.entrySet()) {
					final Message theMessage = theEntry.getKey();
					final MessageReceived theMessageReceived = theEntry.getValue();
					MessageServices.resendUserMessage(theMessage, theMessageReceived.getSender(), theMessageReceived.getRecipient());
					if (++nbMessages >= EventMng.MAX_REREAD) {
						break;
					}
				}
			} catch (final SQLException e) {
				EventMng.LOGGER.fatal(e, e);
			}
			final long after = System.currentTimeMillis();
			if (after - before > 1000) {
				EventMng.LOGGER.info("TIME LEAK EventMng.reRead " + (after - before) + net.violet.common.StringShop.SPACE + inObjet.getObject_serial());
			}
		} else {
			final Message theResultMessage = Factories.MESSAGE.findFirstMessageReceived(inObjet);
			if (theResultMessage != null) { // il a au moins un nouveau message en attente
				MessageServices.sendStartApplication(inObjet, VoiceMailApplet.APPLET_NAME);
			}
		}
	}

	/**
	 * Méthode appelée lorsqu'un message est joué par un lapin ping.
	 * 
	 * @param inObject objet qui a reçu le message.
	 * @param inEventId id de l'event.
	 * @param inUpdateNbrMessage : envoi un paquet de mise à jour du nombre de
	 *            message en attente
	 */
	public static void played(VObject inObject, int inEventId, boolean inUpdateNbrMessage) {
		final long before = System.currentTimeMillis();
		final Message theMessage = Factories.MESSAGE.findByEventID(inEventId);
		if (theMessage != null) {
			EventMng.played(inObject, theMessage, inUpdateNbrMessage);
			try {
				theMessage.deleteEvent();
			} catch (final SQLException e) {
				EventMng.LOGGER.fatal(e, e);
			}
		} else {
			EventMng.LOGGER.info("No message found for event : " + inEventId);
			Event theEvent;
			theEvent = EventImpl.find(inEventId);
			if (theEvent != null) {
				Factories.EVSEQ.deleteByEventID(theEvent.getId());
				theEvent.delete();
			}
			EventMng.refreshCountMessagesAfterPlaying(inObject);
		}
		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventMng.LOGGER.info("TIME LEAK EventMng.played " + (after - before) + net.violet.common.StringShop.SPACE + inObject.getObject_serial());
		}
	}

	/**
	 * Méthode appelée lorsqu'un message est joué: - ping avec sd = 2, via
	 * {@link played(VObjectImpl, int)} - xmpp (notify) : lorsqu'un message est
	 * joué par un objet xmpp ou en mode interactif. Met à jour le nombre de
	 * messages non lus.
	 * 
	 * @param inObject objet qui a reçu le message.
	 * @param inMessage message.
	 * @param inMessageReceived message reçu.
	 * @param updateNbrMessage : envoi un paquet de mise à jour du nombre de
	 *            message en attente
	 */
	public static void played(VObject inObject, Message inMessage, boolean updateNbrMessage) {
		final long before = System.currentTimeMillis();
		final User sender;

		final int limit2Archive = VObjectData.getData(inObject).getObjectType().instanceOf(ObjectType.RFID) ? 0 : 1;

		// On incrémente le nombre de fois qu'on a joué le message puisqu'on
		// vient de le lire
		inMessage.setCount(inMessage.getCount() + 1);

		final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(inMessage.getId());
		if (theMessageReceived != null) {
			sender = theMessageReceived.getSender().getUser();

			if (inMessage.getCount() > limit2Archive) {
				theMessageReceived.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED);
				if (updateNbrMessage) {
					EventMng.refreshCountMessagesAfterPlaying(inObject);
				}
			} else {
				theMessageReceived.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.INBOX);
			}

			if (null == inMessage.getNabcast()) {
				// Accusés de lecture et de réception par e-mail
				EventMng.notifyPlayedByEmail(inMessage, sender, inObject);

				if (inMessage.getCount() < 2) {
					final List<Subscription> subscriptions = Factories.SUBSCRIPTION.findByApplicationAndObject(Application.NativeApplication.BILAN.getApplication(), inObject);
					if (!subscriptions.isEmpty()) {
						final SubscriptionData bilanSubscription = SubscriptionData.getData(subscriptions.get(0));
						final Object theSetting = bilanSubscription.getSettings().get(BilanHandler.NBR);
						final String newValue = theSetting == null ? "0" : Integer.toString(Integer.parseInt(theSetting.toString()) + 1);
						bilanSubscription.setSetting(BilanHandler.NBR, newValue);
					}
				}
			}
		}
		final long after = System.currentTimeMillis();
		if (after - before > 1000) {
			EventMng.LOGGER.info("TIME LEAK EventMng.played_2 " + (after - before) + net.violet.common.StringShop.SPACE + inObject.getObject_serial());
		}
	}

	/**
	 * Sends an email to the sender and/or the recipient as acknowledgement that
	 * the message sent has been played
	 * 
	 * @param inMessage
	 * @param inSender
	 * @param inRecipient
	 */
	private static void notifyPlayedByEmail(Message inMessage, User inSender, VObject inRecipient) {
		final String messageTitle = inMessage.getText();

		if ((inSender != null) && inSender.getNotifyMessagePlayed()) {
			Templates.messagePlayed(inRecipient, inSender, messageTitle, (int) inMessage.getCount());
		}
		if ((inRecipient != null) && (inMessage.getCount() < 2) && inRecipient.getOwner().getNotifyMessageReceived()) {
			Templates.messageReceived(inRecipient, inSender, messageTitle);
		}
	}
}
