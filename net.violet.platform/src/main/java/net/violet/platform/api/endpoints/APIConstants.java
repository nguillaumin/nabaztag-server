package net.violet.platform.api.endpoints;

import net.violet.platform.util.Constantes;

/**
 * @author christophe - Violet
 */
public interface APIConstants {

	String HTTPS = "https";

	// the authentication realm for the API
	String API_REALM = "api.violet.net";

	// HTTP entry point : concatenation of host, port and context (optionaly)
	String REST_SERVICE = Constantes.API_SERVICE + "/rest";

	// credentials for an internal REST client call
	String REST_CLIENT_KEY = "RESTClient";
	String REST_CLIENT_PWD = "private";

	// in HTTPS, the API credentials keys can be passed as simple
	// request parameters or headers under this keys :
	String API_KEY = "api_key";
	String API_SIGN = "api_sign";

	String VIOLET_PREFIX = "violet.";

	// action names
	String GET_PACKAGE_ACTION = "violet.applications.getPackage";
	String GET_SUBSCRIPTIONS_ACTION = "violet.subscriptions.get";
	String GET_APILIB_ACTION = "violet.apilib.get";

}
