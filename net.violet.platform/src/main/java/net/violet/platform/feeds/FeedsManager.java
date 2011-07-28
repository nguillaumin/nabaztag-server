package net.violet.platform.feeds;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.GroupData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.processors.EntryProcessor;
import net.violet.platform.feeds.processors.FullTTSProcessor;
import net.violet.platform.feeds.processors.ProcessedEntry;
import net.violet.platform.feeds.processors.ProcessingException;
import net.violet.platform.feeds.senders.EntrySender;
import net.violet.platform.feeds.senders.FullSender;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringTools;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * A tool class currently mainly used by the group feature to interact with the group's wall (actually a rss feed).
 */
public class FeedsManager {

	private static final int MAX_ITEMS_AMOUNT = 10;

	private static final Logger LOGGER = Logger.getLogger(FeedsManager.class);

	private static final String FEED_TYPE = "rss_2.0";
	private static final String VIOLET_LINK = "http://my.violet.net";
	private static final int DESC_MAX_LENGTH = 140;

	/**
	 * Adds an entry to the given group's feed.
	 * Optimization : this method silently adds the new entry to the feed and broadcast it to the group members (rabbit members actually)
	 * @param group
	 * @param author
	 * @param title
	 * @param content
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 * @throws IOException
	 */
	public static void addEntry(GroupData group, VObjectData author, String title, String content) throws IllegalArgumentException, FeedException, IOException {

		final SyndFeed theFeed = new SyndFeedInput().build(FilesManagerFactory.FILE_MANAGER.getReaderFor(group.getStream().getReference()));
		theFeed.setPublishedDate(new CCalendar(false, CCalendar.UNIVERSAL_TIME).getTime());

		final List<SyndEntry> entries = theFeed.getEntries();
		final SyndEntry theEntry = new SyndEntryImpl();
		theEntry.setAuthor(author.getEmailAddress());
		theEntry.setTitle(title);

		final SyndContent entryContent = new SyndContentImpl();
		entryContent.setValue(content);

		theEntry.setDescription(entryContent);
		theEntry.setLink(FeedsManager.VIOLET_LINK);
		theEntry.setPublishedDate(new CCalendar(false, CCalendar.UNIVERSAL_TIME).getTime());
		theEntry.setUri(System.currentTimeMillis() + entries.size() + "");
		entries.add(0, theEntry);
		theFeed.setEntries(entries);
		FeedsManager.writeContentToFiles(theFeed, group.getStream());

		// we'll now add the new item and broadcast its contents to members
		try {
			final FeedData theGroupFeed = group.getFeed();
			final EntryProcessor processor = FullTTSProcessor.getProcessor(theGroupFeed.getLanguage().getReference());
			final ProcessedEntry processedEntry = processor.processEntry(theEntry);

			final EntrySender sender = new FullSender(theGroupFeed, FeedsManager.MAX_ITEMS_AMOUNT, group.getMembers());
			sender.performTreatment(Collections.singletonList(processedEntry));
		} catch (final ProcessingException e) {
			FeedsManager.LOGGER.fatal(e, e);
		}

	}

	/**
	 * This method uses the group's feed file (which must exist !) and fills it up with the required elements
	 * of an empty feed (RSS 2.0)
	 * @param group
	 * @throws IOException
	 * @throws FeedException
	 */
	public static void createEmptyFeed(GroupData group) throws IOException, FeedException {
		final FilesData streamFile = group.getStream();
		final SyndFeed theFeed = new SyndFeedImpl();

		theFeed.setFeedType(FeedsManager.FEED_TYPE);
		theFeed.setTitle(group.getName());
		theFeed.setLink(FeedsManager.VIOLET_LINK);
		theFeed.setDescription(StringTools.getFirstSentence(group.getDescription(), FeedsManager.DESC_MAX_LENGTH));
		theFeed.setLanguage(group.getLanguage().getLang_iso_code());
		theFeed.setPublishedDate(new CCalendar(false, CCalendar.UNIVERSAL_TIME).getTime());

		final VObjectData creator = group.getCreator();
		theFeed.setAuthor(creator.getEmailAddress());
		FeedsManager.writeContentToFiles(theFeed, streamFile);
	}

	private static void writeContentToFiles(SyndFeed feed, FilesData file) throws IOException, FeedException {
		TmpFile tmpFile = null;
		try {
			tmpFile = FilesManager.TMP_MANAGER.new TmpFile();
			new SyndFeedOutput().output(feed, tmpFile.get());

			FilesManagerFactory.FILE_MANAGER.writeContentTo(new FileInputStream(tmpFile.get()), file.getReference());
		} catch (final IOException e) {
			FeedsManager.LOGGER.fatal(e, e);
			throw e;
		} catch (final FeedException e) {
			FeedsManager.LOGGER.fatal(e, e);
			throw e;
		} finally {
			if (tmpFile != null) {
				tmpFile.delete();
			}
		}
	}

}
