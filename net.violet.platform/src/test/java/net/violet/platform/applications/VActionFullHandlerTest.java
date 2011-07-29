package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedSubscription;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.FeedMock;
import net.violet.platform.datamodel.mock.TtsVoiceMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.FeedSubscriptionData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class VActionFullHandlerTest extends MockTestBase {

	private static final String PODCAST_URL = "http://www.lesinrocks.com/xml/rss/podcast.xml";
	
	private ApplicationData getRssApplication() {
		new TtsVoiceMock(1L, "voix de virgine", "Virginie", getKowalskyObject().getPreferences().getLangPreferences(), "FR-Anastasie", true, false);
		final ApplicationData appli = ApplicationData.getData(Application.NativeApplication.RSS_FULL.getApplication());
		return appli;
	}

	private ApplicationData getPodcastApplication() {
		new TtsVoiceMock(1L, "voix de virgine", "Virginie", getKowalskyObject().getPreferences().getLangPreferences(), "FR-Anastasie", true, false);
		final ApplicationData appli = ApplicationData.getData(Application.NativeApplication.PODCAST_FULL.getApplication());
		return appli;
	}

	@Test(expected = InvalidSettingException.class)
	public void objectIsV1Test() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getBrewsterObject());

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, "url");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.NB_NEWS, "10");
		theParams.put(VActionFullHandler.LANG, "fr-FR");
		ApplicationHandlerManager.createSubscription(getPodcastApplication(), object, theParams);
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getRssApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidLangTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, "url");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.NB_NEWS, "10");
		theParams.put(VActionFullHandler.LANG, "10");

		ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
	}

	@Test(expected = InvalidSettingException.class)
	public void unreachableUrlTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, "http://www.wonderland-rss.xml");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.NB_NEWS, "10");
		theParams.put(VActionFullHandler.LANG, "fr");

		ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
	}

	@Test(expected = InvalidSettingException.class)
	public void tooManyNewsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, PODCAST_URL);
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.NB_NEWS, "-4");
		theParams.put(VActionFullHandler.LANG, "fr");

		ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
	}

	@Test(expected = InvalidSettingException.class)
	public void notEnoughNewsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, PODCAST_URL);
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.NB_NEWS, "55");
		theParams.put(VActionFullHandler.LANG, "fr");

		ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
	}

	@Test
	public void validPodcastSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, PODCAST_URL);
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.LANG, "fr-FR");

		Assert.assertNull(Factories.FEED.findByUrlAndType(PODCAST_URL, Feed.Type.PODCAST));

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(getPodcastApplication(), object, theParams);
		Assert.assertTrue(theSub.isValid());

		final Map<String, Object> settings = theSub.getSettings();
		Assert.assertEquals(5, settings.size());

		Assert.assertEquals("1", settings.get(VActionFullHandler.NB_NEWS));
		Assert.assertEquals("fr-FR", settings.get(VActionFullHandler.LANG));
		Assert.assertEquals("label", settings.get(VActionFullHandler.LABEL));

		final int feedId = Integer.parseInt(settings.get(VActionFullHandler.FEED).toString());
		final FeedData theFeed = FeedData.findById(feedId);
		Assert.assertNotNull(theFeed);
		Assert.assertEquals(PODCAST_URL, theFeed.getUrl());

		final FeedSubscriptionData fSub = FeedSubscriptionData.findByObjectAndFeed(object, theFeed);
		Assert.assertNotNull(fSub);
		Assert.assertNull(fSub.getLastReadItem());

		final int fileId = Integer.parseInt(settings.get(VActionFullHandler.FILE).toString());
		final Files file = Factories.FILES.find(fileId);
		Assert.assertNotNull(file);
	}

	@Test
	public void validRssSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, "http://www.tf1.fr/xml/rss/0,,9,00.xml");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.LANG, "fr-FR");
		theParams.put(VActionFullHandler.NB_NEWS, "4");

		Assert.assertNull(Factories.FEED.findByUrlAndType("http://www.tf1.fr/xml/rss/0,,9,00.xml", Feed.Type.RSS));

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
		Assert.assertTrue(theSub.isValid());

		final Map<String, Object> settings = theSub.getSettings();
		Assert.assertEquals(5, settings.size());

		Assert.assertEquals("4", settings.get(VActionFullHandler.NB_NEWS));
		Assert.assertEquals("fr-FR", settings.get(VActionFullHandler.LANG));
		Assert.assertEquals("label", settings.get(VActionFullHandler.LABEL));

		final int feedId = Integer.parseInt(settings.get(VActionFullHandler.FEED).toString());
		final Feed feed = Factories.FEED.find(feedId);
		Assert.assertNotNull(feed);
		Assert.assertEquals("http://www.tf1.fr/xml/rss/0,,9,00.xml", feed.getUrl());

		final FeedSubscription sub = Factories.FEED_SUBSCRIPTION.findByObjectAndFeed(getKowalskyObject(), feed);
		Assert.assertNotNull(sub);
		Assert.assertNull(sub.getLastReadItem());

		final int fileId = Integer.parseInt(settings.get(VActionFullHandler.FILE).toString());
		final Files file = Factories.FILES.find(fileId);
		Assert.assertNotNull(file);

	}

	@Test
	public void subscribeAlreadyExistingActionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Feed theFeed = new FeedMock("http://www.tf1.fr/xml/rss/0,,9,00.xml", getFrLang(), Feed.Type.RSS, Feed.AccessRight.FULL);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, "http://www.tf1.fr/xml/rss/0,,9,00.xml");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.LANG, "fr-FR");
		theParams.put(VActionFullHandler.NB_NEWS, "4");

		Assert.assertNotNull(Factories.FEED.findByUrlAndType("http://www.tf1.fr/xml/rss/0,,9,00.xml", Feed.Type.RSS));

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
		Assert.assertTrue(theSub.isValid());
		final FeedSubscriptionData sub = FeedSubscriptionData.findByObjectAndFeed(object, FeedData.getData(theFeed));
		Assert.assertTrue(sub.isValid());

		final Map<String, Object> settings = theSub.getSettings();
		Assert.assertEquals(5, settings.size());

		Assert.assertEquals("4", settings.get(VActionFullHandler.NB_NEWS));
		Assert.assertEquals("fr-FR", settings.get(VActionFullHandler.LANG));
		Assert.assertEquals("label", settings.get(VActionFullHandler.LABEL));
		Assert.assertEquals(theFeed.getId().toString(), settings.get(VActionFullHandler.FEED));

		final int fileId = Integer.parseInt(settings.get(VActionFullHandler.FILE).toString());
		final Files file = Factories.FILES.find(fileId);
		Assert.assertNotNull(file);

	}

	@Test
	public void deleteTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Feed theFeed = new FeedMock("http://www.tf1.fr/xml/rss/0,,9,00.xml", getFrLang(), Feed.Type.RSS, Feed.AccessRight.FULL);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, "http://www.tf1.fr/xml/rss/0,,9,00.xml");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.LANG, "fr-FR");
		theParams.put(VActionFullHandler.NB_NEWS, "4");

		Assert.assertNotNull(Factories.FEED.findByUrlAndType("http://www.tf1.fr/xml/rss/0,,9,00.xml", Feed.Type.RSS));

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
		Assert.assertTrue(theSub.isValid());
		FeedSubscriptionData sub = FeedSubscriptionData.findByObjectAndFeed(object, FeedData.getData(theFeed));
		Assert.assertTrue(sub.isValid());

		final Map<String, Object> settings = theSub.getSettings();
		Assert.assertEquals(5, settings.size());
		Assert.assertEquals("4", settings.get(VActionFullHandler.NB_NEWS));
		Assert.assertEquals("fr-FR", settings.get(VActionFullHandler.LANG));
		Assert.assertEquals("label", settings.get(VActionFullHandler.LABEL));
		Assert.assertEquals(theFeed.getId().toString(), settings.get(VActionFullHandler.FEED));
		final int fileId = Integer.parseInt(settings.get(VActionFullHandler.FILE).toString());
		final Files file = Factories.FILES.find(fileId);
		Assert.assertNotNull(file);

		ApplicationHandlerManager.deleteSubscription(theSub);

		Assert.assertNull(Factories.FEED.findByUrlAndType("http://www.tf1.fr/xml/rss/0,,9,00.xml", Feed.Type.RSS));
		sub = FeedSubscriptionData.findByObjectAndFeed(object, FeedData.getData(theFeed));
		Assert.assertNull(sub);
	}

	@Test
	public void updateOnlyNewsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Feed theFeed = new FeedMock("http://www.tf1.fr/xml/rss/0,,9,00.xml", getFrLang(), Feed.Type.RSS, Feed.AccessRight.FULL);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, "http://www.tf1.fr/xml/rss/0,,9,00.xml");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.LANG, "fr-FR");
		theParams.put(VActionFullHandler.NB_NEWS, "4");

		Assert.assertNotNull(Factories.FEED.findByUrlAndType("http://www.tf1.fr/xml/rss/0,,9,00.xml", Feed.Type.RSS));

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
		Assert.assertTrue(theSub.isValid());

		Map<String, Object> settings = theSub.getSettings();
		Assert.assertEquals(5, settings.size());
		Assert.assertEquals("4", settings.get(VActionFullHandler.NB_NEWS));
		Assert.assertEquals("fr-FR", settings.get(VActionFullHandler.LANG));
		Assert.assertEquals("label", settings.get(VActionFullHandler.LABEL));
		Assert.assertEquals(theFeed.getId().toString(), settings.get(VActionFullHandler.FEED));
		final int fileId = Integer.parseInt(settings.get(VActionFullHandler.FILE).toString());
		final Files file = Factories.FILES.find(fileId);
		Assert.assertNotNull(file);

		theParams.clear();

		theParams.put(VActionFullHandler.URL, "http://www.tf1.fr/xml/rss/0,,9,00.xml");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.LANG, "fr-FR");
		theParams.put(VActionFullHandler.NB_NEWS, "6");

		ApplicationHandlerManager.updateSubscription(theSub, theParams);
		Assert.assertTrue(theSub.isValid());

		settings = theSub.getSettings();
		Assert.assertEquals(5, settings.size());
		Assert.assertEquals("6", settings.get(VActionFullHandler.NB_NEWS));
		Assert.assertEquals("fr-FR", settings.get(VActionFullHandler.LANG));
		Assert.assertEquals("label", settings.get(VActionFullHandler.LABEL));
		Assert.assertEquals(theFeed.getId().toString(), settings.get(VActionFullHandler.FEED));
		final int newFileId = Integer.parseInt(settings.get(VActionFullHandler.FILE).toString());
		final Files newFile = Factories.FILES.find(newFileId);

		Assert.assertNotNull(newFile);
		Assert.assertEquals(file, newFile);
	}

	@Test
	public void updateLabelTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Feed theFeed = new FeedMock("http://www.tf1.fr/xml/rss/0,,9,00.xml", getFrLang(), Feed.Type.RSS, Feed.AccessRight.FULL);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(VActionFullHandler.URL, "http://www.tf1.fr/xml/rss/0,,9,00.xml");
		theParams.put(VActionFullHandler.LABEL, "label");
		theParams.put(VActionFullHandler.LANG, "fr-FR");
		theParams.put(VActionFullHandler.NB_NEWS, "4");

		Assert.assertNotNull(Factories.FEED.findByUrlAndType("http://www.tf1.fr/xml/rss/0,,9,00.xml", Feed.Type.RSS));

		final SubscriptionData theSub = ApplicationHandlerManager.createSubscription(getRssApplication(), object, theParams);
		Assert.assertTrue(theSub.isValid());

		Map<String, Object> settings = theSub.getSettings();
		Assert.assertEquals(5, settings.size());
		Assert.assertEquals("4", settings.get(VActionFullHandler.NB_NEWS));
		Assert.assertEquals("fr-FR", settings.get(VActionFullHandler.LANG));
		Assert.assertEquals("label", settings.get(VActionFullHandler.LABEL));
		Assert.assertEquals(theFeed.getId().toString(), settings.get(VActionFullHandler.FEED));
		final int fileId = Integer.parseInt(settings.get(VActionFullHandler.FILE).toString());
		final Files file = Factories.FILES.find(fileId);
		Assert.assertNotNull(file);

		theParams.clear();

		theParams.put(VActionFullHandler.URL, "http://www.tf1.fr/xml/rss/0,,9,00.xml");
		theParams.put(VActionFullHandler.LABEL, "label2");
		theParams.put(VActionFullHandler.LANG, "fr-FR");
		theParams.put(VActionFullHandler.NB_NEWS, "4");

		ApplicationHandlerManager.updateSubscription(theSub, theParams);
		Assert.assertTrue(theSub.isValid());

		settings = theSub.getSettings();
		Assert.assertEquals(5, settings.size());
		Assert.assertEquals("4", settings.get(VActionFullHandler.NB_NEWS));
		Assert.assertEquals("fr-FR", settings.get(VActionFullHandler.LANG));
		Assert.assertEquals("label2", settings.get(VActionFullHandler.LABEL));
		Assert.assertEquals(theFeed.getId().toString(), settings.get(VActionFullHandler.FEED));
		final int newFileId = Integer.parseInt(settings.get(VActionFullHandler.FILE).toString());
		final Files newFile = Factories.FILES.find(newFileId);
		Assert.assertNotNull(newFile);
		Assert.assertFalse(file.equals(newFile));
	}

}
