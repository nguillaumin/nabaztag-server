package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyProfilForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageReceivedData;
import net.violet.platform.dataobjects.MessageSentData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyProfilAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyProfilAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyProfilForm myForm = (MyProfilForm) form;

		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		final long userlog_id = 0;

		int user_id = 0;
		try {
			user_id = Integer.parseInt(myForm.getUser_id());
		} catch (final NumberFormatException e) {
			MyProfilAction.LOGGER.debug(e, e);
		}

		// header informations
		final User friend = Factories.USER.find(user_id);
		if ((user != null) && (friend != null)) {

			final VObject theUserObject = SessionTools.getRabbitFromSession(session);
			if (null != theUserObject) {
				myForm.setMessagesReceived(MessageReceivedData.findAllReceivedMessagesByObjectFromUser(theUserObject, friend, 3));
			}
			// Whether the user don't have a object
			final List<VObject> friendObjects = Factories.VOBJECT.findByOwner(friend);
			if (!friendObjects.isEmpty()) {
				myForm.setSendMessages(MessageSentData.findAllSentMessagesByUserToObject(friendObjects.get(0), user, 3));
			}
			myForm.setIsFriend(user.existFriend(friend) ? 1 : 0);
		}

		if (user != null) {
			myForm.setNabCast(NabcastData.findAllCreatedByUser(user));
		}

		if (friend != null) {
			myForm.setNabShares(MusicData.findAllNabshareByOwner(friend));
			myForm.setProfil(UserData.getData(friend));
		} else {
			myForm.setNabShares(MusicData.findByStyle(MusicStyle.CATEGORIE_MP3_PERSO));
		}

		// save user logue information
		myForm.setLangUser(Long.toString(lang.getId()));

		/* profil informations */
		myForm.setUserlog_id(Long.toString(userlog_id));
		myForm.setUser_id(Integer.toString(user_id));

		return mapping.getInputForward();
	}
}
