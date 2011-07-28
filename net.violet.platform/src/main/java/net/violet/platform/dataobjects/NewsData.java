package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.News;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class NewsData extends APIData<News> {

	private static final Logger LOGGER = Logger.getLogger(NewsData.class);

	public static NewsData getData(News inNews) {
		try {
			return RecordData.getData(inNews, NewsData.class, News.class);
		} catch (final InstantiationException e) {
			NewsData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			NewsData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			NewsData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			NewsData.LOGGER.fatal(e, e);
		}

		return null;
	}

	public static List<NewsData> findByLangAndProduct(Lang inLang, Product inProduct, int skip, int count) {
		return NewsData.generateList(Factories.NEWS.getNewsByLangAndProduct(inLang, inProduct, skip, count));
	}

	private static List<NewsData> generateList(List<News> inList) {
		final List<NewsData> result = new ArrayList<NewsData>();
		for (final News aNews : inList) {
			result.add(NewsData.getData(aNews));
		}
		return result;
	}

	protected NewsData(News inRecord) {
		super(inRecord);
	}

	public String getTitle() {
		final News thePieceOfNews = getRecord();
		if ((thePieceOfNews != null) && (thePieceOfNews.getTitle() != null)) {
			return thePieceOfNews.getTitle();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getProduct() {
		final News thePieceOfNews = getRecord();
		if ((thePieceOfNews != null) && (thePieceOfNews.getNewsProduct() != null)) {
			return thePieceOfNews.getNewsProduct().getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getLanguage() {
		final News thePieceOfNews = getRecord();
		if ((thePieceOfNews != null) && (thePieceOfNews.getNewsLang() != null)) {
			return thePieceOfNews.getNewsLang().getTitle();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getPictureBig() {
		final News thePieceOfNews = getRecord();
		if ((thePieceOfNews != null) && (thePieceOfNews.getPicture_b() != null)) {
			return thePieceOfNews.getPicture_b().getPath();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getPictureSmall() {
		final News thePieceOfNews = getRecord();
		if ((thePieceOfNews != null) && (thePieceOfNews.getPicture_s() != null)) {
			return thePieceOfNews.getPicture_s().getPath();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getIntro() {
		final News thePieceOfNews = getRecord();
		if ((thePieceOfNews != null) && (thePieceOfNews.getIntro() != null)) {
			return thePieceOfNews.getIntro();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getBody() {
		final News thePieceOfNews = getRecord();
		if ((thePieceOfNews != null) && (thePieceOfNews.getBody() != null)) {
			return thePieceOfNews.getBody();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getAbstract() {
		final News thePieceOfNews = getRecord();
		if ((thePieceOfNews != null) && (thePieceOfNews.getAbstract() != null)) {
			return thePieceOfNews.getAbstract();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Timestamp getDatePub() {
		final News thePieceOfNews = getRecord();
		if (thePieceOfNews != null) {
			return thePieceOfNews.getDatePub();
		}
		return null;
	}

	public Timestamp getDateExp() {
		final News thePieceOfNews = getRecord();
		if (thePieceOfNews != null) {
			return thePieceOfNews.getDateExp();
		}
		return null;
	}

	public static NewsData findByAPIId(String inAPIId, String inAPIKey) {
		NewsData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.NEWS, inAPIKey);
		if (theID != 0) {
			final News news = Factories.NEWS.find(theID);
			if (news != null) {
				theResult = NewsData.getData(news);
			}
		}
		return theResult;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.NEWS;
	}

	public static NewsData create(SiteLangData theLang, String inTitle, String inAbstract, FilesData inBigData, FilesData inSmallData, String inIntro, String inBody, Date inDatePub, Date inDateExp, ProductData inProduct) {

		Files inBig = null;
		Files inSmall = null;
		if (inBigData != null) {
			inBig = inBigData.getReference();
		}
		if (inSmallData != null) {
			inSmall = inSmallData.getReference();
		}

		final News theNews = Factories.NEWS.create(theLang.getRecord(), inTitle, inAbstract, inSmall, inBig, inIntro, inBody, inDatePub, inDateExp, inProduct.getRecord());
		if (theNews != null) {
			return NewsData.getData(theNews);
		}
		return NewsData.getData(null);
	}

	public void update(SiteLangData inLang, String inTitle, String inAbstract, FilesData inBigData, FilesData inSmallData, String inIntro, String inBody, Date inDatePub, Date inDateExp, ProductData inProduct) {
		final News theNews = this.getRecord();

		Files inBig = null;
		if (inBigData != null) {
			inBig = inBigData.getReference();
		}
		Files inSmall = null;
		if (inSmallData != null) {
			inSmall = inSmallData.getReference();
		}
		if (theNews != null) {
			theNews.update(inLang.getReference(), inTitle, inAbstract, inBig, inSmall, inIntro, inBody, inDatePub, inDateExp, inProduct.getReference());
		}
	}
}
