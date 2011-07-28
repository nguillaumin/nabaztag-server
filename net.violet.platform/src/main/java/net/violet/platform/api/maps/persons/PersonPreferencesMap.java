package net.violet.platform.api.maps.persons;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.FriendsListsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.UserEmailData;

public class PersonPreferencesMap extends AbstractAPIMap {

	public static final String LANGUAGE = "language";

	public PersonPreferencesMap(UserData inUser) {
		super();

		final List<String> addressesList = new ArrayList<String>();
		for (final UserEmailData email : UserEmailData.findByUser(inUser)) {
			addressesList.add(email.getAddress());
		}

		// TODO generer list of langues TTS
		final List<String> TTS_LanguagesList = new ArrayList<String>();
		for (final Lang spokenLang : inUser.getLangs()) {
			TTS_LanguagesList.add(spokenLang.getIsoCode());
		}

		put("emails", addressesList);
		put("timezone", inUser.getTimezone().getTimezone_javaId());

		if (inUser.getUser_24() == 1) {
			put("time_format", "24");
		} else {
			put("time_format", "12");
		}

		put(PersonPreferencesMap.LANGUAGE, inUser.getUserLang().getLang_iso_code());
		put("spoken_languages", TTS_LanguagesList);
		put("newsletter", inUser.isNewsletterSubscriber());

		final FriendsListsData theParameter = FriendsListsData.findByUser(inUser);
		final long level = theParameter.getFriendslists_confirmationlevel();
		final boolean filter = theParameter.getParentalFilter();

		put("notify_added_as_contact", Boolean.valueOf((level == 3) || (level == 2)));
		put("must_approve_contact_request", Boolean.valueOf((level == 3) || (level == 1)));
		put("only_messages_from_friends", filter);

		put("visible", Boolean.valueOf(inUser.getAnnu().getAnnuConfirm() == 1));

		put("notify_played", Boolean.valueOf(inUser.notifyIfPlayed()));

		// TODO modification date (on which table ?)
	}
}
