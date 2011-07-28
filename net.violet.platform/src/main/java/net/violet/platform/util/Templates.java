package net.violet.platform.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.DicoData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

/**
 * This class provides static methods which can be used to send email according
 * to the situation The message is defined in files (format txt and html), the
 * subject of the email is the title of the html file (the string between
 * <title> and </title>) the stylesheet of the email has to be in a file named
 * styles_mail.css, which has to be located in MAILPATH directory (directory
 * also containing a directory per language)
 */
public class Templates {

	private static final String MAIL_INFO = "info@violet.net";
	private static final String STYLE_FILE = "styles_mail.css";
	private static final Logger LOGGER = Logger.getLogger(Templates.class);

	/**
	 * Sends an email after creation of an account with rabbit
	 * 
	 * @param userId user id
	 * @param serial serial of the rabbit
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean createAccount(User inUser) {

		if (inUser == null) {
			return false;
		}

		final Map<String, String> replaces = new HashMap<String, String>();

		replaces.put("EMAIL", inUser.getUser_email());
		replaces.put("PASSWORD", inUser.getPassword());
		replaces.put("FIRST_NAME", inUser.getAnnu().getAnnu_prenom());
		replaces.put("LAST_NAME", inUser.getAnnu().getAnnu_nom());

		return Templates.send(inUser, replaces, "create_account");
	}

	/**
	 * Return identification information
	 * 
	 * @param userId user id
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean returnIdentifying(UserData inUser) {
		if ((inUser == null) || (inUser.getReference() == null)) {
			return false;
		}

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("EMAIL", inUser.getUser_email());
		replaces.put("PASSWORD", inUser.getPassword());

		return Templates.send(inUser.getReference(), replaces, "password_recovery");
	}

	/**
	 * Sends an email to a friend to invit him/her
	 * 
	 * @param userId user id
	 * @param friendEmail email address of the friend receiving the message
	 * @return true if the mail is sent, false otherwise
	 */
	public static boolean tellMyFriends(User inUser, String friendEmail) {
		if (inUser == null) {
			return false;
		}

		final List<VObject> userObjects = Factories.VOBJECT.findByOwner(inUser);
		final String[] theObjects = new String[userObjects.size()];
		for (int i = 0; i < userObjects.size(); i++) {
			final VObject theObject = userObjects.get(i);
			theObjects[i] = theObject.getHardware().getLabel() + StringShop.SPACE + "(" + theObject.getProfile().getLabel() + ")";
		}

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("FIRST_NAME", inUser.getAnnu().getAnnu_prenom());
		replaces.put("LAST_NAME", inUser.getAnnu().getAnnu_nom());
		replaces.put("EMAIL", inUser.getUser_email());
		replaces.put("USER_OBJECTS", StringShop.unsplit(theObjects, StringShop.COMMA + StringShop.SPACE, true));

		return Templates.send(inUser.getAnnu().getLangPreferences(), replaces, "tell_friends", inUser.getUser_email(), friendEmail);
	}

	/**
	 * MessageImpl not sent, nabdest error
	 * 
	 * @param userId user id
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean notSentBadNabDest(User inUser) {
		if (inUser == null) {
			return false;
		}

		return Templates.send(inUser, new HashMap<String, String>(), "message_not_sent_bad_recipient");
	}

	/**
	 * Not sent, no account
	 * 
	 * @param sender address of the sender who doesn't have account, we'll send
	 *            the mail to this address
	 * @param langUser language of the user
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean notSentNoAccount(String sender, Lang langUser) {

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("LINK_CREATE", "http://www.violet.net/createAccount");

		return Templates.send(langUser, replaces, "message_not_sent_no_account", Templates.MAIL_INFO, sender);
	}

	/**
	 * Not sent, no MP3 format
	 * 
	 * @param userId user id
	 * @param nameDest name of the previous mail destinator
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean notSentNoMP3Format(User inUser, List<VObject> inRecipients) {
		if (inUser == null) {
			return false;
		}

		final String[] theRecipients = new String[inRecipients.size()];
		for (int i = 0; i < inRecipients.size(); i++) {
			theRecipients[i] = inRecipients.get(i).getProfile().getLabel();
		}

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("OBJ_RECIPIENT", StringShop.unsplit(theRecipients, StringShop.COMMA + StringShop.SPACE, true));

		return Templates.send(inUser, replaces, "message_not_sent_no_mp3");
	}

	/**
	 * The template of a message send by a ztamp with application MailPost
	 * (net.violet.js.mailpost)
	 * 
	 * @param inApp
	 * @param inFrom
	 * @param inSender
	 * @param inReader
	 * @param inTo
	 * @param inCc
	 * @param inSubject
	 * @param inText
	 * @return
	 */
	public static boolean sendAppMailPost(Application inApp, User inFrom, VObject inSender, VObject inReader, Collection<InternetAddress> inTo, Collection<InternetAddress> inCc, String inSubject, String inText) {

		final Annu from = inFrom.getAnnu();
		final Lang templateLang = from.getLangPreferences();

		final Map<String, String> replacementMap = new HashMap<String, String>();
		replacementMap.put("FIRST_NAME", from.getAnnu_prenom());
		replacementMap.put("LAST_NAME", from.getAnnu_nom());

		replacementMap.put("OBJ_SENDER", inSender.getProfile().getLabel());
		replacementMap.put("OBJ_READER", inReader.getProfile().getLabel());

		replacementMap.put("SUBJECT", inSubject);
		replacementMap.put("MESSAGE", inText);

		String appName = inApp.getProfile().getTitle();
		try {
			appName = DicoData.findByDicoKeyAndLang(appName, templateLang);
		} catch (final NoSuchMessageException e) {}
		replacementMap.put("APPLICATION", appName);
		replacementMap.put("CREATOR", inApp.getOwner().getAnnu().getAnnu_prenom());

		return Templates.send(templateLang, replacementMap, "message_from_ztamp", inFrom.getUser_email(), inTo, inCc, inSubject);

	}

	/**
	 * MessageImpl correctly sent
	 * 
	 * @param to destination address
	 * @param langUser the used language
	 * @param nameDest name of the destinator
	 * @param message sent message
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean messageSent(String to, Lang langUser, VObject inRecipient, String message) {

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("SEND_AGAIN", "http://my.nabaztag.com/vl/action/myMessages.do?pseudo=" + inRecipient.getObject_login());
		replaces.put("SEND_OTHER", "http://my.nabaztag.com/vl/action/myMessages.do");

		replaces.put("OBJ_RECIPIENT", inRecipient.getProfile().getLabel());
		replaces.put("MESSAGE", message);

		final Annu theAnnu = inRecipient.getOwner().getAnnu();
		replaces.put("FIRST_NAME", theAnnu.getAnnu_prenom());
		replaces.put("LAST_NAME", theAnnu.getAnnu_nom());

		return Templates.send(langUser, replaces, "message_sent", Templates.MAIL_INFO, to);
	}

	/**
	 * MessageImpl played
	 * 
	 * @param to destination address
	 * @param langUser the used language
	 * @param nameDest name of the destinator
	 * @param nabname nabname
	 * @param message played message
	 * @param nbPlay how many times the message has been played
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean messagePlayed(VObject inRecipientObject, User inSender, String message, int nbPlay) {

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("SEND_AGAIN", "http://my.nabaztag.com/vl/action/myMessages.do?pseudo=" + inRecipientObject.getObject_login());
		replaces.put("SEND_OTHER", "http://my.nabaztag.com/vl/action/myMessages.do");

		replaces.put("OBJ_RECIPIENT", inRecipientObject.getProfile().getLabel());
		replaces.put("MESSAGE", message);
		replaces.put("NB_TIMES", Integer.toString(nbPlay));

		final Annu theAnnu = inRecipientObject.getOwner().getAnnu();
		replaces.put("FIRST_NAME", theAnnu.getAnnu_prenom());
		replaces.put("LAST_NAME", theAnnu.getAnnu_nom());

		return Templates.send(inSender.getAnnu().getLangPreferences(), replaces, "message_played", Templates.MAIL_INFO, inSender.getUser_email());

	}

	/**
	 * MessageImpl received
	 * 
	 * @param to destination address
	 * @param langUser the used language
	 * @param nameDest name of the destinator
	 * @param senderName name of the sender
	 * @param message sent message
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean messageReceived(VObject inRecipientObject, User inSenderUser, String message) {
		final User theRecipient = inRecipientObject.getOwner();
		final Lang langUser = theRecipient.getAnnu().getLangPreferences();
		final String nameDest = inRecipientObject.getProfile().getLabel();

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("SEND_AGAIN", "http://my.nabaztag.com/vl/action/myMessages.do");

		replaces.put("OBJ_RECIPIENT", nameDest);
		replaces.put("FIRST_NAME", inSenderUser.getAnnu().getAnnu_prenom());
		replaces.put("LAST_NAME", inSenderUser.getAnnu().getAnnu_nom());

		replaces.put("MESSAGE", message);

		if (inSenderUser.getUser_image() == 1) {
			replaces.put("PICTURE", Constantes.IMG_PATH + "/photo/" + inSenderUser.getId() + "_S.jpg");
		} else {
			replaces.put("PICTURE", Constantes.IMG_PATH + "/include_img/template/default_S.jpg");
		}

		return Templates.send(langUser, replaces, "message_received", Templates.MAIL_INFO, theRecipient.getUser_email());
	}

	/**
	 * NabcastImpl not updated
	 * 
	 * @param userId user id
	 * @param nabcast name of the nabcast
	 * @return true if the email is sent, false otherwise
	 */
	public static boolean nabcastNotUpdated(User inUser, String nabcast) {
		if (inUser == null) {
			return false;
		}

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("NABCAST", nabcast);

		replaces.put("LINK_UPDATE", "http://my.nabaztag.com/vl/action/myTerrier.do");

		return Templates.send(inUser, replaces, "nabcast_not_updated");
	}

	/**
	 * @param ask_id id of the user asking for friendness
	 * @param ans_id id of the user recieving the friendness request (and so the
	 *            mail)
	 */
	public static boolean validateAddFriend(User ask_user, User ans_user) {
		return Templates.notifyFriend(ask_user, ans_user, "validate_add_friend");
	}

	public static boolean notifyAddFriend(User ask_user, User ans_user) {
		return Templates.notifyFriend(ask_user, ans_user, "notify_add_friend");
	}

	private static boolean notifyFriend(User ask_user, User ans_user, String inTemplateName) {
		if ((ask_user == null) || (ans_user == null)) {
			return false;
		}

		final Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("FIRST_NAME", ask_user.getAnnu().getAnnu_prenom());
		replaces.put("LAST_NAME", ask_user.getAnnu().getAnnu_nom());

		if (ask_user.getUser_image() == 1) {
			replaces.put("PICTURE", Constantes.IMG_PATH + "/photo/" + ask_user.getId() + "_S.jpg");
		} else {
			replaces.put("PICTURE", Constantes.IMG_PATH + "/include_img/template/default_S.jpg");
		}

		// TODO LINK_PROFILE
		return Templates.send(ans_user, replaces, inTemplateName);

	}

	private static boolean send(Lang inLang, Map<String, String> replaces, String filename, String from, Collection<InternetAddress> inTo, Collection<InternetAddress> inCc, String inSubject) {

		replaces.put("PATH_IMG", Constantes.IMG_PATH);
		replaces.put("LINK_CONTACT", Templates.getLinkContact());
		replaces.put("STYLESHEET", FilesManagerFactory.FILE_MANAGER.getMailTemplate(Templates.STYLE_FILE));

		final String langPath = Templates.getLangRsc(inLang);

		try {
			String text_txt = FilesManagerFactory.FILE_MANAGER.getMailTemplate(langPath + StringShop.SLASH + filename + ".txt");
			String text_html = FilesManagerFactory.FILE_MANAGER.getMailTemplate(langPath + StringShop.SLASH + filename + ".html");

			String subject = (inSubject == null) ? Templates.getSubject(text_html) : inSubject;

			for (final String key : replaces.keySet()) {
				String replacementValue = replaces.get(key);
				replacementValue = (replacementValue == null) ? StringShop.EMPTY_STRING : Matcher.quoteReplacement(replacementValue);

				if (text_txt != null) {
					text_txt = text_txt.replaceAll("\r", StringShop.EMPTY_STRING);
					text_txt = text_txt.replaceAll(key, replacementValue);
				}
				if (text_html != null) {
					text_html = text_html.replaceAll(key, StringTools.cr2br(replacementValue));
					subject = subject.replaceAll(key, replacementValue);
				}
			}

			MailTools.send(from, inTo, inCc, null, subject, text_txt, text_html);
			return true;

		} catch (final MessagingException me) {
			Templates.LOGGER.error(me, me);
		}

		return false;
	}

	/**
	 * Sends an email to the specidied address from the specified address, uses
	 * replaces to replace keywords in the text
	 * 
	 * @param user indicates the user, used to get the language to use
	 * @param replaces contains keywords and their true value
	 * @param filename name of the file containing the text to send
	 * @param from source address
	 * @param to destination address
	 * @return true if the message is well sent, false otherwise
	 */
	private static boolean send(Lang inLang, Map<String, String> replaces, String filename, String from, String to) {

		try {
			return Templates.send(inLang, replaces, filename, from, MailTools.buildAddressCollection(Arrays.asList(to)), null, null);
		} catch (final AddressException e) {
			Templates.LOGGER.fatal(e, e);
		}

		return false;
	}

	/**
	 * Sends an email to default address (mail of the user) from default
	 * address, uses replaces to replace keywords in the text
	 * 
	 * @param user indicates the user, used to get the language to use and the
	 *            destination address
	 * @param replaces contains keywords and their true value
	 * @param filename name of the file containing the text to send
	 * @return true if the message is well sent, false otherwise
	 */
	private static boolean send(User inUser, Map<String, String> replaces, String filename) {
		return Templates.send(inUser.getAnnu().getLangPreferences(), replaces, filename, Templates.MAIL_INFO, inUser.getUser_email());
	}

	private static final Pattern SUBJECT_PATTERN = Pattern.compile(".*<title>(.+)</title>.*");

	/**
	 * Returns the subject to use for the email, we use the sequence between
	 * <title>...</title> tags
	 * 
	 * @param htmlContent the html content used to extract the subject..
	 * @return the subject (can be empty but not null)
	 */
	private static String getSubject(final String htmlContent) {
		final Matcher theMatcher;
		if ((htmlContent != null) && (theMatcher = Templates.SUBJECT_PATTERN.matcher(htmlContent)).find()) {
			return Templates.conversion(theMatcher.group(1));
		}

		return StringShop.EMPTY_STRING;
	}

	private static String getLinkContact() {
		return "http://www.violet.net/help";
	}

	/**
	 * Used to convert special characters from html format to regular format
	 * 
	 * @param s the string to convert
	 * @return the string after having converted special characters
	 */
	private static String conversion(String inS) {
		String s = inS;
		s = s.replaceAll("&agrave;", "à");
		s = s.replaceAll("&acirc;", "â");
		s = s.replaceAll("&auml;", "ä");
		s = s.replaceAll("&ccedil;", "ç");
		s = s.replaceAll("&egrave;", "è");
		s = s.replaceAll("&eacute;", "é");
		s = s.replaceAll("&ecirc;", "ê");
		s = s.replaceAll("&euml;", "ë");
		s = s.replaceAll("&icirc;", "î");
		s = s.replaceAll("&iuml;", "ï");
		s = s.replaceAll("&ocirc;", "ô");
		s = s.replaceAll("&ouml;", "ö");
		s = s.replaceAll("&ugrave;", "ù");
		s = s.replaceAll("&ucirc;", "û");
		s = s.replaceAll("&uuml;", "ü");

		return s;
	}

	/**
	 * Returns the path to the directory according to the language
	 * 
	 * @param inLang réference sur la langue.
	 * @return a string representing the directory
	 */
	private static String getLangRsc(Lang inLang) {
		String langPath = ObjectLangData.DEFAULT_OBJECT_LANGUAGE.getLang_iso_code(); // default

		if (inLang != null) { // vérifie les langues proposés par le site
			langPath = inLang.getIsoCode();
		}

		// ne garder que les 2 premières lettres !
		return langPath.substring(0, 2);
	}

	/**
	 * Envoie un mail à OTRS@violet.net si un client ne peut se connecté au
	 * serveur via XMPP Utilisée par les JSP !!
	 * 
	 * @param request
	 * @param macAddress
	 * @param nomDomaine
	 */
	public static void sendMailXMPP(HttpServletRequest request, String macAddress, String nomDomaine, String raison) {

		final String nabname;
		final String email_utilisateur;
		final StringBuilder text = new StringBuilder("Problème de connexion XMPP pour l'adresse mac : " + macAddress + ".\n");
		final VObject theObject = Factories.VOBJECT.findBySerial(macAddress);
		if (null != theObject) {
			nabname = theObject.getObject_login();
			email_utilisateur = theObject.getOwner().getUser_email();
		} else {
			nabname = "inconnu";
			email_utilisateur = "inconnu";
		}

		text.append("Raison : ").append(raison).append(".\n");
		text.append("Nabname :").append(nabname).append(".\n");
		text.append("Email de l'utilisateur : ").append(email_utilisateur).append(".\n");
		text.append("Nom du domaine : ").append(nomDomaine).append(".\n");
		text.append("Nom du serveur : ").append(Constantes.SRV_PATH).append(".\n");
		text.append("\nDétails de la requête :\n");
		text.append("Adresse IP du client : ").append(request.getRemoteAddr()).append(".\n");
		text.append("Protocole utilisé par le client : ").append(request.getProtocol()).append(".\n");
		text.append("Méthode utilisée par le client : ").append(request.getMethod()).append(".\n");
		text.append("Nom du serveur accédé par le client : ").append(request.getServerName()).append(StringShop.POINT);

		try {
			MailTools.send(Constantes.SUPPORT_MAILFROM, Collections.singletonList(new InternetAddress("OTRS@violet.net")), null, null, "JABBER", text.toString(), null);
		} catch (final AddressException ae) {
			Templates.LOGGER.fatal(ae, ae);
		} catch (final MessagingException me) {
			Templates.LOGGER.fatal(me, me);
		}
	}
}
