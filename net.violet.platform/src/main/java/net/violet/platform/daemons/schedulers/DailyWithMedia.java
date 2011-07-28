package net.violet.platform.daemons.schedulers;

import net.violet.platform.datamodel.SchedulingType;

public class DailyWithMedia extends AbstractTimelyScheduler {

	private static final SchedulingType.SCHEDULING_TYPE SCHEDULING_TYPE = SchedulingType.SCHEDULING_TYPE.DailyWithMedia;

	public DailyWithMedia(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected SchedulingType.SCHEDULING_TYPE getSchedulingType() {
		return DailyWithMedia.SCHEDULING_TYPE;
	}

}
