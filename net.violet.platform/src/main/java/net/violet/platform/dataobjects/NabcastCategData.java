package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.NabcastCateg;
import net.violet.platform.datamodel.NabcastCategImpl;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.factories.Factories;

public class NabcastCategData extends RecordData<NabcastCateg> {

	/**
	 * Construct a new NabcastData object from the NabcastCategImpl
	 * 
	 * @param inNabcastCateg
	 */
	public NabcastCategData(NabcastCateg inNabcastCateg) {
		super(inNabcastCateg);
	}

	/**
	 * Finds all NabcastCategImpl from its id
	 * 
	 * @param inId
	 * @return a NabcastData
	 */
	public static List<NabcastCategData> findAllNabcastCategByLang(Lang inLang, boolean force) {
		List<NabcastCategData> result = new ArrayList<NabcastCategData>();
		if (force) {
			result = NabcastCategData.generateList(Factories.NABCAST_CATEG.findAllByLang(inLang));
		} else {
			result = NabcastCategData.generateList(Factories.NABCAST_CATEG.getNabcastCateg(inLang.getId()));
		}
		return result;
	}

	/**
	 * Finds a NabcastCategImpl from its id
	 * 
	 * @param inId
	 * @return a NabcastData
	 */
	public static NabcastCategData findNabcastCateg(int inId) {
		return new NabcastCategData(NabcastCategImpl.find(inId));
	}

	/**
	 * Finds a NabcastCategImpl from its id
	 * 
	 * @param inId
	 * @param force Permet de récupèrer toutes les catégories sans filtrer si il
	 *            y a des nabcasts dans la catégorie
	 * @return a NabcastData
	 */
	public static List<NabcastCategData> findAllNabcastCategByLangs(List<Lang> inLang, boolean force) {
		final List<NabcastCategData> nabcastCategDataList = new ArrayList<NabcastCategData>();

		for (final Lang lang : inLang) {
			nabcastCategDataList.addAll(NabcastCategData.findAllNabcastCategByLang(lang, force));
		}

		return nabcastCategDataList;
	}

	/**
	 * Generates a list of NabcastData with the given NabcastCategImpl list
	 * 
	 * @param inNabcastCategs MusicImpl list
	 * @param inUser the user
	 * @return
	 */
	private static List<NabcastCategData> generateList(List<NabcastCateg> inNabcastCategs) {
		final List<NabcastCategData> nabcastCategDataList = new ArrayList<NabcastCategData>();

		for (final NabcastCateg tempNabcastCateg : inNabcastCategs) {
			nabcastCategDataList.add(new NabcastCategData(tempNabcastCateg));
		}

		return nabcastCategDataList;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		final NabcastCateg theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcastCateg_id
	 */
	public long getIdCategorie() {
		return getId();
	}

	/**
	 * @return the attribute nabcastcateg_id
	 */
	public long getNabcastcateg_id() {
		return getId();
	}

	/**
	 * @return the attribute nabcastCateg_title
	 */
	public String getNameCategorie() {
		final NabcastCateg theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcastcateg_val() != null)) {
			return theRecord.getNabcastcateg_val();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nabcastCateg_desc(ription)
	 */
	public String getDescCategorie() {
		final NabcastCateg theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcastcateg_desc() != null)) {
			return theRecord.getNabcastcateg_desc();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nabcastCateg_desc(ription)
	 */
	public String getNabcastcateg_desc() {
		return getDescCategorie();
	}

	/**
	 * @return the attribute nabcastCateg_pricate
	 */
	public int getNbr_nabcast() {
		final NabcastCateg theRecord = getRecord();
		if (theRecord != null) {
			return NabcastImpl.findByCateg(theRecord.getId(), theRecord.getNabcastcateg_lang()).size();
		}

		return 0;
	}

	/**
	 * return the attribute nabcastCateg_lang
	 */
	public long getLangCategorie() {
		final NabcastCateg theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcastcateg_lang();
		}
		return 2;
	}

	/**
	 * return the attribute nabcastCateg_lang
	 */
	public long getNabcastcateg_lang() {
		return getLangCategorie();
	}

	public String getNabcastcateg_val() {
		return getNameCategorie();
	}
}
