package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.Factories;

/**
 * Une promo géniale
 */
public class SIXnCoInitializer extends AbstractApplicationInitializer {

	public final static Application APPLICATION = Factories.APPLICATION.findByName("net.violet.js.operationsixnco");

	@Override
	protected Application getApplication() {
		return SIXnCoInitializer.APPLICATION;
	}

}
