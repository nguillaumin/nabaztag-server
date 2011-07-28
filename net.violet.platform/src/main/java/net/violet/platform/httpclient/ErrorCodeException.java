package net.violet.platform.httpclient;

public class ErrorCodeException extends Exception {

	public ErrorCodeException(int code) {
		super("Connection request sent error code : " + code);
	}

}
