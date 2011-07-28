package net.violet.platform.api.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction.APIMethodParam.Level;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.APICaller.CallerClass;
import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Base class for all classes wishing to implement the Action interface.
 */
public abstract class AbstractAction implements Action {

	private static final Logger LOGGER = Logger.getLogger(AbstractAction.class);

	/**
	 * Returns the list of caller classes authorized to call this action.
	 * Default is to allow all callers.
	 * 
	 * @return
	 */
	public List<CallerClass> getAuthorizedCallerClasses() {
		return APICaller.ALL_CALLERS;
	}

	/**
	 * Returns the list of application classes authorized to call this action.
	 * Default is to allow all application classes.
	 * 
	 * @return
	 */
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}

	/**
	 * The only method implementing classes have to implement. This method contains the action behavior and 
	 * is called by the processRequest method.
	 * @param inParam
	 * @return
	 * @throws APIException
	 */
	protected abstract Object doProcessRequest(ActionParam inParam) throws APIException;

	/**
	 * Check authorizations and perform call
	 * 
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	public final Object processRequest(ActionParam inParam) throws APIException {

		if (!isAuthorized(inParam)) {
			throw new ForbiddenException();
		}

		checkParameters(inParam);

		try {
			return doProcessRequest(inParam);
		} catch (final IllegalArgumentException e) {
			final String strErrMsg = "An unexpected error occured when processing the call to " + this.getName() + " (" + inParam + ")";
			AbstractAction.LOGGER.error(strErrMsg, e);
			throw new InternalErrorException(e);
		}

	}

	/**
	 * Returns true if the API Call emitter is allowed to call this action. By
	 * default, allow applications that belong to one of the authorized classes.
	 * 
	 * @param inApplication
	 * @return
	 */
	private boolean isAuthorized(ActionParam inParam) {

		final APICaller theEmitter = inParam.getCaller();
		final CallerClass whosCalling = theEmitter.getCallerClass();
		final boolean authorized = getAuthorizedCallerClasses().contains(whosCalling);

		if (authorized && theEmitter.isApplication()) {
			// Cas simple, on regarde la liste des classes d'applications autorisées.
			final ApplicationClass theClass = theEmitter.getApplicationClass();

			if (Application.ApplicationClass.NATIVE == theClass) {
				return true;
			}

			return getAuthorizedApplicationClasses().contains(theClass);
		}

		return authorized;
	}

	/**
	 * This annotation should be used to indicate which parameters are supported by the action.
	 * 
	 * Let's consider an Action A taking three parameters x,y and z. x and y are required, z is optional. 
	 * Here is the code that should appear in the concret class A :
	 * <code>class A extends AbstractAction {
	 * @APIMethodParam(REQUIRED)
	 * 	private statif final String xParam = "x";
	 * @APIMethodParam
	 * 	private statif final String yParam = "y";
	 * @APIMethodParam(OPTIONAL)
	 * 	private statif final String zParam = "z";
	 * 
	 * ...
	 * }
	 * </code>
	 * As we can see above, the default level for the annotation is REQUIRED.
	 * NB : the fields' names are worthless, only the values are important.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value = { ElementType.FIELD })
	public @interface APIMethodParam {

		Level value() default Level.REQUIRED;

		public static enum Level {
			REQUIRED,
			OPTIONAL
		}
	}

	/**
	 * Performs the automatic 'parameters' presency check' according to the fields tagged with the @APIMethodParam annotation.
	 * @param inParam
	 * @throws MissingParameterException if one of the tagged field is missing
	 */
	private void checkParameters(ActionParam inParam) throws MissingParameterException {
		for (final Field aField : getClass().getDeclaredFields()) {
			final APIMethodParam theAnnotation = aField.getAnnotation(APIMethodParam.class);
			if ((theAnnotation != null) && (theAnnotation.value() == Level.REQUIRED)) {
				aField.setAccessible(true);
				try {
					final String paramName = aField.get(this).toString();
					if (!inParam.hasParam(paramName)) {
						throw new MissingParameterException(paramName);
					}
				} catch (final IllegalArgumentException e) {
					AbstractAction.LOGGER.fatal(e, e);
				} catch (final IllegalAccessException e) {
					AbstractAction.LOGGER.fatal(e, e);
				}
				aField.setAccessible(false);
			}
		}
	}

	@Override
	public String toString() {
		return this.getType() + " " + this.getName();
	}

	public final String getName() {
		final String[] theFullClassName = getClass().getName().split("\\.");
		final String thePackageName = theFullClassName[theFullClassName.length - 2];
		final String theMethodName = StringUtils.uncapitalize(theFullClassName[theFullClassName.length - 1]);
		return APIConstants.VIOLET_PREFIX + thePackageName + "." + theMethodName;
	}

	public final Class<? extends APIException>[] getErrors() {
		try {
			final Method method = getClass().getDeclaredMethod("doProcessRequest", ActionParam.class);
			return (Class<? extends APIException>[]) method.getExceptionTypes();
		} catch (final SecurityException anException) {
			AbstractAction.LOGGER.fatal(anException, anException);
			return null;
		} catch (final NoSuchMethodException anException) {
			AbstractAction.LOGGER.fatal(anException, anException);
			return null;
		}
	}

	public final Collection<String> getParams() {
		try {
			final List<String> theResult = new LinkedList<String>();
			for (final Field aField : getClass().getDeclaredFields()) {
				final APIMethodParam theAnnotation = aField.getAnnotation(APIMethodParam.class);
				if (theAnnotation != null) {
					aField.setAccessible(true);
					try {
						final Object theValue = aField.get(null);
						if (theValue instanceof String) {
							theResult.add(theValue.toString() + "[" + theAnnotation.value().name() + "]");
						} else {
							AbstractAction.LOGGER.fatal("Param name is not a string! [" + getClass() + ", " + aField.getName() + "]");
						}
					} catch (final IllegalArgumentException e) {
						AbstractAction.LOGGER.fatal(e, e);
					} catch (final IllegalAccessException e) {
						AbstractAction.LOGGER.fatal(e, e);
					}
					aField.setAccessible(false);
				}
			}
			return theResult;

		} catch (final SecurityException anException) {
			AbstractAction.LOGGER.fatal(anException, anException);
			return null;
		}
	}

	/**
	 * Récupère l'utilisateur à partir du nom du paramètre.
	 * 
	 * @param inParam Structure pour les paramètres.
	 * @param inParamName Nom du paramètre ou <code>null</code> si le paramètre
	 *            est le paramètre principal.
	 * @return Une référence vers l'utilisateur.
	 * @throws InvalidParameterException
	 * @throws NoSuchApplicationException
	 */
	protected final UserData getRequestedUser(ActionParam inParam, String inParamName) throws InvalidParameterException, NoSuchPersonException {

		final String userId = (inParamName == null) ? inParam.getMainParamAsString() : inParam.getString(inParamName, true);

		final UserData userData = UserData.findByAPIId(userId, inParam.getCallerAPIKey());

		if (userData == null) {
			throw new NoSuchPersonException(APIErrorMessage.NO_SUCH_PERSON);
		}

		if (AbstractAction.LOGGER.isDebugEnabled()) {
			AbstractAction.LOGGER.debug("getRequestedUser(" + userId + ") returned " + userData.getUser_email());
		}

		return userData;
	}

	/**
	 * Récupère l'application à partir du nom du paramètre.
	 * 
	 * @param inParam Structure pour les paramètres.
	 * @param inParamName Nom du paramètre ou <code>null</code> si le paramètre
	 *            est le paramètre principal.
	 * @return Une référence vers l'application.
	 * @throws InvalidParameterException
	 * @throws NoSuchApplicationException
	 */
	protected final ApplicationData getRequestedApplication(ActionParam inParam, String inParamName) throws InvalidParameterException, NoSuchApplicationException {

		final String appId;
		if (inParamName == null) {
			appId = inParam.getMainParamAsString();
		} else {
			appId = inParam.getString(inParamName, true);
		}

		final ApplicationData app;
		final APICaller caller = inParam.getCaller();

		/**
		 * look for the requested application : the parameter passed is
		 * different depending of who made the call
		 */
		if (caller.isApplication()) {
			// an application has received an encoded ID for other
			// application/object/.. he may asked about
			// TODO : pass the 'application native' parameter to ensure further
			// ID verification
			app = ApplicationData.findByAPIId(appId, caller.getAPIKey(), true);

		} else if (caller.isPlatformComponent()) {
			// platform components can uses directly the application public key
			final ApplicationCredentialsData credData = ApplicationCredentialsData.findByPublicKey(appId);
			if (credData != null) {
				app = credData.getApplication();
			} else {
				throw new NoSuchApplicationException();
			}

		} else { // TODO : user caller
			throw new NoSuchApplicationException();
		}

		if (app == null) {
			throw new NoSuchApplicationException();
		}

		if (AbstractAction.LOGGER.isDebugEnabled()) {
			AbstractAction.LOGGER.debug("getRequestedApplication(" + appId + ") returned " + app.getName());
		}

		return app;
	}

	/**
	 * Récupère l'objet à partir du nom du paramètre.
	 * 
	 * @param inParam Structure pour les paramètres.
	 * @param inParamName Nom du paramètre ou <code>null</code> si le paramètre
	 *            est le paramètre principal.
	 * @return Une référence vers l'objet.
	 * @throws InvalidParameterException
	 * @throws NoSuchObjectException
	 */
	protected final VObjectData getRequestedObject(ActionParam inParam, String inParamName) throws InvalidParameterException, NoSuchObjectException {

		final String objectId = (inParamName == null) ? inParam.getMainParamAsString() : inParam.getString(inParamName, true);

		final VObjectData object = VObjectData.findByAPIId(objectId, inParam.getCallerAPIKey());
		if ((object == null) || !object.isValid()) {
			throw new NoSuchObjectException();
		}

		if (AbstractAction.LOGGER.isDebugEnabled()) {
			AbstractAction.LOGGER.debug("getRequestedObject(" + objectId + ") returned " + object.getObject_login());
		}

		return object;
	}

}
