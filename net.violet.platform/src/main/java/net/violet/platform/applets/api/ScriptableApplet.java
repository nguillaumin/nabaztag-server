package net.violet.platform.applets.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.platform.applets.AppletException;
import net.violet.platform.applets.tools.Expirable;

/**
 * Interface des applications JavaScript, Ruby..
 */
public interface ScriptableApplet extends Expirable {

	Date getReleaseDate();

	/**
	 * Process an interactive event transmitted by an object and return a list
	 * of pojo responses adressed to one or more objects
	 * 
	 * @param inObjectId emettor of the event
	 * @param event wrapper (pojo map)
	 * @return
	 */
	List<Object> processEvent(Map<String, Object> inEvent) throws AppletException;

}
