package net.violet.platform.applications;

import java.util.Collections;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FeedSubscriptionData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class VActionFreeHandlerTest extends MockTestBase {

	private ApplicationData getRssApplication() {
		final ApplicationData appli = ApplicationData.findByName("net.violet.rss.lci");
		final FeedData theFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Feed.Type.RSS, Feed.AccessRight.FREE);
		for (int i = 1; i < 10; i++) {
			theFeed.addItem(Collections.<Files> emptyList(), "title" + i, "link" + i, "id" + i, 20);
		}

		appli.createSetting(ApplicationSetting.FeedHandler.FEED_ID, theFeed.getId().toString());

		return appli;
	}

	private ApplicationData getPodcastApplication() {
		final ApplicationData appli = ApplicationData.findByName("net.violet.podcast.europe1_sante");
		final FeedData theFeed = FeedData.getFeed("url", TtsLangData.findByISOCode("fr-FR"), Feed.Type.PODCAST, Feed.AccessRight.FREE);
		for (int i = 11; i < 20; i++) {
			theFeed.addItem(Collections.<Files> emptyList(), "title" + i, "link" + i, "id" + i, 20);
		}

		appli.createSetting(ApplicationSetting.FeedHandler.FEED_ID, theFeed.getId().toString());

		return appli;
	}

	private FeedData getFeed(ApplicationData appli) {
		final ApplicationSettingData setting = ApplicationSettingData.findByApplicationAndKey(appli, ApplicationSetting.FeedHandler.FEED_ID);
		return FeedData.findById(Long.parseLong(setting.getValue()));
	}

	@Test(expected = InvalidSettingException.class)
	public void objectIsV1Test() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getBrewsterObject());

		ApplicationHandlerManager.createSubscription(getPodcastApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getRssApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = InvalidSettingException.class)
	public void tooManyNewsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getRssApplication(), object, Collections.<String, Object> singletonMap(VActionFreeHandler.NB_NEWS, "45"));
	}

	@Test(expected = InvalidSettingException.class)
	public void notEnoughNewsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getRssApplication(), object, Collections.<String, Object> singletonMap(VActionFreeHandler.NB_NEWS, "0"));
	}

	@Test
	public void validPodcastSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final ApplicationData podcast = getPodcastApplication();

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(podcast, object, Collections.<String, Object> emptyMap());
		Assert.assertTrue(theSub.isValid());

		final Map<String, Object> settings = theSub.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertEquals("1", settings.get(VActionFreeHandler.NB_NEWS));

		final FeedData theFeed = getFeed(podcast);
		final FeedSubscriptionData subscription = FeedSubscriptionData.findByObjectAndFeed(object, theFeed);
		Assert.assertNotNull(subscription);
		Assert.assertTrue(subscription.isValid());
		Assert.assertEquals("title18", subscription.getLastReadItem().getTitle());
	}

	@Test
	public void validRssSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final ApplicationData rss = getRssApplication();

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(rss, object, Collections.<String, Object> singletonMap(VActionFreeHandler.NB_NEWS, "8"));
		Assert.assertTrue(theSub.isValid());

		final Map<String, Object> settings = theSub.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertEquals("8", settings.get(VActionFreeHandler.NB_NEWS));

		final FeedData theFeed = getFeed(rss);
		final FeedSubscriptionData subscription = FeedSubscriptionData.findByObjectAndFeed(object, theFeed);
		Assert.assertNotNull(subscription);
		Assert.assertTrue(subscription.isValid());
		Assert.assertEquals("title1", subscription.getLastReadItem().getTitle());
	}

}
