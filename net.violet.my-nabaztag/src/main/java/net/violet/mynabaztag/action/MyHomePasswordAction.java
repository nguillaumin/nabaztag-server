package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyHomePasswordForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPwd;
import net.violet.platform.datamodel.UserPwdImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.Templates;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MyHomePasswordAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyHomePasswordForm myForm = (MyHomePasswordForm) form;

		final int mode = myForm.getMode();
		final String theLogin = myForm.getUser_login();
		final String theEmail = myForm.getUser_mail();

		int ok = -1;
		if (mode == 1) {
			final User theUser = Factories.USER.findByEmail(theEmail);
			if (theUser != null) {
				final String theUserPwd = theUser.getPassword();
				final UserPwd thePwd = UserPwdImpl.findByPseudoAndUser(theLogin, theUser);
				if (thePwd != null) {
					thePwd.setPwd(theUserPwd);
					Templates.returnIdentifying(UserData.getData(theUser));
					ok = 1;
				}
			}
		}

		myForm.setMode(mode);
		myForm.setOk(ok);

		return mapping.getInputForward();
	}
}
