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

public class DeleteTest extends AbstractTestBase {

	@Test
	public void deleteTest() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Doramichan et Anpainman");
		final List<Press> pressList = new ArrayList<Press>();
		final Files inFile = new FilesMock("../mocks/file1", MimeType.MIME_TYPES.JPEG);
		pressList.add(new PressMock(1L, frLang, "Cellular Automata", "dorami press", inFile, "http://mathworld.wolfram.com/CellularAutomaton.html", doramichanProduct));
		pressList.add(new PressMock(2L, frLang, "Fractals", "des milliards des mondes", inFile, "http://www.syti.net/Fractals.html", doramichanProduct));
		final PressData pressdata1 = PressData.getData(pressList.get(0));

		Assert.assertEquals(2, pressList.size());

		final Action theAction = new Delete();
		final APICaller inCaller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, pressdata1.getApiId(inCaller));
		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object thePress = theAction.processRequest(theActionParam);
		Assert.assertNull(thePress);
	}

	@Test(expected = NoSuchPressException.class)
	public void testNoSuchPress() throws APIException {

		final Action theAction = new Delete();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", "Fractals");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = InvalidParameterException.class)
	public void testParameter() throws APIException {

		final Action theAction = new Delete();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

}
