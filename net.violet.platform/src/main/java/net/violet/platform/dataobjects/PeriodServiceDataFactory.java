package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.factories.ServiceFactory;

public class PeriodServiceDataFactory {

	/**
	 * Stores a set of constants used to display some Services
	 */
	private static final class ConstantesStorage {

		/** Holds a mapped list of pairs<ID, LABEL> */
		static final Map<Service, List<String[]>> myConstantes = new HashMap<Service, List<String[]>>();

		private ConstantesStorage() {
			List<String[]> theList = new ArrayList<String[]>();
			// Constantes des podcasts
			theList.add(new String[] { "12:00:00", "02:00:00", "01:00:00" });
			theList.add(new String[] { "js/twice_day", "js/delay_7200000", "js/delay_3600000" });

			ConstantesStorage.myConstantes.put(ServiceFactory.SERVICE.PODCAST.getService(), theList);

			theList = new ArrayList<String[]>();
			// Constantes des RSS
			theList.add(new String[] { "02:00:00", "01:00:00", "00:20:00" });
			theList.add(new String[] { "mysrv/every_2_hours", "mysrv/every_hour", "mysrv/every_20_min" });

			ConstantesStorage.myConstantes.put(ServiceFactory.SERVICE.RSS.getService(), theList);
		}

		/**
		 * Gets a string array of the LABELS for the given ServiceImpl
		 * 
		 * @param inService
		 * @return the LABELS
		 */
		private String[] getLabels(Service inService) {
			return ConstantesStorage.myConstantes.get(inService).get(1);
		}

		/**
		 * Gets a string array of the IDs for the given ServiceImpl
		 * 
		 * @param inService
		 * @return the IDs
		 */
		private String[] getIds(Service inService) {
			return ConstantesStorage.myConstantes.get(inService).get(0);
		}
	}

	private static final ConstantesStorage myConstantesFactory = new ConstantesStorage();
	private static final Map<Service, Map<Lang, List<FrequenceData>>> listFrequence = new HashMap<Service, Map<Lang, List<FrequenceData>>>();

	/**
	 * Generates a list of frequenceData object from a String array V1
	 * 
	 * @param inFreqs
	 * @return
	 */
	public static List<FrequenceData> generateListFrequence(Service inService, Lang inLang) {
		Map<Lang, List<FrequenceData>> srvFreq;

		if (!PeriodServiceDataFactory.listFrequence.containsKey(inService)) {
			srvFreq = new HashMap<Lang, List<FrequenceData>>();
		} else {
			srvFreq = PeriodServiceDataFactory.listFrequence.get(inService);
		}

		if (!srvFreq.containsKey(inLang)) {
			srvFreq.put(inLang, FrequenceData.generateListFrequence(PeriodServiceDataFactory.myConstantesFactory.getLabels(inService), PeriodServiceDataFactory.myConstantesFactory.getIds(inService), inLang));
			PeriodServiceDataFactory.listFrequence.put(inService, srvFreq);
		}
		return PeriodServiceDataFactory.listFrequence.get(inService).get(inLang);
	}
}
