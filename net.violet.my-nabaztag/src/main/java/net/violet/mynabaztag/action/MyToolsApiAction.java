package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyToolsApiForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyToolsApiAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyToolsApiForm myForm = (MyToolsApiForm) form;

		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(request.getSession(true));
		String checkAPI = myForm.getCheckAPI();

		/**
		 * Checks if the user really exists, i.e. theUser != null
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		/**
		 * Checks if the user has a rabbit
		 */
		if (theObject == null) {
			return mapping.getInputForward();
		}

		final String numSerie = theObject.getObject_serial();
		/**
		 * Checks if the serial is well known
		 */
		if ((numSerie == null) || numSerie.equals(StringShop.EMPTY_STRING)) {
			return mapping.getInputForward();
		}
		/**
		 * Sets the value of checkAPI
		 */
		if ((checkAPI == null) || checkAPI.equals(StringShop.EMPTY_STRING)) {
			checkAPI = "0";
		}

		long user_extconnect = theUser.getUser_extconnect();
		if (myForm.getMode() == 1) {
			if ((user_extconnect <= 0) && checkAPI.equals("1")) {
				// on génère la clef
				final long val = CCalendar.getCurrentTimeInSecond() + ConvertTools.randCodeInt();
				theUser.setExtConnect(val);
				user_extconnect = val;
			}
			if ((user_extconnect > 0) && checkAPI.equals("0")) {
				theUser.setExtConnect(0);
				user_extconnect = 0;
			}
		}

		if (user_extconnect > 0) {
			checkAPI = "1";
		} else {
			checkAPI = "0";
		}

		myForm.setUserData(UserData.getData(theUser));
		myForm.setNumSerie(numSerie);
		myForm.setCheckAPI(checkAPI);
		myForm.setExtConnect(user_extconnect);

		return mapping.getInputForward();
	}
}
