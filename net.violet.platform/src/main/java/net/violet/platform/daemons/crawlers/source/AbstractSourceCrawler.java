package net.violet.platform.daemons.crawlers.source;

import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.Crawl;
import net.violet.platform.datamodel.CrawlImpl;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

/**
 * Classe de base pour les sources passives.
 */
abstract class AbstractSourceCrawler extends AbstractCrawler {


	private static final Logger LOGGER = Logger.getLogger(AbstractSourceCrawler.class);

	/**
	 * Référence sur l'id de la source (pour crawl)
	 */
	private final int mCrawlSourceID;

	/**
	 * Nombre de sources mises à jour.
	 */
	private int mSourceUpdatedCount;

	/**
	 * nbr d'accès au source successif, fait une pause entre. (Evite de spammer
	 * les sites de source)
	 */
	private final int mCountCheck;

	/**
	 * Constructeur à partir des ID de source.
	 */
	protected AbstractSourceCrawler(int inCrawlSourceID, int inCountCheck, String[] inArgs) {
		super(inArgs);
		this.mCrawlSourceID = inCrawlSourceID;
		this.mCountCheck = inCountCheck;

	}

	/**
	 * Met à jour la source.
	 * 
	 * @param string
	 */
	protected static void updateSource(String inSourcePath, int inSourceVal) {
		final Source theSource = Factories.SOURCE.findByPath(inSourcePath);
		if (theSource != null) {
			theSource.setVal(inSourceVal);
		} else {
			AbstractSourceCrawler.LOGGER.info("source path NULL : " + inSourcePath);
		}
	}

	/**
	 * Récupère la source.
	 * 
	 * @param string
	 */
	protected Source getSource(String inSourcePath) {
		return Factories.SOURCE.findByPath(inSourcePath);
	}

	@Override
	protected final void process() {
		final CrawlImpl.RecordWalker<Crawl> recordWalker = new CrawlImpl.RecordWalker<Crawl>() {

			public void process(Crawl inObject) {
				processCrawl(inObject);
				this.countCheck++;
				if (this.countCheck == AbstractSourceCrawler.this.mCountCheck) {
					try {
						Thread.sleep(1000); // temporise
					} catch (final InterruptedException e) {
						
						e.printStackTrace();
					}
					this.countCheck = 0; // reset
				}
			}

			private int countCheck;
		};

		this.mSourceUpdatedCount = 0;
		final int nbSources = CrawlImpl.walkByType(recordWalker, this.mCrawlSourceID);
		AbstractSourceCrawler.LOGGER.info(" total : " + nbSources + " ok : " + this.mSourceUpdatedCount);
	}

	/**
	 * Traite une source (un objet crawl).
	 * 
	 * @param inCrawl objet crawl.
	 */
	protected abstract void processCrawl(Crawl inCrawl);

	protected final void incrementUpdateCount() {
		this.mSourceUpdatedCount++;
	}
}
