package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesBiblioForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicStyleData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesBiblioAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesBiblioForm myForm = (MyMessagesBiblioForm) form;
		final HttpSession session = request.getSession(true);

		final Lang lang = SessionTools.getLangFromSession(session, request);

		if (myForm.getLangBiblio().equals(StringShop.EMPTY_STRING)) {
			myForm.setLangBiblio(Long.toString(lang.getId()));
		}

		// on recup la liste des Biblio d'oeil en fonction de la lang selection.
		// on save la liste dans le form.
		myForm.setListeCatBiblio(MusicStyleData.findAllCategForBiblio(Factories.LANG.find(Long.parseLong(myForm.getLangBiblio()))));

		// save de la lang
		myForm.setLangUser(Long.toString(lang.getId()));

		// initialisation de nom du Biblio oeil
		if (myForm.getNameBiblio() == null) {
			myForm.setNameBiblio("-");
		}

		return mapping.getInputForward();
	}

}
