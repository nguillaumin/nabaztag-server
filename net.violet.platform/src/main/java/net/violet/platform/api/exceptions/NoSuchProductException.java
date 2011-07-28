package net.violet.platform.api.exceptions;

public class NoSuchProductException extends APIException {

	public NoSuchProductException(APIErrorMessage inMessage) {
		super(ErrorCode.NoSuchProduct, inMessage);
	}

	public NoSuchProductException(APIErrorMessage inMessage, String inParamValue) {
		super(ErrorCode.NoSuchProduct, inMessage, inParamValue);
	}

}
