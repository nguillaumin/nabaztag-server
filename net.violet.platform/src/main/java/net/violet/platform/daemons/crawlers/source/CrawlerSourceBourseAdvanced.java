package net.violet.platform.daemons.crawlers.source;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.datamodel.Crawl;
import net.violet.platform.management.MonitorLogger;

import org.apache.log4j.Logger;

/**
 * Permet d'updater les sources pour la bourse avancé
 */
public class CrawlerSourceBourseAdvanced extends CrawlerSourceBourse {

	private static final Logger LOGGER = Logger.getLogger(CrawlerSourceBourseAdvanced.class);

	private static final Set<String> BOGUS_VALUES = new HashSet<String>();

	/**
	 * Constructeur à partir des paramètres sur la ligne de commande.
	 */
	public CrawlerSourceBourseAdvanced(String[] inArgs) {
		// pause d'une seconde toutes les 50 sources updaté, evite de se faire
		// blacklister
		super(Crawl.CRAWL_TYPE_ID_BOURSE_ADVANCED, 50, inArgs);
	}

	/**
	 * Traite une source (un objet crawl).
	 * 
	 * @param inCrawl objet crawl.
	 */
	@Override
	protected void processCrawl(Crawl inCrawl) {

		// parse temporaire
		final String url = inCrawl.getCrawl_param().replaceAll(net.violet.common.StringShop.SPACE, "%20").replaceAll("\\^", "%5E");
		final String source = inCrawl.getCrawl_source();

		final int valIndice = CrawlerSourceBourseAdvanced.getValueBourseSource(url);
		if (valIndice > 0) {
			AbstractSourceCrawler.updateSource(source, valIndice);
		}
	}

	/**
	 * Méthode utilisé coté site aussi, lors de l'activation ou mise à jour du
	 * service
	 * 
	 * @param url : url de la bourse passé
	 */
	public static int getValueBourseSource(String url) {

		String content = net.violet.common.StringShop.EMPTY_STRING;

		try {
			content = StreamTools.readString(new URL(url));
			content = content.substring(content.indexOf("- ") + 2);
			content = content.replaceAll("\"|%|\\\\x2b|\\\\x2e", net.violet.common.StringShop.EMPTY_STRING).trim();

			if (content.contains("N/A")) {
				// Cette valeur n'existe pas/plus.
				CrawlerSourceBourseAdvanced.reportErrorWithValue(url);
			} else {
				try {
					final float theValue = Float.parseFloat(content);
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

					return valIndice;
				} catch (final NumberFormatException anException) {
					CrawlerSourceBourseAdvanced.LOGGER.fatal("Could not parse content for url = " + url + ", content = " + content);
				}
			}
		} catch (final Throwable aThrowable) {
			CrawlerSourceBourseAdvanced.LOGGER.fatal(aThrowable, aThrowable);
		}
		return -1;
	}

	private static void reportErrorWithValue(String inParam) {
		synchronized (CrawlerSourceBourseAdvanced.BOGUS_VALUES) {
			if (!CrawlerSourceBourseAdvanced.BOGUS_VALUES.contains(inParam)) {
				CrawlerSourceBourseAdvanced.BOGUS_VALUES.add(inParam);

				MonitorLogger.sendReport("Bourse Full: l'URL " + inParam + " ne marche pas");
			}
		}
	}
}
