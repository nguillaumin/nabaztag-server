package net.violet.platform.management;

import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * Classe dérivée de TimerTask où run est protégé par un try/catch avec log des
 * exceptions au niveau fatal.
 */
public abstract class TimerTaskWithLog extends TimerTask {

	/**
	 * Constructeur par défaut.
	 */
	public TimerTaskWithLog() {
		// This space for rent.
	}

	@Override
	public final void run() {
		try {
			doRun();
		} catch (final Throwable aThrowable) {
			final Logger theLogger = Logger.getLogger(this.getClass());
			theLogger.fatal(aThrowable, aThrowable);
		}
	}

	/**
	 * Point d'entrée du timer.
	 */
	protected abstract void doRun();
}
