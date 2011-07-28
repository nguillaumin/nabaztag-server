package net.violet.platform.api.exceptions;

public class ForbiddenException extends APIException {

	public ForbiddenException() {
		super(ErrorCode.ForbiddenException, APIErrorMessage.PERMISSION_DENIED);
	}

}
