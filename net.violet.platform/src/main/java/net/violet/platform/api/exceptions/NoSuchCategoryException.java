package net.violet.platform.api.exceptions;

public class NoSuchCategoryException extends APIException {

	public NoSuchCategoryException(String categoryName) {
		super(ErrorCode.NoSuchCategory, APIErrorMessage.NO_SUCH_CATEGORY, categoryName);
	}

}
