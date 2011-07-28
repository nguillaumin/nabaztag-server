package net.violet.platform.daemons.schedulers;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.common.utils.concurrent.units.AbstractProcessUnit;
import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.message.application.factories.AbstractMessageFactory;
import net.violet.platform.message.application.factories.AbstractMessageFactory.MESSAGE_TYPE;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

/**
 * The abstract class for Schedulers.
 */
public abstract class AbstractScheduler extends AbstractShellScheduler {

	private static final Logger LOGGER = Logger.getLogger(AbstractScheduler.class);

	private final ThreadWatcher mThreadWatcher;

	private final int mAmountOfProcesses;

	protected abstract class SendingWorker<U> implements Worker<U> {

		private final BlockingExecutor<MessageProcessUnit> mSendingSchedulerExecutor = new BlockingExecutor<MessageProcessUnit>(getAmountOfProcess(), new Worker<MessageProcessUnit>() {

			public void process(MessageProcessUnit inUnit) {
				inUnit.processing();
				try {
					sendMessage(inUnit);
				} finally {
					inUnit.stopWatching();
					if (!inUnit.isError()) {
						inUnit.processed();
					}
				}

			}
		}, "Sending-Scheduler");

		protected void send2SendingSchedulerExecutor(MessageProcessUnit inMessageUnit) throws InterruptedException {
			inMessageUnit.startWatching();
			this.mSendingSchedulerExecutor.put(inMessageUnit);
		}
	}

	/**
	 * Constructeur à partir des arguments en ligne de commande.
	 */
	protected AbstractScheduler(String[] inArgs) {
		super(inArgs.clone());
		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'), new LongOpt("amountOfRunningProcesses", LongOpt.REQUIRED_ARGUMENT, null, 'p'), };

		final Getopt theGetOpt = new Getopt(getClass().getSimpleName(), inArgs, "p:h", theLongOpts);
		int processes = 0;
		int theOption;

		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 'h':
				AbstractScheduler.LOGGER.debug("help here \n");
				break;
			case 'p':
				processes = Integer.parseInt(theGetOpt.getOptarg());
				break;
			}
		}

		this.mAmountOfProcesses = processes;
		this.mThreadWatcher = new ThreadWatcher();
	}

	protected final int getAmountOfProcess() {
		return this.mAmountOfProcesses;
	}

	/**
	 * Point d'entrée du crawler. Effectue une itération.
	 */
	@Override
	protected final void process(long inStartTime) {

		final CCalendar theCalendar = new CCalendar(inStartTime + getExpectedMaxProcessingTime());

		// walks on all available time zones
		Factories.TIMEZONE.walkDistincts(new RecordWalker<Timezone>() {

			public void process(Timezone inTimezone) {
				theCalendar.setTimeZone(inTimezone.getJavaTimeZone());
				final TimezoneProcessUnit theTimeZoneUnit = new TimezoneProcessUnit(inTimezone, (CCalendar) theCalendar.clone(), AbstractScheduler.this.mThreadWatcher);

				try {
					send2GatheringSchedulerExecutor(theTimeZoneUnit);
				} catch (final InterruptedException e) {
					AbstractScheduler.LOGGER.fatal(e, e);
				}
			}

		});

		this.mThreadWatcher.await();
	}

	protected abstract void send2GatheringSchedulerExecutor(TimezoneProcessUnit inTimeZoneUnit) throws InterruptedException;

	protected final ThreadWatcher getThreadWatcher() {
		return this.mThreadWatcher;
	}

	protected void sendMessage(MessageProcessUnit inMessageUnit) {
		AbstractMessageFactory.sendMessage(inMessageUnit, getMessageType());
	}

	protected MESSAGE_TYPE getMessageType() {
		return MESSAGE_TYPE.FILES_MESSAGE;
	}

	public abstract static class MessageProcessUnit extends AbstractProcessUnit<SubscriptionSchedulingData, CCalendar, Object> {

		private final AbstractMessageFactory.ACTION mAction;

		protected MessageProcessUnit(SubscriptionSchedulingData inMaterial, CCalendar inCondition, ThreadWatcher inThreadWatcher) {
			this(inMaterial, inCondition, inThreadWatcher, AbstractMessageFactory.ACTION.ADD);
		}

		public MessageProcessUnit(SubscriptionSchedulingData inMaterial, CCalendar inCondition, ThreadWatcher inThreadWatcher, AbstractMessageFactory.ACTION inAction) {
			super(inMaterial, inCondition, inThreadWatcher);
			this.mAction = inAction;
		}

		public abstract void runWhenSuccessful();

		public boolean isAdd() {
			return this.mAction == AbstractMessageFactory.ACTION.ADD;
		}

	}

	public abstract static class MessageNSettingProcessUnit extends MessageProcessUnit {

		private final SubscriptionSchedulingSettingsData mSchedulingSettingsData;

		protected MessageNSettingProcessUnit(SubscriptionSchedulingSettingsData inSchedulingSettingsData, SubscriptionSchedulingData inMaterial, CCalendar inCondition, ThreadWatcher inThreadWatcher) {
			super(inMaterial, inCondition, inThreadWatcher);
			this.mSchedulingSettingsData = inSchedulingSettingsData;
		}

		public final SubscriptionSchedulingSettingsData getSchedulingSettingsData() {
			return this.mSchedulingSettingsData;
		}

	}

	protected static class TimezoneProcessUnit extends AbstractProcessUnit<Timezone, CCalendar, Object> {

		public TimezoneProcessUnit(Timezone inProcessMaterial, CCalendar inCondition, ThreadWatcher inWatcher) {
			super(inProcessMaterial, inCondition, inWatcher);
		}

	}

}
