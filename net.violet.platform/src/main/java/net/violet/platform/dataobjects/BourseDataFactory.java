package net.violet.platform.dataobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;

public class BourseDataFactory {

	private static final String[] LABELS = new String[] { "indices/USA", "S&P/TSX", "DOW JONES INDU.", "NASDAQ COMPOSITE", "NASDAQ 100", "SP 500", "indices/Europe", "DAX Xetra", "BEL 20", "Ibex35", "CAC 40", "Mibtel", "AEX 25", "SMI", "FTSE 100", "indices/Asia", "Nikkei 225" };
	private static final String[] IDS = new String[] { "----USA----", "money.S&P/TSX", "money.DOW JONES INDU.", "money.NASDAQ COMPOSITE", "money.NASDAQ 100", "money.SP 500", "----Europe----", "money.DAX XETRA", "money.BEL 20", "money.IBEX35", "money.CAC 40", "money.MIB 30", "money.AEX 25", "money.SMI", "money.FTSE 100", "----ASIE et OCEANIE----", "money.NIKKEI 225", };

	private static final Map<Lang, List<FrequenceData>> listFrequence = new HashMap<Lang, List<FrequenceData>>();

	/**
	 * Generates a list of frequenceData object from a String array
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListFrequence(Lang inLang) {
		if (!BourseDataFactory.listFrequence.containsKey(inLang)) {
			BourseDataFactory.listFrequence.put(inLang, FrequenceData.generateListFrequenceBourseFree(BourseDataFactory.LABELS, BourseDataFactory.IDS, inLang));
		}

		return BourseDataFactory.listFrequence.get(inLang);
	}
}
