package net.violet.platform.api.exceptions;

public class InternalErrorException extends APIException {

	public InternalErrorException(String msg) {
		super(ErrorCode.InternalError, APIErrorMessage.INTERNAL_ERROR, msg);
	}

	public InternalErrorException(Throwable e) {
		super(ErrorCode.InternalError, APIErrorMessage.INTERNAL_ERROR, e.getMessage());
	}

	public InternalErrorException() {
		this(APIErrorMessage.INTERNAL_ERROR.getMessage());
	}
}
