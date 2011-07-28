package net.violet.platform.feeds;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.httpclient.Connection;
import net.violet.platform.httpclient.ConnectionConfiguration;
import net.violet.platform.httpclient.ConnectionsManager;

import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * Dealing with feeds can be very tough, therefor this class contains some useful methods which can help.  
 */
public class FeedsTools {

	private static final Pattern EXTENSION_MATCHER = Pattern.compile(".*\\.m(?:p(?:3|4)|4a)$", Pattern.CASE_INSENSITIVE);

	/**
	 * Returns the first valid enclosure url found in the given entry, null if there is not any.
	 * A valid enclosure url refers to a file with extension in [mp3,mp4, m4a) and has a content type containing "audio"
	 * @param entry
	 * @return
	 */
	public static String extractEnclosureUrl(SyndEntry entry) {
		String enclosureUrl = null;
		final Iterator<SyndEnclosure> theEnclosures = entry.getEnclosures().iterator();
		while (theEnclosures.hasNext() && (enclosureUrl == null)) {
			final SyndEnclosure anEnclosure = theEnclosures.next();

			if (FeedsTools.EXTENSION_MATCHER.matcher(anEnclosure.getUrl()).matches()) {
				if ((anEnclosure.getType() != null) && anEnclosure.getType().contains("audio")) {
					enclosureUrl = anEnclosure.getUrl();
				}
			}
		}

		return enclosureUrl;
	}

	/**
	 * Uses the given url and try to open a connection with it and to parse the stream as a SyndFeed object.
	 * If all of these tasks are successfully completed true is returned, otherwise it returns false.
	 * 
	 * username and password may be given if needed but they may be null.
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean isFeedValid(String url, String username, String password) {
		final ConnectionsManager manager = new ConnectionsManager(1);
		Connection connection = null;

		try {
			final ConnectionConfiguration configuration = new ConnectionConfiguration(url);
			if ((username != null) && (password != null)) {
				configuration.addCredentials(username, password);
			}

			connection = manager.openConnection(configuration);
			final InputStream theInputStream = connection.getInputStream();

			new SyndFeedInput().build(new XmlReader(theInputStream));

			return true;

		} catch (final Exception e) {

		} finally {
			if (connection != null) {
				connection.close();
			}
			manager.shutdown();
		}

		return false;

	}

	/**
	 * Opens a connection to the given url (which should refer to a valid feed) and try to extract the language used
	 * by the feed.
	 * 
	 * If the language cannot be found, the default language (en-US) is returned
	 * 
	 * @param url
	 * @return
	 */
	public static TtsLangData extractFeedLanguage(String url) {
		final ConnectionsManager manager = new ConnectionsManager(1);
		Connection connection = null;

		try {
			connection = manager.openConnection(new ConnectionConfiguration(url));
			final InputStream theInputStream = connection.getInputStream();

			final SyndFeed feed = new SyndFeedInput().build(new XmlReader(theInputStream));

			if (feed.getLanguage() != null) {
				final TtsLangData theLang = TtsLangData.getDefaultTtsLanguage(feed.getLanguage());
				if (theLang != null) {
					return theLang;
				}
			}

		} catch (final Exception e) {

		} finally {
			if (connection != null) {
				connection.close();
			}
			manager.shutdown();
		}

		return TtsLangData.findByISOCode("en-US");
	}

	// Migration tools, all the methods below are used by the migration process

	// type = 1 for podcast, 2 for rss
	public static Set<VAction> getUsedFullActionByType(int type) {
		final Set<VAction> usedActions = new HashSet<VAction>();

		final Application theApplication = type == 1 ? Application.NativeApplication.PODCAST_FULL.getApplication() : Application.NativeApplication.RSS_FULL.getApplication();

		for (final Subscription aSubscription : Factories.SUBSCRIPTION.findAllByApplication(theApplication)) {
			final Object actionSetting = aSubscription.getSettings().get("actionId");
			if (actionSetting != null) {
				final VAction theAction = Factories.VACTION.find(Long.parseLong(actionSetting.toString()));
				if (theAction.getAccess_right().equals("FULL")) {
					usedActions.add(Factories.VACTION.find(Long.parseLong(actionSetting.toString())));
				}
			}
		}

		return usedActions;
	}

}
