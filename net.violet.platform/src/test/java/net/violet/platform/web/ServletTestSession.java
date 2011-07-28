package net.violet.platform.web;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * Classe pour la session.
 */

@SuppressWarnings("deprecation")
public class ServletTestSession implements HttpSession {

	private final Hashtable<String, Object> mAttributes;

	/**
	 * Constructeur par défaut.
	 */
	public ServletTestSession() {
		this.mAttributes = new Hashtable<String, Object>();
	}

	public Object getAttribute(String inAttrName) {
		return this.mAttributes.get(inAttrName);
	}

	public Enumeration getAttributeNames() {
		return this.mAttributes.keys();
	}

	public long getCreationTime() {
		throw new UnsupportedOperationException();
	}

	public String getId() {
		throw new UnsupportedOperationException();
	}

	public long getLastAccessedTime() {
		throw new UnsupportedOperationException();
	}

	public int getMaxInactiveInterval() {
		throw new UnsupportedOperationException();
	}

	public ServletContext getServletContext() {
		throw new UnsupportedOperationException();
	}

	public HttpSessionContext getSessionContext() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public Object getValue(String arg0) {
		throw new UnsupportedOperationException();
	}

	public String[] getValueNames() {
		throw new UnsupportedOperationException();
	}

	public void invalidate() {
		throw new UnsupportedOperationException();
	}

	public boolean isNew() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void putValue(String arg0, Object arg1) {
		throw new UnsupportedOperationException();
	}

	public void removeAttribute(String inAttrName) {
		this.mAttributes.remove(inAttrName);
	}

	@SuppressWarnings("unused")
	public void removeValue(String arg0) {
		throw new UnsupportedOperationException();
	}

	public void setAttribute(String inAttrName, Object inAttrValue) {
		this.mAttributes.put(inAttrName, inAttrValue);
	}

	@SuppressWarnings("unused")
	public void setMaxInactiveInterval(int arg0) {
		throw new UnsupportedOperationException();
	}
}
