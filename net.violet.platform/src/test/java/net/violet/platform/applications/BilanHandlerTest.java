package net.violet.platform.applications;

import java.util.Collections;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class BilanHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		return ApplicationData.getData(Application.NativeApplication.BILAN.getApplication());
	}

	@Test
	public void validSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(BilanHandler.NBR));
		Assert.assertEquals("0", settings.get(BilanHandler.NBR));
	}

	@Test
	public void updateTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final SubscriptionData theSubscription = SubscriptionData.create(getApplication(), object, Collections.<String, Object> singletonMap(BilanHandler.NBR, "12"));

		ApplicationHandlerManager.updateSubscription(theSubscription, Collections.<String, Object> emptyMap());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(BilanHandler.NBR));
		Assert.assertEquals("12", settings.get(BilanHandler.NBR));
	}

}
