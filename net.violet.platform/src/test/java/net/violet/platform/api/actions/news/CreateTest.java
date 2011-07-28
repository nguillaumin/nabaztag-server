package net.violet.platform.api.actions.news;

import java.util.Date;
import java.util.HashMap;
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
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.ProductMock;
import net.violet.platform.dataobjects.FilesData;

import org.junit.Assert;
import org.junit.Test;

public class CreateTest extends AbstractTestBase {

	@Test
	public void createNews() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Product doramichanProduct = new ProductMock(1, "Za Jonc");
		final Files theBig = new FilesMock("../mock/bigPicture.jpg", MimeType.MIME_TYPES.JPEG);
		final FilesData theBigData = FilesData.getData(theBig);
		final Files theSmall = new FilesMock("../mock/.jpg", MimeType.MIME_TYPES.JPEG);
		final FilesData theSmallData = FilesData.getData(theSmall);

		final Action theAction = new Create();
		final APICaller theCaller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(Create.LANGUAGE_PARAM, frLang.getIsoCode());
		theParams.put(Create.PRODUCT_PARAM, doramichanProduct.getName());
		theParams.put(Create.BODY_PARAM, "inBody mock");
		theParams.put(Create.TITLE_PARAM, "inTitle mock");

		theParams.put(Create.ABSTRACT_PARAM, "inAbstract mock");
		theParams.put(Create.INTRO_PARAM, "inIntro mock");
		final Date date = new Date();
		theParams.put(Create.DATE_EXP_PARAM, date);
		theParams.put(Create.BIG_PARAM, theBigData.getApiId(theCaller));
		theParams.put(Create.SMALL_PARAM, theSmallData.getApiId(theCaller));
		final ActionParam theActionParam = new ActionParam(theCaller, theParams);

		final Object theNewsRecovered = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theNewsRecovered);
		Assert.assertTrue(theNewsRecovered instanceof NewsInformationMap);
		final NewsInformationMap theNewsInformation = (NewsInformationMap) theNewsRecovered;
		Assert.assertEquals(frLang.getTitle(), theNewsInformation.get(NewsInformationMap.LANGUAGE));
		Assert.assertEquals(doramichanProduct.getName(), theNewsInformation.get(NewsInformationMap.PRODUCT));

		Assert.assertEquals("inTitle mock", theNewsInformation.get(NewsInformationMap.TITLE));
		Assert.assertEquals("inBody mock", theNewsInformation.get(NewsInformationMap.BODY));
		Assert.assertEquals("inAbstract mock", theNewsInformation.get(NewsInformationMap.ABSTRACT));
		Assert.assertEquals("inIntro mock", theNewsInformation.get(NewsInformationMap.INTRO));
		//	Assert.assertEquals(date, theNewsInformation.get(NewsInformationMap.DATE_PUB));
		Assert.assertEquals(date, theNewsInformation.get(NewsInformationMap.DATE_EXP));
		Assert.assertEquals(theBig.getPath(), theNewsInformation.get(NewsInformationMap.PICTURE_BIG));
		Assert.assertEquals(theSmall.getPath(), theNewsInformation.get(NewsInformationMap.PICTURE_SMALL));

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
		theParams.put(Create.BODY_PARAM, "inBody mock");
		theParams.put(Create.TITLE_PARAM, "title 1");
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
		theParams.put(Create.PRODUCT_PARAM, "inexisting product !");
		theParams.put(Create.BODY_PARAM, "inBody mock");
		theParams.put(Create.ABSTRACT_PARAM, "inAbstract");
		theParams.put(Create.DATE_EXP_PARAM, new Date());
		theParams.put(Create.INTRO_PARAM, "inIntro mock");
		theParams.put(Create.TITLE_PARAM, "inTitle");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

}
