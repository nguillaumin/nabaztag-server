package net.violet.platform.daemons.schedulers;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.sql.Timestamp;

import net.violet.platform.daemons.AbstractDaemon;
import net.violet.platform.datamodel.SchedulingType;

import org.apache.log4j.Logger;

public abstract class AbstractShellScheduler extends AbstractDaemon {


	private static final Logger LOGGER = Logger.getLogger(AbstractShellScheduler.class);

	private long mExpectedMaxProcessingTime;

	/**
	 * Constructeur à partir des arguments en ligne de commande.
	 */
	protected AbstractShellScheduler(String[] inArgs) {
		super(inArgs.clone());
		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'), new LongOpt("expectedProcessingTime", LongOpt.REQUIRED_ARGUMENT, null, 'x'), };

		final Getopt theGetOpt = new Getopt(getClass().getSimpleName(), inArgs, "x:h", theLongOpts);
		this.mExpectedMaxProcessingTime = 0L;
		int theOption;

		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 'h':
				AbstractShellScheduler.LOGGER.debug("help here \n");
				break;
			case 'x':
				this.mExpectedMaxProcessingTime = Long.parseLong(theGetOpt.getOptarg());
				break;
			}
		}
	}

	/**
	 * Point d'entrée du crawler. C'est là que figure la boucle principale.
	 */
	@Override
	public final long doRun() {
		final SchedulingType theType = getSchedulingType().getRecord();
		final long coverage = theType.getCoveredFor().getTime();

		final long effectiveStartTime = Math.max(getStartTime(), coverage - this.mExpectedMaxProcessingTime);
		final long coveredFor = effectiveStartTime + this.mExpectedMaxProcessingTime + getDelay();

		theType.setCoveredFor(new Timestamp(coveredFor));

		AbstractShellScheduler.LOGGER.debug("Start Processing");
		process(effectiveStartTime);
		AbstractShellScheduler.LOGGER.info("Scheduling covered until " + coveredFor);
		final long after = System.currentTimeMillis();
		final long took = after - getStartTime();

		if (!runOnce()) {
			if (took < getDelay()) {
				AbstractShellScheduler.LOGGER.info("[" + getClass().getSimpleName() + "] Process time: " + took + " ms");
			} else {
				AbstractShellScheduler.LOGGER.warn("[" + getClass().getSimpleName() + "] Process took more than delay (" + took + " > " + getDelay() + ")");
			}

			this.mExpectedMaxProcessingTime = took;
			final long sleepFor = (effectiveStartTime + this.mExpectedMaxProcessingTime + getDelay() - took) - (System.currentTimeMillis());
			return (sleepFor < 0) ? 0 : sleepFor;
		}

		return 0;
	}

	protected long getExpectedMaxProcessingTime() {
		return this.mExpectedMaxProcessingTime;
	}

	/**
	 * Point d'entrée du crawler. Effectue une itération.
	 */
	protected abstract void process(long inStartTime);

	protected abstract SchedulingType.SCHEDULING_TYPE getSchedulingType();

}
