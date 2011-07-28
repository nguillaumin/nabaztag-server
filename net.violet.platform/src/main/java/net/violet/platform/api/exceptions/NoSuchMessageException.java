package net.violet.platform.api.exceptions;

public class NoSuchMessageException extends APIException {

	public NoSuchMessageException(APIErrorMessage inMessage) {
		super(ErrorCode.NoSuchMessage, inMessage);
	}
}
