package net.violet.platform.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.factories.Factories;

/**
 * Voices for TTS
 * 
 *
 */
public interface TtsVoice extends Record<TtsVoice> {

	/**
	 * Liste de toutes les voix (conservée en cache), triée par langue.
	 */
	List<TtsVoice> ALL_VOICES = TtsVoiceCommon.findAll();

	/**
	 * Liste des voix, avec une clé sur le champs ttsvoice_str.
	 */
	Map<String, TtsVoice> ALL_VOICES_BY_STR = TtsVoiceCommon.buildVoicesByStrMap(TtsVoice.ALL_VOICES);

	/**
	 * Liste des voix, avec une clé sur le champs ttsvoice_command.
	 */
	Map<String, TtsVoice> ALL_VOICES_BY_COMMAND = TtsVoiceCommon.buildVoicesByCommandMap(TtsVoice.ALL_VOICES);

	/**
	 * Les voix par défaut.
	 */
	Map<Long, TtsVoice> DEFAULT_VOICES = TtsVoiceCommon.buildDefaultMap(TtsVoice.ALL_VOICES);

	/**
	 * Flag pour dire que la voix est la voix par défaut
	 */
	int FLAG_DEFAULT = 0x1;

	long getTtsvoice_id();

	String getTtsvoice_libelle();

	long getTtsvoice_lang();

	Lang getLang();

	String getTtsvoice_command();

	String getTtsvoice_str();

	boolean isDefault();

	class TtsVoiceCommon {

		public static List<TtsVoice> findAll() {
			return Factories.TTSVOICE.findAll();
		}

		/**
		 * Enregistre les voix par défaut.
		 * 
		 * @return une map avec les voix par défaut.
		 */
		protected static Map<Long, TtsVoice> buildDefaultMap(List<TtsVoice> inVoices) {
			final Map<Long, TtsVoice> theResult = new HashMap<Long, TtsVoice>();
			for (final TtsVoice theVoice : inVoices) {
				final long theLang = theVoice.getTtsvoice_lang();
				// On met de toute façon une voix.
				if (!theResult.containsKey(theLang) || theVoice.isDefault()) {
					theResult.put(theLang, theVoice);
				}
			}
			return theResult;
		}

		/**
		 * Crée la relation str -> voix
		 * 
		 * @return une map avec les voix, par str.
		 */
		protected static Map<String, TtsVoice> buildVoicesByStrMap(List<TtsVoice> inVoices) {
			final Map<String, TtsVoice> theResult = new HashMap<String, TtsVoice>();
			for (final TtsVoice theVoice : inVoices) {
				theResult.put(theVoice.getTtsvoice_str(), theVoice);
			}
			return theResult;
		}

		/**
		 * Crée la relation commande -> voix
		 * 
		 * @return une map avec les voix, par commande.
		 */
		protected static Map<String, TtsVoice> buildVoicesByCommandMap(List<TtsVoice> inVoices) {
			final Map<String, TtsVoice> theResult = new HashMap<String, TtsVoice>();
			for (final TtsVoice theVoice : inVoices) {
				theResult.put(theVoice.getTtsvoice_command(), theVoice);
			}
			return theResult;
		}

		public static TtsVoice findByName(String inName) {
			return TtsVoice.ALL_VOICES_BY_STR.get(inName);
		}

		public static TtsVoice findByCommand(String inCommand) {
			return TtsVoice.ALL_VOICES_BY_COMMAND.get(inCommand);
		}

		public static TtsVoice getDefaultVoice(long inLang) {
			TtsVoice theVoice = TtsVoice.DEFAULT_VOICES.get(inLang);
			if (theVoice == null) {
				theVoice = TtsVoice.DEFAULT_VOICES.get(Lang.LANG_US_ID);
			}
			return theVoice;
		}

		public static List<TtsVoice> getAllByLang(Lang inLang) {
			final List<TtsVoice> theResultList = new ArrayList<TtsVoice>();
			for (final TtsVoice theTtsvoice : TtsVoice.ALL_VOICES) {
				if (theTtsvoice.getLang().equals(inLang)) {
					theResultList.add(theTtsvoice);
				}
			}
			return theResultList;
		}

	}
}
