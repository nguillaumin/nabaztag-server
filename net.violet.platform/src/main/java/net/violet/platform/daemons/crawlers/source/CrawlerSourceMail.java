package net.violet.platform.daemons.crawlers.source;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import net.violet.common.StringShop;
import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.applications.GmailTwitterHandler;
import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.Application.NativeApplication;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.schedulers.KeywordHandler;
import net.violet.platform.schedulers.NewContentWithKeywordAndMediaHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.CipherTools;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.MailTools;
import net.violet.platform.util.Pair;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.log4j.Logger;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

/**
 * This class provides a multi-thread way to check email accounts of the
 * database and to signal new emails to the user
 */
public class CrawlerSourceMail extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerSourceMail.class);

	private static final int MAX_EMAILS_AMOUNT = 50;

	public static final Palette MAIL_COLOR_PAL = Palette.LIGHT;

	private static final String INBOX = "INBOX";
	private static final int THREADNBR = 50; // number of threads executing in the same time
	private final BlockingExecutor<SubscriptionData> mBlockingExecutor;

	/**
	 * Creates a NewMailCrawler defined to use nbrThreadsTmp threads
	 * 
	 * @param nbrThreadsTmp number of threads
	 */
	public CrawlerSourceMail(String[] inArgs) {
		super(inArgs);

		this.mBlockingExecutor = new BlockingExecutor<SubscriptionData>(CrawlerSourceMail.THREADNBR, new Worker<SubscriptionData>() {

			public void process(SubscriptionData inSubscription) {
				doProcessSubscription(inSubscription);
			}
		}, "MailUser");

	}

	@Override
	protected void quit() {
		this.mBlockingExecutor.quit();
		super.quit();
	}

	@Override
	protected void process() {
		for (final Subscription aSubscription : Factories.SUBSCRIPTION.findAllByApplication(NativeApplication.MAIL.getApplication())) {
			try {
				CrawlerSourceMail.this.mBlockingExecutor.put(SubscriptionData.getData(aSubscription));
			} catch (final InterruptedException ie) {
				CrawlerSourceMail.LOGGER.fatal(ie, ie);
			}
		}

		try {
			this.mBlockingExecutor.waitTermination();
		} catch (final InterruptedException e) {
			// This space for rent.
		}
	}

	private void doProcessSubscription(SubscriptionData inSubscription) {
		final PojoMap theSettings = inSubscription.getSettings();

		if (theSettings.containsKey(MailAlertHandler.SOURCE)) {
			final List<Source> theSources = Factories.SOURCE.findByRootPath(theSettings.get(MailAlertHandler.SOURCE).toString());

			if (!theSources.isEmpty()) {

				final String host = theSettings.get(MailAlertHandler.HOST).toString();
				final String user = CipherTools.uncipher(theSettings.get(MailAlertHandler.LOGIN).toString());
				final String passwd = CipherTools.uncipher(theSettings.get(MailAlertHandler.PASSWORD).toString());
				final String typeMail = theSettings.get(MailAlertHandler.TYPE).toString();

				Store store = null;
				Folder folder = null;

				try {
					store = MailTools.getStore(typeMail, host, user, passwd);
					if (store == null) {
						throw new MessagingException("store == null");
					}

					folder = store.getDefaultFolder();

					if (folder != null) {
						folder = folder.getFolder(CrawlerSourceMail.INBOX);
					}

					if (folder == null) {
						throw new MessagingException("folder == null");
					}

					if (!folder.isOpen()) {
						folder.open(Folder.READ_ONLY);
					}
					CrawlerSourceMail.LOGGER.error("Connecting : " + host + " - " + user);
					processFolderAndSources(folder, inSubscription, theSources, theSettings);

				} catch (final MessagingException mex) {
					CrawlerSourceMail.LOGGER.error("Unable to connect : " + host + " - " + user);
				} finally {
					if ((folder != null) && folder.isOpen()) {
						try {
							folder.close(false);
						} catch (final MessagingException e) {
							CrawlerSourceMail.LOGGER.error(e, e);
						}
					}
					if (store != null) {
						try {
							store.close();
						} catch (final MessagingException e) {
							CrawlerSourceMail.LOGGER.error(e, e);
						}
					}
				}
			}
		}
	}

	private static final Pattern KEYWORD_PATTERN = Pattern.compile("^\\$(?:[^\\.]+\\.|[^$]+){2}(.*)$");

	private void processFolderAndSources(Folder inFolder, SubscriptionData inSubscription, List<Source> inSources, PojoMap inSettings) throws MessagingException {

		final int mostRecentMessageIndex = inFolder.getMessageCount();
		final int eldestMessageIndex = Math.max(mostRecentMessageIndex - CrawlerSourceMail.MAX_EMAILS_AMOUNT, 1); // we will only check 50 messages, max

		// all the keywords/medias to use for active alert
		final Map<String, String> theMediaKeywords = new HashMap<String, String>();
		for (final SubscriptionSchedulingData aScheduling : SubscriptionSchedulingData.findAllBySubscriptionAndType(inSubscription, SCHEDULING_TYPE.NewContentWithKeywordAndMedia)) {
			final SubscriptionSchedulingSettingsData keywordSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(aScheduling, KeywordHandler.KEYWORD);
			final SubscriptionSchedulingSettingsData mediaSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(aScheduling, NewContentWithKeywordAndMediaHandler.MEDIA);
			theMediaKeywords.put(keywordSetting.getValue(), mediaSetting.getValue());
		}
		// this list is empty if there isn't any keyword (default alert)

		// the sources, at least one default and possibly some others for keywords
		final List<Pair<Source, String>> thePassiveKeywords = new LinkedList<Pair<Source, String>>();
		for (final Source aSource : inSources) {
			final Matcher theMatcher = CrawlerSourceMail.KEYWORD_PATTERN.matcher(aSource.getSource_path());
			if (theMatcher.matches()) {
				final String theKeyWord = theMatcher.group(1);
				thePassiveKeywords.add(new Pair<Source, String>(aSource, (theKeyWord == null) ? StringShop.EMPTY_STRING : theKeyWord));
			} else {
				CrawlerSourceMail.LOGGER.error("Source path does not match the keyword_pattern : " + aSource.getSource_id() + " - " + aSource.getSource_path());
			}
		}

		final Set<String> newContentList = new HashSet<String>(); // all the new messages in the box since the last time
		final Set<String> allContentList = new HashSet<String>(); // all unread messages in the box

		// here we retrieve the index of the first unread message (or messagesAmount + 1 if we didn't find any) : all previous messages are also new
		final int theFirstNewMessageIndex = getFirstNewMessageIndex(inFolder, inSettings);

		for (int indexMessage = mostRecentMessageIndex; indexMessage >= eldestMessageIndex; indexMessage--) {
			final Message theMessage = inFolder.getMessage(indexMessage);
			if (!theMessage.isSet(Flags.Flag.SEEN) && !theMessage.isSet(Flags.Flag.DELETED)) {
				final String theContent = getMessageFilterMaterial(theMessage);
				allContentList.add(theContent);

				if (indexMessage >= theFirstNewMessageIndex) {
					newContentList.add(theContent);
				}
			}
		}

		// Treatment of the passive alerts : based on the unread messages
		for (final Pair<Source, String> aKeyword : thePassiveKeywords) {
			int amount = 0;

			if (!aKeyword.getSecond().equals(StringShop.EMPTY_STRING)) { // here there is a real keyword to match
				final Pattern thePattern = Pattern.compile(".*" + Pattern.quote(aKeyword.getSecond()) + ".*");
				for (final String aContent : allContentList) {
					if (thePattern.matcher(aContent).matches()) {
						amount++;
					}
				}
			} else { // no keyword, that's the default source
				amount = allContentList.size();
			}
			aKeyword.getFirst().setVal(amount);
		}

		//Treatment of the media alerts : based on the new messages
		final Set<MusicData> mediaToSend = new HashSet<MusicData>();
		if (!newContentList.isEmpty()) { // if there is at least one new message we can proceed
			if (theMediaKeywords.isEmpty()) { // there is not any keywords : default alert
				mediaToSend.add(MusicData.getDefaultMailAlert(inSubscription.getObject().getPreferences().getLang().getReference()));
			} else {
				for (final Entry<String, String> aKeywordWithMedia : theMediaKeywords.entrySet()) {
					final Pattern thePattern = Pattern.compile(".*" + Pattern.quote(aKeywordWithMedia.getKey()) + ".*");
					for (final String aContent : newContentList) {
						if (thePattern.matcher(aContent).matches()) {
							mediaToSend.add(MusicData.getData(Factories.MUSIC.find(Long.parseLong(aKeywordWithMedia.getValue()))));
						}
					}
				}
			}
		}

		if (!mediaToSend.isEmpty()) {
			final Files[] filesToSend = new Files[mediaToSend.size()];
			int i = 0;
			for (final MusicData aMusic : mediaToSend) {
				filesToSend[i++] = aMusic.getFile().getReference();
			}

			MessageServices.sendServiceMessage(filesToSend, inSubscription.getObject().getReference(), "Service Mail", null, Constantes.QUEUE_TTL_SERVICE, CrawlerSourceMail.MAIL_COLOR_PAL, MessageSignature.MAIL_SIGNATURE, false, JabberMessageFactory.IDLE_MODE);
		}

		// if we did find a new message we update the settings
		if (theFirstNewMessageIndex <= mostRecentMessageIndex) {
			final Message theNewReferenceMessage = inFolder.getMessage(mostRecentMessageIndex);
			final String theUID;
			if (inFolder instanceof IMAPFolder) {
				theUID = Long.toString(((IMAPFolder) inFolder).getUID(theNewReferenceMessage));
			} else {
				theUID = (((POP3Folder) inFolder).getUID(theNewReferenceMessage)).toString();
			}

			inSubscription.setSetting(MailAlertHandler.ID_LAST_NEWS, theUID);

			//final String theLastTimeMessage = theNewReferenceMessage.getSentDate() == null ? new CCalendar(false).getTimestampUTC() : new CCalendar(theNewReferenceMessage.getSentDate().getTime()).getTimestampUTC();
		//	inSubscription.setSetting(GmailTwitterHandler.LAST_MODIFIED, theLastTimeMessage);
		}

		inSubscription.setSetting(GmailTwitterHandler.LAST_MODIFIED, new CCalendar(false).getTimestampUTC());

	}

	/**
	 * Returns the index of the first unread message, or messagesAmount+1 if there is not any.
	 * 
	 * @param inFolder
	 * @param inMailUserData
	 * @return
	 * @throws MessagingException
	 * @throws InvalidParameterException 
	 */
	private int getFirstNewMessageIndex(Folder inFolder, PojoMap inSettings) throws MessagingException {
		int theResult;

		final String theStoredUIDStr = (inSettings.get(MailAlertHandler.ID_LAST_NEWS) == null) ? StringShop.ZERO : inSettings.get(MailAlertHandler.ID_LAST_NEWS).toString();

		final int mostRecentMessageIndex = inFolder.getMessageCount();
		final int eldestMessageIndex = Math.max(mostRecentMessageIndex - CrawlerSourceMail.MAX_EMAILS_AMOUNT, 1); // we will only check 50 messages, max

		if (inFolder instanceof IMAPFolder) {
			// in IMAP uid are increasing
			final long theStoredUIDLong = Long.parseLong(theStoredUIDStr);
			theResult = 1; // Tous les messages sont nouveaux.
			for (int indexMessage = mostRecentMessageIndex; indexMessage >= eldestMessageIndex; indexMessage--) {
				final Message theMessage = inFolder.getMessage(indexMessage);
				if (theMessage != null) {
					final long theMessageUID = ((IMAPFolder) inFolder).getUID(theMessage);
					if (theStoredUIDLong >= theMessageUID) {
						theResult = indexMessage + 1; // the previous message is the newest unread one.
						break;
					}
				}
			}
		} else {
			// POP protocol is not as easier :  we use both the id if we can, otherwise the delivery date
			theResult = 0; // default value indicating that the search failed
			for (int indexMessage = mostRecentMessageIndex; indexMessage >= eldestMessageIndex; indexMessage--) {
				final Message theMessage = inFolder.getMessage(indexMessage);
				if (theMessage != null) {
					final String theMessageUID = ((POP3Folder) inFolder).getUID(theMessage);
					if (theStoredUIDStr.equals(theMessageUID)) { // the stored id has been found, all previously encountered messages are new.
						theResult = indexMessage + 1;
						break;
					}
				}
			}

			if (theResult == 0) { // we failed finding the stored id
				Date theStoredDate;
				try {
					//theStoredDate = inSettings.getDate(GmailTwitterHandler.LAST_MODIFIED, new CCalendar(false).getTime());
					final CCalendar defaultTime = new CCalendar(false); // the default last_modified : 1 day ago
          	                        defaultTime.add(Calendar.DATE, -1);
                                        theStoredDate = inSettings.getDate(GmailTwitterHandler.LAST_MODIFIED, defaultTime.getTime());
				} catch (final InvalidParameterException e) {
					CrawlerSourceMail.LOGGER.fatal("Invalid format for date param", e);
					return theResult;
				}

				theResult = mostRecentMessageIndex + 1; // default : no new messages
				for (int indexMessage = mostRecentMessageIndex; indexMessage >= eldestMessageIndex; indexMessage--) {
					final Message theMessage = inFolder.getMessage(indexMessage);
					if (theMessage != null) {
						final Date theMessageDate = theMessage.getSentDate();
						if ((theMessageDate != null) && theMessageDate.before(theStoredDate)) { // this message has been sent earlier, all previous messages are new
							theResult = indexMessage + 1;
							break;
						}
					}
				}
			}
		}

		return theResult;
	}

	/**
	 * m contains the filter
	 * 
	 * @param m the message to test
	 * @param filter the filter
	 * @return true if the message matches the filter, false otherwise
	 */
	private String getMessageFilterMaterial(Message inMessage) {
		final StringBuilder theBuilder = new StringBuilder();
		try {
			final Address[] theFromAddr = inMessage.getFrom();
			if ((theFromAddr != null) && (theFromAddr.length >= 1) && (theFromAddr[0] instanceof InternetAddress)) {
				theBuilder.append(((InternetAddress) theFromAddr[0]).getAddress());
			}
			if (inMessage.getSubject() != null) {
				theBuilder.append(inMessage.getSubject());
			}
		} catch (final MessagingException mex) {
			CrawlerSourceMail.LOGGER.warn(mex, mex);
		}
		return theBuilder.toString();
	}
}
