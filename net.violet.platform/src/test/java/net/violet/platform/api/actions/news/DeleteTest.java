package net.violet.platform.api.actions.news;

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
import net.violet.platform.api.exceptions.NoSuchNewsException;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.News;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.mock.NewsMock;
import net.violet.platform.datamodel.mock.ProductMock;
import net.violet.platform.dataobjects.NewsData;

import org.junit.Assert;
import org.junit.Test;

public class DeleteTest extends AbstractTestBase {

	@Test
	public void deleteTest() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Doramichan et Anpainman");
		final List<News> newsList = new ArrayList<News>();
		newsList.add(new NewsMock(1L, frLang, doramichanProduct, "ドラえもんの妹さん !"));
		newsList.add(new NewsMock(2L, frLang, doramichanProduct, "アンパンマン、優しい君は !"));
		final NewsData newsdata1 = NewsData.getData(newsList.get(0));

		Assert.assertEquals(2, newsList.size());

		final Action theAction = new Delete();
		final APICaller inCaller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, newsdata1.getApiId(inCaller));
		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object theNews = theAction.processRequest(theActionParam);
		Assert.assertNull(theNews);
	}

	@Test(expected = NoSuchNewsException.class)
	public void testNoSuchNews() throws APIException {

		final Action theAction = new Delete();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", "JPFR");
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
