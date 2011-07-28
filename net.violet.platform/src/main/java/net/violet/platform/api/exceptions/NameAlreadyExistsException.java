package net.violet.platform.api.exceptions;

public class NameAlreadyExistsException extends APIException {

	public NameAlreadyExistsException(APIErrorMessage inMessage) {
		super(ErrorCode.NameAlreadyExists, inMessage);
	}
}
