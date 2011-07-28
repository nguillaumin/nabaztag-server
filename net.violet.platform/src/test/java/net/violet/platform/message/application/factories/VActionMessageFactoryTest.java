package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.applications.VActionFreeHandler;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedSubscription;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.datamodel.mock.FeedMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FeedSubscriptionData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.junit.Assert;
import org.junit.Test;

public class VActionMessageFactoryTest extends MockTestBase {

	private void init() {
		final FeedData theFeed = FeedData.getData(new FeedMock("http://www.lesechos.fr/rss/rss_une.xml", getFrLang(), Feed.Type.RSS, Feed.AccessRight.FREE));
		for (int i = 0; i < 30; i++) {
			theFeed.addItem(Collections.<Files> singletonList(new FilesMock("path" + i, MIME_TYPES.A_MPEG)), "title" + i, "link" + i, "uri" + 1, 30);
		}

		FeedSubscriptionData.create(theFeed, VObjectData.getData(getKowalskyObject()), 30);

		final ApplicationData theApplication = ApplicationData.findByName("net.violet.rss.cbbc_newsround");
		theApplication.createSetting(ApplicationSetting.FeedHandler.FEED_ID, theFeed.getId().toString());
	}

	@Test
	public void getMessageWithFreeNewContents() {
		init();
		final int nbNews = 3;
		final Feed theFeed = Factories.FEED.findByUrlAndType("http://www.lesechos.fr/rss/rss_une.xml", Feed.Type.RSS);
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.rss.cbbc_newsround"), getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(VActionFreeHandler.NB_NEWS, Integer.toString(nbNews));
		theSubscription.setSettings(settings);

		final VActionMessageFactory theFactory = (VActionMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final SubscriptionSchedulingData scheduling = SubscriptionSchedulingData.create(SubscriptionData.getData(theSubscription), SchedulingType.SCHEDULING_TYPE.Ambiant);
		final Message2Send theMessage = theFactory.getMessage(new MessageProcessUnit(scheduling, null, null) {

			@Override
			public void runWhenSuccessful() {
			}
		}).get(0);
		Assert.assertNotNull(theMessage);
		Assert.assertEquals(ServiceFactory.SERVICE.RSS.getService().getTtl(), theMessage.getTTL());
		Assert.assertEquals(VActionMessageFactory.buildTitle(ServiceFactory.SERVICE.RSS.getLabel(), "title29"), theMessage.getTitle());
		Assert.assertEquals(new MessageSignature(ServiceFactory.SERVICE.RSS.getService()), theMessage.getSignature());
		Assert.assertEquals(getKowalskyObject(), theMessage.getRecipient());
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());
		Assert.assertEquals(Palette.RANDOM, theMessage.getColorPal());
		Assert.assertTrue(theMessage.isStream());
		final Files[] body = theMessage.getBody();
		Assert.assertNotNull(body);

		Assert.assertEquals(nbNews + 1, body.length);
		Assert.assertEquals("path27", body[1].getPath());
		Assert.assertEquals("path28", body[2].getPath());
		Assert.assertEquals("path29", body[3].getPath());

		theMessage.runWhenSent();
		final FeedSubscriptionData sub = FeedSubscriptionData.findByObjectAndFeed(VObjectData.getData(getKowalskyObject()), FeedData.getData(theFeed));
		Assert.assertEquals("title29", sub.getLastReadItem().getTitle());

	}

	@Test
	public void getMessageWithFullNewContents() {
		init();
		final int nbNews = 3;
		final Feed theFeed = Factories.FEED.findByUrlAndType("http://www.lesechos.fr/rss/rss_une.xml", Feed.Type.RSS);
		final Subscription theSubscription = new SubscriptionMock(1, Application.NativeApplication.RSS_FULL.getApplication(), getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(VActionFullHandler.FEED, theFeed.getId().toString());
		settings.put(VActionFullHandler.FILE, new FilesMock("full_announce", MIME_TYPES.A_MPEG).getId().toString());
		settings.put(VActionFreeHandler.NB_NEWS, Integer.toString(nbNews));
		theSubscription.setSettings(settings);

		final VActionMessageFactory theFactory = (VActionMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final SubscriptionSchedulingData scheduling = SubscriptionSchedulingData.create(SubscriptionData.getData(theSubscription), SchedulingType.SCHEDULING_TYPE.Ambiant);
		final Message2Send theMessage = theFactory.getMessage(new MessageProcessUnit(scheduling, null, null) {

			@Override
			public void runWhenSuccessful() {
			}
		}).get(0);
		Assert.assertNotNull(theMessage);
		Assert.assertEquals(ServiceFactory.SERVICE.RSS.getService().getTtl(), theMessage.getTTL());
		Assert.assertEquals(VActionMessageFactory.buildTitle(ServiceFactory.SERVICE.RSS.getLabel(), "title29"), theMessage.getTitle());
		Assert.assertEquals(new MessageSignature(ServiceFactory.SERVICE.RSS.getService()), theMessage.getSignature());
		Assert.assertEquals(getKowalskyObject(), theMessage.getRecipient());
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, theMessage.getMode());
		Assert.assertEquals(Palette.RANDOM, theMessage.getColorPal());
		Assert.assertTrue(theMessage.isStream());
		final Files[] body = theMessage.getBody();
		Assert.assertNotNull(body);

		Assert.assertEquals(nbNews + 1, body.length);
		Assert.assertEquals("full_announce", body[0].getPath());
		Assert.assertEquals("path27", body[1].getPath());
		Assert.assertEquals("path28", body[2].getPath());
		Assert.assertEquals("path29", body[3].getPath());

		theMessage.runWhenSent();
		final FeedSubscriptionData sub = FeedSubscriptionData.findByObjectAndFeed(VObjectData.getData(getKowalskyObject()), FeedData.getData(theFeed));
		Assert.assertEquals("title29", sub.getLastReadItem().getTitle());

	}

	@Test
	public void getMessageWithNoNewContents() {
		init();
		final int nbNews = 3;
		final Feed theFeed = Factories.FEED.findByUrlAndType("http://www.lesechos.fr/rss/rss_une.xml", Feed.Type.RSS);

		final FeedSubscription sub = Factories.FEED_SUBSCRIPTION.create(theFeed, getKowalskyObject(), 0);
		Assert.assertEquals("title29", sub.getLastReadItem().getTitle());

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.rss.cbbc_newsround"), getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(VActionFreeHandler.NB_NEWS, Integer.toString(nbNews));
		theSubscription.setSettings(settings);

		final VActionMessageFactory theFactory = (VActionMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final SubscriptionSchedulingData scheduling = SubscriptionSchedulingData.create(SubscriptionData.getData(theSubscription), SchedulingType.SCHEDULING_TYPE.Ambiant);
		final List<Message2Send> theMessage = theFactory.getMessage(new MessageProcessUnit(scheduling, null, null) {

			@Override
			public void runWhenSuccessful() {
			}
		});
		Assert.assertTrue(theMessage.isEmpty());

	}

}
