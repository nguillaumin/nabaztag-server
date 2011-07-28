package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyToolsForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyToolsAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyToolsForm myForm = (MyToolsForm) form;

		final User user = SessionTools.getUserFromSession(request);

		if (user != null) {
			myForm.setUserData(UserData.getData(user));
		} else {
			myForm.setUserData(UserData.getData(null));
		}

		return mapping.getInputForward();
	}
}
