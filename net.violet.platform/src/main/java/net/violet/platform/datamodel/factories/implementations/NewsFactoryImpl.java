package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.News;
import net.violet.platform.datamodel.NewsImpl;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.NewsFactory;

import org.apache.log4j.Logger;

public class NewsFactoryImpl extends RecordFactoryImpl<News, NewsImpl> implements NewsFactory {

	private static final Logger LOGGER = Logger.getLogger(NewsFactoryImpl.class);

	NewsFactoryImpl() {
		super(NewsImpl.SPECIFICATION);
	}

	public List<News> getNewsByLangAndProduct(Lang inLang, Product inProduct, int inSkip, int inCount) {
		final String[] inJoinTables = { "lang", "product" };
		final String inOrderBy = "date_pub DESC";
		final List<Object> values = Arrays.asList(new Object[] { inLang.getId(), inProduct.getId() });
		final String condition = "news.lang = lang.lang_id AND news.product = product.id AND lang.lang_id = ? AND product.id = ?";
		return findAll(inJoinTables, condition, values, inOrderBy, inSkip, inCount);
	}

	public News create(Lang inLang, String inTitle, String inAbstract, Files inSmall, Files inBig, String inIntro, String inBody, Date inDatePub, Date inDateExp, Product inProduct) {
		try {
			return new NewsImpl(inLang, inTitle, inAbstract, inSmall, inBig, inIntro, inBody, inDatePub, inDateExp, inProduct);
		} catch (final SQLException e) {
			NewsFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public boolean usesFiles(Files inFile) {
		//TODO Uncomment when the table will used
		//return count(null, " picture_small = ?  or picture_big = ?", Arrays.asList(new Object[] { inFile.getId(), inFile.getId() }), null) > 0;
		return false;
	}
}
