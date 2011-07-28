package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesClinForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MotherTongueLangData;
import net.violet.platform.dataobjects.MusicStyleData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesClinAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesClinForm myForm = (MyMessagesClinForm) form;
		final HttpSession session = request.getSession(true);

		final Lang lang = SessionTools.getLangFromSession(session, request);

		if (myForm.getLangClin().equals(StringShop.EMPTY_STRING)) {
			myForm.setLangClin(Long.toString(lang.getId()));
		}

		// on recup la liste des clin d'oeil en fonction de la lang selection.
		// on save la liste dans le form.
		myForm.setListeCatClin(MusicStyleData.findAllClinByLang(Factories.LANG.find(Long.parseLong(myForm.getLangClin()))));

		// save de la lang
		myForm.setLangUser(Long.toString(lang.getId()));

		myForm.setLangList(MotherTongueLangData.findAllMotherTongue());

		// initialisation de nom du clin oeil
		if (myForm.getNameClin().equals(StringShop.EMPTY_STRING)) {
			myForm.setNameClin("-");
		}

		return mapping.getInputForward();
	}

}
