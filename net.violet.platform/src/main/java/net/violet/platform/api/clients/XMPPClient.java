package net.violet.platform.api.clients;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import net.violet.common.utils.server.ProcessName;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.Action.ActionType;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.IQApiQuery;
import net.violet.platform.xmpp.packet.VioletApiPacket;

import org.apache.log4j.Logger;

public class XMPPClient implements APIClient {

	// LOGGER
	private static final Logger LOGGER = Logger.getLogger(XMPPClient.class);

	// who makes the call ?
	private final APICaller mEmitter;

	public XMPPClient(APICaller inEmitter) {

		if (inEmitter == null) {
			throw new IllegalArgumentException("Emitter for XMPPClient cannot be NULL !");
		}
		this.mEmitter = inEmitter;
	}

	/**
	 * @see net.violet.platform.api.clients.APIClient#executeMethodCall(java.lang.String,
	 *      java.lang.String)
	 */
	public Object executeMethodCall(String actionName, String inMainParamValue) throws APIException {
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(ActionParam.MAIN_PARAM_KEY, inMainParamValue);

		return executeMethodCall(ActionType.GET, actionName, paramMap);
	}

	/**
	 * @see net.violet.platform.api.clients.APIClient#executeMethodCall(net.violet.platform.api.actions.Action.ActionType,
	 *      java.lang.String, java.util.Map)
	 */
	public Object executeMethodCall(ActionType actionType, // usefull only in REST call
	String actionName, Map<String, Object> inAPICallParams) throws APIException {

		final Matcher parsedActionName = APIClient.actionNamePattern.matcher(actionName);

		if (!parsedActionName.matches()) {
			throw new InvalidParameterException(APIErrorMessage.NO_SUCH_METHOD, net.violet.common.StringShop.EMPTY_STRING);
		}

		try {
			final VioletApiPacket packet = new VioletApiPacket(inAPICallParams);
			packet.setFrom(this.mEmitter.getAPIKey() + "@" + Constantes.XMPP_APPLET_COMPONENT + "/" + ProcessName.getProcessName().replaceAll("/", "-"));
			packet.setTo(actionName + "@" + Constantes.XMPP_API_COMPONENT);

			XMPPClient.LOGGER.info("Applet or component " + this.mEmitter + " is calling API method " + actionName + " with packet " + packet.getPacketID());
			final Long startTime = System.currentTimeMillis();
			final Object resp = IQApiQuery.sendPacket(Constantes.XMPP_APPLET_COMPONENT, packet).getElement();
			final Long elapsedTime = System.currentTimeMillis() - startTime;
			XMPPClient.LOGGER.info("Applet or component " + this.mEmitter + " received API response to " + actionName + " in packet " + packet.getPacketID() + " in " + elapsedTime + "ms");

			checkAPIResponse(resp);
			return resp;

		} catch (final Exception e) {
			final String strErrMsg = "An error happened when calling the API : " + actionName;
			XMPPClient.LOGGER.error(strErrMsg, e);
			throw new APIException(APIErrorMessage.INTERNAL_ERROR, e.getMessage());
		}
	}

	/**
	 * Rethrow the original API exception if the response is an error map
	 * @param inAPIResponse
	 * @throws APIException
	 */
	private void checkAPIResponse(Object inAPIResponse) throws APIException {

		if (inAPIResponse == null) {
			// nothing to say. it's not an error
			return;
		}

		try {
			final Map<String, Object> errorMap = (Map<String, Object>) inAPIResponse;

			if ((errorMap.get("type") == null) || (errorMap.get("status") == null)) {
				// it doesn't look like an error map
				return;
			}

			// revive 
			final APIException apiException = new APIException(new PojoMap(errorMap));
			throw apiException;

		} catch (final ClassCastException notAMap) {
			// the response is not a map >> it cannot be an exception

		} catch (final InvalidParameterException notAnExceptionMap) {
			// the map has not the structure of an exception map
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XMPPClient for " + this.mEmitter.toString();
	}

}
