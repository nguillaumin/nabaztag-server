package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyNbReceivedMessagesForm;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.MessageReceivedData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyNbReceivedMessagesAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyNbReceivedMessagesForm myForm = (MyNbReceivedMessagesForm) form;

		final User user = SessionTools.getUserFromSession(request);
		final VObject rabbit = SessionTools.getRabbitFromSession(request.getSession(true));

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		myForm.setUserData(UserData.getData(user));

		final long nbMessages = MessageReceivedData.countReceivedMessagesByVObject(rabbit, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, true);
		myForm.setAffNbMessages(Long.toString(nbMessages));

		return mapping.getInputForward();
	}

}
