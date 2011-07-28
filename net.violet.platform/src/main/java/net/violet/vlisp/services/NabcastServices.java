package net.violet.vlisp.services;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.AnimImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageImpl;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastCateg;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.NabcastVal;
import net.violet.platform.datamodel.NabcastValImpl;
import net.violet.platform.datamodel.Subscriber;
import net.violet.platform.datamodel.SubscriberImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.NabcastValData;
import net.violet.platform.dataobjects.SubscriberData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

public class NabcastServices {

	private static final Logger LOGGER = Logger.getLogger(NabcastServices.class);

	/**
	* Retourne true si propritaire nabcast param userid : id user param
	* nabcastid : id du nabcast
	* 
	* @return true si ok
	*/
	public static boolean isNabcastAuthor(long userid, long nabcastid) {
		final Nabcast nabcast = NabcastImpl.find(nabcastid);
		return (nabcast != null) && (nabcast.getAuthor().getId().longValue() == userid);
	}

	public static void nbrNabcastInOneWeek(int nabcast_id, String nameCateg) {

		final int timeOneWeekBefore = CCalendar.getCurrentTimeInSecond() - (24 * 3600 * 7);

		final NabcastCateg categ = Factories.NABCAST_CATEG.findByName(nameCateg);

		final List<Nabcast> listNabcastUpdate = NabcastImpl.getListNabcastByCateg(categ.getId(), 0, 0);

		for (final Nabcast theNabcast : listNabcastUpdate) {
			final int count = 0;
			final List<NabcastVal> listNabcastVal = NabcastValImpl.findLastByNabcastAndDate(theNabcast, timeOneWeekBefore);
			if (listNabcastVal.size() >= 3) { // seuil d'indexation
				theNabcast.setPostUpdate(count);
			} else {
				theNabcast.setPostUpdate(0);
			}
		}
		final Nabcast inNabcast = NabcastImpl.find(nabcast_id);
		inNabcast.setPrivate(0);
	}

	public static void sendNabcastToMySubscriber(int idNabcast, CCalendar timenext_diff, int idMp3, int user_id, final String title, net.violet.platform.dataobjects.NabcastData nd, int heure_delay, int minute_delay, final long nabcastValId) {
		final List<SubscriberData> listAbonNabcastSub = SubscriberData.findBySubscriberId(idNabcast);

		final User theSender = Factories.USER.find(user_id);
		final Files theFile = Factories.MUSIC.find(idMp3).getFile();

		String theColorSign = nd.getNabcast_color_sign();
		if ((theColorSign == null) || (theColorSign.length() < 3)) {
			theColorSign = "rand";
		}

		final String theTitle = NabcastImpl.find(idNabcast).getNabcast_title();
		final String theColorSignFinal = theColorSign;

		final Long theMusicSignId = nd.getNabcast_music_sign();
		final Music theMusicSignFinal = (theMusicSignId != null) ? Factories.MUSIC.find(theMusicSignId) : null;

		final Long theAnimSign = nd.getNabcast_anim_sign();
		final Anim theAnimFinal = (theAnimSign != null) ? Factories.ANIM.find(theAnimSign) : null;

		final CCalendar theNextTimeDiff = timenext_diff;
		final int hour = heure_delay;
		final int minute = minute_delay;
		new Thread(new Runnable() {

			public void run() {

				for (final SubscriberData sub : listAbonNabcastSub) {
					try {
						final VObject theObject = sub.getSubscriber_VObject();
						if (theObject != null) {
							final int heurenab = sub.getSubscriber_heure();
							final int minutenab = sub.getSubscriber_min();

							CCalendar theDeliveryDate;
							final String timezonename = theObject.getTimeZone().getTimezone_javaId();
							if (theNextTimeDiff != null) { // calcul le temps pour l'envoi différé
								if (heurenab > -1) { // il a choisi un horaire 
									theDeliveryDate = NabcastServices.giveMeFirstNextTimeDiff(heurenab, minutenab, timezonename, hour, minute, theNextTimeDiff);
								} else {
									theDeliveryDate = theNextTimeDiff;
								}
							} else { // calcul pour l'envoi immediat
								if (heurenab > -1) {
									theDeliveryDate = NabcastServices.giveMeFirstNextTime(heurenab, minutenab, timezonename);
								} else {
									theDeliveryDate = null; // envoi immédiat
								}
							}

							final CCalendar theDeliveryDateFinal = theDeliveryDate;

							MessageServices.sendMsgNabcast(theSender, theFile, theObject, title + " (nabcast)", theDeliveryDateFinal, theColorSignFinal, Palette.RANDOM, theAnimFinal, theMusicSignFinal, nabcastValId);

						} else {
							// Permet de lister les objets qui n'existent plus et qui sont
							// tjs abonné au nabcast
							NabcastServices.LOGGER.fatal("Cet objet n'existe plus (id =" + sub.getSubscriber_user() + "), il est pourtant abonné au nabcast " + theTitle + " de l'utilisateur : " + theSender.getUser_email());
						}
					} catch (final Exception e) {
						NabcastServices.LOGGER.fatal("Nabcast ID => " + sub.getSubscriber_nabcast() + " subscriber => " + sub.getSubscriber_user(), e);
					}
				}
			}
		}).start();

	}

	/**
	 * Cette méthode est utilisée pour calculer le premier nextTime d'un
	 * service.
	 * 
	 * @param heure L'heure voulue par la personne pour son service.
	 * @param minute Les minutes voulues par la personne pour son service.
	 * @param timezone_javaId La timeZone de l'utilisateur qui va demander le
	 *            service.
	 * @return un long qui est un time en milliseconde / 1000.
	 */
	private static CCalendar giveMeFirstNextTime(int heure, int minute, String timezone_javaId) {

		// on recupre la timezone de l'utilisateur
		final TimeZone tz = TimeZone.getTimeZone(timezone_javaId);
		// on rcupre le calendrier de l'utilisateur
		final CCalendar theResult = new CCalendar(tz);
		final int heureEnCours = theResult.get(Calendar.HOUR_OF_DAY);
		final int minEnCours = theResult.get(Calendar.MINUTE);
		// on sait quelle heure il est chez la personne
		// dans les deux cas suivant la personne veut son service sur une heure
		// qui est dj passe
		// on va donc l'implmenter pour le lendemain.
		if (heureEnCours > heure) {
			theResult.add(Calendar.DAY_OF_YEAR, 1);
		} else if ((heureEnCours == heure) && (minEnCours > minute)) {
			theResult.add(Calendar.DAY_OF_YEAR, 1);
		}

		theResult.set(Calendar.HOUR_OF_DAY, heure);
		theResult.set(Calendar.MINUTE, minute);

		return theResult;
	}

	/**
	 * Retourne abonne nabcast param objid : id objet param nbcastid : id du
	 * nabcast
	 * 
	 * @return true si ok
	 */
	public static boolean isAbonneUser(VObject obj, Nabcast nab) {
		boolean isAbonneUser = false;
		if ((obj != null) && (nab != null)) {
			if (obj.getSubscribedNabcast().containsKey(nab)) {
				isAbonneUser = true;
			}
		}
		return isAbonneUser;
	}

	/**
	 * Retourne abonne nabcast param objid : objet id param nbcastid : id du
	 * nabcast
	 * 
	 * @return le log de la requete 0 = erreur, 1 = OK, 2 = deja abonnee
	 */
	public static void abonneUser(VObject inObject, int nbcastid, int heure, int min) {
		final Nabcast nab = NabcastImpl.find(nbcastid);
		if (!NabcastServices.isAbonneUser(inObject, nab) && (inObject != null) && (nab != null)) {
			final SubscriberImpl sub = new SubscriberImpl(inObject, nab, heure, min);
			inObject.getSubscribedNabcast().put(nab, sub);
		}
	}

	/**
	 * delete NabcastImpl param nabcastid : id du nabcast param userid : id user
	 * 
	 * @return le log de la requete 0 = erreur, 1 = OK
	 */
	public static void deleteNabcast(int nabcastid, int userid) {
		final Nabcast nabcast = NabcastImpl.find(nabcastid);
		if ((nabcast != null) && (nabcast.getAuthor().getId() == userid)) {
			final List<Subscriber> list = nabcast.getSubscribers();
			for (final Subscriber sub : list) {
				final VObject obj = sub.getVObjectSubscriber();
				obj.deabonne2Nabcast(nabcast);
			}
			nabcast.delete();
		}
	}

	/**
	 * desabonne all user param nabcastid : id du nabcast
	 * 
	 * @return le log de la requete 0 = erreur, sinon OK
	 */
	public static void desabonneAllUser(int nabcastid) {
		final Nabcast nabcast = NabcastImpl.find(nabcastid);
		if (nabcast != null) {
			final List<Subscriber> subs = nabcast.getSubscribers();
			for (final Subscriber sub : subs) {
				final VObject obj = sub.getVObjectSubscriber();
				if (obj != null) {
					obj.deabonne2Nabcast(nabcast);
				}
			}
		}
	}

	public static void deleteMsgNabCastInFuture(long nabcastValId) {
		final List<Message> listMessagePost = MessageImpl.findMessagesByNabcastAndState(nabcastValId, MessageReceived.MESSAGE_RECEIVED_STATES.PENDING);

		for (final Message theMessage : listMessagePost) {
			final MessageReceived theMessRsc = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theMessage.getId());
			theMessRsc.delete();
			theMessage.delete();
		}
	}

	public static String[] getMinuteHourNabcast(VObject obj, int nabcastid) {
		final Nabcast nabcast = NabcastImpl.find(nabcastid);
		if ((obj != null) && (nabcast != null)) {
			final Map<Nabcast, Subscriber> subs = obj.getSubscribedNabcast();
			if (subs.containsKey(nabcast)) {
				return new String[] { Integer.toString(subs.get(nabcast).getSubscriber_min()), Integer.toString(subs.get(nabcast).getSubscriber_heure()) };
			}
		}
		return new String[] { "0", "0" };
	}

	/**
	 * Récupération de la date differe
	 * 
	 * @param date sous format "MMDDYYYY"
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static CCalendar nexttimediffFromCalendar(int month, int day, int year, String heure_delay, String minute_delay, User theUser) {
		int hour = 0;
		int myminute = 0;

		try {
			hour = new Integer(heure_delay).intValue();
			myminute = new Integer(minute_delay).intValue();
		} catch (final Exception e) {

		}

		final String timezone = theUser.getTimezone().getTimezone_javaId();
		final TimeZone tz = TimeZone.getTimeZone(timezone);

		final CCalendar theResult = new CCalendar(tz);
		theResult.set(year, month, day, hour, myminute);
		return theResult;
	}

	public static void deleteAllMsgNabCastSubscribe(List<NabcastValData> listNabcastPostInFuture, VObject inObject, User inUserSender) {

		final Messenger theRecipient = Factories.MESSENGER.getByObject(inObject);
		final Messenger theSender = Factories.MESSENGER.getByUser(inUserSender);

		try {
			for (final NabcastValData theNabcastValData : listNabcastPostInFuture) {
				theRecipient.deleteMessageNabcastInFutureFromMessenger(theSender, theNabcastValData.getNabcastval_id());
			}
		} catch (final SQLException e) {
			NabcastServices.LOGGER.fatal(e, e);
		}
	}

	public static void addMsgNabCastSubscribe(List<NabcastValData> list, User inUserSubcribe, VObject inObjectSubcribe, int heures, int minutes, int idusernabcast, String inAnim, String inMusicSign, String inMusicColorSign) {
		final String timezonename = inUserSubcribe.getTimezone().getTimezone_javaId();
		String music_color_sign = inMusicColorSign;
		if ((music_color_sign == null) || (music_color_sign.length() < 3)) {
			music_color_sign = "rand";
		}

		final Music theMusicSign;
		if (inMusicSign != null) {
			theMusicSign = Factories.MUSIC.find(Long.parseLong(inMusicSign));
		} else {
			theMusicSign = null;
		}
		final Anim theAnim;
		if (inAnim != null) {
			theAnim = AnimImpl.find(Long.parseLong(inAnim));
		} else {
			theAnim = null;
		}

		final User theNabcastAuthor = Factories.USER.find(idusernabcast);
		for (final NabcastValData nvd : list) {
			final String nabcastName = nvd.getNabcastval_titre();
			final Long nabcastValId = new Long(nvd.getNabcastval_id());
			CCalendar theDeliveryDate = nvd.getNabcastDeliveryDate();
			final Files theFile = nvd.getNabcastFile();

			if (heures > -1) { // il a choisi un horaire
				theDeliveryDate = NabcastServices.giveMeFirstNextTimeDiff(heures, minutes, timezonename, 0, 0, theDeliveryDate);
			}

			try {
				MessageServices.sendMsgNabcast(theNabcastAuthor, theFile, inObjectSubcribe, nabcastName + " (nabcast)", theDeliveryDate, music_color_sign, Palette.RANDOM, theAnim, theMusicSign, nabcastValId);
			} catch (final IllegalArgumentException e) {
				NabcastServices.LOGGER.fatal(e, e);
			}
		}
	}

	/**
	 * Cette mthode est utiliser pour calculer le temps prochain selon une heure
	 * et une minute choisi et un temps butoire.
	 * 
	 * @param heure L'heure voulue par la personne pour son service.
	 * @param minute Les minutes voulues par la personne pour son service.
	 * @param timezone_javaId La timeZone de l'utilisateur qui va demander le
	 *            service.
	 * @return un long qui est un time en milliseconde / 1000.
	 */
	private static CCalendar giveMeFirstNextTimeDiff(int heure, int minute, String timezone_javaId, int heureNow, int minuteNow, CCalendar timenext_diff) {
		// on recupre la timezone de l'utilisateur
		final TimeZone tz = TimeZone.getTimeZone(timezone_javaId);
		// on rcupre le calendrier de l'utilisateur
		final CCalendar theResult = new CCalendar(tz);
		final int heureEnCours = heureNow;// c.get(heureNow);
		final int minEnCours = minuteNow;// c.get(minuteNow); on sait quelle heure il est chez la personne dans les deux cas suivant la personne veut son service sur une heure qui est dj passe on va donc l'implmenter pour le lendemain.
		if (heureEnCours > heure) {
			theResult.add(Calendar.DAY_OF_YEAR, 1);
		} else if ((heureEnCours == heure) && (minEnCours > minute)) {
			theResult.add(Calendar.DAY_OF_YEAR, 1);
		}

		theResult.set(Calendar.HOUR_OF_DAY, heure);
		theResult.set(Calendar.MINUTE, minute);
		while (theResult.before(timenext_diff)) {
			theResult.add(Calendar.DAY_OF_YEAR, 1);
		}

		return theResult;
	}

}
