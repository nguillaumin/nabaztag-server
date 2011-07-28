package net.violet.platform.voice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.content.converters.ContentType;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.Constantes;
import net.violet.platform.voice.server.TTSClient;
import net.violet.platform.voice.server.TTSServerImpl;

import org.apache.log4j.Logger;

public class TTSServices {

	private static final Logger LOGGER = Logger.getLogger(TTSServices.class);

	public static class TTSServicesConfig {

		private final int nbConcurrentTTS;

		public TTSServicesConfig(int inNbConcurrentTTS) {
			this.nbConcurrentTTS = inNbConcurrentTTS;
		}

		/**
		 * @return the nbConcurrentTTS
		 */
		public int getNbConcurrentTTS() {
			return this.nbConcurrentTTS;
		}
	}

	private final Semaphore ttsServicesSemaphore;

	private static TTSServicesConfig gDefaultConfig;

	/**
	 * Constructeur prenant en paramêtre un élément de configuration.
	 */
	public TTSServices(TTSServicesConfig inTTSServiceConfig) {
		final int nbTTSMax = (inTTSServiceConfig == null) ? Constantes.NB_CONCURRENT_TTS : inTTSServiceConfig.getNbConcurrentTTS();

		this.ttsServicesSemaphore = (nbTTSMax > 0) ? new Semaphore(nbTTSMax, true) : null;

	}

	private static Map<TTSServicesConfig, TTSServices> INSTANCES = new HashMap<TTSServicesConfig, TTSServices>();

	public static TTSServices getInstance(TTSServicesConfig inConfig) {

		// Map config -> instance.
		TTSServices theResult = TTSServices.INSTANCES.get(inConfig);

		if (theResult == null) {
			synchronized (TTSServices.INSTANCES) {
				theResult = TTSServices.INSTANCES.get(inConfig);
				if (theResult == null) {
					theResult = new TTSServices(inConfig);
					TTSServices.INSTANCES.put(inConfig, theResult);
				}
			}
		}
		return theResult;
	}

	public static TTSServices getDefaultInstance() {
		if (TTSServices.gDefaultConfig == null) {
			synchronized (TTSServices.class) {
				if (TTSServices.gDefaultConfig == null) {
					TTSServices.gDefaultConfig = new TTSServicesConfig(Constantes.NB_CONCURRENT_TTS);
				}
			}
		}
		return TTSServices.getInstance(TTSServices.gDefaultConfig);
	}

	private static final Pattern PCFORMAT = Pattern.compile("(\\d+)KPCM(?:\\d+)BITS");

	public Files postTTS(String text, boolean chor, boolean adp, TtsVoice inVoice) throws IllegalArgumentException {
		if (inVoice == null) {
			throw new IllegalArgumentException("No voice given");
		}

		try {

			final TTSClient theClient = new TTSClient();
			final String theVoiceName = inVoice.getTtsvoice_str();
			final Lang theVoiceLang = Factories.LANG.find(inVoice.getTtsvoice_lang());
			Map<String, Object> theByteResult = null;
			try {

				if (this.ttsServicesSemaphore != null) {
					this.ttsServicesSemaphore.acquire();
				}
				theByteResult = theClient.generateTTS(theVoiceName, theVoiceLang.getIsoCode(), text, Constantes.PRIORITY_ACCESS_TTS);

			} finally {
				if (this.ttsServicesSemaphore != null) {
					this.ttsServicesSemaphore.release();
				}
			}
			if (theByteResult != null) {
				final byte[] theAudioFile = (byte[]) theByteResult.get(TTSServerImpl.DATA);
				final Matcher theMatcher = TTSServices.PCFORMAT.matcher(theByteResult.get(TTSServerImpl.FORMAT).toString());
				if ((theAudioFile != null) && (theAudioFile.length > 0) && theMatcher.matches()) {
					final int freq = Integer.parseInt(theMatcher.group(1));

					final ContentType theType;

					if (freq == 8) {
						theType = ContentType.PCM_8;
					} else if (freq == 22) {
						theType = ContentType.PCM_22;
					} else {
						theType = null;
					}

					return FilesManagerFactory.FILE_MANAGER.post(theAudioFile, theType, ContentType.MP3_32, Files.CATEGORIES.BROAD, adp, chor, MimeType.MIME_TYPES.A_MPEG);

				}
			}
		} catch (final InterruptedException e) {
			TTSServices.LOGGER.info(e, e);
		}
		return null;
	}
}
