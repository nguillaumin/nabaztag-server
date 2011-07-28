package net.violet.platform.voice.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.violet.common.utils.cache.CacheHandler;
import net.violet.common.utils.cache.CacheHandlerFactory;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcHandler;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.common.XmlRpcInvocationException;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcNoSuchHandlerException;
import org.apache.xmlrpc.webserver.WebServer;

/**
 * Classe pour le serveur de tts.
 */
public final class TTSServerImpl implements XmlRpcHandler {

	private static final Logger LOGGER = Logger.getLogger(TTSServerImpl.class);

	public static final String DATA = "DATA";

	public static final String FORMAT = "FORMAT";

	public static final String SPEAK = "SPEAK ";

	public static final int SERVER_PORT = 5555;

	static final String HANDLER_PREFIX = "TTSServerImpl.";

	static final String GENERATE_TTS_METHOD = "generateTTS";

	public static final String LIST_TTS_CURRENT_JOBS_METHOD = "getCurrentJobs";

	public static final String KILL_JOB_METHOD = "killJob";

	public static final String CANCEL_JOB_METHOD = "cancelJob";

	public static final String RELOAD_XML = "reloadXmlConfiguration";

	public static final String RELOAD_DRIVERS = "reloadDrivers";

	public static final String STATS_METHOD = "stats";

	public static final String SIZE_METHOD = "size";

	public static final String LIST_VOICES_METHOD = "listVoices";

	public static final String VOICE_NAME_KEY = "name";
	public static final String VOICE_LANG_KEY = "lang";

	public static final int TTS_TEXT_MAX_SIZE = 500;

	private static final Pattern MIN_REPEAT_CHARACTER = Pattern.compile("((.)(\\2){5,}+)");
	private static final String REPLACE_BY_GROUP2 = "$2";

	/**
	 * Paire VoiceName & Text pour faire référence aux objets.
	 */
	private static final class TTSCacheRef implements Serializable {

		private static final long serialVersionUID = 1L;

		private String mVoiceName;

		private String mText;

		/**
		 * Constructeur à partir d'une classe et de la valeur de hachage.
		 */
		private TTSCacheRef(String inVoiceName, String inText) {
			this.mVoiceName = inVoiceName;
			this.mText = inText;
		}

		@Override
		public int hashCode() {
			return this.mVoiceName.hashCode() + this.mText.hashCode();
		}

		@Override
		public boolean equals(Object inObject) {
			return (this == inObject) || ((inObject instanceof TTSCacheRef) && ((TTSCacheRef) inObject).mVoiceName.equals(this.mVoiceName) && ((TTSCacheRef) inObject).mText.equals(this.mText));
		}

		/**
		 * Ecriture de l'objet.
		 * 
		 * @param inOutputStream flux pour l'écriture.
		 * @throws IOException
		 */
		private void writeObject(ObjectOutputStream inOutputStream) throws IOException {
			inOutputStream.writeUTF(this.mVoiceName);
			inOutputStream.writeUTF(this.mText);
		}

		/**
		 * Lecture de l'objet.
		 * 
		 * @param inInputStream flux pour la lecture.
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void readObject(ObjectInputStream inInputStream) throws IOException {
			this.mVoiceName = inInputStream.readUTF();
			this.mText = inInputStream.readUTF();
		}

		private void add2Cache(Map<String, Object> inContent) {
			boolean TTSOK = false;
			final byte[] datas = (byte[]) inContent.get(TTSServerImpl.DATA);
			int i = datas.length - 1;

			while (!TTSOK && (i > 0)) {
				if (datas[i--] != 0) {
					TTSOK = true;
				}
			}

			if (!TTSOK) {
				TTSServerImpl.LOGGER.error("TTS plein de 0 V: " + this.mVoiceName + ", T: " + this.mText);
			}

			TTSServerImpl.CACHE_INSTANCE.add(this, inContent);
		}
	}

	/**
	 * Reference the cache instance
	 */
	private static final CacheHandler<TTSCacheRef, Map<String, Object>> CACHE_INSTANCE;

	static {
		CacheHandler<TTSCacheRef, Map<String, Object>> theCacheHandler = null;
		try {
			theCacheHandler = CacheHandlerFactory.getInstance("TTSCache");
		} catch (final Exception e) {
			TTSServerImpl.LOGGER.fatal(e, e);
		}
		CACHE_INSTANCE = theCacheHandler;
	}

	/** stats information **/
	private static long treatedTTS;
	private static long processedTime;
	private static long maxGenerateTime;

	/**
	 * Référence sur la config.
	 */
	private TTSConfig mConfig;

	/**
	 * Moteurs.
	 */
	private final Map<String, TTSEngine> mEngines;

	/**
	 * Constructeur,
	 */
	private TTSServerImpl() {
		// On charge la config la première fois.
		this.mConfig = TTSConfig.loadConfig();
		this.mEngines = new HashMap<String, TTSEngine>();
		for (final TTSEngineConfig theConfig : this.mConfig.getEngineConfigs()) {
			final TTSEngine theEngine = TTSEngine.createEngine(theConfig);
			this.mEngines.put(theConfig.getName(), theEngine);
		}
	}

	private Map<String, Object> generateTTS(XmlRpcRequest inRequest) throws XmlRpcException {
		final Map<String, Object> theResult;
		try {
			final String theProcessName = (String) inRequest.getParameter(0);
			final String theVoiceName = (String) inRequest.getParameter(1);
			final String theLang = (String) inRequest.getParameter(2);
			String theText = (String) inRequest.getParameter(3);

			if (theText.length() > TTSServerImpl.TTS_TEXT_MAX_SIZE) {
				theText = theText.substring(0, TTSServerImpl.TTS_TEXT_MAX_SIZE);
			}

			final int thePriority = ((Integer) inRequest.getParameter(4)).intValue();

			final Map<String, Object> cachedItem = TTSServerImpl.CACHE_INSTANCE.get(new TTSCacheRef(theVoiceName, theText));

			if (cachedItem == null) {
				try {
					theResult = generateTTS(theProcessName, theVoiceName, theLang, theText, thePriority);
					new TTSCacheRef(theVoiceName, theText).add2Cache(theResult);
				} catch (final Exception anException) {
					throw new XmlRpcInvocationException(anException.getMessage(), anException);
				}
			} else {
				TTSServerImpl.LOGGER.info("CACHE : (" + theVoiceName + " - " + theText + ")");
				theResult = cachedItem;
			}

		} catch (final ClassCastException anException) {
			TTSServerImpl.LOGGER.error(anException, anException);
			throw new XmlRpcInvocationException(anException.getMessage(), anException);
		}
		return theResult;
	}

	private Map<String, Object> generateTTS(String inProcessName, String inVoicename, String inLang, String inText, int inPriority) throws Exception {
		final long startTime = System.currentTimeMillis();
		TTSVoice theVoice = this.mConfig.getVoice(inVoicename);

		if ((inText == null) || inText.trim().equals(net.violet.common.StringShop.EMPTY_STRING)) {
			throw new IllegalArgumentException("Cannot process null or empty text through TTS");
		}

		if (theVoice == null) {
			theVoice = this.mConfig.getDefaultVoice(inLang);
		}
		if (theVoice == null) {
			throw new IllegalStateException("Cannot get any voice (bad configuration?)");
		}
		final TTSEngine theEngine = this.mEngines.get(theVoice.getEngine());
		if (theEngine == null) {
			throw new NullPointerException("Voice has no associated engine");
		}
		final String theCleanedText = cleanText(inText);

		final long completionTime;
		final Map<String, Object> theResult;
		final Map<String, Object> cachedItem = TTSServerImpl.CACHE_INSTANCE.get(new TTSCacheRef(inVoicename, theCleanedText));

		if (cachedItem == null) {
			final TTSJob theTTSJob = new TTSJob(inProcessName, theVoice, theCleanedText, inPriority);
			theResult = theEngine.processJob(theTTSJob);
			new TTSCacheRef(inVoicename, theCleanedText).add2Cache(theResult);
			completionTime = System.currentTimeMillis() - startTime;
			TTSServerImpl.LOGGER.info(theTTSJob.getId() + " (" + inVoicename + " - " + theCleanedText + ") generated in : " + completionTime + "ms");
		} else {
			theResult = cachedItem;
			completionTime = 0;
			TTSServerImpl.LOGGER.info("CACHE : (" + inVoicename + " - " + theCleanedText + ") generated in : " + completionTime + "ms");
		}

		/* Each 1000 tasks, a periodical log is made */
		synchronized (TTSServerImpl.class) {
			TTSServerImpl.treatedTTS++;
			TTSServerImpl.processedTime += completionTime;
			if (TTSServerImpl.maxGenerateTime < completionTime) {
				TTSServerImpl.maxGenerateTime = completionTime;
			}
			if (TTSServerImpl.treatedTTS >= 1000) {
				final long waitingTasks = getCurrentAndAwaitingJobs().size();
				TTSServerImpl.LOGGER.info("PERIODICAL LOG : " + TTSServerImpl.treatedTTS + " tts treated in " + TTSServerImpl.processedTime + " ms (" + (TTSServerImpl.processedTime / TTSServerImpl.treatedTTS) + " ms/tts) max= " + TTSServerImpl.maxGenerateTime + " ms - current TTS : " + waitingTasks);

				TTSServerImpl.treatedTTS = 0;
				TTSServerImpl.processedTime = 0;
				TTSServerImpl.maxGenerateTime = 0;
			}

		}
		/**/

		return theResult;
	}

	private String cleanText(String inText) {
		final String theNewText = inText.replaceAll(TTSServerImpl.MIN_REPEAT_CHARACTER.pattern(), TTSServerImpl.REPLACE_BY_GROUP2);

		if (inText.length() != theNewText.length()) {
			TTSServerImpl.LOGGER.info("OLD TEXT : " + inText);
			TTSServerImpl.LOGGER.info("NEW TEXT : " + theNewText);
		}

		return theNewText;
	}

	/**
	 * Reload du fichier de conf.
	 */
	private void reloadXmlConfiguration() {
		// load du fichier
		this.mConfig = TTSConfig.loadConfig();

		// regarde s'il y a des moteurs a retiré
		boolean removeEngine = true;
		for (final Map.Entry<String, TTSEngine> engineTTS : this.mEngines.entrySet()) {
			final String engineName = engineTTS.getKey();
			final TTSEngine theTTSEngine = engineTTS.getValue();
			for (final TTSEngineConfig theNewEngineConfig : this.mConfig.getEngineConfigs()) {
				if (theNewEngineConfig.getName().equals(engineName)) {
					removeEngine = false;
					break;
				}
			}

			if (removeEngine) {
				this.mEngines.remove(engineName);
				theTTSEngine.closeEngineConfig();
			}
		}

		// regarde s'il y a de nouveaux moteurs
		for (final TTSEngineConfig theNewEngineConfig : this.mConfig.getEngineConfigs()) {
			final TTSEngine theTTSEngine = this.mEngines.get(theNewEngineConfig.getName());
			if (theTTSEngine == null) { // il y a un nouveau moteur
				final TTSEngine theNewEngine = TTSEngine.createEngine(theNewEngineConfig);
				this.mEngines.put(theNewEngineConfig.getName(), theNewEngine);
			} else { // regarde si il y a pas eu des changements dans les options du moteur
				if (!theTTSEngine.getCommand().equals(theNewEngineConfig.getCommand()) || !theTTSEngine.getHost().equals(theNewEngineConfig.getHost()) || (theTTSEngine.getMaxChannels() != theNewEngineConfig.getMaxChannels())) {
					theTTSEngine.updateEngineConfig(theNewEngineConfig);
				}
			}
		}
	}

	/**
	 * Reload des drivers.
	 */
	private void reloadDrivers() {
		for (final TTSEngine theTTSengine : this.mEngines.values()) {
			theTTSengine.updateDriverEngineConfig();
		}
	}

	private List<Object> getCurrentAndAwaitingJobs() {
		final List<Object> theList = new ArrayList<Object>();
		for (final TTSEngine engine : this.mEngines.values()) {
			final Object[] arrayC = engine.getCurrentJobs();
			if ((arrayC != null) && (arrayC.length > 0)) {
				theList.addAll(Arrays.asList(arrayC));
			}
			final Object[] arrayA = engine.getAwaitingJobs();
			if ((arrayA != null) && (arrayA.length > 0)) {
				theList.addAll(Arrays.asList(arrayA));
			}
		}
		return theList;
	}

	public Object execute(XmlRpcRequest inRequest) throws XmlRpcException {
		Object theResult = "NoSuchMethod";
		try {
			final String theMethod = inRequest.getMethodName().substring(TTSServerImpl.HANDLER_PREFIX.length());
			if (theMethod.equals(TTSServerImpl.GENERATE_TTS_METHOD)) {
				theResult = generateTTS(inRequest);
			} else if (theMethod.equals(TTSServerImpl.LIST_TTS_CURRENT_JOBS_METHOD)) {
				theResult = getCurrentAndAwaitingJobs();
			} else if (theMethod.equals(TTSServerImpl.KILL_JOB_METHOD)) {
				theResult = false;
				for (final TTSEngine engine : this.mEngines.values()) {
					if (engine.killJob((Integer) inRequest.getParameter(0))) {
						theResult = true;
						break;
					}
				}
			} else if (theMethod.equals(TTSServerImpl.CANCEL_JOB_METHOD)) {
				theResult = false;
				for (final TTSEngine engine : this.mEngines.values()) {
					if (engine.cancelJob((Integer) inRequest.getParameter(0))) {
						theResult = true;
						break;
					}
				}
			} else if (theMethod.equals(TTSServerImpl.STATS_METHOD)) {
				final TTSEngine theEngine = this.mEngines.get(inRequest.getParameter(0));
				if (theEngine == null) {
					throw new IllegalArgumentException("This engine doesn't exist : " + inRequest.getParameter(0));
				}
				theResult = theEngine.getStats();
			} else if (theMethod.equals(TTSServerImpl.SIZE_METHOD)) {
				return getCurrentAndAwaitingJobs().size();
			} else if (theMethod.equals(TTSServerImpl.LIST_VOICES_METHOD)) {
				final String theEngineName = (String) inRequest.getParameter(0);
				final TTSEngine theEngine = this.mEngines.get(theEngineName);
				if (theEngine == null) {
					throw new IllegalArgumentException("This engine doesn't exist : " + theEngineName);
				}
				theResult = listVoices(theEngineName);
			} else if (theMethod.equals(TTSServerImpl.RELOAD_XML)) {
				reloadXmlConfiguration();
				theResult = true;
			} else if (theMethod.equals(TTSServerImpl.RELOAD_DRIVERS)) {
				reloadDrivers();
				theResult = true;
			}
		} catch (final NumberFormatException e) {
			TTSServerImpl.LOGGER.fatal(e, e);
			throw new XmlRpcInvocationException(e.getMessage(), e);
		} catch (final Exception e) {
			TTSServerImpl.LOGGER.fatal(e, e);
			throw new XmlRpcInvocationException(e.getMessage(), e);
		}

		return theResult;
	}

	private List<Map<String, String>> listVoices(String inEngineName) {
		final List<Map<String, String>> theResult = new LinkedList<Map<String, String>>();
		for (final TTSVoice theVoice : this.mConfig.getVoices()) {
			if (theVoice.getEngine().equals(inEngineName)) {
				final Map<String, String> theVoiceData = new HashMap<String, String>();
				theVoiceData.put(TTSServerImpl.VOICE_NAME_KEY, theVoice.getName());
				theVoiceData.put(TTSServerImpl.VOICE_LANG_KEY, theVoice.getLanguage());
				theResult.add(theVoiceData);
			}
		}
		return theResult;
	}

	public static void main(String[] args) {
		try {
			// Lancement du serveur.
			TTSServerImpl.LOGGER.info("Starting server");
			final TTSServerImpl theTTSServer = new TTSServerImpl();
			final WebServer theWebServer = new WebServer(TTSServerImpl.SERVER_PORT);
			theWebServer.getXmlRpcServer().setHandlerMapping(new XmlRpcHandlerMapping() {

				public XmlRpcHandler getHandler(String inHandler) throws XmlRpcNoSuchHandlerException {
					final XmlRpcHandler theResult;
					if (inHandler.startsWith(TTSServerImpl.HANDLER_PREFIX)) {
						theResult = theTTSServer;
					} else {
						throw new XmlRpcNoSuchHandlerException(inHandler);
					}
					return theResult;
				}
			});
			theWebServer.start();
			TTSServerImpl.LOGGER.info("Server ready");
		} catch (final Throwable aThrowable) {
			TTSServerImpl.LOGGER.fatal(aThrowable, aThrowable);
		}
	}
}
