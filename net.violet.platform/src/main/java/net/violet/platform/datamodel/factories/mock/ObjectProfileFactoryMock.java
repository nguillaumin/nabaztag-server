package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.factories.ObjectProfileFactory;
import net.violet.platform.datamodel.mock.ObjectProfileMock;

public class ObjectProfileFactoryMock extends RecordFactoryMock<ObjectProfile, ObjectProfileMock> implements ObjectProfileFactory {

	ObjectProfileFactoryMock() {
		super(ObjectProfileMock.class);
	}

	public boolean usesFiles(Files inFile) {
		for (final ObjectProfile p : findAll()) {
			if (p.getPicture().equals(inFile)) {
				return true;
			}
		}
		return false;
	}

}
