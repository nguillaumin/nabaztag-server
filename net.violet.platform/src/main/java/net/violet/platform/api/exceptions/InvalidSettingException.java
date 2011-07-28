package net.violet.platform.api.exceptions;

public class InvalidSettingException extends APIException {

	public InvalidSettingException(String illegalSetting, String value) {
		super(APIErrorMessage.INVALID_SETTING, illegalSetting, value);
	}

}
