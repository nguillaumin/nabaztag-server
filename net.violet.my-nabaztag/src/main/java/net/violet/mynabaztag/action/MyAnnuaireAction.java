package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyAnnuaireForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyAnnuaireAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyAnnuaireForm myForm = (MyAnnuaireForm) form;

		// initilisation des infos depuis la session de l'utilisateur connect
		final HttpSession session = request.getSession(true);

		final Lang languser = SessionTools.getLangFromSession(session, request);

		// save de la lang
		myForm.setLangUser(Long.toString(languser.getId()));

		final String sagemin = SessionTools.getSessionUserpref(session, "annu_agemin");
		final String sagemax = SessionTools.getSessionUserpref(session, "annu_agemax");
		final String sexe = SessionTools.getSessionUserpref(session, "annu_sexe");
		final String fullFriend = SessionTools.getSessionUserpref(session, "annu_fullfriend");
		final String pseudo = SessionTools.getSessionUserpref(session, "annu_pseudo");
		final String ville = SessionTools.getSessionUserpref(session, "annu_ville");
		final String pays = SessionTools.getSessionUserpref(session, "annu_pays");
		final String type_tri = SessionTools.getSessionUserpref(session, "annu_type_tri");
		final String typeTri = SessionTools.getSessionUserpref(session, "annu_typeTri");
		final String page_index = SessionTools.getSessionUserpref(session, "annu_page_index");
		final String nbAffParPage = SessionTools.getSessionUserpref(session, "annu_nbAffParPage");

		if (SessionTools.getSessionUserpref(session, "annu_session").equals("1")) {
			myForm.setSession("OK");
		} else {
			myForm.setSession("KO");
		}

		myForm.setPseudo(pseudo);
		myForm.setVille(ville);
		myForm.setPays(pays);
		myForm.setSexe(sexe);
		myForm.setAgemin(sagemin);
		myForm.setAgemax(sagemax);
		myForm.setFullfriend(fullFriend);

		myForm.setType_tri(type_tri);
		myForm.setTypeTri(typeTri);
		myForm.setPage_index(page_index);
		myForm.setNbAffParPage(nbAffParPage);

		myForm.setListeLastProfil(UserData.recuperationDesDerniersInscrits(12, languser, true));
		myForm.setListeVille(AnnuData.findAllCities());
		myForm.setListePays(CountryData.findAnnuCountries(languser));

		return mapping.getInputForward();
	}
}
