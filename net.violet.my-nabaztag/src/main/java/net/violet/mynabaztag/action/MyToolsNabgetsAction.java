package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyToolsNabgetsForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyToolsNabgetsAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyToolsNabgetsForm myForm = (MyToolsNabgetsForm) form;
		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);
		final String languser = Long.toString(SessionTools.getLangFromSession(session, request).getId());

		if (theUser != null) {
			myForm.setUserData(UserData.getData(theUser));
		} else {
			myForm.setUserData(UserData.getData(null));
		}

		myForm.setLangUser(languser);
		if (theUser != null) {
			myForm.setUserId(theUser.getId().intValue());
		} else {
			myForm.setUserId(0);
		}

		return mapping.getInputForward();
	}
}
