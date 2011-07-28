package net.violet.platform.api.actions;

import java.util.Collection;
import java.util.List;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application.ApplicationClass;

/**
 * An action must provide a response to an API call the response is a simple
 * POJO object : a map with key/values
 * 
 * @author christophe - Violet
 */
public interface Action {

	/**
	 * Le type d'une action est associé à la méthode HTTP à utiliser dans l'API
	 * REST
	 * 
	 * @author christophe - Violet
	 */
	enum ActionType {
		GET,
		UPDATE,
		CREATE,
		DELETE;

		@Override
		public String toString() {
			return this.name();
		}

	};

	/**
	 * @param params
	 * @return
	 * @throws APIException
	 */
	Object processRequest(ActionParam inParam) throws APIException;

	/**
	 * @return TRUE if the response may be cached by HTTP proxies..
	 */
	boolean isCacheable();

	/**
	 * @return the delay in seconds during which the response may be cached
	 */
	long getExpirationTime();

	/**
	 * @return the list of caller classes authorized to make this call
	 */
	List<APICaller.CallerClass> getAuthorizedCallerClasses();

	/**
	 * @return the list of applications classes authorized to make this call
	 */
	List<ApplicationClass> getAuthorizedApplicationClasses();

	/**
	 * @return the type of the action.
	 */
	ActionType getType();

	/**
	 * @return the name of the action.
	 */
	String getName();

	Class<? extends APIException>[] getErrors();

	Collection<String> getParams();
}
