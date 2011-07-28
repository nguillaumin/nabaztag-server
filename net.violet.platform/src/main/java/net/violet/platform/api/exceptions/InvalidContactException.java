package net.violet.platform.api.exceptions;

public class InvalidContactException extends APIException {

	public InvalidContactException() {
		super(ErrorCode.InvalidContact, APIErrorMessage.INVALID_CONTACT);
	}
}
