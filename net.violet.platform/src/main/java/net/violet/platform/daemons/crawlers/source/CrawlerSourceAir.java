package net.violet.platform.daemons.crawlers.source;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.utils.io.StreamTools;
import net.violet.platform.datamodel.Crawl;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.ConvertTools;

import org.apache.log4j.Logger;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

public class CrawlerSourceAir extends AbstractSourceCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerSourceAir.class);

	private static enum SOURCES {
		PARIS("paris") {

			@Override
			protected Map<String, Integer> doProcess(String inContent, String inSource) {
				final String getair = ConvertTools.extractInfo(inContent, "jour=jour", "</form>", 0);
				try {
					int today = Integer.parseInt(ConvertTools.extractInfo(getair, "indice-", "\"", 0));

					if ((today >= 0) && (today < 5)) {
						today = 1; // good
					} else if ((today >= 5) && (today < 8)) {
						today = 6; // middle
					} else if (today >= 8) {
						today = 9; // bad
					}
					return Collections.singletonMap(inSource + ".today", today);

				} catch (final NumberFormatException e) {
					CrawlerSourceAir.LOGGER.fatal(e, e);
				}
				return Collections.emptyMap();
			}
		},
		ROME("rome") {

			@Override
			protected Map<String, Integer> doProcess(String inContent, String inSource) {
				if (inContent != null) {

					int today = 0;
					for (final char aChar : inContent.toCharArray()) {
						if (aChar == '#') {
							today++;
						}
					}

					if ((today >= 0) && (today < 2)) {
						today = 1; // good
					} else if ((today >= 2) && (today < 6)) {
						today = 6; // middle
					} else if (today >= 6) {
						today = 9; // bad
					}
					return Collections.singletonMap(inSource + ".today", today);
				}
				return Collections.emptyMap();
			}

			@Override
			protected String getContent(String inParam) {
				final CCalendar theCalendar = new CCalendar(System.currentTimeMillis());
				try {
					final String content = CrawlerSourceAir.httpGetPdfContent(inParam, theCalendar.getDateOfBirthFormatted());
					return content;
				} catch (final IOException e) {
					try {
						final String inDate = theCalendar.getDateOfBirthFormatted().replaceAll(net.violet.common.StringShop.HYPHEN, net.violet.common.StringShop.EMPTY_STRING);
						final String content = CrawlerSourceAir.httpGetPdfContent(inParam, inDate);
						return content;
					} catch (final IOException e1) {
						CrawlerSourceAir.LOGGER.fatal(e1, e1);
						theCalendar.addSeconds(-20);
					}
				}
				return net.violet.common.StringShop.EMPTY_STRING;
			}
		},
		BELGIQUE("belgique") {

			@Override
			protected Map<String, Integer> doProcess(String inContent, String inSource) {
				final String getair = ConvertTools.extractInfo(inContent, "Agglom", ">Zone<", 0);
				final Map<String, Integer> theMap = new HashMap<String, Integer>();

				for (int u = 0; u < SOURCES.BE_KEYS.length; u++) {
					String valAir = ConvertTools.extractInfo(getair, SOURCES.BE_KEYS[u], "title=", 0);
					valAir = ConvertTools.extractInfo(valAir, "src=\"", ".png", 0);

					try {
						int today = Integer.parseInt(valAir);

						if ((today >= 0) && (today < 5)) {
							today = 1; // good // 0 a viré juste pour éviter erreur d'envoi
						} else if ((today >= 5) && (today < 8)) {
							today = 6; // middle
						} else if (today >= 8) {
							today = 9; // bad
						}

						theMap.put(inSource + net.violet.common.StringShop.POINT + SOURCES.BE_AIR_CITIES[u] + ".today", today);
					} catch (final NumberFormatException e) {
						CrawlerSourceAir.LOGGER.fatal(e, e);
					}
				}
				return theMap;
			}

		},
		LONDON("london") {

			@Override
			protected Map<String, Integer> doProcess(String inContent, String inSource) {
				try {
					int today = Integer.parseInt(ConvertTools.extractInfo(inContent, "London IQA :", "\"", 0));

					if ((today >= 0) && (today <= 31)) {
						today = 1; // good
					} else if ((today >= 32) && (today <= 49)) {
						today = 6; // middle
					} else if (today >= 50) {
						today = 9; // bad
					}

					return Collections.singletonMap(inSource + ".today", today);
				} catch (final NumberFormatException e) {
					CrawlerSourceAir.LOGGER.fatal(e, e);
				}

				return Collections.emptyMap();
			}
		},
		US("us") {

			@Override
			protected Map<String, Integer> doProcess(String inContent, String inSource) {
				final Map<String, Integer> theMap = new HashMap<String, Integer>();
				for (int u = 0; u < SOURCES.US_KEYS.length; u++) {
					String valAir = ConvertTools.extractInfo(inContent, SOURCES.US_KEYS[u], "action=airnow.showlocal&CityID=", 0);
					valAir = ConvertTools.extractInfo(valAir, "PM2.5", "/strong", 0);
					valAir = ConvertTools.extractInfo(valAir, "strong>", "<", 0);

					if (!net.violet.common.StringShop.EMPTY_STRING.equals(valAir)) {
						try {
							int today = Integer.parseInt(valAir);

							if ((today >= 0) && (today < 51)) {
								today = 1; // good // 0 a viré juste pour éviter erreur d'envoi
							} else if ((today >= 51) && (today < 101)) {
								today = 6; // middle
							} else if (today >= 101) {
								today = 9; // bad
							}

							theMap.put(inSource + net.violet.common.StringShop.POINT + SOURCES.US_AIR_CITIES[u] + ".today", today);
						} catch (final NumberFormatException e) {
							CrawlerSourceAir.LOGGER.fatal(e, e);
						}
					}
				}

				return theMap;
			}
		};

		private static final String[] BE_AIR_CITIES = { "Bruxelles", "Anvers", "Gand", "Liege", "Charleroi" };
		private static final String[] BE_KEYS = { "Bruxelles", "Anvers", "Gand", "ge", "Charleroi" };
		private static final String[] US_AIR_CITIES = { "Atlanta", "Boston", "Chicago", "Cleveland", "Dallas", "Denver", "Detroit", "LosAngeles", "LosAngelesMetroandInlandOrange", "Miami", "Minneapolis", "New-York", "Philadelphia", "Phoenix", "Richmond", "SaintLouis", "SanDiegoCoast", "SanDiegoFoothills", "SanDiegoMesaandInlandValley", "SanFrancisco", "Seattle-Bellevue-KentValley", "StLouis", "Tampa", "Washington" };
		private static final String[] US_KEYS = { "Atlanta, GA", "Boston, MA", "Chicago, IL", "Cleveland-Akron-Lorain, OH", "Dallas-Fort Worth, TX", "Denver, CO", "Detroit, MI", "Los Angeles Inland, CA", "Los Angeles Metro and Inland Orange Co., CA", "Miami, FL", "Minneapolis-St. Paul, MN", "New York City, NY", "Philadelphia, PA", "Phoenix, AZ", "Richmond, VA", "Saint Louis, MO", "San Diego Coast, CA", "San Diego Foothills, CA", "San Diego Mesa and Inland Valley, CA", "San Francisco, CA", "Seattle-Bellevue-Kent Valley, WA", "St. Louis (Metro-east), IL", "Tampa, FL", "Washington, DC" };

		private static final Map<String, SOURCES> INDEX_SOURCE;
		private static final Pattern SOURCE_PATTERN = Pattern.compile(".*\\.([\\p{Alpha}]+)$");
		static {
			final Map<String, SOURCES> theMap = new HashMap<String, SOURCES>();

			for (final SOURCES aSource : SOURCES.values()) {
				theMap.put(aSource.getName(), aSource);
			}

			INDEX_SOURCE = Collections.unmodifiableMap(theMap);
		}

		private final String mName;

		private SOURCES(String inSourceName) {
			this.mName = inSourceName;
		}

		private String getName() {
			return this.mName;
		}

		public static SOURCES findSourceByPath(String inPath) {
			final Matcher theMatcher = SOURCES.SOURCE_PATTERN.matcher(inPath);

			if (theMatcher.matches()) {
				return SOURCES.INDEX_SOURCE.get(theMatcher.group(1));
			}
			return null;
		}

		public void process(Crawl inCrawl) {
			final String content = getContent(inCrawl.getCrawl_param());

			final Map<String, Integer> theSourcePathsNVals = doProcess(content, inCrawl.getCrawl_source());

			for (final Entry<String, Integer> aSourcePathNVal : theSourcePathsNVals.entrySet()) {
				AbstractSourceCrawler.updateSource(aSourcePathNVal.getKey(), aSourcePathNVal.getValue());
			}
		}

		protected String getContent(String inParam) {
			try {
				return StreamTools.readString(new URL(inParam));
			} catch (final IOException e) {
				CrawlerSourceAir.LOGGER.fatal(e, e);
				return "";
			}
		}

		protected abstract Map<String, Integer> doProcess(String inContent, String inSource);

	}

	/**
	 * Constructeur à partir des paramètres sur la ligne de commande.
	 */
	public CrawlerSourceAir(String[] inArgs) {
		super(Crawl.CRAWL_TYPE_ID_AIR, 0, inArgs);
	}

	/**
	 * Traite une source (un objet crawl).
	 * 
	 * @param inCrawl objet crawl.
	 */
	@Override
	protected void processCrawl(Crawl inCrawl) {

		try {
			SOURCES.findSourceByPath(inCrawl.getCrawl_source()).process(inCrawl);
		} catch (final Throwable aThrowable) {
			CrawlerSourceAir.LOGGER.fatal(aThrowable, aThrowable);
		}
	}

	private static String httpGetPdfContent(String urlStr, String inDate) throws IOException {
		final PDDocument theDoc = PDDocument.load(new URL(urlStr + inDate + "RMC.pdf"));
		final PDFTextStripper theStripper = new PDFTextStripper();
		final String result = theStripper.getText(theDoc);
		theDoc.close();
		return result;
	}
}
