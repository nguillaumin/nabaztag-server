package net.violet.platform.api.exceptions;

public class NoSuchApplicationException extends APIException {

	public NoSuchApplicationException() {
		super(ErrorCode.NoSuchApplication, APIErrorMessage.NO_SUCH_APPLICATION);
	}
}
