package net.violet.platform.api.actions.admin.object;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.admin.AdminAccountInformationMap;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.VObjectData;

public class SearchObject extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException {

		final String objectId = inParam.getString("id", false);

		final String serial = inParam.getString(SetSerial.SERIAL, false);

		if ((serial == null) && (objectId == null)) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_PARAMETER, "missing sn or id params");
		}

		VObjectData theObjectData = null;

		if (serial != null) {
			theObjectData = VObjectData.findBySerial(serial);
		} else if (objectId != null) {
			theObjectData = VObjectData.findByAPIId(objectId, inParam.getCallerAPIKey());
		}

		if ((theObjectData == null) || !theObjectData.isValid()) {
			throw new NoSuchObjectException();
		}

		return new AdminAccountInformationMap(inParam.getCaller(), theObjectData.getOwner(), Arrays.asList(theObjectData));
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Collections.emptyList();
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}
}
