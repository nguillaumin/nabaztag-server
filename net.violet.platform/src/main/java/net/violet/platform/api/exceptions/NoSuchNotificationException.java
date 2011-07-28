package net.violet.platform.api.exceptions;

public class NoSuchNotificationException extends APIException {

	public NoSuchNotificationException() {
		super(ErrorCode.NoSuchNotification, APIErrorMessage.NO_SUCH_NOTIFICATION);
	}
}
