package net.violet.platform.management;

import org.apache.log4j.Logger;

/**
 * Classe dérivée de Thread où run est protégé par un try/catch avec log des
 * exceptions au niveau fatal.
 */
public abstract class ThreadWithLog extends Thread {

	/**
	 * Constructeur par défaut.
	 */
	public ThreadWithLog() {
		// This space for rent.
	}

	/**
	 * Constructeur à partir d'une chaîne.
	 * 
	 * @param inName nom du thread.
	 */
	public ThreadWithLog(String inName) {
		super(inName);
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
	 * Point d'entrée du thread.
	 */
	protected abstract void doRun();
}
