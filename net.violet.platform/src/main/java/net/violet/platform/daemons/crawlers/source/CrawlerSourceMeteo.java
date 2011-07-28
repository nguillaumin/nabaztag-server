package net.violet.platform.daemons.crawlers.source;

import java.net.URL;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.datamodel.Crawl;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;

public class CrawlerSourceMeteo extends AbstractSourceCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerSourceMeteo.class);

	/**
	 * 0: soleil          1: nuages        2: brouillard        3: pluie
	 *        4: neige        5: orage        
	 */
	private static final int[] CONVERTMETEO = { 5, 5, 5, 5, 5, 3, 3, 4, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 4, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 5, 5, 3, 3, 4, 4, 4, 1, 3, 4, 5 };

	/**
	 * Constructeur à partir des paramètres sur la ligne de commande.
	 */
	public CrawlerSourceMeteo(String[] inArgs) {
		super(Crawl.CRAWL_TYPE_ID_METEO, 30, inArgs);
	}

	/**
	 * Traite une source (un objet crawl).
	 * 
	 * @param inCrawl objet crawl.
	 */
	@Override
	protected void processCrawl(Crawl inCrawl) {

		String content = net.violet.common.StringShop.EMPTY_STRING;

		try {

			String param = inCrawl.getCrawl_param();
			// Patch permettant de récupèrer au bon endroit les données
			// (temporaire)
			param = param.replaceAll("\\?dayNum=0", net.violet.common.StringShop.EMPTY_STRING);
			param = param.replaceAll("detail/", "today-");
			final String source = inCrawl.getCrawl_source();

			content = StreamTools.readString(new URL(param));

			content = ConvertTools.extractInfo(content, "Jour", "today_right", 0);

			String contentweather = ConvertTools.extractInfo(content, "images/61x61/", ".", 0);
			String temperatureweather = ConvertTools.extractInfo(content, "26px;\">", "&deg;", 0);

			if (contentweather.equals(net.violet.common.StringShop.EMPTY_STRING) || contentweather.equals("25") || contentweather.equals("24") || contentweather.equals("23") || contentweather.equals("44")) {
				param = param.replaceAll("today", "tomorrow"); // température du lendemain
				content = StreamTools.readString(new URL(param));
				content = ConvertTools.extractInfo(content, "Jour", "daynight_footer", 0);

				contentweather = ConvertTools.extractInfo(content, "images/93x93/", ".", 0);
				temperatureweather = ConvertTools.extractInfo(content, "33px;\">", "&deg;", 0);
			}
			try {
				int codeweather = Integer.parseInt(contentweather);

				if (codeweather < 0) {
					codeweather = 26; // nuages par défaut
				} else if (codeweather > 47) {
					codeweather = 26;
				}
				codeweather = CrawlerSourceMeteo.CONVERTMETEO[codeweather];
				final int temperature = Integer.parseInt(temperatureweather);

				AbstractSourceCrawler.updateSource(source + ".weather", codeweather);
				AbstractSourceCrawler.updateSource(source + ".temp", temperature);
			} catch (final NumberFormatException anException) {
				CrawlerSourceMeteo.LOGGER.fatal("Could not parse content for url = " + param + ", source:" + source);
			}
			incrementUpdateCount();

		} catch (final Throwable aThrowable) {
			CrawlerSourceMeteo.LOGGER.fatal(aThrowable, aThrowable);
		}
	}
}
