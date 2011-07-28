package net.violet.platform.api.actions.people;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.persons.PersonInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.dataobjects.UserData;

public class Search extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final PojoMap queryParameters = inParam.getPojoMap("query", true);
		final List<PersonInformationMap> lstPersonInformations = new ArrayList<PersonInformationMap>();

		if (queryParameters.isEmpty()) {
			throw new InvalidParameterException(APIErrorMessage.EMPTY_QUERY, net.violet.common.StringShop.EMPTY_STRING);
		}

		final String firstName = queryParameters.getString("first_name", false);
		final String lastName = queryParameters.getString("last_name", false);
		final String genderCode = queryParameters.getString("gender", false);
		final String city = queryParameters.getString("city", false);
		final String country = queryParameters.getString("country", false);
		final Integer olderThan = queryParameters.getInteger("older_than");
		final Integer youngerThan = queryParameters.getInteger("younger_than");

		if ((firstName == null) && (lastName == null) && (olderThan == null) && (youngerThan == null) && (genderCode == null) && (city == null) && (country == null)) {
			throw new InvalidParameterException(APIErrorMessage.EMPTY_QUERY, net.violet.common.StringShop.EMPTY_STRING);
		}

		final int skip = inParam.getInt("skip", 0);
		final int count = inParam.getInt("count", 10);

		// note : we do not search here for unknown genders when genderCode is
		// null, instead, we do not apply the param
		final AnnuData.Gender gender = (genderCode != null) ? AnnuData.Gender.getValue(genderCode) : null;

		for (final UserData user : UserData.searchUsers(firstName, lastName, olderThan, youngerThan, gender, city, country, skip, count)) {
			lstPersonInformations.add(new PersonInformationMap(inParam.getCaller(), user, false));
		}

		return lstPersonInformations;
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
