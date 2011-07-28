package net.violet.platform.dataobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;

//TODO: quand les Web Radio seront dans le nouveau modèle passer cela dans PeriodServiceDataFactory
public class PeriodWebRadioDataFactory {

	private static final String[] IDS = new String[] { "900000", "1800000", "2700000", "3600000", "7200000" };
	private static final String[] LABELS = new String[] { "mysrv/15_min", "mysrv/30_min", "mysrv/45_min", "mysrv/hour", "mysrv/2_hours" };
	private static final Map<Lang, List<FrequenceData>> listFrequence = new HashMap<Lang, List<FrequenceData>>();

	/**
	 * Generates a list of frequenceData object from a String array V1
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListFrequence(Lang inLang) {
		if (!PeriodWebRadioDataFactory.listFrequence.containsKey(inLang)) {
			PeriodWebRadioDataFactory.listFrequence.put(inLang, FrequenceData.generateListFrequence(PeriodWebRadioDataFactory.LABELS, PeriodWebRadioDataFactory.IDS, inLang));
		}

		return PeriodWebRadioDataFactory.listFrequence.get(inLang);
	}
}
