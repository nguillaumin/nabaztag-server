package net.violet.mynabaztag.action;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyTerrierMyselfForm;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.AnnuImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.struts.DispatchActionWithLog;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierMyselfAction extends DispatchActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyTerrierMyselfAction.class);

	public ActionForward change(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final MyTerrierMyselfForm myForm = (MyTerrierMyselfForm) form;
		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(request.getSession(true));

		/**
		 * Check if the user really exists, i.e. theUser != null
		 */
		if ((theUser == null) || (theObject == null)) {
			return mapping.findForward("no_compte");
		}

		final String userDescription = myForm.getRabbitAnnounce();
		final String firstName = myForm.getFirstName();
		final String lastName = myForm.getLastName();

		String dateBirth = myForm.getAnnee() + "-" + myForm.getMois() + "-" + myForm.getJour();
		if (myForm.getAnnee().equals(StringShop.EMPTY_STRING) || myForm.getMois().equals(StringShop.EMPTY_STRING) || myForm.getJour().equals(StringShop.EMPTY_STRING)) {
			dateBirth = null;
		}

		final char annuSexe = myForm.getAnnuSexe();

		final String annuCp = myForm.getAnnuCp();
		final String annuCity = myForm.getAnnuCity();
		final String annuCountry = myForm.getAnnuCountry();

		int annuConfirm = myForm.getAnnuConfirm();
		if (annuConfirm == 1) {
			annuConfirm = 0;
		} else {
			annuConfirm = 1;
		}

		Annu annu = theUser.getAnnu();

		if (annu == null) {
			// Creation de l'annuaire
			try {
				annu = new AnnuImpl(theUser, StringShop.EMPTY_STRING, theUser.getLang());
			} catch (final SQLException e) {
				MyTerrierMyselfAction.LOGGER.fatal(e, e);
			}
		}

		if (annu != null) {

			if (dateBirth != null) {
				final Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(myForm.getAnnee()), Integer.parseInt(myForm.getMois()) - 1, Integer.parseInt(myForm.getJour()), 0, 0, 0);
				annu.setDateBirth(new Date(cal.getTimeInMillis()));
			}
			annu.setAddress(annuCountry, annuCp);
			annu.setAllInformation(Character.toString(annuSexe), annuCity, annuConfirm, lastName, firstName, userDescription);
		}

		theUser.setComment(userDescription);
		ObjectProfile objectProfile = theObject.getProfile();
		if (objectProfile == null) {
			objectProfile = Factories.VOBJECT.createObjectProfile(theObject);
		}
		objectProfile.setDescription(userDescription);

		theUser.setIsGood(0);

		return load(mapping, form, request, response);
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyTerrierMyselfForm myForm = (MyTerrierMyselfForm) form;
		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);
		final Lang langUser = SessionTools.getLangFromSession(session, request);

		/**
		 * check if the user really exists, i.e. theUser != null
		 */
		if (theUser == null) {
			return mapping.findForward("no_compte");
		}

		// Description
		myForm.setRabbitAnnounce(theUser.getUser_comment());
		if (myForm.getRabbitAnnounce() == null) {
			myForm.setRabbitAnnounce(StringShop.EMPTY_STRING);
		}

		final Annu annuaire = theUser.getAnnu();
		if (annuaire != null) {
			myForm.setFirstName(annuaire.getAnnu_prenom());
			myForm.setLastName(annuaire.getAnnu_nom());
		}

		// Liste des PaysImpl

		myForm.setListePays(CountryData.findAll(langUser));

		// Liste des jours
		final List<LabelData> listeJour = new ArrayList<LabelData>(32);
		for (int i = 0; i < 32; i++) {
			LabelData ld = new LabelData();
			String jourDico;
			if (i == 0) {
				jourDico = DicoTools.dico(langUser, "profile/date_day");
			} else {
				if (i < 10) {
					jourDico = String.valueOf("0" + i);
				} else {
					jourDico = String.valueOf(i);
				}
				ld.setId(jourDico);
			}
			ld.setLabel(jourDico);
			listeJour.add(ld);
			ld = null;
		}
		myForm.setListeJour(listeJour);

		// Liste des mois
		final List<LabelData> listeMois = new ArrayList<LabelData>(13);
		String moisDico = StringShop.EMPTY_STRING;
		for (int i = 0; i < 13; i++) {
			LabelData ld = new LabelData();
			String month;
			if (i == 0) {
				month = DicoTools.dico(langUser, "profile/date_month");
			} else {
				if (i < 10) {
					month = String.valueOf("0" + i);
				} else {
					month = String.valueOf(i);
				}
				ld.setId(month);
				switch (i) {
				case 1:
					moisDico = DicoTools.dico(langUser, "profile/date_month_january");
					break;
				case 2:
					moisDico = DicoTools.dico(langUser, "profile/date_month_february");
					break;
				case 3:
					moisDico = DicoTools.dico(langUser, "profile/date_month_march");
					break;
				case 4:
					moisDico = DicoTools.dico(langUser, "profile/date_month_april");
					break;
				case 5:
					moisDico = DicoTools.dico(langUser, "profile/date_month_may");
					break;
				case 6:
					moisDico = DicoTools.dico(langUser, "profile/date_month_june");
					break;
				case 7:
					moisDico = DicoTools.dico(langUser, "profile/date_month_july");
					break;
				case 8:
					moisDico = DicoTools.dico(langUser, "profile/date_month_august");
					break;
				case 9:
					moisDico = DicoTools.dico(langUser, "profile/date_month_september");
					break;
				case 10:
					moisDico = DicoTools.dico(langUser, "profile/date_month_october");
					break;
				case 11:
					moisDico = DicoTools.dico(langUser, "profile/date_month_november");
					break;
				case 12:
					moisDico = DicoTools.dico(langUser, "profile/date_month_december");
					break;
				}
				month = moisDico;
			}
			ld.setLabel(month);
			listeMois.add(ld);
			ld = null;
		}
		myForm.setListeMois(listeMois);

		// Liste des annes
		final Calendar first = Calendar.getInstance();
		final int year = first.get(Calendar.YEAR);
		final int anneeEnCours = year;
		final List<LabelData> listeAnnee = new ArrayList<LabelData>(102);
		for (int i = -1; i <= 100; i++) {
			LabelData ld = new LabelData();
			String yearDico;
			if (i == -1) {
				yearDico = DicoTools.dico(langUser, "profile/date_year");
			} else {
				yearDico = String.valueOf(anneeEnCours - i);
				ld.setId(yearDico);
			}
			ld.setLabel(yearDico);
			listeAnnee.add(ld);
			ld = null;
		}
		myForm.setListeAnnee(listeAnnee);

		// Date de Naissance
		String jour;
		String mois;
		String annee;

		if ((annuaire == null) || (annuaire.getAnnu_datebirth() == null)) {
			jour = DicoTools.dico(langUser, "profile/date_day");
			mois = DicoTools.dico(langUser, "profile/date_month");
			annee = DicoTools.dico(langUser, "profile/date_year");
		} else {
			final Calendar cal = Calendar.getInstance();
			cal.setTime(annuaire.getAnnu_datebirth());
			jour = Integer.toString(cal.get(Calendar.DATE));
			mois = Integer.toString(cal.get(Calendar.MONTH) + 1);
			annee = Integer.toString(cal.get(Calendar.YEAR));

			if (jour.length() < 2) {
				jour = "0" + jour;
			}
			if (mois.length() < 2) {
				mois = "0" + mois;
			}

		}
		myForm.setJour(jour);
		myForm.setMois(mois);
		myForm.setAnnee(annee);

		// CP, VILLE, COUNTRY
		if (annuaire != null) {
			myForm.setAnnuCp(annuaire.getAnnu_cp());
			myForm.setAnnuCity(annuaire.getAnnu_city());
		}

		if ((annuaire == null) || (annuaire.getAnnu_country() == null)) {
			myForm.setAnnuCountry("FR");
		} else {
			myForm.setAnnuCountry(annuaire.getAnnu_country());
		}

		// SEXE
		final String tmp = (annuaire == null) ? null : annuaire.getAnnu_sexe();
		char c = 0;
		if ((tmp != null) && !tmp.equals(StringShop.EMPTY_STRING)) {
			c = tmp.charAt(0);
		}

		myForm.setAnnuSexe(c);

		// chgt de la valeur pour l'apparition dans l'annuaire
		if (annuaire != null) {
			int ac = annuaire.getAnnu_confirm();
			if (ac == 1) {
				ac = 0;
			} else {
				ac = 1;
			}
			myForm.setAnnuConfirm(ac);
		}

		myForm.setRabbitMail(theObject.getObject_login() + "@nabaztag.com");
		myForm.setRabbitName(theObject.getObject_login());
		myForm.setRabbitPicture(Long.toString(theUser.getUser_image()));

		return mapping.getInputForward();
	}

	public static class LabelData implements Serializable {

		public static final long serialVersionUID = 1;
		private String label;
		private String id;

		public LabelData() {
		}

		public LabelData(final String id, final String label) {
			this.id = id;
			this.label = label;
		}

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getLabel() {
			return this.label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

	}

}
