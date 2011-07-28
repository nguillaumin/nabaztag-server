package net.violet.platform.handlers.rfid;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import net.violet.platform.applications.VActionFreeHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.FeedSubscription;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.ZtampBatch;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FeedMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.ZtampBatchMock;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class RfidInitializerTest extends MockTestBase {

	@Test
	public void initRfidTest() {

		final VObject theRfid = Factories.VOBJECT.createObject(HARDWARE.ZTAMP, "serial", "name", "name", getKowalskyUser(), null, 4, 4, null);
		final Application theApplication = Factories.APPLICATION.findByName("net.violet.js.discovery");

		final ZtampBatch theBatch = new ZtampBatchMock(0, theApplication, "param", "prefix");

		RfidInitializerFactory.initRfid(theRfid, theBatch);

		final List<Subscription> theSubscriptions = Factories.SUBSCRIPTION.findByApplicationAndObject(theApplication, theRfid);
		Assert.assertEquals(1, theSubscriptions.size());
		final Subscription theSubscription = theSubscriptions.get(0);

		Assert.assertEquals(0, theSubscription.getSettings().size());
		final List<SubscriptionScheduling> theScheds = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);
		Assert.assertEquals(1, theScheds.size());
		Assert.assertEquals(SchedulingType.SCHEDULING_TYPE.InteractionTrigger, theScheds.get(0).getType());
		Assert.assertEquals(1, Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionScheduling(theScheds.get(0)).size());
	}

	@Test
	public void initRfidRssFreeTest() {

		final VObject theRfid = Factories.VOBJECT.createObject(HARDWARE.ZTAMP, "serial", "name", "name", getKowalskyUser(), null, 4, 4, null);
		final Application theApplication = new ApplicationMock(0, "net.violet.rss.test.com", getKowalskyUser(), new CCalendar(false).getTime());
		new FilesMock(8460L, "blabla", MimeType.MIME_TYPES.A_MPEG, Calendar.getInstance().getTime());
		final Feed theFeed = new FeedMock("http://www.lesechos.fr/rss/rss_une.xml", getFrLang(), Feed.Type.RSS, Feed.AccessRight.FREE);
		Factories.APPLICATION_SETTING.create(theApplication, ApplicationSetting.FeedHandler.FEED_ID, theFeed.getId().toString());

		Factories.FEED_ITEM.create(theFeed, Collections.singletonList(Factories.FILES.find(8460)), "item1", "link", "idXML1");
		Factories.FEED_ITEM.create(theFeed, Collections.singletonList(Factories.FILES.find(8460)), "item2", "link", "idXML2");

		final ZtampBatch theBatch = new ZtampBatchMock(0, theApplication, theApplication.getId().toString(), net.violet.common.StringShop.EMPTY_STRING);

		RfidInitializerFactory.initRfid(theRfid, theBatch);

		final List<Subscription> theSubscriptions = Factories.SUBSCRIPTION.findByApplicationAndObject(theApplication, theRfid);
		Assert.assertEquals(1, theSubscriptions.size());
		final Subscription theSubscription = theSubscriptions.get(0);

		Assert.assertEquals(1, theSubscription.getSettings().size());
		Assert.assertEquals("5", theSubscription.getSettings().get(VActionFreeHandler.NB_NEWS));

		final List<SubscriptionScheduling> theScheds = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);
		Assert.assertEquals(1, theScheds.size());
		Assert.assertEquals(SchedulingType.SCHEDULING_TYPE.InteractionTrigger, theScheds.get(0).getType());
		Assert.assertEquals(1, Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionScheduling(theScheds.get(0)).size());

		final FeedSubscription sub = Factories.FEED_SUBSCRIPTION.findByObjectAndFeed(theRfid, theFeed);
		Assert.assertNotNull(sub);
		Assert.assertNull(sub.getLastReadItem());

	}
}
