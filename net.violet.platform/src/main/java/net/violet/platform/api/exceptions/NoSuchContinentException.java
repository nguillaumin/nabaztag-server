package net.violet.platform.api.exceptions;

public class NoSuchContinentException extends APIException {

	public NoSuchContinentException(APIErrorMessage inMessage, String inParamValue) {
		super(ErrorCode.NoSuchProduct, inMessage, inParamValue);
	}

}
