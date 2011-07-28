package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;

public class Exists extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String theEmailAddress = inParam.getMainParamAsString();

		if (UserData.findByEmail(theEmailAddress) == null) {
			return false;
		}

		return true;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
