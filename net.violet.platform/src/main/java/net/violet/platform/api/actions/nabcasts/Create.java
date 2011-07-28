package net.violet.platform.api.actions.nabcasts;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import net.violet.platform.api.maps.SignatureInformationMap;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.api.maps.applications.ApplicationProfileMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Signature;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.SignatureData;
import net.violet.platform.dataobjects.UserData;

public class Create extends AbstractAction {

	static final String MODE_KEY = "mode";
	static final String SIGNATURE = "signatureId";

	static final String NABCAST_PREFIX = "net.violet.nabcast.";
	static final int MAX_SIZE = 128;
	private static final String APPLICATION_CREATE_ACTION = "violet.applications.create";
	static final String SET_SETTINGS_ACTION = "violet.applications.setSettings";

	static final FilesData ICON = FilesData.find(65193017L);
	static final FilesData SCHEDULING = FilesData.find(65197643L);
	static final FilesData SETTING = FilesData.find(65197585L);
	static final Pattern NABCAST_MATCHER = Pattern.compile(Pattern.quote(Create.NABCAST_PREFIX) + "\\d+\\..+");

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws APIException, ForbiddenException, InvalidParameterException, InvalidSessionException, NameAlreadyExistsException, NoSuchCategoryException, NoSuchFileException {

		final Map<String, String> profile = inParam.getMap(net.violet.platform.api.actions.applications.Create.PROFILE_MAP, true);
		final String nabcastName = profile.get(ApplicationProfileMap.TITLE);
		if (nabcastName.length() > Create.MAX_SIZE) {
			throw new InvalidParameterException(APIErrorMessage.OUT_OF_BOUND, nabcastName);
		}

		final String sessionId = inParam.getString(ActionParam.SESSION_PARAM_KEY, true);
		final UserData theUser = SessionManager.getUserFromSessionId(sessionId, inParam.getCaller());

		final MODE theMode = MODE.findByLabel(inParam.getString(Create.MODE_KEY, true));
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

		final ApplicationInformationMap theApplication = (ApplicationInformationMap) APIController.getAction(Create.APPLICATION_CREATE_ACTION).processRequest(theParam);

		final FilesData theFileData = FilesData.getFilesData(signature.get(SignatureInformationMap.ITEM), inParam.getCallerAPIKey());
		final AnimData theAnimData = AnimData.findByAPIId(signature.get(SignatureInformationMap.ANIM), inParam.getCallerAPIKey());
		final Signature.ColorType theColor = Signature.ColorType.getColorTypeByLabel(signature.get(SignatureInformationMap.COLOR));

		theParam = (ActionParam) inParam.clone();
		theParam.put(ActionParam.MAIN_PARAM_KEY, theApplication.getString(ApplicationInformationMap.ID));
		final Map<String, String> theSettings = new HashMap<String, String>();
		theSettings.put(Create.MODE_KEY, theMode.label);
		theSettings.put(Create.SIGNATURE, String.valueOf(SignatureData.get(theFileData, theAnimData, theColor).getId()));
		theParam.put("settings", theSettings);

		APIController.getAction(Create.SET_SETTINGS_ACTION).processRequest(theParam);

		return null;
	}

	public static enum MODE {
		RANDOM("random"),
		SEQUENTIAL("sequential");

		private static final Map<String, MODE> LABEL_MAP;
		static {
			final Map<String, MODE> theLabelMap = new HashMap<String, MODE>();
			for (final MODE aMode : MODE.values()) {
				theLabelMap.put(aMode.label, aMode);
			}
			LABEL_MAP = Collections.unmodifiableMap(theLabelMap);
		}

		private final String label;

		MODE(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return this.label;
		}

		public static MODE findByLabel(String label) {
			return MODE.LABEL_MAP.get(label);
		}

	}

	static String generateApplicationName(String nabcastName, UserData inUser) {
		return Create.NABCAST_PREFIX + inUser.getId() + net.violet.common.StringShop.POINT + nabcastName;
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
