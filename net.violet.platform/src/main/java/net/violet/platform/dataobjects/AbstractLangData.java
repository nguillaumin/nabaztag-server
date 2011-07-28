package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

public abstract class AbstractLangData extends RecordData<Lang> {

	private final Pattern isoCodePattern;

	/**
	 * Construct a new AbstractLangData object
	 * 
	 * @param inLang LangImpl object
	 */
	public AbstractLangData(Lang inLang) {
		super(inLang);

		final String[] theCodes = inLang.getIsoCode().split("-");

		if (theCodes.length == 2) {
			this.isoCodePattern = Pattern.compile(theCodes[0] + "(?:-" + theCodes[1] + ")?", Pattern.CASE_INSENSITIVE);
		} else {
			this.isoCodePattern = Pattern.compile(theCodes[0], Pattern.CASE_INSENSITIVE);
		}
	}

	/**
	 * @return the attibute lang_id
	 */
	public long getLang_id() {
		final Lang theLang = getRecord();
		if (theLang != null) {
			return theLang.getId();
		}
		return 0;
	}

	/**
	 * @return the attibute id
	 */
	public long getId() {
		return getLang_id();
	}

	/**
	 * @return the attibute lang_title
	 */
	public String getLang_title() {
		final Lang theLang = getRecord();
		if (theLang != null) {
			return theLang.getTitle();
		}
		return StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attibute lang_iso_code
	 */
	public String getLang_iso_code() {
		final Lang theLang = getRecord();
		if (theLang != null) {
			return theLang.getIsoCode();
		}
		return StringShop.EMPTY_STRING;
	}

	public long getLang_type() {
		final Lang theLang = getRecord();
		if (theLang != null) {
			return theLang.getType();
		}
		return -1;
	}

	public Lang getReference() {
		return getRecord();
	}

	/**
	 * Vérifie que le code passé en paramètre est un code connu.
	 * 
	 * @param inLanguageISOCode code de langue.
	 * @throws InvalidParameterException si le code n'est pas connu, i.e. qu'on
	 *             n'a aucune langue avec ce code.
	 */
	public static boolean isLanguageCodeValid(String inLanguageISOCode) {
		final Lang theLang = Factories.LANG.findByIsoCode(inLanguageISOCode);
		if (theLang == null) {
			return false;
		}
		return true;
	}

	/**
	 * Récupère les langues disponibles selon l'isocode passé ex: fr =>
	 * fr-FR,fr-BE ex :fr-FR => fr-FR
	 * 
	 * @param inIsocodeLanguage
	 * @return
	 */
	protected static <T extends AbstractLangData> List<T> getAllByLanguageCode(String inLanguageISOCode, Collection<T> inLangData) {
		final ArrayList<T> theList = new ArrayList<T>();

		for (final T theLang : inLangData) {
			if (theLang.isoCodePattern.matcher(inLanguageISOCode).matches()) {
				theList.add(theLang);
			}
		}
		return theList;
	}

}
