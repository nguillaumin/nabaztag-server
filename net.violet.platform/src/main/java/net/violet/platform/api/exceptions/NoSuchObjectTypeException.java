package net.violet.platform.api.exceptions;

public class NoSuchObjectTypeException extends APIException {

	public NoSuchObjectTypeException(String inType) {
		super(ErrorCode.NoSuchType, APIErrorMessage.NO_SUCH_TYPE, inType);
	}
}
