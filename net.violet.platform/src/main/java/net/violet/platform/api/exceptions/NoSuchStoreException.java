package net.violet.platform.api.exceptions;

public class NoSuchStoreException extends APIException {

	public NoSuchStoreException() {
		super(ErrorCode.NoSuchStore, APIErrorMessage.NO_SUCH_STORE);
	}
}
