package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.Factories;

/**
 *
 * @author christophe - Violet
 */
public class OperationMobileadInitializer extends AbstractApplicationInitializer {

	public final static Application MOBILEAD = Factories.APPLICATION.findByName("net.violet.js.operationmobilead");

	/**
	 * @see net.violet.platform.handlers.rfid.AbstractApplicationInitializer#getApplication()
	 */
	@Override
	protected Application getApplication() {
		return OperationMobileadInitializer.MOBILEAD;
	}

}
