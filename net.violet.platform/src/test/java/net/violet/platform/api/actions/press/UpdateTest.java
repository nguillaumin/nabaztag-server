package net.violet.platform.api.actions.press;

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
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.press.PressInformationMap;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Press;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.PressMock;
import net.violet.platform.datamodel.mock.ProductMock;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.PressData;

import org.junit.Assert;
import org.junit.Test;

public class UpdateTest extends AbstractTestBase {

	@Test
	public void updatePress() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Doramichan et Anpainman");
		final List<Press> pressList = new ArrayList<Press>();
		final Files inFile = new FilesMock("../mocks/file1", MimeType.MIME_TYPES.JPEG);
		pressList.add(new PressMock(1L, frLang, "Cellular Automata", "dorami press", inFile, "http://mathworld.wolfram.com/CellularAutomaton.html", doramichanProduct));
		pressList.add(new PressMock(2L, frLang, "Fractals", "des milliards des mondes", inFile, "http://www.syti.net/Fractals.html", doramichanProduct));
		final PressData pressdata1 = PressData.getData(pressList.get(0));
		final Files thePicture = new FilesMock("../mock/file.jpg", MimeType.MIME_TYPES.JPEG);
		final FilesData thePictureData = FilesData.getData(thePicture);

		final Action theAction = new Update();
		final APICaller inCaller = getPublicApplicationAPICaller();

		final HashMap<String, Object> thePressMap = new HashMap<String, Object>();
		thePressMap.put(PressInformationMap.LANGUAGE, frLang.getIsoCode());
		thePressMap.put(PressInformationMap.PRODUCT, doramichanProduct.getName());
		thePressMap.put(PressInformationMap.TITLE, "inTitle updated");
		thePressMap.put(PressInformationMap.URL, "http://www.updated.fr");
		thePressMap.put(PressInformationMap.ABSTRACT, "inAbstract updated");
		thePressMap.put(PressInformationMap.PICTURE, thePictureData.getApiId(inCaller));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, pressdata1.getApiId(inCaller));
		theParams.put(Update.PRESS_PARAM, thePressMap);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object thePressRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(thePressRecovered);
		Assert.assertTrue(thePressRecovered instanceof PressInformationMap);
		final PressInformationMap thePressInformation = (PressInformationMap) thePressRecovered;
		Assert.assertEquals(frLang.getTitle(), thePressInformation.get(PressInformationMap.LANGUAGE));
		Assert.assertEquals(doramichanProduct.getName(), thePressInformation.get(PressInformationMap.PRODUCT));

		Assert.assertEquals("inTitle updated", thePressInformation.get(PressInformationMap.TITLE));
		Assert.assertEquals("inAbstract updated", thePressInformation.get(PressInformationMap.ABSTRACT));
		Assert.assertEquals("http://www.updated.fr", thePressInformation.get(PressInformationMap.URL));
		Assert.assertEquals(thePicture.getPath(), thePressInformation.get(PressInformationMap.PICTURE));

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

	@Test(expected = NoSuchProductException.class)
	public void testNoSuchProduct() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Doramichan et Anpainman");
		final List<Press> pressList = new ArrayList<Press>();
		final Files inFile = new FilesMock("../mocks/file1", MimeType.MIME_TYPES.JPEG);
		pressList.add(new PressMock(1L, frLang, "Cellular Automata", "dorami press", inFile, "http://mathworld.wolfram.com/CellularAutomaton.html", doramichanProduct));
		pressList.add(new PressMock(2L, frLang, "Fractals", "des milliards des mondes", inFile, "http://www.syti.net/Fractals.html", doramichanProduct));
		final PressData pressdata1 = PressData.getData(pressList.get(0));

		final Action theAction = new Update();
		final APICaller inCaller = getPublicApplicationAPICaller();

		final HashMap<String, Object> thePressMap = new HashMap<String, Object>();
		thePressMap.put(PressInformationMap.LANGUAGE, frLang.getIsoCode());
		thePressMap.put(PressInformationMap.PRODUCT, "inexisting product");
		thePressMap.put(PressInformationMap.TITLE, "inTitle updated");
		thePressMap.put(PressInformationMap.URL, "http://www.updated.fr");
		thePressMap.put(PressInformationMap.ABSTRACT, "inAbstract updated");
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, pressdata1.getApiId(inCaller));
		theParams.put(Update.PRESS_PARAM, thePressMap);
		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
	}

}
