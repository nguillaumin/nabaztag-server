package net.violet.platform.datamodel.mock;

import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Continent;

public class ContinentMock extends AbstractMockRecord<Continent, ContinentMock> implements Continent {

	private final String continent_name;

	protected ContinentMock(long inId) {
		super(inId);
		this.continent_name = "Vide";
	}

	public ContinentMock(long inId, String nomContinent) {
		super(inId);
		this.continent_name = nomContinent;
	}

	public List<Continent> getAllContinents() {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return this.continent_name;
	}
}
