package net.violet.platform.httpclient;

import java.net.HttpURLConnection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import net.violet.common.Constantes;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;
import org.apache.nutch.net.protocols.HttpDateFormat;

/**
 * The ConnectionsManager is the best way to create Connection objects (actually, creating Connection objects through another way
 * will be punished !). 
 * 
 * It supports some protocols (actually feed and http) and can smartly open connections with given urls.
 *  
 * Several ConnectionsManager instances can exist at the same time, however, considering that this object is thread-safe, 
 * it is strongly recommended to avoir creating more than one instance (unless parameters, such as timeout, are different).
 * 
 * Do not forget to call the shutdown method when the ConnectionsManager is no longer used to properly close
 * the opened/pending connections.
 */
public class ConnectionsManager {

	private static final Logger LOGGER = Logger.getLogger(ConnectionsManager.class);

	static {
		Protocol.registerProtocol("feed", Protocol.getProtocol("http"));
	}

	private final MultiThreadedHttpConnectionManager manager;
	private final Timer timer;

	/**
	 * Creates a ConnectionsManager which will be able to open connectionsAmount of connections at the same time.
	 * The default timeout value is used (10 seconds)
	 * @param connectionsAmount
	 */
	public ConnectionsManager(int connectionsAmount) {
		this(connectionsAmount, Constantes.CONNECTION_TIMEOUT);
	}

	/**
	 * Creates a ConnectionsManager by providing the maximum amount of opened connections at a moment and 
	 * the timeout in milliseconds.
	 * @param connectionsAmount
	 * @param timeout
	 */
	public ConnectionsManager(int connectionsAmount, int timeout) {
		this.manager = new MultiThreadedHttpConnectionManager();
		this.manager.getParams().setDefaultMaxConnectionsPerHost(connectionsAmount);
		this.manager.getParams().setStaleCheckingEnabled(true);
		this.manager.getParams().setConnectionTimeout(timeout);
		this.manager.getParams().setSoTimeout(timeout);

		//herited from the old class but is this really necessary ?
		final TimerTask theTask = new TimerTask() {

			@Override
			public void run() {
				ConnectionsManager.this.manager.deleteClosedConnections();
			};
		};
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(theTask, Constantes.GC_CYCLE, Constantes.GC_CYCLE);
	}

	/**
	 * Uses the given ConnectionConfiguration object to open a new connection. Returns an open Connection.
	 * 
	 * This method may return null, which is NOT a failure : if the ConnectionConfiguration provides some
	 * condition parameters (such as 'If-modified-since' or an etag) and these conditions are not true (basically the
	 * content has not been modified since the last time) no connections are opened and null is return.
	 * 
	 * A ConnectionException is thrown if either a connection problem happens or the server response is an error code.
	 * @param configuration
	 * @return
	 * @throws ConnectionException
	 */
	public Connection openConnection(ConnectionConfiguration configuration) throws ConnectionException {

		final GetMethod theGetMethod = new GetMethod(configuration.getUri().toString());

		theGetMethod.setRequestHeader("User-Agent", "CrawlerViolet");
		theGetMethod.setRequestHeader("Accept-Encoding", "gzip, *");
		theGetMethod.setFollowRedirects(true);

		final HttpClient theClient = new HttpClient(this.manager);

		// is authentication required ?
		if (configuration.getCredentials() != null) {
			theGetMethod.setDoAuthentication(true);
			final HttpClientParams theParams = new HttpClientParams(DefaultHttpParams.getDefaultParams());
			theParams.setAuthenticationPreemptive(true);
			theParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
			theClient.setParams(theParams);
			final HttpState theState;
			if (theClient.getState() != null) {
				theState = theClient.getState();
				theState.clearCredentials();
			} else {
				theState = new HttpState();
			}
			theState.setCredentials(new AuthScope(configuration.getUri().getHost(), AuthScope.ANY_PORT), configuration.getCredentials());
		}

		// obtain the ETag from a local store, returns null if not found
		final String etag = configuration.getEtag();
		if (etag != null) {
			theGetMethod.addRequestHeader("If-None-Match", etag);
		}

		// obtain the Last-Modified from a local store, returns null if not found
		final Date lastModified = configuration.getLastModified();
		if (lastModified != null) {
			theGetMethod.addRequestHeader("If-Modified-Since", HttpDateFormat.toString(lastModified));
		}

		// establish connection, get response headers
		try {
			theClient.executeMethod(theGetMethod);
		} catch (final Exception e) {
			ConnectionsManager.LOGGER.fatal(e, e);
			theGetMethod.releaseConnection();
			throw new ConnectionException(e);
		}

		// if it returns Not modified then we already have the content, return null
		if (theGetMethod.getStatusCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
			theGetMethod.releaseConnection();
			return null;
		} else if (theGetMethod.getStatusCode() > 200) {
			theGetMethod.releaseConnection();
			throw new ConnectionException(theGetMethod.getStatusCode(), configuration);
		}

		return new Connection(theGetMethod);

	}

	/**
	 * Closes the ConnectionsManager. A closed ConnectionsManager cannot be used to open connections any more, all connections
	 * opened with this manager are closed.
	 */
	public void shutdown() {
		this.timer.cancel();
		this.manager.shutdown();
	}

}
