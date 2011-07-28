package net.violet.content;

import java.io.OutputStream;
import java.io.PrintStream;

import net.violet.common.debug.LoggingOutputStream;
import net.violet.common.utils.PropertiesTools;

import org.apache.commons.io.output.NullOutputStream;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ScriptConstantes {

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
		PROPERTIES.load("script.constante.properties");
	}

	/* CONFIG */

	public static final String SCRIPT_PATH = ScriptConstantes.PROPERTIES.getProperties("SCRIPT_PATH");

	public static final String LOCAL_TMP_PATH = System.getProperty("java.io.tmpdir");

	public static final String JOIN_MP3 = SCRIPT_PATH + "joinmp3.sh";
	public static final String RSCMNG_WAV2ADP = SCRIPT_PATH + "wav2adp.sh";
	public static final String FFMPEG = SCRIPT_PATH + "ffmpeg.sh";
	public static final String FFMPEG_SPLIT = SCRIPT_PATH + "splitter.sh";
	public static final String FFMPEG_MERGE = SCRIPT_PATH + "join.sh";
	/* FIN CONFIG */

	public static final int NB_ADP = ScriptConstantes.PROPERTIES.<Integer> getProperties("NB_ADP");

	public static final int NB_FFMPEG = ScriptConstantes.PROPERTIES.<Integer> getProperties("NB_FFMPEG");

	public static final int NB_JOIN = ScriptConstantes.PROPERTIES.<Integer> getProperties("NB_JOIN");

}
