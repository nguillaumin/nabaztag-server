package net.violet.platform.api.actions.stores;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidDataException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.maps.store.StoreInformationMap;
import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Store;
import net.violet.platform.datamodel.StoreCity;
import net.violet.platform.datamodel.Store.STORE_STATUS;
import net.violet.platform.datamodel.Store.STORE_TYPE;
import net.violet.platform.datamodel.mock.CountryMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.StoreCityMock;
import net.violet.platform.datamodel.mock.StoreMock;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.StoreData;

import org.junit.Assert;
import org.junit.Test;

public class UpdateTest extends AbstractTestBase {

	@Test
	public void updateStore() throws APIException {

		final Files thePicture = new FilesMock("../mocks/file1", MimeType.MIME_TYPES.JPEG);

		final Files theNewPicture = new FilesMock("/new/picture", MimeType.MIME_TYPES.JPEG);
		final FilesData theNewData = FilesData.getData(theNewPicture);

		final Country France = new CountryMock(77L, "FR", "France");
		final Country England = new CountryMock(78L, "GB", "England");
		final StoreCity Paris = new StoreCityMock(1L, "Paris", France.getCode());
		final StoreCity London = new StoreCityMock(2L, "London", England.getCode());
		final Store theStore = new StoreMock(1L, "Pixmania", STORE_TYPE.physical, thePicture, "15, rue de la Paix", "75016", Paris, STORE_STATUS.prefered, "http://www.mock.fr", 2L, "update test comment");
		final StoreData storedata1 = StoreData.getData(theStore);

		Assert.assertEquals(Paris, theStore.getStoreCity());
		Assert.assertEquals(France.getName(), storedata1.getCountryName());

		final Action theAction = new Update();
		final APICaller inCaller = getPublicApplicationAPICaller();

		final HashMap<String, Object> theStoreMap = new HashMap<String, Object>();
		theStoreMap.put(StoreInformationMap.NAME, "FNAC");
		theStoreMap.put(StoreInformationMap.TYPE, STORE_TYPE.online.name());
		theStoreMap.put(StoreInformationMap.PICTURE, theNewData.getApiId(inCaller));//c'est aussi un String...
		theStoreMap.put(StoreInformationMap.ADDRESS, "17, Penny Lane");
		theStoreMap.put(StoreInformationMap.ZIPCODE, "1010");
		theStoreMap.put(StoreInformationMap.CITY, "London");
		theStoreMap.put(StoreInformationMap.COUNTRY, "GB");
		theStoreMap.put(StoreInformationMap.ZIPCODE, "1010");

		theStoreMap.put(London.getName(), StoreInformationMap.CITY);
		theStoreMap.put(storedata1.getCountryName(), StoreInformationMap.COUNTRY);

		theStoreMap.put(StoreInformationMap.STATUS, STORE_STATUS.network.name());
		theStoreMap.put(StoreInformationMap.URL, "URL updated");
		theStoreMap.put(StoreInformationMap.RANK, "12");
		theStoreMap.put(StoreInformationMap.COMMENT, "comment updated");

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, storedata1.getApiId(inCaller));
		theParams.put(Update.STORE_PARAM, theStoreMap);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object theStoreRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theStoreRecovered);
		Assert.assertTrue(theStoreRecovered instanceof StoreInformationMap);
		final StoreInformationMap theStoreInformation = (StoreInformationMap) theStoreRecovered;

		Assert.assertEquals("FNAC", theStoreInformation.get(StoreInformationMap.NAME));
		Assert.assertEquals(STORE_TYPE.online.name(), theStoreInformation.get(StoreInformationMap.TYPE));
		Assert.assertEquals(theNewData.getPath(), theStoreInformation.get(StoreInformationMap.PICTURE));
		Assert.assertEquals("1010", theStoreInformation.get(StoreInformationMap.ZIPCODE));

		Assert.assertEquals("London", theStoreInformation.get(StoreInformationMap.CITY));
		Assert.assertEquals("England", theStoreInformation.get(StoreInformationMap.COUNTRY));

		Assert.assertEquals(STORE_STATUS.network.name(), theStoreInformation.get(StoreInformationMap.STATUS));
		Assert.assertEquals("URL updated", theStoreInformation.get(StoreInformationMap.URL));
		Assert.assertEquals("12", theStoreInformation.get(StoreInformationMap.RANK));
		Assert.assertEquals("comment updated", theStoreInformation.get(StoreInformationMap.COMMENT));

	}

	@Test
	public void testEmptyFields() throws APIException {

		final Files inPicture = new FilesMock("../mocks/file", MimeType.MIME_TYPES.JPEG);
		final StoreCity inCity = new StoreCityMock(1L, "Paris", "FR");
		final Store theStore = new StoreMock(1L, "Pixmania", STORE_TYPE.physical, inPicture, "15, rue de la Paix", "75016", inCity, STORE_STATUS.prefered, "http://www.mock.fr", 2L, "update test comment");
		Assert.assertEquals(inPicture.getPath(), theStore.getPicture().getPath());
		final StoreData storedata1 = StoreData.getData(theStore);

		final Action theAction = new Update();
		final APICaller inCaller = getPublicApplicationAPICaller();

		final HashMap<String, Object> theStoreMap = new HashMap<String, Object>();
		theStoreMap.put(StoreInformationMap.NAME, "FNAC");
		theStoreMap.put(StoreInformationMap.ADDRESS, "17, Penny Lane");

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, storedata1.getApiId(inCaller));
		theParams.put(Update.STORE_PARAM, theStoreMap);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object theStoreRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theStoreRecovered);
		Assert.assertTrue(theStoreRecovered instanceof StoreInformationMap);
		final StoreInformationMap theStoreInformation = (StoreInformationMap) theStoreRecovered;

		Assert.assertEquals("FNAC", theStoreInformation.get(StoreInformationMap.NAME));
		Assert.assertEquals("17, Penny Lane", theStoreInformation.get(StoreInformationMap.ADDRESS));
		Assert.assertEquals(STORE_TYPE.physical.name(), theStoreInformation.get(StoreInformationMap.TYPE));
		Assert.assertEquals(inPicture.getPath(), theStoreInformation.get(StoreInformationMap.PICTURE));

	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameter() throws APIException {

		final Action theAction = new Update();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", "JPFR");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = MissingParameterException.class)
	public void testMissingParameter() throws APIException {

		final Action theAction = new Update();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = InvalidDataException.class)
	public void testCitySansCountry() throws APIException {

		final Files inPicture = new FilesMock("../mocks/file", MimeType.MIME_TYPES.JPEG);
		final StoreCity inCity = new StoreCityMock(1L, "Paris", "FR");
		final Store theStore = new StoreMock(1L, "Pixmania", STORE_TYPE.physical, inPicture, "15, rue de la Paix", "75016", inCity, STORE_STATUS.prefered, "http://www.mock.fr", 2L, "update test comment");
		Assert.assertEquals(inPicture.getPath(), theStore.getPicture().getPath());
		final StoreData storedata1 = StoreData.getData(theStore);

		final Action theAction = new Update();
		final APICaller inCaller = getPublicApplicationAPICaller();

		final HashMap<String, Object> theStoreMap = new HashMap<String, Object>();
		theStoreMap.put(StoreInformationMap.CITY, "Metropolis");

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, storedata1.getApiId(inCaller));
		theParams.put(Update.STORE_PARAM, theStoreMap);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		theAction.processRequest(theActionParam);

	}

}
