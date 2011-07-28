package net.violet.platform.voice.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.server.ProcessName;
import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * Classe pour accéder au serveur de TTS.
 */
public class TTSClient {


	private static final Logger LOGGER = Logger.getLogger(TTSClient.class);

	private final XmlRpcClient mXmlRpcClient;

	private static XmlRpcClient createXmlRpcClient(String inHostname) {
		final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		XmlRpcClient theClient = null;
		try {
			config.setServerURL(new URL(inHostname + ":" + TTSServerImpl.SERVER_PORT));
			theClient = new XmlRpcClient();
			theClient.setConfig(config);
		} catch (final MalformedURLException e) {
			TTSClient.LOGGER.fatal(e, e);
		}

		return theClient;
	}

	public TTSClient() {
		this(Constantes.TTS_SERVER);
	}

	public TTSClient(String hostName) {
		this.mXmlRpcClient = TTSClient.createXmlRpcClient("http://" + hostName);
	}

	public Map<String, Object> generateTTS(String inVoiceName, String inLangAbbr, String inText, int inPriority) {
		final String theProcessName = ProcessName.getProcessName();
		final Object[] params = new Object[] { theProcessName, inVoiceName, inLangAbbr, inText, new Integer(inPriority) };
		try {
			return (Map<String, Object>) this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.GENERATE_TTS_METHOD, params);
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
			System.err.println("XMLException : " + e.getMessage());
		}
		return null;
	}

	public Object[] getCurrentJobs() {
		try {
			return (Object[]) this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.LIST_TTS_CURRENT_JOBS_METHOD, new Object[] {});
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
		}
		return null;
	}

	public boolean killJob(int mJobId) {
		try {
			return ((Boolean) this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.KILL_JOB_METHOD, new Object[] { new Integer(mJobId) })).booleanValue();
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
		}
		return false;
	}

	public boolean reloadXml() {
		try {
			return ((Boolean) this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.RELOAD_XML, new Object[] {})).booleanValue();
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
		}
		return false;
	}

	public boolean reloadDrivers() {
		try {
			return ((Boolean) this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.RELOAD_DRIVERS, new Object[] {})).booleanValue();
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
		}
		return false;
	}

	public boolean cancelJob(int mJobId) {
		try {
			return ((Boolean) this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.CANCEL_JOB_METHOD, new Object[] { mJobId })).booleanValue();
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
		}
		return false;
	}

	public String getEngineStats(String engineName) {
		try {
			return this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.STATS_METHOD, new Object[] { engineName }).toString();
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
		}
		return "Failed to retrieve stats";
	}

	public int getSize() {
		try {
			return (Integer) this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.SIZE_METHOD, new Object[] {});
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
		}
		return -1;
	}

	public List<Map<String, String>> getVoiceList(String engineName) {
		Object[] theList = null;
		try {
			theList = (Object[]) this.mXmlRpcClient.execute(TTSServerImpl.HANDLER_PREFIX + TTSServerImpl.LIST_VOICES_METHOD, new Object[] { engineName });
		} catch (final XmlRpcException e) {
			TTSClient.LOGGER.fatal(e, e);
		}
		final List<Map<String, String>> theResult;
		if (theList != null) {
			theResult = new ArrayList<Map<String, String>>(theList.length);
			for (final Object theVoiceData : theList) {
				final Map<String, String> theVoiceDataMap = (Map<String, String>) theVoiceData;
				theResult.add(theVoiceDataMap);
			}
		} else {
			theResult = null;
		}
		return theResult;
	}
}
