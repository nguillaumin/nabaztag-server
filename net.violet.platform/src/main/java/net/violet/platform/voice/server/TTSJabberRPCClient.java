package net.violet.platform.voice.server;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.server.ProcessName;
import net.violet.platform.xmpp.JabberRPCClientQuery;

public class TTSJabberRPCClient {

	public static Map<String, Object> generateTTS(String dest, String inVoiceName, String inLangAbbr, String inText, int inPriority) {
		final String theProcessName = ProcessName.getProcessName();
		final List<Object> params = Arrays.asList(new Object[] { theProcessName, inVoiceName, inLangAbbr, inText, new Integer(inPriority) });
		return (Map<String, Object>) JabberRPCClientQuery.getClientResult(dest, TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.GENERATE_TTS_METHOD, params);
	}

}
