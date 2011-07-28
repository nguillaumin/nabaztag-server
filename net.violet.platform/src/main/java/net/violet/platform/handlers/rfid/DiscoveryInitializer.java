package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.Factories;

public class DiscoveryInitializer extends AbstractApplicationInitializer {

	public final static Application APPLICATION = Factories.APPLICATION.findByName("net.violet.js.discovery");

	@Override
	protected Application getApplication() {
		return DiscoveryInitializer.APPLICATION;
	}

}
