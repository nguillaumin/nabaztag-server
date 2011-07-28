package net.violet.platform.api.exceptions;

public class BlackListedException extends APIException {

	public BlackListedException() {
		super(ErrorCode.Blacklisted, APIErrorMessage.BLACKLISTED);
	}
}
