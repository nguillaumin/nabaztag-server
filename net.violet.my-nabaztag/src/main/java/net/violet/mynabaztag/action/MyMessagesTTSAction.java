package net.violet.mynabaztag.action;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesTTSForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.dataobjects.VoiceData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesTTSAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final MyMessagesTTSForm myForm = (MyMessagesTTSForm) form;

		final User theUser = SessionTools.getUserFromSession(request);
		/**
		 * Check if the user really exists
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		final List<Lang> theLangs = new LinkedList<Lang>(theUser.getLangs());
		// On met la langue de la session au début.
		final Lang theSessionLang = SessionTools.getLangFromSession(session, request);
		theLangs.remove(theSessionLang);
		theLangs.add(0, theSessionLang);

		int idVoice = 1;

		// chargement de la liste des voix
		final List<VoiceData> listeVoix = VoiceData.getVoiceList(theLangs);

		if (!listeVoix.isEmpty()) {
			final VoiceData vD = listeVoix.get(0);
			idVoice = (int) vD.getIdVoix();
		}
		myForm.setListeVoix(listeVoix);
		myForm.setLangUser(Long.toString(theSessionLang.getId()));
		myForm.setIdVoice(idVoice);

		return mapping.getInputForward();
	}

}
