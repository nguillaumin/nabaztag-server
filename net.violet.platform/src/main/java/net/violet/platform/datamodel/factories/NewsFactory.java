package net.violet.platform.datamodel.factories;

import java.util.Date;
import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.News;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface NewsFactory extends RecordFactory<News>, FilesAccessor {

	List<News> getNewsByLangAndProduct(Lang lang, Product product, int skip, int count);

	News create(Lang inLang, String inTitle, String inAbstract, Files inSmall, Files inBig, String inIntro, String inBody, Date inDatePub, Date inDateExp, Product inProduct);

}
