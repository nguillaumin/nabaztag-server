package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Continent;
import net.violet.platform.datamodel.Country;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;

public class CountryMock extends AbstractMockRecord<Country, CountryMock> implements Country {

	private final String code;
	private final Long continent;
	private final String name;
	private final long langId;

	public CountryMock(long id, String inCode, String inName, long inContinent) {
		super(id);
		this.code = inCode;
		this.name = inName;
		this.continent = inContinent;
		this.langId = Lang.LANG_US_ID;
	}

	public CountryMock(long id, String inCode, String inName) {
		this(id, inCode, inName, 1L);

	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public Continent getContinent() {
		return Factories.CONTINENT.find(this.continent);
	}

	public Lang getMainLanguage() {
		return Factories.LANG.find(this.langId);
	}
}
