package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyAddFriendForm;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.DispatchActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyAddFriendAction extends DispatchActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final User user = SessionTools.getUserFromSession(request);

		final MyAddFriendForm myForm = (MyAddFriendForm) form;

		final User friend = Factories.USER.find(myForm.getFriend_id());
		if (friend == null) {
			return mapping.getInputForward();
		}

		int mode = MyTerrierFriendsAction.FL_ERROR;

		if (user.existFriend(friend)) {
			mode = MyTerrierFriendsAction.ALREADY_IN_FL;
		}

		if (mode != MyTerrierFriendsAction.ALREADY_IN_FL) {
			for (final Contact aContact : Factories.CONTACT.findAllSentContactRequest(user, 0, 0)) {
				if (aContact.getPerson().equals(user)) {
					mode = MyTerrierFriendsAction.ALREADY_IN_FL;
					break;
				}
			}
			if (mode != MyTerrierFriendsAction.ALREADY_IN_FL) {
				mode = MyTerrierFriendsAction.addContact(user, friend);
			}
		}

		myForm.setMode(mode);
		myForm.setProfil(UserData.find(myForm.getFriend_id()));
		myForm.setFriend_id(friend.getId().intValue());

		return mapping.findForward("success_add_friend");
	}

}
