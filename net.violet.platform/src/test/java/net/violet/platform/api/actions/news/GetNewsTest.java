package net.violet.platform.api.actions.news;

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
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.mock.NewsMock;
import net.violet.platform.datamodel.mock.ProductMock;

import org.junit.Assert;
import org.junit.Test;

public class GetNewsTest extends AbstractTestBase {

	@Test
	public void getNews() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Doramichan et Anpainman");
		final NewsMock mock1 = new NewsMock(1L, frLang, doramichanProduct, "ドラえもんの妹さん !");
		final NewsMock mock2 = new NewsMock(2L, frLang, doramichanProduct, "アンパンマン、優しい君は !");

		final Action theAction = new GetNews();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", frLang.getIsoCode());
		theParams.put("product", doramichanProduct.getName());
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theNewsRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theNewsRecovered);
		Assert.assertTrue(theNewsRecovered instanceof List);
		final List theNewsAsList = (List) theNewsRecovered;
		Assert.assertEquals(2, theNewsAsList.size());
		mock1.delete();
		mock2.delete();
	}

	@Test(expected = InvalidParameterException.class)
	public void testParameter() throws APIException {

		final Action theAction = new GetNews();
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
		final Action theAction = new GetNews();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", frLang.getIsoCode());
		theParams.put("product", "inexisting product !");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}
}
