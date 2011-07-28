package net.violet.platform.feeds.processors;


public class ProcessingException extends Exception {

	public ProcessingException(String message) {
		super(message);
	}

	public ProcessingException(Throwable cause) {
		super(cause);
	}

}
