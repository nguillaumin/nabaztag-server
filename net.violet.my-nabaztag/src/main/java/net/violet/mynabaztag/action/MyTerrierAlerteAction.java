package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyTerrierAlerteForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierAlerteAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyTerrierAlerteForm myForm = (MyTerrierAlerteForm) form;

		final User theUser = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists, i.e. theUser != null
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		if (myForm.getMode() == 1) {
			theUser.setMessageParameters(myForm.getMsgSent(), myForm.getMsgPlayed(), myForm.getMsgReceived(), myForm.getNewsletter());
		}

		myForm.setMsgSent((int) theUser.getUser_authmsg());
		myForm.setMsgPlayed(theUser.getNotifyMessagePlayed() ? 1 : 0);
		myForm.setMsgReceived(theUser.getNotifyMessageReceived() ? 1 : 0);
		myForm.setNewsletter(theUser.getUser_newsletter());

		return mapping.getInputForward();
	}

}
