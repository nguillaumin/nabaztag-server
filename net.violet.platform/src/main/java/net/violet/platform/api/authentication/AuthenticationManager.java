package net.violet.platform.api.authentication;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import net.violet.common.utils.DigestTools;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.callers.ComponentAPICaller;
import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

public class AuthenticationManager {

	// keys used to store the application or error message as request attributes
	private static final String CALL_EMITTER_KEY = "emitter";
	private static final String AUTH_ERROR_KEY = "authentication_error";

	/**
	 * Authentification de l'appel HTTP (REST et XML-RPC) Mets l'appelant en
	 * attribut de la requète si l'authentification a réussie ou bien un message
	 * d'erreur
	 * 
	 * @return <code>true</code> si l'application est authentifiée
	 */
	public static boolean authenticateHttpCall(HttpServletRequest req) {

		ApplicationCredentialsData credentials = null;

		String callerId = req.getRemoteUser(); // should return the api_key via basic or digest authentication process

		if (callerId != null) {
			// we have passed through a BASIC or DIGEST http authentication
			// process
			if (HttpServletRequest.BASIC_AUTH.equals(req.getAuthType()) && !req.getScheme().equals(APIConstants.HTTPS)) { //

				req.setAttribute(AuthenticationManager.AUTH_ERROR_KEY, "The Basic authentication scheme can only be used over https !");
				return false;
			}

			// do not need to check further : the password has been verified
			credentials = ApplicationCredentialsData.findByPublicKey(callerId);

		} else if (req.getScheme().equals(APIConstants.HTTPS)) { // Try manual authentication over https we can look through the api_key and api_sign in the request headers or params
			callerId = AuthenticationManager.searchParameter(APIConstants.API_KEY, req);

			if (callerId != null) {
				final String apiSign = AuthenticationManager.searchParameter(APIConstants.API_SIGN, req);
				credentials = ApplicationCredentialsData.findByPublicKey(callerId);

				// check the private key
				if (((credentials == null) || !credentials.isValid()) || (!credentials.getPrivateKey().equals(apiSign))) {
					req.setAttribute(AuthenticationManager.AUTH_ERROR_KEY, "API Call failed ! Bad authentication provided ");
					return false;
				}
			}
		}

		if ((credentials == null) || !credentials.isValid()) {
			req.setAttribute(AuthenticationManager.AUTH_ERROR_KEY, "API call authentication failed ! Bad authentication provided ");
			return false;
		}

		final APICaller caller;
		final APICaller.CallerClass callerClass = credentials.getCallerClass();

		if (callerClass == APICaller.CallerClass.APPLICATION) {
			caller = new ApplicationAPICaller(credentials);

		} else if (callerClass == APICaller.CallerClass.COMPONENT) {
			caller = new ComponentAPICaller(callerId);
		} else {
			req.setAttribute(AuthenticationManager.AUTH_ERROR_KEY, "Unknown API caller !");
			return false;
		}

		// store the emitter for later retrieval
		req.setAttribute(AuthenticationManager.CALL_EMITTER_KEY, caller);
		return true;

	}

	/**
	 * Calculate the key for DIGEST authentication
	 * 
	 * @param inPublicKey
	 * @param inPrivateKey
	 * @return
	 */
	public static String getDigestedKey(String inPublicKey, String inPrivateKey) {
		return DigestTools.digest(inPublicKey + ":" + APIConstants.API_REALM + ":" + inPrivateKey, DigestTools.Algorithm.MD5);
	}

	/**
	 * @param req
	 * @return
	 */
	public static APICaller getAPICallEmitter(HttpServletRequest req) {
		return (APICaller) req.getAttribute(AuthenticationManager.CALL_EMITTER_KEY);
	}

	/**
	 * @param req
	 * @return
	 */
	public static String getErrorMsg(HttpServletRequest req) {
		return (String) req.getAttribute(AuthenticationManager.AUTH_ERROR_KEY);
	}

	/**
	 * This method looks for the provided parameter in the HTTP header and in
	 * the request parameters. If the parameter is in the header and in the
	 * parameters string the value in the header is returned.
	 * 
	 * @param inParameter the parameter to look for.
	 * @param inRequest the request containing (or not) the parameter.
	 * @return the value of the parameter (or null).
	 */
	private static String searchParameter(String inParameter, HttpServletRequest inRequest) {

		String paramValue = inRequest.getHeader(inParameter);

		if (paramValue == null) {
			paramValue = inRequest.getParameter(inParameter);
		}

		return paramValue;
	}

	/**
	 * Digested keys generator : Enter your Public and private keys to obtain
	 * the DIGEST(ed) key
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Usage : ObjectAuthenticationManager %PUBLIC_KEY %PRIVATE_KEY");
			return;
		}

		final String publicKey = args[0];
		final String privateKey = args[1];
		final String digested = AuthenticationManager.getDigestedKey(publicKey, privateKey);

		System.out.println(MessageFormat.format("Public key : \"{0}\", private key : \"{1}\", digested key : \"{2}\"", publicKey, privateKey, digested));
	}
}
