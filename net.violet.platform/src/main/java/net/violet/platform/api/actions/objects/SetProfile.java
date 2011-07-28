package net.violet.platform.api.actions.objects;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.GPSInformationMap;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.objects.ObjectProfileMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectProfileData;
import net.violet.platform.dataobjects.VObjectData;

public class SetProfile extends AbstractObjectAction {

	/**
	 * @throws NoSuchObjectException
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws NoSuchFileException
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ForbiddenException, InvalidSessionException, NoSuchFileException {

		final VObjectData theObject = getRequestedObject(inParam);

		// Check Session
		doesSessionBelongToVObject(theObject, inParam);

		// Retrieve the existing profile
		ObjectProfileData currentProfile = theObject.getProfile();

		if ((currentProfile == null) || (currentProfile.getReference() == null)) {
			// create profile
			currentProfile = ObjectProfileData.createObjectProfile(theObject);
		}

		// Retrieve the posted values
		final PojoMap newProfile = inParam.getPojoMap("profile", true);

		// Read the new values, defaulting to ancient value if they are not
		// found
		final String description = newProfile.getString(ObjectProfileMap.DESCRIPTION, currentProfile.getDescription());

		currentProfile.setDescription(description);

		final String thePictureFile = inParam.getString("picture_file", false);
		if (thePictureFile != null) { // mise à jour de l'image
			final FilesData filesData = FilesData.getFilesData(thePictureFile, inParam.getCallerAPIKey());
			currentProfile.setPicture(filesData);
		}

		final String label = newProfile.getString(ObjectProfileMap.LABEL, currentProfile.getLabel());
		currentProfile.setLabel(label);

		if (newProfile.containsKey(ObjectProfileMap.GPS_LOCALIZATION)) {
			final PojoMap GPSInformation = newProfile.getPojoMap(ObjectProfileMap.GPS_LOCALIZATION, false);
			final String theLatitude = GPSInformation.getString(GPSInformationMap.LATITUDE, true);
			final String theLongitude = GPSInformation.getString(GPSInformationMap.LONGITUDE, true);
			currentProfile.setGPSInformation(theLatitude, theLongitude);
		}

		return null;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0; // expires immediately
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
