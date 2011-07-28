package net.violet.platform.api.maps.applications;

import java.util.Date;
import java.util.Map;

import net.violet.platform.api.actions.applications.GetPackage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationPackageData;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * This map is used to expose the accessible fields of action
 * violet.applications.getPackage in the public API
 */
public class ApplicationPackageMap extends AbstractAPIMap {

	private final static String NAME_FIELD = "name";
	private final static String INTERATIVE_FIELD = "interactive";
	private final static String LANGUAGE_FIELD = "language";
	private final static String APP_CLASS_FIELD = "class";
	private final static String API_FIELD = "api_version";
	private final static String SRC_CODE_FIELD = "code";
	private final static String LAST_MODIFIED_FIELD = "last_modified";

	private static final Logger LOGGER = Logger.getLogger(ApplicationPackageMap.class);

	/**
	 * Constructor for the API method call
	 * 
	 * @param appData
	 * @param appPackageData
	 * @see GetPackage
	 */
	public ApplicationPackageMap(ApplicationData appData, ApplicationPackageData appPackageData) {

		super(10);

		if ((appData == null) || (appPackageData == null)) {
			throw new IllegalArgumentException("Application data or package data cannot be null !");
		}

		put(ApplicationPackageMap.NAME_FIELD, appData.getName());
		put(ApplicationPackageMap.INTERATIVE_FIELD, appData.isInteractive());
		put(ApplicationPackageMap.APP_CLASS_FIELD, appData.getApplicationClass().name());

		put(ApplicationPackageMap.LANGUAGE_FIELD, appPackageData.getSourceType());
		put(ApplicationPackageMap.API_FIELD, appPackageData.getApiVersion());

		put(ApplicationPackageMap.SRC_CODE_FIELD, appPackageData.getSource());
		put(ApplicationPackageMap.LAST_MODIFIED_FIELD, appPackageData.getModificationDate());

		if (ApplicationPackageMap.LOGGER.isDebugEnabled()) {
			ApplicationPackageMap.LOGGER.debug("ApplicationPackageMap : " + this);
		}

	}

	/**
	 * Constructor to read the pojo map in an API response
	 * 
	 * @param inAppPackageMap map received by the API
	 * @throws InvalidParameterException
	 */
	public ApplicationPackageMap(Map<String, Object> inAppPackageMap) throws InvalidParameterException {

		super(inAppPackageMap, true);
	}

	/**
	 * @return
	 */
	public String getApiVersion() {
		return this.getString(ApplicationPackageMap.API_FIELD, null);
	}

	/**
	 * @return
	 */
	public String getName() {
		return this.getString(ApplicationPackageMap.NAME_FIELD, null);
	}

	/**
	 * @return
	 * @throws InvalidParameterException 
	 */
	public Date getReleaseDate() {
		try {
			return this.getDate(ApplicationPackageMap.LAST_MODIFIED_FIELD);

		} catch (final InvalidParameterException e) {
			ApplicationPackageMap.LOGGER.error("Unable to retrieve application package release date " + this, e);
			return new Date();
		}
	}

	/**
	 * @see net.violet.platform.applets.AppDescriptor#getApplicationClass()
	 */
	public ApplicationClass getApplicationClass() {
		return ApplicationClass.getByName(this.getString(ApplicationPackageMap.APP_CLASS_FIELD));
	}

	/**
	 * @return a suitable class name
	 * @see net.violet.platform.applets.AppDescriptor#getClassName()
	 */
	public String getClassName() {
		return "net.violet.js." + StringUtils.replaceChars(this.getString(ApplicationPackageMap.NAME_FIELD), " -*/+", net.violet.common.StringShop.EMPTY_STRING);
	}

	/**
	 * @return the source code of the scriptable application
	 * @see net.violet.platform.applets.AppDescriptor#getSourceCode()
	 */
	public String getSourceCode() {
		return this.getString(ApplicationPackageMap.SRC_CODE_FIELD);
	}

	/**
	 * @see net.violet.platform.applets.AppDescriptor#getLanguage()
	 */
	public String getLanguage() {
		return this.getString(ApplicationPackageMap.LANGUAGE_FIELD);
	}

	/**
	 * @see net.violet.platform.applets.AppDescriptor#isInteractive()
	 */
	public boolean isInteractive() {
		try {
			return this.getBoolean(ApplicationPackageMap.INTERATIVE_FIELD, false);
		} catch (final InvalidParameterException e) {
			return false;
		}
	}

	/**
	 * @see net.violet.platform.applets.AppDescriptor#isNative()
	 */
	public boolean isNative() {
		return ApplicationClass.NATIVE.equals(getApplicationClass());
	}

	/**
	 * API maps should overwrite this method to implement more specific
	 * conformance tests
	 * 
	 * @throws InvalidParameterException
	 */
	@Override
	public void checkConformance() throws InvalidParameterException {
		getDate(ApplicationPackageMap.LAST_MODIFIED_FIELD, true);

	}
}
