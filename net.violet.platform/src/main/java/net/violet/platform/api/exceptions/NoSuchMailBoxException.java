package net.violet.platform.api.exceptions;

public final class NoSuchMailBoxException extends APIException {

	public NoSuchMailBoxException(String inMailBox) {
		super(APIErrorMessage.NO_SUCH_MAIL_BOX, inMailBox);
	}

}
