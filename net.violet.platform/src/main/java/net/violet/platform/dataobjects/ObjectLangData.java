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
 * Les langues de l'objet.
 */
public class ObjectLangData extends AbstractLangData {

	private static final Map<Lang, ObjectLangData> ALL_OBJECT_LANGUAGE = ObjectLangData.createCache(Factories.LANG.getAllObjectLanguages());
	private static final Map<String, ObjectLangData> ALL_OBJECT_LANGUAGE_BY_ISOCODE = ObjectLangData.createIsoCodeCache(ObjectLangData.ALL_OBJECT_LANGUAGE);
	private static final String DEFAULT_OBJECT_LANGUAGE_ISOCODE = "en-US";
	private static final String DEFAULT_BIBLIO_LANGUAGE_ISOCODE = "fr-FR";
	public static final String JAPANESE_OBJECT_LANGUAGE_ISOCODE = "ja-JP";
	public static final ObjectLangData DEFAULT_OBJECT_LANGUAGE = ObjectLangData.ALL_OBJECT_LANGUAGE.get(Factories.LANG.findByIsoCode(ObjectLangData.DEFAULT_OBJECT_LANGUAGE_ISOCODE));
	public static final ObjectLangData DEFAULT_BIBLIO_LANGUAGE = ObjectLangData.ALL_OBJECT_LANGUAGE.get(Factories.LANG.findByIsoCode(ObjectLangData.DEFAULT_BIBLIO_LANGUAGE_ISOCODE));

	/**
	 * Construct a new LangData object
	 * 
	 * @param inLang LangImpl object
	 */
	private ObjectLangData(Lang inLang) {
		super(inLang);
	}

	private static Map<Lang, ObjectLangData> createCache(List<Lang> inLangs) {
		final Map<Lang, ObjectLangData> langDataList = new HashMap<Lang, ObjectLangData>();

		for (final Lang tempLang : inLangs) {
			langDataList.put(tempLang, new ObjectLangData(tempLang));
		}

		return Collections.unmodifiableMap(langDataList);
	}

	private static Map<String, ObjectLangData> createIsoCodeCache(Map<Lang, ObjectLangData> inCache) {
		final Map<String, ObjectLangData> theResult = new HashMap<String, ObjectLangData>();
		for (final ObjectLangData theLangData : inCache.values()) {
			theResult.put(theLangData.getLang_iso_code(), theLangData);
		}
		return Collections.unmodifiableMap(theResult);
	}

	public static Collection<ObjectLangData> getAllObjectLanguages() {
		return ObjectLangData.ALL_OBJECT_LANGUAGE.values();
	}

	public static ObjectLangData get(Lang inLang) {
		return ObjectLangData.ALL_OBJECT_LANGUAGE.get(inLang);
	}

	/**
	 * Récupère une langue d'objet (à partir d'un code ISO) et lève une
	 * exception si la langue n'existe pas.
	 * 
	 * @param thelanguageCode
	 * @return
	 * @throws InvalidParameterException Si le code fourni ne correspond à
	 *             aucune langue du site.
	 */
	public static ObjectLangData getByISOCode(String inLangCode) throws InvalidParameterException {
		final ObjectLangData theResult = ObjectLangData.ALL_OBJECT_LANGUAGE_BY_ISOCODE.get(inLangCode);
		if (theResult == null) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_LANGUAGE_CODE, net.violet.common.StringShop.EMPTY_STRING);
		}

		return theResult;
	}

	/**
	 * Pour un code ISO donné, retourne la langue de l'objet par défaut. Si le
	 * code est court, retourne une langue objet par défaut qui correspond (e.g.
	 * fr -> fr-FR). Si le code est long, retourne la langue objet
	 * correspondante. Si aucune langue n'existe, retourne la langue objet par
	 * défaut.
	 * 
	 * @param inISOCode code ISO.
	 * @return la langue de l'objet associée par défaut.
	 */
	public static ObjectLangData getDefaultObjectLanguage(String inISOCode) {
		final List<ObjectLangData> theList = ObjectLangData.getAllObjectLanguageByCode(inISOCode);
		final ObjectLangData theResult;
		if (theList.isEmpty()) {
			theResult = ObjectLangData.DEFAULT_OBJECT_LANGUAGE;
		} else {
			theResult = theList.get(0);
		}
		return theResult;
	}

	/**
	 * Pour un code ISO donné, retourne toutes les langues de l'objet qui
	 * correspondent. Si le code est court, retourne toutes les langues objets
	 * (e.g. fr -> fr-FR). Si le code est long, retourne la langue objet
	 * correspondante. Si aucune langue n'existe, retourne une liste vide.
	 * 
	 * @param inISOCode code ISO.
	 * @return la langue objet associée par défaut.
	 */
	public static List<ObjectLangData> getAllObjectLanguageByCode(String inISOCode) {
		return AbstractLangData.getAllByLanguageCode(inISOCode, ObjectLangData.ALL_OBJECT_LANGUAGE.values());
	}
}
