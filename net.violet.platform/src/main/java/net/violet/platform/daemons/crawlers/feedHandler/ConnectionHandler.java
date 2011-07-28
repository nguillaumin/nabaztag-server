package net.violet.platform.daemons.crawlers.feedHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;
import org.apache.nutch.net.protocols.HttpDateFormat;

import com.sun.syndication.io.impl.DateParser;

/**
 * Handles a connection
 */
public class ConnectionHandler {

	private static final Logger LOGGER = Logger.getLogger(ConnectionHandler.class);
	private static final String FEED = "feed";
	private static final String HTTP = "http";
	private static final String COLUMN_SLASH_SLASH = "://";
	public static final String PROTOCOL_HTTP = ConnectionHandler.HTTP + ConnectionHandler.COLUMN_SLASH_SLASH;
	private static final String PROTOCOL_FEED = ConnectionHandler.FEED + ConnectionHandler.COLUMN_SLASH_SLASH;
	static {
		Protocol.registerProtocol(ConnectionHandler.FEED, Protocol.getProtocol(ConnectionHandler.HTTP));
	}

	public static class ConnectionSettings {

		private final String mUrl;
		private final String mHost;
		private String mETag;
		private Date mLastModified;
		private final Credentials authentificationsSettings;

		public ConnectionSettings(String inURL, String inETag, Date inLasModified, String inUserName, String inPassword) throws MalformedURLException {
			this.mUrl = inURL;
			this.mETag = inETag;
			this.mLastModified = inLasModified;
			/* create a URL to the given url */
			final URL sourceURL = new URL(inURL.replaceAll(ConnectionHandler.PROTOCOL_FEED, ConnectionHandler.PROTOCOL_HTTP));
			this.mHost = sourceURL.getHost();

			if ((inUserName != null) && (inPassword != null)) {
				this.authentificationsSettings = new NTCredentials(inUserName, inPassword, this.mHost, net.violet.common.StringShop.EMPTY_STRING);
			} else {
				this.authentificationsSettings = null;
			}
		}

		/**
		 * @return the mUrl
		 */
		public String getUrl() {
			return this.mUrl;
		}

		/**
		 * @return the mETag
		 */
		public String getETag() {
			return this.mETag;
		}

		/**
		 * @return the mLastModified
		 */
		public String getLastModified() {
			return this.mLastModified != null ? HttpDateFormat.toString(this.mLastModified) : null;
		}

		public Date getLastModifiedDate() {
			return this.mLastModified;
		}

		/**
		 * @param tag the mETag to set
		 */
		public void setETag(String tag) {
			this.mETag = tag;
		}

		/**
		 * @param lastModified the mLastModified to set
		 */
		public void setLastModified(String lastModified) {
			if (lastModified != null) {
				try {
					this.mLastModified = HttpDateFormat.toDate(lastModified);
				} catch (final ParseException e) {
					this.mLastModified = DateParser.parseDate(lastModified);

					if (this.mLastModified == null) {
						ConnectionHandler.LOGGER.fatal("CANNOT PARSE lastmodified : " + lastModified);
					}
				}
			} else {
				this.mLastModified = null;
			}
		}

		public Credentials getAuthenticationSettings() {
			return this.authentificationsSettings;
		}

		/**
		 * @return the host
		 */
		public String getHost() {
			return this.mHost;
		}
	}

	private final Map<ConnectionSettings, GetMethod> mOpenConnections = new HashMap<ConnectionSettings, GetMethod>();
	private final MultiThreadedHttpConnectionManager mConnectionManager;
	private final Timer mTimer = new Timer();
	private static final String[] USER_AGENT = new String[] { "User-Agent", "CrawlerViolet" };

	public ConnectionHandler(int inNbConnection) {
		this.mConnectionManager = new MultiThreadedHttpConnectionManager();
		this.mConnectionManager.getParams().setDefaultMaxConnectionsPerHost(inNbConnection);
		this.mConnectionManager.getParams().setStaleCheckingEnabled(true);
		this.mConnectionManager.getParams().setConnectionTimeout(net.violet.common.Constantes.CONNECTION_TIMEOUT);
		// Time to way for an answer
		this.mConnectionManager.getParams().setSoTimeout(net.violet.common.Constantes.CONNECTION_TIMEOUT);
		final TimerTask theTask = new TimerTask() {

			@Override
			public void run() {
				ConnectionHandler.this.mConnectionManager.deleteClosedConnections();
			};
		};
		this.mTimer.scheduleAtFixedRate(theTask, net.violet.common.Constantes.GC_CYCLE, net.violet.common.Constantes.GC_CYCLE);
	}

	public InputStream connect(ConnectionSettings inConnectionSetting) throws IOException {
		final HttpClient theClient;
		final InputStream theResultingInputStream;
		final GetMethod theGetMethod = new GetMethod(inConnectionSetting.getUrl());

		final GetMethod previousConnection;

		synchronized (this.mOpenConnections) {
			previousConnection = this.mOpenConnections.put(inConnectionSetting, theGetMethod);
		}

		// Unlikely to happen
		if (previousConnection != null) {
			previousConnection.releaseConnection();
		}

		if (inConnectionSetting.getAuthenticationSettings() != null) {
			theGetMethod.setDoAuthentication(true);
			final HttpClientParams theParams = new HttpClientParams(DefaultHttpParams.getDefaultParams());
			theParams.setAuthenticationPreemptive(true);
			// Ignore cookies
			theParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
			theClient = new HttpClient(theParams, this.mConnectionManager);
			final HttpState theState;

			if (theClient.getState() != null) {
				theState = theClient.getState();
				theState.clearCredentials();
			} else {
				theState = new HttpState();
			}

			theState.setCredentials(new AuthScope(inConnectionSetting.getHost(), AuthScope.ANY_PORT), inConnectionSetting.getAuthenticationSettings());

		} else {
			theClient = new HttpClient(this.mConnectionManager);
		}

		theGetMethod.setRequestHeader(ConnectionHandler.USER_AGENT[0], ConnectionHandler.USER_AGENT[1]);

		final String encoding;

		// add parameters to the connection
		theGetMethod.setFollowRedirects(true);
		// allow both GZip and Deflate (ZLib) encodings
		theGetMethod.setRequestHeader("Accept-Encoding", "gzip, *");

		// obtain the ETag from a local store, returns null if not found
		final String etag = inConnectionSetting.getETag();

		if (etag != null) {
			theGetMethod.addRequestHeader("If-None-Match", etag);
		} else {
			ConnectionHandler.LOGGER.info("no ETag Found");
		}

		// obtain the Last-Modified from a local store, returns null if not
		// found
		final String lastModified = inConnectionSetting.getLastModified();
		if (lastModified != null) {
			ConnectionHandler.LOGGER.info("last modified : " + lastModified);
			theGetMethod.addRequestHeader("If-Modified-Since", lastModified);
		} else {
			ConnectionHandler.LOGGER.info(" no last modified found");
		}

		// establish connection, get response headers

		theClient.executeMethod(theGetMethod);

		// if it returns Not modified then we already have the content, return
		if (theGetMethod.getStatusCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
			// Si on se dit qu'a priori, on ne se reconnecte pas au même
			// serveur, ça vaut le coup
			// de déconnecter (dans le cas de Twitter & Gmail, si, on va se
			// reconnecter au même serveur).
			// sourceConnection.disconnect();
			ConnectionHandler.LOGGER.info("No modification");
			theGetMethod.releaseConnection();
			return null;
		} else if (theGetMethod.getStatusCode() > 200) {
			final StringBuilder theMsg = new StringBuilder("the web site answered : ");
			theMsg.append(theGetMethod.getStatusCode());
			theMsg.append(" for : ");
			theMsg.append(inConnectionSetting.getUrl());
			if (inConnectionSetting.getAuthenticationSettings() != null) {
				theMsg.append(" | username = ");
				final Credentials theCredentials = inConnectionSetting.getAuthenticationSettings();
				theMsg.append(theCredentials);

			}

			ConnectionHandler.LOGGER.info(theMsg.toString());
			theGetMethod.releaseConnection();
			return null;
		}

		// get the last modified & etag and
		// store them for the next check

		Header theHeader = theGetMethod.getResponseHeader("Last-Modified");

		if (theHeader != null) {
			inConnectionSetting.setLastModified(theHeader.getValue());
		}

		theHeader = theGetMethod.getResponseHeader("Content-Encoding");
		// obtain the encoding returned by the server
		if (theHeader != null) {
			encoding = theHeader.getValue();
		} else {
			encoding = null;
		}

		theHeader = theGetMethod.getResponseHeader("ETag");
		if (theHeader != null) {
			inConnectionSetting.setETag(theHeader.getValue());
		}
		// create the appropriate stream wrapper based on
		// the encoding type
		if ((encoding != null) && encoding.equalsIgnoreCase("gzip")) {
			theResultingInputStream = new GZIPInputStream(theGetMethod.getResponseBodyAsStream());
		} else if ((encoding != null) && encoding.equalsIgnoreCase("deflate")) {
			theResultingInputStream = new InflaterInputStream(theGetMethod.getResponseBodyAsStream(), new Inflater(true));
		} else {
			theResultingInputStream = theGetMethod.getResponseBodyAsStream();
		}

		return theResultingInputStream;
	}

	public void disconnect(ConnectionSettings inConnectionSetting) {
		if (inConnectionSetting != null) {
			final GetMethod connection2Release;

			synchronized (this.mOpenConnections) {
				connection2Release = this.mOpenConnections.remove(inConnectionSetting);
			}

			if (connection2Release != null) {
				connection2Release.releaseConnection();
			}
		}
	}

	public void shutdown() {
		synchronized (this.mOpenConnections) {
			this.mTimer.cancel();
			this.mConnectionManager.shutdown();
			this.mOpenConnections.clear();
		}
	}
}
