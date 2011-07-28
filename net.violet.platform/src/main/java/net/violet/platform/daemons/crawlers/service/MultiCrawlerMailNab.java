package net.violet.platform.daemons.crawlers.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import net.violet.platform.api.actions.messages.AbstractSendMessageAction;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidDataException;
import net.violet.platform.api.exceptions.TTSGenerationFailedException;
import net.violet.platform.applets.AppResourcesLoader;
import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.UserEmailData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.Templates;
import net.violet.vlisp.services.ManageMessageServices;

import org.apache.log4j.Logger;

/**
 * This class provides a way to send the same mail to a list of destinations It uses the same mechanisms as CrawlerMailNab class but is designed to
 * treat a list of destionations and not only one
 */
public class MultiCrawlerMailNab extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(MultiCrawlerMailNab.class);

	// Connection parameters
	private static final String HOST = "mail2.violet.net";
	private static final String USER = "all" + Constantes.POPMAIL;
	private static final String PASSWORD = "stf3@(po";
	private static final int PORT = 110;
	private static final String INBOX_FOLDER = "INBOX";

	private static final int MAX_MESSAGES_AMOUNT = 200;

	public MultiCrawlerMailNab(String[] inArgs) {
		super(inArgs);
	}

	/**
	 * Returns the opened folder, or null if it couldn't be opened
	 * 
	 * @return
	 */
	private static Folder getOpenedFolder() {

		final Properties props = System.getProperties();
		props.put("mail.pop3.timeout", "20000");
		props.put("mail.pop3.connectiontimeout", "20000");

		final Session msession = Session.getInstance(props, null);
		msession.setDebug(false);

		Store store = null;
		try {
			store = msession.getStore("pop3");
			store.connect(MultiCrawlerMailNab.HOST, MultiCrawlerMailNab.PORT, MultiCrawlerMailNab.USER, MultiCrawlerMailNab.PASSWORD);
		} catch (final MessagingException me) {
			MultiCrawlerMailNab.LOGGER.warn("getStoreMail/connect :" + me);
		}

		if ((store == null) || !store.isConnected()) {
			return null;
		}

		Folder folder = null;

		try {
			folder = store.getDefaultFolder();
			if (folder == null) {
				MultiCrawlerMailNab.LOGGER.info("No default folder");
				return null;
			}

			folder = folder.getFolder(MultiCrawlerMailNab.INBOX_FOLDER);
			if (folder == null) {
				MultiCrawlerMailNab.LOGGER.info("Invalid folder");
				return null;
			}

			// try to open read/write and if that fails try read-only
			folder.open(Folder.READ_WRITE);
		} catch (final MessagingException ex) {
			MultiCrawlerMailNab.LOGGER.warn("openFolder/openRW :" + ex);
			try {
				if (folder != null) {
					folder.open(Folder.READ_ONLY);
				}
			} catch (final MessagingException ex1) {
				MultiCrawlerMailNab.LOGGER.warn("openFolder/openRO :" + ex1);
			}
		}

		return folder;
	}

	/**
	 * Returns a valid email address.
	 * 
	 * @param emailAddress
	 * @return
	 */
	private final static Pattern GET_EMAIL = Pattern.compile("^.*<([^>]+)>.*$");

	private static String filterEmail(String emailAddress) {
		final Matcher theMatcher = MultiCrawlerMailNab.GET_EMAIL.matcher(Matcher.quoteReplacement(emailAddress));
		return (theMatcher.matches()) ? theMatcher.group(1).trim() : emailAddress;
	}

	/**
	 * Returns the object name based on the given address.
	 * 
	 * @param address
	 * @return
	 */
	private static String getObjectLoginFromAddress(String address) {

		final int i = address.indexOf(Constantes.POPMAIL);
		if (i > 0) {
			return address.substring(0, i);
		}

		return null;
	}

	private static MimeBodyPart lookForAttachment(Part part) {

		try {
			final Object theUnknownPart = part.getContent();
			if (theUnknownPart instanceof Multipart) {
				final Multipart theMultiPart = (Multipart) theUnknownPart;

				for (int partIndex = 0; partIndex < theMultiPart.getCount(); partIndex++) {
					final MimeBodyPart aPart = (MimeBodyPart) theMultiPart.getBodyPart(partIndex);
					if (aPart != null) {
						final String contentType = aPart.getContentType();
						//On a tout plein de type exotique (audio/x-mp3, audio/mp3,etc...)
						if ((contentType != null) && contentType.startsWith("audio/")) {
							MultiCrawlerMailNab.LOGGER.info("MP3_MAIL content type : " + contentType);
							return aPart;
						}
					}
				}
			}
		} catch (final Exception e) {}

		return null;
	}

	/**
	 * Builds a CCalendar object based on the given date as string.
	 * 
	 * @param inTimeStr
	 * @param inLang
	 * @param timeZone
	 * @return
	 */
	private static CCalendar retrieveDate(String inTimeStr, int inLang, String timeZone) {
		final TimeZone tz = TimeZone.getTimeZone(timeZone);

		int flagtimeerror = 0;
		int cutcar = 5; // pour le format date=
		/* Syntaxe anglaise pour la date */
		int lang = inLang;
		if (inTimeStr.indexOf("us") > 0) {
			lang = 2;
			cutcar = 7; // pour le format date=US
		}
		final String chainetime = inTimeStr.substring(cutcar);
		final String date = chainetime.substring(0, chainetime.indexOf(";"));
		final String time = chainetime.substring(chainetime.indexOf(";") + 1);

		final int minute = ConvertTools.atoi(time.substring(time.indexOf(":") + 1));
		final int hour = ConvertTools.atoi(time.substring(0, time.indexOf(":")));
		int day;
		int month;

		if (lang == 2) {
			month = ConvertTools.atoi(date.substring(0, date.indexOf("/")));
			day = ConvertTools.atoi(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));
		} else {
			day = ConvertTools.atoi(date.substring(0, date.indexOf("/")));
			month = ConvertTools.atoi(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));
		}
		int year = ConvertTools.atoi(date.substring(date.lastIndexOf("/") + 1));
		if (year > 2000) {
			year -= 2000;
		}
		if (year > 100) {
			flagtimeerror = 1;
		}
		if ((minute < 0) || (minute > 59)) {
			flagtimeerror = 1;
		}
		if ((hour < 0) || (hour > 23)) {
			flagtimeerror = 1;
		}
		if ((day < 1) || (day > 31)) {
			flagtimeerror = 1;
		}
		if ((month < 1) || (month > 12)) {
			flagtimeerror = 1;
		}

		CCalendar theResult;
		if (flagtimeerror == 0) {
			theResult = new CCalendar(0, tz);
			theResult.set(year + 2000, month - 1, day, hour, minute);
		} else {
			theResult = null;
		}

		if (theResult == null) {
			// L'utilisateur n'a pas fourni une date lisible.
			// On prend maintenant + une semaine.
			theResult = CCalendar.getTimeInFuture(Constantes.ONE_WEEK_IN_MS);
		}
		return theResult;
	}

	/**
	 * Sends the message to the recipients list
	 * 
	 * @param inUserSend
	 * @param inFile
	 * @param inRecipients
	 * @param inMusicName
	 * @param inDate
	 */
	private static void sendAllMessages(UserData inUserSend, FilesData inFile, List<VObjectData> inRecipients, String inMusicName, CCalendar inDate) {
		final APICaller caller = AppResourcesLoader.LOADER;
		final String theMusicName = (inMusicName == null) ? net.violet.common.StringShop.EMPTY_STRING : inMusicName;

		final Files file = inFile.getReference();

		for (final VObjectData inObjectDest : inRecipients) {

			if (inObjectDest != null) {
				try {
					if (inObjectDest.getObjectType().instanceOf(ObjectType.RFID)) {

						// Create POJO
						final Map<String, Object> msgMap = new HashMap<String, Object>();

						msgMap.put("from", inUserSend.getApiId(caller));
						msgMap.put("to", inObjectDest.getApiId(caller));
						msgMap.put("title", theMusicName);

						msgMap.put("sequence", Collections.singletonList(AbstractSendMessageAction.getPOJOSequenceMap(file)));
						msgMap.put("alt", AbstractSendMessageAction.getUnsupportedContent(inObjectDest.getPreferences().getLang().getReference()));

						// store the message in its JSON form to be replayed later
						final Files msgBodyContent = AbstractSendMessageAction.postPojoMessage(msgMap);

						if (msgBodyContent == null) {
							throw new InternalErrorException("File creation failed");
						}

						ManageMessageServices.putInbox(msgBodyContent, msgMap, Palette.RANDOM, inUserSend.getReference(), inObjectDest.getReference(), theMusicName);

					} else {
						ManageMessageServices.sendUserMessageAndNotification(inUserSend.getReference(), inObjectDest.getReference(), file, inDate, Palette.RANDOM, theMusicName, true);
					}
				} catch (final Exception e) {}

			} else {
				Templates.notSentBadNabDest(inUserSend.getReference());
			}
		}

	}

	private static final String[] BLACKED_DOMAINS = { "mailer-daemon", "postmaster@", "info@", "@nabaztag.com" };

	/**
	 * Checks if the given address is valid (not linked to a blacked domain).
	 * 
	 * @param theSenderAddress
	 * @return
	 */
	private boolean isValidSenderAddress(String theSenderAddress) {
		final String theEmailAddress = theSenderAddress.toLowerCase();
		for (final String blackedDomain : MultiCrawlerMailNab.BLACKED_DOMAINS) {
			if (theEmailAddress.indexOf(blackedDomain) > -1) {
				return false;
			}
		}
		return true;
	}

	private static void processEmail(List<VObjectData> recipients, String subject, MimeBodyPart attachmentPart, String sendingAddress) throws IOException, MessagingException, TTSGenerationFailedException, InternalErrorException, InvalidDataException {

		UserData sender = UserData.findByEmail(sendingAddress);
		if ((sender == null) || !sender.isValid()) {
			final UserEmailData alternateEmail = UserEmailData.findByAddress(sendingAddress);
			if ((alternateEmail != null) && alternateEmail.isValid()) {
				sender = alternateEmail.getUser();
			} else {
				Templates.notSentNoAccount(sendingAddress, Factories.LANG.find(Lang.LANG_US_ID));
				return;
			}
		}

		final String timezone = sender.getTimezone().getTimezone_javaId();

		if (attachmentPart != null) {

			String fileName;
			try {
				fileName = (subject == null) ? attachmentPart.getFileName() : subject;
			} catch (final MessagingException e) {
				fileName = subject;
			}

			final int index;
			CCalendar theDate = null;
			if ((index = fileName.indexOf("date=")) > 0) {
				fileName = fileName.substring(0, index);
				theDate = MultiCrawlerMailNab.retrieveDate(fileName.substring(index), 1, timezone);
			}

			final FilesData theFile = FilesData.postLibraryItem(MimeType.MIME_TYPES.A_MPEG.getLabel(), attachmentPart.getInputStream());
			if ((theFile != null) && theFile.isValid()) {
				MultiCrawlerMailNab.sendAllMessages(sender, theFile, recipients, fileName, theDate);
			}

		} else {
			String messagetts = (subject == null) ? net.violet.common.StringShop.EMPTY_STRING : subject.trim();

			final int index;
			CCalendar theDate = null;
			if ((index = messagetts.indexOf("date=")) > 0) {
				messagetts = messagetts.substring(0, index);
				theDate = MultiCrawlerMailNab.retrieveDate(messagetts.substring(index), 1, timezone);
			}

			String musicName = messagetts;
			if (messagetts.length() > 200) {
				musicName = messagetts.substring(0, 200) + "...";
			}
			final TtsVoiceData theVoice = TtsVoiceData.findTtsVoiceByLang(TtsLangData.getDefaultTtsLanguage(sender.getAnnu().getLang().getLang_iso_code()));
			final FilesData theFile = FilesData.postTTS(sender, messagetts, musicName, theVoice, false, true, true);

			MultiCrawlerMailNab.sendAllMessages(sender, theFile, recipients, musicName, theDate);
		}

	}

	@Override
	protected void process() {

		final Folder theFolder = MultiCrawlerMailNab.getOpenedFolder();
		if ((theFolder != null) && theFolder.isOpen()) {

			int totalMessages = 0;
			try {
				totalMessages = theFolder.getMessageCount();
			} catch (final MessagingException me) {
				MultiCrawlerMailNab.LOGGER.warn("totalMessage :" + me);
			}
			if (totalMessages > MultiCrawlerMailNab.MAX_MESSAGES_AMOUNT) {
				totalMessages = MultiCrawlerMailNab.MAX_MESSAGES_AMOUNT;
			}

			for (int i = 1; i <= totalMessages; i++) {
				Message theMessage = null;
				try {
					theMessage = theFolder.getMessage(i);

					final String theSenderAddress = MultiCrawlerMailNab.filterEmail(theMessage.getFrom()[0].toString());

					if (isValidSenderAddress(theSenderAddress)) {

						final List<VObjectData> destObjects = new ArrayList<VObjectData>();
						for (final Address address : theMessage.getAllRecipients()) {
							final String receiverAddress = MultiCrawlerMailNab.filterEmail(address.toString());
							final String to = MultiCrawlerMailNab.getObjectLoginFromAddress(receiverAddress);
							if (to != null) {
								destObjects.add(VObjectData.findByName(to));
							}
						}

						final MimeBodyPart theMimeBodyPart = MultiCrawlerMailNab.lookForAttachment(theMessage);
						MultiCrawlerMailNab.processEmail(destObjects, theMessage.getSubject(), theMimeBodyPart, theSenderAddress);

					}
				} catch (final Exception e) {
					MultiCrawlerMailNab.LOGGER.warn(e, e);
				} finally {
					if (theMessage != null) {
						try {
							theMessage.setFlag(javax.mail.Flags.Flag.DELETED, true);
						} catch (final MessagingException e) {
							MultiCrawlerMailNab.LOGGER.warn(e, e);
						}
					}
				}
			}
		}

		try {
			if (theFolder != null) {
				final Store theStore = theFolder.getStore();
				theFolder.close(true);
				theStore.close();
			}
		} catch (final MessagingException e) {
			MultiCrawlerMailNab.LOGGER.warn(e, e);
		}

	}

}
