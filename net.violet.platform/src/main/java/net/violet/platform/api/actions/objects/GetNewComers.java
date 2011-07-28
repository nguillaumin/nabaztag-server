package net.violet.platform.api.actions.objects;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.objects.ObjectInformationMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.StringShop;

public class GetNewComers extends AbstractObjectAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final int count = inParam.getInt("count", 10);

		String hardwareProperty = inParam.getString("hw_type", false);
		if (hardwareProperty == null) {
			hardwareProperty = ObjectType.NABAZTAG.getTypeName();
		}

		final ObjectType theType = ObjectType.findByName(hardwareProperty.toString());
		if (theType == null) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_HARDWARE_NAME, StringShop.EMPTY_STRING);
		}

		final List<ObjectInformationMap> resultList = new LinkedList<ObjectInformationMap>();
		for (final VObjectData anObject : VObjectData.findLastCreatedByType(count, theType)) {
			resultList.add(new ObjectInformationMap(inParam.getCaller(), anObject, false));
		}

		return resultList;

	}

	// Action interface

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

	public long getExpirationTime() {
		return Constantes.TWO_HOURS_IN_S;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
