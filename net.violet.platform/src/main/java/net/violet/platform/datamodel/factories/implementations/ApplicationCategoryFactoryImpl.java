package net.violet.platform.datamodel.factories.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationCategoryImpl;
import net.violet.platform.datamodel.factories.ApplicationCategoryFactory;

public class ApplicationCategoryFactoryImpl extends RecordFactoryImpl<ApplicationCategory, ApplicationCategoryImpl> implements ApplicationCategoryFactory {

	public ApplicationCategoryFactoryImpl() {
		super(ApplicationCategoryImpl.SPECIFICATION);
	}

	public List<ApplicationCategory> getAllCategories() {
		return new ArrayList<ApplicationCategory>(findAllMapped().values());
	}

	public ApplicationCategory findByName(String inName) {
		return find(" application_category_name = ? ", Arrays.asList((Object) inName));
	}

	/**
	 * @see net.violet.platform.datamodel.factories.ApplicationCategoryFactory#findByShortName(java.lang.String)
	 */
	public ApplicationCategory findByShortName(String inShortName) {
		// recreate the full entry for the DicoTools.dico
		return findByName(ApplicationCategoryFactory.CATG_KEY_PRFX + inShortName + ApplicationCategoryFactory.CATG_KEY_SFX);
	}

}
