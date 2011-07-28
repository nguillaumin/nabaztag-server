package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Continent;
import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.CountryImpl;
import net.violet.platform.datamodel.factories.CountryFactory;

public class CountryFactoryImpl extends RecordFactoryImpl<Country, CountryImpl> implements CountryFactory {

	CountryFactoryImpl() {
		super(CountryImpl.SPECIFICATION);
	}

	public Country findByCode(String code) {
		return findByKey(1, code);
	}

	@Override
	public Country find(long id) {
		return findByKey(0, id);
	}

	public List<Country> findAll() {
		return findAll(null, null, "pays_nom ASC");
	}

	public List<Country> findAnnuCountries() {
		final String[] inJoinTables = new String[] { "user", "annu" };
		final String condition = " annu_user = user_id and annu_confirm = ? and user_main > ? and annu_country = pays_code  ";
		return findAllDistinct(inJoinTables, condition, Arrays.asList(new Object[] { 1, 0 }), "pays_nom");
	}

	public List<Country> findCountriesByContinent(Continent continent) {
		return findAll("pays_continent = ?", Arrays.asList(new Object[] { continent.getId() }), "pays_nom");
	}
}
