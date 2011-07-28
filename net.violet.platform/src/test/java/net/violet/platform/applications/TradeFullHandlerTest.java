package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.SourceMock;
import net.violet.platform.datamodel.mock.TtsVoiceMock;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class TradeFullHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		new SourceMock(1, "money.bel 20", 0);
		new TtsVoiceMock(1L, "voix de virgine", "Virginie", getKowalskyObject().getPreferences().getLangPreferences(), "FR-Anastasie", true, false);
		return ApplicationData.getData(Application.NativeApplication.BOURSE_FULL.getApplication());
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = MissingSettingException.class)
	public void missingCodeAndSourceParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(TradeFullHandler.ALERT_NAME, "alertName"));
	}

	@Test(expected = MissingSettingException.class)
	public void missingCodeAndAlertParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(TradeFullHandler.SOURCE, "source"));
	}

	@Test(expected = MissingSettingException.class)
	public void missingSourceAndAlertParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(TradeFullHandler.STOCK_CODE, "code"));
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidCodeTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.STOCK_CODE, "code");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void noMatchingSourceTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.SOURCE, "sillySource");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.SOURCE, "money.toto");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSourceSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.SOURCE, "money.bel 20");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFullHandler.SOURCE) && settings.containsKey(TradeFullHandler.ALERT_NAME) && settings.containsKey(TradeFullHandler.MUSIC));
		Assert.assertEquals("money.bel 20", settings.get(TradeFullHandler.SOURCE));
		Assert.assertEquals("alert", settings.get(TradeFullHandler.ALERT_NAME));
		final int musicId = Integer.parseInt(settings.get(TradeFullHandler.MUSIC).toString());
		final Music theMusic = Factories.MUSIC.find(musicId);
		Assert.assertNotNull(theMusic);
		Assert.assertNotNull(theMusic.getFile());
	}

	@Test
	public void validCodeSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.STOCK_CODE, "GOOG");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(4, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFullHandler.SOURCE) && settings.containsKey(TradeFullHandler.ALERT_NAME) && settings.containsKey(TradeFullHandler.MUSIC));
		Assert.assertTrue(settings.containsKey(TradeFullHandler.STOCK_CODE));
		Assert.assertEquals("$" + theSubscription.getId() + ".money", settings.get(TradeFullHandler.SOURCE));
		Assert.assertEquals("alert", settings.get(TradeFullHandler.ALERT_NAME));
		Assert.assertEquals("GOOG", settings.get(TradeFullHandler.STOCK_CODE));
		final int musicId = Integer.parseInt(settings.get(TradeFullHandler.MUSIC).toString());
		final Music theMusic = Factories.MUSIC.find(musicId);
		Assert.assertNotNull(theMusic);
		Assert.assertNotNull(theMusic.getFile());
		Assert.assertNotNull(Factories.SOURCE.findByPath(settings.get(TradeFullHandler.SOURCE).toString()) != null);
	}

	@Test
	public void updateFromCodeToSourceTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.STOCK_CODE, "GOOG");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(4, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFullHandler.SOURCE) && settings.containsKey(TradeFullHandler.ALERT_NAME) && settings.containsKey(TradeFullHandler.MUSIC));
		Assert.assertTrue(settings.containsKey(TradeFullHandler.STOCK_CODE));
		final String oldSource = "$" + theSubscription.getId() + ".money";
		Assert.assertEquals(oldSource, settings.get(TradeFullHandler.SOURCE));
		Assert.assertEquals("alert", settings.get(TradeFullHandler.ALERT_NAME));
		Assert.assertEquals("GOOG", settings.get(TradeFullHandler.STOCK_CODE));
		final int oldMusicId = Integer.parseInt(settings.get(TradeFullHandler.MUSIC).toString());
		final Music oldMusic = Factories.MUSIC.find(oldMusicId);
		Assert.assertNotNull(oldMusic);
		Assert.assertNotNull(oldMusic.getFile());
		Assert.assertNotNull(Factories.SOURCE.findByPath(oldSource));

		params.clear();
		params.put(TradeFullHandler.ALERT_NAME, "alert2");
		params.put(TradeFullHandler.SOURCE, "money.bel 20");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		Assert.assertTrue(theSubscription.isValid());
		settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFullHandler.SOURCE) && settings.containsKey(TradeFullHandler.ALERT_NAME) && settings.containsKey(TradeFullHandler.MUSIC));
		Assert.assertEquals("money.bel 20", settings.get(TradeFullHandler.SOURCE));
		Assert.assertEquals("alert2", settings.get(TradeFullHandler.ALERT_NAME));
		final int musicId = Integer.parseInt(settings.get(TradeFullHandler.MUSIC).toString());
		final Music theMusic = Factories.MUSIC.find(musicId);
		Assert.assertNotNull(theMusic);
		Assert.assertNotNull(theMusic.getFile());

		Assert.assertNull(Factories.SOURCE.findByPath(oldSource));
		Assert.assertNull(Factories.MUSIC.find(oldMusicId));
	}

	@Test
	public void updateWithSameAlertTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.STOCK_CODE, "GOOG");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(4, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFullHandler.SOURCE) && settings.containsKey(TradeFullHandler.ALERT_NAME) && settings.containsKey(TradeFullHandler.MUSIC));
		Assert.assertTrue(settings.containsKey(TradeFullHandler.STOCK_CODE));
		final String oldSource = "$" + theSubscription.getId() + ".money";
		Assert.assertEquals(oldSource, settings.get(TradeFullHandler.SOURCE));
		Assert.assertEquals("alert", settings.get(TradeFullHandler.ALERT_NAME));
		Assert.assertEquals("GOOG", settings.get(TradeFullHandler.STOCK_CODE));
		final int oldMusicId = Integer.parseInt(settings.get(TradeFullHandler.MUSIC).toString());
		final Music oldMusic = Factories.MUSIC.find(oldMusicId);
		Assert.assertNotNull(oldMusic);
		Assert.assertNotNull(oldMusic.getFile());
		Assert.assertNotNull(Factories.SOURCE.findByPath(oldSource));

		params.clear();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.SOURCE, "money.bel 20");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		Assert.assertTrue(theSubscription.isValid());
		settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFullHandler.SOURCE) && settings.containsKey(TradeFullHandler.ALERT_NAME) && settings.containsKey(TradeFullHandler.MUSIC));
		Assert.assertEquals("money.bel 20", settings.get(TradeFullHandler.SOURCE));
		Assert.assertEquals("alert", settings.get(TradeFullHandler.ALERT_NAME));
		final int musicId = Integer.parseInt(settings.get(TradeFullHandler.MUSIC).toString());
		Assert.assertEquals(musicId, oldMusicId);
	}

	@Test
	public void updateFromCodeToSameCodeTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.STOCK_CODE, "GOOG");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(4, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFullHandler.SOURCE) && settings.containsKey(TradeFullHandler.ALERT_NAME) && settings.containsKey(TradeFullHandler.MUSIC));
		Assert.assertTrue(settings.containsKey(TradeFullHandler.STOCK_CODE));
		final String oldSourcePath = "$" + theSubscription.getId() + ".money";
		Assert.assertEquals(oldSourcePath, settings.get(TradeFullHandler.SOURCE));
		Assert.assertEquals("alert", settings.get(TradeFullHandler.ALERT_NAME));
		Assert.assertEquals("GOOG", settings.get(TradeFullHandler.STOCK_CODE));
		final int oldMusicId = Integer.parseInt(settings.get(TradeFullHandler.MUSIC).toString());
		final Music oldMusic = Factories.MUSIC.find(oldMusicId);
		Assert.assertNotNull(oldMusic);
		Assert.assertNotNull(oldMusic.getFile());

		final Source oldSource = Factories.SOURCE.findByPath(oldSourcePath);
		Assert.assertNotNull(oldSource);

		params.clear();
		params.put(TradeFullHandler.ALERT_NAME, "alert");
		params.put(TradeFullHandler.STOCK_CODE, "GOOG");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		Assert.assertTrue(theSubscription.isValid());
		settings = theSubscription.getSettings();
		Assert.assertEquals(4, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFullHandler.SOURCE) && settings.containsKey(TradeFullHandler.ALERT_NAME) && settings.containsKey(TradeFullHandler.MUSIC));
		Assert.assertEquals(oldSourcePath, settings.get(TradeFullHandler.SOURCE));
		Assert.assertEquals("alert", settings.get(TradeFullHandler.ALERT_NAME));
		final int musicId = Integer.parseInt(settings.get(TradeFullHandler.MUSIC).toString());
		Assert.assertEquals(musicId, oldMusicId);
		final Source newSource = Factories.SOURCE.findByPath(settings.get(TradeFullHandler.SOURCE).toString());
		Assert.assertEquals(oldSource, newSource);
	}

}
