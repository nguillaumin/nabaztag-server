package net.violet.platform.dataobjects;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;

/**
 * Les langues du site.
 */
public class SiteLangData extends AbstractLangData {

	private static final Map<Lang, SiteLangData> ALL_SITE_LANGUAGE = SiteLangData.createCache(Factories.LANG.getAllSiteLanguages());
	public static final String DEFAULT_SITE_LANGUAGE_ISOCODE = "en";
	public static final String JAPAN_SITE_LANGUAGE_ISOCODE = "ja";
	public static final SiteLangData DEFAULT_SITE_LANGUAGE = SiteLangData.ALL_SITE_LANGUAGE.get(Factories.LANG.findByIsoCode(SiteLangData.DEFAULT_SITE_LANGUAGE_ISOCODE));
	private static final Map<String, SiteLangData> ALL_SITE_LANGUAGE_BY_ISOCODE = SiteLangData.createIsoCodeCache(SiteLangData.ALL_SITE_LANGUAGE);

	/**
	 * Construct a new LangData object
	 * 
	 * @param inLang LangImpl object
	 */
	private SiteLangData(Lang inLang) {
		super(inLang);
	}

	/**
	 * Create the cache from a list of langs.
	 * 
	 * @param inLangs list of langs from the DB.
	 * @return
	 */
	private static Map<Lang, SiteLangData> createCache(List<Lang> inLangs) {
		final Map<Lang, SiteLangData> langDataList = new HashMap<Lang, SiteLangData>();

		for (final Lang tempLang : inLangs) {
			langDataList.put(tempLang, new SiteLangData(tempLang));
		}

		return Collections.unmodifiableMap(langDataList);
	}

	private static Map<String, SiteLangData> createIsoCodeCache(Map<Lang, SiteLangData> inCache) {
		final Map<String, SiteLangData> theResult = new HashMap<String, SiteLangData>();
		for (final SiteLangData theLangData : inCache.values()) {
			theResult.put(theLangData.getLang_iso_code(), theLangData);
		}
		return Collections.unmodifiableMap(theResult);
	}

	public static Collection<SiteLangData> getAllSiteLanguages() {
		return SiteLangData.ALL_SITE_LANGUAGE.values();
	}

	public static SiteLangData get(Lang inLang) {
		return SiteLangData.ALL_SITE_LANGUAGE.get(inLang);
	}

	/**
	 * Récupère une langue du site (à partir d'un code ISO) et lève une
	 * exception si la langue n'existe pas.
	 * 
	 * @param thelanguageCode
	 * @return
	 * @throws InvalidParameterException Si le code fourni ne correspond à
	 *             aucune langue du site.
	 */
	public static SiteLangData getByISOCode(String inLangCode) throws InvalidParameterException {

		final SiteLangData lang = SiteLangData.ALL_SITE_LANGUAGE_BY_ISOCODE.get(inLangCode);

		if (lang == null) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_LANGUAGE_CODE, inLangCode);
		}

		return lang;
	}
}
