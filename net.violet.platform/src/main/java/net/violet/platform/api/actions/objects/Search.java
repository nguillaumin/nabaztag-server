package net.violet.platform.api.actions.objects;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.objects.ObjectInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;

public class Search extends AbstractObjectAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException {

		final PojoMap queryParameters = inParam.getPojoMap("query", true);
		final List<ObjectInformationMap> theResultList = new ArrayList<ObjectInformationMap>();

		if (queryParameters.isEmpty()) {
			throw new InvalidParameterException(APIErrorMessage.EMPTY_QUERY, net.violet.common.StringShop.EMPTY_STRING);
		}

		final String theName = queryParameters.getString("name", false);
		final String theHardware = queryParameters.getString("hw_type", false);
		final String theCity = queryParameters.getString("city", false);
		final String theCountry = queryParameters.getString("country", false);

		if ((theName == null) && (theHardware == null) && (theCity == null) && (theCountry == null)) {
			throw new InvalidParameterException(APIErrorMessage.EMPTY_QUERY, net.violet.common.StringShop.EMPTY_STRING);
		}

		final int skip = inParam.getInt("skip", 0);
		final int count = inParam.getInt("count", 10);

		ObjectType theType = null;
		if (theHardware != null) {
			theType = ObjectType.findByName(theHardware);
			if (theType == null) {
				throw new InvalidParameterException(APIErrorMessage.NOT_A_VALID_OBJECT, net.violet.common.StringShop.EMPTY_STRING);
			}
		}

		for (final VObjectData theObject : VObjectData.searchObjects(theName, theType, theCity, theCountry, skip, count)) {
			final boolean isVisible = checkOwnerObject(theObject, inParam);
			theResultList.add(new ObjectInformationMap(inParam.getCaller(), theObject, isVisible));
		}

		return theResultList;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
