package net.violet.mynabaztag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyAnnuaireResultForm;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyAnnuaireResultAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyAnnuaireResultAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyAnnuaireResultForm myForm = (MyAnnuaireResultForm) form;

		// initilisation des infos depuis la session de l'utilisateur connect
		final HttpSession session = request.getSession(true);

		final String languser = Long.toString(SessionTools.getLangFromSession(session, request).getId());

		// save de la lang
		myForm.setLangUser(languser);

		// rcupration des premires informations gnriques
		String sagemin = myForm.getAgemin();
		String sagemax = myForm.getAgemax();
		String sexe = myForm.getSexe();
		final String pseudo = myForm.getPseudo();
		final String ville = myForm.getVille();
		final String pays = myForm.getPays();
		String typeTri = myForm.getTypeTri();

		if (typeTri == null) {
			typeTri = StringShop.EMPTY_STRING;
		}

		int nbAffParPage = myForm.getNbAffParPage();
		if (nbAffParPage == 0) {
			nbAffParPage = 6;
		}

		/* FORMATAGE DES INFORMATION DE RECHERCHE */

		// si sagemin et sagemax ne sont pas rensigns, on les initialise "-1"
		if ((sagemin != null) && sagemin.trim().equals(StringShop.EMPTY_STRING)) {
			sagemin = "-1";
		}

		if ((sagemax != null) && sagemax.trim().equals(StringShop.EMPTY_STRING)) {
			sagemax = "-1";
		}

		// convertion en int des string sagemin et sagemax vers agemin et agemax
		int agemin = 0;
		try {
			agemin = Integer.parseInt(sagemin);
		} catch (final NumberFormatException e) {
			MyAnnuaireResultAction.LOGGER.debug("MyAnnuaireResultAction" + e);
		}

		int agemax = 0;
		try {
			agemax = Integer.parseInt(sagemax);
		} catch (final NumberFormatException e) {
			MyAnnuaireResultAction.LOGGER.debug("MyAnnuaireResultAction" + e);
		}

		if ((sexe != null) && sexe.trim().equals(StringShop.EMPTY_STRING)) {
			sexe = StringShop.EMPTY_STRING;
		}

		myForm.setSexe(sexe);

		// gestion du tri
		final String getTri = "0";
		int tri = 0;

		if (!myForm.getType_tri().trim().equals(StringShop.EMPTY_STRING)) {
			try {
				tri = Integer.parseInt(getTri);
			} catch (final NumberFormatException e) {
				MyAnnuaireResultAction.LOGGER.debug("MyAnnuaireResultAction" + e);
				tri = 0;
			}
		}

		/* FIN FORMATAGE DES INFORMATION DE RECHERCHE */

		// recupration du nombre de profils correspondant la recherche trouves
		final List<UserData> profils = UserData.searchUsers(tri, typeTri, pseudo, agemin, agemax, sexe, ville, pays);

		final int nombre_profils = profils.size();

		// calcul du nombre de pages totales
		int nb_pages = 1;

		if (nombre_profils > nbAffParPage) {
			nb_pages = nombre_profils / nbAffParPage;
			final float nb_pages_tmp = (float) nombre_profils / nbAffParPage;

			if (nb_pages_tmp > (int) nb_pages_tmp) {
				nb_pages++;
			}
		}
		myForm.setNombre_pages(nb_pages);

		// calcul index chagement de page
		int indexD = myForm.getPage_indexD();
		int indexMM = myForm.getPage_indexMM();
		int indexM = myForm.getPage_indexM();
		int index = myForm.getPage_index();
		int indexP = myForm.getPage_indexP();
		int indexPP = myForm.getPage_indexPP();
		int indexF = myForm.getPage_indexF();
		final int indexNew = myForm.getPage_new();
		if (indexNew == 1) {
			index = 0;
			myForm.setPage_new(0);
		}

		if (indexD == 0) {
			indexD = 1;
		}
		if (indexF == 0) {
			indexF = (nbAffParPage * (nb_pages - 1)) + 1;
		}

		// index = index + indexNew;
		if ((index == 0) || (index == 1)) {
			index = 1;
			indexMM = 0;
			indexM = 0;
		}
		if (index == nbAffParPage + 1) {
			indexMM = 0;
			indexM = 1;
		}

		if (nb_pages == 1) {
			indexP = 0;
			indexPP = 0;
		} else if (nb_pages == nbAffParPage + 1) {
			indexP = nbAffParPage + 1;
			indexPP = 0;
		}

		/* ON CLACUL LES INDEX */
		if (nb_pages > 1) {
			indexP = index + nbAffParPage;
			if (index >= nbAffParPage) {
				indexM = index - nbAffParPage;
			}
		}
		if (nb_pages > 2) {
			indexPP = index + 2 * nbAffParPage;
			if (index >= 2 * nbAffParPage) {
				indexMM = index - 2 * nbAffParPage;
			}
		}

		if (index > nombre_profils) {
			--index;
			indexP = 0;
			indexPP = 0;
		} else {
			if (index > (nombre_profils - nbAffParPage)) {
				indexP = 0;
				indexPP = 0;
			} else {
				if (index > (nombre_profils - 2 * nbAffParPage)) {
					indexPP = 0;
				}
			}
		}

		myForm.setPage_indexD(indexD);
		myForm.setPage_indexMM(indexMM);
		myForm.setPage_AffIndexMM(indexMM / nbAffParPage + 1);
		myForm.setPage_indexM(indexM);
		myForm.setPage_AffIndexM(indexM / nbAffParPage + 1);
		myForm.setPage_index(index);
		myForm.setPage_AffIndex(index / nbAffParPage + 1);
		myForm.setPage_indexP(indexP);
		myForm.setPage_AffIndexP(indexP / nbAffParPage + 1);
		myForm.setPage_indexPP(indexPP);
		myForm.setPage_AffIndexPP(indexPP / nbAffParPage + 1);
		myForm.setPage_indexF(indexF);

		myForm.setPage_courante((index / nbAffParPage) + 1);

		// recupration des profils
		List<UserData> listeResultProfil = profils;

		if (!profils.isEmpty() && (nombre_profils > nbAffParPage)) {
			if (index - 1 + nbAffParPage <= profils.size()) {
				listeResultProfil = profils.subList(index - 1, index - 1 + nbAffParPage);
			} else {
				listeResultProfil = profils.subList(index - 1, profils.size());
			}
		}
		try {
			String ageminTemp = StringShop.EMPTY_STRING;
			if (-1 != agemin) {
				ageminTemp = Integer.toString(agemin);
			}

			SessionTools.setSessionUserPrefs(session, "annu_agemin", ageminTemp);

			String agemaxTemp = StringShop.EMPTY_STRING;
			if (-1 != agemax) {
				agemaxTemp = Integer.toString(agemax);
			}

			SessionTools.setSessionUserPrefs(session, "annu_agemax", agemaxTemp);
			SessionTools.setSessionUserPrefs(session, "annu_agemax", agemaxTemp);
			SessionTools.setSessionUserPrefs(session, "annu_sexe", sexe);
			SessionTools.setSessionUserPrefs(session, "annu_pseudo", pseudo);
			SessionTools.setSessionUserPrefs(session, "annu_ville", ville);
			SessionTools.setSessionUserPrefs(session, "annu_pays", pays);
			SessionTools.setSessionUserPrefs(session, "annu_type_tri", Integer.toString(tri));
			SessionTools.setSessionUserPrefs(session, "annu_typeTri", typeTri);
			SessionTools.setSessionUserPrefs(session, "annu_page_index", Integer.toString(index));
			SessionTools.setSessionUserPrefs(session, "annu_nbAffParPage", Integer.toString(nbAffParPage));
			SessionTools.setSessionUserPrefs(session, "annu_session", "1");

		} catch (final NullPointerException e) {
			MyAnnuaireResultAction.LOGGER.debug("MyAnnuaireResultAction" + e);
		}

		myForm.setNombre_profils(nombre_profils);
		myForm.setListeResultProfil(listeResultProfil);

		return mapping.getInputForward();
	}
}
