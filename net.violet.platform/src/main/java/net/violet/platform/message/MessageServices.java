package net.violet.platform.message;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.EvSeqImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageLogImpl;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.ScheduledMessageImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.ping.EventMng;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberComponent;
import net.violet.platform.xmpp.JabberComponentManager;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.Packet;

/**
 * Classe pour la gestion des messages (vers les objets).
 */
public class MessageServices {

	private static final Logger LOGGER = Logger.getLogger(MessageServices.class);

	/**
	 * Inner Class containing 1 set of the body for a message
	 */
	public static class Body {

		private final Files file;
		private final boolean stream;
		private final Palette colorPal;

		public Body(Files inFile, Palette inColorPal) {
			this(inFile, inColorPal, true);
		}

		public Body(Files inFile, Palette inColorPal, boolean inStream) throws IllegalArgumentException {
			if (inFile == null) {
				throw new IllegalArgumentException("The file we are trying to send through a message is NULL");
			}
			this.file = inFile;
			this.colorPal = inColorPal;
			this.stream = inStream;
		}

		/**
		 * @return the colorPal
		 */
		public Palette getPalette() {
			return this.colorPal;
		}

		/**
		 * @return the file
		 */
		public Files getFile() {
			return this.file;
		}

		/**
		 * @return the stream
		 */
		public boolean isStream() {
			return this.stream;
		}
	}

	/**
	 * Méthode qui envoie un message d'un utilisateur à un lapin en prennant une
	 * liste d'urls
	 * 
	 * @param user_from : id du user envoyeur
	 * @param urls : tableau d'urls qui seront jouées par le lapin
	 * @param objId : object_Id de sa zone du destinataire (object_id)
	 * @param msgTitle : nom du message (uniquement pour la bd et éventuellement
	 *            le site)
	 * @param ttl : time to live du message avant purge en secondes
	 * @param color_sig : null si pas de signature sinon couleur hexa (rand pour
	 *            aléatoire)
	 * @param color_pal : palette couleurs pour la chorégraphie (-1 random)
	 * @param signature_Url : tableau d'urls ordonnée qui seront jouées par le
	 *            lapin en tant que signature idem autre méthode (1: url de la
	 *            music ; 2:usr de l'anim)
	 * @param stream : le lapin peut-il s'il sait le faire, streamer les flux
	 *            des urls. (meteo : false)
	 * @param chor : le lapin doit-il ou non faire une chorégraphie pendant la
	 *            lecture.
	 * @return 1 si l'envoie c'est correctement déroulé.
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public static void sendMsgNabcast(User inSender, Files theFile, VObject inRecipient, String msgTitle, CCalendar inDeliveryDate, String inColorSig, Palette inColorPal, Anim inSignatureAnim, Music inSignatureMusic, Long inNabcastValId) throws IllegalArgumentException {
		final MessageSignature theSignature = new MessageSignature(inColorSig, inSignatureAnim, inSignatureMusic);
		MessageServices.sendUserMessage(Factories.MESSENGER.getByUser(inSender), new Body[] { new Body(theFile, inColorPal, true) }, Factories.MESSENGER.getByObject(inRecipient), msgTitle, inDeliveryDate, theSignature, inNabcastValId, JabberMessageFactory.NOTIFY_MODE);
	}

	/**
	 * Méthode qui envoie un message d'un utilisateur à un lapin en prennant une
	 * liste d'urls Généralement utilisé pour les crawlers, reco, api.
	 * 
	 * @param files fichiers qui composent le message.
	 * @param inRecipient destinataire du message
	 * @param inText texte du message (pour le log)
	 * @param inDeliveryDate date d'envoi du message ou <code>null</code> pour
	 *            un envoi immédiat
	 * @param inColorPal couleur de la palette
	 * @param inSignature signature du message
	 * @param stream si on doit faire du straem
	 * @param inMode mode d'envoi.
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public static void sendServiceMessage(Files[] files, VObject inRecipient, String inText, CCalendar inDeliveryDate, int inQueueTTLInSec, Palette inColorPal, MessageSignature inSignature, boolean stream, int inMode) throws IllegalArgumentException {
		final Body[] bodies = new Body[files.length];

		for (int i = 0; i < files.length; i++) {
			final Files theFile = files[i];
			bodies[i] = new Body(theFile, inColorPal, stream);
		}
		final MessageDraft msgDraft = MessageServices.createMessageDraft(inRecipient, inText, bodies, inSignature);
		msgDraft.setTTLInSecond(inQueueTTLInSec);
		MessageServices.send(msgDraft, inDeliveryDate, inMode, bodies);
	}

	/**
	 * Fonction permettant d'envoyer plusieurs urls sur un lapin V2 Méthode
	 * utilisée pour l'API streaming.
	 * 
	 * @param urls chemin vers les fichiers à lire
	 * @param inRecipient le nom du destinataire
	 * @param ttl durée de vie du message (en millisecondes), ou <code>0</code>
	 *            pour une durée de vie infinie.
	 */
	public static void sendStreamingUrls(String[] urls, VObject inRecipient, int inQueueTTLInSecs) throws IllegalArgumentException {
		final MessageDraft theMessage = new MessageDraft(inRecipient);
		for (final String theUrl : urls) {
			theMessage.addAudio(theUrl, true, true);
		}
		if (inQueueTTLInSecs == 0) {
			theMessage.setTTLInSecond(Constantes.QUEUE_TTL_FIVE_MINUTES);
		} else {
			theMessage.setTTLInSecond(inQueueTTLInSecs);
		}
		MessageServices.send(theMessage, null, JabberMessageFactory.IDLE_MODE, null);
	}

	/**
	 * Méthode qui envoie un message d'un utilisateur à un lapin en prennant une
	 * url
	 * 
	 * @param inSender l'utilisateur émetteur du message
	 * @param inMessage nom du message (accept, cancel, ask, reject).
	 * @param inRecipient le destinataire (VObjectImpl)
	 * @param msgTitle the titre du message
	 * @throws SQLException
	 * @throws IllegalArgumentException si le VObjectImpl ne peut être trouvé ou
	 *             créé
	 */
	public static void sendDialogMessage(User inSender, String inMessage, VObject inRecipient, String msgTitle) throws IllegalArgumentException {
		final Map<String, List<ConfigFiles>> dialogConfigFiles = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.DIALOG, inRecipient.getPreferences().getLangPreferences());
		final Files body = dialogConfigFiles.get(inMessage).get(0).getFiles();
		MessageServices.sendUserMessage(inSender, body, inRecipient, msgTitle, null, Palette.RANDOM, false);
	}

	/**
	 * Sends a message from a UserImpl to a VObjectImpl.
	 * 
	 * @param inSender the UserImpl sending the message
	 * @param inBody the body of the message (mp3, chor, adp)
	 * @param inRecipient the VObjectImpl receiving the message
	 * @param text the text of the message
	 * @param inColorPal the color palette to be used when the message plays
	 * @param inDeliveryDate date d'envoi du message ou <code>null</code> pour
	 *            un envoi immédiat.
	 * @throws SQLException
	 * @throws IllegalArgumentException si le VObjectImpl ne peut être trouvé ou
	 *             créé
	 */
	public static void sendUserMessage(User inSender, Files inBody, VObject inRecipient, String text, CCalendar inDeliveryDate, Palette inColorPal, boolean isStream) throws IllegalArgumentException {
		MessageServices.sendUserMessage(Factories.MESSENGER.getByUser(inSender), new Body[] { new Body(inBody, inColorPal, isStream) }, Factories.MESSENGER.getByObject(inRecipient), text, inDeliveryDate, null, null, JabberMessageFactory.NOTIFY_MODE);
	}

	/**
	 * Sends a message from a MessengerImpl (sender: UserImpl) to a
	 * MessengerImpl (recipient : VObjectImpl)
	 * 
	 * @param inSender the UserImpl sending the message
	 * @param inBody the body of the message (mp3, chor, adp)
	 * @param inRecipient the VObjectImpl receiving the message
	 * @param inTimeOfDelivery date d'envoi du message ou <code>null</code> pour
	 *            envoyer le message tout de suite.
	 * @param inSignature The message signature if <code>null</code> the
	 *            sender's signature will be used
	 * @param inNabcastValId : id du post du nabcast ( nabcastval_id)
	 * @param inMode mode d'envoi du message
	 * @param inColorPal the color to be used when the message plays
	 * @param text the text of the message
	 * @throws SQLException
	 */
	private static void sendUserMessage(Messenger inSender, Body[] inBody, Messenger inRecipient, String inText, CCalendar inTimeOfDelivery, MessageSignature inSignature, Long inNabcastValId, int inMode) throws IllegalArgumentException {
		if ((inRecipient != null) && (inBody != null) && (inBody.length > 0) && (inSender != null)) {

			final Body body = inBody[0];

			MessageSignature theSignature = inSignature;
			if (theSignature == null) {
				theSignature = new MessageSignature(inSender.getUser());
			}

			final MessageDraft msgDraft = MessageServices.createMessageDraft(inRecipient.getObject(), inText, inBody, theSignature);

			final Message theMessage = Factories.MESSAGE.create(body.getFile(), inText, inTimeOfDelivery, body.getPalette());
			theMessage.setSignatureInfo((long) theSignature.getColor4Message(), theSignature.getMusic(), theSignature.getAnim());

			msgDraft.setMessageID(theMessage.getId());
			msgDraft.setSender(inSender.getUser());

			final MessageReceived theMessageRecieved = Factories.MESSAGE_RECEIVED.create(theMessage, inRecipient, inSender);

			if (inNabcastValId == null) {
				inSender.getMessageSent().put(theMessage, Factories.MESSAGE_SENT.create(theMessage, inRecipient, inSender));
			} else {
				theMessage.setTheNabcast(inNabcastValId);
			}

			if (inTimeOfDelivery != null) {
				theMessageRecieved.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.PENDING);
			}
			inRecipient.getMessageReceived().put(theMessage, theMessageRecieved);
			msgDraft.setTTLInSecond(Constantes.QUEUE_TTL_USER_MESSAGE);
			MessageServices.send(msgDraft, inTimeOfDelivery, inMode, inBody);
			final Long eventId = msgDraft.getEventID();
			if (eventId != null) {
				theMessage.setEventId(eventId.intValue());
			}
		}
	}

	/**
	 * Sends a message from a MessengerImpl (sender: UserImpl) to a
	 * MessengerImpl (recipient : VObjectImpl)
	 * 
	 * @param inMessage the MessageImpl to send
	 * @param inSender the UserImpl sending the message
	 * @param inRecipient the VObjectImpl receiving the message
	 */
	public static void resendUserMessage(net.violet.platform.datamodel.Message inMessage, Messenger inSender, Messenger inRecipient) {

		if ((inRecipient != null) && (inMessage != null)) {

			if (inMessage.getBody() == null) {
				MessageServices.LOGGER.error("Envoi d'un message avec body null, message_id = " + inMessage.getId());

			} else {

				try {
					final MessageReceived messageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(inMessage.getId());
					messageReceived.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.INBOX);

					final MessageDraft messageDraft = new MessageDraft(inRecipient.getObject());
					MessageServices.fillMessageDraftWithUserMessage(messageDraft, inMessage, inSender);

					// On renvoie immédiatement.
					messageDraft.setTTLInSecond(Constantes.QUEUE_TTL_ONE_DAY);
					MessageServices.send(messageDraft, null, JabberMessageFactory.NOTIFY_MODE, null);

					final Long eventId = messageDraft.getEventID();
					if (eventId != null) {
						inMessage.setEventId(eventId.intValue());
					}

				} catch (final Exception e) {
					MessageServices.LOGGER.error("Message " + inMessage.getId() + " couldn't be resent.", e);
				}
			}
		} else {
			MessageServices.LOGGER.debug("message null : " + (inMessage == null) + ", recipient null : " + (inRecipient == null));
		}
	}

	/**
	 * Sends a message from a MessengerImpl (sender: UserImpl) to a
	 * MessengerImpl (recipient : VObjectImpl)
	 * 
	 * @param inMessage the MessageImpl to send
	 * @param inSender the UserImpl sending the message
	 * @param inRecipient the VObjectImpl receiving the message
	 * @throws ConversionException
	 * @throws InvalidParameterException
	 */
	public static void fillMessageDraftWithUserMessage(MessageDraft inMessageDraft, Message inMessage, Messenger inSender) throws InvalidParameterException, ConversionException {

		final Files messageBody = inMessage.getBody();

		if (messageBody == null) {
			MessageServices.LOGGER.fatal("Envoi d'un message avec body null, message_id = " + inMessage.getId());
			return;
		}

		inMessageDraft.setMessageID(inMessage.getId());
		inMessageDraft.setSender(inSender.getUser());
		inMessageDraft.setTitle(inMessage.getText());

		/*
		 * Déterminer la signature du message
		 */
		// on prend la signature de l'utilisateur
		MessageSignature signature = new MessageSignature(inSender.getUser());
		final Music music = inMessage.getMusic();
		final Anim anim = inMessage.getAnim();

		if ((music != null) && (anim != null)) {
			// le message a une signature particulière
			Long colorIndex = inMessage.getColor();
			if (colorIndex == null) {
				// récupérer le couleur de la signature utilisateur
				colorIndex = (long) signature.getColor4Message();
			}
			signature = new MessageSignature(colorIndex.intValue(), anim, music);
		}

		inMessageDraft.addSignature(signature);

		/*
		 * Déterminer le corps du message (la liste des séquences à jouer)
		 */
		MessageServices.LOGGER.debug("Type du message : " + messageBody.getType());
		if (messageBody.getType() == MimeType.MIME_TYPES.JSON) {
			// message body is stored in JSON text format (POJO)
			final String jsonBody = FilesManagerFactory.FILE_MANAGER.getTextContent(messageBody);
			MessageServices.LOGGER.debug("Read POJO message from inbox : " + jsonBody);
			inMessageDraft.addPojo(jsonBody, MimeType.MIME_TYPES.JSON);

		} else {
			// message body is a single media file (MP3, CHOR, ..)
			inMessageDraft.fillMessageDraft(new Body(messageBody, inMessage.getPalette()));
		}

	}

	/**
	 * Factory method
	 * 
	 * @param inRecipient
	 * @param inTitle
	 * @param inBody
	 * @param inSignature
	 * @return
	 */
	public static MessageDraft createMessageDraft(VObject inRecipient, String inTitle, Body[] inBody, MessageSignature inSignature) {
		final MessageDraft msgDraft = new MessageDraft(inRecipient);
		msgDraft.fillMessageDraft(inTitle, inSignature, inBody);
		return msgDraft;
	}

	/**
	 * Méthode qui envoie un message audio à un objet, avec un TTL de 5 minutes.
	 * Utilisée pour des messages interactifs (du type erreur de reconnaissance
	 * vocale).
	 * 
	 * @param inRecipient objet auquel on envoie le message.
	 * @param inFile FilesImpl audio à envoyer.
	 */
	public static void sendAudioMessage(VObject inRecipient, Files inFile) {
		final MessageDraft theMessage = new MessageDraft(inRecipient);
		theMessage.addAudio(inFile.getPath(), false, true);
		theMessage.setTTLInSecond(Constantes.QUEUE_TTL_FIVE_MINUTES);
		MessageServices.send(theMessage, null, JabberMessageFactory.IDLE_MODE, null);
	}

	/**
	 * Envoi d'une commande spéciale sur le lapin afin qu'il passe en mode
	 * interactif
	 */
	public static void sendStartApplication(VObject inRecipient, String inName) {
		final MessageDraft theMessage = new MessageDraft(inRecipient);
		theMessage.addSetting("snd.itmode=none");
		theMessage.addSetting("chor.itmode=none");
		theMessage.addStartInteractive(inName);
		theMessage.setTTLInSecond(Constantes.QUEUE_TTL_FIVE_MINUTES);
		if (inRecipient.isXMPP()) {
			MessageServices.sendUsingXmpp(theMessage, JabberMessageFactory.IDLE_MODE);
		} else {
			MessageServices.send(theMessage, null, JabberMessageFactory.IDLE_MODE, null);
		}
	}

	/**
	 * Méthode qui envoie un message chorégraphique à un utilisateur. Utilisée
	 * par l'API.
	 * 
	 * @param inRecipient objet auquel on envoie le message.
	 * @param inFile ficher avec la chorégraphie.
	 * @param inTTLInSeconds durée de vie de l'événement, <code>0</code> pour 5
	 *            minutes.
	 */
	public static void sendChoregraphy(VObject inRecipient, Files inFile, String inTitle, int inTTLInSeconds) {
		final MessageDraft theMessage = new MessageDraft(inRecipient);
		theMessage.addChoreography(inFile.getPath2chor(), false);

		if (inTitle != null) {
			theMessage.setTitle(inTitle);
		}

		if (inTTLInSeconds == 0) {
			theMessage.setTTLInSecond(Constantes.QUEUE_TTL_FIVE_MINUTES);
		} else {
			theMessage.setTTLInSecond(inTTLInSeconds);
		}
		MessageServices.send(theMessage, null, JabberMessageFactory.IDLE_MODE, null);
	}

	/**
	 * Ajoute un message dans la base (lapins ping & non ping). Met à jour
	 * mTimeOfDelivery.
	 */
	private static void createEvent(net.violet.platform.message.Message inMessage, CCalendar timeOfDelivery, int inMode) {
		long obj = inMessage.getReceiver().getId();
		final int theTTLInSeconds = inMessage.getTTLInSecond();
		final long theTimeOfDeliveryInSecs;
		if (timeOfDelivery != null) {
			obj = -obj;
			theTimeOfDeliveryInSecs = timeOfDelivery.getTimeInMillis() / 1000;
		} else {
			theTimeOfDeliveryInSecs = CCalendar.getCurrentTimeInSecond();
		}
		final long theExpirationDate;
		if (theTTLInSeconds != 0) {
			theExpirationDate = theTTLInSeconds + theTimeOfDeliveryInSecs;
		} else {
			theExpirationDate = 0;
		}

		Long event = null;
		try {
			event = Factories.EVENT.insert((int) obj, (int) theTimeOfDeliveryInSecs, (int) theExpirationDate, inMode);
		} catch (final SQLException se) {
			MessageServices.LOGGER.fatal(se, se);
		}
		if (event != null) {
			inMessage.setEventID(event);
		}
	}

	/**
	 * Envoie le message (en mode ping ou xmpp) et le loggue.
	 * 
	 * @param inMessage le message à envoyer
	 * @param timeOfDelivery date à laquelle envoyer le message ou
	 *            <code>null</code> pour un envoi immédiat.
	 * @param inTTL durée de vie de message sur le serveur, en millisecondes, ou
	 *            <code>0</code> pour une durée de vie infinie.
	 * @param mode mode d'envoi du message (IDLE, ASLEEP, etc.).
	 * @param bodies les éléments du message, peut être <code>null</code>
	 *            (utilisé pour le log).
	 */
	public static void send(net.violet.platform.message.Message inMessage, CCalendar inTimeOfDelivery, int mode, Body[] bodies) {
		MessageServices.send(inMessage, inTimeOfDelivery, mode, bodies, null);
	}

	public static void send(net.violet.platform.message.Message inMessage, CCalendar inTimeOfDelivery, int mode, Body[] bodies, JabberComponent inComponent) {

		// Dans tous les cas, on écrit dans la base (pour que le message soit
		// dans la liste des messages de l'utilisateur).
		if (inMessage.getReceiver().isXMPP()) {
			if (inTimeOfDelivery == null) { // envoi immédiat. XMPP.
				if (inMessage.getMessageID() != null) { // ce n'est pas un message de service
					EventMng.refreshCountMessagesAfterSending(inMessage.getReceiver());
				}
				MessageServices.sendUsingXmpp(inMessage, mode, inComponent);
			} else {
				// Envoi dans la table du futur.
				MessageServices.sendScheduledXmpp(inMessage, mode, inTimeOfDelivery);
			}
		} else {
			// Ping (evseq).
			MessageServices.createEvent(inMessage, inTimeOfDelivery, mode);
			MessageServices.sendUsingDB(inMessage);
			EventMng.refreshCountMessagesAfterSending(inMessage.getReceiver());
		}

		// insertion dans le log
		Files file = null;
		int nbItems = 0;
		if (bodies != null) {
			file = bodies[0].getFile();
			nbItems = bodies.length;
		}

		// TODO reste à setter le service s'il y en a un
		if (file != null) {
			MessageLogImpl.insert(inMessage.getReceiver(), file, inMessage.getTitle(), inTimeOfDelivery, nbItems);
		}
	}

	public static void send(net.violet.platform.message.Message inMessage) {
		MessageServices.send(inMessage, null);
	}

	public static void send(net.violet.platform.message.Message inMessage, JabberComponent inComponent) {
		MessageServices.send(inMessage, inMessage.getDeliveryDate(), JabberMessageFactory.DEFAULT_MODE, null, inComponent);
	}

	/**
	 * Envoie le message.
	 */
	private static void sendUsingDB(net.violet.platform.message.Message inMessage) {
		// On n'envoie qu'en cas de succès de l'insertion dans event.
		if (inMessage.getEventID() != null) {
			// Insertion dans evseq.
			int seq_ix = 0;
			for (final Sequence theSeq : inMessage.getSequenceList()) {
				try {
					EvSeqImpl.insert(inMessage.getEventID(), seq_ix++, theSeq.getType(), theSeq.getData());
				} catch (final SQLException se) {
					MessageServices.LOGGER.fatal(se, se);
				}
			}
		}
	}

	/**
	 * Envoie le message en utilisant Jabber.
	 * 
	 * @param inMessage
	 * @param expirationDate
	 * @param mode
	 */
	public static void sendUsingXmpp(net.violet.platform.message.Message inMessage, int mode) {
		MessageServices.sendUsingXmpp(inMessage, mode, null);
	}

	public static void sendUsingXmpp(net.violet.platform.message.Message inMessage, int mode, JabberComponent inComponent) {
		// Envoi du message en utilisant la connexion jabber.
		final JabberComponent theClient = (inComponent == null) ? JabberComponentManager.getComponent(Constantes.XMPP_PLATFORM_COMPONENT) : inComponent;
		theClient.sendMessage(inMessage, mode);
	}

	private static void sendScheduledXmpp(net.violet.platform.message.Message inMessage, int mode, CCalendar timeOfDelivery) {
		final Packet thePacket = JabberComponent.getPacket(inMessage, timeOfDelivery, mode, JabberComponentManager.getComponentDefaultFromAddress(Constantes.XMPP_PLATFORM_COMPONENT));
		try {
			ScheduledMessageImpl.insert(new Timestamp(timeOfDelivery.getTimeInMillis()), thePacket.toXML(), inMessage.getMessageID());
		} catch (final SQLException e) {
			MessageServices.LOGGER.fatal(e, e);
		}
	}

}
