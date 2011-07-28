package net.violet.platform.applications;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.CipherTools;

import org.junit.Assert;
import org.junit.Test;

public class GmailTwitterHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		final ApplicationData appli = ApplicationData.getData(Application.NativeApplication.GMAIL.getApplication());
		appli.createSetting(ApplicationSetting.FeedHandler.URL, "https://mail.google.com/mail/feed/atom");
		return appli;
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = MissingSettingException.class)
	public void missingLanguageParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(GmailTwitterHandler.LOGIN, "login");
		params.put(GmailTwitterHandler.PASSWORD, "password");

		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = MissingSettingException.class)
	public void missingLoginParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(GmailTwitterHandler.LANGUAGE, "fr");
		params.put(GmailTwitterHandler.PASSWORD, "password");

		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = MissingSettingException.class)
	public void missingPasswordParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(GmailTwitterHandler.LANGUAGE, "fr");
		params.put(GmailTwitterHandler.LOGIN, "login");

		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidLanguageParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(GmailTwitterHandler.LANGUAGE, "invalid");
		params.put(GmailTwitterHandler.LOGIN, "vnabaztag");
		params.put(GmailTwitterHandler.PASSWORD, "violet123");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidAccountParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(GmailTwitterHandler.LANGUAGE, "fr");
		params.put(GmailTwitterHandler.LOGIN, "vnabaztag1");
		params.put(GmailTwitterHandler.PASSWORD, "violet123");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(GmailTwitterHandler.LANGUAGE, "fr");
		params.put(GmailTwitterHandler.LOGIN, "vnabaztag");
		params.put(GmailTwitterHandler.PASSWORD, "violet123");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);

		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(GmailTwitterHandler.LANGUAGE) && settings.containsKey(GmailTwitterHandler.LOGIN) && settings.containsKey(GmailTwitterHandler.PASSWORD));
		Assert.assertEquals("fr", settings.get(GmailTwitterHandler.LANGUAGE));
		Assert.assertEquals("vnabaztag", settings.get(GmailTwitterHandler.LOGIN));
		Assert.assertEquals(CipherTools.cipher("violet123"), settings.get(GmailTwitterHandler.PASSWORD));
	}

	@Test
	public void updateSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put(GmailTwitterHandler.ETAG, "etag");
		settings.put(GmailTwitterHandler.LANGUAGE, "fr");
		settings.put(GmailTwitterHandler.LAST_ENTRY_ID, "lastEntryId");
		settings.put(GmailTwitterHandler.LAST_MODIFIED, "lastModified");
		settings.put(GmailTwitterHandler.LOGIN, "login");
		settings.put(GmailTwitterHandler.PASSWORD, "password");

		final SubscriptionData theSubscription = SubscriptionData.create(getApplication(), object, settings);

		final Map<String, Object> newSettings = new HashMap<String, Object>();
		newSettings.put(GmailTwitterHandler.LOGIN, "vnabaztag");
		newSettings.put(GmailTwitterHandler.PASSWORD, "violet123");
		newSettings.put(GmailTwitterHandler.LANGUAGE, "en");

		ApplicationHandlerManager.updateSubscription(theSubscription, newSettings);

		final Map<String, Object> theSettings = theSubscription.getSettings();
		Assert.assertEquals(6, theSettings.size());
		Assert.assertEquals("en", theSettings.get(GmailTwitterHandler.LANGUAGE));
		Assert.assertEquals("vnabaztag", theSettings.get(GmailTwitterHandler.LOGIN));
		Assert.assertEquals(CipherTools.cipher("violet123"), theSettings.get(GmailTwitterHandler.PASSWORD));
		Assert.assertEquals("etag", theSettings.get(GmailTwitterHandler.ETAG));
		Assert.assertEquals("lastEntryId", theSettings.get(GmailTwitterHandler.LAST_ENTRY_ID));
		Assert.assertEquals("lastModified", theSettings.get(GmailTwitterHandler.LAST_MODIFIED));

	}

}
