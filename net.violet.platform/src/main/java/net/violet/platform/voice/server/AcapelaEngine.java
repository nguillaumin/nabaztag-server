package net.violet.platform.voice.server;

import java.io.IOException;
import java.net.SocketException;

public class AcapelaEngine extends CommonEngine {

	public static final String NAME = "acapela";

	public AcapelaEngine(TTSEngineConfig inConfig) {
		super(inConfig);
	}

	private static final String TTSFORMAT = "22KPCM16BITS";

	@Override
	protected String getTTSFormat() {
		return AcapelaEngine.TTSFORMAT;
	}

	@Override
	protected void throwError(int inErrorCode, String inErrorMsg) throws IOException {
		if (inErrorCode == 12) {
			throw new SocketException("Could not connect to TTS server (error 12) [" + inErrorCode + "]");
		} else if (inErrorCode == 1) {
			throw new IllegalStateException("Error with voice (bad state?) (error 1) [" + inErrorMsg + "]");
		} else if (inErrorCode == -16) {
			throw new SocketException("Network problem (error -16) [" + inErrorMsg + "]");
		} else {
			throw new IOException("Unknown error with underlying client (error " + inErrorCode + ") [" + inErrorMsg + "]");
		}
	}
}
