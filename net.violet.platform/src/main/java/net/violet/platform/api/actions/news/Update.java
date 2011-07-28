package net.violet.platform.api.actions.news;

import java.util.Date;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchNewsException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.news.NewsInformationMap;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.NewsData;
import net.violet.platform.dataobjects.ProductData;
import net.violet.platform.dataobjects.SiteLangData;

public class Update extends AbstractAction {

	//private static final Logger LOGGER = Logger.getLogger(Create.class);
	public static final String NEWS_PARAM = "news";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchProductException, NoSuchFileException, NoSuchNewsException {

		final String theAPIId = inParam.getMainParamAsString();
		final NewsData theNews = NewsData.findByAPIId(theAPIId, inParam.getCallerAPIKey());

		if (theNews == null) {
			throw new NoSuchNewsException();
		}

		final PojoMap theNewsMap = inParam.getPojoMap(Update.NEWS_PARAM, true);

		final String theTitle = theNewsMap.getString(NewsInformationMap.TITLE, theNews.getTitle());
		final String theBody = theNewsMap.getString(NewsInformationMap.BODY, theNews.getBody());
		final String theAbstract = theNewsMap.getString(NewsInformationMap.ABSTRACT, theNews.getAbstract());
		final String theIntro = theNewsMap.getString(NewsInformationMap.INTRO, theNews.getIntro());
		final Date theDatePub = theNewsMap.getDate(NewsInformationMap.DATE_PUB, theNews.getDatePub());
		final Date theDateExp = theNewsMap.getDate(NewsInformationMap.DATE_EXP, theNews.getDateExp());
		final String theLangCode = theNewsMap.getString(NewsInformationMap.LANGUAGE, theNews.getLanguage());
		final SiteLangData theLang = SiteLangData.getByISOCode(theLangCode);
		final String theProductName = theNewsMap.getString(NewsInformationMap.PRODUCT, theNews.getProduct());
		final String theBigAPIId = theNewsMap.getString(NewsInformationMap.PICTURE_BIG, theNews.getPictureBig());
		final String theSmallAPIId = theNewsMap.getString(NewsInformationMap.PICTURE_SMALL, theNews.getPictureSmall());

		final ProductData theProduct = ProductData.findByName(theProductName);
		if (theProduct.getReference() == null) {
			throw new NoSuchProductException(APIErrorMessage.NO_SUCH_PRODUCT, theProductName);
		}
		FilesData theBig = null;
		if (theBigAPIId != null) {
			theBig = FilesData.getFilesData(theBigAPIId, inParam.getCallerAPIKey());
			if (theBig == null) {
				throw new NoSuchFileException();
			}
		}
		FilesData theSmall = null;
		if (theSmallAPIId != null) {
			theSmall = FilesData.getFilesData(theSmallAPIId, inParam.getCallerAPIKey());
			if (theSmall == null) {
				throw new NoSuchFileException();
			}
		}

		theNews.update(theLang, theTitle, theAbstract, theBig, theSmall, theIntro, theBody, theDatePub, theDateExp, theProduct);

		return new NewsInformationMap(inParam.getCaller(), theNews);
	}

	public long getExpirationTime() {
		return 0L;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

}
