package net.violet.platform.daemons.schedulers;

import java.util.List;

import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.schedulers.FrequencyHandler;

public class FrequencyScheduler extends AbstractFrequencyScheduler {

	private static final SCHEDULING_TYPE TYPE = SCHEDULING_TYPE.Frequency;

	public FrequencyScheduler(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected SCHEDULING_TYPE getSchedulingType() {
		return FrequencyScheduler.TYPE;
	}

	@Override
	protected List<FrequencyHandler.Frequency> getFrequencies() {
		return FrequencyHandler.Frequency.getHourlyFrequencies();
	}

}
