package net.violet.platform.dataobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;

public class HumeurDataFactory {

	private static final String[] FREQS_LABELS = new String[] { "srv_humeur/freq_rare", "srv_humeur/freq_normal", "srv_humeur/freq_often", "srv_humeur/freq_very_often" };

	private static final String[] FREQS_IDS = new String[] { "1", "3", "6", "10" };
	private static final Map<Lang, List<FrequenceData>> listFrequence = new HashMap<Lang, List<FrequenceData>>();

	/**
	 * Generates a list of frequenceData object from a LangImpl object
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListFrequence(Lang inLang) {
		if (!HumeurDataFactory.listFrequence.containsKey(inLang)) {
			HumeurDataFactory.listFrequence.put(inLang, FrequenceData.generateListFrequence(HumeurDataFactory.FREQS_LABELS, HumeurDataFactory.FREQS_IDS, inLang));
		}

		return HumeurDataFactory.listFrequence.get(inLang);
	}
}
