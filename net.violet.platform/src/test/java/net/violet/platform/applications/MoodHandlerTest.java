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

public class MoodHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		return ApplicationData.getData(Application.NativeApplication.MOOD.getApplication());
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidLanguagesParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(MoodHandler.LANGUAGES, "toto");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSubscriptionWithSingletonsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(MoodHandler.LANGUAGES, "fr");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(MoodHandler.LANGUAGES));
		Assert.assertEquals(Collections.singletonList("fr"), settings.get(MoodHandler.LANGUAGES));
	}

	@Test
	public void validSubscriptionWithListsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();

		final List<String> langs = Arrays.asList("fr", "en");

		params.put(MoodHandler.LANGUAGES, langs);
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(MoodHandler.LANGUAGES));
		Assert.assertEquals(langs, settings.get(MoodHandler.LANGUAGES));
	}

	@Test
	public void updateTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();

		final List<String> langs = Arrays.asList("fr", "en");

		params.put(MoodHandler.LANGUAGES, langs);
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(MoodHandler.LANGUAGES));
		Assert.assertEquals(langs, settings.get(MoodHandler.LANGUAGES));

		params.clear();
		params.put(MoodHandler.LANGUAGES, Arrays.asList("it", "de"));
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(MoodHandler.LANGUAGES));
		Assert.assertEquals(Arrays.asList("it", "de"), settings.get(MoodHandler.LANGUAGES));
	}

}
