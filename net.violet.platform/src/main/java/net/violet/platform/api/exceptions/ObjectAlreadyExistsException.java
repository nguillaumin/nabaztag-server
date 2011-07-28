package net.violet.platform.api.exceptions;

public class ObjectAlreadyExistsException extends APIException {

	public ObjectAlreadyExistsException(APIErrorMessage inMessage) {
		super(ErrorCode.ObjectAlreadyExists, inMessage);
	}
}
