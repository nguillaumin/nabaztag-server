package net.violet.platform.applications;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

public class ClockHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		return ApplicationData.getData(Application.NativeApplication.CLOCK.getApplication());
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = MissingSettingException.class)
	public void missingLanguagesParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(ClockHandler.TYPES, "type"));
	}

	@Test(expected = MissingSettingException.class)
	public void missingTypesParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(ClockHandler.LANGUAGES, "languages"));
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidLanguagesParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(ClockHandler.LANGUAGES, "toto");
		params.put(ClockHandler.TYPES, "1");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidTypesParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(ClockHandler.LANGUAGES, "fr");
		params.put(ClockHandler.TYPES, "18");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void nonNumericTypesParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(ClockHandler.LANGUAGES, "fr");
		params.put(ClockHandler.TYPES, "type");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSubscriptionWithSingletonsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(ClockHandler.LANGUAGES, "fr");
		params.put(ClockHandler.TYPES, "1");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(ClockHandler.LANGUAGES) && settings.containsKey(ClockHandler.TYPES));
		Assert.assertEquals(Collections.singletonList("fr"), settings.get(ClockHandler.LANGUAGES));
		Assert.assertEquals(Collections.singletonList("1"), settings.get(ClockHandler.TYPES));
	}

	@Test
	public void validSubscriptionWithListsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();

		final List<String> langs = Arrays.asList("fr", "en");
		final List<String> types = Arrays.asList("1", "3");

		params.put(ClockHandler.LANGUAGES, langs);
		params.put(ClockHandler.TYPES, types);
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(ClockHandler.LANGUAGES) && settings.containsKey(ClockHandler.TYPES));
		Assert.assertEquals(langs, settings.get(ClockHandler.LANGUAGES));
		Assert.assertEquals(types, settings.get(ClockHandler.TYPES));
	}

	@Test
	public void updateLanguagesTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();

		final List<String> langs = Arrays.asList("fr", "en");
		final List<String> types = Arrays.asList("1", "3");

		params.put(ClockHandler.LANGUAGES, langs);
		params.put(ClockHandler.TYPES, types);
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(ClockHandler.LANGUAGES) && settings.containsKey(ClockHandler.TYPES));
		Assert.assertEquals(langs, settings.get(ClockHandler.LANGUAGES));
		Assert.assertEquals(types, settings.get(ClockHandler.TYPES));

		params.clear();
		params.put(ClockHandler.LANGUAGES, "fr");
		params.put(ClockHandler.TYPES, types);
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(ClockHandler.LANGUAGES) && settings.containsKey(ClockHandler.TYPES));
		Assert.assertEquals(Arrays.asList("fr"), settings.get(ClockHandler.LANGUAGES));
		Assert.assertEquals(types, settings.get(ClockHandler.TYPES));
	}

	@Test
	public void updateTypesTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();

		final List<String> langs = Arrays.asList("fr", "en");
		final List<String> types = Arrays.asList("1", "3");

		params.put(ClockHandler.LANGUAGES, langs);
		params.put(ClockHandler.TYPES, types);
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(ClockHandler.LANGUAGES) && settings.containsKey(ClockHandler.TYPES));
		Assert.assertEquals(langs, settings.get(ClockHandler.LANGUAGES));
		Assert.assertEquals(types, settings.get(ClockHandler.TYPES));

		params.clear();
		params.put(ClockHandler.LANGUAGES, langs);
		params.put(ClockHandler.TYPES, Arrays.asList("1", "2"));
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(ClockHandler.LANGUAGES) && settings.containsKey(ClockHandler.TYPES));
		Assert.assertEquals(langs, settings.get(ClockHandler.LANGUAGES));
		Assert.assertEquals(Arrays.asList("1", "2"), settings.get(ClockHandler.TYPES));
	}

	@Test
	public void updateAllParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();

		final List<String> langs = Arrays.asList("fr", "en");
		final List<String> types = Arrays.asList("1", "3");

		params.put(ClockHandler.LANGUAGES, langs);
		params.put(ClockHandler.TYPES, types);
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(ClockHandler.LANGUAGES) && settings.containsKey(ClockHandler.TYPES));
		Assert.assertEquals(langs, settings.get(ClockHandler.LANGUAGES));
		Assert.assertEquals(types, settings.get(ClockHandler.TYPES));

		params.clear();
		params.put(ClockHandler.LANGUAGES, Arrays.asList("it", "de"));
		params.put(ClockHandler.TYPES, Arrays.asList("2"));
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(2, settings.size());
		Assert.assertTrue(settings.containsKey(ClockHandler.LANGUAGES) && settings.containsKey(ClockHandler.TYPES));
		Assert.assertEquals(Arrays.asList("it", "de"), settings.get(ClockHandler.LANGUAGES));
		Assert.assertEquals(Arrays.asList("2"), settings.get(ClockHandler.TYPES));
	}

}
