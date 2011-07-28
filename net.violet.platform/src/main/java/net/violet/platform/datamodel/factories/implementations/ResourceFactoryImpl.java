package net.violet.platform.datamodel.factories.implementations;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Resource;
import net.violet.platform.datamodel.ResourceImpl;
import net.violet.platform.datamodel.factories.ResourceFactory;

public class ResourceFactoryImpl extends RecordFactoryImpl<Resource, ResourceImpl> implements ResourceFactory {

	protected ResourceFactoryImpl() {
		super(ResourceImpl.SPECIFICATION);
	}

}
