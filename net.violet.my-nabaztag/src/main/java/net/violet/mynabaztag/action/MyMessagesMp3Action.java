package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesMp3Form;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesMp3Action extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesMp3Form myForm = (MyMessagesMp3Form) form;
		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		final int idMp3 = 0;

		myForm.setListeMp3(MusicData.findAllPersoByUser(user));
		myForm.setLangUser(Long.toString(lang.getId()));
		myForm.setIdMp3(idMp3);

		return mapping.getInputForward();
	}

}
