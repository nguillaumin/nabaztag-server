package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.NabcastCateg;

public interface NabcastCategFactory extends RecordFactory<NabcastCateg> {

	List<NabcastCateg> getNabcastCateg(long lang);

	NabcastCateg findByName(String name);

	List<NabcastCateg> findAllByLang(Lang inLang);
}
