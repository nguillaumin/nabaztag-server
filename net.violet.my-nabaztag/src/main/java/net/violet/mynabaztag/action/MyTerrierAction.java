package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyTerrierForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SrvDialogData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyTerrierForm myForm = (MyTerrierForm) form;
		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);
		final VObject rabbit = SessionTools.getRabbitFromSession(session);

		final int user_id = (user == null) ? 0 : (int) user.getId().intValue();

		final String languser = Long.toString(SessionTools.getLangFromSession(session, request).getId().intValue());

		// liste des mp3 du user
		if (user_id > 0) {
			if (SrvDialogData.hasSomethingToDisplay(rabbit) > 0) {
				myForm.setPopup("popup");
			}
		}

		final User theUser = Factories.USER.find(user_id);
		UserData userData;
		if (theUser != null) {
			userData = UserData.getData(theUser);
		} else {
			userData = UserData.getData(null);
		}

		myForm.setUserData(userData);
		myForm.setLangUser(languser);
		myForm.setUserId(user_id);

		return mapping.getInputForward();
	}

}
