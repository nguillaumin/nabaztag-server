package net.violet.platform.api.actions.press;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPressException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.PressData;

public class Delete extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPressException, InternalErrorException {

		final String pressAPIId = inParam.getMainParamAsString();

		final PressData thePressData = PressData.findByAPIId(pressAPIId, inParam.getCaller().getAPIKey());
		if (thePressData == null) {
			throw new NoSuchPressException();
		}

		if (!thePressData.delete()) {
			throw new InternalErrorException(APIErrorMessage.DELETE_FAILED.getMessage());
		}
		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.DELETE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}
}
