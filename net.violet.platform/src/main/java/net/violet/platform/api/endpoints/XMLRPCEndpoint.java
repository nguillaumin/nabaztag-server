package net.violet.platform.api.endpoints;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.APIController;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.AuthenticationManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.formats.EnumResponsesFormats;
import net.violet.platform.api.formats.HttpResponseHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcHandler;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.common.XmlRpcHttpRequestConfigImpl;
import org.apache.xmlrpc.common.XmlRpcInvocationException;
import org.apache.xmlrpc.common.XmlRpcNotAuthorizedException;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.XmlRpcServlet;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;

/**
 * @author christophe - Violet
 */
public class XMLRPCEndpoint extends XmlRpcServlet {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(RESTEndpoint.class);

	private class AuthXmlRpcHttpRequestConfig extends XmlRpcHttpRequestConfigImpl {

		private final APICaller mEmitter;

		AuthXmlRpcHttpRequestConfig(APICaller inEmitter) {
			this.mEmitter = inEmitter;
		};

		protected APICaller getCallEmitter() {
			return this.mEmitter;
		}

	}

	/**
	 * @author christophe - Violet
	 */
	private class AuthenticatedXmlRpcServletServer extends XmlRpcServletServer {

		/**
		 * Processes the servlet request.
		 * 
		 * @param pRequest The servlet request being read.
		 * @param pResponse The servlet response being created.
		 * @throws IOException Reading the request or writing the response
		 *             failed.
		 * @throws ServletException Processing the request failed.
		 */
		@Override
		public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			if (AuthenticationManager.authenticateHttpCall(req)) {
				super.execute(req, resp);

			} else {
				throw new ServletException(new XmlRpcNotAuthorizedException(AuthenticationManager.getErrorMsg(req)));
			}
		}

		@Override
		protected XmlRpcHttpRequestConfigImpl newConfig(HttpServletRequest req) {
			return new AuthXmlRpcHttpRequestConfig(AuthenticationManager.getAPICallEmitter(req));
		}
	}

	/*
	 * our XML RPC Handler
	 */
	private static final XmlRpcHandler API_RPC_HANDLER = new XmlRpcHandler() {

		/**
		 * Génère la réponse à une requète via XML-RPC
		 * 
		 * @param inRequest
		 */

		public Object execute(XmlRpcRequest inRequest) throws XmlRpcException {

			Object response;
			Object formatedResponse = null;

			try {
				String actionKey = inRequest.getMethodName(); // method name is
				// in the form :
				// violet
				// .<object
				// >.<methodName
				// >
				final int sepPos = actionKey.lastIndexOf('.');
				actionKey = actionKey.substring(0, sepPos) + "." + StringUtils.capitalize(actionKey.substring(sepPos + 1));

				// Try to load the action from the controller
				Action action = APIController.getAction(actionKey);

				// Populate the action params
				final AuthXmlRpcHttpRequestConfig conf = (AuthXmlRpcHttpRequestConfig) inRequest.getConfig();
				final ActionParam actionParam = new ActionParam(conf.getCallEmitter(), (Map) inRequest.getParameter(0));

				// if not specified, default response format is XML-RPC
				final EnumResponsesFormats format = EnumResponsesFormats.getFormatFor(actionParam.getString("format", false), EnumResponsesFormats.XML_RPC);

				// process the request and format the response
				try {
					// Delegate the req treatment to the dedicated action
					response = action.processRequest(actionParam);
					// Apply the correct format to the response
					formatedResponse = HttpResponseHelper.formatResp(response, format);

				} catch (final APIException e) {
					final String errMsg = "Error when processing the request " + actionKey + " " + actionParam;
					XMLRPCEndpoint.LOGGER.error(errMsg, e);
					throw new XmlRpcInvocationException(e.getCode(), e.getMessage(), e);

				} finally {
					action = null;
					response = null; // enable quick garbage collecting
				}

				// special callback function for JSON response
				if ("json".equals(format)) {
					final String callback = actionParam.getString("callback", false);

					if (callback != null) {
						formatedResponse = callback.concat("(").concat((String) formatedResponse).concat(");");
					}
				}

			} catch (final APIException e) {
				// LOGGER.error(StringShop.EMPTY_STRING,e);
				throw new XmlRpcInvocationException(e.getCode(), e.getMessage(), e);
			}

			return formatedResponse;
		}

	};

	/**
	 * Define the unique XmlRpcHandler mapping for the API
	 */
	@Override
	protected XmlRpcHandlerMapping newXmlRpcHandlerMapping() {

		return new XmlRpcHandlerMapping() {

			public XmlRpcHandler getHandler(String inHandler) {
				return XMLRPCEndpoint.API_RPC_HANDLER;
			}
		};
	}

	/**
	 * Creates a new instance of {@link org.apache.xmlrpc.webserver.RequestData}
	 * for the request.
	 */
	@Override
	public void doPost(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException, ServletException {

		getXmlRpcServletServer().execute(pRequest, pResponse);
	}

	/**
	 * Creates a new instance of {@link XmlRpcServer}, which is being used to
	 * process the requests. The default implementation will simply invoke
	 * <code>new {@link XmlRpcServer}.
	 */
	@Override
	protected XmlRpcServletServer newXmlRpcServer(ServletConfig pConfig) {
		return new AuthenticatedXmlRpcServletServer();
	}

}
