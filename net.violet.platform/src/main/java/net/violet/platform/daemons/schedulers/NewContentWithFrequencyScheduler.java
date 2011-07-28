package net.violet.platform.daemons.schedulers;

import java.util.List;

import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.schedulers.FrequencyHandler;

public class NewContentWithFrequencyScheduler extends AbstractFrequencyScheduler {

	public NewContentWithFrequencyScheduler(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected SCHEDULING_TYPE getSchedulingType() {
		return SchedulingType.SCHEDULING_TYPE.NewContentWithFrequency;
	}

	@Override
	protected List<FrequencyHandler.Frequency> getFrequencies() {
		return FrequencyHandler.Frequency.getGlobalFrequencies();
	}

}
