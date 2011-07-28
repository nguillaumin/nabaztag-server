package net.violet.platform.api.actions.objects;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.VObjectData;

/**
 * Renvoie l'id API d'un objet s'il est enregistré.
 */
public class IsRegistered extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String objectSerial = inParam.getMainParamAsString();
		final VObjectData objectData = VObjectData.findBySerial(objectSerial);

		if ((objectData != null) && objectData.isValid()) {
			final PojoMap registeredObject = new PojoMap(2);
			registeredObject.put("id", objectData.getApiId(inParam.getCaller()));
			return registeredObject;

		}

		return false;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return Action.ActionType.GET;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
