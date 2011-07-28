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
import net.violet.platform.api.exceptions.NoSuchPressException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Press;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.PressMock;
import net.violet.platform.datamodel.mock.ProductMock;
import net.violet.platform.dataobjects.PressData;

import org.junit.Assert;
import org.junit.Test;

public class GetTest extends AbstractTestBase {

	@Test
	public void getPress() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Doramichan et Anpainman");
		final List<Press> pressList = new ArrayList<Press>();
		final Files inFile = new FilesMock("../mocks/file1", MimeType.MIME_TYPES.JPEG);
		pressList.add(new PressMock(1L, frLang, "Cellular Automata", "dorami press", inFile, "http://mathworld.wolfram.com/CellularAutomaton.html", doramichanProduct));
		pressList.add(new PressMock(2L, frLang, "Fractals", "des milliards des mondes", inFile, "http://www.syti.net/Fractals.html", doramichanProduct));
		final PressData pressdata1 = PressData.getData(pressList.get(0));

		final Action theAction = new Get();
		final APICaller theCaller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, pressdata1.getApiId(theCaller));
		final ActionParam theActionParam = new ActionParam(theCaller, theParams);

		final Object theNewsRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theNewsRecovered);
		Assert.assertTrue(theNewsRecovered instanceof Map);
		final Map theNewsAsMap = (Map) theNewsRecovered;
		Assert.assertEquals(pressdata1.getApiId(theCaller), theNewsAsMap.get(ActionParam.MAIN_PARAM_KEY));
	}

	@Test(expected = InvalidParameterException.class)
	public void testParameter() throws APIException {

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", "JPFR");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = NoSuchPressException.class)
	public void testNoSuchPress() throws APIException {

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "press");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}
}
