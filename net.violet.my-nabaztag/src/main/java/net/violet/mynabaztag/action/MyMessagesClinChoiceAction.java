package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesClinChoiceForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesClinChoiceAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesClinChoiceForm myForm = (MyMessagesClinChoiceForm) form;
		final HttpSession session = request.getSession(true);

		final Lang lang = SessionTools.getLangFromSession(session, request);

		if (myForm.getLangClin() == null) { // premier fois, on prend la langue du user
			myForm.setLangClin(String.valueOf(lang.getId()));
		}

		// on recup la liste des clin d'oeil en fonction de la lang selection.

		// on save la liste dans le form.
		myForm.setListeClin(MusicData.findAllClin(Factories.LANG.find(Long.parseLong(myForm.getLangClin())), myForm.getIdClin()));

		// save de la lang
		myForm.setLangUser(String.valueOf(lang.getId()));

		// initialisation de nom du clin oeil
		if (myForm.getNameClin() == null) {
			myForm.setNameClin("-");
		}

		return mapping.getInputForward();
	}

}
