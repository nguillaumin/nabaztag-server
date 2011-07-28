package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyUserPrefsForm;
import net.violet.mynabaztag.services.UserPrefsServices;
import net.violet.platform.datamodel.User;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyUserPrefsAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {

		// initialisation des objets
		final MyUserPrefsForm myForm = (MyUserPrefsForm) form;
		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);

		String mode = StringShop.EMPTY_STRING;
		String output = StringShop.EMPTY_STRING;
		String champ = StringShop.EMPTY_STRING;
		String value = StringShop.EMPTY_STRING;
		int good = 0;

		if (theUser != null) {
			mode = myForm.getMode();
			champ = myForm.getChamp();
			value = myForm.getSetValue();

			if (mode.equals("load")) {

				String json = "{\"prefs\":{";
				boolean findLayout = false;

				// look in session
				final List champUserPrefs = UserPrefsServices.getChampUserPrefs();
				for (int y = 0; y < champUserPrefs.size(); y++) {
					final String champtmp = champUserPrefs.get(y).toString();
					final String valuetmp = SessionTools.getSessionUserpref(session, champtmp);
					if (!valuetmp.equals(StringShop.EMPTY_STRING)) {
						json += "\"" + champtmp + "\":\"" + valuetmp + "\",";
						findLayout = true;
					}
				}

				// If the session is empty, we look in cookies
				if (!findLayout) {

					if (!SessionTools.getCookies(request).isEmpty()) {
						for (int y = 0; y < champUserPrefs.size(); y++) {
							final String champtmp = champUserPrefs.get(y).toString();
							String champPref = StringShop.EMPTY_STRING;
							final String cookie = SessionTools.getCookieByName(request, champtmp);
							if (null != cookie) {
								champPref = cookie.trim();
								if (!champPref.equals(StringShop.EMPTY_STRING)) {
									json += "\"" + champtmp + "\":\"" + champPref + "\",";
									findLayout = true;
								}
							}
						}
					}
				}

				// If the user preferences weren't find; well, just take a look
				// in the database
				if (!findLayout) {
					output = UserPrefsServices.getJSONPrefs(theUser, response, session);
				} else {
					json = json.substring(0, json.length() - 1); // retire le , de fin
					json += "}}";
					output = json;
				}

			} else if (mode.equals("update")) {
				good = UserPrefsServices.updateUserPrefs(theUser, value);
				if (good < 0) {
					output = "NOTOK";
				} else {
					session.setAttribute(champ, value); // maj de la session
					SessionTools.createCookie(response, champ, value);
					output = "OK";
				}
			}
		}

		myForm.setOutput(output);

		return mapping.getInputForward();
	}
}
