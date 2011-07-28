package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.factories.HardwareFactory;
import net.violet.platform.datamodel.mock.HardwareMock;

public class HardwareFactoryMock extends RecordFactoryMock<Hardware, HardwareMock> implements HardwareFactory {

	@Override
	public void loadCache() {
		HardwareMock.BUILDER.generateValuesFromInitFile(3, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/hardwareInit");
	}

	public HardwareFactoryMock() {
		super(HardwareMock.class);
	}

	public boolean usesFiles(Files inFile) {
		return false;
	}
}
