package net.violet.platform.api.actions.objects;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.objects.ObjectInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.VObjectData;

public class FindByName extends AbstractObjectAction {

	/**
	 * Find an object by its name
	 * 
	 * @throws InvalidParameterException
	 * @throws NoSuchObjectException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ForbiddenException, InvalidSessionException {

		final VObjectData theObject = VObjectData.findByName(inParam.getMainParamAsString());

		if (theObject == null) {
			throw new NoSuchObjectException();
		}

		final boolean isVisible = checkOwnerObject(theObject, inParam);

		// this method does not return an invisible object unless it belongs to the session user.
		if (theObject.getPreferences().isVisible() || isVisible) {
			return new ObjectInformationMap(inParam.getCaller(), theObject, isVisible);
		}

		return null;
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
		return Application.CLASSES_ALL;
	}
}
