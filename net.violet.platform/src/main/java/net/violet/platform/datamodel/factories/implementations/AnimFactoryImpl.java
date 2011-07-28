package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.AnimImpl;
import net.violet.platform.datamodel.factories.AnimFactory;

public class AnimFactoryImpl extends RecordFactoryImpl<Anim, AnimImpl> implements AnimFactory {

	public AnimFactoryImpl() {
		super(AnimImpl.SPECIFICATION);
	}

	/**
	 * Accesseur sur toutes les animations.
	 */
	public List<Anim> findAllOrderByName() {
		return findAll(null, null, "anim_name");
	}

	public Anim findByName(String anim_name) {
		return find("anim_name = ?", Arrays.asList(new Object[] { anim_name }));
	}

}
