package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesForm myForm = (MyMessagesForm) form;
		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		final List<UserData> listeAmisForm = new ArrayList<UserData>();
		for (final User ami : user.getFriendsWithObject()) {
			listeAmisForm.add(UserData.getData(ami));
		}

		if (myForm.getOnglet().equals(null)) {
			myForm.setOnglet(StringShop.EMPTY_STRING);
		} else {
			myForm.setOnglet(myForm.getOnglet());
		}

		SessionTools.setSessionUserPrefs(session, "page", "messages"); //request. getSession (). setAttribute ( "page" , "messages"
		// );
		myForm.setLangUser(Long.toString(lang.getId()));
		myForm.setUserId(user.getId());
		myForm.setListeAmis(listeAmisForm);

		if ((request.getParameter("shuffle") != null) && (new Integer(request.getParameter("shuffle")).intValue() == 1)) {
			final VObject obj = Factories.VOBJECT.findRandomObject();
			// On récupère le pseudo (ou nom) de cet object (utilisateur)
			// String pseudo = objectServices.getPseudoObj(idObject);
			final String pseudo = obj.getObject_login();
			myForm.setPseudo(pseudo);
			return mapping.findForward("shuffle");
		}

		myForm.setUserData(UserData.getData(user));
		myForm.setUserId(user.getId());

		if (request.getParameter("nabname") != null) {
			final String pseudo = request.getParameter("nabname");
			myForm.setPseudo(pseudo);
		}

		return mapping.getInputForward();
	}
}
