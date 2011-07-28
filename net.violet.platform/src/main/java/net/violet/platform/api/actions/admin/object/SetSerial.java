package net.violet.platform.api.actions.admin.object;

import java.util.Collections;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.ObjectAlreadyExistsException;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.VObjectData;

public class SetSerial extends AbstractAction {

	public static String SERIAL = "sn";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ObjectAlreadyExistsException {

		final String objectId = inParam.getMainParamAsString();
		final String macAddress = inParam.getString(SetSerial.SERIAL, true);

		final VObjectData theObjectData = VObjectData.findByAPIId(objectId, inParam.getCallerAPIKey());
		if ((theObjectData == null) || !theObjectData.isValid()) {
			throw new NoSuchObjectException();
		}

		theObjectData.setSerial(macAddress);

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Collections.emptyList();
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}
}
