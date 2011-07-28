package net.violet.platform.api.exceptions;

public class BadMimeTypeException extends APIException {

	public BadMimeTypeException(String inMimeType) {
		super(ErrorCode.BadMimeType, APIErrorMessage.BAD_MIME_TYPE, inMimeType);
	}
}
