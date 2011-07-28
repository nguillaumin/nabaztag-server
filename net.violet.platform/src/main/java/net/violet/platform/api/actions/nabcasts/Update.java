package net.violet.platform.api.actions.nabcasts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.APIController;
import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NameAlreadyExistsException;
import net.violet.platform.api.exceptions.NoSuchCategoryException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchNabcastException;
import net.violet.platform.api.maps.SignatureInformationMap;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.api.maps.applications.ApplicationProfileMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Signature;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SignatureData;
import net.violet.platform.dataobjects.UserData;

public class Update extends AbstractAction {

	private static final String APPLICATION_UPDATE_ACTION = "violet.applications.update";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws APIException, ForbiddenException, InvalidParameterException, InvalidSessionException, NameAlreadyExistsException, NoSuchCategoryException, NoSuchFileException {

		final ApplicationData theNabcast = ApplicationData.findByAPIId(inParam.getMainParamAsString(), inParam.getCallerAPIKey(), true);

		if (!Create.NABCAST_MATCHER.matcher(theNabcast.getName()).matches()) {
			throw new NoSuchNabcastException();
		}

		final UserData theUser = SessionManager.getUserFromSessionId(inParam.getString(ActionParam.SESSION_PARAM_KEY, true), inParam.getCaller());
		if (!theNabcast.getOwner().equals(theUser)) {
			throw new ForbiddenException();
		}

		final Map<String, String> profile = inParam.getMap(net.violet.platform.api.actions.applications.Create.PROFILE_MAP, true);
		final String nabcastName = profile.get(ApplicationProfileMap.TITLE);
		if (nabcastName.length() > Create.MAX_SIZE) {
			throw new InvalidParameterException(APIErrorMessage.OUT_OF_BOUND, nabcastName);
		}

		final Create.MODE theMode = Create.MODE.findByLabel(inParam.getString(Create.MODE_KEY, true));
		if (theMode == null) {
			throw new InvalidParameterException(Create.MODE_KEY, inParam.getString(Create.MODE_KEY, true));
		}
		final Map<String, String> signature = inParam.getMap("signature", true);

		final String applicationName = Create.generateApplicationName(nabcastName, theUser);

		ActionParam theParam = (ActionParam) inParam.clone();
		theParam.put(net.violet.platform.api.actions.applications.Create.NAME, applicationName);
		theParam.put(net.violet.platform.api.actions.applications.Create.CLASS, ApplicationClass.NATIVE.name());

		profile.put(ApplicationProfileMap.ICON_FILE, Create.ICON.getApiId(inParam.getCaller()));
		profile.put(ApplicationProfileMap.SCHEDULING_FILE, Create.SCHEDULING.getApiId(inParam.getCaller()));
		profile.put(ApplicationProfileMap.SETTING_FILE, Create.SETTING.getApiId(inParam.getCaller()));

		final List<String> hardwaresLabels = new LinkedList<String>();
		for (final ObjectType aType : theNabcast.getSupportedTypes()) {
			hardwaresLabels.add(aType.getTypeName());
		}
		theParam.put(net.violet.platform.api.actions.applications.Create.HARDWARES, hardwaresLabels);

		final ApplicationInformationMap theApplication = (ApplicationInformationMap) APIController.getAction(Update.APPLICATION_UPDATE_ACTION).processRequest(theParam);

		final FilesData theFileData = FilesData.getFilesData(signature.get(SignatureInformationMap.ITEM), inParam.getCallerAPIKey());
		final AnimData theAnimData = AnimData.findByAPIId(signature.get(SignatureInformationMap.ANIM), inParam.getCallerAPIKey());
		final Signature.ColorType theColor = Signature.ColorType.getColorTypeByLabel(signature.get(SignatureInformationMap.COLOR));

		theParam = (ActionParam) inParam.clone();
		theParam.put(ActionParam.MAIN_PARAM_KEY, theApplication.getString(ApplicationInformationMap.ID));
		final Map<String, String> theSettings = new HashMap<String, String>();
		theSettings.put(Create.MODE_KEY, theMode.toString());
		theSettings.put(Create.SIGNATURE, String.valueOf(SignatureData.get(theFileData, theAnimData, theColor).getId()));
		theParam.put("settings", theSettings);

		APIController.getAction(Create.SET_SETTINGS_ACTION).processRequest(theParam);

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
