package net.violet.platform.api.actions.stores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.store.StoreInformationMap;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.Store;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.mock.StoreCityMock;
import net.violet.platform.datamodel.mock.StoreMock;

import org.junit.Assert;
import org.junit.Test;

public class GetStoreTest extends AbstractTestBase {

	@Test
	public void testGetStore() throws APIException {

		final StoreCity city1 = new StoreCityMock(1, "Paris", "FR", "France");
		final StoreCity city2 = new StoreCityMock(2, "Cracovie", "PL", "Pologne");
		final StoreCity city3 = new StoreCityMock(3, "Lausanne", "CH", "Suisse");

		final List<Store> StoresList = new ArrayList<Store>();
		StoresList.add(new StoreMock(1L, "Store Mock 1", city1, "url"));
		StoresList.add(new StoreMock(2L, "Store Mock 2", city1, "url"));
		StoresList.add(new StoreMock(3L, "Store Mock 3", city2, "url"));
		StoresList.add(new StoreMock(4L, "Store Mock 4", city1, "url"));
		StoresList.add(new StoreMock(5L, "Store Mock 5", city3, "url"));

		final Action theAction = new GetStores();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "FR");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List<StoreInformationMap> theResultAsList = (List<StoreInformationMap>) theResult;
		Assert.assertEquals(3, theResultAsList.size());
		for (final Store inName : StoresList) {
			theResultAsList.contains(inName.getName());
		}
		for (final StoreInformationMap inResult : theResultAsList) {
			Assert.assertEquals("Paris", inResult.get(StoreInformationMap.CITY));
			Assert.assertEquals("France", inResult.get(StoreInformationMap.COUNTRY));
			Assert.assertEquals("mock/empty/picture", inResult.get(StoreInformationMap.PICTURE));
			Assert.assertEquals("online", inResult.get(StoreInformationMap.TYPE));
			Assert.assertEquals(net.violet.common.StringShop.EMPTY_STRING, inResult.get(StoreInformationMap.ADDRESS));
			Assert.assertEquals(net.violet.common.StringShop.EMPTY_STRING, inResult.get(StoreInformationMap.ZIPCODE));
			Assert.assertEquals("normal", inResult.get(StoreInformationMap.STATUS));
			Assert.assertEquals("url", inResult.get(StoreInformationMap.URL));
			Assert.assertEquals("1", inResult.get(StoreInformationMap.RANK));
			Assert.assertEquals(net.violet.common.StringShop.EMPTY_STRING, inResult.get(StoreInformationMap.COMMENT));
		}

		for (final Store inStore : StoresList) {
			inStore.delete();
		}
	}

	@Test(expected = InvalidParameterException.class)
	public void testParameter() throws APIException {

		final Action theAction = new GetStores();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, Hardware.HARDWARE.DALDAL);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = InvalidParameterException.class)
	public void testCountryCode() throws APIException {

		final Action theAction = new GetStores();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "cc");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}
}
