package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesNabshareForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.CategData;
import net.violet.platform.dataobjects.TagData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesNabshareAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesNabshareForm myForm = (MyMessagesNabshareForm) form;

		final HttpSession session = request.getSession(true);

		final Lang lang = SessionTools.getLangFromSession(session, request);

		if (lang == null) {
			return mapping.findForward("login");
		}

		myForm.setLangUser(Long.toString(lang.getId()));
		// on rcupre la liste des catgories
		myForm.setListeCateg(CategData.findAllInLang(lang));
		// gnre la liste du nuage de mots (word cloud)
		myForm.setListeWordCloud(TagData.findByLang(lang, 30));

		return mapping.getInputForward();
	}

}
