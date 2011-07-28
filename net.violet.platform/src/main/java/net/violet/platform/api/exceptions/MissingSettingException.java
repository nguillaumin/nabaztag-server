package net.violet.platform.api.exceptions;

public class MissingSettingException extends APIException {

	public MissingSettingException(String setting) {
		super(APIErrorMessage.MISSING_SETTING, setting);
	}

}
