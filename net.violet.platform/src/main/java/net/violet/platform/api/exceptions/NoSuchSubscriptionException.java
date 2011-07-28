package net.violet.platform.api.exceptions;

public class NoSuchSubscriptionException extends APIException {

	public NoSuchSubscriptionException() {
		super(ErrorCode.NoSuchSubscription, APIErrorMessage.NO_SUCH_SUBSCRIPTION);
	}

}
