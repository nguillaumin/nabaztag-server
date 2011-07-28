package net.violet.platform.api.authentication;

import java.util.Calendar;
import java.util.Date;

import net.violet.common.utils.DigestTools;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.dataobjects.UserData;

public class SessionManager {

	private static final String SECRET_PASSWORD = "4fdd311928028c8332d478d210817475";

	/**
	 * @param inParams
	 * @return
	 * @throws ForbiddenException
	 * @throws InvalidSessionException
	 * @throws InvalidParameterException
	 */
	public static UserData getUserFromSessionParam(ActionParam inParams) throws ForbiddenException, InvalidSessionException, InvalidParameterException {
		final APICaller apiCaller = inParams.getCaller();

		/*
		 * Sur le site web, l'utilisateur est celui qui s'est loggué et est en
		 * session
		 */
		if (Application.ApplicationClass.WEBUI.equals(apiCaller.getApplicationClass())) {
			final String sessionKey = inParams.getString(ActionParam.SESSION_PARAM_KEY, true);
			return SessionManager.getUserFromSessionId(sessionKey, apiCaller);
		}

		/*
		 * les autres applications peuvent fournir un userId
		 */
		final String userId = inParams.getString("userId", true);
		return UserData.findByAPIId(userId, apiCaller.getAPIKey());
	}

	/**
	 * This method generates a valid session Id identifying the provided user
	 * for the given application according to the provided expiration date.
	 * 
	 * @param inApplication
	 * @param inUser
	 * @param inExpiration
	 * @return
	 */
	public static String generateSessionId(APICaller inCaller, UserData inUser, Date inExpiration) {

		final String userId = Long.toHexString(inUser.getId());
		final String ttl = Long.toHexString(inExpiration.getTime() / 1000);

		final String signature = SessionManager.computeSignature(inCaller, userId, ttl);

		return userId + ":" + ttl + ":" + signature;
	}

	private static String computeSignature(APICaller inCaller, String userId, String ttl) {
		final String toEncode = userId + ":" + ttl + ":" + inCaller.getAPIKey() + ":" + SessionManager.SECRET_PASSWORD;
		return DigestTools.digest(toEncode, DigestTools.Algorithm.SHA1);
	}

	/**
	 * Returns UserData if the given session Id is valid for the given
	 * application. The method returns InvalidSessionException if : - the
	 * signature of the provided sessionId is not valid for the given
	 * application - the previous session has already expired
	 * 
	 * @param inSessionId
	 * @param inApplication
	 * @return
	 * @throws ForbiddenException
	 * @throws InvalidSessionException
	 */
	public static UserData getUserFromSessionId(String inSessionId, APICaller inCaller) throws ForbiddenException, InvalidSessionException {

		if (inCaller == null) {
			throw new ForbiddenException();
		}

		if (SessionManager.isSessionValid(inSessionId, inCaller)) { // Session
			// is good
			final UserData theUserData = SessionManager.getUserFromValidSessionId(inSessionId);

			if (theUserData.isValid()) {
				return theUserData;
			}
			throw new InvalidSessionException();
		}
		throw new InvalidSessionException();
	}

	/**
	 * Returns true if the given session Id is valid for the given application.
	 * The method returns false if : - the signature of the provided sessionId
	 * is not valid for the given application - the previous session has already
	 * expired
	 * 
	 * @param sessionId
	 * @param application
	 * @return
	 */
	public static boolean isSessionValid(String sessionId, APICaller inCaller) {

		final String[] information = sessionId.split(":");

		if (information.length < 3) {
			return false;
		}

		final String userId = information[0];
		final String ttl = information[1];
		final String signature = information[2];

		// the signature is not valid.
		if (!signature.equals(SessionManager.computeSignature(inCaller, userId, ttl))) {
			return false;
		}

		// the session has expired.
		final Long ttlInMillis = Long.parseLong(ttl, 16) * 1000;
		if (Calendar.getInstance().getTimeInMillis() > ttlInMillis) {
			return false;
		}

		return true;
	}

	/**
	 * Uses the provided sessionId to find the identified user. Caution : this
	 * method does NOT check if the session Id is valid
	 * 
	 * @param sessionId
	 * @return
	 */
	public static UserData getUserFromValidSessionId(String sessionId) {

		final String userIdInHexa = sessionId.substring(0, sessionId.indexOf(':'));
		final long userId = Long.parseLong(userIdInHexa, 16);

		return UserData.find(userId);
	}

}
