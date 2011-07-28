package net.violet.platform.httpclient;

public class InvalidContentException extends Exception {

	public InvalidContentException(String contentType) {
		super("This content cannot be downloaded : " + contentType);
	}

}
