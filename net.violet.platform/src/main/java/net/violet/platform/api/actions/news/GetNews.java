package net.violet.platform.api.actions.news;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.news.NewsInformationMap;
import net.violet.platform.datamodel.Product;
import net.violet.platform.dataobjects.NewsData;
import net.violet.platform.dataobjects.ProductData;
import net.violet.platform.dataobjects.SiteLangData;

public class GetNews extends AbstractAction {

	//private static final Logger LOGGER = Logger.getLogger(GetNews.class);

	public static String LANGUAGE_PARAM = "language";
	public static String PRODUCT_PARAM = "product";
	public static String SKIP_PARAM = "skip";
	public static String COUNT_PARAM = "count";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchProductException {

		final int skip = inParam.getInt(GetNews.SKIP_PARAM, 0);
		final int count = inParam.getInt(GetNews.COUNT_PARAM, 10);

		final String theLangCode = inParam.getString(GetNews.LANGUAGE_PARAM, true);
		final String theProductName = inParam.getString(GetNews.PRODUCT_PARAM, true);

		final ProductData theProductData = ProductData.findByName(theProductName);
		final SiteLangData theLang = SiteLangData.getByISOCode(theLangCode);
		final Product theProduct = theProductData.getReference();

		if (theProduct == null) {
			throw new NoSuchProductException(APIErrorMessage.NO_SUCH_PRODUCT, theProductName);
		}

		final List<NewsInformationMap> newsFromProductAndLanguage = new LinkedList<NewsInformationMap>();

		for (final NewsData aNewsData : NewsData.findByLangAndProduct(theLang.getReference(), theProduct, skip, count)) {
			newsFromProductAndLanguage.add(new NewsInformationMap(inParam.getCaller(), aNewsData));
		}

		return newsFromProductAndLanguage;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

}
