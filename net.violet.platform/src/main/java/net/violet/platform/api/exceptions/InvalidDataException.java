package net.violet.platform.api.exceptions;

public class InvalidDataException extends APIException {

	/**
	 * @param inMessage APIErrorMessage
	 * @param inReplacementValues one or more values to fill the message
	 *            parameters (they appear as {0}.. {n} in the message content)
	 */
	public InvalidDataException(APIErrorMessage inMessage, String... inReplacementValues) {
		super(ErrorCode.InvalidData, inMessage, inReplacementValues);
	}

}
