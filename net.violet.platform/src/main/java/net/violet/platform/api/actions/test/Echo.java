package net.violet.platform.api.actions.test;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;

/**
 * A simple action to test the API input and ouput formats
 * 
 * @author christophe - Violet
 */
public class Echo extends AbstractAction {

	/**
	 * Just return what we have received
	 * 
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) {
		return inParam;
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
}
