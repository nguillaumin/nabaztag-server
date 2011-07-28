package net.violet.platform.web;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe pour la réponse.
 */

public class ServletTestResponse implements HttpServletResponse {

	/**
	 * Writer (pour l'accès avec un writer).
	 */
	private final PrintWriter mWriter;
	/**
	 * Output stream (pour l'accès avec un output stream).
	 */
	private final ServletOutputStream mServletOutputStream;
	/**
	 * Là où sont vraiment les données.
	 */
	private final ByteArrayOutputStream mOutputStream;
	private int mStatusCode;
	private String mContentType;
	/**
	 * Référence sur les entêtes HTTP.
	 */
	private final Map<String, List<String>> mHeaders;

	protected final List<Cookie> cookies = new ArrayList<Cookie>();

	protected ServletTestResponse() {
		this.mOutputStream = new ByteArrayOutputStream();
		this.mWriter = new PrintWriter(this.mOutputStream);
		this.mServletOutputStream = new ServletOutputStream() {

			@Override
			public void write(int inByte) {
				ServletTestResponse.this.mOutputStream.write(inByte);
			}
		};
		this.mStatusCode = HttpServletResponse.SC_OK;
		this.mHeaders = new HashMap<String, List<String>>();
	}

	public void addCookie(Cookie arg0) {
		this.cookies.add(arg0);
	}

	@SuppressWarnings("unused")
	public void addDateHeader(String arg0, long arg1) {
		throw new UnsupportedOperationException();
	}

	public void addHeader(String inHeaderName, String inHeaderValue) {
		List<String> theValues = this.mHeaders.get(inHeaderName);
		if (theValues == null) {
			theValues = new LinkedList<String>();
			this.mHeaders.put(inHeaderName, theValues);
		}
		theValues.add(inHeaderValue);
	}

	public void addIntHeader(String inHeaderName, int inHeaderValue) {
		addHeader(inHeaderName, String.valueOf(inHeaderValue));
	}

	public boolean containsHeader(String inHeaderName) {
		return this.mHeaders.containsKey(inHeaderName);
	}

	@SuppressWarnings("unused")
	public String encodeRedirectURL(String arg0) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public String encodeRedirectUrl(String arg0) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public String encodeURL(String arg0) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public String encodeUrl(String arg0) {
		throw new UnsupportedOperationException();
	}

	public void sendError(int inStatus) {
		this.mStatusCode = inStatus;
	}

	@SuppressWarnings("unused")
	public void sendError(int inStatus, String inStatusLine) {
		this.mStatusCode = inStatus;
	}

	@SuppressWarnings("unused")
	public void sendRedirect(String arg0) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void setDateHeader(String arg0, long arg1) {
		throw new UnsupportedOperationException();
	}

	public void setHeader(String inHeaderName, String inHeaderValue) {
		final List<String> theValues = new LinkedList<String>();
		theValues.add(inHeaderValue);
		this.mHeaders.put(inHeaderName, theValues);
	}

	public void setIntHeader(String inHeaderName, int inHeaderValue) {
		setHeader(inHeaderName, String.valueOf(inHeaderValue));
	}

	public void setStatus(int inStatus) {
		this.mStatusCode = inStatus;
	}

	@SuppressWarnings("unused")
	public void setStatus(int inStatus, String inStatusLine) {
		this.mStatusCode = inStatus;
	}

	public void flushBuffer() {
		throw new UnsupportedOperationException();
	}

	public int getBufferSize() {
		throw new UnsupportedOperationException();
	}

	public String getCharacterEncoding() {
		throw new UnsupportedOperationException();
	}

	public String getContentType() {
		return this.mContentType;
	}

	public Locale getLocale() {
		throw new UnsupportedOperationException();
	}

	public ServletOutputStream getOutputStream() {
		return this.mServletOutputStream;
	}

	public PrintWriter getWriter() {
		return this.mWriter;
	}

	public boolean isCommitted() {
		throw new UnsupportedOperationException();
	}

	public void reset() {
		throw new UnsupportedOperationException();
	}

	public void resetBuffer() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void setBufferSize(int arg0) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void setCharacterEncoding(String arg0) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void setContentLength(int inContentLength) {
	}

	public void setContentType(String inContentType) {
		this.mContentType = inContentType;
	}

	@SuppressWarnings("unused")
	public void setLocale(Locale arg0) {
		throw new UnsupportedOperationException();
	}

	public int getStatus() {
		return this.mStatusCode;
	}

	public String getWrittenData() {
		this.mWriter.flush();
		return this.mOutputStream.toString();
	}
}
