package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.factories.SchedulingTypeFactory;
import net.violet.platform.datamodel.mock.SchedulingTypeMock;

public class SchedulingTypeFactoryMock extends RecordFactoryMock<SchedulingType, SchedulingTypeMock> implements SchedulingTypeFactory {

	SchedulingTypeFactoryMock() {
		super(SchedulingTypeMock.class);
	}

	@Override
	public void loadCache() {
		SchedulingTypeMock.BUILDER.generateValuesFromInitFile(3, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/schedulingTypeInit");
	}

}
