package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.SourceMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class AirHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		new SourceMock(1, "air.paris.today", 0);
		new SourceMock(2, "air.us.Dallas.today", 0);
		return ApplicationData.getData(Application.NativeApplication.AIR.getApplication());
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = MissingSettingException.class)
	public void missingLanguageParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(AirHandler.SOURCE_SETTING, "source"));
	}

	@Test(expected = MissingSettingException.class)
	public void missingSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(AirHandler.LANGUAGE_SETTING, "language"));
	}

	@Test(expected = InvalidSettingException.class)
	public void nonMatchingSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		params.put(AirHandler.SOURCE_SETTING, "sillySource");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		params.put(AirHandler.SOURCE_SETTING, "air.se.stockholm");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidLanguageParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(AirHandler.LANGUAGE_SETTING, "sk-SK");
		params.put(AirHandler.SOURCE_SETTING, "air.paris.today");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		params.put(AirHandler.SOURCE_SETTING, "air.paris.today");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(AirHandler.SOURCE_SETTING) && settings.containsKey(AirHandler.LANGUAGE_SETTING));
		Assert.assertEquals("air.paris.today", settings.get(AirHandler.SOURCE_SETTING));
		Assert.assertEquals("fr-FR", settings.get(AirHandler.LANGUAGE_SETTING));
	}

	@Test
	public void updateLanguageTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		params.put(AirHandler.SOURCE_SETTING, "air.paris.today");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(AirHandler.SOURCE_SETTING) && settings.containsKey(AirHandler.LANGUAGE_SETTING));
		Assert.assertEquals("air.paris.today", settings.get(AirHandler.SOURCE_SETTING));
		Assert.assertEquals("fr-FR", settings.get(AirHandler.LANGUAGE_SETTING));

		params.clear();
		params.put(AirHandler.LANGUAGE_SETTING, "en-US");
		params.put(AirHandler.SOURCE_SETTING, "air.paris.today");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(AirHandler.SOURCE_SETTING) && settings.containsKey(AirHandler.LANGUAGE_SETTING));
		Assert.assertEquals("air.paris.today", settings.get(AirHandler.SOURCE_SETTING));
		Assert.assertEquals("en-US", settings.get(AirHandler.LANGUAGE_SETTING));
	}

	@Test
	public void updateSourceTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		params.put(AirHandler.SOURCE_SETTING, "air.paris.today");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(AirHandler.SOURCE_SETTING) && settings.containsKey(AirHandler.LANGUAGE_SETTING));
		Assert.assertEquals("air.paris.today", settings.get(AirHandler.SOURCE_SETTING));
		Assert.assertEquals("fr-FR", settings.get(AirHandler.LANGUAGE_SETTING));

		params.clear();
		params.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		params.put(AirHandler.SOURCE_SETTING, "air.us.Dallas.today");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(AirHandler.SOURCE_SETTING) && settings.containsKey(AirHandler.LANGUAGE_SETTING));
		Assert.assertEquals("air.us.Dallas.today", settings.get(AirHandler.SOURCE_SETTING));
		Assert.assertEquals("fr-FR", settings.get(AirHandler.LANGUAGE_SETTING));
	}

	@Test
	public void updateAllParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(AirHandler.LANGUAGE_SETTING, "fr-FR");
		params.put(AirHandler.SOURCE_SETTING, "air.paris.today");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(AirHandler.SOURCE_SETTING) && settings.containsKey(AirHandler.LANGUAGE_SETTING));
		Assert.assertEquals("air.paris.today", settings.get(AirHandler.SOURCE_SETTING));
		Assert.assertEquals("fr-FR", settings.get(AirHandler.LANGUAGE_SETTING));

		params.clear();
		params.put(AirHandler.LANGUAGE_SETTING, "en-US");
		params.put(AirHandler.SOURCE_SETTING, "air.us.Dallas.today");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(AirHandler.SOURCE_SETTING) && settings.containsKey(AirHandler.LANGUAGE_SETTING));
		Assert.assertEquals("air.us.Dallas.today", settings.get(AirHandler.SOURCE_SETTING));
		Assert.assertEquals("en-US", settings.get(AirHandler.LANGUAGE_SETTING));
	}

}
