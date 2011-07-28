package net.violet.platform.applets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.Action.ActionType;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.AbstractComponentAPICaller;
import net.violet.platform.api.clients.APIClient;
import net.violet.platform.api.clients.XMPPClient;
import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.applications.ApplicationPackageMap;

import org.apache.log4j.Logger;

/**
 * Used by the applet component to retrieve applications resources via API calls
 * 
 * @author christophe - Violet
 */
public final class AppResourcesLoader extends AbstractComponentAPICaller {

	// API client used to make the call (XMPP or HTTP)
	private APIClient mApiClient;

	// FIXME : load this from the application table ?
	private static final String API_KEY = "AppResourcesLoader";
	private static final String API_PWD = "private";

	private static final String IF_MODIFIED_SINCE = "if_modified_since";

	public static AppResourcesLoader LOADER = new AppResourcesLoader();

	private static final Logger LOGGER = Logger.getLogger(AppResourcesLoader.class);

	/**
	 * Private constructor and Singleton pattern
	 */
	private AppResourcesLoader() {
		this.mApiClient = new XMPPClient(this);
		// mApiClient = new RESTClient(APIConstants.REST_SERVICE, this);
	}

	/**
	 * For test purposes only. In production, the only client used will be XMPP.
	 * But i need to debug this over HTTP with a REST client..
	 * 
	 * @param inAPIClient
	 */
	@Deprecated
	public void setAPIClient(APIClient inAPIClient) {
		this.mApiClient = inAPIClient;
	}

	public APIClient getAPIClient() {
		return this.mApiClient;
	}

	/**
	 * Find the javascript source file for this class name
	 * 
	 * @param appPublicKey key of the application
	 * @return the javascript source file
	 * @throws APIException
	 */

	public String getApplicationSources(String appPublicKey) throws APIException {

		final ApplicationPackageMap appPackageMap = getApplicationPackage(appPublicKey);

		return appPackageMap.getSourceCode();
	}

	/**
	 * Find the javascript source file for this class name
	 * 
	 * @param appPublicKey key of the application
	 * @return the javascript source file
	 * @throws APIException
	 */
	public ApplicationPackageMap getApplicationPackage(String appPublicKey) throws APIException {

		Map<String, Object> appPackageMap;
		try {
			appPackageMap = (Map<String, Object>) this.mApiClient.executeMethodCall(APIConstants.GET_PACKAGE_ACTION, appPublicKey);

		} catch (final Exception e) {
			AppResourcesLoader.LOGGER.error("Unable to call " + APIConstants.GET_PACKAGE_ACTION, e);
			throw new APIException(APIErrorMessage.INTERNAL_ERROR, e);
		}

		if (appPackageMap == null) {
			throw new APIException(APIErrorMessage.INVALID_APPLICATION);
		}

		if (AppResourcesLoader.LOGGER.isDebugEnabled()) {
			AppResourcesLoader.LOGGER.debug("Rebuilding application package map with : " + appPackageMap);
		}
		return new ApplicationPackageMap(appPackageMap);
	}

	public ApplicationPackageMap getApplicationPackage(String appPublicKey, Date inLastUpdated) throws APIException {

		final Map<String, Object> params = new PojoMap(2);
		params.put(ActionParam.MAIN_PARAM_KEY, appPublicKey);
		params.put(AppResourcesLoader.IF_MODIFIED_SINCE, inLastUpdated);

		Map<String, Object> appPackageMap;
		try {
			appPackageMap = (Map<String, Object>) this.mApiClient.executeMethodCall(Action.ActionType.GET, APIConstants.GET_PACKAGE_ACTION, params);

		} catch (final Exception e) {
			AppResourcesLoader.LOGGER.error("Unable to call " + APIConstants.GET_PACKAGE_ACTION, e);
			throw new APIException(APIErrorMessage.INTERNAL_ERROR, e);
		}

		if (appPackageMap == null) {
			throw new APIException(APIErrorMessage.INVALID_APPLICATION);
		}

		return new ApplicationPackageMap(appPackageMap);
	}

	/**
	 * Retrieves the source of the given API library version making a distant
	 * call to Violet public API
	 * 
	 * @param inJsApiLibVersion
	 * @return the API sources or an empty String if it doesn't exist or an
	 *         error occured
	 */
	public String getApiSource(String inJsApiLibVersion) {

		Map<String, Object> apiPackageMap;
		final Map<String, Object> params = new HashMap<String, Object>(2);
		try {

			params.put(ActionParam.MAIN_PARAM_KEY, AppLanguages.JAVASCRIPT.name());
			params.put("version", inJsApiLibVersion);
			apiPackageMap = (Map<String, Object>) this.mApiClient.executeMethodCall(ActionType.GET, APIConstants.GET_APILIB_ACTION, params);

		} catch (final Exception e) {
			AppResourcesLoader.LOGGER.error("Unable to call " + APIConstants.GET_APILIB_ACTION, e);
			apiPackageMap = null;
		}

		return (apiPackageMap == null) ? net.violet.common.StringShop.EMPTY_STRING : (String) apiPackageMap.get("code");

	}

	/**
	 * We use the component Class name to key for the API calls
	 * 
	 * @see net.violet.platform.api.callers.APICaller#getAPIKey()
	 */
	public String getAPIKey() {
		return AppResourcesLoader.API_KEY;
	}

	/**
	 * FIXME : password is used only by the REST client to produce the DIGEST
	 * authentication credentials The current implementation use the JdbcRealm
	 * to perform the verification by looking in the application table >> THERE
	 * MUST BE an entry in the application table for this call to work.
	 * 
	 * @see net.violet.platform.api.callers.APICaller#getAPIPassword()
	 */
	@Override
	public String getAPIPassword() {
		return AppResourcesLoader.API_PWD;
	}

	@Override
	public String toString() {
		return "AppResourcesLoader (API Caller class : " + APICaller.CallerClass.COMPONENT.name() + ")";
	}
}
