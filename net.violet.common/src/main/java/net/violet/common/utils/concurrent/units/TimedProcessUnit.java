package net.violet.common.utils.concurrent.units;

import net.violet.common.utils.concurrent.ThreadWatcher;

public abstract class TimedProcessUnit<M, C, R> extends AbstractProcessUnit<M, C, R> {

	private final long mTime2Run;

	public TimedProcessUnit(M inMaterial, C inCondition, long inTime2Wait, ThreadWatcher inWatcher) {
		super(inMaterial, inCondition, inWatcher);
		this.mTime2Run = System.currentTimeMillis() + inTime2Wait;
	}

	public long getRemainingTime2Wait() {
		final long now = System.currentTimeMillis();

		return (now < this.mTime2Run) ? (this.mTime2Run - now) : 0L;
	}
}
