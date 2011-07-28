package net.violet.platform.api.exceptions;

public class NoSuchFileException extends APIException {

	public NoSuchFileException() {
		super(ErrorCode.NoSuchFile, APIErrorMessage.NO_SUCH_FILE);
	}
}
