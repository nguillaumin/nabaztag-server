package net.violet.platform.applets.interactive;

import java.util.List;

import net.violet.platform.applets.Applet;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.interactif.Status;
import net.violet.platform.message.Message;

/**
 * An InteractiveApplet is specially designed to execute an interactive
 * application.
 */
public interface InteractiveApplet extends Applet {

	/**
	 * The method to call to process the interactive mode.
	 * 
	 * @param inConfig
	 * @param inObject
	 * @param inPassiveObject
	 * @param cookie
	 * @param pos
	 * @param idx
	 * @param interrupt
	 * @return
	 */
	List<Message> processItMode(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx, Status interrupt);

}
