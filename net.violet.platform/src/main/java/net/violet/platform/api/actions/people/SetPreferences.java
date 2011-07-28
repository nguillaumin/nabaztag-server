package net.violet.platform.api.actions.people;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FriendsListsData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.dataobjects.TimezoneData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.UserEmailData;
import net.violet.platform.dataobjects.VObjectData;

/**
 * Set the preferences of a given user. emails A list of alternate e-mail
 * addresses (string list). timezone Timezone where the person is (string).
 * time_format Time format either 12 or 24 ( TimeFormat ). language IETF code of
 * the preferred language for all interac- tion with the end person (string).
 * newsletter Whether the person subscribed to the newsletter (boolean).
 * notify_added_as_contact Whether the person must be notiﬁed when he is added
 * as a contact (boolean). must_approve_contact_request Whether the person must
 * be approve contact request (boolean). visible Is the person name visible in
 * the Violet directory (boolean).
 */
public class SetPreferences extends AbstractUserAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, ForbiddenException, InvalidSessionException {

		final UserData user = getRequestedUser(inParam, null);

		// Check Session
		AbstractUserAction.doesSessionBelongToUser(user, inParam);

		final PojoMap preferences = inParam.getPojoMap("preferences", true);

		// Read the posted values, defaulting to the current ones if they are
		// not present

		// timezone Ex : America/New_York, Europe/Paris
		final String timezoneId = preferences.getString("timezone", user.getTimezone().getTimezone_javaId());

		final TimezoneData timezoneData = TimezoneData.findByJavaId(timezoneId);
		if (timezoneData == null) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_TIMEZONE, timezoneId);
		}

		final List<String> alternateEmails = preferences.getList("emails");
		if (alternateEmails != null) {
			for (final String anEmail : alternateEmails) {
				if (!UserEmailData.isAddressAvailableForUser(user, anEmail)) {
					throw new InvalidParameterException("emails", anEmail);
				}
			}
		}

		// time_format : accepted API values are 12 or 24
		// current user_data value : 1 = 24 h , 0 = 12 h
		int timeFormat = preferences.getInt("time_format", (user.getUser_24() == 1) ? 24 : 0);
		// convert back into the user_data encoding values
		timeFormat = (timeFormat == 24) ? 1 : 0;

		// Language : accepted API values are ISO codes,
		SiteLangData languageData;
		final String languageISOCode = preferences.getString("language", null);

		if (languageISOCode != null) {
			languageData = SiteLangData.getByISOCode(languageISOCode);
		} else {
			// default to the current language selection
			languageData = user.getUserLang();
		}

		// TODO : Fix temporaire, en attente que le webui puisse prendre en compte cela. 
		// Quand le user change la langue du site, on update aussi ces objets.
		final List<VObjectData> objectList = VObjectData.findByOwner(user);
		final ObjectLangData theObjectLang = ObjectLangData.getDefaultObjectLanguage(user.getUserLang().getLang_iso_code());
		for (final VObjectData theObjectData : objectList) {
			theObjectData.getPreferences().setLang(theObjectLang);
		}

		// news letter subscription : accepted API values are boolean
		final boolean newsletter = preferences.getBoolean("newsletter", user.isNewsletterSubscriber());

		// Si on notifie l'utilisateur lorsque ses messages envoyés sont lus.
		final boolean notifyMessagePlayed = preferences.getBoolean("notify_played", user.notifyIfPlayed());

		// Save the updated preferences in the user table
		user.setPreferences(timezoneData, timeFormat, languageData, newsletter, notifyMessagePlayed);

		// visibility is stored in the annu_confirm field (0, 1)
		final Boolean visible = preferences.getBoolean("visible");
		if (visible != null) {
			user.getAnnu().setAnnuConfirm(visible.booleanValue() ? 1 : 0);
		}

		/*
		 * Confirmation level for warnings : notify_added_as_contact +
		 * must_approve_contact_request
		 */
		int confirmationLevel = -1;
		final FriendsListsData friendList = FriendsListsData.findByUser(user);
		/*
		 * DB field : friendlists_confirmationlevel 0 : no confirmation 1 : must
		 * approve contact request 2 : notify me when added as a contact 3 : 2 +
		 * 1
		 */
		final Boolean notifyAddedProperty = preferences.getBoolean("notify_added_as_contact");
		if (notifyAddedProperty != null) {
			confirmationLevel = notifyAddedProperty.booleanValue() ? 2 : 0;
		}

		final Boolean mustApproveContact = preferences.getBoolean("must_approve_contact_request");
		if ((mustApproveContact != null) && mustApproveContact.booleanValue()) {
			confirmationLevel += 1;
		}

		final Boolean parentalFilter = preferences.getBoolean("only_messages_from_friends");
		if (parentalFilter != null) {
			friendList.setParentalFilter(parentalFilter.booleanValue());
		}

		if (confirmationLevel != -1) {
			friendList.setFriendslists_confirmationlevel(confirmationLevel);
		}

		/*
		 * alternative email list
		 */
		if (alternateEmails != null) {
			UserEmailData.setAddressesForUser(user, alternateEmails);
		}

		/*
		 * liste de TTS languages
		 */
		final List<String> TTS_Languages = preferences.getList("spoken_languages");
		if (TTS_Languages != null) {

			for (final TtsLangData currentLang : TtsLangData.findAll(user.getReference())) {

				final String isoLang = currentLang.getLang_iso_code();
				// parcours la liste des languages TTS et retire ce qui ne sont
				// pas dans la nouvelle liste
				// on stocke ceux qui sont déjà bons
				if (!TTS_Languages.contains(isoLang)) {
					user.removeAlternateTTS_Langs(currentLang);
				} else {
					TTS_Languages.remove(isoLang);
				}
			}

			// Ajoute les nouveaux languages
			for (final String isoLang : TTS_Languages) {
				final TtsLangData inTTSData = TtsLangData.findByISOCode(isoLang);
				if ((inTTSData == null) || !inTTSData.isValid()) {
					throw new InvalidParameterException(APIErrorMessage.INVALID_LANGUAGE_CODE, net.violet.common.StringShop.EMPTY_STRING);
				}
				user.addTTS_Language(inTTSData);
			}
		}

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
