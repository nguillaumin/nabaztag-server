package net.violet.platform.applets.js;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;

/**
 * Compile and cache scriptable versions of JavaScript sources
 * 
 * @author christophe - Violet
 */
public final class JsScriptFactory {

	private static final Logger LOGGER = Logger.getLogger(JsScriptFactory.class);

	/**
	 * @param inSrc
	 * @param inFileName
	 * @return
	 * @throws APIException
	 */
	public static Script prepareScript(String inFileName, String inSrc) throws APIException {

		JsScriptFactory.LOGGER.debug("Preparing script " + inFileName + " !");

		if (inSrc == null) {
			JsScriptFactory.LOGGER.error("Applications sources (" + inFileName + ") cannot be null !");
			throw new APIException(APIErrorMessage.INVALID_APPLICATION); // ;
		}

		try {
			final Context ctx = Context.enter();
			ctx.initStandardObjects();

			return ctx.compileString(inSrc, inFileName, 1, null);
		} finally {
			Context.exit();
		}
	}
}
