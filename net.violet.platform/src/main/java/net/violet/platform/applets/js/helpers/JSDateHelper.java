package net.violet.platform.applets.js.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.violet.platform.applets.js.JSEnvironment;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * Provides conversions between Java and JavaScript dates
 */
public class JSDateHelper {

	private static final SimpleDateFormat jsDateFmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss 'GMT'ZZ");
	private static final JSEnvironment jsEnv = JSEnvironment.getInstance();

	/**
	 * Convert a NativeDate into a java.util.Date
	 * 
	 * @param jsDate
	 * @return
	 */
	public static Date convertFromJs(Scriptable jsDate) {
		final Context ctx = JSEnvironment.getInstance().getNewContext();
		return JSDateHelper.convertFromJs(jsDate, ctx);

	}

	/**
	 * Convert a NativeDate into a java.util.Date
	 * 
	 * @param jsDate
	 * @return
	 */
	public static Date convertFromJs(Scriptable jsDate, Context ctx) {

		final Double ms;
		Context.enter(ctx);

		try {
			ms = (Double) jsDate.getDefaultValue(Double.class);
		} finally {
			Context.exit();
		}
		final Date javaDate = new Date(ms.longValue());
		return javaDate;
	}

	/**
	 * Create a new Date object in the given Rhino context
	 * 
	 * @param ctx
	 * @param javaDate
	 * @return
	 */
	public static Scriptable createJsDate(Context ctx, Date javaDate) {
		// Create a native JavaScript Date calling the constructor :
		// new Date("Month day, year hour:min:sec GMT+/-")
		final Object[] args = { JSDateHelper.jsDateFmt.format(javaDate) };
		return ctx.newObject(JSDateHelper.jsEnv.getBlankScope(ctx), "Date", args);
	}

}
