package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
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

public class TaichiHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		return ApplicationData.getData(Application.NativeApplication.TAICHI.getApplication());
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = InvalidSettingException.class)
	public void nonMatchingSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TaichiHandler.SOURCE, "sillySource");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void nonMatchingSourceAgainTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TaichiHandler.SOURCE, "taichi.extrem");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TaichiHandler.SOURCE, "taichi.slow");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(TaichiHandler.SOURCE));
		Assert.assertEquals("taichi.slow", settings.get(TaichiHandler.SOURCE));
	}

	@Test
	public void updateTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TaichiHandler.SOURCE, "taichi.slow");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(TaichiHandler.SOURCE));
		Assert.assertEquals("taichi.slow", settings.get(TaichiHandler.SOURCE));

		params.clear();
		params.put(TaichiHandler.SOURCE, "taichi.fast");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(TaichiHandler.SOURCE));
		Assert.assertEquals("taichi.fast", settings.get(TaichiHandler.SOURCE));
	}

}
