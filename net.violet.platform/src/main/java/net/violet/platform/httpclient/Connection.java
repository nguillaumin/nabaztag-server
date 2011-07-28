package net.violet.platform.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.nutch.net.protocols.HttpDateFormat;

import com.sun.syndication.io.impl.DateParser;

/**
 * A Connection should be created by a ConnectionsManager object. It contains the response received from the server
 * and can be used to read the result's content and/or some information returned by the server.
 * 
 * A Connection object is NOT thread-safe and should not be accessed by several threads.
 * 
 * Do not forget to close the connection when all required information have been read.
 */
public class Connection {

	private final GetMethod method;
	private final boolean isClosed;

	/**
	 * Can only be used by the ConnectionsManager
	 * @param method
	 */
	Connection(GetMethod method) {
		this.method = method;
		this.isClosed = false;
	}

	/**
	 * Returns the value of the 'Last-Modified' header. Can be null if the server does not provide this information.
	 * @return
	 */
	public String getLastModified() {
		return getHeaderValue("Last-Modified");
	};

	/**
	 * Returns the value of the 'Last-Modified' header as a Date object.
	 * May be null if the server does not provided this information.
	 * @return
	 * @throws ParseException if the given header is not a parsable date format.
	 */
	public Date getLastModifiedAsDate() throws ParseException {
		final String lastModifiedAsString = getLastModified();
		if (lastModifiedAsString == null) {
			return null;
		}

		Date theDate;
		try {
			theDate = HttpDateFormat.toDate(lastModifiedAsString);
		} catch (final ParseException e) {
			theDate = DateParser.parseDate(lastModifiedAsString);
			if (theDate == null) {
				throw new ParseException("Cannot parse " + lastModifiedAsString + " into a date", 0);
			}
		}

		return theDate;
	}

	/**
	 * Returns the Etag. May be null.
	 * @return
	 */
	public String getEtag() {
		return getHeaderValue("ETag");
	}

	public InputStream getInputStream() throws IOException {
		final String encoding = getHeaderValue("Content-Encoding");
		final InputStream responseAsStream = this.method.getResponseBodyAsStream();
		if ((encoding != null) && encoding.equalsIgnoreCase("gzip")) {
			return new GZIPInputStream(responseAsStream);
		} else if ((encoding != null) && encoding.equalsIgnoreCase("deflate")) {
			return new InflaterInputStream(responseAsStream, new Inflater(true));
		} else {
			return responseAsStream;
		}
	}

	/**
	 * Closes the connection and release the resources.
	 * Closing an already closed connection does not matter.
	 */
	public void close() {
		if (!this.isClosed) {
			this.method.releaseConnection();
		}
	}

	/**
	 * Returns true if the connection is closed.
	 * If the connection is closed, calling other methods may give unexpected behavior (exceptions or invalid results).
	 * @return
	 */
	public boolean isClosed() {
		return this.isClosed;
	}

	private String getHeaderValue(String key) {
		final Header theHeader = this.method.getResponseHeader(key);
		return theHeader == null ? null : theHeader.getValue();
	}

}
