package net.violet.platform.api.exceptions;

/**
 * Classe pour une exception indiquant qu'un objet (violet) n'est pas trouvé ou
 * n'existe pas.
 */
public class NoSuchObjectException extends APIException {

	public NoSuchObjectException() {
		super(ErrorCode.NoSuchObject, APIErrorMessage.NO_SUCH_OBJECT);
	}
}
