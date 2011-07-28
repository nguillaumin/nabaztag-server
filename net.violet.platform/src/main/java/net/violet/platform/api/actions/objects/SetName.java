package net.violet.platform.api.actions.objects;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NameAlreadyExistsException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.VObjectData;

public class SetName extends AbstractObjectAction {

	/**
	 * Changes the name of the requested object. The new name has to be passed
	 * as "name" parameter value.
	 * 
	 * @throws NoSuchObjectException
	 * @throws InvalidParameterException
	 * @throws NoSuchObjectException if the requested object could not be found.
	 * @throws InvalidParameterException if the name parameter is missing or is
	 *             invalid.
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws NameAlreadyExistsException
	 * @throws NameAlreadyExists if the provided name is already used by an
	 *             other object.
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ForbiddenException, InvalidSessionException, NameAlreadyExistsException {

		final VObjectData theObject = getRequestedObject(inParam);

		// Check Session
		doesSessionBelongToVObject(theObject, inParam);

		final String theName = inParam.getString("name", true).trim();

		if (!VObjectData.isNameValid(theName)) {
			throw new InvalidParameterException(APIErrorMessage.INVALID_NAME, theName);
		}

		if (VObjectData.findByName(theName) != null) {
			throw new NameAlreadyExistsException(APIErrorMessage.NAME_ALREADY_EXISTS);
		}

		theObject.setName(theName);

		return null;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0; // expires immediatly
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
