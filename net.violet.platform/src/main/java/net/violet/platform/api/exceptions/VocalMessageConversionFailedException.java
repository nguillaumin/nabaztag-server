package net.violet.platform.api.exceptions;

public class VocalMessageConversionFailedException extends APIException {

	public VocalMessageConversionFailedException(APIErrorMessage inMessage) {
		super(ErrorCode.VocalMessageConversionFailed, inMessage);
	}
}
