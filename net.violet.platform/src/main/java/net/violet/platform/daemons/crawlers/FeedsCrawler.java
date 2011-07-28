package net.violet.platform.daemons.crawlers;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.violet.common.StringShop;
import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.applications.GmailTwitterHandler;
import net.violet.platform.applications.VActionFreeHandler;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.implementations.VActionFactoryImpl;

/**
 * Migration crawler to the new subscription settings system
 */
public class FeedsCrawler {

	private static abstract class AbstractRssPodcastWalker implements RecordWalker<VAction> {

		private final Feed.Type type;
		private final Feed.AccessRight accessRight;
		private final Executor executor;
		private final int nbItems;

		protected AbstractRssPodcastWalker(Feed.Type type, Feed.AccessRight accessRight, Executor executor) {
			this.type = type;
			this.accessRight = accessRight;
			this.executor = executor;
			this.nbItems = type == Feed.Type.RSS ? 30 : 1;
		}

		protected void doSomethingSpecial(VAction theAction, Feed newFeed) {
			return;
		}

		public void process(final VAction theAction) {
			this.executor.execute(new Runnable() {

				public void run() {
					if (!theAction.getUrl().equals(StringShop.EMPTY_STRING)) {

						// new feed creation
						Feed newFeed = Factories.FEED.create(theAction.getUrl().trim(), AbstractRssPodcastWalker.this.type, theAction.getLang(), AbstractRssPodcastWalker.this.accessRight);
						if (newFeed == null) { // creation failed, but the feed may already exist
							newFeed = Factories.FEED.findByUrlAndType(theAction.getUrl().trim(), AbstractRssPodcastWalker.this.type);
							if (newFeed == null) { // no, it's a real failure
								System.err.println("Failed : " + theAction.getId() + " " + theAction.getUrl());
								return;
							}
						} else {
							final Date lastModified = theAction.getLastModified();
							final Timestamp newLastModified = (lastModified == null) ? null : new Timestamp(lastModified.getTime());
							newFeed.updateInformation(theAction.getETag(), newLastModified);
						}

						doSomethingSpecial(theAction, newFeed); // special treatment for the free feeds

						// now, we can take care of the full applications using this action as feed
						final Application theAppli = (AbstractRssPodcastWalker.this.type == Feed.Type.PODCAST) ? Application.NativeApplication.PODCAST_FULL.getApplication() : Application.NativeApplication.RSS_FULL.getApplication();
						final Feed theRealFeed = newFeed; // trick to make it available in the anonymous class
						for (final Subscription aSubscription : Factories.SUBSCRIPTION.findAllByApplication(theAppli)) {
							final Map<String, Object> currentSettings = aSubscription.getSettings();
							if (!currentSettings.containsKey(VActionFullHandler.FEED) && currentSettings.get(VActionFullHandler.ACTION).toString().equals(theAction.getId().toString())) {
								currentSettings.put(VActionFullHandler.FEED, theRealFeed.getId().toString());
								aSubscription.setSettings(currentSettings);
							}
						}

						// try to rescue the contents
						final List<Content> existingContents = Factories.CONTENT.findMostRecentsByAction(theAction, AbstractRssPodcastWalker.this.nbItems, true);
						Collections.reverse(existingContents);
						for (final Content aContent : existingContents) {
							Factories.FEED_ITEM.create(newFeed, Collections.singletonList(aContent.getFile()), aContent.getTitle(), aContent.getLink(), aContent.getId_xml());
						}
					}
				}
			});
		}
	}

	private static class RssPodcastFreeWalker extends AbstractRssPodcastWalker {

		private RssPodcastFreeWalker(Feed.Type type, Executor executor) {
			super(type, Feed.AccessRight.FREE, executor);
		}

		@Override
		public void doSomethingSpecial(VAction theAction, Feed newFeed) {

			final Application theApplicationUsingThisAction = Factories.APPLICATION.find(theAction.getApplicationId());
			if ((theApplicationUsingThisAction != null) && ((Application.NativeApplication.findByApplication(theApplicationUsingThisAction) == Application.NativeApplication.RSS_FREE) || (Application.NativeApplication.findByApplication(theApplicationUsingThisAction) == Application.NativeApplication.PODCAST_FREE))) {
				if (Factories.APPLICATION_SETTING.findByApplicationAndKey(theApplicationUsingThisAction, ApplicationSetting.FeedHandler.FEED_ID) == null) {
					Factories.APPLICATION_SETTING.create(theApplicationUsingThisAction, ApplicationSetting.FeedHandler.FEED_ID, newFeed.getId().toString());
				}
			}
			// Here the application free has a link to the feed

			// We edit the free subscriptions to this application, only nbNews is required
			for (final Subscription aSubscription : Factories.SUBSCRIPTION.findAllByApplication(theApplicationUsingThisAction)) {
				final Object nbNewsSetting = aSubscription.getSettings().get(VActionFreeHandler.NB_NEWS);
				aSubscription.setSettings(Collections.singletonMap(VActionFreeHandler.NB_NEWS, nbNewsSetting.toString()));
			}
		}
	}

	private static class RssPodcastFullWalker extends AbstractRssPodcastWalker {

		private RssPodcastFullWalker(Feed.Type type, Executor executor) {
			super(type, Feed.AccessRight.FULL, executor);
		}

	}

	private static class GmailTwitterWalker implements RecordWalker<Subscription> {

		private final Executor executor;

		private GmailTwitterWalker(Executor executor) {
			this.executor = executor;
		}

		public void process(final Subscription inObject) {
			this.executor.execute(new Runnable() {

				public void run() {
					final Map<String, Object> settings = new HashMap<String, Object>(inObject.getSettings());
					settings.put(GmailTwitterHandler.LAST_ENTRY_ID, inObject.getSettings().get("id_last_news"));
					inObject.setSettings(settings);
				}
			});
		}
	}

	private static final Set<VAction> usedActions = Collections.synchronizedSet(new HashSet<VAction>());

	// fills up usedActions, actions which are not in the set are unused and have to be removed
	private static class RssPodcastFullSuppressor implements RecordWalker<Subscription> {

		private final Executor executor;

		private RssPodcastFullSuppressor(Executor executor) {
			this.executor = executor;
		}

		public void process(final Subscription inObject) {
			this.executor.execute(new Runnable() {

				public void run() {
					final Object actionSetting = inObject.getSettings().get(VActionFullHandler.ACTION);
					if (actionSetting != null) {
						final VAction action = Factories.VACTION.find(Long.parseLong(actionSetting.toString()));
						if ((action != null) && (action.getAccess_right().equals("FULL"))) {
							FeedsCrawler.usedActions.add(action);
						}
					}
				}
			});

		}
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(50);

		/** migration gmail/twitter **/
		final GmailTwitterWalker gmailTwitterWalker = new GmailTwitterWalker(executor);
		Factories.SUBSCRIPTION.walkByApplication(Application.NativeApplication.GMAIL.getApplication(), gmailTwitterWalker);
		Factories.SUBSCRIPTION.walkByApplication(Application.NativeApplication.TWITTER.getApplication(), gmailTwitterWalker);

		/** removed unused full actions **/
		final RssPodcastFullSuppressor remover = new RssPodcastFullSuppressor(executor);
		Factories.SUBSCRIPTION.walkByApplication(Application.NativeApplication.RSS_FULL.getApplication(), remover);
		Factories.SUBSCRIPTION.walkByApplication(Application.NativeApplication.PODCAST_FULL.getApplication(), remover);

		FeedsCrawler.waitForTermination(executor); // needs to wait for the executor to be terminated : the usedActions set will be fully populated then

		Factories.VACTION.walk(new RecordWalker<VAction>() {

			public void process(VAction inObject) {
				if ((inObject.getAccess_right().equals("FULL")) && !FeedsCrawler.usedActions.contains(inObject)) {
					inObject.delete();
				}
			}
		});

		/** rss/podcast free migration **/
		executor = Executors.newFixedThreadPool(50);
		((VActionFactoryImpl) Factories.VACTION).walkByServiceAndAccessRight(2, "FREE", new RssPodcastFreeWalker(Feed.Type.RSS, executor));
		((VActionFactoryImpl) Factories.VACTION).walkByServiceAndAccessRight(1, "FREE", new RssPodcastFreeWalker(Feed.Type.PODCAST, executor));
		FeedsCrawler.waitForTermination(executor); // have to wait for the free feed to be fully migrated before starting processing the full ones

		/** rss/podcast full migration **/
		executor = Executors.newFixedThreadPool(50);
		((VActionFactoryImpl) Factories.VACTION).walkByServiceAndAccessRight(2, "FULL", new RssPodcastFullWalker(Feed.Type.RSS, executor));
		((VActionFactoryImpl) Factories.VACTION).walkByServiceAndAccessRight(1, "FULL", new RssPodcastFullWalker(Feed.Type.PODCAST, executor));
		FeedsCrawler.waitForTermination(executor);
	}

	private static void waitForTermination(ExecutorService executor) throws InterruptedException {
		Thread.sleep(5000);

		executor.shutdown();
		System.err.println("executor shuted down");
		while (!executor.isTerminated()) {
			System.err.println("waiting for full termination");
			executor.awaitTermination(10, TimeUnit.SECONDS);
		}
		System.err.println("Terminated");
	}
}
