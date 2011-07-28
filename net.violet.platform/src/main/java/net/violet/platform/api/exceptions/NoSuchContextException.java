package net.violet.platform.api.exceptions;

public class NoSuchContextException extends APIException {

	public NoSuchContextException() {
		super(ErrorCode.NoSuchContext, APIErrorMessage.NO_SUCH_CONTEXT);
	}
}
