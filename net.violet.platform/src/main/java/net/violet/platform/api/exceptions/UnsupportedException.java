package net.violet.platform.api.exceptions;

/**
 * @author christophe - Violet
 */
public class UnsupportedException extends APIException {

	/**
	 * @param inMessage APIErrorMessage
	 * @param inReplacementValues one or more values to fill the message
	 *            parameters (they appear as {0}.. {n} in the message content)
	 */
	public UnsupportedException(APIErrorMessage inMessage, String... inReplacementValues) {
		super(ErrorCode.Unsupported, inMessage, inReplacementValues);
	}

}
