package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class ApplicationCategoryData extends APIData<ApplicationCategory> {

	private static final Logger LOGGER = Logger.getLogger(ApplicationCategoryData.class);

	public static ApplicationCategoryData getData(ApplicationCategory inCategory) {
		try {
			return RecordData.getData(inCategory, ApplicationCategoryData.class, ApplicationCategory.class);
		} catch (final InstantiationException e) {
			ApplicationCategoryData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ApplicationCategoryData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			ApplicationCategoryData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			ApplicationCategoryData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected ApplicationCategoryData(ApplicationCategory inRecord) {
		super(inRecord);
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.APPLICATION_CATEGORIE;
	}

	public static List<ApplicationCategoryData> findAll() {
		return ApplicationCategoryData.generateList(Factories.APPLICATION_CATEGORY.getAllCategories());
	}

	private static List<ApplicationCategoryData> generateList(List<ApplicationCategory> categoriesList) {
		if ((categoriesList != null) && !categoriesList.isEmpty()) {
			final List<ApplicationCategoryData> categDataList = new ArrayList<ApplicationCategoryData>();
			for (final ApplicationCategory aCateg : categoriesList) {
				categDataList.add(new ApplicationCategoryData(aCateg));
			}

			return categDataList;
		}
		return Collections.emptyList();
	}

	public long getId() {
		final ApplicationCategory record = getRecord();
		if (record != null) {
			return record.getId();
		}
		return 0;
	}

	public String getName() {
		final ApplicationCategory record = getRecord();
		if (record != null) {
			return record.getName();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static ApplicationCategoryData findByName(String inName) {
		final ApplicationCategory theCateg = Factories.APPLICATION_CATEGORY.findByName(inName);
		if (theCateg == null) {
			return null;
		}

		return new ApplicationCategoryData(theCateg);
	}

}
