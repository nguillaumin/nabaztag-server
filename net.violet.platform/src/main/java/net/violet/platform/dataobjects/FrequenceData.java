package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;

public final class FrequenceData {

	private final String id;
	private final String label;

	/**
	 * Construct a new FrequenceData
	 * 
	 * @param inId
	 * @param inLabel
	 * @param inLang
	 * @param inSrvId
	 */
	public FrequenceData(String inId, String inLabel) {
		this.id = inId;
		this.label = inLabel;
	}

	private static String generateLabelBourseFree(String inLabel, Lang inLang) {
		final String theResult;
		if (inLang != null) {
			if (inLabel.contains("indices")) {
				theResult = DicoTools.dico(inLang, inLabel);
			} else {
				theResult = inLabel;
			}
		} else {
			theResult = net.violet.common.StringShop.EMPTY_STRING;
		}
		return theResult;
	}

	public static String generateLabel(String inLabel, Lang inLang, boolean inDico) {
		final String theResult;
		if (inLang != null) {
			theResult = DicoTools.dico(inLang, inLabel);
		} else if (inDico) {
			theResult = net.violet.common.StringShop.EMPTY_STRING;
		} else {
			theResult = inLabel;
		}
		return theResult;
	}

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	static List<FrequenceData> generateListFrequence(String[] inFreqs, String[] inIds, Lang inLang) {
		final List<FrequenceData> freqDataList = new ArrayList<FrequenceData>();

		if (inFreqs.length == inIds.length) {
			for (int i = 0; i < inFreqs.length; i++) {
				freqDataList.add(new FrequenceData(inIds[i], FrequenceData.generateLabel(inFreqs[i], inLang, true)));
			}
		}

		return freqDataList;
	}

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	static List<FrequenceData> generateListFrequenceBourseFree(String[] inFreqs, String[] inIds, Lang inLang) {
		final List<FrequenceData> freqDataList = new ArrayList<FrequenceData>();

		if (inFreqs.length == inIds.length) {
			for (int i = 0; i < inFreqs.length; i++) {
				freqDataList.add(new FrequenceData(inIds[i], FrequenceData.generateLabelBourseFree(inFreqs[i], inLang)));
			}
		}

		return freqDataList;
	}

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	static List<FrequenceData> generateListFrequenceBilan(String[] inFreqs, Lang inLang) {
		return FrequenceData.generateListFrequence(inFreqs, inLang, false /* inLeadingZero */, true);
	}

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	static List<FrequenceData> generateListFrequence(String[] inFreqs, Lang inLang, boolean dico) {
		return FrequenceData.generateListFrequence(inFreqs, inLang, true /* inLeadingZero */, dico);
	}

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	static List<FrequenceData> generateListFrequence(String[] inFreqs, Lang inLang, boolean inLeadingZero, boolean dico) {
		final List<FrequenceData> freqDataList = new ArrayList<FrequenceData>();

		for (int i = 0; i < inFreqs.length; i++) {
			final String theLabel = FrequenceData.generateLabel(inFreqs[i], inLang, dico);
			if ((i < 10) && inLeadingZero) {
				freqDataList.add(new FrequenceData("0" + String.valueOf(i), theLabel));
			} else {
				freqDataList.add(new FrequenceData(String.valueOf(i), theLabel));
			}
		}

		return freqDataList;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		if (this.id != null) {
			return this.id;
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		if (this.label != null) {
			return this.label;
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

}
