package net.violet.platform.applets.js;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.RhinoConverter;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.applets.AppletException;
import net.violet.platform.applets.api.ScriptableApplet;
import net.violet.platform.applets.tools.Expirable;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Wrap an application instance to be stored in the cache
 * 
 * @author christophe - Violet
 */
public class InterpretedJsApplicationWrapper implements Expirable, ScriptableApplet {

	private static final Logger LOGGER = Logger.getLogger(InterpretedJsApplicationWrapper.class);

	private final JSEnvironment mJsEnv;

	private final String mAppName;
	private final String mApiVersion;
	private final String mApiKey;
	private final Date mAppDate;
	private Scriptable mAppScope;

	/**
	 * @param inAppScript
	 * @param inApiVersion
	 * @throws APIException
	 */
	public InterpretedJsApplicationWrapper(String inAppName, String inApiKey, Date inReleaseDate, boolean inIsTrusted, Script inAppScript, String inApiVersion) {

		this.mAppName = inAppName;
		this.mApiVersion = inApiVersion;
		this.mApiKey = inApiKey;
		this.mAppDate = inReleaseDate;
		this.mJsEnv = JSEnvironment.getInstance();

		// Create the Rhino context for this thread
		final Context cx = this.mJsEnv.getNewContext();

		try {
			// the application scope is the container of all applications objects (functions and variables..)
			// it inherits of all the API library objects by the API scope
			this.mAppScope = this.mJsEnv.getNewApplicationScope(cx, this.mApiVersion, inIsTrusted);
			inAppScript.exec(cx, this.mAppScope);

		} finally {
			Context.exit();
		}
	}

	/**
	 * @see net.violet.platform.applets.api.ScriptableApplet#processEvent(java.lang.String,
	 *      java.lang.Object)
	 */
	public List<Object> processEvent(Map<String, Object> inPojoEvent) throws AppletException {

		if (InterpretedJsApplicationWrapper.LOGGER.isDebugEnabled()) {
			InterpretedJsApplicationWrapper.LOGGER.debug("ENTERING PROCESS EVENT " + inPojoEvent);
		}

		// Create the Rhino context for this thread
		final Context cx = this.mJsEnv.getNewContext();

		try {

			// Get the scriptable application instance
			final ScriptableObject app = this.mJsEnv.getAppInstance(cx, this.mAppScope, this.mApiVersion);

			// Make the call to processEvent()
			final Object[] args = { RhinoConverter.convertToJS(inPojoEvent) };
			final Object resp = ScriptableObject.callMethod(cx, app, "processEvent", args);

			// Convert the native JavaScript structure in POJO
			final Object pojoResponse = RhinoConverter.convertFromJS(resp, true);

			if (InterpretedJsApplicationWrapper.LOGGER.isDebugEnabled()) {
				InterpretedJsApplicationWrapper.LOGGER.debug(this.mAppName + " RETURNED " + pojoResponse);
			}

			if (!(pojoResponse instanceof List)) {
				throw new AppletException("returned processEvent() value is not a list of messages for event " + inPojoEvent + " by application " + this.mAppName);
			}

			return (List<Object>) pojoResponse;

		} catch (final Exception e) {
			final String strErrMsg = "PROCESSING OF EVENT " + inPojoEvent + " BY APPLICATION " + this.mAppName + " RETURNED AN ERROR : " + e.getMessage();
			InterpretedJsApplicationWrapper.LOGGER.warn(strErrMsg, e);
			throw new AppletException(strErrMsg, e);

		} finally {
			Context.exit();
		}
	}

	/**
	 * Just compare the publication date we have here with the date of the repository
	 * @return TRUE if the application is no more up to date
	 * @see net.violet.platform.applets.tools.Expirable#needsRefresh(java.util.Date)
	 */
	public boolean needsRefresh(Date inRepositoryDate) {

		final boolean needsRefresh = ((inRepositoryDate != null) && inRepositoryDate.after(this.mAppDate));
		InterpretedJsApplicationWrapper.LOGGER.debug("Checking if " + this.mAppName + " (" + this.mAppDate + ") needs refresh. New known publication date is " + inRepositoryDate + ". >> " + needsRefresh);

		return needsRefresh;

		/*final boolean fixedDelayRefreshNeeded = super.needsRefresh(inLastKnownPublicationDate);
		if (fixedDelayRefreshNeeded) {
			// do that request as AppResourcesLoader
			final APIClient apiClient = AppResourcesLoader.mSingleInstance.getAPIClient();
			try {
				AppResourcesLoader.mSingleInstance.getApplicationPackage(this.mApiKey, this.mAppDate);*/
		/*
		 * its a strange behaviour but if the API call doesn't return a
		 * NotModifiedException when we pass the last publication date
		 * we know about the application the application NEEDS to be
		 * reloaded !!
		 */
		/*return true;

		} catch (final NotModifiedException no) {
		return false;
		} catch (final APIException e) {
		InterpretedJsApplicationWrapper.LOGGER.error("Unable to make API call to get application package", e);
		return false; // what else could we do ?
		}
		}
		
		return false;*/
	}

	/**
	 * @return the date this application was last published
	 * Will tell us if we need to refresh.
	 * @see net.violet.platform.applets.api.ScriptableApplet#getReleaseDate()
	 */
	public Date getReleaseDate() {
		return this.mAppDate;
	}

}
