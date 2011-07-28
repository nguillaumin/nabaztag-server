package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.DicoTools;

public final class SourceData extends RecordData<Source> {

	private static final String[] SRV_AIR_IDS = new String[] { "----FRANCE----", "air.paris.today", "----ANGLETERRE----", "air.london.today", "----ITALIE----", "air.rome.today", "----BELGIQUE----", "air.belgique.Bruxelles.today", "air.belgique.Anvers.today", "air.belgique.Gand.today", "air.belgique.Liege.today", "air.belgique.Charleroi.today", "----US----", "air.us.Atlanta.today", "air.us.Boston.today", "air.us.Chicago.today", "air.us.Cleveland.today", "air.us.Dallas.today", "air.us.Denver.today", "air.us.Detroit.today", "air.us.LosAngeles.today", "air.us.LosAngelesMetroandInlandOrange.today", "air.us.Miami.today", "air.us.Minneapolis.today", "air.us.New-York.today", "air.us.Philadelphia.today", "air.us.Phoenix.today", "air.us.Richmond.today", "air.us.SaintLouis.today",
			"air.us.SanDiegoCoast.today", "air.us.SanDiegoFoothills.today", "air.us.SanDiegoMesaandInlandValley.today", "air.us.SanFrancisco.today", "air.us.Seattle-Bellevue-KentValley.today", "air.us.StLouis.today", "air.us.Tampa.today", "air.us.Washington.today" };

	private static final String[] SRV_AIR_LABELS = new String[] { "city_air/france", "city_air/paris", "city_air/england", "city_air/london", "city_air/italy", "city_air/rome", "city_air/belgium", "city_air/bruxelles", "city_air/anvers", "city_air/gand", "city_air/liege", "city_air/charleroi", "city_air/united-states", "city_air/atlanta", "city_air/boston", "city_air/chicago", "city_air/cleveland", "city_air/dallas", "city_air/denver", "city_air/detroit", "city_air/la-inland", "city_air/la-metro", "city_air/miami", "city_air/minneapolis", "city_air/newyork", "city_air/philadelphia", "city_air/phoenix", "city_air/richmond", "city_air/saint-louis", "city_air/san-diego-coast", "city_air/san-diego-foothills", "city_air/san-diego-mesa", "city_air/san-francisco", "city_air/seattle",
			"city_air/saint-louis-metro-east", "city_air/tampa", "city_air/washington" };

	private final String label;
	private static final Map<Lang, List<FrequenceData>> listFrequence = new HashMap<Lang, List<FrequenceData>>();

	private SourceData(Source inSource, Lang inLang) {
		super(inSource);
		if ((inLang != null) && (inSource != null)) {
			this.label = DicoTools.dico_if(inLang, inSource.getSource_dico());
		} else {
			this.label = null;
		}
	}

	public static List<SourceData> findAllByLang(Lang inLang) {
		final List<Source> listSources = Factories.SOURCE.findByFilteredPath("Nmeteo%weather");
		final List<SourceData> listSourceData = new ArrayList<SourceData>();

		for (final Source tempSource : listSources) {
			listSourceData.add(new SourceData(tempSource, inLang));
		}

		Collections.sort(listSourceData, new Comparator<SourceData>() {

			public int compare(SourceData o1, SourceData o2) {
				return o1.getLabel().compareTo(o2.getLabel());
			}
		});

		return listSourceData;
	}

	public static List<FrequenceData> findAllCitiesForSrvAirInLang(Lang inLang) {
		if (SourceData.listFrequence.get(inLang) == null) {
			SourceData.listFrequence.put(inLang, FrequenceData.generateListFrequence(SourceData.SRV_AIR_LABELS, SourceData.SRV_AIR_IDS, inLang));
		}

		return SourceData.listFrequence.get(inLang);
	}

	/**
	 * @return the label of the SourceImpl : the attribute source_dico with the
	 *         right language
	 */
	public String getLabel() {
		if (this.label != null) {
			return this.label;
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute source_path
	 */
	public String getSource_path() {
		final Source theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getSource_path() != null)) {
			return theRecord.getSource_path();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute source_path
	 */
	public long getId() {
		final Source theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}
		return 0;
	}
}
