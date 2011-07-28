package net.violet.platform.daemons.schedulers;

import net.violet.platform.datamodel.SchedulingType;

public class DailyScheduler extends AbstractTimelyScheduler {

	private static final SchedulingType.SCHEDULING_TYPE SCHEDULING_TYPE = SchedulingType.SCHEDULING_TYPE.Daily;

	public DailyScheduler(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected SchedulingType.SCHEDULING_TYPE getSchedulingType() {
		return DailyScheduler.SCHEDULING_TYPE;
	}

}
