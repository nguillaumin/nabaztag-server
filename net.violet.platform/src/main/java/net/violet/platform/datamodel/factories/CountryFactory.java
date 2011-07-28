package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Continent;
import net.violet.platform.datamodel.Country;

public interface CountryFactory extends RecordFactory<Country> {

	/**
	 * Returns a country according to the provided country code.
	 * 
	 * @param countryCode the country code
	 * @return the Pays object, null.
	 */
	Country findByCode(String code);

	/**
	 * Returns a list of all the Pays objects for the given language.
	 * 
	 * @param inLang the language
	 * @return a list of Pays, can be empty.
	 */
	List<Country> findAll();

	/**
	 * Returns a list of all the coutries which have at least one user.
	 * 
	 * @return a list of Pays
	 */

	List<Country> findAnnuCountries();

	List<Country> findCountriesByContinent(Continent continent);

}
