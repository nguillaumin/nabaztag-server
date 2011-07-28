package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.factories.MimeTypeFactory;
import net.violet.platform.datamodel.mock.MimeTypeMock;

public class MimeTypeFactoryMock extends RecordFactoryMock<MimeType, MimeTypeMock> implements MimeTypeFactory {

	MimeTypeFactoryMock() {
		super(MimeTypeMock.class);
	}

	@Override
	public void loadCache() {
		MimeTypeMock.BUILDER.generateValuesFromInitFile(3, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/mimeTypeInit");
	}

}
