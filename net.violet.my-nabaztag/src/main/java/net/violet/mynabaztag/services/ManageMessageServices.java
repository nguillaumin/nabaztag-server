package net.violet.mynabaztag.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import net.violet.platform.api.exceptions.BlackListedException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.ParentalFilterException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.MyConstantes;
import net.violet.platform.util.StringShop;
import net.violet.platform.util.Templates;

import org.apache.log4j.Logger;

public class ManageMessageServices {

	private static final Logger LOGGER = Logger.getLogger(ManageMessageServices.class);

	/**
	 * Constructeur Utilise la zone par default
	 */
	public ManageMessageServices() {
		// This space for rent.
	}

	/**
	 * Random number generator used for the color or a message
	 */
	private static final Random rgen = new Random();

	private static final String[] colors = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public static String rand() {
		String color = StringShop.EMPTY_STRING;

		for (int i = 0; i < 6; i++) {
			color += ManageMessageServices.colors[ManageMessageServices.rgen.nextInt(14)];
		}

		return color;
	}

	/**
	 * Retourne les url du service méteo du user
	 * 
	 * @param timezone : timezone de l'objet
	 * @param inTypeDeg : degrès ou farenheit ( 1 : degrès ; 2: farenheit)
	 * @param srv_src : source de la ville (Nmeteo.FRANCE.Paris.weather)
	 * @param inLang : langue demander sur le service
	 * @return tableau url sinon null (error source)
	 */
	// TODO: passer en private qd migration terminée
	public enum WEATHER_OPTIONS {
		TODAY, TOMORROW, SKY, TEMP, DEGREE, FARENHEIT, METEO
	};

	/**
	 * check si on passe par la liste d'amis ou direct par le nabname pour
	 * envoyer le message
	 * 
	 * @param objectFriendDest :
	 * @param objNameDest :
	 * @return VObject sinon null
	 */
	public static VObject checkDest(String objectFriendDest, String objNameDest) {
		VObject theResult = null;

		if (objNameDest.trim().equals(StringShop.EMPTY_STRING)) { // on passe par la liste d'ami
			theResult = Factories.VOBJECT.findByName(objectFriendDest);

		} else { // on prend le destinataire renseigné
			theResult = Factories.VOBJECT.findByName(objNameDest);
		}

		return theResult;
	}

	/**
	 * check si le destinataire a pas mis de filtre parental ou blacklisté la
	 * personne
	 * 
	 * @param userSender
	 * @param userDest
	 * @return message
	 * @throws ParentalFilterException
	 * @throws BlackListedException
	 */
	public static boolean checkSendMessage(User userSender, User userDest) throws ParentalFilterException, BlackListedException {

		final FriendsLists friendsLists = Factories.FRIENDS_LISTS.findByUser(userDest);

		// check filtre parental du destinataire
		if ((friendsLists != null) && (friendsLists.getFriendslists_filter() == 1) && !userSender.existFriend(userDest) && (userSender.getId() != userDest.getId())) {
			throw new ParentalFilterException();
		}

		// check le blackList du destinataire
		if (userDest.existInBlackliste(userSender) > 0) {
			throw new BlackListedException();
		}

		return true;
	}

	/**
	 * Envoi message a plusieurs destinataires immédiatement ou en différé et
	 * envoi de notification si le user a autorisé + ajout dans les stats
	 * 
	 * @param userSender
	 * @param inObjectListDest
	 * @param inFile : le file a envoyer
	 * @param inDeliveryDate : null immédiatement sinon différé
	 * @param libelle : libellé du message
	 * @param inPalette : palette voulu
	 * @return Message ou null
	 * @throws InternalErrorException
	 */
	public static void sendUserMessageAndNotification(UserData userSender, List<VObjectData> inObjectListDest, FilesData inFile, Date inDeliveryDate, Palette inPalette, String libelle) throws InternalErrorException {

		CCalendar theCalDeliveryDate = null;
		if (inDeliveryDate != null) { // message envoi différé
			final String timezoneName = userSender.getTimezone().getTimezone_javaId();

			theCalDeliveryDate = new CCalendar(TimeZone.getTimeZone(timezoneName));
			theCalDeliveryDate.setTime(inDeliveryDate);
		}

		for (final VObjectData inObject : inObjectListDest) {
			ManageMessageServices.sendUserMessageAndNotification(userSender.getReference(), inObject.getReference(), inFile.getReference(), theCalDeliveryDate, inPalette, libelle);
		}
	}

	public static void sendUserMessageAndNotification(UserData userSender, VObjectData inObjectDest, FilesData inFile, Date inDeliveryDate, Palette inPalette, String libelle) throws InternalErrorException {

		CCalendar theCalDeliveryDate = null;
		if (inDeliveryDate != null) { // message envoi différé
			final String timezoneName = userSender.getTimezone().getTimezone_javaId();

			theCalDeliveryDate = new CCalendar(TimeZone.getTimeZone(timezoneName));
			theCalDeliveryDate.setTime(inDeliveryDate);
		}

		ManageMessageServices.sendUserMessageAndNotification(userSender.getReference(), inObjectDest.getReference(), inFile.getReference(), theCalDeliveryDate, inPalette, libelle);
	}

	/**
	 * Envoi le message immédiatement ou en différé et envoi de notification si
	 * le user a autorisé + ajout dans les stats
	 * 
	 * @param userSender
	 * @param inObjectDest
	 * @param inFile : le file a envoyer
	 * @param inDeliveryDate : null immédiatement sinon différé
	 * @param libelle : libellé du message
	 * @param color : palette voulu ; -1 random
	 * @throws InternalErrorException
	 */
	public static void sendUserMessageAndNotification(User userSender, VObject inObjectDest, Files inFile, CCalendar inDeliveryDate, Palette inPalette, String libelle) throws InternalErrorException {
		try {
			MessageServices.sendUserMessage(userSender, inFile, inObjectDest, libelle, inDeliveryDate, inPalette, false);
		} catch (final Exception e) {
			ManageMessageServices.LOGGER.fatal(e, e);
			throw new InternalErrorException(e.getMessage());
		}

		// le message est bien parti on peut le notifier et le mettre dans les
		// stats

		final Music theMusic = Factories.MUSIC.findByFile(inFile);

		if (theMusic != null) {
			Factories.STATS.insert(userSender, inObjectDest, MyConstantes.STAT_WEB);
		}

		// le user veux une notification
		if (userSender.getUser_authmsg() == 1) {
			Templates.messageSent(userSender.getUser_email(), userSender.getLang(), inObjectDest, libelle);
		}
	}

	public static void putInbox(Files inMessageBodyContent, Map<String, Object> inMessageMap, Palette inPalette, User inUser, VObject inObject, String inDelivery) throws InternalErrorException {
		try {
			final Message theMessage = Factories.MESSAGE.create(inMessageBodyContent, (String) inMessageMap.get("title"), null, inPalette);
			final Messenger theSender = Factories.MESSENGER.getByUser(inUser);
			final Messenger theReceiver = Factories.MESSENGER.getByObject(inObject);
			theReceiver.getMessageReceived().put(theMessage, Factories.MESSAGE_RECEIVED.create(theMessage, theReceiver, theSender));
			theSender.getMessageSent().put(theMessage, Factories.MESSAGE_SENT.create(theMessage, theReceiver, theSender));

			if (inUser.getUser_authmsg() == 1) {
				Templates.messageSent(inUser.getUser_email(), inUser.getLang(), inObject, inDelivery);
			}

			// Notification de reception de message dans la inbox du ztamp
			if (inObject.getOwner().getNotifyMessageReceived()) {
				Templates.messageReceived(inObject, inUser, (String) inMessageMap.get("title"));
			}
		} catch (final IllegalArgumentException e) {
			ManageMessageServices.LOGGER.fatal(e, e);
			throw new InternalErrorException(e);
		}
	}
}
