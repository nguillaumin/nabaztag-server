package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Categ;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.DicoTools;

import org.apache.log4j.Logger;

public final class CategData extends RecordData<Categ> {

	private static final Logger LOGGER = Logger.getLogger(CategData.class);

	static final Map<Lang, List<CategData>> mapCategDataByLang = CategData.createCache();
	private final String categ_type;

	public CategData(Categ inCateg, Lang inLang) {
		super(inCateg);
		if (inCateg.getName() != null) {
			this.categ_type = DicoTools.dico(inLang, inCateg.getName());
		} else {
			this.categ_type = net.violet.common.StringShop.EMPTY_STRING;
		}
	}

	public CategData(Categ inCateg) {
		super(inCateg);
		this.categ_type = net.violet.common.StringShop.EMPTY_STRING;
	}

	public static CategData getData(Categ inCateg) {
		try {
			return RecordData.getData(inCateg, CategData.class, Categ.class);
		} catch (final InstantiationException e) {
			CategData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			CategData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			CategData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			CategData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Returns all available CategData for the given LangImpl to generate the
	 * cache
	 * 
	 * @param inLang
	 * @return
	 */
	private static List<CategData> generateAllInLang(Lang inLang) {
		return CategData.generateList(Factories.CATEG.findAll(), inLang);
	}

	/**
	 * Returns all available CategData for the given LangImpl from cache
	 * 
	 * @param inLang
	 * @return
	 */
	public static List<CategData> findAllInLang(Lang inLang) {
		if (CategData.mapCategDataByLang.containsKey(inLang)) {
			return CategData.mapCategDataByLang.get(inLang);
		}

		return Collections.emptyList();
	}

	private static Map<Lang, List<CategData>> createCache() {
		final Map<Lang, List<CategData>> categDataMap = new HashMap<Lang, List<CategData>>();

		for (final Lang lang : Factories.LANG.findAllMotherTongue()) {
			categDataMap.put(lang, CategData.generateAllInLang(lang));
		}

		return categDataMap;
	}

	/**
	 * Generates a list of CategData with the given SourceImpl list
	 * 
	 * @param inLang
	 * @return
	 */
	private static List<CategData> generateList(List<Categ> inCateg, Lang inLang) {
		final List<CategData> categDataList = new ArrayList<CategData>();

		for (final Categ tempCateg : inCateg) {
			categDataList.add(new CategData(tempCateg, inLang));
		}

		return categDataList;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		final Categ theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}
		return 0;
	}

	/**
	 * @return the attribut name
	 */
	public String getType() {
		return this.categ_type;
	}

	/**
	 * @return the attribute label (name)
	 */
	public String getLabel() {
		return getType();
	}

	public static CategData findByName(String theCategName) {
		final Categ theCateg = Factories.CATEG.findByName(theCategName);
		return (theCateg != null) ? CategData.getData(theCateg) : null;
	}
}
