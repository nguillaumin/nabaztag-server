package net.violet.platform.api.exceptions;

public class NoSuchItemException extends APIException {

	public NoSuchItemException() {
		super(ErrorCode.NoSuchItem, APIErrorMessage.NO_SUCH_ITEM);
	}
}
