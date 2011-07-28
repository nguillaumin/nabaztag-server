package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Continent;

public interface ContinentFactory extends RecordFactory<Continent> {

	List<Continent> findAllContinents();

	Continent findIdByName(String continentName);

}
