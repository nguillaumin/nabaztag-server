package net.violet.platform.api.exceptions;

public class InvalidFileFormatException extends APIException {

	public InvalidFileFormatException(String inFormat) {
		super(APIErrorMessage.INVALID_NAME, inFormat);
	}

}
