package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

import org.junit.Assert;
import org.junit.Test;

public class NewsTest extends DBTest {

	@Test
	public void GetNews() {

		final Lang lang = Factories.LANG.findByIsoCode("de");
		final Product product = Factories.PRODUCT.find(1L);
		final List<News> TheNewsList = Factories.NEWS.getNewsByLangAndProduct(lang, product, 0, 30);

		Assert.assertNotNull(TheNewsList);
		Assert.assertEquals(TheNewsList.size(), 2);
		final List<String> stores = new ArrayList<String>();
		for (final News theNews : TheNewsList) {
			final String aux = theNews.getTitle();
			if ((aux != null) && !StringShop.EMPTY_STRING.equals(aux) && !stores.contains(aux)) {
				stores.add(aux);
			}
			Assert.assertEquals("de", theNews.getNewsLang().getIsoCode());
			Assert.assertEquals("Corporate", theNews.getNewsProduct().getName());
		}
		Assert.assertTrue(stores.contains("Tables"));
		Assert.assertTrue(stores.contains("Chaises"));
	}

	@Test
	public void update() {

		final Lang lang = Factories.LANG.findByIsoCode("de");
		final Lang langIt = Factories.LANG.findByIsoCode("it");
		final Product product = Factories.PRODUCT.find(1L);
		final Product product2 = Factories.PRODUCT.find(2L);
		final List<News> TheNewsList = Factories.NEWS.getNewsByLangAndProduct(lang, product, 0, 30);
		final News theNews = TheNewsList.get(0);
		theNews.update(langIt, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, null, null, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, null, null, null);

		Assert.assertEquals("it", theNews.getNewsLang().getIsoCode());
		theNews.update(null, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, null, null, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, null, null, product2);

		Assert.assertEquals("it", theNews.getNewsLang().getIsoCode());
		Assert.assertEquals("Nabaztag", theNews.getNewsProduct().getName());
	}
}
