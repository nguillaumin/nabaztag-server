package net.violet.platform.api.clients;

import java.util.Map;
import java.util.regex.Pattern;

import net.violet.platform.api.actions.Action.ActionType;
import net.violet.platform.api.exceptions.APIException;

/**
 * Interface for java API clients
 * 
 * @author christophe - Violet
 */
public interface APIClient {

	// regular expression to parse the action name
	Pattern actionNamePattern = Pattern.compile("violet\\.([\\w]+)\\.([\\w]+)");

	/**
	 * The simplest type of call when only one parameter is needed Works only if
	 * Action type is GET (no side effects)
	 * 
	 * @param actionName
	 * @param inMainParamValue
	 * @return the POJO response
	 * @throws APIException
	 */
	Object executeMethodCall(String actionName, String inMainParamValue) throws APIException;

	/**
	 * A more sophisticated call when multiple structured parameters must be
	 * passed
	 * 
	 * @param actionName
	 * @param inParam
	 * @return the POJO response
	 * @throws APIException
	 */
	Object executeMethodCall(ActionType actionType, String actionName, Map<String, Object> inParams) throws APIException;

}
