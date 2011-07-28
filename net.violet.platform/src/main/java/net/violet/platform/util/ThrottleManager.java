package net.violet.platform.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

/**
 * A throttle is used to prevent code from attempting a particular operation too
 * often. Often it is desirable to retry an operation under failure conditions,
 * but simplistic approaches to retrying operations can lead to large numbers of
 * spurious attempts to do something that will obviously fail. The throttle
 * class provides a mechanism for limiting such attempts by measuring whether or
 * not an activity has been performed N times in the last M seconds. The user of
 * the class decides the appropriate throttle parameters and then simply calls
 * through to throttle to determine whether or not to go ahead with the
 * operation.
 */
public class ThrottleManager<T> {

	public static class Throttle {

		private final long[] mOps;
		private int mLastOp;

		/**
		 * Constructs a new throttle instance that will allow the specified
		 * number of operations to proceed within the specified period (the
		 * period is measured in milliseconds).
		 * <p>
		 * As operations and period define a ratio, use the smallest value
		 * possible for <code>operations</code> as an array is created to track
		 * the time at which each operation was performed (e.g. use 6 ops per 10
		 * seconds rather than 60 ops per 100 seconds if possible). However,
		 * note that you may not always want to reduce the ratio as much as
		 * possible if you wish to allow bursts of operations up to some large
		 * value.
		 */
		Throttle(ThrottleProfile inProfile) {
			this.mOps = new long[inProfile.getAmountOfOperations()];
		}

		/**
		 * Registers an attempt at an operation and returns true if the
		 * operation should be performed (filtered) or false if it should not.
		 * 
		 * @return true if the throttle is activated, false if the operation can
		 *         proceed.
		 */
		boolean throttleOp(long inPeriod) {
			final long timeStamp = System.currentTimeMillis();
			/*
			 * First we check to see if we would throttle an operation occuring
			 * at the specified timestamp. Typically used in conjunction with
			 * {@link #noteOp}.
			 */
			// if the oldest operation was performed less than inPeriod ago, we
			// need to throttle
			synchronized (this.mOps) {
				final long elapsed = timeStamp - this.mOps[this.mLastOp];
				// if negative time elapsed, we must be running on windows;
				// let's just cope by not
				// throttling
				if ((elapsed >= 0) && (elapsed < inPeriod)) {
					return true;
				}

				// Notes that an operation occurred at the specified timestamp,
				// overwrites the oldest operation with the current time and
				// move the oldest operation
				// pointer to the second oldest operation (which is now the
				// oldest as we overwrote the
				// oldest)
				this.mOps[this.mLastOp] = timeStamp;
				this.mLastOp = (this.mLastOp + 1) % this.mOps.length;
			}
			return false;
		}

		/**
		 * Returns the timestamp of the most recently recorded operation.
		 */
		public long getLatestOperation() {
			return this.mOps[(this.mLastOp + this.mOps.length - 1) % this.mOps.length];
		}
	}

	/**
	 * Class used to identify a client
	 */
	public static class ThrottleProfile {

		private final int mAmountOfOperations;
		private final long mPeriod;

		public ThrottleProfile(int inAmountOfOperations, long inPeriod) {
			this.mAmountOfOperations = inAmountOfOperations;
			this.mPeriod = inPeriod;
		}

		/**
		 * @return the mAmountOfOperations
		 */
		public int getAmountOfOperations() {
			return this.mAmountOfOperations;
		}

		/**
		 * @return the mPeriod
		 */
		public long getPeriod() {
			return this.mPeriod;
		}
	}

	private final ThrottleProfile mDefaultProfile;
	private final Map<T, Throttle> THROTTLE_MANAGER = new HashMap<T, Throttle>();
	private long mDestructionPeriod;
	private final Timer mTimer = new Timer();
	private final TimerTask theTask = new TimerTask() {

		@Override
		public void run() {
			final long BestBefore = System.currentTimeMillis() - ThrottleManager.this.mDestructionPeriod;
			synchronized (ThrottleManager.this.THROTTLE_MANAGER) {

				for (final Iterator<Entry<T, Throttle>> theIterator = ThrottleManager.this.THROTTLE_MANAGER.entrySet().iterator(); theIterator.hasNext();) {
					final Entry<T, Throttle> anEntry = theIterator.next();

					if (anEntry.getValue().getLatestOperation() < BestBefore) {
						theIterator.remove();
					}

				}
			}
		}
	};

	public ThrottleManager(ThrottleProfile inDefaultProfile) {
		this.mDefaultProfile = inDefaultProfile;
		this.mDestructionPeriod = this.mDefaultProfile.getPeriod();
		this.mTimer.scheduleAtFixedRate(this.theTask, this.mDestructionPeriod, this.mDestructionPeriod);
	}

	public boolean isOperationAllowed(T inClient) {
		return isOperationAllowed(inClient, this.mDefaultProfile);
	}

	public boolean isOperationAllowed(T inClient, ThrottleProfile inProfile) {
		if (inClient == null) {
			return false;
		}

		Throttle theThrottle;
		synchronized (this.THROTTLE_MANAGER) {
			theThrottle = this.THROTTLE_MANAGER.get(inClient);

			if (theThrottle == null) {
				theThrottle = new Throttle(inProfile);
				this.THROTTLE_MANAGER.put(inClient, theThrottle);
				if (inProfile.getPeriod() > this.mDestructionPeriod) {
					this.mDestructionPeriod = inProfile.getPeriod();
					this.mTimer.cancel();
					this.mTimer.scheduleAtFixedRate(this.theTask, this.mDestructionPeriod, this.mDestructionPeriod);
				}
			}
		}

		return !theThrottle.throttleOp(inProfile.getPeriod());
	}

	public static <R> ThrottleManager<R> getRessourcesThrottle(ThrottleProfile inProfile) {
		return new ThrottleManager<R>(inProfile);
	}
}
