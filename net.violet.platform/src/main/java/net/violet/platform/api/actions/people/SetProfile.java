package net.violet.platform.api.actions.people;

import java.util.Date;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;

public class SetProfile extends AbstractUserAction {

	/**
	 * @throws NoSuchPersonException
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws NoSuchFileException
	 * @throws APIException (NoSuchPersonException, InvalidDataException)
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, ForbiddenException, InvalidSessionException, NoSuchFileException {

		final UserData theUser = getRequestedUser(inParam, null);

		// Check Session
		AbstractUserAction.doesSessionBelongToUser(theUser, inParam);

		// Retrieve the existing profile
		final AnnuData currentProfile = theUser.getAnnu();
		final AnnuData.Gender currentGender = AnnuData.Gender.getValue(currentProfile.getAnnu_sexe());

		// Retrieve the posted values
		final PojoMap newProfile = inParam.getPojoMap("profile", true);

		// Read the new values, defaulting to ancient value if they are not
		// found
		final String firstName = newProfile.getString("first_name", currentProfile.getFirstName());
		final String lastName = newProfile.getString("last_name", currentProfile.getLastName());
		final Date birthDate = newProfile.getDate("birth_date", currentProfile.getBirthDate());

		// the gender determination involves a special trick :
		// API gender values for setProfile, getProfile.. are 'M', 'F'
		// but the values in the Annu table are : 'H', 'F'.. (!!)
		final AnnuData.Gender gender = AnnuData.Gender.getValue(newProfile.getString("gender", currentGender.getProfileCode()));

		if (newProfile.containsKey("gender") && (gender == AnnuData.Gender.UNKNOWN)) { // we couldn 't recognize the passed value
			throw new InvalidParameterException(APIErrorMessage.NOT_A_GENDER, net.violet.common.StringShop.EMPTY_STRING);
		}

		final String city = newProfile.getString("city", currentProfile.getAnnuCity());
		final String zip = newProfile.getString("zip_code", currentProfile.getAnnuCp());
		final String countryCode = newProfile.getString("country", currentProfile.getAnnuCountry());
		final String description = newProfile.getString("description", currentProfile.getDescription());

		CountryData country = null;
		if (countryCode != null) {
			country = CountryData.getDefaultCountryByJavaId(countryCode, SiteLangData.DEFAULT_SITE_LANGUAGE.getReference());
			if (country == null) {
				throw new InvalidParameterException(APIErrorMessage.NOT_A_COUNTRY_CODE, net.violet.common.StringShop.EMPTY_STRING);
			}
		}

		currentProfile.updateProfile(firstName, lastName, birthDate, gender.getCodeAnnu(), city, zip, country, description);

		final String thePictureFile = inParam.getString("picture_file", false);

		if (thePictureFile != null) { // mise à jour de l'image
			final FilesData filesData = FilesData.getFilesData(thePictureFile, inParam.getCallerAPIKey());
			currentProfile.updateProfilePicture(filesData);
		}

		return null;
	}

	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return true;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
