package net.violet.platform.api.exceptions;

public class NoSuchContactException extends APIException {

	public NoSuchContactException() {
		super(ErrorCode.NoSuchContact, APIErrorMessage.NO_SUCH_CONTACT);
	}
}
