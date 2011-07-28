package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesBiblioChoiceForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesBiblioChoiceAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesBiblioChoiceForm myForm = (MyMessagesBiblioChoiceForm) form;
		final HttpSession session = request.getSession(true);

		final Lang lang = SessionTools.getLangFromSession(session, request);

		if (myForm.getLangBiblio() == null) {
			myForm.setLangBiblio(Long.toString(lang.getId()));
		}

		// on recup la liste des Biblio d'oeil en fonction de la lang selection.
		// on save la liste dans le form.
		myForm.setListeBiblio(MusicData.findAllForBiblio(myForm.getIdBiblio()));

		// save de la lang
		myForm.setLangUser(Long.toString(lang.getId()));

		// initialisation de nom du Biblio oeil
		if (myForm.getNameBiblio() == null) {
			myForm.setNameBiblio("-");
		}

		return mapping.getInputForward();
	}

}
