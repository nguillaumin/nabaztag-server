package net.violet.platform.api.exceptions;

/**
 * Exception thrown when the asked resource has not been modified (not really an
 * error)
 */
public class NotModifiedException extends APIException {

	public NotModifiedException() {
		super(ErrorCode.NotModified, APIErrorMessage.NOT_MODIFIED);
	}
}
