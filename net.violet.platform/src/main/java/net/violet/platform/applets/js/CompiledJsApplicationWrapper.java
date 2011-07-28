package net.violet.platform.applets.js;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.RhinoConverter;
import net.violet.platform.applets.AppletException;
import net.violet.platform.applets.api.ScriptableApplet;
import net.violet.platform.applets.tools.Expirable;

/**
 * @author christophe - Violet
 */
public class CompiledJsApplicationWrapper implements Expirable, ScriptableApplet {

	private final JSEnvironment mJsEnv;

	private final RhinoInteractiveApplication mRhinoAppInstance;
	private final String mAppName;
	private final Date mAppPublication;

	/**
	 * @param appScript
	 * @param apiVersion
	 */
	public CompiledJsApplicationWrapper(String inAppName, Date inAppPublicationDate, RhinoInteractiveApplication inRhinoApp) {
		this.mAppName = inAppName;
		this.mAppPublication = inAppPublicationDate;
		this.mRhinoAppInstance = inRhinoApp;
		this.mJsEnv = JSEnvironment.getInstance();
	}

	/**
	 * This wrapper method just convert the in and out parameters from POJO
	 * structure to Rhino Scriptable
	 * 
	 * @throws AppletException
	 * @see net.violet.platform.applets.api.ScriptableApplet#processEvent(java.lang.String,
	 *      java.lang.Object)
	 */
	public List<Object> processEvent(Map<String, Object> inPojoEvent) throws AppletException {

		try {
			final Object result = this.mRhinoAppInstance.processEvent(RhinoConverter.convertToJS(inPojoEvent));
			return (List<Object>) RhinoConverter.convertFromJS(result, false);

		} catch (final ConversionException e) {
			throw new AppletException("Processing of event " + inPojoEvent + " by application " + this.mAppName + " returned an invalid response : " + e.getMessage(), e);

		} catch (final RuntimeException e) {
			throw new AppletException("Processing of event " + inPojoEvent + " by application " + this.mAppName + " returned an execution error : " + e.getMessage(), e);
		}
	}

	/**
	 * @return TRUE if this version is no more up to date compared to the repository
	 * @see net.violet.platform.applets.tools.Expirable#needsRefresh(java.util.Date)
	 */
	public boolean needsRefresh(Date inRepositoryDate) {
		return ((inRepositoryDate != null) && inRepositoryDate.after(this.mAppPublication));
	}

	/**
	 * @return the release Date of this version
	 * @see net.violet.platform.applets.api.ScriptableApplet#getReleaseDate()
	 */
	public Date getReleaseDate() {
		return this.mAppPublication;
	}

}
