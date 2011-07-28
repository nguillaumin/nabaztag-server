package net.violet.platform.daemons.schedulers;

import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;

public class AmbiantWithKeywordScheduler extends AmbiantScheduler {

	private static final SchedulingType.SCHEDULING_TYPE SCHEDULING_TYPE = SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword;

	public AmbiantWithKeywordScheduler(String[] inArgs) {
		super(inArgs);
	}

	@Override
	protected SCHEDULING_TYPE getSchedulingType() {
		return AmbiantWithKeywordScheduler.SCHEDULING_TYPE;
	}

}
