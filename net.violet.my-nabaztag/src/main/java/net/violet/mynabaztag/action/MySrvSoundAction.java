package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvSoundForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MySrvSoundAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MySrvSoundForm myForm = (MySrvSoundForm) form;
		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}
		final Lang lang = user.getLang();
		final VObject object = SessionTools.getRabbitFromSession(session);
		final int user_main = (object == null) ? 0 : (int) object.getId().intValue();

		String name = StringShop.EMPTY_STRING;
		final List<MusicData> sonList = new ArrayList<MusicData>();
		myForm.setUser_id(user.getId().intValue());
		myForm.setUser_main(user_main);
		myForm.setLangUser(lang.getId().intValue());

		name = StringShop.EMPTY_STRING;
		final Music theMusic = Factories.MUSIC.find(myForm.getSrvSound_id());
		if (theMusic != null) {
			name = theMusic.getMusic_name();
		}

		// Creation de la liste des mp3Perso et des musiques de reveil
		sonList.addAll(MusicData.findAllForAlarmClock(lang));
		sonList.addAll(MusicData.findAllPersoByUser(user));

		// Recreation de la liste des reveils

		myForm.setName(name);
		myForm.setSonList(sonList);

		myForm.setSrvSound_id(myForm.getSrvSound_id());

		return mapping.getInputForward();
	}
}
