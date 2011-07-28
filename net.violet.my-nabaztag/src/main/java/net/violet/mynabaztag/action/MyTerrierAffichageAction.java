package net.violet.mynabaztag.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyTerrierAffichageForm;
import net.violet.mynabaztag.services.UserPrefsServices;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.AnnuImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPrefs;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierAffichageAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyTerrierAffichageAction.class);

	@Override
	protected ActionForward doExecute(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		Lang langUser = SessionTools.getLangFromSession(session, request);
		final User user = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists, i.e. theUser != null
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		final MyTerrierAffichageForm myForm = (MyTerrierAffichageForm) form;
		String layout = "layout_green";

		// mode == 1 -> user asked for an update
		if (myForm.getMode() == 1) {

			// Changes main language
			final long newLangUser = Long.parseLong(myForm.getLangUser());
			langUser = Factories.LANG.find(newLangUser);
			SessionTools.setLangInSession(session, langUser);
			user.setLang(langUser);

			Annu annu = user.getAnnu();
			if (annu == null) {
				// Creation de l'annuaire
				try {
					annu = new AnnuImpl(user, StringShop.EMPTY_STRING, langUser);
				} catch (final SQLException e) {
					MyTerrierAffichageAction.LOGGER.fatal(e, e);
				}
			} else {
				annu.setLangPreferences(Factories.LANG.getParentByIsocode(langUser.getIsoCode()));
			}

			final List<VObject> theObjects = Factories.VOBJECT.findByOwner(user);
			for (final VObject theObject : theObjects) {
				final ObjectPreferences theObjectPreferences = theObject.getPreferences();
				if (theObjectPreferences == null) {
					Factories.VOBJECT.createObjectPreferences(theObject, langUser);
				} else {
					theObjectPreferences.setLangPreferences(langUser);
				}
			}

			// Changes spoken languages
			// TODO peut etre un peu inutilement complique cet algo, a voir
			final Set<Lang> tempList = new HashSet<Lang>();
			tempList.add(langUser);
			tempList.addAll(Factories.LANG.getCollectionLangFromIdsArray(myForm.getCheckListLang()));

			final List<Lang> temp = user.getLangs();
			final List<Lang> delete = new ArrayList<Lang>();
			final List<Lang> add = new ArrayList<Lang>(tempList);
			final List<List<Lang>> update = new ArrayList<List<Lang>>();

			add.removeAll(temp);
			for (final Lang langIdToDelete : temp) {
				if (!tempList.contains(langIdToDelete)) {
					delete.add(langIdToDelete);
				}
			}
			if ((delete.size() != 0) && (add.size() != 0)) {
				if (delete.size() > add.size()) {
					while (!add.isEmpty()) {
						final List<Lang> subList = new ArrayList<Lang>();
						subList.add(delete.remove(0));
						subList.add(add.remove(0));
						update.add(subList);
					}
				} else {
					while (!delete.isEmpty()) {
						final List<Lang> subList = new ArrayList<Lang>();
						subList.add(delete.remove(0));
						subList.add(add.remove(0));
						update.add(subList);
					}
				}
			}

			user.updateUserSpokenLanguages(delete, add, update);

			// changes layout
			layout = myForm.getLayout();
			UserPrefsServices.updateUserPrefs(user, layout);
			// TODO a virer, pareil que pour spklang
			session.setAttribute("userprefs_layout", layout);
			SessionTools.createCookie(response, "userprefs_layout", layout);
		}

		// Prints the page
		final UserPrefs uPrefs = Factories.USER_PREFS.find(user.getId().intValue());
		final Map<String, String> userPrefs = (uPrefs == null) ? new TreeMap<String, String>() : uPrefs.getUserPrefs();
		if (!userPrefs.isEmpty()) {
			layout = userPrefs.get("userprefs_layout");
		}
		myForm.setLayout(layout);

		myForm.setLangUser(Long.toString(langUser.getId().intValue()));

		final Vector<String> spkLang = new Vector<String>();
		for (final Lang l : user.getLangs()) {
			spkLang.add(Long.toString(l.getId().intValue()));
		}
		myForm.setCheckListLang(spkLang.toArray(new String[0]));
		myForm.setLangList(TtsLangData.getAllTtsLanguages());

		return mapping.getInputForward();
	}

}
