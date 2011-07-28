package net.violet.platform.handlers.rfid;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.factories.Factories;


/**
 *
 * @author christophe - Violet
 */
public class ColoriGOMixteInitializer extends AbstractApplicationInitializer {

	public final static Application APPLICATION = Factories.APPLICATION.findByName("net.violet.js.colorigomixte");

	@Override
	protected Application getApplication() {
		return ColoriGOMixteInitializer.APPLICATION;
	}

}
