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

public class WeatherHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		new SourceMock(1, "Nmeteo.SUEDE.Gothenburg.weather", 0);
		new SourceMock(2, "Nmeteo.SUEDE.Stockholm.weather", 0);
		return ApplicationData.getData(Application.NativeApplication.WEATHER.getApplication());
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = MissingSettingException.class)
	public void missingSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(WeatherHandler.UNIT, "unit"));
	}

	@Test(expected = MissingSettingException.class)
	public void missingUnitParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(WeatherHandler.SOURCE, "source"));
	}

	@Test(expected = InvalidSettingException.class)
	public void nonMatchingSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(WeatherHandler.UNIT, "1");
		params.put(WeatherHandler.SOURCE, "sillySource");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(WeatherHandler.UNIT, "1");
		params.put(WeatherHandler.SOURCE, "Nmeteo.COUNTRY.city.weather");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidUnitParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Stockholm.weather");
		params.put(WeatherHandler.UNIT, "4");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void nonNumericUnitParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Stockholm.weather");
		params.put(WeatherHandler.UNIT, "unit");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Stockholm.weather");
		params.put(WeatherHandler.UNIT, "1");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(WeatherHandler.SOURCE) && settings.containsKey(WeatherHandler.UNIT));
		Assert.assertEquals("Nmeteo.SUEDE.Stockholm.weather", settings.get(WeatherHandler.SOURCE));
		Assert.assertEquals("1", settings.get(WeatherHandler.UNIT));
	}

	@Test
	public void updateUnitTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(WeatherHandler.UNIT, "2");
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Stockholm.weather");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(WeatherHandler.SOURCE) && settings.containsKey(WeatherHandler.UNIT));
		Assert.assertEquals("Nmeteo.SUEDE.Stockholm.weather", settings.get(WeatherHandler.SOURCE));
		Assert.assertEquals("2", settings.get(WeatherHandler.UNIT));

		params.clear();
		params.put(WeatherHandler.UNIT, "1");
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Stockholm.weather");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(WeatherHandler.SOURCE) && settings.containsKey(WeatherHandler.UNIT));
		Assert.assertEquals("Nmeteo.SUEDE.Stockholm.weather", settings.get(WeatherHandler.SOURCE));
		Assert.assertEquals("1", settings.get(WeatherHandler.UNIT));
	}

	@Test
	public void updateSourceTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(WeatherHandler.UNIT, "1");
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Stockholm.weather");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(WeatherHandler.SOURCE) && settings.containsKey(WeatherHandler.UNIT));
		Assert.assertEquals("Nmeteo.SUEDE.Stockholm.weather", settings.get(WeatherHandler.SOURCE));
		Assert.assertEquals("1", settings.get(WeatherHandler.UNIT));

		params.clear();
		params.put(WeatherHandler.UNIT, "1");
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Gothenburg.weather");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(WeatherHandler.SOURCE) && settings.containsKey(WeatherHandler.UNIT));
		Assert.assertEquals("Nmeteo.SUEDE.Gothenburg.weather", settings.get(WeatherHandler.SOURCE));
		Assert.assertEquals("1", settings.get(WeatherHandler.UNIT));
	}

	@Test
	public void updateAllParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(WeatherHandler.UNIT, "1");
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Gothenburg.weather");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(WeatherHandler.SOURCE) && settings.containsKey(WeatherHandler.UNIT));
		Assert.assertEquals("Nmeteo.SUEDE.Gothenburg.weather", settings.get(WeatherHandler.SOURCE));
		Assert.assertEquals("1", settings.get(WeatherHandler.UNIT));

		params.clear();
		params.put(WeatherHandler.UNIT, "2");
		params.put(WeatherHandler.LANGUAGE, "fr-FR");
		params.put(WeatherHandler.SOURCE, "Nmeteo.SUEDE.Stockholm.weather");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(WeatherHandler.SOURCE) && settings.containsKey(WeatherHandler.UNIT));
		Assert.assertEquals("Nmeteo.SUEDE.Stockholm.weather", settings.get(WeatherHandler.SOURCE));
		Assert.assertEquals("2", settings.get(WeatherHandler.UNIT));
	}

}
