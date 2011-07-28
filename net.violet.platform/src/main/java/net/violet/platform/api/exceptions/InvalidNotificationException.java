package net.violet.platform.api.exceptions;

public class InvalidNotificationException extends APIException {

	public InvalidNotificationException() {
		super(ErrorCode.InvalidNotification, APIErrorMessage.INVALID_NOTIFICATION);
	}
}
