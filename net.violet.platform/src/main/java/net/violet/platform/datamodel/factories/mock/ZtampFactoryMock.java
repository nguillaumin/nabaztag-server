package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Ztamp;
import net.violet.platform.datamodel.factories.ZtampFactory;
import net.violet.platform.datamodel.mock.ZtampMock;

public class ZtampFactoryMock extends RecordFactoryMock<Ztamp, ZtampMock> implements ZtampFactory {

	ZtampFactoryMock() {
		super(ZtampMock.class);
	}

	public Ztamp findBySerial(String serial) {
		for (final Ztamp theZtamp : findAll()) {
			if (theZtamp.getSerial().equals(serial)) {
				return theZtamp;
			}
		}
		return null;
	}

	public boolean usesFiles(Files inFile) {
		return false;
	}
}
