package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNabaztalandHistoForm;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.NabcastValData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MyNabaztalandHistoAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);
		final VObject object = SessionTools.getRabbitFromSession(session);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		final MyNabaztalandHistoForm myForm = (MyNabaztalandHistoForm) form;

		myForm.setUser_id(user.getId().intValue());
		if (object != null) {
			myForm.setUser_main(object.getId().intValue());
		}

		final int idNabcast = myForm.getIdNabcast();
		final Nabcast theNabcast = NabcastImpl.find(idNabcast);
		if (theNabcast == null) {
			return mapping.findForward("home");
		}

		myForm.setNabcastData(NabcastData.find(idNabcast));

		myForm.setListeNabcastval(NabcastValData.findSongsNabcast(theNabcast, 0, 0, 0, 1, user));

		return mapping.findForward("histo");
	}
}
