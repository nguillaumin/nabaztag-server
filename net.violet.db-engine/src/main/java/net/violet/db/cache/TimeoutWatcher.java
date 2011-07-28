package net.violet.db.cache;

import org.apache.log4j.Logger;

abstract class TimeoutWatcher extends Thread {

	private static final Logger LOGGER = Logger.getLogger(TimeoutWatcher.class);

	private volatile long mLastUpdate;
	private volatile boolean mQuit;
	private final long mTimeout;

	public TimeoutWatcher(long inTimeout) {
		this.mTimeout = inTimeout;
	}

	public void update() {
		synchronized (this) {
			this.mLastUpdate = System.currentTimeMillis();
			notify();
		}
	}

	@Override
	public void run() {

		synchronized (this) {
			do {
				try {
					wait(this.mTimeout);
				} catch (final InterruptedException e) {
					TimeoutWatcher.LOGGER.fatal(e, e);
				}

				if ((System.currentTimeMillis() - this.mLastUpdate) >= this.mTimeout) {
					timeoutHandling();
				}

			} while (!this.mQuit);
		}
	}

	public abstract void timeoutHandling();

	public void quit() {
		this.mQuit = true;

		synchronized (this) {
			notify();
		}
	}
}
