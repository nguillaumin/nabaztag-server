package net.violet.platform.daemons;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import org.apache.log4j.Logger;

/**
 * Classe de base pour les crawlers.
 */
public abstract class AbstractDaemon implements Runnable {


	private static final Logger LOGGER = Logger.getLogger(AbstractDaemon.class);

	/**
	 * Délai par défaut: 30 minutes.
	 */
	public static final int DEFAULT_DELAY = 1800000;

	/**
	 * Si on tourne.
	 */
	private volatile boolean mRunning;

	/**
	 * Si on doit quitter.
	 */
	private volatile boolean mQuit;

	/**
	 * Si on ne tourne qu'une seule fois.
	 */
	private final boolean mOnce;

	/**
	 * Mutex pour la boucle.
	 */
	private final Integer mWaitMutexObject = new Integer(0);

	/**
	 * Délai entre deux itérations.
	 */
	private final int mDelay;

	/**
	 * Délai max entre deux itérations.
	 */
	private final int mMaxDelay;

	/**
	 * heure de lancement d'une itération
	 */
	private long mStartTime;

	/**
	 * Constructeur à partir des arguments en ligne de commande.
	 */
	protected AbstractDaemon(String[] inArgs) {
		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'), new LongOpt("delay", LongOpt.REQUIRED_ARGUMENT, null, 'd'), new LongOpt("maxdelay", LongOpt.REQUIRED_ARGUMENT, null, 'm'), new LongOpt("once", LongOpt.NO_ARGUMENT, null, 1), };
		final Getopt theGetOpt = new Getopt(getClass().getSimpleName(), inArgs, "d:m:h", theLongOpts);
		int theOption;
		int theDelay = AbstractDaemon.DEFAULT_DELAY;
		int theMaxDelay = 0;
		boolean once = false;
		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 1:
				// --once
				once = true;
				break;

			case 'd':
				theDelay = Integer.parseInt(theGetOpt.getOptarg());
				break;

			case 'm':
				theMaxDelay = Integer.parseInt(theGetOpt.getOptarg());
				break;
			}
		}

		this.mDelay = theDelay;
		this.mMaxDelay = (theMaxDelay == 0) ? this.mDelay * 2 : theMaxDelay;
		this.mOnce = once;
	}

	/**
	 * Point d'entrée du crawler. C'est là que figure la boucle principale.
	 */
	public final void run() {
		synchronized (this.mWaitMutexObject) {
			this.mRunning = true;
			long waitFor = 0L;
			do {
				if (waitFor > 0) {
					try {
						AbstractDaemon.LOGGER.debug("Will sleep for " + waitFor);
						this.mWaitMutexObject.wait(waitFor);
					} catch (final InterruptedException anException) {
						// This space for rent.
					}
				}
				this.mStartTime = System.currentTimeMillis();
				waitFor = doRun();
			} while (!this.mQuit && !this.mOnce);
			this.mRunning = false;
		}
	}

	/**
	 * Method starting point of the daemons.
	 * 
	 * @return the amount of time to wait between 2 loops (if running more than
	 *         once)
	 */
	protected abstract long doRun();

	public void stopCrawler() {
		// On dit qu'il faut sortir.
		this.mQuit = true;

		quit();

		// On attend la fin de la boucle principale.
		while (this.mRunning) {
			synchronized (this.mWaitMutexObject) {
				if (this.mRunning) {
					this.mWaitMutexObject.notify();
				}
			}
		}
	}

	protected void quit() {
		// this space for rent
	}

	public int getDelay() {
		return this.mDelay;
	}

	public int getMaxDelay() {
		return this.mMaxDelay;
	}

	public boolean runOnce() {
		return this.mOnce;
	}

	public long getStartTime() {
		return this.mStartTime;
	}
}
