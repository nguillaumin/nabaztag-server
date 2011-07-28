package net.violet.platform.message.application.factories;

import java.util.Collections;

import net.violet.platform.applications.BilanHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.junit.Assert;
import org.junit.Test;

public class BilanMessageFactoryTest extends MockTestBase {

	@Test
	public void getABilanTest() {

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(Application.NativeApplication.BILAN.getApplication(), getKowalskyObject());

		theSubscription.setSettings(Collections.singletonMap(BilanHandler.NBR, "3"));

		final BilanMessageFactory factory = (BilanMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, null, null) {

			@Override
			public void runWhenSuccessful() {
			}

		}).get(0);

		Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
		Assert.assertEquals(BilanMessageFactory.TITLE, message.getTitle());
		Assert.assertEquals(Palette.RANDOM, message.getColorPal());
		Assert.assertEquals(theSubscriptionData, message.getSubscription());
		Assert.assertEquals(MessageSignature.BILAN_SIGNATURE, message.getSignature());
		Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
		Assert.assertFalse(message.isStream());
		final Files[] theFiles = message.getBody();
		Assert.assertEquals(1, theFiles.length);
		Assert.assertEquals("broadcast/broad/config/resume/fr/comment/2.mp3", theFiles[0].getPath());

	}

	@Test
	public void getAnOtherBilanTest() {

		final Subscription theSubscription = Factories.SUBSCRIPTION.create(Application.NativeApplication.BILAN.getApplication(), getKowalskyObject());

		theSubscription.setSettings(Collections.singletonMap(BilanHandler.NBR, "10"));

		getKowalskyObject().getPreferences().setLangPreferences(getUsLang());

		final BilanMessageFactory factory = (BilanMessageFactory) AbstractMessageFactory.getFactoryByApplication(theSubscription.getApplication());

		final SubscriptionData theSubscriptionData = SubscriptionData.getData(theSubscription);
		final SubscriptionSchedulingData theScheduling = SubscriptionSchedulingData.create(theSubscriptionData, SchedulingType.SCHEDULING_TYPE.Ambiant);
		final Message2Send message = factory.getMessage(new MessageProcessUnit(theScheduling, null, null) {

			@Override
			public void runWhenSuccessful() {
			}

		}).get(0);

		Assert.assertEquals(Constantes.QUEUE_TTL_SERVICE, message.getTTL());
		Assert.assertEquals(BilanMessageFactory.TITLE, message.getTitle());
		Assert.assertEquals(Palette.RANDOM, message.getColorPal());
		Assert.assertEquals(theSubscriptionData, message.getSubscription());
		Assert.assertEquals(MessageSignature.BILAN_SIGNATURE, message.getSignature());
		Assert.assertEquals(theSubscription.getObject(), message.getRecipient());
		Assert.assertEquals(JabberMessageFactory.IDLE_MODE, message.getMode());
		Assert.assertFalse(message.isStream());
		final Files[] theFiles = message.getBody();
		Assert.assertEquals(1, theFiles.length);
		Assert.assertEquals("broadcast/broad/config/resume/us/comment/3.mp3", theFiles[0].getPath());

		getKowalskyObject().getPreferences().setLangPreferences(getFrLang());

	}
}
