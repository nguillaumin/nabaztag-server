package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.ServiceMock;

public class ServiceFactoryMock extends RecordFactoryMock<Service, ServiceMock> implements ServiceFactory {

	ServiceFactoryMock() {
		super(ServiceMock.class);
	}

	@Override
	public void loadCache() {
		FilesMock.initSignatureFiles();
		ServiceMock.BUILDER.generateValuesFromInitFile(7, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/servicesInit");
	}

	public Service findByLabel(String inName) {
		for (final Service aService : findAll()) {
			if (aService.getLabel().equals(inName)) {
				return aService;
			}
		}
		return null;
	}

}
