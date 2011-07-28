package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNabaztalandHomeForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MotherTongueLangData;
import net.violet.platform.dataobjects.NabcastCategData;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.SrvCategData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MyNabaztalandHomeAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyNabaztalandHomeAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);
		final VObject object = SessionTools.getRabbitFromSession(session);
		final Lang userLang = SessionTools.getLangFromSession(session, request);

		final MyNabaztalandHomeForm myForm = (MyNabaztalandHomeForm) form;

		final int sizeListNabcast = 0;
		String langCategorie = myForm.getLangCategorie();
		int nabcastCateg = 0;

		if (myForm.getLangList().size() == 0) {
			/*
			 * final List<MotherTongueLangData> spkLangTemp = new
			 * ArrayList<MotherTongueLangData>();
			 * spkLangTemp.addAll(MotherTongueLangData.findAll(user)); final
			 * List <MotherTongueLangData> spkLangNabcast = new
			 * ArrayList<MotherTongueLangData>(); for (MotherTongueLangData lang
			 * : spkLangTemp) { final int tempSizeList =
			 * NabcastServices.getNbrNabcastbyCateg(nabcastCateg, (int)
			 * lang.getId()); if (0 < tempSizeList){ if
			 * (langCategorie.equals(String.valueOf(lang.getId())))
			 * sizeListNabcast = tempSizeList; spkLangNabcast.add(lang); } else{
			 * if(lang.getId() == userLang.getId().longValue()){ userLang =
			 * Factories.LANG.find(3); } } } if(spkLangNabcast.isEmpty()){
			 * spkLangNabcast.addAll(MotherTongueLangData.
			 * findAllLanguagesForNabcasts()); //set la langue de nabcast par
			 * défaut userLang = Factories.LANG.find(1); }
			 * myForm.setLangList(spkLangNabcast);
			 */

			myForm.setLangList(MotherTongueLangData.findAllMotherTongue());
		}

		int mode = 0;

		// INFOS DE SESSION
		myForm.setUser_id((user == null) ? 0 : user.getId().intValue());
		myForm.setUser_main((object == null) ? 0 : object.getId().intValue());

		langCategorie = myForm.getLangCategorie();

		if (langCategorie.equals(StringShop.EMPTY_STRING)) {
			langCategorie = Long.toString(userLang.getId());
		}

		myForm.setLangCategorie(langCategorie);
		myForm.setLanguser(Long.toString(userLang.getId()));
		// FIN INFOS DE SESSION

		int langCategory = 2;

		try {
			langCategory = Integer.parseInt(langCategorie);
		} catch (final NumberFormatException e) {
			MyNabaztalandHomeAction.LOGGER.debug("MyNabaztalandHomeAction" + e);
		}

		mode = myForm.getMode();
		// si le mode n'est pas precise on repasse en mode 0
		if (mode != 1) {
			mode = 0;
		}

		// affichage des categories + affichage des nabcasts en random de toutes
		// les categ
		myForm.setListeNabcastCateg(NabcastCategData.findAllNabcastCategByLang(Factories.LANG.find(langCategory), false));
		myForm.setListeCategorie(SrvCategData.findAllDistinctByLang(Factories.LANG.find(langCategory)));
		// Affichage des Nabcasts propres a une categorie
		if (mode == 1) {
			nabcastCateg = myForm.getNabcastCateg();

			if (myForm.getIdCateg() > 0) {
				nabcastCateg = myForm.getIdCateg();
			}

			if (nabcastCateg == 0) {
				nabcastCateg = 1;
			}

			myForm.setNameCateg(NabcastCategData.findNabcastCateg(nabcastCateg).getNameCategorie());
			int nbAffParPage = myForm.getNbAffParPage();
			nbAffParPage = sizeListNabcast; // ???
			myForm.setNabcastCateg(nabcastCateg);

			myForm.setListNabcast(NabcastData.findAllByCateg(nabcastCateg, object, 0, nbAffParPage));
		}
		return mapping.getInputForward();
	}
}
