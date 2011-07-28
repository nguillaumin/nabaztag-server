package net.violet.platform.daemons.schedulers;

import net.violet.platform.datamodel.SchedulingType;

public class WeeklyScheduler extends AbstractTimelyScheduler {

	private static final SchedulingType.SCHEDULING_TYPE SCHEDULING_TYPE = SchedulingType.SCHEDULING_TYPE.Weekly;

	public WeeklyScheduler(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected SchedulingType.SCHEDULING_TYPE getSchedulingType() {
		return WeeklyScheduler.SCHEDULING_TYPE;
	}

}
