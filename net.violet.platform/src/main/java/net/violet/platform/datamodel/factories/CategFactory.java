package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Categ;

public interface CategFactory extends RecordFactory<Categ> {

	List<Categ> findAll();

	Categ createNewCateg(long id);

	Categ findByName(String theCategName);

}
