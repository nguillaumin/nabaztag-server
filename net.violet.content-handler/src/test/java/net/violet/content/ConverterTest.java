package net.violet.content;

import java.io.FileNotFoundException;

import net.violet.common.utils.io.TmpFileManager;

import org.apache.log4j.Logger;

public class ConverterTest {

	private static final Logger LOGGER = Logger.getLogger(ConverterTest.class);

	protected static final TmpFileManager TMP_MANAGER;
	static {
		TmpFileManager theManager = null;
		try {
			theManager = new TmpFileManager(ScriptConstantes.LOCAL_TMP_PATH);
		} catch (final FileNotFoundException e) {
			LOGGER.fatal(e, e);
		}
		TMP_MANAGER = theManager;

	}
}
