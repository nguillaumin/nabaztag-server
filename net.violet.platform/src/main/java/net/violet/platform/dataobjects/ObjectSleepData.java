package net.violet.platform.dataobjects;

import java.sql.Time;

public class ObjectSleepData {

	private final Time mWakeUp;
	private final Time mSleep;

	public ObjectSleepData(Time inWakeUp, Time inSleep) {
		this.mWakeUp = inWakeUp;
		this.mSleep = inSleep;
	}

	public Time getWakeUpTime() {
		return this.mWakeUp;
	}

	public Time getSleepTime() {
		return this.mSleep;
	}
}
