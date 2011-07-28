package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.persons.PersonInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;

public class FindByEmailAddress extends AbstractUserAction {

	/**
	 * Find an object by its name
	 * 
	 * @throws InvalidParameterException
	 * @throws NoSuchPersonException
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException {

		final String theEmailAddress = inParam.getMainParamAsString();
		final UserData theUserData = UserData.findByEmail(theEmailAddress);

		if (theUserData == null) {
			throw new NoSuchPersonException(APIErrorMessage.NO_SUCH_PERSON);
		}

		return new PersonInformationMap(inParam.getCaller(), theUserData, true);
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
