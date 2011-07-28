package net.violet.platform.api.exceptions;

public class ContactAlreadyExistsException extends APIException {

	public ContactAlreadyExistsException() {
		super(ErrorCode.ContactAlreadyExists, APIErrorMessage.CONTACT_ALREADY_EXISTS);
	}
}
