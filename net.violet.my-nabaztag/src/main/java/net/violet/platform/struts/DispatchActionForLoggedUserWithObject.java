package net.violet.platform.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class DispatchActionForLoggedUserWithObject extends DispatchActionWithLog {

	/**
	 * Méthode execute. Si l'utilisateur n'est pas connecté, renvoie vers la
	 * page de login.
	 */
	@Override
	protected final ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		final ActionForward theResult;
		final User user = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			theResult = mapping.findForward("login");
		} else {
			final HttpSession session = request.getSession(true);
			final VObject object = SessionTools.getRabbitFromSession(session);
			/**
			 * Check if the user really has a rabbit
			 */
			if (object == null) {
				theResult = mapping.findForward("login");
			} else {
				theResult = dispatchExecute(mapping, form, request, response);
			}
		}

		return theResult;
	}
}
