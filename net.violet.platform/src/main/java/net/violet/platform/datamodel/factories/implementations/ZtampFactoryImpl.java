package net.violet.platform.datamodel.factories.implementations;

import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Ztamp;
import net.violet.platform.datamodel.ZtampImpl;
import net.violet.platform.datamodel.factories.ZtampFactory;

public class ZtampFactoryImpl extends RecordFactoryImpl<Ztamp, ZtampImpl> implements ZtampFactory {

	ZtampFactoryImpl() {
		super(ZtampImpl.SPECIFICATION);
	}

	public Ztamp findBySerial(String serial) {
		return findByKey(1, serial);
	}

	public boolean usesFiles(Files inFile) {
		return count(null, " visual = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}
}
