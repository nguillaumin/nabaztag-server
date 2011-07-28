package net.violet.common.utils.concurrent;

import org.apache.log4j.Logger;

/**
 * Used to wait for the termination of an initially undetermined amount of
 * {@link Thread}s
 */
public class ThreadWatcher {

	private static final Logger LOGGER = Logger.getLogger(ThreadWatcher.class);

	private long workingThread;

	/**
	 * Tells this {@link ThreadWatcher} to look after one more {@link Thread}'s
	 * termination
	 */
	public void incrementAmountWorkingThreads() {
		synchronized (this) {
			this.workingThread++;
		}
	}

	/**
	 * Tells this {@link ThreadWatcher} that a {@link Thread} it was looking
	 * after has terminated
	 */
	public void countDown() {
		synchronized (this) {
			this.workingThread--;

			if (this.workingThread == 0) {
				notify();
			}
		}
	}

	/**
	 * Make the caller wait until completion of all the {@link Thread} watched
	 */
	public void await() {
		synchronized (this) {
			while (this.workingThread != 0) {
				try {
					wait();
				} catch (final InterruptedException e) {
					ThreadWatcher.LOGGER.fatal(e, e);
				}
			}

		}
	}

	public void reset() {
		synchronized (this) {
			this.workingThread = 0;
			notify();
		}
	}
}
