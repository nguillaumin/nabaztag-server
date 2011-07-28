package net.violet.platform.datamodel.factories.implementations;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MimeTypeImpl;
import net.violet.platform.datamodel.factories.MimeTypeFactory;

public class MimeTypeFactoryImpl extends RecordFactoryImpl<MimeType, MimeTypeImpl> implements MimeTypeFactory {

	public MimeTypeFactoryImpl() {
		super(MimeTypeImpl.SPECIFICATION);
	}

}
