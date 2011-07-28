package net.violet.platform.api.exceptions;

public class InvalidMimeTypeException extends APIException {

	private static final long serialVersionUID = -3607891931705669275L;

	public InvalidMimeTypeException(String inMimeType) {
		super(APIErrorMessage.INVALID_MIME_TYPE, inMimeType);
	}

}
