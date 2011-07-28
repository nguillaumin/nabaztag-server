package net.violet.platform.api.actions.objects;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.AbstractAction.APIMethodParam.Level;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.api.maps.objects.ObjectPreferencesMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.ObjectPreferencesData;
import net.violet.platform.dataobjects.TimezoneData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.SleepTime;

import org.apache.log4j.Logger;

/**
 * Set the preferences of a given object.
 */

public class SetPreferences extends AbstractObjectAction {

	private static final Logger LOGGER = Logger.getLogger(SetPreferences.class);

	@APIMethodParam(Level.REQUIRED)
	public static final String PREFERENCES = "preferences";

	/**
	 * Process violet.objects.setPreferences
	 * 
	 * @see net.violet.platform.api.actions.Action#processRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ForbiddenException, InvalidSessionException {

		final VObjectData object = getRequestedObject(inParam);
		final UserData owner = object.getOwner();

		// Check Session
		doesSessionBelongToVObject(object, inParam);

		// Retrieve existing preferences
		ObjectPreferencesData currentPreferences = object.getPreferences();

		if ((currentPreferences == null) || (currentPreferences.getReference() == null)) {
			// Create preferences
			currentPreferences = ObjectPreferencesData.createObjectPreferences(object, ObjectLangData.getDefaultObjectLanguage(owner.getUserLang().getLang_iso_code()));
		}

		// Retrieve the posted values
		final PojoMap newPreferences = inParam.getPojoMap(SetPreferences.PREFERENCES, true);

		// Read the new values, defaulting to ancient value if they are not
		// found
		final boolean isVisible = newPreferences.getBoolean(ObjectPreferencesMap.VISIBLE, currentPreferences.isVisible());
		final boolean isPrivate = newPreferences.getBoolean(ObjectPreferencesMap.PRIVATE, currentPreferences.isPrivate());
		final boolean notifyReceived = newPreferences.getBoolean(ObjectPreferencesMap.NOTIFY_RECEIVED, owner.notifyIfReceived());

		final String timezoneId = newPreferences.getString(ObjectPreferencesMap.TIMEZONE, object.getTimeZone());
		final TimezoneData timezone = TimezoneData.findByJavaId(timezoneId);

		if (timezone == null) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_TIMEZONE, inParam.toString());
		}

		SetPreferences.LOGGER.info("The NewPreferences : " + newPreferences);

		SleepTime sleepTime = null;
		if (newPreferences.containsKey(ObjectPreferencesMap.SLEEP_TIMES)) {

			final PojoMap sleepMap = newPreferences.getPojoMap(ObjectPreferencesMap.SLEEP_TIMES, false);
			SetPreferences.LOGGER.info("SleepTimes present : " + sleepMap);

			final PojoMap weekSleepingTimes = sleepMap.getPojoMap("monday", false);
			final PojoMap weekendSleepingTimes = sleepMap.getPojoMap("saturday", false);
			SetPreferences.LOGGER.info("Monday : " + weekSleepingTimes);
			SetPreferences.LOGGER.info("Saturday : " + weekendSleepingTimes);

			sleepTime = new SleepTime(weekSleepingTimes.getInt("wakeup_time_h", -1), weekSleepingTimes.getInt("wakeup_time_m", -1), weekSleepingTimes.getInt("sleep_time_h", -1), weekSleepingTimes.getInt("sleep_time_m", -1), weekendSleepingTimes.getInt("wakeup_time_h", -1), weekendSleepingTimes.getInt("wakeup_time_m", -1), weekendSleepingTimes.getInt("sleep_time_h", -1), weekendSleepingTimes.getInt("sleep_time_m", -1));
		}

		// Language : accepted API values are ISO codes,
		ObjectLangData languageData;
		final String languageISOCode = newPreferences.getString(ObjectPreferencesMap.LANGUAGE, null);

		if (languageISOCode != null) {
			languageData = ObjectLangData.getByISOCode(languageISOCode);
		} else {
			// default to the current language selection
			languageData = currentPreferences.getLang();
		}

		object.setPreferences(notifyReceived, timezone, sleepTime);
		currentPreferences.setPreferences(isVisible, isPrivate, languageData);

		return null;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

}
