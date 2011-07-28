package net.violet.platform.daemons.crawlers.feed.sender;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.violet.platform.daemons.crawlers.vaction.AbstractFeedCrawler.FeedUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.concurrent.units.AbstractCrawlerProcessUnit;

public abstract class FeedCrawlerSender<T extends AbstractCrawlerProcessUnit> {

	private final ExecutorService mThreadPool;

	/**
	 * Constructeur à partir du service et des paramètres en ligne de commande.
	 * 
	 * @param inArgs
	 */
	public FeedCrawlerSender(String[] inArgs) {
		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("sending", LongOpt.REQUIRED_ARGUMENT, null, 's'), };

		final Getopt theGetOpt = new Getopt(getClass().getSimpleName(), inArgs, "s:", theLongOpts);
		int nbInsertingThreads = 0;
		int theOption = 0;

		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 's':
				nbInsertingThreads = Integer.parseInt(theGetOpt.getOptarg());
				break;
			}
		}

		this.mThreadPool = Executors.newFixedThreadPool(nbInsertingThreads);
	}

	protected void execute(Runnable inRunnable) {
		this.mThreadPool.execute(inRunnable);
	}

	public abstract void processNews(final List<Files> inNewsFiles, final List<T> inNews, final MessageSignature inMessageSignature, final String inTitle, final FeedUnit inFeedUnit);

	public abstract int getTTL();
}
