package net.violet.platform.api.actions.objects;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.ObjectAlreadyExistsException;
import net.violet.platform.api.maps.objects.ObjectInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.TagTmpSiteData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.Constantes;

/**
 * This API Action creates a new VObject and links it to the user. The VObject must have been recognized and added to the temporary vobjects table.
 */
public class Create extends AbstractObjectAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, ObjectAlreadyExistsException, NoSuchObjectException, InternalErrorException {

		final UserData theSessionUser = SessionManager.getUserFromSessionParam(inParam);
		final String theSerialNumber = inParam.getString("serial_number", true);

		final TagTmpSiteData theTagTmpSiteData = TagTmpSiteData.findBySerial(theSerialNumber);
		if (theTagTmpSiteData == null) {
			throw new NoSuchObjectException();
		}

		final ObjectType theObjectType = theTagTmpSiteData.getObjectType();

		if (!theObjectType.isValidSerial(theSerialNumber)) {
			throw new InvalidParameterException(APIErrorMessage.NO_VALID_OBJECT_SERIAL, net.violet.common.StringShop.EMPTY_STRING);
		}

		if (VObjectData.findBySerial(theSerialNumber) != null) {
			throw new ObjectAlreadyExistsException(APIErrorMessage.SERIAL_ALREADY_EXISTS);
		}

		final String theObjectLabel = inParam.getString("object_name", true);

		// Creates the object
		final VObjectData theObjectData = VObjectData.createObject(theObjectType, theSerialNumber, theObjectLabel, theSessionUser, "unknow/unknow");
		if ((theObjectData == null) || !theObjectData.isValid()) {
			throw new InternalErrorException("Object creation failed");
		}

		// l'utilisateur a desormais au moins un objet
		theSessionUser.setHasObject();
		// suppression de tag_tmp_site car l'objet est bien inscrit
		theTagTmpSiteData.delete();

		if (theObjectData.getObjectType().instanceOf(ObjectType.NABAZTAG)) {
			theObjectData.addDefaultServices();
		}

		return new ObjectInformationMap(inParam.getCaller(), theObjectData, true);
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	/**
	 * Object informations may be cached one day
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.CREATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
