package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.factories.ApplicationCategoryFactory;
import net.violet.platform.datamodel.mock.ApplicationCategoryMock;

public class ApplicationCategoryFactoryMock extends RecordFactoryMock<ApplicationCategory, ApplicationCategoryMock> implements ApplicationCategoryFactory {

	public ApplicationCategoryFactoryMock() {
		super(ApplicationCategoryMock.class);
	}

	@Override
	public void loadCache() {
		ApplicationCategoryMock.BUILDER.generateValuesFromInitFile(2, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/applicationCategInit");
	}

	public List<ApplicationCategory> getAllCategories() {
		return new ArrayList<ApplicationCategory>(findAllMapped().values());
	}

	public ApplicationCategory findByName(String inName) {
		for (final ApplicationCategory categ : findAll()) {
			if (categ.getName().equals(inName)) {
				return categ;
			}
		}

		return null;
	}

	/**
	 * @see net.violet.platform.datamodel.factories.ApplicationCategoryFactory#findByShortName(java.lang.String)
	 */
	public ApplicationCategory findByShortName(String inShortName) {
		// recreate the full entry for the DicoTools.dico
		return findByName(ApplicationCategoryFactory.CATG_KEY_PRFX + inShortName + ApplicationCategoryFactory.CATG_KEY_SFX);
	}

}
