package net.violet.platform.datamodel.factories.implementations;

import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.HardwareImpl;
import net.violet.platform.datamodel.factories.HardwareFactory;

public class HardwareFactoryImpl extends RecordFactoryImpl<Hardware, HardwareImpl> implements HardwareFactory {

	public boolean usesFiles(Files inFile) {
		return count(null, " picture_file_id = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

	public HardwareFactoryImpl() {
		super(HardwareImpl.SPECIFICATION);
	}
}
