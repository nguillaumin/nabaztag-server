package net.violet.vadmin.util;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.commondev.utils.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.sessions.Create;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.BadCredentialsException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.vadmin.actions.Admin;

import org.apache.log4j.Logger;

/**
 * This class provides static methods which allow to retrieve and set
 * information or objects from the session.
 */
public class SessionTools {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(SessionTools.class);

	public static final String ATTRIBUTE_ID_SESSION_USER = "id";
	public static final String ATTRIBUTE_NAME_SESSION_USER = "name";
	public static final String ATTRIBUTE_EMAIL_COOKIE_USER = "email";
	public static final String ATTRIBUTE_PASS_COOKIE_USER = "pass";

	/**
	 * Retrieves user from session (user_id is in session's values).
	 * 
	 * @param session the session to check
	 * @return a UserImpl object, possibly null if the user wasn't found
	 */
	public static final boolean isLogged(HttpServletRequest request) {

		boolean isLogged = false;
		final HttpSession session = request.getSession();

		if (SessionTools.attributeIsPresent(SessionTools.ATTRIBUTE_ID_SESSION_USER, session)) {
			isLogged = true;
		} else {
			final String email = SessionTools.getCookieByName(request, ATTRIBUTE_EMAIL_COOKIE_USER);
			final String password = SessionTools.getCookieByName(request, ATTRIBUTE_PASS_COOKIE_USER);
			if ((email != null) && (password != null)) {
				try {
					if (AdminAuthorization.getAuthorization(email)) {
						String currentSessionId = SessionTools.createSession(email, password);
						if (currentSessionId != null) {
							SessionTools.fillSession(currentSessionId, ATTRIBUTE_EMAIL_COOKIE_USER, request);
							isLogged = true;
						}
					}
				} catch (NumberFormatException e) {} catch (FileNotFoundException e) {}
			}
		}
		return isLogged;
	}

	/**
	 * Sets the user value in session.
	 * 
	 * @param session the session to update
	 * @param userId the user id to store
	 */
	private static void setUserInSession(HttpSession session, String sessionId, String name) {
		session.setAttribute(SessionTools.ATTRIBUTE_ID_SESSION_USER, sessionId);
		session.setAttribute(SessionTools.ATTRIBUTE_NAME_SESSION_USER, name);
	}

	/**
	 * Fill the session for an user
	 * 
	 * @param user the user's session
	 * @param request the request
	 * @param session the session to fill
	 */
	private static void fillSession(String sessionid, String name, HttpServletRequest request) {

		if (request != null) {
			final HttpSession session = request.getSession();
			SessionTools.setUserInSession(session, sessionid, name);
		}
	}

	/**
	 * Cleans all elements which are present in the session.
	 * 
	 * @param session the session to clean up.
	 * @return <code>true</code> if the session was cleaned up and
	 *         <code>false</code> if not.
	 */
	private static void cleanSession(HttpSession session) {

		try {
			final Enumeration attrib = session.getAttributeNames();
			while (attrib.hasMoreElements()) {
				final String name = attrib.nextElement().toString();
				if (!name.equals("lang")) {
					session.removeAttribute(name);
				}
			}
			// session.invalidate();
		} catch (final NullPointerException e) {
			SessionTools.LOGGER.fatal(e, e);
		}
	}

	/**
	 * Indicates if the requested attribute (attributeName) is in session.
	 * Returns false if the session is null.
	 * 
	 * @param attributeName
	 * @param session
	 * @return true if the attribute is present, false otherwise
	 */
	private static boolean attributeIsPresent(String attributeName, HttpSession session) {
		if (session == null) {
			return false;
		}

		final Enumeration myEnumeration = session.getAttributeNames();
		boolean found = false;

		while (myEnumeration.hasMoreElements() && !found) {
			final Object myAttribute = myEnumeration.nextElement();
			if (myAttribute.equals(attributeName)) {
				found = true;
			}
		}

		return found;
	}

	/**
	 * Retrieves the Id of the current session.
	 * 
	 * @param session the session to check.
	 * @return the Id session or null if it doesn't exist.
	 */
	public static final String getSessionId(HttpSession session) {

		if (SessionTools.attributeIsPresent(ATTRIBUTE_ID_SESSION_USER, session)) {
			return session.getAttribute(ATTRIBUTE_ID_SESSION_USER).toString();
		}
		return null;
	}

	/**
	 * Log a user
	 * 
	 * @param request the request
	 * @param response the response
	 * @param pseudo the pseudo of the user.
	 * @param password we suppose the password is already encrypted via Cypher.
	 * @return <code>true<code> is the logon succeed, or <code>false</code> if
	 *         not.
	 */
	public static void logUser(HttpServletRequest request, HttpServletResponse response, String sessionId, String email, String password) {
		/** session creation **/
		SessionTools.fillSession(sessionId, email, request);
		SessionTools.createDefaultCookies(response, email, password);
	}

	/**
	 * Unlog an user. Delete both cookies and session which belongs to this
	 * user.
	 * 
	 * @param request the request
	 */
	public static void unlogUser(HttpServletRequest request, HttpServletResponse response) {

		SessionTools.cleanSession(request.getSession());

		final Cookie[] cookies = request.getCookies();

		if (null != cookies) {
			for (final Cookie cookie : cookies) {
				final Cookie readyToDie = new Cookie(cookie.getName(), StringShop.EMPTY_STRING);
				readyToDie.setDomain(AdminConstantes.DOMAIN);
				readyToDie.setPath(AdminConstantes.PATH);
				readyToDie.setMaxAge(0);
				response.addCookie(readyToDie);
			}
		}
	}

	/**
	 * Create the default cookies : the version (?), the user pseudo, the user
	 * password
	 * 
	 * @param response the response
	 * @param pseudoUser the user login
	 * @param password the user password
	 */
	private static void createDefaultCookies(HttpServletResponse response, String pseudoUser, String password) {

		// createCookie(response, "ver", "2"); // it seems to be never use
		SessionTools.createCookie(response, ATTRIBUTE_EMAIL_COOKIE_USER, pseudoUser);
		SessionTools.createCookie(response, ATTRIBUTE_PASS_COOKIE_USER, password);
	}

	/**
	 * Create a cookie from a name and a value
	 * 
	 * @param response the response to set the cookie
	 * @param name the name of the cookie
	 * @param value the value of the cookie
	 */
	public static void createCookie(HttpServletResponse response, String name, String value) {

		final int expires = 60 * 60 * 24 * 3600;

		final Cookie cookie = new Cookie(name, value);
		cookie.setDomain(AdminConstantes.DOMAIN);
		cookie.setPath(AdminConstantes.PATH);
		cookie.setMaxAge(expires);
		response.addCookie(cookie);
	}

	/**
	 * Finds and returns the value of a cookie from his name.
	 * 
	 * @param request the resquest.
	 * @param cookieName the cookie's value to find.
	 * @return a <code>String</code> of the value or <code>null</code> the
	 *         cookie was not found.
	 */
	public static String getCookieByName(HttpServletRequest request, String cookieName) {

		final Cookie[] cookies = request.getCookies();

		if (null != cookies) {
			for (final Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Create a session through the API
	 * @param email the email
	 * @param password the password
	 * @return the Id of the session generated or <code>null</code> if an error occurred.
	 */
	public static String createSession(String email, String password) {

		final Calendar theCal = Calendar.getInstance();
		theCal.add(Calendar.YEAR, 1);

		final Action theAction = new Create();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("email", email);
		theParams.put("password", password);
		theParams.put("expiration", theCal.getTime());

		try {
			return (String) Admin.processRequest(theAction, theParams);
		} catch (InvalidParameterException e) {
			LOGGER.info("InvalidParameterException : " + e.getMessage());
		} catch (NoSuchPersonException e) {
			LOGGER.info("NoSuchPersonException : " + e.getMessage());
		} catch (BadCredentialsException e) {
			LOGGER.info("BadCredentialsException : " + e.getMessage());
		} catch (APIException e) {
			LOGGER.info("APIException : " + e.getMessage());
		}
		return null;
	}
}
