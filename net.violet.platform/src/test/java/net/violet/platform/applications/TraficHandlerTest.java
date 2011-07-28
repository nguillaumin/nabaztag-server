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

public class TraficHandlerTest extends MockTestBase {

	private ApplicationData getApplication() {
		new SourceMock(1, "trafic.auteuil.bercy", 0);
		new SourceMock(2, "trafic.maillot.italie", 0);
		return ApplicationData.getData(Application.NativeApplication.TRAFIC.getApplication());
	}

	@Test(expected = MissingSettingException.class)
	public void missingParamsTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> emptyMap());
	}

	@Test(expected = MissingSettingException.class)
	public void missingStartParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(TraficHandler.END, "end"));
	}

	@Test(expected = MissingSettingException.class)
	public void missingEndParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());

		ApplicationHandlerManager.createSubscription(getApplication(), object, Collections.<String, Object> singletonMap(TraficHandler.START, "start"));
	}

	@Test(expected = InvalidSettingException.class)
	public void startEqualsEndTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TraficHandler.END, "toto");
		params.put(TraficHandler.START, "toto");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test(expected = InvalidSettingException.class)
	public void invalidSourceParamTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TraficHandler.END, "toto");
		params.put(TraficHandler.START, "tutu");
		ApplicationHandlerManager.createSubscription(getApplication(), object, params);
	}

	@Test
	public void validSubscriptionTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TraficHandler.END, "bercy");
		params.put(TraficHandler.START, "auteuil");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		final Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(TraficHandler.SOURCE) && settings.containsKey(TraficHandler.END) && settings.containsKey(TraficHandler.START));
		Assert.assertEquals("bercy", settings.get(TraficHandler.END));
		Assert.assertEquals("auteuil", settings.get(TraficHandler.START));
		Assert.assertEquals("trafic.auteuil.bercy", settings.get(TraficHandler.SOURCE));
	}

	@Test
	public void updateTest() throws InvalidSettingException, MissingSettingException {
		final VObjectData object = VObjectData.getData(getKowalskyObject());
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(TraficHandler.END, "bercy");
		params.put(TraficHandler.START, "auteuil");
		final SubscriptionData theSubscription = ApplicationHandlerManager.createSubscription(getApplication(), object, params);
		Assert.assertTrue(theSubscription.isValid());
		Map<String, Object> settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(TraficHandler.SOURCE) && settings.containsKey(TraficHandler.END) && settings.containsKey(TraficHandler.START));
		Assert.assertEquals("bercy", settings.get(TraficHandler.END));
		Assert.assertEquals("auteuil", settings.get(TraficHandler.START));
		Assert.assertEquals("trafic.auteuil.bercy", settings.get(TraficHandler.SOURCE));

		params.clear();
		params.put(TraficHandler.END, "italie");
		params.put(TraficHandler.START, "maillot");
		ApplicationHandlerManager.updateSubscription(theSubscription, params);
		settings = theSubscription.getSettings();
		Assert.assertEquals(3, settings.size());
		Assert.assertTrue(settings.containsKey(TraficHandler.SOURCE) && settings.containsKey(TraficHandler.END) && settings.containsKey(TraficHandler.START));
		Assert.assertEquals("italie", settings.get(TraficHandler.END));
		Assert.assertEquals("maillot", settings.get(TraficHandler.START));
		Assert.assertEquals("trafic.maillot.italie", settings.get(TraficHandler.SOURCE));
	}

}
