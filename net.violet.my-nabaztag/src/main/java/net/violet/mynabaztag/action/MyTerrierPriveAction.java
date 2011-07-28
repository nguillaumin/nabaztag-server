package net.violet.mynabaztag.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyTerrierPriveForm;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.FriendsListsImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierPriveAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyTerrierPriveAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyTerrierPriveForm myForm = (MyTerrierPriveForm) form;

		final User theUser = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists, i.e. theUser == null
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		FriendsLists friendsLists = FriendsListsImpl.findByUser(theUser);

		try {
			if (friendsLists == null) {
				friendsLists = new FriendsListsImpl(theUser.getId(), (myForm.getMode() == 1) ? myForm.getAuthorisation() + myForm.getNotification() : 0, (myForm.getMode() == 1) ? myForm.getFiltre() : 0, (myForm.getMode() == 1) ? myForm.getAntispam() : 0);
			} else {
				if (myForm.getMode() == 1) {
					friendsLists.setParameters(myForm.getAuthorisation() + myForm.getNotification(), myForm.getFiltre(), myForm.getAntispam());
				}
			}

			myForm.setAuthorisation(friendsLists.getFriendslists_confirmationlevel() & 1);
			myForm.setNotification(friendsLists.getFriendslists_confirmationlevel() & 2);
			myForm.setFiltre(friendsLists.getFriendslists_filter());
			myForm.setAntispam(friendsLists.getFriendslists_antispam());
		} catch (final SQLException e) {
			MyTerrierPriveAction.LOGGER.fatal(e, e);
		}

		return mapping.getInputForward();
	}

}
