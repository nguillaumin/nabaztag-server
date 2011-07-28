package net.violet.platform.daemons.crawlers.source;

import java.net.URL;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.datamodel.Crawl;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;

public class CrawlerSourceTrafic extends AbstractSourceCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerSourceTrafic.class);

	private static final String[] PORTES = { "Chapelle", "Bagnolet", "Bercy", "Italie", "Orleans", "Auteuil", "Maillot" };
	private final int[] REF_MOYEN = { 6, 4, 2, 2, 6, 3, 6 };
	private final int[] REF_MOYEN_INV = { 6, 6, 4, 2, 2, 6, 3 };

	/**
	 * Constructeur à partir des paramètres sur la ligne de commande.
	 */
	public CrawlerSourceTrafic(String[] inArgs) {
		super(Crawl.CRAWL_TYPE_ID_TRAFIC, 0, inArgs);
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

			final String param = inCrawl.getCrawl_param();
			final String source = inCrawl.getCrawl_source();

			content = StreamTools.readString(new URL(param));
			if ((content != null) && !content.equals(net.violet.common.StringShop.EMPTY_STRING) && !content.startsWith("<?xml")) {
				final int[] tempshor = new int[7];
				final int[] tempshorinv = new int[7];
				int k = 0;

				for (int i = 0; i < 7; i++) {
					try {
						k = CrawlerSourceTrafic.skipInfo(content, ";BP " + CrawlerSourceTrafic.PORTES[i] + " ; BP " + CrawlerSourceTrafic.PORTES[(i < 6) ? i + 1 : 0] + " ;", 0);
						k = CrawlerSourceTrafic.skipInfo(content, ";", k);
						k = CrawlerSourceTrafic.skipInfo(content, ";", k);
						tempshor[i] = ConvertTools.atoi_safe(ConvertTools.extractInfo(content, ";", " mn", k));
					} catch (final NumberFormatException nfe) { // valeur au dessus de 1 heure
						try {
							final int minute = ConvertTools.atoi_safe(ConvertTools.extractInfo(content, "h ", " mn", k));

							tempshor[i] = 60 + minute;
						} catch (final NumberFormatException ne) {
							CrawlerSourceTrafic.LOGGER.fatal("Syntaxe pour l'heure : " + ConvertTools.extractInfo(content, ";", " mn", k));
						}
					}
					try {
						k = CrawlerSourceTrafic.skipInfo(content, ";BP " + CrawlerSourceTrafic.PORTES[i] + " ; BP " + CrawlerSourceTrafic.PORTES[(i == 0) ? 6 : i - 1] + " ;", 0);
						k = CrawlerSourceTrafic.skipInfo(content, ";", k);
						k = CrawlerSourceTrafic.skipInfo(content, ";", k);
						tempshorinv[i] = ConvertTools.atoi_safe(ConvertTools.extractInfo(content, ";", " mn", k));
					} catch (final NumberFormatException nfe) { // valeur au dessus de 1 heure
						try {
							final int minute = ConvertTools.atoi_safe(ConvertTools.extractInfo(content, "h ", " mn", k));

							tempshorinv[i] = 60 + minute;
						} catch (final NumberFormatException ne) {
							CrawlerSourceTrafic.LOGGER.fatal("Syntaxe pour l'heure : " + ConvertTools.extractInfo(content, ";", " mn", k));
						}
					}
				}

				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						if (i != j) {
							if ((tempshor[i] == 0) || (tempshorinv[i] == 0)) {
								CrawlerSourceTrafic.LOGGER.fatal("Could not parse content for url = " + param + ", source:" + source + "." + CrawlerSourceTrafic.PORTES[i].toLowerCase() + "." + CrawlerSourceTrafic.PORTES[j].toLowerCase());
							} else {
								int length = 0;
								int lengthref = 0;
								k = i;
								while (k != j) {
									length += tempshor[k];
									lengthref += this.REF_MOYEN[k];
									k = (k < 6) ? k + 1 : 0;
								}
								int lengthinv = 0;
								int lengthrefinv = 0;
								k = i;
								while (k != j) {
									lengthinv += tempshorinv[k];
									lengthrefinv += this.REF_MOYEN_INV[k];
									k = (k == 0) ? 6 : k - 1;
								}
								if (lengthinv < length) {
									length = lengthinv;
								}

								AbstractSourceCrawler.updateSource(source + "." + CrawlerSourceTrafic.PORTES[i].toLowerCase() + "." + CrawlerSourceTrafic.PORTES[j].toLowerCase() + ".time", length);

								if (lengthrefinv < lengthref) {
									lengthref = lengthrefinv;
								}
								if (length != 0) {
									length = lengthref * 3 / length;
								}
								if (length < 0) {
									length = 0;
								} else if (length > 6) {
									length = 6;
								}

								AbstractSourceCrawler.updateSource(source + "." + CrawlerSourceTrafic.PORTES[i].toLowerCase() + "." + CrawlerSourceTrafic.PORTES[j].toLowerCase(), length);
							}
						}
					}
				}
			} else {
				CrawlerSourceTrafic.LOGGER.fatal("accès impossible au site sytadin : result => " + content);
			}
		} catch (final Throwable aThrowable) {
			CrawlerSourceTrafic.LOGGER.fatal(aThrowable, aThrowable);
		}
	}

	private static int skipInfo(String content, String info, int inValue) {
		int i = inValue;
		int val = 0;
		if (i < 0) {
			val = -1;
		} else {
			i = content.indexOf(info, i);
			if (i < 0) {
				val = -1;
			} else {
				val = i + info.length();
			}
		}
		return val;
	}
}
