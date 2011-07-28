package net.violet.platform.datamodel.factories.implementations;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.SchedulingTypeImpl;
import net.violet.platform.datamodel.factories.SchedulingTypeFactory;

public class SchedulingTypeFactoryImpl extends RecordFactoryImpl<SchedulingType, SchedulingTypeImpl> implements SchedulingTypeFactory {

	SchedulingTypeFactoryImpl() {
		super(SchedulingTypeImpl.SPECIFICATION);
	}

}
