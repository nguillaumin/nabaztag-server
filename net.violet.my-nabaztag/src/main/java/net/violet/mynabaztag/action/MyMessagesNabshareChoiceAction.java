package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesNabshareChoiceForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesNabshareChoiceAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesNabshareChoiceForm myForm = (MyMessagesNabshareChoiceForm) form;
		final HttpSession session = request.getSession(true);

		final Lang lang = SessionTools.getLangFromSession(session, request);
		final int idCatag = myForm.getIdCateg();
		final String tag = myForm.getTag();

		final List<MusicData> listeCateg = new ArrayList<MusicData>();

		if (idCatag > 0) {
			// on rcupre les musiques de la catgorie par rapport a la catgorie
			listeCateg.addAll(MusicData.findByCategAndLang(idCatag, lang));
		} else if (tag != null) {
			// on rcupre les musiques de la catgorie par rapport a un tag
			listeCateg.addAll(MusicData.findByTagAndLang(tag, lang));
		}

		// on recup la liste des clin d'oeil en fonction de la lang selection.
		// on save la liste dans le form.
		myForm.setListeNabshareRand(MusicData.findNabshareRand(lang));

		myForm.setLangUser(Long.toString(lang.getId()));
		myForm.setListeCateg(listeCateg);

		if ((request.getParameter("shuffle") != null) && (new Integer(request.getParameter("shuffle")).intValue() == 1)) {
			return mapping.findForward("shuffleNabshare");
		}

		return mapping.getInputForward();
	}

}
