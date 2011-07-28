package net.violet.platform.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPrefs;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;

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
	private static final String DOMAIN = MyConstantes.DOMAIN;

	private static final String ATTRIBUTE_NAME_SESSION_OBJECT = "idLapin";
	private static final String ATTRIBUTE_NAME_SESSION_USER = "id";
	private static final String ATTRIBUTE_NAME_SESSION_LANG = "lang";

	/**
	 * Retrieves user from session (user_id is in session's values).
	 * 
	 * @param session the session to check
	 * @return a UserImpl object, possibly null if the user wasn't found
	 */
	public static final User getUserFromSession(HttpServletRequest request) {

		User user = null;
		final String pseudoCookie = "user";
		final String passwordCookie = "pass";

		final HttpSession session = request.getSession();

		if (SessionTools.attributeIsPresent(SessionTools.ATTRIBUTE_NAME_SESSION_USER, session)) {
			try {
				final Long theResult = Long.parseLong(session.getAttribute(SessionTools.ATTRIBUTE_NAME_SESSION_USER).toString());
				if (theResult > 0) {
					user = Factories.USER.find(theResult);
				}
			} catch (final NumberFormatException anException) {
				SessionTools.LOGGER.fatal(anException, anException);
			}
		} else {
			// Find in cookies from Login and Password (or just Login (?))
			final String pseudo = SessionTools.getCookieByName(request, pseudoCookie);
			final String password = SessionTools.getCookieByName(request, passwordCookie);
			if ((pseudo != null) && (password != null)) {
				final VObject theObject = Factories.VOBJECT.findByNameAndMD5Password(pseudo, password);
				if (theObject != null) {
					SessionTools.fillSession(theObject.getOwner(), theObject, request);
				}
			}
		}
		return user;
	}

	/**
	 * Sets the user value in session.
	 * 
	 * @param session the session to update
	 * @param user the user to store
	 */
	public static void setUserInSession(HttpSession session, User user) {
		long userId;
		if (user != null) {
			userId = user.getId();
		} else {
			userId = 0;
		}

		SessionTools.setUserInSession(session, userId);
	}

	/**
	 * Sets the user value in session.
	 * 
	 * @param session the session to update
	 * @param userId the user id to store
	 */
	private static void setUserInSession(HttpSession session, long userId) {
		session.setAttribute(SessionTools.ATTRIBUTE_NAME_SESSION_USER, Long.toString(userId));
	}

	/**
	 * Retrieves rabbit from session (idLapin is in session's values).
	 * 
	 * @param session the session to check
	 * @return a VObjectImpl object, possibly null if the object doesn't exist
	 */
	public static final VObject getRabbitFromSession(HttpSession session) {
		VObject rabbit = null;

		if (SessionTools.attributeIsPresent(SessionTools.ATTRIBUTE_NAME_SESSION_OBJECT, session)) {
			final String theAttribute = session.getAttribute(SessionTools.ATTRIBUTE_NAME_SESSION_OBJECT).toString();
			try {
				final long theValue = Long.parseLong(theAttribute);
				if (theValue > 0) {
					rabbit = Factories.VOBJECT.find(theValue);
				}
			} catch (final NumberFormatException anException) {
				SessionTools.LOGGER.fatal(anException, anException);
			}
		}

		return rabbit;
	}

	/**
	 * Returns the rabbit id stored in session. This method actually is a
	 * shortcut to avoid using getRabbitFromSession and testing if the returned
	 * object is null or not. If the object is null this method returns 0.
	 * 
	 * @param session the session to check
	 * @return the object id, or 0 if there is no object
	 */
	/*
	 * methode temporaire, a virer quand on n'utilisera plus les id directement
	 * mais seulement les VObjects
	 */
	public static final long getRabbitIdFromSession(HttpSession session) {
		final VObject object = SessionTools.getRabbitFromSession(session);
		if (object != null) {
			return object.getId();
		}
		return 0;
	}

	/**
	 * Stores the information about the specified object in the session
	 * 
	 * @param session the session to use to store the information
	 * @param object the object to store
	 */
	public static void setRabbitInSession(HttpSession session, VObject object) {
		long objectId;
		if (object != null) {
			objectId = object.getId();
		} else {
			objectId = 0;
		}

		SessionTools.setRabbitInSession(session, objectId);
	}

	/**
	 * Stores the specified object id in the session
	 * 
	 * @param session the session to use to store the information
	 * @param objectId the id to store
	 */
	public static void setRabbitInSession(HttpSession session, long objectId) {
		session.setAttribute(SessionTools.ATTRIBUTE_NAME_SESSION_OBJECT, Long.toString(objectId));
	}

	/**
	 * Retrieves language from session (lang is in session's values) or from
	 * request (i.e. from the browser preferences). If lang attribute is not in
	 * the session we check the accepted languages from the request.
	 * 
	 * @param session the session to check
	 * @param request the request to access browser options
	 * @return a LangImpl object, shouldn't be null unless a database error
	 *         occured (default value : English US)
	 */
	public static Lang getLangFromSession(HttpSession session, HttpServletRequest request) {
		Lang lang = null;

		if (SessionTools.attributeIsPresent(SessionTools.ATTRIBUTE_NAME_SESSION_LANG, session)) {
			lang = Factories.LANG.find(Long.parseLong(session.getAttribute(SessionTools.ATTRIBUTE_NAME_SESSION_LANG).toString()));
		}

		if ((lang == null) && (request != null) && (request.getHeader("Accept-Language") != null)) {

			for (final String l : request.getHeader("Accept-Language").split(",")) {
				if (l.contains("fr")) {
					lang = Factories.LANG.find(1); // Français
				}
				if (l.contains("us")) {
					lang = Factories.LANG.find(2); // English US
				}
				if (l.contains("gb")) {
					lang = Factories.LANG.find(3); // English UK
				}
				if (l.equals("en")) {
					lang = Factories.LANG.find(2); // English (actually US)
				}
				if (l.equals("de")) {
					lang = Factories.LANG.find(4); // Deutsch
				}
				if (l.equals("es")) {
					lang = Factories.LANG.find(5); // Espanol
				}
				if (l.equals("it")) {
					lang = Factories.LANG.find(6); // Italiano
				}

				if (lang != null) {
					break;
				}
			}
		}

		if (lang == null) {
			lang = Factories.LANG.find(Lang.LANG_US_ID); // default value : English US
		}

		return lang;
	}

	/**
	 * Sets the lang value in session. If lang == null the new lang is English
	 * US (default)
	 * 
	 * @param session the session to update
	 * @param lang the lang to store
	 */
	public static void setLangInSession(HttpSession session, Lang lang) {
		long langId;
		if (lang != null) {
			langId = lang.getId();
		} else {
			langId = Lang.LANG_US_ID; // default value : English US
		}

		session.setAttribute(SessionTools.ATTRIBUTE_NAME_SESSION_LANG, Long.toString(langId));
	}

	/**
	 * Sets the user preferences for the session
	 * 
	 * @param session the session to update
	 * @param attribute the name of the attribute
	 * @param value the string of the attribute
	 */
	public static void setSessionUserPrefs(HttpSession session, String attribute, String value) {
		session.setAttribute(attribute, value);
	}

	/**
	 * Retrieve an attribute , if exists, of the user preferences from the
	 * current session
	 * 
	 * @param session the user session
	 * @param attribute the attribute to retrieve
	 * @return the value of the attribute, or an empty <code>String</code>
	 */
	public static final String getSessionUserpref(HttpSession session, String attribute) {
		String annuPref = StringShop.EMPTY_STRING;
		if (SessionTools.attributeIsPresent(attribute, session)) {
			annuPref = session.getAttribute(attribute).toString();
		}
		SessionTools.LOGGER.info("Attribute : " + attribute + " Value = " + annuPref);
		return annuPref;
	}

	/**
	 * permet de recupere l'ip
	 * 
	 * @param request
	 * @return ip
	 */
	public static String getIP(HttpServletRequest request) {

		String ipaddress;
		// Avec détection proxy
		if (request.getHeader("HTTP_X_FORWARDED_FOR") == null) {
			ipaddress = request.getRemoteAddr();
		} else {
			ipaddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		return ipaddress;
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
	 * Log a user
	 * 
	 * @param request the request
	 * @param response the response
	 * @param pseudo the pseudo of the user.
	 * @param password we suppose the password is already encrypted via Cypher.
	 * @return <code>true<code> is the logon succeed, or <code>false</code> if
	 *         not.
	 */
	public static User logUser(HttpServletRequest request, HttpServletResponse response, String pseudo, String password) {
		final VObject theObject = Factories.VOBJECT.findByNameAndMD5Password(pseudo, password);
		if (theObject != null) {
			SessionTools.logUser(request, response, theObject);
			return theObject.getOwner();
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
	public static void logUser(HttpServletRequest request, HttpServletResponse response, VObject inObject) {
		if ((inObject != null) && (request != null) && (response != null)) {
			final User theUser = inObject.getOwner();
			/** session creation **/
			SessionTools.fillSession(theUser, inObject, request);

			/**
			 * Delete old cookies (if exist) | 24/10/2007 - patch deleting the
			 * cookies from the old version of cookies settings
			 **/
			SessionTools.cleanOldCookiesPath(request, response);

			/** cookies creation **/
			final UserPrefs uPrefs = theUser.getUPreferences();
			if (uPrefs != null) {
				SessionTools.createDefaultCookies(response, inObject.getObject_login(), inObject.getMD5Password(), uPrefs.getUserprefs_layout());
			} else {
				SessionTools.createDefaultCookies(response, inObject.getObject_login(), inObject.getMD5Password());
			}
		}
	}

	/**
	 * Unlog an user. Delete both cookies and session which belongs to this
	 * user.
	 * 
	 * @param request the request
	 */
	public static void unlogUser(HttpServletRequest request, HttpServletResponse response) {

		/**
		 * Delete old cookies (if exist) | 24/10/2007 - patch deleting the
		 * cookies from the old version of cookies settings
		 **/
		SessionTools.cleanOldCookiesPath(request, response);

		SessionTools.cleanSession(request.getSession());

		final Cookie[] cookies = request.getCookies();

		if (null != cookies) {
			for (final Cookie cookie : cookies) {
				final Cookie readyToDie = new Cookie(cookie.getName(), StringShop.EMPTY_STRING);
				readyToDie.setDomain(SessionTools.DOMAIN);
				readyToDie.setPath("/vl");
				readyToDie.setMaxAge(0);
				response.addCookie(readyToDie);
			}
		}
	}

	/**
	 * Fill the session for an user
	 * 
	 * @param user the user's session
	 * @param request the request
	 * @param session the session to fill
	 */
	private static void fillSession(User user, VObject inObject, HttpServletRequest request) {

		if (request != null) {

			final HttpSession session = request.getSession();

			if (user == null) {
				SessionTools.setLangInSession(session, SessionTools.getLangFromSession(session, request));
			} else {
				SessionTools.setUserInSession(session, user);
				SessionTools.setLangInSession(session, user.getLang());
				SessionTools.setRabbitInSession(session, inObject);
			}
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
	 * Create the default cookies : the version (?), the user pseudo, the user
	 * password
	 * 
	 * @param response the response
	 * @param pseudoUser the user login
	 * @param password the user password
	 */
	private static void createDefaultCookies(HttpServletResponse response, String pseudoUser, String password) {

		// createCookie(response, "ver", "2"); // it seems to be never use
		SessionTools.createCookie(response, "user", pseudoUser);
		SessionTools.createCookie(response, "pass", password);
	}

	/**
	 * Creates the default cookies : user pseudo, user password and user layout
	 * 
	 * @param response
	 * @param pseudoUser
	 * @param password
	 * @param layout
	 */
	private static void createDefaultCookies(HttpServletResponse response, String pseudoUser, String password, String layout) {
		SessionTools.createCookie(response, "user", pseudoUser);
		SessionTools.createCookie(response, "pass", password);
		SessionTools.createCookie(response, "userprefs_layout", layout);
	}

	/**
	 * Create a cookie from a name and a value
	 * 
	 * @param response the response to set the cookie
	 * @param name the name of the cookie
	 * @param value the value of the cookie
	 */
	public static void createCookie(HttpServletResponse response, String name, String value) {

		final int expires = 60 * 60 * 24 * 360;

		final Cookie cookie = new Cookie(name, value);
		cookie.setDomain(SessionTools.DOMAIN);
		cookie.setPath("/vl");
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

	// TODO : 12/10/2007 This method is not use at this time
	/**
	 * Finds and returns all the values in the cookies.
	 * 
	 * @param request the request.
	 * @return a <code>Map</code> of <code>String</code> which contains the
	 *         values. Keys are <code>"user"</code>, <code>"pass"</code>,
	 *         <code>"ver"</code> or <code>"userprefs_layout"</code>.
	 */
	public static Map<String, String> getCookies(HttpServletRequest request) {

		final Map<String, String> values = new HashMap<String, String>();

		final Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (null != cookies[i]) {
				values.put(cookies[i].getName(), cookies[i].getValue());
			}
		}
		return values;
	}

	// TODO : 12/10/2007 This method is not use at this time
	/**
	 * Update the value of a cookie from its name.
	 * 
	 * @param request the request.
	 * @param cookieName the name of the cookie to update.
	 * @param newValue the new value to set.
	 * @return <code>true</code> if the cookie was updated, <code>false</code>
	 *         if it wasn't.
	 */
	public static boolean updateCookie(HttpServletRequest request, String cookieName, String newValue) {
		final Cookie[] cookies = request.getCookies();

		for (int i = 0; i < cookies.length; i++) {
			if (null != cookies[i]) {

				if (cookieName.equals(cookies[i].getName())) {
					cookies[i].setValue(newValue);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Erase the old cookies (created without an specified path)
	 * 
	 * @param request the request
	 * @param response the reponse
	 */
	private static void cleanOldCookiesPath(HttpServletRequest request, HttpServletResponse response) {

		final Cookie[] cookies = request.getCookies();

		if (null != cookies) {
			for (final Cookie cookie : cookies) {
				final Cookie readyToDie = new Cookie(cookie.getName(), StringShop.EMPTY_STRING);
				readyToDie.setDomain(SessionTools.DOMAIN);
				readyToDie.setPath("/vl/action");
				readyToDie.setMaxAge(0);
				response.addCookie(readyToDie);
			}
		}
	}

	public static String getDOMAIN() {
		return SessionTools.DOMAIN;
	}

	// TODO : Méthodes appelées dans les JSP. Voir si on peut passer par Struts
	// (les ActionForm) pour les récupérer dans les JSP.
	/* Temporary methods */
	@Deprecated
	public static final String getHourModFromSession(HttpServletRequest request) {
		final User user = SessionTools.getUserFromSession(request);
		if (user != null) {
			return Integer.toString(user.getHourMod());
		}
		return "24";
	}
}
