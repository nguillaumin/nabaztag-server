package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.BilanDataFactory;
import net.violet.platform.dataobjects.BourseDataFactory;
import net.violet.platform.dataobjects.DateDataFactory;
import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.dataobjects.HumeurDataFactory;
import net.violet.platform.dataobjects.PeriodWebRadioDataFactory;
import net.violet.platform.dataobjects.SourceData;
import net.violet.platform.dataobjects.TaichiDataFactory;

import org.junit.Assert;
import org.junit.Test;

public class FrequenceDataTest extends DBTest {

	@Test
	public void testFrequenceDataHumeur() {
		final List<FrequenceData> theData = HumeurDataFactory.generateListFrequence(Factories.LANG.find(1));
		Assert.assertEquals("1", theData.get(0).getId());
		Assert.assertEquals("NOLOC [srv_humeur/freq_rare]", theData.get(0).getLabel());
		Assert.assertEquals("3", theData.get(1).getId());
		Assert.assertEquals("NOLOC [srv_humeur/freq_normal]", theData.get(1).getLabel());
		Assert.assertEquals("6", theData.get(2).getId());
		Assert.assertEquals("NOLOC [srv_humeur/freq_often]", theData.get(2).getLabel());
		Assert.assertEquals("10", theData.get(3).getId());
		Assert.assertEquals("NOLOC [srv_humeur/freq_very_often]", theData.get(3).getLabel());
	}

	@Test
	public void testFrequenceDataAir() {
		final List<FrequenceData> theData = SourceData.findAllCitiesForSrvAirInLang(Factories.LANG.find(1));
		Assert.assertEquals("----FRANCE----", theData.get(0).getId());
		Assert.assertEquals("NOLOC [city_air/france]", theData.get(0).getLabel());
		Assert.assertEquals("air.paris.today", theData.get(1).getId());
		Assert.assertEquals("NOLOC [city_air/paris]", theData.get(1).getLabel());
		Assert.assertEquals("----ANGLETERRE----", theData.get(2).getId());
		Assert.assertEquals("NOLOC [city_air/england]", theData.get(2).getLabel());
		Assert.assertEquals("air.london.today", theData.get(3).getId());
		Assert.assertEquals("NOLOC [city_air/london]", theData.get(3).getLabel());
	}

	@Test
	public void testFrequenceDataBilan() {
		final List<FrequenceData> theData = BilanDataFactory.generateListdays(Factories.LANG.find(1));
		Assert.assertEquals("0", theData.get(0).getId());
		Assert.assertEquals("NOLOC [main/monday]", theData.get(0).getLabel());
		Assert.assertEquals("1", theData.get(1).getId());
		Assert.assertEquals("NOLOC [main/tuesday]", theData.get(1).getLabel());
		Assert.assertEquals("2", theData.get(2).getId());
		Assert.assertEquals("NOLOC [main/wednesday]", theData.get(2).getLabel());
		Assert.assertEquals("3", theData.get(3).getId());
		Assert.assertEquals("NOLOC [main/thursday]", theData.get(3).getLabel());
	}

	@Test
	public void testFrequenceDataTaichi() {
		final List<FrequenceData> theData = TaichiDataFactory.generateListFrequence(Factories.LANG.find(1));
		Assert.assertEquals("taichi.slow", theData.get(0).getId());
		Assert.assertEquals("NOLOC [srv_taichi/freq_rare]", theData.get(0).getLabel());
		Assert.assertEquals("taichi.normal", theData.get(1).getId());
		Assert.assertEquals("NOLOC [srv_taichi/freq_normal]", theData.get(1).getLabel());
		Assert.assertEquals("taichi.fast", theData.get(2).getId());
		Assert.assertEquals("NOLOC [srv_taichi/freq_often]", theData.get(2).getLabel());
	}

	@Test
	public void testFrequenceDataRadio() {
		final List<FrequenceData> theData = PeriodWebRadioDataFactory.generateListFrequence(Factories.LANG.find(1));
		Assert.assertEquals("900000", theData.get(0).getId());
		Assert.assertEquals("NOLOC [mysrv/15_min]", theData.get(0).getLabel());
		Assert.assertEquals("1800000", theData.get(1).getId());
		Assert.assertEquals("NOLOC [mysrv/30_min]", theData.get(1).getLabel());
		Assert.assertEquals("2700000", theData.get(2).getId());
		Assert.assertEquals("NOLOC [mysrv/45_min]", theData.get(2).getLabel());
		Assert.assertEquals("3600000", theData.get(3).getId());
		Assert.assertEquals("NOLOC [mysrv/hour]", theData.get(3).getLabel());
	}

	@Test
	public void testFrequenceDataBourse() {
		final List<FrequenceData> theData = BourseDataFactory.generateListFrequence(Factories.LANG.find(1));
		Assert.assertEquals("----USA----", theData.get(0).getId());
		Assert.assertEquals("NOLOC [indices/USA]", theData.get(0).getLabel());
		Assert.assertEquals("money.S&P/TSX", theData.get(1).getId());
		Assert.assertEquals("S&P/TSX", theData.get(1).getLabel());
		Assert.assertEquals("money.DOW JONES INDU.", theData.get(2).getId());
		Assert.assertEquals("DOW JONES INDU.", theData.get(2).getLabel());
		Assert.assertEquals("money.NASDAQ COMPOSITE", theData.get(3).getId());
		Assert.assertEquals("NASDAQ COMPOSITE", theData.get(3).getLabel());
	}

	@Test
	public void testFrequenceDataDays() {
		final List<FrequenceData> theData = DateDataFactory.generateListDays(Factories.LANG.find(1));
		Assert.assertEquals("00", theData.get(0).getId());
		Assert.assertEquals("NOLOC [profile/date_day]", theData.get(0).getLabel());
		Assert.assertEquals("00", theData.get(1).getId());
		Assert.assertEquals("01", theData.get(1).getLabel());
		Assert.assertEquals("01", theData.get(2).getId());
		Assert.assertEquals("02", theData.get(2).getLabel());
		Assert.assertEquals("02", theData.get(3).getId());
		Assert.assertEquals("03", theData.get(3).getLabel());
	}

}
