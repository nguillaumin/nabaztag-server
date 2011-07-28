package net.violet.platform.daemons.schedulers;

import java.util.List;
import java.util.Random;

import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.schedulers.FrequencyHandler;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Pair;

public class RandomWithFrequencyScheduler extends AbstractFrequencyScheduler {

	private static final Random RANDOM_DELIVERY_GENERATOR = new Random(System.currentTimeMillis());

	public RandomWithFrequencyScheduler(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected SCHEDULING_TYPE getSchedulingType() {
		return SchedulingType.SCHEDULING_TYPE.RandomWithFrequency;
	}

	@Override
	public Pair<Long, CCalendar> getDeliveryDate(CCalendar reference, CCalendar start, CCalendar end, FrequencyHandler.Frequency inFrequency) {
		final Pair<Long, CCalendar> thePair = super.getDeliveryDate(reference, start, end, inFrequency);
		if (thePair.getSecond() != null) {
			final int randomTime = (int) (end.getTimeInMillis() - thePair.getSecond().getTimeInMillis()) / 1000;
			if (randomTime > 0) {
				thePair.getSecond().addSeconds(RandomWithFrequencyScheduler.RANDOM_DELIVERY_GENERATOR.nextInt(randomTime));
			}
		}

		return thePair;
	}

	@Override
	protected List<FrequencyHandler.Frequency> getFrequencies() {
		return FrequencyHandler.Frequency.getGlobalFrequencies();
	}
}
