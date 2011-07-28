package net.violet.platform.applets.js;

import java.lang.reflect.Member;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.clients.APIClient;
import net.violet.platform.api.clients.HttpProxyClient;
import net.violet.platform.api.clients.MailProxyClient;
import net.violet.platform.api.clients.RESTClient;
import net.violet.platform.api.clients.XMPPClient;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.RhinoConverter;
import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.applets.api.ApplicationEvent;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.continuations.Continuation;

/**
 * Proxy object to make platform calls like - API calls, HTTP calls and mail
 * send available from within the JavaScript applications 
 * The exposed methods are created in the API library as functions of the object violet.__proxy__
 * 
 * @author christophe - Violet
 */
public class JSProxy {

	private static final Logger LOGGER = Logger.getLogger(JSProxy.class);

	private static boolean mUseRest;

	/**
	 * Map of exposed methods
	 */
	public final static Map<String, Member> mExposedMethods;

	/**
	 * static initialization loop
	 */
	static {
		final String[] exposedMethodNames = { "makeAPICall", "makeHttpCall", "sendMail" };
		final Class[][] methodParams = { { Object.class, String.class, Object.class }, { Object.class, String.class, ScriptableObject.class, ScriptableObject.class }, { Object.class, ScriptableObject.class, ScriptableObject.class, String.class, String.class } };

		mExposedMethods = new HashMap<String, Member>();
		int i = 0;

		for (final String methodName : exposedMethodNames) {
			try {
				final Member exposedMethod = JSProxy.class.getDeclaredMethod(methodName, methodParams[i++]);
				JSProxy.mExposedMethods.put(methodName, exposedMethod);

			} catch (final Exception e) {
				JSProxy.LOGGER.fatal("Unable to add reference to exposed method " + methodName + " ! Signature may have changed.", e);
			}
		}
	}

	/**
	 * A wrapper around the context of the event that can be reconstitued from a
	 * Continuation, or from informations passed directly by the trusted
	 * (native) applications
	 * 
	 * @author christophe - Violet
	 */
	static class EventContextWrapper implements ApplicationEvent {

		private String mApiKey;
		private PojoMap mEvent;

		// use lazy initialization for these ones
		VObjectData mObject = null;
		VObjectData mReader = null;

		EventContextWrapper(Object inContext) {

			if (!(inContext instanceof Continuation)) {
				Map<String, Object> contextMap;
				try {
					contextMap = (Map<String, Object>) RhinoConverter.convertFromJS(inContext, true);
				} catch (final ConversionException e) {
					JSProxy.LOGGER.error("Invalid event context object when making proxy call", e);
					contextMap = Collections.emptyMap();
				}
				this.mApiKey = (String) contextMap.get("apiKey");
				this.mEvent = new PojoMap((Map<String, Object>) contextMap.get("event"));;
			}
		}

		/**
		 * @return the API Key of the application
		 * @throws InvalidParameterException
		 */
		public String getApplicationPublicKey() {
			return this.mApiKey;
		}

		/**
		 * @return the API Key of the application
		 * @throws InvalidParameterException
		 */
		public ApplicationData getApplication() {
			if (this.mApiKey == null) {
				return null;
			}

			return ApplicationCredentialsData.findByPublicKey(this.mApiKey).getApplication();
		}

		/**
		 * @return a map of the event with the event context (application state)
		 */
		public PojoMap getEvent() {
			return this.mEvent;
		}

		/**
		 * @return the object that generated the interactive event
		 * @throws InvalidParameterException
		 */
		public VObjectData getObject() {
			if (this.mObject == null) {
				final String encryptedObjectId = this.mEvent.getString("trigger.object.id", null);
				if (encryptedObjectId != null) {
					this.mObject = VObjectData.findByAPIId(encryptedObjectId, getApplicationPublicKey());
				}
			}
			return this.mObject;
		}

		/**
		 * @return the object's owner or NULL if no ztamp was involved
		 * @throws InvalidParameterException
		 */
		public UserData getObjectOwner() {
			getObject();
			return (this.mObject == null) ? null : this.mObject.getOwner();
		}

		/**
		 * WARNING : If we have used a target, but the reader object is the same as the declared target, 
		 * there is no reader property in the trigger event map > use the target.
		 * If we didn't force a target, there is no reader property either : the target is ALLWAYS the reader.
		 * @return the Reader object.
		 * @throws InvalidParameterException
		 * @throws InvalidParameterException
		 */
		public VObjectData getReader() {
			if (this.mReader == null) {
				final String encryptedObjectId = this.mEvent.getString("trigger.reader.id", this.mEvent.getString("trigger.target.id", null));
				if (encryptedObjectId != null) {
					this.mReader = VObjectData.findByAPIId(encryptedObjectId, getApplicationPublicKey());
				}
			}
			return this.mReader;
		}

		@Override
		public String toString() {
			return "apiKey : " + this.mApiKey + ", sender : " + this.getObject() + ", event : " + this.mEvent;
		}
	}

	/**
	 * @param methodName
	 * @return
	 */
	public static Member getExposedMethod(String methodName) {
		return JSProxy.mExposedMethods.get(methodName);
	}

	/**
	 * @param methodName
	 * @return
	 */
	public static Set<String> getExposedMethodNames() {
		return JSProxy.mExposedMethods.keySet();
	}

	/**
	 * Note : use for local tests only using tomcat
	 * 
	 * @param useRest TRUE to use the REST client (XMPP client used by default)
	 */
	@Deprecated
	public static void useRestMode(boolean useRest) {
		JSProxy.mUseRest = useRest;
	}

	/**
	 * Do the Rhino to POJO conversions of parameters and responses maps 
	 * IMPORTANT NOTE : if the signature of this method changes, 
	 * be sure to report the changes in the static initialization block
	 * 
	 * @param inPublicKey
	 * @param inActionName
	 * @param inParams
	 * @return
	 * @throws ConversionException
	 * @throws APIException
	 */
	public static Object makeAPICall(Object inEventContext, String inActionName, Object inParams) {

		final EventContextWrapper eventContext;
		Map<String, Object> apiParams = null;
		Object response;

		try {

			eventContext = new EventContextWrapper(inEventContext);
			apiParams = (Map<String, Object>) RhinoConverter.convertFromJS(inParams, true);

			// Make the API call using the application credentials
			final String appPublicKey = eventContext.getApplicationPublicKey();
			final ApplicationCredentialsData credentials = ApplicationCredentialsData.findByPublicKey(appPublicKey);
			final APICaller caller = new ApplicationAPICaller(credentials);
			final APIClient client = JSProxy.mUseRest ? new RESTClient(APIConstants.REST_SERVICE, caller) : new XMPPClient(caller);
			response = client.executeMethodCall(Action.ActionType.UPDATE, inActionName, apiParams);

		} catch (final APIException e) {
			response = e;

		} catch (final ConversionException e) {
			final String strErrMsg = "Some conversion error occured when making API call to " + inActionName + "(" + apiParams + ")";
			JSProxy.LOGGER.error(strErrMsg, e);
			response = new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER_BECAUSE, "unknown", strErrMsg);

		} catch (final RuntimeException e) {
			final String strErrMsg = "Some conversion error occured when making API call to " + inActionName + "(" + apiParams + ")";
			JSProxy.LOGGER.error(strErrMsg, e);
			response = new InternalErrorException(strErrMsg);
		}

		return RhinoConverter.convertToJS(response);
	}

	/**
	 * Do the Rhino to POJO conversions of parameters and responses maps 
	 * IMPORTANT NOTE : if the signature of this method changes, 
	 * be sure to report the changes in JSProxy#getMakeHttpCallMethod()
	 * 
	 * @param inUrl
	 * @param inJsParams
	 * @param inJsOptions
	 * @return a JS object with the status code and body of the response
	 */
	public static Scriptable makeHttpCall(Object inEventContext, String inUrl, ScriptableObject inJsParams, ScriptableObject inJsOptions) {

		// EventContextWrapper eventContext = null;
		Map<String, Object> params = null;
		Map<String, Object> options = null;
		Map<String, Object> response;

		try {
			params = (Map<String, Object>) RhinoConverter.convertFromJS(inJsParams, true);
			options = (Map<String, Object>) RhinoConverter.convertFromJS(inJsOptions, true);

			response = HttpProxyClient.call(inUrl, params, options);

		} catch (final ConversionException howthat) { // cannot occur
			JSProxy.LOGGER.error("Unexpected conversion exception with input parameters of JSHttpProxy.call ", howthat);
			response = new APIException(APIErrorMessage.INVALID_PARAMETER, howthat);
		}

		return (Scriptable) RhinoConverter.convertToJS(response);
	}

	/**
	 * Do the Rhino to POJO conversions of parameters and responses maps 
	 * IMPORTANT NOTE : if the signature of this method changes, 
	 * be sure to report the changes in JSProxy#getSendMailMethod()
	 * 
	 * @param inFrom
	 * @param inTo
	 * @param inCc
	 * @param inSubject
	 * @param inText
	 * @return
	 * @throws InternalErrorException
	 */
	public static Scriptable sendMail(Object inEventContext, ScriptableObject inTo, ScriptableObject inCc, String inSubject, String inText) {

		EventContextWrapper eventContext = null;
		List<String> to = Collections.emptyList();
		List<String> cc = Collections.emptyList();
		Map<String, Object> response;

		try {
			eventContext = new EventContextWrapper(inEventContext);
			to = (List<String>) RhinoConverter.convertFromJS(inTo, true);
			cc = (List<String>) RhinoConverter.convertFromJS(inCc, true);

			response = MailProxyClient.send(eventContext, to, cc, inSubject, inText);

		} catch (final APIException whot) {
			response = whot;

		} catch (final ConversionException howthat) {
			// cannot occur with RhinoConverter dropMalformedItems=true (?)
			JSProxy.LOGGER.error("Unexpected conversion exception with input parameters of JSProxy.sendMail ", howthat);
			response = new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER_BECAUSE, "unknown", howthat.getMessage());

		} catch (final Exception evil) { // NullPointerException.. (?)
			final String strErrMsg = "Unexpected exception during JSProxy.sendMail (event context : " + eventContext + ")";
			JSProxy.LOGGER.error(strErrMsg, evil);
			response = new InternalErrorException(strErrMsg);
		}

		return (Scriptable) RhinoConverter.convertToJS(response);
	}

}
