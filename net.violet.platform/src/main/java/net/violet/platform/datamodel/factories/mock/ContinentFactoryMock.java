package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Continent;
import net.violet.platform.datamodel.factories.ContinentFactory;
import net.violet.platform.datamodel.mock.ContinentMock;

public class ContinentFactoryMock extends RecordFactoryMock<Continent, ContinentMock> implements ContinentFactory {

	ContinentFactoryMock() {
		super(ContinentMock.class);
	}

	public List<Continent> findAllContinents() {
		final List<Continent> theContinents = new LinkedList<Continent>();
		for (final Continent aContinent : findAll()) {
			theContinents.add(aContinent);
		}
		return theContinents;
	}

	public Continent findIdByName(String inContinentName) {
		for (final Continent inContinent : findAll()) {
			if (inContinentName.equals(inContinent.getName())) {
				return inContinent;
			}
		}
		return null;
	}
}
