package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.CCalendar;

public class DateDataFactory {

	private static final String[] MONTH_LABELS = CCalendar.MONTH_LABELS;
	private static final String[] YEAR_LABEL = new String[] { "profile/date_year" };
	private static final String[] DAY_LABEL = new String[] { "profile/date_day" };

	private static final List<FrequenceData> DAYS = DateDataFactory.createLabelsDays();
	private static final List<FrequenceData> YEARS = DateDataFactory.createLabelsYears();
	private static final Map<Lang, List<FrequenceData>> monthList = new HashMap<Lang, List<FrequenceData>>();
	private static final Map<Lang, List<FrequenceData>> yearList = new HashMap<Lang, List<FrequenceData>>();
	private static final Map<Lang, List<FrequenceData>> dayList = new HashMap<Lang, List<FrequenceData>>();

	private static List<FrequenceData> createLabelsDays() {
		final String[] days = new String[31];

		for (int i = 1; i <= 31; i++) {
			if (i < 10) {
				days[i - 1] = "0" + String.valueOf(i);
			} else {
				days[i - 1] = String.valueOf(i);
			}
		}

		return FrequenceData.generateListFrequence(days, null, false);
	}

	private static List<FrequenceData> createLabelsYears() {
		final String[] years = new String[100];
		final CCalendar myCalendar = new CCalendar(false);
		final int year = myCalendar.getYear();

		for (int i = 0; i < 100; i++) {
			years[i] = String.valueOf(year - i);
		}

		return FrequenceData.generateListFrequence(years, null, false);
	}

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListDays(Lang inLang) {
		if (!DateDataFactory.dayList.containsKey(inLang)) {
			final List<FrequenceData> localDay = new ArrayList<FrequenceData>();
			localDay.addAll(DateDataFactory.DAYS);
			localDay.add(0, FrequenceData.generateListFrequence(DateDataFactory.DAY_LABEL, inLang, true).get(0));
			DateDataFactory.dayList.put(inLang, localDay);
		}
		return DateDataFactory.dayList.get(inLang);
	}

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListMonths(Lang inLang) {
		if (!DateDataFactory.monthList.containsKey(inLang)) {
			DateDataFactory.monthList.put(inLang, FrequenceData.generateListFrequence(DateDataFactory.MONTH_LABELS, inLang, true));
		}

		return DateDataFactory.monthList.get(inLang);
	}

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListYears(Lang inLang) {
		if (!DateDataFactory.yearList.containsKey(inLang)) {
			final List<FrequenceData> localYears = new ArrayList<FrequenceData>();
			localYears.addAll(DateDataFactory.YEARS);
			localYears.add(0, FrequenceData.generateListFrequence(DateDataFactory.YEAR_LABEL, inLang, true).get(0));
			DateDataFactory.yearList.put(inLang, localYears);
		}

		return DateDataFactory.yearList.get(inLang);
	}
}
