package net.violet.platform.api.actions.press;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPressException;
import net.violet.platform.api.maps.press.PressInformationMap;
import net.violet.platform.dataobjects.PressData;

public class Get extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPressException {
		final String pressAPIId = inParam.getMainParamAsString();

		final APICaller theCaller = inParam.getCaller();

		final PressData thePressData = PressData.findByAPIId(pressAPIId, theCaller.getAPIKey());
		if (thePressData == null) {
			throw new NoSuchPressException();
		}
		return new PressInformationMap(theCaller, thePressData);
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

}
