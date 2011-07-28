package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.Factories;

/**
 *
 * @author christophe - Violet
 */
public class OperationDELLInitializer extends AbstractApplicationInitializer {

	public final static Application APPLICATION = Factories.APPLICATION.findByName("net.violet.js.operationdell");

	/**
	 * @see net.violet.platform.handlers.rfid.AbstractApplicationInitializer#getApplication()
	 */
	@Override
	protected Application getApplication() {
		return OperationDELLInitializer.APPLICATION;
	}

}
