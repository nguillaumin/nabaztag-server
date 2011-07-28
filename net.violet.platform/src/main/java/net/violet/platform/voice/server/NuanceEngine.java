package net.violet.platform.voice.server;

import java.io.IOException;
import java.net.SocketException;

public class NuanceEngine extends CommonEngine {

	public static final String NAME = "nuance";

	public NuanceEngine(TTSEngineConfig inConfig) {
		super(inConfig);
	}

	private static final String TTSFORMAT = "8KPCM16BITS";

	@Override
	protected String getTTSFormat() {
		return NuanceEngine.TTSFORMAT;
	}

	@Override
	protected void throwError(int inErrorCode, String inErrorMsg) throws IOException {

		if (((inErrorCode >= 80) && (inErrorCode <= 83)) || ((inErrorCode >= 101) && (inErrorCode <= 104)) || (inErrorCode == 91)) {
			throw new SocketException("NetWork error with underlying client (error " + inErrorCode + ") [" + inErrorMsg + "]");
		}

		throw new IOException("Unknown error with underlying client (error " + inErrorCode + ") [" + inErrorMsg + "]");
	}
}
