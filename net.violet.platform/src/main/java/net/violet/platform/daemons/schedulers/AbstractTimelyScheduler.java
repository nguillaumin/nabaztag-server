package net.violet.platform.daemons.schedulers;

import java.text.ParseException;
import java.util.Calendar;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.SubscriptionSchedulingSettingsData;
import net.violet.platform.schedulers.DailyHandler;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

public abstract class AbstractTimelyScheduler extends AbstractScheduler {

	private static final Logger LOGGER = Logger.getLogger(AbstractTimelyScheduler.class);

	private final BlockingExecutor<TimezoneProcessUnit> mGatheringSchedulerExecutor = new BlockingExecutor<TimezoneProcessUnit>(getAmountOfProcess(), new SendingWorker<TimezoneProcessUnit>() {

		public void process(final TimezoneProcessUnit inTimeZoneUnit) {

			try {
				inTimeZoneUnit.processing();
				final DailyHandler.Weekday theDay = DailyHandler.Weekday.findByCalendarId(inTimeZoneUnit.getProcessConditioner().get(Calendar.DAY_OF_WEEK));
				final CCalendar endCalendar = (CCalendar) inTimeZoneUnit.getProcessConditioner().clone();
				endCalendar.addMillis(getDelay());

				Factories.SUBSCRIPTION.walkTimelySubscription(inTimeZoneUnit.get(), getSchedulingType(), theDay, inTimeZoneUnit.getProcessConditioner(), endCalendar, new JoinRecordsWalker<Subscription, SubscriptionSchedulingSettings>() {

					/**
					 * BEWARE: the contract from the walker is that the delivery
					 * date may be the day after in which case HH-mm-SS < now()
					 * -> day+1
					 * 
					 * @param inSubscription
					 * @param inScheduling
					 */
					public void process(Subscription inSubscription, SubscriptionSchedulingSettings inScheduling) {
						// For robustness but it is not really needed (HH:mm:SS
						// vs HH:mm)

						try {
							final CCalendar theDeliveryDate = CCalendar.getSQLCCalendar(inScheduling.getValue(), inTimeZoneUnit.getProcessConditioner().getTimeZone());

							if (theDeliveryDate.before(inTimeZoneUnit.getProcessConditioner())) {
								theDeliveryDate.add(Calendar.DAY_OF_MONTH, 1);
							}

							final MessageProcessUnit theMessageUnit = new MessageNSettingProcessUnit(SubscriptionSchedulingSettingsData.getData(inScheduling), SubscriptionSchedulingData.getData(inScheduling.getSubscriptionScheduling()), theDeliveryDate, getThreadWatcher()) {

								@Override
								public void runWhenSuccessful() {
									// Nothing to do so far
								}
							};
							try {
								send2SendingSchedulerExecutor(theMessageUnit);
							} catch (final InterruptedException e) {
								AbstractTimelyScheduler.LOGGER.info(e, e);
							}
						} catch (final ParseException e) {
							AbstractTimelyScheduler.LOGGER.fatal(e, e);
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

	public AbstractTimelyScheduler(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected void send2GatheringSchedulerExecutor(TimezoneProcessUnit inTimeZoneUnit) throws InterruptedException {
		inTimeZoneUnit.startWatching();
		this.mGatheringSchedulerExecutor.put(inTimeZoneUnit);
	}

}
