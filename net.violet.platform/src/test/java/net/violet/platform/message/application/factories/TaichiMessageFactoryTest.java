package net.violet.platform.message.application.factories;

import java.util.Collections;

import net.violet.platform.applications.TaichiHandler;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SourceMock;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.schedulers.AmbiantHandler;
import net.violet.platform.util.Constantes;

import org.junit.Assert;
import org.junit.Test;

public class TaichiMessageFactoryTest extends MockTestBase {

	@Test
	public void getNotMessage() {
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.taichi"), getKowalskyObject());
		final TaichiMessageFactory factory = (TaichiMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		Assert.assertNull(factory.getMessage(null));
	}

	@Test
	public void getSourceMessageNoLastTimeTest() {
		final Source sourceF = new SourceMock(8205, "taichi.fast", 255, System.currentTimeMillis());

		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.taichi"), getKowalskyObject());
		theSubscription.setSettings(Collections.singletonMap(TaichiHandler.SOURCE, sourceF.getSource_path()));

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);

		final TaichiMessageFactory factory = (TaichiMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final long theLastTime = System.currentTimeMillis();
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNotNull(theMessage);
		Assert.assertTrue(theMessage.isSourceModeUpdate());
		Assert.assertEquals(new Integer((int) sourceF.getSource_val()), theMessage.getSources().get(Integer.toString(factory.getSource().getId())));
		Assert.assertEquals(Constantes.QUEUE_TTL_SOURCES, theMessage.getTTLInSecond());
		final SubscriptionSchedulingSettingsData theLastTimeSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, AmbiantHandler.LAST_TIME);
		Assert.assertNotNull(theLastTimeSetting);
		Assert.assertEquals(theLastTime, Long.parseLong(theLastTimeSetting.getValue()));

		sourceF.delete();

	}

	@Test
	public void getSourceMessageWithLastTimeNUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source sourceN = new SourceMock(8207, "taichi.normal", 80, System.currentTimeMillis());
		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.taichi"), getKowalskyObject());
		theSubscription.setSettings(Collections.singletonMap(TaichiHandler.SOURCE, sourceN.getSource_path()));

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, Long.toString(sourceN.getSource_time() * 1000));

		final TaichiMessageFactory factory = (TaichiMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNull(theMessage);

		sourceN.delete();
	}

	@Test
	public void getSourceMessageWithLastTimeUTest() {
		final long sourceUpdateTime = System.currentTimeMillis() / 1000;
		final Source sourceS = new SourceMock(8206, "taichi.slow", 40, System.currentTimeMillis());
		final long theLastTime = sourceUpdateTime * 1000;
		final Subscription theSubscription = new SubscriptionMock(1, Factories.APPLICATION.findByName("net.violet.taichi"), getKowalskyObject());
		theSubscription.setSettings(Collections.singletonMap(TaichiHandler.SOURCE, sourceS.getSource_path()));

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		theScheduling.createSetting(AmbiantHandler.LAST_TIME, "0");

		final TaichiMessageFactory factory = (TaichiMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());
		final MessageDraft theMessage = factory.getSourceMessage(theScheduling, theLastTime);

		Assert.assertNotNull(theMessage);
		Assert.assertTrue(theMessage.isSourceModeUpdate());
		Assert.assertEquals(new Integer((int) sourceS.getSource_val()), theMessage.getSources().get(Integer.toString(factory.getSource().getId())));
		Assert.assertEquals(Constantes.QUEUE_TTL_SOURCES, theMessage.getTTLInSecond());
		final SubscriptionSchedulingSettingsData theLastTimeSetting = SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(theScheduling, AmbiantHandler.LAST_TIME);
		Assert.assertNotNull(theLastTimeSetting);
		Assert.assertEquals(theLastTime, Long.parseLong(theLastTimeSetting.getValue()));

		sourceS.delete();
	}

}
