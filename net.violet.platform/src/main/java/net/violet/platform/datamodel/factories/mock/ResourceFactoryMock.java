package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Resource;
import net.violet.platform.datamodel.factories.ResourceFactory;
import net.violet.platform.datamodel.mock.ResourceMock;

public class ResourceFactoryMock extends RecordFactoryMock<Resource, ResourceMock> implements ResourceFactory {

	protected ResourceFactoryMock() {
		super(ResourceMock.class);
	}

}
