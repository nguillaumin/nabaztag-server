package net.violet.platform.dataobjects;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import org.apache.nutch.analysis.lang.LanguageIdentifier;
import org.apache.nutch.util.NutchConfiguration;

/**
 * Les langues du site.
 */
public class TtsLangData extends AbstractLangData {

	private static final Map<Lang, TtsLangData> ALL_TTS_LANGUAGE = TtsLangData.createCache(Factories.LANG.getAllTTSLanguages());
	private static final Map<String, TtsLangData> ALL_TTS_LANGUAGE_BY_ISOCODE = TtsLangData.createIsoCodeCache(TtsLangData.ALL_TTS_LANGUAGE);
	private static final String DEFAULT_TTS_LANGUAGE_ISOCODE = "en-US";
	private static final TtsLangData DEFAULT_TTS_LANGUAGE = TtsLangData.ALL_TTS_LANGUAGE.get(Factories.LANG.findByIsoCode(TtsLangData.DEFAULT_TTS_LANGUAGE_ISOCODE));

	/**
	 * Construct a new LangData object
	 * 
	 * @param inLang LangImpl object
	 */
	private TtsLangData(Lang inLang) {
		super(inLang);
	}

	/**
	 * Generates a list of LangData with the given LangImpl list
	 * 
	 * @param inLangs LangImpl list
	 * @return
	 */
	private static Map<Lang, TtsLangData> createCache(List<Lang> inLangs) {
		final Map<Lang, TtsLangData> langDataList = new HashMap<Lang, TtsLangData>();

		for (final Lang tempLang : inLangs) {
			langDataList.put(tempLang, new TtsLangData(tempLang));
		}

		return Collections.unmodifiableMap(langDataList);
	}

	private static Map<String, TtsLangData> createIsoCodeCache(Map<Lang, TtsLangData> inCache) {
		final Map<String, TtsLangData> theResult = new HashMap<String, TtsLangData>();
		for (final TtsLangData theLangData : inCache.values()) {
			theResult.put(theLangData.getLang_iso_code(), theLangData);
		}
		return Collections.unmodifiableMap(theResult);
	}

	/**
	 * Generates a list of TtsLangData with the given Lang list
	 * 
	 * @param inLangs Lang list
	 * @return
	 */
	private static List<TtsLangData> generateListFromLangs(List<Lang> inLangs) {
		final List<TtsLangData> langDataList = new LinkedList<TtsLangData>();

		for (final Lang tempLang : inLangs) {
			langDataList.add(TtsLangData.ALL_TTS_LANGUAGE.get(tempLang));
		}

		return Collections.unmodifiableList(langDataList);
	}

	public static Collection<TtsLangData> getAllTtsLanguages() {
		return TtsLangData.ALL_TTS_LANGUAGE.values();
	}

	/**
	 * Pour un code ISO donné, retourne la langue TTS par défaut. Si le code est
	 * court, retourne une langue TTS par défaut qui correspond (e.g. fr ->
	 * fr-FR). Si le code est long, retourne la langue TTS correspondante. Si
	 * aucune langue n'existe, retourne la langue par défaut.
	 * 
	 * @param inISOCode code ISO.
	 * @return la langue TTS associée par défaut.
	 */
	public static TtsLangData getDefaultTtsLanguage(String inISOCode) {
		final List<TtsLangData> theList = TtsLangData.getAllTtsLanguageByCode(inISOCode);
		final TtsLangData theResult;
		if (theList.isEmpty()) {
			theResult = TtsLangData.DEFAULT_TTS_LANGUAGE;
		} else {
			theResult = theList.get(0);
		}
		return theResult;
	}

	/**
	 * Pour un code ISO donné, retourne toutes les langues TTS qui
	 * correspondent. Si le code est court, retourne toutes les langues TTS
	 * (e.g. fr -> fr-FR). Si le code est long, retourne la langue TTS
	 * correspondante. Si aucune langue n'existe, retourne une liste vide.
	 * 
	 * @param inISOCode code ISO.
	 * @return la langue TTS associée par défaut.
	 */
	public static List<TtsLangData> getAllTtsLanguageByCode(String inISOCode) {
		return AbstractLangData.getAllByLanguageCode(inISOCode, TtsLangData.ALL_TTS_LANGUAGE.values());
	}

	/**
	 * Pour une langue du site, retourne la langue TTS par défaut.
	 * 
	 * @param inLang une langue du site.
	 * @return la langue TTS associée par défaut.
	 */
	public static TtsLangData getDefaultTtsLanguage(SiteLangData inLang) {
		return TtsLangData.getDefaultTtsLanguage(inLang.getLang_iso_code());
	}

	public static TtsLangData getIdentifyLanguage(String inText) {
		final String theIdentifiedLang = TtsLangData.identifyLanguage(inText);
		return TtsLangData.getDefaultTtsLanguage(theIdentifiedLang);
	}

	public static TtsLangData getIdentifyLanguage(String inText, SiteLangData inLang) {
		final String theTargetLangData = TtsLangData.identifyLanguage(inText);
		final List<TtsLangData> theList = TtsLangData.getAllTtsLanguageByCode(theTargetLangData);
		final TtsLangData theResult;
		if (theList.isEmpty()) {
			theResult = TtsLangData.getDefaultTtsLanguage(inLang);
		} else {
			theResult = theList.get(0);
		}
		return theResult;
	}

	private static final LanguageIdentifier IDENTIFIER = new LanguageIdentifier(NutchConfiguration.create());

	//Patch temporaire, on a aucun endroit sur la bdd pour savoir 
	//quelle est la langue par défaut à partir d'un isocode court.
	private static final Map<String, String> ISOCODE = new HashMap<String, String>();
	static {
		TtsLangData.ISOCODE.put("fr", "fr-FR");
		TtsLangData.ISOCODE.put("en", "en-US");
	}

	/**
	 * Identifie du texte et retourne la langue de ce texte.
	 * 
	 * @param inString chaîne dont on veut la langue.
	 * @return code IETF (court) de la langue pour cette chaîne.
	 */
	static String identifyLanguage(String inString) {
		final String theResult;
		synchronized (TtsLangData.IDENTIFIER) {
			final String theNutchGuessedLanguage = TtsLangData.IDENTIFIER.identify(inString);
			final String defaultIsocode = TtsLangData.ISOCODE.get(theNutchGuessedLanguage);
			if (defaultIsocode != null) {
				theResult = defaultIsocode;
			} else {
				theResult = theNutchGuessedLanguage;
			}

		}
		return theResult;
	}


	/**
	 * Finds all the languages for the given user
	 * 
	 * @return a list filled with LangData
	 */
	public static Collection<TtsLangData> findAll(User inUser) {
		if (inUser != null) {
			return TtsLangData.generateListFromLangs(inUser.getLangs());
		}
		return TtsLangData.getAllTtsLanguages();
	}

	/**
	 * Récupère une langue Tts (à partir d'un code ISO) et lève une exception si
	 * la langue n'existe pas.
	 * 
	 * @param thelanguageCode
	 * @return
	 * @throws InvalidParameterException Si le code fourni ne correspond à
	 *             aucune langue de tts.
	 */
	public static TtsLangData findByISOCode(String inLangCode) {
		return TtsLangData.ALL_TTS_LANGUAGE_BY_ISOCODE.get(inLangCode);
	}
}
