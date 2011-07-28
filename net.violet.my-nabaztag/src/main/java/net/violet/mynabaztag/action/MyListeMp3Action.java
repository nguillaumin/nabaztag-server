package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyListeMp3Form;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.dataobjects.CategData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.TagData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyListeMp3Action extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyListeMp3Form myForm = (MyListeMp3Form) form;
		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		/**
		 * Check if the user really exists, i.e. user_id > 0
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		final List<MusicData> listeMp3User1 = new ArrayList<MusicData>();
		final List<MusicData> listeMp3User2 = new ArrayList<MusicData>();

		final List<TagData> listeTags = new ArrayList<TagData>();
		final List<CategData> listeCateg = new LinkedList<CategData>();
		String forward = "liste_mp3";

		final int mp3Id = myForm.getMp3Id();
		if (mp3Id > 0) { // on retourne la liste des tags de se mp3
			listeTags.addAll(TagData.findByMp3(mp3Id));
			listeCateg.addAll(CategData.findAllInLang(lang));
			forward = "edit_mp3";
		} else { // liste des mp3 du user
			final List<MusicData> listeMp3User = MusicData.findAllPersoByUser(theUser);

			// on découpe la liste pour la répartir sur 3 listes distinctes
			// (pour affichage)
			final int nb_mp3 = listeMp3User.size();
			final int mod = nb_mp3 % 2;
			final int index_base = nb_mp3 / 2;

			int index1 = index_base;

			if (mod > 0) {
				index1 = index_base + 1;
			}

			listeMp3User1.addAll(listeMp3User.subList(0, index1));
			listeMp3User2.addAll(listeMp3User.subList(index1, nb_mp3));

			forward = "liste_mp3";
		}

		myForm.setListeMp3User1(listeMp3User1);
		myForm.setListeMp3User2(listeMp3User2);

		myForm.setListeCateg(listeCateg);
		myForm.setListeTags(listeTags);

		return mapping.findForward(forward);
	}

}
