package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyTerrierBlackListForm;
import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.BlackData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierBlackListAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final MyTerrierBlackListForm myForm = (MyTerrierBlackListForm) form;

		final User user = SessionTools.getUserFromSession(request);

		/**
		 * Checks if the user really exists, i.e. userid > 0
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		if (myForm.getCheckBlackList() != null) {
			for (final String s : myForm.getCheckBlackList()) {
				final User blacklistedUser = Factories.USER.find(Long.parseLong(s));
				if ((blacklistedUser != null) && user.getBlackList().containsKey(blacklistedUser)) {
					user.getBlackList().remove(blacklistedUser);
				}
			}
		}

		final List<BlackData> blacklist = new ArrayList<BlackData>();

		for (final User blacklistedUser : user.getBlackList().keySet()) {
			final Black b = user.getBlackList().get(blacklistedUser);
			blacklist.add(new BlackData(blacklistedUser, b.getComment()));
		}

		myForm.setListBlack(blacklist);

		return mapping.getInputForward();
	}
}
