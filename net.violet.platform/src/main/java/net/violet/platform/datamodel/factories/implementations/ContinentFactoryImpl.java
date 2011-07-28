package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Continent;
import net.violet.platform.datamodel.ContinentImpl;
import net.violet.platform.datamodel.factories.ContinentFactory;

public class ContinentFactoryImpl extends RecordFactoryImpl<Continent, ContinentImpl> implements ContinentFactory {

	ContinentFactoryImpl() {
		super(ContinentImpl.SPECIFICATION);
	}

	public List<Continent> findAllContinents() {

		return findAll(null, null, "name");
	}

	public Continent findIdByName(String continentName) {

		return find("name = ?", Arrays.asList(new Object[] { continentName }));
	}

}
