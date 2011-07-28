package net.violet.platform.daemons.schedulers;

import java.util.List;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Pair;

import org.apache.log4j.Logger;

/**
 * Cases covered : 
 * If lastTime == null -> NOW 
 * If start >= lastTime+freq -> NOW 
 * 
 * If lastTime > end 
 * If lastTime+freq > end
 * If start < lastTime+freq < end 
 * 	-> lastTime+freq Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.create(inScheduling,FREQUENCY.LAST_TIME_KEY, start.getTimestamp());
 */
public abstract class AbstractFrequencyScheduler extends AbstractScheduler {

	private static final Logger LOGGER = Logger.getLogger(AbstractFrequencyScheduler.class);

	private static final int MAX_RUN_BEFORE_UPDATE = 100;

	protected AbstractFrequencyScheduler(String[] inArgs) {
		super(inArgs);
	}

	private final BlockingExecutor<TimezoneProcessUnit> mGatheringSchedulerExecutor = new BlockingExecutor<TimezoneProcessUnit>(getAmountOfProcess(), new SendingWorker<TimezoneProcessUnit>() {

		public void process(final TimezoneProcessUnit inTimeZoneUnit) {

			try {
				inTimeZoneUnit.processing();
				final Timezone theTimezone = inTimeZoneUnit.get();
				final CCalendar start = inTimeZoneUnit.getProcessConditioner();
				final CCalendar end = (CCalendar) start.clone();
				end.addMillis(getDelay());

				for (final FrequencyHandler.Frequency frequency : getFrequencies()) {

					Factories.SUBSCRIPTION.walkFrequencySubscriptionByTimezone(getSchedulingType(), frequency, theTimezone, start, end, new JoinRecordsWalker<Subscription, SubscriptionScheduling>() {

						public void process(Subscription inSubscription, final SubscriptionScheduling inScheduling) {
							final SubscriptionSchedulingSettings theSetting = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionSchedulingAndKey(inScheduling, FrequencyHandler.LAST_TIME);

							final CCalendar theReference = CCalendar.parseTimestamp(theSetting.getValue(), theTimezone.getTimezone_javaId());

							final Pair<Long, CCalendar> theDeliveryDate = getDeliveryDate(theReference, start, end, frequency);

							final MessageProcessUnit theMessageUnit = new MessageProcessUnit(SubscriptionSchedulingData.getData(inScheduling), theDeliveryDate.getSecond(), getThreadWatcher()) {

								@Override
								public void runWhenSuccessful() {
									if (theDeliveryDate.getFirst() > AbstractFrequencyScheduler.MAX_RUN_BEFORE_UPDATE) {
										final CCalendar theDate = (theDeliveryDate.getSecond() != null) ? theDeliveryDate.getSecond() : start;
										theSetting.setValue(theDate.getTimestamp(theTimezone.getJavaTimeZone()));

									}
								}
							};

							try {
								send2SendingSchedulerExecutor(theMessageUnit);
							} catch (final InterruptedException e) {
								AbstractFrequencyScheduler.LOGGER.fatal(e, e);
							}

						}

					});
				}

			} finally {
				inTimeZoneUnit.stopWatching();

				if (!inTimeZoneUnit.isError()) {
					inTimeZoneUnit.processed();
				}
			}
		}

	}, "Gathering-Scheduler");

	protected abstract List<FrequencyHandler.Frequency> getFrequencies();

	@Override
	protected void send2GatheringSchedulerExecutor(TimezoneProcessUnit inTimeZoneUnit) throws InterruptedException {
		inTimeZoneUnit.startWatching();
		this.mGatheringSchedulerExecutor.put(inTimeZoneUnit);
	}

	/**
	 * Return the delevery time a message given the current time, the time of
	 * registration to the service and the period
	 * 
	 * @param now
	 * @param registrationTime
	 * @param period
	 * @return
	 */
	public Pair<Long, CCalendar> getDeliveryDate(CCalendar reference, CCalendar start, CCalendar end, FrequencyHandler.Frequency inFrequency) {
		final CCalendar deliveryDate = (CCalendar) reference.clone();
		final long theRefTime = reference.getTimeInMillis() / 1000;
		final long theFrequency = inFrequency.getTimeInSecond();
		final CCalendar endCal = (CCalendar) end.clone();
		endCal.addSeconds(-1);
		final long endTime = endCal.getTimeInMillis() / 1000;

		final long instancesAmount = (((endTime - theRefTime)) / theFrequency);

		deliveryDate.addSeconds((int) (instancesAmount * theFrequency));

		if (!deliveryDate.equals(start)) {
			return new Pair<Long, CCalendar>(instancesAmount, deliveryDate);
		}
		return new Pair<Long, CCalendar>(instancesAmount, null);

	}

}
