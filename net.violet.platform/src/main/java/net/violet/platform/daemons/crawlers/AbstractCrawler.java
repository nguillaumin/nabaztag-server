package net.violet.platform.daemons.crawlers;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import net.violet.platform.daemons.AbstractDaemon;

import org.apache.log4j.Logger;

/**
 * Classe de base pour les crawlers.
 */
public abstract class AbstractCrawler extends AbstractDaemon {


	private static final Logger LOGGER = Logger.getLogger(AbstractCrawler.class);

	/**
	 * Constructeur à partir des arguments en ligne de commande.
	 */
	protected AbstractCrawler(String[] inArgs) {
		super(inArgs.clone());
		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'),

		};
		final Getopt theGetOpt = new Getopt(getClass().getSimpleName(), inArgs, "h", theLongOpts);
		int theOption;
		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 'h':
				AbstractCrawler.LOGGER.debug("help here \n");
				break;
			}
		}
	}

	/**
	 * Point d'entrée du crawler. C'est là que figure la boucle principale.
	 */
	@Override
	public final long doRun() {
		process();
		final long after = System.currentTimeMillis();
		final long took = after - getStartTime();

		if (!runOnce()) {
			if (took < getDelay()) {
				AbstractCrawler.LOGGER.info("[" + getClass().getSimpleName() + "] Process time: " + took + " ms");
				return getDelay() - took;
			}
			AbstractCrawler.LOGGER.warn("[" + getClass().getSimpleName() + "] Process took more than delay (" + took + " > " + getDelay() + ")");
		}

		return 0;
	}

	/**
	 * Point d'entrée du crawler. Effectue une itération.
	 */
	protected abstract void process();
}
