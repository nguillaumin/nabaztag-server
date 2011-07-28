package net.violet.common;

import java.io.OutputStream;
import java.io.PrintStream;

import net.violet.common.debug.LoggingOutputStream;
import net.violet.common.utils.PropertiesTools;

import org.apache.commons.io.output.NullOutputStream;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Constantes {

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

	protected static final PropertiesTools PROPERTIES = new PropertiesTools();

	static {
		PROPERTIES.load("common.constante.properties");
	}

	public static final int CONNECTION_TIMEOUT = 10000;
	public static final long GC_CYCLE = 180000L;
	public static final int SEND_MAIL_ALERT_BUG = Constantes.PROPERTIES.<Integer> getProperties("SEND_MAIL_ALERT_BUG");

}
