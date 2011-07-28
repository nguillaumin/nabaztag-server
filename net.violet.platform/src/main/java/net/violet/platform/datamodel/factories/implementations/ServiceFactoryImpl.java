package net.violet.platform.datamodel.factories.implementations;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.ServiceImpl;
import net.violet.platform.datamodel.factories.ServiceFactory;

public class ServiceFactoryImpl extends RecordFactoryImpl<Service, ServiceImpl> implements ServiceFactory {

	ServiceFactoryImpl() {
		super(ServiceImpl.SPECIFICATION);
	}

	public Service findByLabel(String inName) {
		return Service.serviceByNameCache.get(inName);
	}

}
