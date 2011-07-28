package net.violet.platform.datamodel.factories.implementations;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Hint;
import net.violet.platform.datamodel.HintImpl;
import net.violet.platform.datamodel.factories.HintFactory;

public class HintFactoryImpl extends RecordFactoryImpl<Hint, HintImpl> implements HintFactory {

	public HintFactoryImpl() {
		super(HintImpl.SPECIFICATION);
	}

}
