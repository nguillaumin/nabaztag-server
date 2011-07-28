package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Continent;
import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.factories.CountryFactory;
import net.violet.platform.datamodel.mock.CountryMock;

public class CountryFactoryMock extends RecordFactoryMock<Country, CountryMock> implements CountryFactory {

	CountryFactoryMock() {
		super(CountryMock.class);
	}

	public Country findByCode(String countryCode) {
		for (final Country p : findAll()) {
			if (p.getCode().equals(countryCode)) {
				return p;
			}
		}
		return null;
	}

	public List<Country> findAnnuCountries() {
		throw new UnsupportedOperationException();
	}

	public List<Country> findCountriesByContinent(Continent continent) {
		final List<Country> result = new ArrayList<Country>();
		for (final Country inPays : findAll()) {

			if (inPays.getContinent().equals(continent)) {
				result.add(inPays);
			}
		}
		return result;
	}
}
