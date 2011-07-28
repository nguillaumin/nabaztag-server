package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Anim;

public interface AnimFactory extends RecordFactory<Anim> {

	List<Anim> findAllOrderByName();

	Anim findByName(String anim_name);

}
