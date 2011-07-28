package net.violet.platform.api.exceptions;

public class NoSuchGroupException extends APIException {

	public NoSuchGroupException() {
		super(APIErrorMessage.NO_SUCH_GROUP);
	}

}
