package net.violet.platform.api.exceptions;

public class BadCredentialsException extends APIException {

	public BadCredentialsException(APIErrorMessage inMessage) {
		super(ErrorCode.BadCredential, inMessage);
	}
}
