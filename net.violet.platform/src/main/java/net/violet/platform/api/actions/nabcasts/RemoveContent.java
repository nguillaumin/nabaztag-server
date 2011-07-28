package net.violet.platform.api.actions.nabcasts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.APIController;
import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchNabcastException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationContentData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.NabcastResourceData;
import net.violet.platform.dataobjects.UserData;

public class RemoveContent extends AbstractAction {

	private static final String REMOVE_CONTENT_KEY = "net.violet.applications.removeContent";
	static final String LABEL = "label";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws APIException, InvalidParameterException, ForbiddenException, InvalidSessionException {

		final ApplicationData theNabcast = ApplicationData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey(), true);
		final String sessionId = inParam.getString(ActionParam.SESSION_PARAM_KEY, true);
		final UserData theUser = SessionManager.getUserFromSessionId(sessionId, inParam.getCaller());
		if (!theNabcast.getOwner().equals(theUser)) {
			throw new ForbiddenException();
		}

		if (!Create.NABCAST_MATCHER.matcher(theNabcast.getName()).matches()) {
			throw new NoSuchNabcastException();
		}

		final String theResource = inParam.getString(RemoveContent.LABEL, true);

		for (final NabcastResourceData aResource : NabcastResourceData.findAllByLabel(theResource, theNabcast)) {

			final ApplicationContentData theContent = aResource.getContent();

			aResource.delete();

			if ((theContent != null) && theContent.isValid()) {
				final Map<String, Object> theParams = new HashMap<String, Object>();
				theParams.put(ActionParam.MAIN_PARAM_KEY, inParam.getMainParamAsString());
				theParams.put(ActionParam.SESSION_PARAM_KEY, sessionId);

				theParams.put(net.violet.platform.api.actions.applications.RemoveContent.CONTENT_ID, theContent.getApiId(inParam.getCaller()));
				APIController.getAction(RemoveContent.REMOVE_CONTENT_KEY).processRequest(new ActionParam(inParam.getCaller(), theParams));
			}

		}

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.DELETE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
