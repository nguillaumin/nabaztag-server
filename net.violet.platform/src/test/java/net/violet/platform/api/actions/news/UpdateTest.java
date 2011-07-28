package net.violet.platform.api.actions.news;

import java.util.ArrayList;
import java.util.Date;
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
import net.violet.platform.api.maps.news.NewsInformationMap;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.News;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.NewsMock;
import net.violet.platform.datamodel.mock.ProductMock;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.NewsData;

import org.junit.Assert;
import org.junit.Test;

public class UpdateTest extends AbstractTestBase {

	@Test
	public void updateNews() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Za Jonc");
		final List<News> newsList = new ArrayList<News>();
		newsList.add(new NewsMock(1L, frLang, doramichanProduct, "ドラえもんの妹さん !"));
		final NewsData newsdata1 = NewsData.getData(newsList.get(0));
		final Files theBig = new FilesMock("../updated/bigPicture.jpg", MimeType.MIME_TYPES.JPEG);
		final FilesData theBigData = FilesData.getData(theBig);
		final Files theSmall = new FilesMock("../updated/smallPicture.jpg", MimeType.MIME_TYPES.JPEG);
		final FilesData theSmallData = FilesData.getData(theSmall);

		final Action theAction = new Update();
		final APICaller theCaller = getPublicApplicationAPICaller();

		final HashMap<String, Object> theNewsMap = new HashMap<String, Object>();
		theNewsMap.put(NewsInformationMap.LANGUAGE, frLang.getIsoCode());
		theNewsMap.put(NewsInformationMap.PRODUCT, doramichanProduct.getName());
		theNewsMap.put(NewsInformationMap.BODY, "inBody updated");
		theNewsMap.put(NewsInformationMap.TITLE, "inTitle updated");
		theNewsMap.put(NewsInformationMap.ABSTRACT, "inAbstract updated");
		theNewsMap.put(NewsInformationMap.INTRO, "inIntro updated");
		theNewsMap.put(NewsInformationMap.PICTURE_BIG, theBigData.getApiId(theCaller));
		theNewsMap.put(NewsInformationMap.PICTURE_SMALL, theSmallData.getApiId(theCaller));
		final Date date = new Date();
		theNewsMap.put(NewsInformationMap.DATE_EXP, date);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, newsdata1.getApiId(theCaller));
		theParams.put(Update.NEWS_PARAM, theNewsMap);
		final ActionParam theActionParam = new ActionParam(theCaller, theParams);

		final Object theNewsRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theNewsRecovered);
		Assert.assertTrue(theNewsRecovered instanceof NewsInformationMap);
		final NewsInformationMap theNewsInformation = (NewsInformationMap) theNewsRecovered;
		Assert.assertEquals(frLang.getTitle(), theNewsInformation.get(NewsInformationMap.LANGUAGE));
		Assert.assertEquals(doramichanProduct.getName(), theNewsInformation.get(NewsInformationMap.PRODUCT));
		Assert.assertEquals("inTitle updated", theNewsInformation.get(NewsInformationMap.TITLE));
		Assert.assertEquals("inBody updated", theNewsInformation.get(NewsInformationMap.BODY));
		Assert.assertEquals("inAbstract updated", theNewsInformation.get(NewsInformationMap.ABSTRACT));
		Assert.assertEquals("inIntro updated", theNewsInformation.get(NewsInformationMap.INTRO));
		//	Assert.assertEquals(date, theNewsInformation.get(NewsInformationMap.DATE_PUB));
		Assert.assertEquals(date, theNewsInformation.get(NewsInformationMap.DATE_EXP));
		Assert.assertEquals(theBig.getPath(), theNewsInformation.get(NewsInformationMap.PICTURE_BIG));
		Assert.assertEquals(theSmall.getPath(), theNewsInformation.get(NewsInformationMap.PICTURE_SMALL));

	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidParameter() throws APIException {

		final Action theAction = new Update();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("news", "JPFR");
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
		final Product doramichanProduct = new ProductMock(1, "Za Jonc");
		final List<News> newsList = new ArrayList<News>();
		newsList.add(new NewsMock(1L, frLang, doramichanProduct, "ドラえもんの妹さん !"));
		final NewsData newsdata1 = NewsData.getData(newsList.get(0));

		final Action theAction = new Update();
		final APICaller inCaller = getPublicApplicationAPICaller();
		final HashMap<String, Object> theNewsMap = new HashMap<String, Object>();
		theNewsMap.put(NewsInformationMap.LANGUAGE, frLang.getIsoCode());
		theNewsMap.put(NewsInformationMap.PRODUCT, "inexisting product");
		theNewsMap.put(NewsInformationMap.BODY, "inBody updated");
		theNewsMap.put(NewsInformationMap.TITLE, "inTitle updated");
		theNewsMap.put(NewsInformationMap.ABSTRACT, "inAbstract updated");
		theNewsMap.put(NewsInformationMap.INTRO, "inIntro updated");
		final Date date = new Date();
		theNewsMap.put(NewsInformationMap.DATE_EXP, date);

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, newsdata1.getApiId(inCaller));
		theParams.put(Update.NEWS_PARAM, theNewsMap);

		final ActionParam theActionParam = new ActionParam(inCaller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

}
