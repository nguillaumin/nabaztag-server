package net.violet.platform.api.exceptions;

public class NoSuchNewsException extends APIException {

	public NoSuchNewsException() {
		super(ErrorCode.NoSuchNews, APIErrorMessage.NO_SUCH_NEWS);
	}
}
