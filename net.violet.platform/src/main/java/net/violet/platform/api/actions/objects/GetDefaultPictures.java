package net.violet.platform.api.actions.objects;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.util.Constantes;

/**
 * This API Action is used to retrieve a list of pictures according to a given objec type.
 * The caller must provide the object type name as main parameter, this object type has to be a 
 * primary type.
 * The returned object is a list of ids (List<String>), each of them being a file identifier.
 */
public class GetDefaultPictures extends AbstractAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {
		final String typeName = inParam.getMainParamAsString();

		final ObjectType theType = ObjectType.findByName(typeName);
		if ((theType == null) || !theType.isPrimaryObject()) {
			throw new InvalidParameterException(APIErrorMessage.NO_VALID_OBJECT_TYPE, "mainParam", typeName);
		}

		final List<String> theFilesIds = new LinkedList<String>();
		for (final FilesData aPictureFile : theType.getDefaultPictures()) {
			theFilesIds.add(aPictureFile.getApiId(inParam.getCaller()));
		}

		return theFilesIds;
	}

	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
