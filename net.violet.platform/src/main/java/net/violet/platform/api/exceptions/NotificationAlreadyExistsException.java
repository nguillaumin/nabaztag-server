package net.violet.platform.api.exceptions;

public class NotificationAlreadyExistsException extends APIException {

	public NotificationAlreadyExistsException() {
		super(ErrorCode.NotificationAlreadyExists, APIErrorMessage.NOTIFICATION_ALREADY_EXISTS);
	}
}
