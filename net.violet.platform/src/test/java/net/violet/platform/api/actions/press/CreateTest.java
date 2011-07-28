package net.violet.platform.api.actions.press;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.press.PressInformationMap;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.ProductMock;
import net.violet.platform.dataobjects.FilesData;

import org.junit.Assert;
import org.junit.Test;

public class CreateTest extends AbstractTestBase {

	@Test
	public void createPress() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Za Jonc");
		final Files thePicture = new FilesMock("../mock/file.jpg", MimeType.MIME_TYPES.JPEG);
		final FilesData thePictureData = FilesData.getData(thePicture);

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(Create.LANGUAGE_PARAM, frLang.getIsoCode());
		theParams.put(Create.PRODUCT_PARAM, doramichanProduct.getName());
		theParams.put(Create.URL_PARAM, "inURL mock");
		theParams.put(Create.TITLE_PARAM, "inTitle mock");
		theParams.put(Create.ABSTRACT_PARAM, "inAbstract mock");
		final APICaller inCaller = getPublicApplicationAPICaller();
		theParams.put(Create.PICTURE_PARAM, thePictureData.getApiId(inCaller));
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object thePressRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(thePressRecovered);
		Assert.assertTrue(thePressRecovered instanceof PressInformationMap);
		final PressInformationMap thePressInformation = (PressInformationMap) thePressRecovered;
		Assert.assertEquals(frLang.getTitle(), thePressInformation.get(PressInformationMap.LANGUAGE));
		Assert.assertEquals(doramichanProduct.getName(), thePressInformation.get(PressInformationMap.PRODUCT));

		Assert.assertEquals("inTitle mock", thePressInformation.get(PressInformationMap.TITLE));
		Assert.assertEquals("inURL mock", thePressInformation.get(PressInformationMap.URL));
		Assert.assertEquals("inAbstract mock", thePressInformation.get(PressInformationMap.ABSTRACT));
		Assert.assertEquals(thePicture.getPath(), thePressInformation.get(PressInformationMap.PICTURE));

	}

	@Test(expected = NoSuchFileException.class)
	public void createPicture() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Za Jonc");

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(Create.LANGUAGE_PARAM, frLang.getIsoCode());
		theParams.put(Create.PRODUCT_PARAM, doramichanProduct.getName());
		theParams.put(Create.URL_PARAM, "inURL mock");
		theParams.put(Create.TITLE_PARAM, "inTitle mock");
		theParams.put(Create.ABSTRACT_PARAM, "inAbstract mock");
		theParams.put(Create.PICTURE_PARAM, "bad keys");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameter() throws APIException {

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", "JPFR");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = MissingParameterException.class)
	public void testMissingParameter() throws APIException {
		final Lang frLang = getSiteFrLang();
		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(Create.LANGUAGE_PARAM, frLang.getIsoCode());
		theParams.put(Create.PRODUCT_PARAM, "inexisting product !");
		theParams.put(Create.URL_PARAM, "inURL mock");
		theParams.put(Create.TITLE_PARAM, "inTitle Mock");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = NoSuchProductException.class)
	public void testNoSuchProduct() throws APIException {
		final Lang frLang = getSiteFrLang();

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(Create.LANGUAGE_PARAM, frLang.getIsoCode());
		theParams.put(Create.PRODUCT_PARAM, "inexisting product");
		theParams.put(Create.URL_PARAM, "inURL mock");
		theParams.put(Create.TITLE_PARAM, "inTitle Mock");
		theParams.put(Create.ABSTRACT_PARAM, "inAbstract Mock");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
	}

}
