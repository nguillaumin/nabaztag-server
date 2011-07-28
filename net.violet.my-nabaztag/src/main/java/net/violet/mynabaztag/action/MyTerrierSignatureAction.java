package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyTerrierSignatureForm;
import net.violet.platform.datamodel.User;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierSignatureAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyTerrierSignatureForm myForm = (MyTerrierSignatureForm) form;

		final User theUser = SessionTools.getUserFromSession(request);
		boolean reloadSignature = false;
		/**
		 * Check if the user really exists, i.e. theUser != null
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		// Changement de signature
		if (myForm.getMode() == 3) {
			String userColorSign = myForm.getUserColorSign();
			if (userColorSign.equals(StringShop.EMPTY_STRING)) {
				userColorSign = "ff0000";
			}
			theUser.setSignatureInformation(myForm.getUserColor(), userColorSign, myForm.getUserMusic());
			reloadSignature = true;
		}

		if (theUser.getColorSign() == null) {
			myForm.setUserColorSign("FF0000");
		} else {
			myForm.setUserColorSign(theUser.getColorSign());
		}

		// recuperation liste des musiques
		myForm.setMusicList(MusicData.findAllByUser(theUser));

		// recuperation liste des animations
		myForm.setAnimList(AnimData.findAllByLangOrderByName(theUser.getLang()));

		if (theUser.getColor() == null) {
			myForm.setUserColor(0);
		} else {
			myForm.setUserColor(theUser.getColor().getId().intValue());
		}

		if (theUser.getMusic() == null) {
			myForm.setUserMusic(0);
		} else {
			myForm.setUserMusic(theUser.getMusic().getId().intValue());
		}

		myForm.setUser_signature(theUser.getSignature(reloadSignature));

		return mapping.getInputForward();
	}
}
