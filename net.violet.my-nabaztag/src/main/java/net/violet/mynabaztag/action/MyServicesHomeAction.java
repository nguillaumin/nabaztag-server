package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyServicesHomeForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MotherTongueLangData;
import net.violet.platform.dataobjects.NabcastCategData;
import net.violet.platform.dataobjects.NablifeServicesData;
import net.violet.platform.dataobjects.SrvCategData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MyServicesHomeAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyServicesHomeForm myForm = (MyServicesHomeForm) form;
		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);

		final VObject rabbit = SessionTools.getRabbitFromSession(session);
		final Lang langUser = SessionTools.getLangFromSession(session, request);

		int idCateg = 0;
		final int sizeListNabcast = 0;

		Lang langCategorie;
		if (myForm.getLangCategorie() == 0) {
			langCategorie = langUser;
		} else {
			langCategorie = Factories.LANG.find(myForm.getLangCategorie());
		}

		if (myForm.getLangList().size() == 0) {
			myForm.setLangList(MotherTongueLangData.findAllMotherTongue());
		}

		if (user != null) {
			myForm.setUser_id(user.getId());
		} else {
			myForm.setUser_id(0);
		}

		if (rabbit == null) {
			myForm.setUser_main(0);
		} else {
			myForm.setUser_main(rabbit.getId());
		}

		myForm.setLanguser(Long.toString(langUser.getId()));
		// FIN DE SESSION

		idCateg = myForm.getIdCateg();

		myForm.setListeCategorie(SrvCategData.findAllDistinctByLang(langCategorie));
		myForm.setListeNabcastCateg(NabcastCategData.findAllNabcastCategByLang(langCategorie, false));

		// Affichage des services propres a une categorie
		int nbAffParPage = myForm.getNbAffParPage();
		nbAffParPage = sizeListNabcast;
		myForm.setIdCateg(idCateg);

		if (idCateg > 0) {

			myForm.setListServices(NablifeServicesData.findListServicesByCateg(idCateg, 0, nbAffParPage, rabbit, langCategorie));
			myForm.setNameCateg(SrvCategData.find(idCateg, langCategorie).getName());

		} else {
			// On affiche la liste des services
			myForm.setListServices(NablifeServicesData.findAllNablifeFrontPageByLang(rabbit, langCategorie));
		}

		myForm.setIdCateg(idCateg);
		myForm.setLangCategorie(langCategorie.getId());
		return mapping.getInputForward();
	}
}
