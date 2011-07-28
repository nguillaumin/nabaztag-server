package net.violet.platform.daemons.schedulers;

import net.violet.platform.datamodel.SchedulingType;

public class DailyWithDuration extends AbstractTimelyScheduler {

	private static final SchedulingType.SCHEDULING_TYPE SCHEDULING_TYPE = SchedulingType.SCHEDULING_TYPE.DailyWithDuration;

	public DailyWithDuration(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected SchedulingType.SCHEDULING_TYPE getSchedulingType() {
		return DailyWithDuration.SCHEDULING_TYPE;
	}

}
