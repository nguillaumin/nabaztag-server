package net.violet.platform.daemons.crawlers.source;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.StringShop;
import net.violet.common.utils.io.StreamTools;
import net.violet.platform.datamodel.Crawl;

import org.apache.log4j.Logger;

public class CrawlerSourceBourse extends AbstractSourceCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerSourceBourse.class);

	private static final String[] INDICES_DB = { "S&P/TSX", "DOW JONES INDU.", "NASDAQ COMPOSITE", "NASDAQ 100", "SP 500", "DAX Xetra", "BEL 20", "Ibex35", "CAC 40", "MIB 30", "AEX 25", "SMI", "FTSE 100", "Nikkei 225" };

	private static final String[] INDICES_HTML = { "S&amp;P/TSX", "DOW INDUSTRIALS", "NASDAQ Composite", "NASDAQ 100", "S&amp;P 500 INDEX", "DAX ", "BEL20", "IBEX 35 MADRID", "CAC 40", "MIB 30", "AEX-INDEX", "Swiss Market Index", "FTSE 100", "Nikkei 225" };

	private static final Pattern GET_VALUE_CODE = Pattern.compile(">((-|\\+)?\\d+\\.\\d+)%</span>");

	private static final Pattern GET_CONTENT = Pattern.compile("Internationaux(.*)Fin Content");

	/**
	 * Constructeur à partir des paramètres sur la ligne de commande.
	 */
	public CrawlerSourceBourse(String[] inArgs) {
		super(Crawl.CRAWL_TYPE_ID_BOURSE_BASIC, 0, inArgs);
	}

	/**
	 * Constructeur à partir des paramètres sur la ligne de commande.
	 */
	protected CrawlerSourceBourse(int inTypeCrawl, int countCheck, String[] inArgs) {
		super(inTypeCrawl, countCheck, inArgs);
	}

	/**
	 * Traite une source (un objet crawl).
	 * 
	 * @param inCrawl objet crawl.
	 */
	@Override
	protected void processCrawl(Crawl inCrawl) {

		try {
			final String param = inCrawl.getCrawl_param().replaceAll(StringShop.SPACE, "%20");
			final String source = inCrawl.getCrawl_source();

			String content = StreamTools.readString(new URL(param)).replaceAll("\\r|\\n", StringShop.EMPTY_STRING);

			Matcher theMatcher = CrawlerSourceBourse.GET_CONTENT.matcher(content);
			if (theMatcher.find()) {
				content = theMatcher.group(1);
			}

			for (int i = 0; i < CrawlerSourceBourse.INDICES_HTML.length; i++) {

				String indice = StringShop.EMPTY_STRING;
				final Pattern getCode = Pattern.compile(CrawlerSourceBourse.INDICES_HTML[i] + "(.*)align=\"center");
				theMatcher = getCode.matcher(content);
				if (theMatcher.find()) {
					indice = theMatcher.group(1);
				}

				theMatcher = CrawlerSourceBourse.GET_VALUE_CODE.matcher(indice);
				if (theMatcher.find()) {
					indice = theMatcher.group(1);
				}

				try {
					final float theValue = Float.parseFloat(indice);
					int valIndice;
					if (theValue < -80) {
						valIndice = 1;
					} else if (theValue < 0) {
						valIndice = 2;
					} else if (theValue == 0) {
						valIndice = 3;
					} else if (theValue < 80) {
						valIndice = 4;
					} else {
						valIndice = 5;
					}

					AbstractSourceCrawler.updateSource(source + "." + CrawlerSourceBourse.INDICES_DB[i].toLowerCase(), valIndice);
				} catch (final NumberFormatException anException) {
					CrawlerSourceBourse.LOGGER.fatal("Could not parse content for url = " + param + ", source:" + source + "." + CrawlerSourceBourse.INDICES_DB[i].toLowerCase());
				}
			}
		} catch (final Throwable aThrowable) {
			CrawlerSourceBourse.LOGGER.fatal(aThrowable, aThrowable);
		}
	}
}
