package net.violet.mynabaztag.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPrefs;
import net.violet.platform.datamodel.UserPrefsImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.SessionTools;

import org.apache.log4j.Logger;

public class UserPrefsServices {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(UserPrefsServices.class);

	// rajouter ici le nouveau champ
	public static final List getChampUserPrefs() {
		final List<String> champUserPrefs = new ArrayList<String>();
		champUserPrefs.add(UserPrefs.USER_PREFS_LAYOUT);
		return champUserPrefs;
	}

	public static String getJSONPrefs(User inUser, HttpServletResponse response, HttpSession session) {
		String json = "{\"prefs\":{";

		if (inUser != null) { // si userid

			final UserPrefs uPrefs = Factories.USER_PREFS.find(inUser.getId());

			final Map<String, String> prefs = (uPrefs == null) ? new HashMap<String, String>() : uPrefs.getUserPrefs();

			if (!prefs.isEmpty()) {
				int boucleok = 0;

				for (final String namekey : prefs.keySet()) {
					json += "\"" + namekey + "\":\"" + prefs.get(namekey) + "\",";
					session.setAttribute(namekey, prefs.get(namekey).toString()); // maj de la session
					SessionTools.createCookie(response, "userprefs_layout", prefs.get(namekey).toString());
					boucleok = 1;
				}
				if (boucleok == 1) {
					json = json.substring(0, json.length() - 1); // retire le , de fin
				}

			}
		}

		json += "}}";
		return json;
	}

	public static int updateUserPrefs(User inUser, String value) {
		int ok = 0;
		if (inUser != null) { // si userid

			UserPrefs theUserPrefs = Factories.USER_PREFS.find(inUser.getId());

			if (theUserPrefs == null) { // si existe pas insert dans les preférences
				try {
					theUserPrefs = new UserPrefsImpl(inUser, value);
				} catch (final SQLException se) {
					UserPrefsServices.LOGGER.fatal(se, se);
				}
			} else {
				theUserPrefs.setLayout(value);
				ok = 1;
			}
		}

		return ok;
	}

}
