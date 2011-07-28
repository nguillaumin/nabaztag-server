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

public class TradeFreeHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		new SourceMock(1, "money.bel 20", 0);
		new SourceMock(2, "money.aex 25", 0);
		return ApplicationData.getData(Application.NativeApplication.BOURSE_FREE.getApplication());
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
		params.put(TradeFreeHandler.SOURCE, "sillySource");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFreeHandler.SOURCE, "money.toto");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFreeHandler.SOURCE, "money.bel 20");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFreeHandler.SOURCE));
		Assert.assertEquals("money.bel 20", settings.get(TradeFreeHandler.SOURCE));
	}

	@Test
	public void updateTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TradeFreeHandler.SOURCE, "money.bel 20");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFreeHandler.SOURCE));
		Assert.assertEquals("money.bel 20", settings.get(TradeFreeHandler.SOURCE));

		params.clear();
		params.put(TradeFreeHandler.SOURCE, "money.aex 25");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(1, settings.size());
		Assert.assertTrue(settings.containsKey(TradeFreeHandler.SOURCE));
		Assert.assertEquals("money.aex 25", settings.get(TradeFreeHandler.SOURCE));
	}

}
