package net.violet.platform.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Classe pour la requête.
 */

public final class ServletTestRequest implements HttpServletRequest {

	/**
	 * Référence sur la session.
	 */
	private final HttpSession mSession;

	/**
	 * Référence sur les cookies.
	 */
	private final Cookie[] mCookies;

	/**
	 * Référence sur les entêtes HTTP.
	 */
	private final Hashtable<String, List<String>> mHeaders;
	/**
	 * Référence sur les attributs.
	 */
	private final Hashtable<String, Object> mAttributes;
	/**
	 * Référence sur les paramètres.
	 */
	private final Hashtable<String, String> mParameters;
	/**
	 * Référence sur les "dispatchers".
	 */
	private final Hashtable<String, RequestDispatcher> mDispatchers;
	/**
	 * Context path (ou null).
	 */
	private final String mContextPath;
	/**
	 * URI (ou null).
	 */
	private final String mRequestURI;
	/**
	 * Query string (ou null).
	 */
	private final String mQueryString;
	/**
	 * Remote user (ou null)
	 */
	private String mRemoteUser;
	/**
	 * Content type (ou null).
	 */
	private String mContentType;
	/**
	 * Méthode.
	 */
	private String mMethod = "GET";
	/**
	 * Reader avec les données dans le corps de la requête.
	 */
	private BufferedReader mReader;
	/**
	 * Stream avec les données dans le corps de la requête.
	 */
	private ServletInputStream mInputStream;
	/**
	 * Données.
	 */
	private final ByteArrayOutputStream mData;
	/**
	 * Encodage de la requête.
	 */
	private String mCharacterEncoding;

	/**
	 * Créateur à partir de la session.
	 * 
	 * @param inSession
	 * @return un nouvel objet ServletTestRequest.
	 */
	public static ServletTestRequest createFromSession(HttpSession inSession) {
		return new ServletTestRequest(inSession, null, null);
	}

	/**
	 * Créateur à partir du context path, de l'URL et de l'utilisateur distant.
	 * 
	 * @param inContextPath
	 *            context path, i.e. préfixe du contexte.
	 * @param inURL
	 *            URL complète, avec le context path et la query string.
	 * @param inRemoteUser
	 *            utilisateur pour cette requête ou <code>null</code> si la
	 *            requête doit être anonyme (non authentifiée).
	 * @return un nouvel objet ServletTestRequest.
	 */
	public static ServletTestRequest createFromURL(String inContextPath, String inURL) {
		return new ServletTestRequest(new ServletTestSession(), inContextPath, inURL);
	}

	/**
	 * Constructeur à partir d'une session et d'une URL.
	 * 
	 * @param inSession
	 *            session pour cette requête.
	 * @param inContextPath
	 *            context path, i.e. préfixe du contexte.
	 * @param inURL
	 *            url pour cette requête.
	 * @param inRemoteUser
	 *            utilisateur pour cette requête ou <code>null</code> si la
	 *            requête doit être anonyme (non authentifiée).
	 */
	private ServletTestRequest(HttpSession inSession, String inContextPath, String inURL) {
		this.mSession = inSession;
		this.mCookies = new Cookie[0];
		this.mHeaders = new Hashtable<String, List<String>>();
		this.mAttributes = new Hashtable<String, Object>();
		this.mParameters = new Hashtable<String, String>();
		this.mDispatchers = new Hashtable<String, RequestDispatcher>();
		this.mContextPath = inContextPath;
		if (inURL != null) {
			final String[] theURL = inURL.split("\\?");
			if (theURL.length == 1) {
				this.mRequestURI = inURL;
				this.mQueryString = net.violet.common.StringShop.EMPTY_STRING;
			} else if (theURL.length == 2) {
				this.mRequestURI = theURL[0];
				this.mQueryString = theURL[1];
			} else {
				throw new UnsupportedOperationException();
			}
		} else {
			this.mRequestURI = null;
			this.mQueryString = null;
		}
		this.mData = new ByteArrayOutputStream();
	}

	public String getAuthType() {
		final String theAuthType;
		if (this.mRemoteUser == null) {
			theAuthType = null;
		} else {
			theAuthType = HttpServletRequest.DIGEST_AUTH;
		}
		return theAuthType;
	}

	public String getContextPath() {
		if (this.mContextPath == null) {
			throw new UnsupportedOperationException();
		}
		return this.mContextPath;
	}

	public Cookie[] getCookies() {
		return this.mCookies;
	}

	@SuppressWarnings("unused")
	public long getDateHeader(String arg0) {
		throw new UnsupportedOperationException();
	}

	public String getHeader(String inHeaderName) {
		final List<String> theHeaderValues = this.mHeaders.get(inHeaderName);
		final String theHeaderValue;
		if ((theHeaderValues != null) && (theHeaderValues.size() > 0)) {
			theHeaderValue = theHeaderValues.get(0);
		} else {
			theHeaderValue = null;
		}
		return theHeaderValue;
	}

	public Enumeration getHeaderNames() {
		return this.mHeaders.keys();
	}

	public Enumeration getHeaders(String inHeaderName) {
		final List<String> theHeaderValues = this.mHeaders.get(inHeaderName);
		final Enumeration<String> theHeaderValuesEnum;
		if (theHeaderValues != null) {
			theHeaderValuesEnum = Collections.enumeration(theHeaderValues);
		} else {
			theHeaderValuesEnum = Collections.enumeration(Collections.<String> emptyList());
		}
		return theHeaderValuesEnum;
	}

	@SuppressWarnings("unused")
	public int getIntHeader(String arg0) {
		throw new UnsupportedOperationException();
	}

	public String getMethod() {
		return this.mMethod;
	}

	public String getPathInfo() {
		throw new UnsupportedOperationException();
	}

	public String getPathTranslated() {
		throw new UnsupportedOperationException();
	}

	public String getQueryString() {
		if (this.mQueryString == null) {
			throw new UnsupportedOperationException();
		}
		return this.mQueryString;
	}

	public String getRemoteUser() {
		return this.mRemoteUser;
	}

	public String getRequestURI() {
		if (this.mRequestURI == null) {
			throw new UnsupportedOperationException();
		}
		return this.mRequestURI;
	}

	public StringBuffer getRequestURL() {
		final StringBuffer theURLBuffer = new StringBuffer();
		theURLBuffer.append(getScheme()).append("://").append(getRequestURI());
		final String theQueryString = getQueryString();
		if (!theQueryString.equals(net.violet.common.StringShop.EMPTY_STRING)) {
			theURLBuffer.append("?").append(theQueryString);
		}
		return theURLBuffer;
	}

	public String getRequestedSessionId() {
		throw new UnsupportedOperationException();
	}

	public String getServletPath() {
		throw new UnsupportedOperationException();
	}

	public HttpSession getSession() {
		return this.mSession;
	}

	@SuppressWarnings("unused")
	public HttpSession getSession(boolean arg0) {
		return this.mSession;
	}

	public Principal getUserPrincipal() {
		throw new UnsupportedOperationException();
	}

	public boolean isRequestedSessionIdFromCookie() {
		throw new UnsupportedOperationException();
	}

	public boolean isRequestedSessionIdFromURL() {
		throw new UnsupportedOperationException();
	}

	public boolean isRequestedSessionIdFromUrl() {
		throw new UnsupportedOperationException();
	}

	public boolean isRequestedSessionIdValid() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public boolean isUserInRole(String arg0) {
		throw new UnsupportedOperationException();
	}

	public Object getAttribute(String arg0) {
		return this.mAttributes.get(arg0);
	}

	public Enumeration getAttributeNames() {
		return this.mAttributes.keys();
	}

	public String getCharacterEncoding() {
		return this.mCharacterEncoding;
	}

	public int getContentLength() {
		return this.mData.size();
	}

	public String getContentType() {
		return this.mContentType;
	}

	public ServletInputStream getInputStream() {
		if (this.mInputStream == null) {
			this.mInputStream = new ServletInputStream() {

				@Override
				public int read() throws IOException {
					return getReader().read();
				}

			};
		}
		return this.mInputStream;
	}

	public String getLocalAddr() {
		throw new UnsupportedOperationException();
	}

	public String getLocalName() {
		throw new UnsupportedOperationException();
	}

	public int getLocalPort() {
		throw new UnsupportedOperationException();
	}

	public Locale getLocale() {
		throw new UnsupportedOperationException();
	}

	public Enumeration getLocales() {
		throw new UnsupportedOperationException();
	}

	public String getParameter(String arg0) {
		return this.mParameters.get(arg0);
	}

	public Map<String, String> getParameterMap() {
		return this.mParameters;
	}

	public Enumeration<String> getParameterNames() {
		return this.mParameters.keys();
	}

	@SuppressWarnings("unused")
	public String[] getParameterValues(String arg0) {
		return this.mParameters.values().toArray(new String[this.mParameters.size()]);
	}

	public String getProtocol() {
		throw new UnsupportedOperationException();
	}

	public BufferedReader getReader() {
		if (this.mReader == null) {
			this.mReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.mData.toByteArray())));
		}
		return this.mReader;
	}

	@SuppressWarnings("unused")
	public String getRealPath(String arg0) {
		throw new UnsupportedOperationException();
	}

	public String getRemoteAddr() {
		throw new UnsupportedOperationException();
	}

	public String getRemoteHost() {
		throw new UnsupportedOperationException();
	}

	public int getRemotePort() {
		throw new UnsupportedOperationException();
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return this.mDispatchers.get(path);
	}

	public String getScheme() {
		return "http";
	}

	public String getServerName() {
		throw new UnsupportedOperationException();
	}

	public int getServerPort() {
		throw new UnsupportedOperationException();
	}

	public boolean isSecure() {
		throw new UnsupportedOperationException();
	}

	public void removeAttribute(String arg0) {
		if (null != this.mAttributes.remove(arg0)) {
			throw new UnsupportedOperationException();
		}
	}

	public void setAttribute(String arg0, Object arg1) {
		this.mAttributes.put(arg0, arg1);
	}

	public void setCharacterEncoding(String arg0) {
		this.mCharacterEncoding = arg0;
	}

	/**
	 * Modifie l'utilisateur (pour l'authentification).
	 * 
	 * @param inRemoteUser
	 *            utilisateur pour l'authentification (ou <code>null</code>).
	 */
	public void setRemoteUser(String inRemoteUser) {
		this.mRemoteUser = inRemoteUser;
	}

	/**
	 * Modifie la méthode.
	 * 
	 * @param inMethod
	 *            méthode de la requête.
	 */
	public void setMethod(String inMethod) {
		this.mMethod = inMethod;
	}

	public OutputStream getDataOutputStream() {
		return this.mData;
	}

	public void setContentType(String inContentType) {
		this.mContentType = inContentType;
	}

	/**
	 * Enregistre un header dans la requête. Si un header du même nom était déjà
	 * présent, il est écrasé avec la nouvelle valeur.
	 * 
	 * @param inHeaderKey
	 *            nom du header.
	 * @param inHeaderValue
	 *            valeur du header.
	 */
	public void setHeader(String inHeaderKey, String inHeaderValue) {
		final List<String> theHeaderValues = new LinkedList<String>();
		theHeaderValues.add(inHeaderValue);
		this.mHeaders.put(inHeaderKey, theHeaderValues);
	}

	/**
	 * Modifie un paramètre.
	 * 
	 * @param inName
	 *            nom du paramètre.
	 * @param inValue
	 *            valeur du paramètre.
	 */
	public void setParameter(String inName, String inValue) {
		this.mParameters.put(inName, inValue);
	}
}
