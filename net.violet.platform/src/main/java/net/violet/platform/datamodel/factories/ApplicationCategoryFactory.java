package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.ApplicationCategory;

public interface ApplicationCategoryFactory extends RecordFactory<ApplicationCategory> {

	static final String CATG_KEY_PRFX = "LOC_srv_category_";
	static final String CATG_KEY_SFX = "/title";

	List<ApplicationCategory> getAllCategories();

	/**
	 * @param inName the key of the category name in the DicoTools.dico
	 * @return
	 */
	ApplicationCategory findByName(String inDicoKey);

	/**
	 * @param inName
	 * @return
	 */
	ApplicationCategory findByShortName(String inShortName);

}
