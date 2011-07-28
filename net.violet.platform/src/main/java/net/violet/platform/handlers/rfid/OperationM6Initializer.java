package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.Factories;

public class OperationM6Initializer extends AbstractApplicationInitializer {

	public final static Application APPLICATION = Factories.APPLICATION.findByName("net.violet.js.operationm6");

	@Override
	protected Application getApplication() {
		return OperationM6Initializer.APPLICATION;
	}
}
