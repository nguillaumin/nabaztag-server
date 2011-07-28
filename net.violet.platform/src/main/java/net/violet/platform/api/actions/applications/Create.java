package net.violet.platform.api.actions.applications;

import java.util.ArrayList;
import java.util.List;

import net.violet.common.StringShop;
import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NameAlreadyExistsException;
import net.violet.platform.api.exceptions.NoSuchCategoryException;
import net.violet.platform.api.exceptions.NoSuchClassException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.api.exceptions.NoSuchObjectTypeException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.applications.ApplicationAdminMap;
import net.violet.platform.api.maps.applications.ApplicationInformationMap;
import net.violet.platform.api.maps.applications.ApplicationProfileMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationCategoryData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.Constantes;

public class Create extends AbstractAction {

	public static final String NAME = "name";
	public static final String CLASS = "class";
	static final String INTERACTIVE = "interactive";
	public static final String CATEGORY = "category";
	static final String VISIBLE = "visible";
	static final String REMOVABLE = "removable";

	public static final String PROFILE_MAP = "profile";
	public static final String LANGUAGES = "languages";
	public static final String HARDWARES = "hardwares";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NameAlreadyExistsException, NoSuchCategoryException, NoSuchFileException, NoSuchClassException, NoSuchObjectTypeException, ForbiddenException, InvalidSessionException {

		final UserData theUser = SessionManager.getUserFromSessionId(inParam.getString(ActionParam.SESSION_PARAM_KEY, true), inParam.getCaller());

		final String applicationName = inParam.getString(Create.NAME, true);
		if (null != ApplicationData.findByName(applicationName)) {
			throw new NameAlreadyExistsException(APIErrorMessage.NAME_ALREADY_EXISTS);
		}

		final boolean isInteractive = inParam.getBoolean(Create.INTERACTIVE, false);
		final ApplicationCategoryData applicationCategory = ApplicationCategoryData.findByName(inParam.getString(Create.CATEGORY, true));
		if (applicationCategory == null) {
			throw new NoSuchCategoryException(inParam.getString(Create.CATEGORY, true));
		}

		final ApplicationClass theClass = ApplicationClass.getByName(inParam.getString(Create.CLASS, true));
		if (theClass == null) {
			throw new NoSuchClassException(inParam.getString(Create.CLASS, true));
		}

		final boolean isVisible = inParam.getBoolean(Create.VISIBLE, false);
		final boolean isRemovable = inParam.getBoolean(Create.REMOVABLE, true);

		final List<ObjectType> theObjectTypes = new ArrayList<ObjectType>();
		for (final String aTypeLabel : inParam.<String> getList(Create.HARDWARES, true)) {
			final ObjectType aType = ObjectType.findByName(aTypeLabel);
			if ((aType == null) || !aType.isPrimaryObject()) {
				throw new NoSuchObjectTypeException(aTypeLabel);
			}
			theObjectTypes.add(aType);
		}

		final List<SiteLangData> theLanguages = new ArrayList<SiteLangData>();
		for (final String aLanguageCode : inParam.<String> getList(Create.LANGUAGES, true)) {
			theLanguages.add(SiteLangData.getByISOCode(aLanguageCode));
		}

		final PojoMap applicationProfile = inParam.getPojoMap(Create.PROFILE_MAP, true);
		final String title = applicationProfile.getString(ApplicationProfileMap.TITLE, true);
		final String description = applicationProfile.getString(ApplicationProfileMap.DESCRIPTION, true);
		final boolean isOpenSource = applicationProfile.getBoolean(ApplicationProfileMap.OPEN_SOURCE, false);
		final String instructions = applicationProfile.getString(ApplicationProfileMap.INSTRUCTIONS, false);

		final String pictureId = applicationProfile.getString(ApplicationProfileMap.PICTURE_FILE, false);
		final String iconId = applicationProfile.getString(ApplicationProfileMap.ICON_FILE, false);
		final String announceId = applicationProfile.getString(ApplicationAdminMap.ANNOUNCEMENT_FILE, false);
		final String url = applicationProfile.getString(ApplicationProfileMap.URL, false);

		final FilesData picture = ((pictureId != null) && !StringShop.EMPTY_STRING.equals(pictureId)) ? FilesData.getFilesData(pictureId, inParam.getCallerAPIKey()) : FilesData.getData(null);
		final FilesData icon = ((iconId != null) && !StringShop.EMPTY_STRING.equals(iconId)) ? FilesData.getFilesData(iconId, inParam.getCallerAPIKey()) : FilesData.getData(null);
		final FilesData announceFile = ((announceId != null) && !StringShop.EMPTY_STRING.equals(announceId)) ? FilesData.getFilesData(announceId, inParam.getCallerAPIKey()) : FilesData.getData(null);

		final String settingsParams = applicationProfile.getString(ApplicationProfileMap.SETTING_FILE, false);
		FilesData settings = FilesData.getData(null);
		if (settingsParams != null) {
			settings = FilesData.getFilesData(settingsParams, inParam.getCallerAPIKey());
		}

		final String schedulingParams = applicationProfile.getString(ApplicationProfileMap.SCHEDULING_FILE, false);
		FilesData schedulings = FilesData.getData(null);
		if (settingsParams != null) {
			schedulings = FilesData.getFilesData(schedulingParams, inParam.getCallerAPIKey());
		}

		final ApplicationData.ApplicationBuilder theApplicationBuilder = new ApplicationData.ApplicationBuilder(theUser, applicationName, theClass, applicationCategory);

		if (isInteractive) {
			theApplicationBuilder.setInteractive();
		}
		if (!isVisible) {
			theApplicationBuilder.setInvisible();
		}
		if (!isRemovable) {
			theApplicationBuilder.setUnRemovable();
		}

		final ApplicationData.ApplicationBuilder.ProfileBuilder theProfilBuilder = theApplicationBuilder.new ProfileBuilder(title, description, instructions, url);

		if (isOpenSource) {
			theProfilBuilder.setOpenSource();
		}

		theProfilBuilder.addAnnounceFile(announceFile).addIconFile(icon).addPictureFile(picture).addSettingsFile(settings).addSchedulingFile(schedulings);

		final ApplicationData theApplication = theApplicationBuilder.build(theProfilBuilder);
		if ((theApplication != null) && theApplication.isValid()) {
			theApplication.setHardware(theObjectTypes);
			theApplication.setLanguages(theLanguages);
			return new ApplicationInformationMap(inParam.getCaller(), theApplication);
		}
		return null;
	}

	/**
	 * Cached information expires after one day
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	/**
	 * YES ! this information may be cached !
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
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
