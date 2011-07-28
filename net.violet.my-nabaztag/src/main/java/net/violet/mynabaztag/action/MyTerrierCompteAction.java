package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyTerrierCompteForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierCompteAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyTerrierCompteForm myForm = (MyTerrierCompteForm) form;

		final User theUser = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists, i.e. theUser != null
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		final VObject theObject = SessionTools.getRabbitFromSession(request.getSession(true));

		if (theObject != null) {
			myForm.setPseudo(theObject.getObject_login());
			myForm.setMacAddress(theObject.getObject_serial());
		}

		myForm.setEmail(theUser.getUser_email());

		final int isConnected;
		if (theObject == null) {

			isConnected = 0;

		} else {
			isConnected = theObject.isConnected() ? 1 : 0;
		}

		myForm.setIsConnected(isConnected);

		return mapping.getInputForward();
	}

}
