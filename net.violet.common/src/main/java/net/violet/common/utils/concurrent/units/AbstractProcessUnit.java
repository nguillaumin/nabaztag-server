package net.violet.common.utils.concurrent.units;

import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.wrapper.ContentWrapper;

public abstract class AbstractProcessUnit<M, C, R> extends ContentWrapper<M> {

	protected enum STATE {
		PENDING,
		PROCESSING,
		PROCESSED,
		ERROR
	};

	private STATE mStatus;
	private final C mCondition;
	private R mResult;

	private final ThreadWatcher mThreadWatcher;

	protected AbstractProcessUnit(M inMaterial, C inCondition, ThreadWatcher inThreadWatcher) {
		super(inMaterial);
		this.mStatus = STATE.PENDING;
		this.mThreadWatcher = inThreadWatcher;
		this.mCondition = inCondition;
	}

	/**
	 * Sets the current state of the {@link AbstractProcessUnit} to
	 * {@link STATE.ERROR}
	 */
	public final void setError() {
		this.mStatus = STATE.ERROR;
	}

	public final boolean isError() {
		return this.mStatus == STATE.ERROR;
	}

	/**
	 * Sets the current state of the {@link AbstractProcessUnit} to
	 * {@link STATE.PROCESSED}
	 */
	public final void processed() {
		this.mStatus = STATE.PROCESSED;
	}

	public final void processing() {
		this.mStatus = STATE.PROCESSING;
	}

	/**
	 * Returns the current state of the {@link AbstractProcessUnit}
	 * 
	 * @return
	 */
	public final STATE getState() {
		return this.mStatus;
	}

	/**
	 * Tells the embeded {@link ThreadWatcher} to start looking after this
	 * {@link MessageUnit}
	 * 
	 * @return whether or not the action was performed
	 */
	public boolean startWatching() {
		final ThreadWatcher theWatcher = getThreadWatcher();
		if (theWatcher != null) {
			theWatcher.incrementAmountWorkingThreads();
			return true;
		}
		return false;
	}

	/**
	 * Tells the embeded {@link ThreadWatcher} to stop looking after this
	 * {@link MessageUnit}
	 * 
	 * @return whether or not the action was performed
	 */
	public boolean stopWatching() {
		final ThreadWatcher theWatcher = getThreadWatcher();
		if (theWatcher != null) {
			theWatcher.countDown();
			return true;
		}
		return false;
	}

	protected ThreadWatcher getThreadWatcher() {
		return this.mThreadWatcher;
	}

	public C getProcessConditioner() {
		return this.mCondition;
	}

	public R getResult() {
		return this.mResult;
	}

	public void setResult(R inResult) {
		this.mResult = inResult;
	}

}
