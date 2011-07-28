package net.violet.platform.httpclient;

public class ConnectionException extends Exception {

	public ConnectionException(int statusCode, ConnectionConfiguration configuration) {
		super(statusCode + " " + configuration);
	}

	public ConnectionException(Throwable cause) {
		super(cause);
	}

}
