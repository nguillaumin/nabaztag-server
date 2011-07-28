package net.violet.platform.api.actions.secrets;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.APICaller.CallerClass;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.util.SecretTimestamp;

/**
 *
 * @author christophe - Violet
 */
public class CreateTimestamp extends AbstractAction {

	/**
	 * Retrieve a new secret timestamp
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) {
		return SecretTimestamp.create();
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

	/**
	 * Returns the list of caller classes authorized to call this action.
	 * 
	 * @return
	 */
	@Override
	public List<CallerClass> getAuthorizedCallerClasses() {
		return APICaller.APPLICATIONS;
	}

	/**
	 * Returns the list of application classes authorized to call this action.
	 * Default is to allow all application classes.
	 * 
	 * @return
	 */
	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}

}
