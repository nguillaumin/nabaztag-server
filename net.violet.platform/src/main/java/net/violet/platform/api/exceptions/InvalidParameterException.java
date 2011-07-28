package net.violet.platform.api.exceptions;

public class InvalidParameterException extends APIException {

	/**
	 * Tell wich parameter is invalid with a dedicated message
	 * 
	 * @param msg : NOT_A_BOOLEAN, NOT_A_STRING, ..
	 * @param inParamName
	 */
	public InvalidParameterException(APIErrorMessage msg, String inParamName) {
		super(ErrorCode.InvalidParameter, msg, inParamName);
	}

	/**
	 * Tell wich parameter and value is invalid with a dedicated message
	 * 
	 * @param msg : NOT_A_BOOLEAN, NOT_A_STRING, ..
	 * @param inParamName
	 * @param inParamValue
	 */
	public InvalidParameterException(APIErrorMessage msg, String inParamName, String inParamValue) {
		super(ErrorCode.InvalidParameter, msg, inParamName, inParamValue);
	}

	/**
	 * Tell wich parameter is invalid with a generic message
	 * 
	 * @param inParamName
	 */
	public InvalidParameterException(String inParamName) {
		super(ErrorCode.InvalidParameter, APIErrorMessage.INVALID_PARAMETER, inParamName);
	}

	/**
	 * Tell wich parameter is invalid and why
	 * 
	 * @param inParamName
	 * @param invalidReason
	 */
	public InvalidParameterException(String inParamName, String inParamValue) {
		super(ErrorCode.InvalidParameter, APIErrorMessage.INVALID_PARAMETER_BECAUSE, inParamName, inParamValue);
	}

	public InvalidParameterException(APIErrorMessage inMsg, Throwable cause) {
		super(ErrorCode.InvalidParameter, inMsg, cause);
	}

	protected InvalidParameterException(ErrorCode inError, APIErrorMessage inMsg, String... inReplacementValues) {
		super(inError, inMsg, inReplacementValues);
	}

}
