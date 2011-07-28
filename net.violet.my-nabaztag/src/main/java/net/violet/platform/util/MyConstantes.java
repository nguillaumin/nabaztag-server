package net.violet.platform.util;

import java.io.OutputStream;
import java.io.PrintStream;

import net.violet.common.debug.LoggingOutputStream;
import net.violet.common.debug.NullOutputStream;
import net.violet.common.utils.PropertiesTools;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MyConstantes {

	// Chargement de log4j.
	static {
		// Tout ce qui va vers stdout est redirigé vers le logger SystemOut au
		// niveau debug.
		final Logger systemOutLogger = LogManager.exists("SystemOut");
		// Si le logger n'existe pas, on ne redirige pas.
		if (systemOutLogger != null) {
			OutputStream theStdoutRedirection;
			// On redirige stdout vers ce flux.
			if (systemOutLogger.isEnabledFor(Level.DEBUG)) {
				theStdoutRedirection = new LoggingOutputStream(systemOutLogger, Level.DEBUG);
			} else {
				theStdoutRedirection = new NullOutputStream();
			}
			System.setOut(new PrintStream(theStdoutRedirection, true));
		}
	}

	private static final PropertiesTools PROPERTIES = new PropertiesTools();
	static {
		PROPERTIES.load("my.constante.properties");
	}

	// 192.168.1.11, .nabaztag.com
	public static final String DOMAIN = MyConstantes.PROPERTIES.getProperties("DOMAIN");

	// 192.168.1.11, 192.168.1.95, r.nabaztag.com
	public static final String STREAM_SERVER = MyConstantes.PROPERTIES.getProperties("STREAM_SERVER");

	// Url du serveur red5
	public static final String RED5_URL_SERVER = MyConstantes.PROPERTIES.getProperties("RED5_URL_SERVER");

	public static final String STAT_WEB = "WEB";

	/* FIN DES SERVICES */

	@Deprecated
	public static final int TIMEOUT_ONEDAY = 86400;
	@Deprecated
	public static final int TIMEOUT_ONEHOUR = 3600;

	/* MAIL POUR SUPPORT */

	public static final String SUPPORT_MAILTO = "franck@violet.net";
	public static final String SUPPORT_MAILFROM = "omevel@violet.net";
	public static final String SUPPORT_SUBJECT = "Erreur plateforme";

}
