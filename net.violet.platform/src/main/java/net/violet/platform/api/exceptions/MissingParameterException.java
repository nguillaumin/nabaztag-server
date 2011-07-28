package net.violet.platform.api.exceptions;

public class MissingParameterException extends InvalidParameterException {

	public MissingParameterException(String inParamName) {
		super(ErrorCode.MissingParameter, APIErrorMessage.MISSING_PARAMETER, inParamName);
	}
}
