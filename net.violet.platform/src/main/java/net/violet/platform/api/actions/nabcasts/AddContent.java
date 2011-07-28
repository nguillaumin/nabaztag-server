package net.violet.platform.api.actions.nabcasts;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.violet.platform.api.actions.APIController;
import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.items.Generate;
import net.violet.platform.api.actions.items.Generate.GENERATION_TYPES;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NoSuchNabcastException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationContentData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.NabcastResourceData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;

public class AddContent extends AbstractAction {

	static final String EXPIRATION_DATE = "expiration_date";
	static final String RELEASE_DATE = "release_date";
	static final String LABEL = "label";

	private static final String ADD_CONTENT_KEY = "net.violet.applications.addContent";
	private static final String REMOVE_CONTENT_KEY = "net.violet.nabcasts.removeContent";
	private static final String GENERATE_KEY = "net.violet.items.generate";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws APIException, InvalidParameterException, NoSuchApplicationException, ForbiddenException, InvalidSessionException {

		final ApplicationData theNabcast = ApplicationData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey(), true);

		if (!Create.NABCAST_MATCHER.matcher(theNabcast.getName()).matches()) {
			throw new NoSuchNabcastException();
		}

		final UserData theUser = SessionManager.getUserFromSessionId(inParam.getString(ActionParam.SESSION_PARAM_KEY, true), inParam.getCaller());
		if (!theNabcast.getOwner().equals(theUser)) {
			throw new ForbiddenException();
		}

		final String thePath = inParam.getString(AddContent.LABEL, true);
		final Date theExpirationDate = inParam.getDate(AddContent.EXPIRATION_DATE, false);
		final Date theReleaseDate = inParam.getDate(AddContent.RELEASE_DATE, true);

		final Map<String, Object> generateParams = new HashMap<String, Object>();
		generateParams.put("body", inParam.getBody());
		generateParams.put(ActionParam.MAIN_PARAM_KEY, inParam.getString("mime_type", true));

		final List<String> formats = new LinkedList<String>();
		for (final ObjectType aType : theNabcast.getSupportedTypes()) {
			if ((aType == ObjectType.NABAZTAG_V1) || (aType == ObjectType.DALDAL)) {
				formats.add(Generate.GENERATION_TYPES.ADP.getLabel());
				formats.add(Generate.GENERATION_TYPES.CHOR.getLabel());
			} else if (aType == ObjectType.NABAZTAG_V2) {
				formats.add(Generate.GENERATION_TYPES.MP3_32.getLabel());
			} else if (aType == ObjectType.MIRROR) {
				formats.add(Generate.GENERATION_TYPES.MP3_128.getLabel());
			}
		}
		generateParams.put(Generate.FORMATS, formats);

		final Map<String, String> theGeneratedFiles = (Map<String, String>) APIController.getAction(AddContent.GENERATE_KEY).processRequest(new ActionParam(inParam.getCaller(), generateParams));

		final List<String> createdContents = new LinkedList<String>();

		for (final Entry<String, String> aGeneratedFile : theGeneratedFiles.entrySet()) {

			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put(ActionParam.MAIN_PARAM_KEY, theNabcast.getApiId(inParam.getCaller()));
			theParams.put(net.violet.platform.api.actions.applications.AddContent.FILE_ID, aGeneratedFile.getValue());
			theParams.put(ActionParam.SESSION_PARAM_KEY, inParam.getString(ActionParam.SESSION_PARAM_KEY));

			final String theContentId = (String) APIController.getAction(AddContent.ADD_CONTENT_KEY).processRequest(new ActionParam(inParam.getCaller(), theParams));
			if (theContentId == null) {
				emergencyExit(createdContents, inParam.getCaller(), inParam.getMainParamAsString(), inParam.getString(ActionParam.SESSION_PARAM_KEY));
			}

			final ApplicationContentData theContent = ApplicationContentData.findByAPIId(theContentId, inParam.getCallerAPIKey());
			final Generate.GENERATION_TYPES theType = GENERATION_TYPES.findByLabel(aGeneratedFile.getKey());
			if (theType != null) {
				for (final ObjectType aType : theType.getObjectTypes()) {
					final NabcastResourceData theNewResource = NabcastResourceData.create(theContent, theExpirationDate, theReleaseDate, aType, thePath);
					if ((theNewResource == null) || !theNewResource.isValid()) {
						emergencyExit(createdContents, inParam.getCaller(), inParam.getMainParamAsString(), inParam.getString(ActionParam.SESSION_PARAM_KEY));
					} else {
						createdContents.add(theContentId);
					}

				}
			}

		}

		return null;
	}

	private void emergencyExit(List<String> createdContents, APICaller caller, String inApplicationId, String sessionId) throws InvalidParameterException, APIException {
		for (final String contentId : createdContents) {
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put(ActionParam.MAIN_PARAM_KEY, inApplicationId);
			theParams.put(ActionParam.SESSION_PARAM_KEY, sessionId);
			theParams.put(net.violet.platform.api.actions.applications.RemoveContent.CONTENT_ID, contentId);

			APIController.getAction(AddContent.REMOVE_CONTENT_KEY).processRequest(new ActionParam(caller, theParams));
		}
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
