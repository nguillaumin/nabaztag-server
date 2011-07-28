package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.common.utils.RegexTools;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.PersonAlreadyExistsException;
import net.violet.platform.api.maps.persons.PersonInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.dataobjects.TimezoneData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Templates;

public class Create extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, PersonAlreadyExistsException {

		final String email = inParam.getString("email", true);

		// Checks if the given email parameter really is a valid email address
		if (!RegexTools.isAValidEmail(email)) {
			throw new InvalidParameterException(APIErrorMessage.NOT_AN_EMAIL_ADDRESS, email);
		}

		// Checks if the given email address is not already used
		if (UserData.findByEmail(email) != null) {
			throw new PersonAlreadyExistsException(APIErrorMessage.EMAIL_ADDRESS_ALREADY_EXISTS);
		}

		final String password = inParam.getString("password", true);

		if (net.violet.common.StringShop.EMPTY_STRING.equals(password)) {
			throw new InvalidParameterException(APIErrorMessage.PASSWORD_CANNOT_BE_EMPTY, net.violet.common.StringShop.EMPTY_STRING);
		}

		final String firstName = inParam.getString("first_name", true);
		final String lastName = inParam.getString("last_name", true);
		final String city = inParam.getString("city", true);
		final String theTimeZoneName = inParam.getString("timezone", true);

		final TimezoneData theTimeZone = TimezoneData.findByJavaId(theTimeZoneName);
		if (theTimeZone == null) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_TIMEZONE, theTimeZoneName);
		}

		final String theLangCode = inParam.getString("lang", true);
		final SiteLangData theLang = SiteLangData.getByISOCode(theLangCode);

		final String theCountryCode = inParam.getString("country", true);
		final CountryData theCountry = CountryData.getDefaultCountryByJavaId(theCountryCode, theLang.getReference());
		if (theCountry == null) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_COUNTRY_CODE, theCountryCode);
		}

		final UserData newUser = UserData.createNewUser(email, password, theLang, theCountry, city, firstName, lastName, theTimeZone);

		if (newUser != null) {
			final PersonInformationMap result = new PersonInformationMap(inParam.getCaller(), newUser, true);
			Templates.createAccount(newUser.getReference());
			return result;
		}

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
