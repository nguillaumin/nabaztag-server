package net.violet.platform.dataobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;

public class BilanDataFactory {

	private static final String[] DAYS_LABELS = new String[] { "main/monday", "main/tuesday", "main/wednesday", "main/thursday", "main/friday", "main/saturday", "main/sunday" };
	private static final Map<Lang, List<FrequenceData>> listDays = new HashMap<Lang, List<FrequenceData>>();

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListdays(Lang inLang) {
		if (!BilanDataFactory.listDays.containsKey(inLang)) {
			BilanDataFactory.listDays.put(inLang, FrequenceData.generateListFrequenceBilan(BilanDataFactory.DAYS_LABELS, inLang));
		}

		return BilanDataFactory.listDays.get(inLang);
	}
}
