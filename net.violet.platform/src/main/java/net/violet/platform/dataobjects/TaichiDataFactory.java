package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;

public class TaichiDataFactory {

	public enum TAICHI_FREQUENCY {
		SLOW("taichi.slow", "srv_taichi/freq_rare"),
		NORMAL("taichi.normal", "srv_taichi/freq_normal"),
		FAST("taichi.fast", "srv_taichi/freq_often");

		private static final Map<String, TAICHI_FREQUENCY> LABEL_MAP;

		static {
			final Map<String, TAICHI_FREQUENCY> theMap = new HashMap<String, TAICHI_FREQUENCY>();
			for (final TAICHI_FREQUENCY aFrequency : TAICHI_FREQUENCY.values()) {
				theMap.put(aFrequency.getLabel(), aFrequency);
			}
			LABEL_MAP = Collections.unmodifiableMap(theMap);
		}

		public static TAICHI_FREQUENCY findByLabel(String inLabel) {
			return TAICHI_FREQUENCY.LABEL_MAP.get(inLabel);
		}

		private final String label;
		private final String dicoKey;

		private TAICHI_FREQUENCY(String inLabel, String inDicoKey) {
			this.label = inLabel;
			this.dicoKey = inDicoKey;
		}

		public String getLabel() {
			return this.label;
		}

		public String getDicoKey() {
			return this.dicoKey;
		}

	}

	private static final Map<Lang, List<FrequenceData>> listFrequence = new HashMap<Lang, List<FrequenceData>>();

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListFrequence(Lang inLang) {

		if (!TaichiDataFactory.listFrequence.containsKey(inLang)) {

			synchronized (TaichiDataFactory.listFrequence) {

				if (!TaichiDataFactory.listFrequence.containsKey(inLang)) {
					final List<FrequenceData> freqDataList = new ArrayList<FrequenceData>();

					for (final TAICHI_FREQUENCY aFrequency : TAICHI_FREQUENCY.values()) {
						freqDataList.add(new FrequenceData(aFrequency.getLabel(), FrequenceData.generateLabel(aFrequency.getDicoKey(), inLang, true)));
					}

					TaichiDataFactory.listFrequence.put(inLang, freqDataList);
				}

			}

		}

		return TaichiDataFactory.listFrequence.get(inLang);
	}
}
