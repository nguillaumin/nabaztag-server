package net.violet.platform.dataobjects;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;

/**
 * Les langues de la reco.
 */
public final class AsrLangData extends AbstractLangData {

	private static final Map<Lang, AsrLangData> ALL_ASR_LANGUAGE = AsrLangData.createCache(Factories.LANG.getAllASRLanguages());
	private static final String DEFAULT_ASR_LANGUAGE_ISOCODE = "en-US";
	private static final AsrLangData DEFAULT_ASR_LANGUAGE = AsrLangData.ALL_ASR_LANGUAGE.get(Factories.LANG.findByIsoCode(AsrLangData.DEFAULT_ASR_LANGUAGE_ISOCODE));

	/**
	 * Construct a new LangData object
	 * 
	 * @param inLang LangImpl object
	 */
	private AsrLangData(Lang inLang) {
		super(inLang);
	}

	/**
	 * Generates a list of LangData with the given LangImpl list
	 * 
	 * @param inLangs LangImpl list
	 * @return
	 */
	private static Map<Lang, AsrLangData> createCache(List<Lang> inLangs) {
		final Map<Lang, AsrLangData> langDataMap = new HashMap<Lang, AsrLangData>();

		for (final Lang tempLang : inLangs) {
			langDataMap.put(tempLang, new AsrLangData(tempLang));
		}

		return Collections.unmodifiableMap(langDataMap);
	}

	public static Collection<AsrLangData> getAllASRLanguages() {
		return AsrLangData.ALL_ASR_LANGUAGE.values();
	}

	/**
	 * Pour une langue du site, retourne la langue TTS par défaut.
	 * 
	 * @param inLang une langue du site.
	 * @return la langue TTS associée par défaut.
	 */
	public static AsrLangData getDefaultAsrLanguage(ObjectLangData inLang) {
		AsrLangData theResult = AsrLangData.ALL_ASR_LANGUAGE.get(inLang.getReference());
		if (theResult == null) {
			theResult = AsrLangData.DEFAULT_ASR_LANGUAGE;
		}
		return theResult;
	}
}
