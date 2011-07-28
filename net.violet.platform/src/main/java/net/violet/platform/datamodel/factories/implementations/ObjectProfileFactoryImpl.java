package net.violet.platform.datamodel.factories.implementations;

import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.ObjectProfileImpl;
import net.violet.platform.datamodel.factories.ObjectProfileFactory;

public class ObjectProfileFactoryImpl extends RecordFactoryImpl<ObjectProfile, ObjectProfileImpl> implements ObjectProfileFactory {

	public ObjectProfileFactoryImpl() {
		super(ObjectProfileImpl.SPECIFICATION);
	}

	public boolean usesFiles(Files inFile) {
		return count(null, " object_profile_picture = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

}
