package net.violet.platform.daemons.schedulers;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.message.application.factories.AbstractMessageFactory.MESSAGE_TYPE;

import org.apache.log4j.Logger;

public class AmbiantScheduler extends AbstractScheduler {


	private static final Logger LOGGER = Logger.getLogger(AmbiantScheduler.class);

	private static final SchedulingType.SCHEDULING_TYPE SCHEDULING_TYPE = SchedulingType.SCHEDULING_TYPE.Ambiant;

	public AmbiantScheduler(String[] inArgs) {
		super(inArgs);
	}

	private final BlockingExecutor<TimezoneProcessUnit> mGatheringSchedulerExecutor = new BlockingExecutor<TimezoneProcessUnit>(getAmountOfProcess(), new SendingWorker<TimezoneProcessUnit>() {

		public void process(final TimezoneProcessUnit inTimeZoneUnit) {
			try {
				inTimeZoneUnit.processing();
				Factories.SUBSCRIPTION.walkSubscriptionBySchedulingTypeAndTimezone(getSchedulingType(), inTimeZoneUnit.get(), new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

					public void process(Subscription inSubscription, SubscriptionScheduling inScheduling) {

						final MessageProcessUnit theMessageUnit = new MessageProcessUnit(SubscriptionSchedulingData.getData(inScheduling), null, getThreadWatcher()) {

							@Override
							public void runWhenSuccessful() {
								// Nothing to do here
							}
						};

						try {
							send2SendingSchedulerExecutor(theMessageUnit);
						} catch (final InterruptedException e) {
							AmbiantScheduler.LOGGER.fatal(e, e);
						}
					}

				});
			} finally {
				inTimeZoneUnit.stopWatching();

				if (!inTimeZoneUnit.isError()) {
					inTimeZoneUnit.processed();
				}
			}
		}

	}, "Gathering-Scheduler");

	@Override
	protected void send2GatheringSchedulerExecutor(TimezoneProcessUnit inTimeZoneUnit) throws InterruptedException {
		inTimeZoneUnit.startWatching();
		this.mGatheringSchedulerExecutor.put(inTimeZoneUnit);
	}

	@Override
	protected SCHEDULING_TYPE getSchedulingType() {
		return AmbiantScheduler.SCHEDULING_TYPE;
	}

	@Override
	protected MESSAGE_TYPE getMessageType() {
		return MESSAGE_TYPE.SOURCE_MESSAGE;
	}
}
