package net.violet.platform.dataobjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.ClockCategories;
import net.violet.platform.datamodel.ClockCategoriesImpl;

public final class ClockCategoriesData extends RecordData<ClockCategories> {

	private static final List<ClockCategoriesData> clockCategories = new ArrayList<ClockCategoriesData>();

	private ClockCategoriesData(ClockCategories inClockCategory) {
		super(inClockCategory);
	}

	/**
	 * Finds all the root categories returns them as CategoriesData for the
	 * service "clock"
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<ClockCategoriesData> findAllRoot() throws SQLException {
		if (ClockCategoriesData.clockCategories.size() == 0) {
			ClockCategoriesData.clockCategories.addAll(ClockCategoriesData.generateList(ClockCategoriesImpl.findAllRoot()));
		}

		return ClockCategoriesData.clockCategories;
	}

	/**
	 * Generates a list of ClockCategoriesData with the given
	 * ClockCategoriesImpl list
	 * 
	 * @param ClockCategoriesImpl
	 * @return
	 */
	private static List<ClockCategoriesData> generateList(List<ClockCategories> inClockCategories) {
		final List<ClockCategoriesData> clockCategoriesDataList = new ArrayList<ClockCategoriesData>();

		for (final ClockCategories aClockCategory : inClockCategories) {
			clockCategoriesDataList.add(new ClockCategoriesData(aClockCategory));
		}

		return clockCategoriesDataList;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		final ClockCategories theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}
		return 0;
	}

	/**
	 * @return the attribute dico_key
	 */
	public String getDico_key() {
		final ClockCategories theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getDico_key() != null)) {
			return theRecord.getDico_key();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

}
