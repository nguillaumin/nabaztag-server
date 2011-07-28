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
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Press;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.mock.PressMock;
import net.violet.platform.datamodel.mock.ProductMock;

import org.junit.Assert;
import org.junit.Test;

public class GetPressClipTest extends AbstractTestBase {

	@Test
	public void getPress() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Doramichan to Anpainman");
		final List<Press> pressList = new ArrayList<Press>();
		pressList.add(new PressMock(1L, frLang, "Cellular Automata", "dorami press", null, "http://mathworld.wolfram.com/CellularAutomaton.html", doramichanProduct));
		pressList.add(new PressMock(2L, frLang, "Fractals", "des milliards des mondes", null, "http://www.syti.net/Fractals.html", doramichanProduct));

		final Action theAction = new GetPressClip();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", frLang.getIsoCode());
		theParams.put("product", doramichanProduct.getName());
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theNewsRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theNewsRecovered);
		Assert.assertTrue(theNewsRecovered instanceof List);
		final List theNewsAsList = (List) theNewsRecovered;
		Assert.assertEquals(pressList.size(), theNewsAsList.size());
	}

	@Test(expected = InvalidParameterException.class)
	public void testParameter() throws APIException {

		final Action theAction = new GetPressClip();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", "JPFR");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = NoSuchProductException.class)
	public void testNoSuchProduct() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Action theAction = new GetPressClip();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", frLang.getIsoCode());
		theParams.put("product", "inexisting product !");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}
}
