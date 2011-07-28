package net.violet.platform.httpclient;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;

import net.violet.common.StringShop;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.nutch.net.protocols.HttpDateFormat;

import com.sun.syndication.io.impl.DateParser;

/**
 * This class is used to wrap all usefull parameters that may be needed to open a Connection throw a ConnectionsManager object.
 * It works as a builder, create an instance with the only required parameter (uri) and then add the optional parameters with
 * addXXX methods.
 */
public class ConnectionConfiguration {

	private final URI uri;
	private Credentials credentials;
	private String etag;
	private Date lastModified;

	/**
	 * Creates a ConnectionConfiguration with only a uri. An exception is thrown if the given string cannot be parsed as a
	 * valid URL.
	 * @param uri
	 * @throws URISyntaxException
	 * @throws MalformedURLException 
	 * @throws URISyntaxException 
	 */
	public ConnectionConfiguration(String uri) throws URISyntaxException {
		this.uri = new URI(uri);
	}

	/**
	 * Adds credentials parameters if the Connection required credentials.
	 * @param login
	 * @param password
	 * @return
	 */
	public ConnectionConfiguration addCredentials(String login, String password) {
		this.credentials = new NTCredentials(login, password, this.uri.getHost(), StringShop.EMPTY_STRING);
		return this;
	}

	/**
	 * Adds an etag. This information will be send to the server which will then returns a code indicating if the content has
	 * been modified (basically the new etag will be different from this one).
	 * @param inEtag
	 * @return
	 */
	public ConnectionConfiguration addEtag(String inEtag) {
		this.etag = inEtag;
		return this;
	}

	/**
	 * Adds a date used to indicate the last time content where retrieved. If the content is still the same (the current last time date
	 * is before this given date) there will be no need to retrieve the content.
	 * @param inLastModified
	 * @return
	 */
	public ConnectionConfiguration addLastModified(Date inLastModified) {
		this.lastModified = inLastModified;
		return this;
	}

	/**
	 * See comment above. The given string is parsed to create a valid Date object. If parse processing failed an exception is 
	 * thrown.
	 * @param inLastModified
	 * @return
	 * @throws ParseException
	 */
	public ConnectionConfiguration addLastModified(String inLastModified) throws ParseException {
		Date theDate;
		try {
			theDate = HttpDateFormat.toDate(inLastModified);
		} catch (final ParseException e) {
			theDate = DateParser.parseDate(inLastModified);
			if (theDate == null) {
				throw new ParseException("Cannot parse " + inLastModified + " into a date", 0);
			}
		}

		return addLastModified(theDate);
	}

	public URI getUri() {
		return this.uri;
	}

	public Credentials getCredentials() {
		return this.credentials;
	}

	public String getEtag() {
		return this.etag;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	@Override
	public String toString() {
		return this.uri + " " + this.etag + " " + this.lastModified + " " + this.credentials;
	}

}
