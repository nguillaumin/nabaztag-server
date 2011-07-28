package net.violet.platform.api.exceptions;

public class TTSGenerationFailedException extends APIException {

	public TTSGenerationFailedException() {
		super(ErrorCode.TTSGenerationFailed, APIErrorMessage.TTS_GENERATION_FAILED);
	}
}
