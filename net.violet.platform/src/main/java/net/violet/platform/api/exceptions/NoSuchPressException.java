package net.violet.platform.api.exceptions;

public class NoSuchPressException extends APIException {

	public NoSuchPressException() {
		super(ErrorCode.NoSuchPress, APIErrorMessage.NO_SUCH_PRESS);
	}
}
