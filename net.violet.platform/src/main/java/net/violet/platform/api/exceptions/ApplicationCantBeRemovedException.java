package net.violet.platform.api.exceptions;

public class ApplicationCantBeRemovedException extends APIException {

	public ApplicationCantBeRemovedException() {
		super(APIErrorMessage.UNREMOVABLE_APPLICATION);
	}

}
