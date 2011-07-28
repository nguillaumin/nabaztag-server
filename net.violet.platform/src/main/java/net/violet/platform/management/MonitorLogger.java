package net.violet.platform.management;

import org.apache.log4j.Logger;

/**
 * Classe pour envoyer des messages de monitoring.
 */
public class MonitorLogger {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(MonitorLogger.class);

	public static void sendReport(String inBody) {
		// MailTools.sendSupport("Platform Report", inBody);
		MonitorLogger.LOGGER.info(inBody);
	}
}
