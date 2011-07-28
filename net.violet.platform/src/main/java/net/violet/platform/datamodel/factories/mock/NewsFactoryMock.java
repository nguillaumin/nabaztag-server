package net.violet.platform.datamodel.factories.mock;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.News;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.NewsFactory;
import net.violet.platform.datamodel.mock.NewsMock;

public class NewsFactoryMock extends RecordFactoryMock<News, NewsMock> implements NewsFactory {

	NewsFactoryMock() {
		super(NewsMock.class);
	}

	public List<News> getNewsByLangAndProduct(Lang inLang, Product inProduct, int skip, int count) {
		final List<News> theNewsResult = new LinkedList<News>();
		final int cpt = 0;
		final int stop = skip + count;
		for (final News aPieceOfNews : findAll()) {
			if (aPieceOfNews.getNewsLang().equals(inLang) && aPieceOfNews.getNewsProduct().equals(inProduct) && (cpt >= skip) && (cpt < stop)) {
				theNewsResult.add(aPieceOfNews);
			}
		}
		return theNewsResult;
	}

	public News create(Lang inLang, String inTitle, String inAbstract, Files inSmall, Files inBig, String inIntro, String inBody, Date inDatePub, Date inDateExp, Product inProduct) {

		final NewsMock theNews = new NewsMock(0L, inLang, inTitle, inAbstract, inSmall, inBig, inIntro, inBody, inDatePub, inDateExp, inProduct);
		return theNews;
	}

	public boolean usesFiles(Files inFile) {
		return false;
	}
}
