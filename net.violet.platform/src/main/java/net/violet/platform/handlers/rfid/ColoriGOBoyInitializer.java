package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.Factories;


/**
 *
 * @author christophe - Violet
 */
public class ColoriGOBoyInitializer extends AbstractApplicationInitializer {

	public final static Application APPLICATION = Factories.APPLICATION.findByName("net.violet.js.colorigoboy");

	@Override
	protected Application getApplication() {
		return ColoriGOBoyInitializer.APPLICATION;
	}

}
