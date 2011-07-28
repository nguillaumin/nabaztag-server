package net.violet.platform.applets.api;

import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.VObjectData;

/**
 * @author christophe - Violet
 */
public interface ApplicationEvent {

	/**
	 * @return the application in which context the event was triggered
	 */
	ApplicationData getApplication();

	/**
	 * @return the object associated to the application subscription
	 */
	VObjectData getObject();

	/**
	 * @return the object
	 */
	VObjectData getReader();

}
