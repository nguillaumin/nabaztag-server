package net.violet.platform.applets.js.helpers;

import org.apache.log4j.Logger;

/**
 * Provide an access to the Logger tool from JavaScript
 * 
 * @author christophe
 */
public class JSDebug {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(JSDebug.class);

	/**
	 * Send this message to the logger with INFO level
	 * 
	 * @param msg
	 */
	public static void write(String msg) {
		JSDebug.LOGGER.info("JS LOG : " + msg);
	}

}
