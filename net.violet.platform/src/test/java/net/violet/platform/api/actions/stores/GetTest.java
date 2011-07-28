package net.violet.platform.api.actions.stores;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchStoreException;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.Store;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.mock.StoreCityMock;
import net.violet.platform.datamodel.mock.StoreMock;
import net.violet.platform.dataobjects.StoreData;

import org.junit.Assert;
import org.junit.Test;

public class GetTest extends AbstractTestBase {

	@Test
	public void testGetStore() throws APIException {

		final StoreCity city1 = new StoreCityMock(1, "Paris", "FR", "France");
		final Store mock1 = new StoreMock(1L, "Store Mock 1", city1, "url");
		final Store mock2 = new StoreMock(2L, "Store Mock 2", city1, "url2");
		final StoreData storedata1 = StoreData.getData(mock1);

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, storedata1.getApiId(caller));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof Map);

		mock1.delete();
		mock2.delete();
	}

	@Test(expected = InvalidParameterException.class)
	public void testParameter() throws APIException {

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("hardware", Hardware.HARDWARE.DALDAL);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = NoSuchStoreException.class)
	public void testNoSuchStore() throws APIException {

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "cc");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}
}
