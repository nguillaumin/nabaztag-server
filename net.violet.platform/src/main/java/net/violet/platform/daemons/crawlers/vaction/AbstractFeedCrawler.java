package net.violet.platform.daemons.crawlers.vaction;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler;
import net.violet.platform.daemons.crawlers.feedHandler.ConnectionHandler.ConnectionSettings;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.StringShop;
import net.violet.platform.util.ThrottleManager;
import net.violet.platform.util.ThrottleManager.ThrottleProfile;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.ParsingFeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public abstract class AbstractFeedCrawler<T extends AbstractCrawlerProcessUnit> extends AbstractCrawler implements FeedCrawler<T> {

	private static final Logger LOGGER = Logger.getLogger(AbstractFeedCrawler.class);

	public static interface FeedUnit {

		/**
		 * Time where the feed was last modified may be null. (From the http request)
		 * @return
		 */
		Date getLastModified();

		String getETag();

		String getUsername();

		String getPassword();

		String getUrl();

		/**
		 * GUID (if available) of the last news
		 * 
		 * @return
		 */
		String getIdLastNews();

		Lang getLang();

		MessageSignature getMessageSignature();

	}

	private final ConnectionHandler mConnectionHandler;
	private final BlockingExecutor<FeedUnit> mFeedUnitProcessorBlockingExecutor;
	/**
	 * Référence sur le service.
	 */
	private final ThrottleManager<FeedUnit> mThrottle;
	private final boolean mIsFree;

	/**
	 * Constructeur à partir du service et des paramètres en ligne de commande.
	 * 
	 * @param inArgs
	 * @param isFree
	 */
	protected AbstractFeedCrawler(String[] inArgs, boolean isFree) {
		super(inArgs.clone());

		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("VAction", LongOpt.REQUIRED_ARGUMENT, null, 'a'), new LongOpt("Connections", LongOpt.REQUIRED_ARGUMENT, null, 'c'), new LongOpt("Processes", LongOpt.OPTIONAL_ARGUMENT, null, 'p'), new LongOpt("Time", LongOpt.OPTIONAL_ARGUMENT, null, 't'), };

		final Getopt theGetOpt = new Getopt(getClass().getSimpleName(), inArgs, "a:c:p:t:", theLongOpts);
		int nbVActionProcessorThreads = 0;
		int nbConnection = 0;
		int theOption = 0;
		int nbProcesses = 0;
		long periodOfTime = 1000L;

		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 'a':
				nbVActionProcessorThreads = Integer.parseInt(theGetOpt.getOptarg());
				break;
			case 'c':
				nbConnection = Integer.parseInt(theGetOpt.getOptarg());
				break;
			case 'p':
				nbProcesses = Integer.parseInt(theGetOpt.getOptarg());
				break;
			case 't':
				periodOfTime = Long.parseLong(theGetOpt.getOptarg());
				break;
			}
		}

		if (nbProcesses != 0) {
			this.mThrottle = ThrottleManager.getRessourcesThrottle(new ThrottleProfile(nbProcesses, periodOfTime));
		} else {
			this.mThrottle = null;
		}

		this.mFeedUnitProcessorBlockingExecutor = new BlockingExecutor<FeedUnit>(nbVActionProcessorThreads, new Worker<FeedUnit>() {

			public void process(FeedUnit inFeedUnit) {
				processFeedUnit(inFeedUnit);
			}
		}, getService().getLabel() + "-" + (isFree ? SERVICE_ACCESSRIGHT.FREE.toString() : SERVICE_ACCESSRIGHT.FULL.toString()));

		this.mConnectionHandler = new ConnectionHandler(nbConnection);
		this.mIsFree = isFree;
	}

	/**
	 * ONLY USE FOR TEST (MISUSE WILL BE PUNISHED)
	 */
	AbstractFeedCrawler() {
		super(new String[] {});
		this.mConnectionHandler = null;
		this.mFeedUnitProcessorBlockingExecutor = null;
		this.mIsFree = true;
		this.mThrottle = null;
	}

	@Override
	protected void process() {

		doProcess();

		try {
			this.mFeedUnitProcessorBlockingExecutor.waitTermination();
		} catch (final InterruptedException e) {
			AbstractFeedCrawler.LOGGER.fatal(e, e);
		}
	}

	protected abstract void doProcess();

	protected void addFeedUnit2Process(FeedUnit inFeedUnit) throws InterruptedException {
		this.mFeedUnitProcessorBlockingExecutor.put(inFeedUnit);
	}

	protected boolean isFree() {
		return this.mIsFree;
	}

	/**
	 * Checks if a feed is processable
	 * 
	 * @param inUrl
	 * @param isPodcast
	 * @param inUsername
	 * @param inPassword
	 * @return
	 */
	public static Map<String, String> checkAction(String url) {
		return AbstractFeedCrawler.checkAction(url, null, null);
	}

	/**
	 * Checks if a feed is processable
	 * 
	 * @param inUrl
	 * @param isPodcast
	 * @param inUsername
	 * @param inPassword
	 * @return
	 */
	public static Map<String, String> checkAction(String inUrl, String inUsername, String inPassword) {
		final Map<String, String> checkResult = new HashMap<String, String>();
		final ConnectionHandler theConnectionHandler = new ConnectionHandler(1);
		checkResult.put("error", "none");
		try {
			final ConnectionSettings theConnectionSettings = new ConnectionSettings(inUrl, null, null, inUsername, inPassword);
			final InputStream theInputStream = theConnectionHandler.connect(theConnectionSettings);

			if (theInputStream != null) {
				final SyndFeedInput input = new SyndFeedInput();
				final SyndFeed feed = input.build(new XmlReader(theInputStream));
				final TtsLangData theLang;
				if ((feed.getLanguage() != null) && ((theLang = TtsLangData.getDefaultTtsLanguage(feed.getLanguage())) != null)) {
					checkResult.put(StringShop.LANG_ID, String.valueOf(theLang.getId()));
				} else {
					checkResult.put(StringShop.LANG_ID, "none");
				}

				final String theTitle = feed.getTitle();

				if ((null == theTitle) || net.violet.common.StringShop.EMPTY_STRING.equals(theTitle)) {
					checkResult.put("channelTitle", "none");
				} else {
					checkResult.put("channelTitle", theTitle);
				}

			} else {
				checkResult.put("error", "Could not get a hold of : " + inUrl);
			}
		} catch (final Exception e) {
			AbstractFeedCrawler.LOGGER.fatal(e, e);
			checkResult.put("error", (e.getMessage() != null) ? e.getMessage() : "Unknown error");
		}
		AbstractFeedCrawler.LOGGER.debug(checkResult);

		return checkResult;
	}

	protected abstract List<Content> getMostRecents(FeedUnit inFeedUnit);

	/**
	 * Traite une action.
	 * 
	 * @param inFeedUnit action à traiter.
	 */
	protected void processFeedUnit(final FeedUnit inFeedUnit) {
		final List<T> theNews = new LinkedList<T>();

		try {
			final Date lastModified = inFeedUnit.getLastModified();
			final ConnectionSettings theConnectionSettings = new ConnectionSettings(inFeedUnit.getUrl(), inFeedUnit.getETag(), lastModified, inFeedUnit.getUsername(), inFeedUnit.getPassword());

			try {
				final InputStream theInputStream = this.mConnectionHandler.connect(theConnectionSettings);
				final List<Content> theContents = getMostRecents(inFeedUnit);
				if (theInputStream != null) {
					final SyndFeedInput input = new SyndFeedInput();
					final SyndFeed feed = input.build(new XmlReader(theInputStream));

					// On ajout une à une des news dans la liste des taitrements et dans la liste des news
					final Iterator iterator = feed.getEntries().iterator();
					int nbItemRead = 0;
					boolean hasContentsBeenMetBefore = false;
					while (iterator.hasNext() && (nbItemRead < getNbItems2Read()) && !hasContentsBeenMetBefore) {

						final SyndEntry aFeedEntry = (SyndEntry) iterator.next();
						if (aFeedEntry.getTitle() != null) {
							// Si la news courante n'a pas déjà été traitée
							if ((inFeedUnit.getIdLastNews() != null) && inFeedUnit.getIdLastNews().equals(aFeedEntry.getUri())) { // traitement rapide Twitter & Gmail
								hasContentsBeenMetBefore = true;
							} else {

								if (isItemNew(theContents, aFeedEntry)) {
									// Test to see if the current ProcessUnit can be process or if we have to skip it
									if ((this.mThrottle == null) || this.mThrottle.isOperationAllowed(inFeedUnit)) {
										final T theProcessUnit = getProcessUnit(aFeedEntry.getTitle(), generateLink(aFeedEntry, false), aFeedEntry.getUri(), inFeedUnit.getLang(), getService().getTtl());

										if (theProcessUnit != null) {
											theNews.add(theProcessUnit);
										}

									} else {
										AbstractFeedCrawler.LOGGER.debug("THROTTLED : " + aFeedEntry.getTitle());
									}
								} else {
									hasContentsBeenMetBefore = true;
								}

							}

							nbItemRead++;
						}
					}
				}
			} finally {
				this.mConnectionHandler.disconnect(theConnectionSettings);
			}

			// Si il y avait des news à lire
			if (!theNews.isEmpty()) {

				final ThreadWatcher theThreadWatcher = new ThreadWatcher();
				try {
					for (final T aProcessUnit : theNews) {
						aProcessUnit.setThreadWatcher(theThreadWatcher);
						aProcessUnit.startWatching();
						processUnit(aProcessUnit);
					}
					theThreadWatcher.await();
				} catch (final InterruptedException e) {
					AbstractFeedCrawler.LOGGER.fatal(e, e);
				}

				// On sépare les traitements réussis des traitements en erreur
				final List<Files> theNewsFiles = new LinkedList<Files>();
				final List<AbstractCrawlerProcessUnit> failedProcesses = new LinkedList<AbstractCrawlerProcessUnit>();

				for (final AbstractCrawlerProcessUnit aUnit : theNews) {

					if (!aUnit.isError()) {
						theNewsFiles.add(aUnit.getResult());
					} else {
						failedProcesses.add(aUnit);
					}
				}

				if (!failedProcesses.isEmpty()) {
					theNews.removeAll(failedProcesses);
				}

				// S'il y a au moins 1 traitement réussi...
				if (!theNewsFiles.isEmpty()) {
					final MessageSignature theActionsSignature = inFeedUnit.getMessageSignature();
					final String theTitle = theNews.get(0).getTitle();
					processNews(theNewsFiles, theNews, theActionsSignature, theTitle, inFeedUnit);

					// A la fin du process on met à jour l'action avec les informations collectées.
					if (getService().equals(ServiceFactory.SERVICE.TWITTER.getService()) || getService().equals(ServiceFactory.SERVICE.GMAIL.getService())) {
						updateConnectionSettingAndIdXMl(inFeedUnit, theConnectionSettings, theNews.get(0).getId_xml());
					} else {
						updateConnectionSettingAndIdXMl(inFeedUnit, theConnectionSettings, null);
					}
				}
			}
		} catch (final MalformedURLException e) {
			AbstractFeedCrawler.LOGGER.fatal(e, e);
		} catch (final SocketTimeoutException e) { // feed is unreachable, do not send mail
			AbstractFeedCrawler.LOGGER.error(e, e);
		} catch (final ParsingFeedException e) { // feed is invalid, do not send mail
			AbstractFeedCrawler.LOGGER.error(e, e);
		} catch (final IOException e) {
			AbstractFeedCrawler.LOGGER.fatal(e, e);
		} catch (final IllegalArgumentException e) {
			AbstractFeedCrawler.LOGGER.error(e, e);// feed is invalid, do not send mail
		} catch (final FeedException e) {
			AbstractFeedCrawler.LOGGER.fatal(e, e);
		}
	}

	protected abstract void updateConnectionSettingAndIdXMl(FeedUnit inUnit, ConnectionSettings inSettings, String inIdXml);

	/**
	 * Returns the {@link Date} when the {@link Content} was last modified. Depending on the Feed,
	 * it could be the date when the item was last modified by the feed's author or if it was not provided, it is the date when
	 * the {@link Content} was inserted.
	 * 
	 * @return
	 */

	@Override
	protected void quit() {
		super.quit();
		this.mConnectionHandler.shutdown();
	}

	/**
	 * Determins what action to perform given a list of {@link Content} and a
	 * {@link SyndEntry}
	 * 
	 * @param inContentList
	 * @param inFeedEntry
	 * @return
	 */
	boolean isItemNew(List<Content> inContentList, SyndEntry inFeedEntry) {
		final Iterator<Content> contentIterator = inContentList.iterator();
		Content theContent = null;

		while (contentIterator.hasNext()) {

			theContent = contentIterator.next();

			final String id = theContent.getId_xml();

			final String theEntryID = ((id != null) ? inFeedEntry.getUri() : net.violet.common.StringShop.EMPTY_STRING) + inFeedEntry.getTitle() + generateLink(inFeedEntry, true);
			final String theContentID = ((id != null) ? id : net.violet.common.StringShop.EMPTY_STRING) + theContent.getTitle() + generateLink(theContent);

			if (theEntryID.equals(theContentID)) {
				return false;
			}

		}

		return true;
	}

	protected String generateLink(SyndEntry inFeedEntry, boolean doStrip) {
		return inFeedEntry.getLink();
	}

	protected String generateLink(Content inContent) {
		return inContent.getLink();
	}

	protected abstract int getNbItems2Read();
}
