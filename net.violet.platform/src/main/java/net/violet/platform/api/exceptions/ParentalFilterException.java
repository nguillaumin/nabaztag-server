package net.violet.platform.api.exceptions;

public class ParentalFilterException extends APIException {

	public ParentalFilterException() {
		super(ErrorCode.ParentalFilter, APIErrorMessage.PARENTAL_FILTER);
	}
}
