package net.violet.platform.api.exceptions;

public class NoSuchClassException extends APIException {

	public NoSuchClassException(String inClass) {
		super(ErrorCode.NoSuchClass, APIErrorMessage.NO_SUCH_CLASS, inClass);
	}

}
