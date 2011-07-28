package net.violet.platform.api.actions.news;

import java.util.Date;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.news.NewsInformationMap;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.NewsData;
import net.violet.platform.dataobjects.ProductData;
import net.violet.platform.dataobjects.SiteLangData;

public class Create extends AbstractAction {

	//private static final Logger LOGGER = Logger.getLogger(Create.class);

	public static final String TITLE_PARAM = "title";
	public static final String BODY_PARAM = "body";
	public static final String ABSTRACT_PARAM = "abstract";
	public static final String INTRO_PARAM = "intro";
	public static final String DATE_PUB_PARAM = "date_pub";
	public static final String DATE_EXP_PARAM = "date_exp";
	public static final String LANGUAGE_PARAM = "language";
	public static final String PRODUCT_PARAM = "product";
	public static final String SMALL_PARAM = "picture_small";
	public static final String BIG_PARAM = "picture_big";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchProductException, NoSuchFileException {

		final String theTitle = inParam.getString(Create.TITLE_PARAM, true);
		final String theBody = inParam.getString(Create.BODY_PARAM, true);
		final String theAbstract = inParam.getString(Create.ABSTRACT_PARAM, true);
		final String theIntro = inParam.getString(Create.INTRO_PARAM, true);
		final Date theDatePub = inParam.getDate(Create.DATE_PUB_PARAM, false);
		final Date theDateExp = inParam.getDate(Create.DATE_EXP_PARAM, true);
		final String theLangCode = inParam.getString(Create.LANGUAGE_PARAM, true);
		final SiteLangData theLang = SiteLangData.getByISOCode(theLangCode);
		final String theProductName = inParam.getString(Create.PRODUCT_PARAM, true);
		final String theBigAPIId = inParam.getString(Create.BIG_PARAM, false);
		final String theSmallAPIId = inParam.getString(Create.SMALL_PARAM, false);

		final ProductData theProduct = ProductData.findByName(theProductName);
		if (theProduct.getReference() == null) {
			throw new NoSuchProductException(APIErrorMessage.NO_SUCH_PRODUCT, theProductName);
		}
		FilesData theBig = null;
		if (theBigAPIId != null) {
			theBig = FilesData.getFilesData(theBigAPIId, inParam.getCallerAPIKey());
		}
		FilesData theSmall = null;
		if (theSmallAPIId != null) {
			theSmall = FilesData.getFilesData(theSmallAPIId, inParam.getCallerAPIKey());
		}

		final NewsData theNews = NewsData.create(theLang, theTitle, theAbstract, theBig, theSmall, theIntro, theBody, theDatePub, theDateExp, theProduct);

		return new NewsInformationMap(inParam.getCaller(), theNews);
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

}
