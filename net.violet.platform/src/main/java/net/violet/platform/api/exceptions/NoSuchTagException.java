package net.violet.platform.api.exceptions;

public class NoSuchTagException extends APIException {

	public NoSuchTagException() {
		super(ErrorCode.NoSuchTag, APIErrorMessage.NO_SUCH_TAG);
	}

}
