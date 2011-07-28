package net.violet.platform.api.exceptions;

public class InvalidSessionException extends APIException {

	public InvalidSessionException() {
		super(ErrorCode.InvalidSession, APIErrorMessage.INVALID_SESSION);
	}
}
