package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyMessagesVocalForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesVocalAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesVocalForm myForm = (MyMessagesVocalForm) form;

		final User user = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		// save de la lang
		myForm.setLangUser(Long.toString(user.getLang().getId()));
		// Récupère un id unique
		myForm.setIdMp3(user.getId() + StringShop.UNDERSCORE + System.currentTimeMillis());
		return mapping.getInputForward();
	}

}
