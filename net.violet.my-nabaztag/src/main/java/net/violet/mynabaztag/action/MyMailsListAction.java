package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMailsListForm;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.MailUserData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMailsListAction extends ActionWithLog {

	// déclaration des variables
	static final int LIMITK = 3;
	static final int LIMITA = 1;

	/**
	 * Logger.
	 */
	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMailsListForm myForm = (MyMailsListForm) form;
		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		/**
		 * Check if the user really exists
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		if (theObject == null) {
			return mapping.findForward("login");
		}

		final ApplicationData mail = ApplicationData.getData(Application.NativeApplication.MAIL.getApplication());

		myForm.setServiceName(mail.getProfile().getTitle());

		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MAIL.getApplication(), theObject);

		// on verifie si l'utilisateur est enregistré
		final int isReg = (subscriptionsList.isEmpty()) ? 0 : 1;

		myForm.setMailsAccounts(MailUserData.FindAllUserMailAccountsInfo(subscriptionsList));

		myForm.setIsReg(isReg);

		return mapping.getInputForward();
	}
}
