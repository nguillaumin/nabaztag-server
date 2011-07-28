package net.violet.platform.api.actions.groups;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchGroupException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.GroupData;
import net.violet.platform.dataobjects.VObjectData;

public class Join extends AbstractAction {

	@APIMethodParam
	private final static String OBJECT = "object";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchGroupException, ForbiddenException {

		final VObjectData theObject = VObjectData.findByAPIId(inParam.getString(Join.OBJECT, true), inParam.getCallerAPIKey());

		final GroupData theGroup = GroupData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey());
		if ((theGroup == null) || !theGroup.isValid()) {
			throw new NoSuchGroupException();
		}

		if (theGroup.getMembers().contains(theObject)) {
			throw new ForbiddenException();
		}

		theGroup.addMember(theObject);

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public boolean isCacheable() {
		return false;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
