package net.violet.platform.dataobjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;

public final class SrvCategData {

	private final long srvcateg_id;
	private final String srvcateg_name;

	private SrvCategData(ApplicationCategory inSrvCateg, Lang inLang) {
		if (inSrvCateg != null) {
			this.srvcateg_name = inSrvCateg.getName();
			this.srvcateg_id = inSrvCateg.getId();
		} else {
			this.srvcateg_name = net.violet.common.StringShop.EMPTY_STRING;
			this.srvcateg_id = 0;
		}
	}

	/**
	 * Returns all available SrvCategData for the given LangImpl
	 * 
	 * @param inLang
	 * @return
	 * @throws SQLException
	 */
	public static List<SrvCategData> findAllDistinctByLang(Lang inLang) {
		return SrvCategData.generateList(ApplicationCategoryData.findAll(), inLang);
	}

	/**
	 * Returns SrvCategData for the given id
	 * 
	 * @param inId
	 * @param inLang
	 * @return
	 * @throws SQLException
	 */
	public static SrvCategData find(int inId, Lang inLang) {
		final ApplicationCategory theCateg = Factories.APPLICATION_CATEGORY.find(inId);
		return new SrvCategData(theCateg, inLang);
	}

	/**
	 * Returns all available SrvCategData
	 * 
	 * @param inLang
	 * @return
	 * @throws SQLException
	 */
	public static List<SrvCategData> findAllDistinct() {
		final List<SrvCategData> srvCategDataList = new ArrayList<SrvCategData>();

		for (final ApplicationCategoryData categ : ApplicationCategoryData.findAll()) {
			srvCategDataList.add(new SrvCategData(categ.getRecord(), null));
		}
		return srvCategDataList;
	}

	/**
	 * Generates a list of SrvCategData with the given SourceImpl list
	 * 
	 * @param inLang
	 * @return
	 */
	private static List<SrvCategData> generateList(List<ApplicationCategoryData> inSrvCategs, Lang inLang) {
		final List<SrvCategData> srvCategDataList = new ArrayList<SrvCategData>();

		for (final ApplicationCategoryData categ : inSrvCategs) {
			srvCategDataList.add(new SrvCategData(categ.getRecord(), inLang));
		}

		return srvCategDataList;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		return this.srvcateg_id;
	}

	/**
	 * @return the attribut name
	 */
	public String getName() {
		return this.srvcateg_name;
	}

	/**
	 * @return the attribute label (name)
	 */
	public String getLabel() {
		return getName();
	}
}
