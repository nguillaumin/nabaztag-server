package net.violet.platform.api.actions.objects;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.VObjectData;

public class Delete extends AbstractObjectAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws NoSuchObjectException, InvalidParameterException, ForbiddenException, InvalidSessionException {

		final VObjectData theObject = getRequestedObject(inParam);

		if ((inParam.getCaller().getApplicationClass() == ApplicationClass.NATIVE) || checkOwnerObject(theObject, inParam)) {
			theObject.delete();
		}

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public boolean isCacheable() {
		return false;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.DELETE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
