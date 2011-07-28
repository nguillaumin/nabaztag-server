package net.violet.platform.dataobjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;

/**
 * Les langues du site my.nabaztag.com
 */
public class MotherTongueLangData extends AbstractLangData {

	private static final List<MotherTongueLangData> ALL_MOTHER_TONGUE_LANG_DATA = MotherTongueLangData.generateListFromLangs(Factories.LANG.findAllMotherTongue());
	private static final List<MotherTongueLangData> ALL_LANG_NABCAST_DATA = MotherTongueLangData.generateListFromLangs(Factories.LANG.findAllLanguagesForNabcasts());

	/**
	 * Construct a new LangData object
	 * 
	 * @param inLang LangImpl object
	 */
	private MotherTongueLangData(Lang inLang) {
		super(inLang);
	}

	/**
	 * Generates a list of LangData with the given LangImpl list
	 * 
	 * @param inLangs LangImpl list
	 * @return
	 */
	private static List<MotherTongueLangData> generateListFromLangs(List<Lang> inLangs) {
		final List<MotherTongueLangData> langDataList = new ArrayList<MotherTongueLangData>();

		for (final Lang tempLang : inLangs) {
			langDataList.add(new MotherTongueLangData(tempLang));
		}

		return Collections.unmodifiableList(langDataList);
	}

	/**
	 * Finds all the mother tongue languages (NON TTS)
	 * 
	 * @return a list filled with LangData
	 */
	public static List<MotherTongueLangData> findAllMotherTongue() {
		return MotherTongueLangData.ALL_MOTHER_TONGUE_LANG_DATA;
	}

	/**
	 * Accesseur sur toutes les langues possibles pour les nabcasts
	 * 
	 * @throws SQLException
	 */
	public static List<MotherTongueLangData> findAllLanguagesForNabcasts() {
		return MotherTongueLangData.ALL_LANG_NABCAST_DATA;
	}

}
