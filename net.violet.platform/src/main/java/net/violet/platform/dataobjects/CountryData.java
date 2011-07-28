package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.DicoTools;

import org.apache.log4j.Logger;

public final class CountryData extends RecordData<Country> {

	private static final Logger LOGGER = Logger.getLogger(CountryData.class);

	public static CountryData getData(Country inPays) {
		try {
			return RecordData.getData(inPays, CountryData.class, Country.class);
		} catch (final InstantiationException e) {
			CountryData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			CountryData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			CountryData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			CountryData.LOGGER.fatal(e, e);
		}

		return null;
	}

	private final Lang theLang;

	protected CountryData(Country inPays, Lang inLang) {
		super(inPays);
		this.theLang = inLang;
	}

	protected CountryData(Country inPays) {
		super(inPays);
		this.theLang = Factories.LANG.findByIsoCode("en-GB");
	}

	/**
	 * Finds all the country for the given lang
	 * 
	 * @param inLang
	 * @return a list of PaysData
	 * @throws SQLException
	 */
	public static List<CountryData> findAll(Lang inLang) {
		return CountryData.generateListFromCountry(Factories.COUNTRIES.findAll(), inLang);
	}

	public static List<CountryData> findAnnuCountries(Lang inLang) {
		List<CountryData> countries = new ArrayList<CountryData>();
		try {
			countries = CountryData.generateListFromCountry(Factories.COUNTRIES.findAnnuCountries(), inLang);
		} catch (final Exception e) {
			CountryData.LOGGER.fatal(e, e);
		}
		return countries;
	}

	/**
	 * Generates a list of PaysData with the given PaysImpl list
	 * 
	 * @param inPays MusicImpl list
	 * @return
	 */
	private static List<CountryData> generateListFromCountry(List<Country> inPays, Lang inLang) {
		final List<CountryData> paysDataList = new ArrayList<CountryData>();

		for (final Country tempPays : inPays) {
			paysDataList.add(new CountryData(tempPays, inLang));
		}
		Collections.sort(paysDataList, new Comparator<CountryData>() {

			public int compare(CountryData o1, CountryData o2) {
				return o1.getPays_nom().compareTo(o2.getPays_nom());
			}
		});

		return paysDataList;
	}

	/**
	 * @return the id
	 */
	public String getPaysCode() {
		final Country record = getRecord();
		if (record != null) {
			return record.getCode();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public long getMainLangId() {
		final Country record = getRecord();
		if (record != null) {
			return record.getMainLanguage().getId();
		}

		return 0L;
	}

	/**
	 * @return the pays_nom
	 */
	public String getPays_nom() {
		final Country record = getRecord();
		if ((record != null) && (record.getName() != null)) {
			return DicoTools.dico_if(this.theLang, record.getName());
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return getPays_nom();
	}

	/**
	 * Returns the default country according to the given country code.
	 * 
	 * @param countryCode
	 * @return a PaysData, or null.
	 */
	public static CountryData getDefaultCountryByJavaId(String countryCode, Lang inLang) {
		final Country thePays = Factories.COUNTRIES.findByCode(countryCode);
		if (thePays != null) {
			return new CountryData(thePays, inLang);
		}
		return null;
	}

	/**
	 * Return the list of country codes
	 * 
	 * @return A list of country codes (list of strings)
	 */
	public static List<String> getCodes() {
		final List<String> theCodes = new ArrayList<String>();
		for (final Country aCountry : Factories.COUNTRIES.findAll()) {
			theCodes.add(aCountry.getCode());
		}

		Collections.sort(theCodes, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}

		});

		return theCodes;
	}

	public static CountryData findByCode(String countryCode) {
		final Country thePays = Factories.COUNTRIES.findByCode(countryCode);
		if (thePays != null) {
			return CountryData.getData(thePays);
		}
		return null;
	}

	public static List<CountryData> findAllByContinent(ContinentData continent) {
		final List<CountryData> result = new ArrayList<CountryData>();
		for (final Country inCountry : Factories.COUNTRIES.findCountriesByContinent(continent.getRecord())) {
			result.add(CountryData.getData(inCountry));
		}
		return result;
	}

	public ContinentData getContinent() {
		final Country record = getRecord();
		if (record != null) {
			return ContinentData.getData(record.getContinent());
		}
		return null;
	}
}
