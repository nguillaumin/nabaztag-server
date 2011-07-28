package net.violet.platform.api.exceptions;

public class PersonAlreadyExistsException extends APIException {

	public PersonAlreadyExistsException(APIErrorMessage inMessage) {
		super(ErrorCode.PersonAlreadyExists, inMessage);
	}
}
