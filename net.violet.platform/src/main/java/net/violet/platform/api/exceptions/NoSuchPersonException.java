package net.violet.platform.api.exceptions;

public class NoSuchPersonException extends APIException {

	public NoSuchPersonException(APIErrorMessage inMessage) {
		super(ErrorCode.NoSuchPerson, inMessage);
	}

	public NoSuchPersonException(APIErrorMessage inMessage, String inParamValue) {
		super(ErrorCode.NoSuchPerson, inMessage, inParamValue);
	}
}
