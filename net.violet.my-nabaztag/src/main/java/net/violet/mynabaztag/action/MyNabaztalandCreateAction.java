package net.violet.mynabaztag.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNabaztalandCreateForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastCategImpl;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.NabcastValImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.NabcastCategData;
import net.violet.platform.dataobjects.NabcastValData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;
import net.violet.vlisp.services.NabcastServices;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MyNabaztalandCreateAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyNabaztalandCreateAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);
		final Lang userLang = SessionTools.getLangFromSession(session, request);

		final MyNabaztalandCreateForm myForm = (MyNabaztalandCreateForm) form;
		final int mode = myForm.getMode();

		/**
		 * Check if the user really exists
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}
		/**
		 * Check if mode has a correct value (de toute façon ces "mode" et
		 * "queFaire" faut les exterminer!)
		 */
		if ((mode < 0) || (mode > 4)) {
			return mapping.findForward("error");
		}

		/* Languages list */
		myForm.setLangList(ObjectLangData.getAllObjectLanguages());
		/* Categories list */
		myForm.setNabcast_categorieList(NabcastCategData.findAllNabcastCategByLangs(theUser.getLangs(), true));
		/* recuperation liste des musiques */
		myForm.setNabcast_soundList(MusicData.findAllByUser(theUser));
		/* recuperation liste des animations */
		myForm.setNabcast_animList(AnimData.findAllByLangOrderByName(userLang));

		if ((mode != 1) && (mode != 3)) {
			myForm.setColorPicker_value(StringShop.EMPTY_STRING);
			myForm.setNabcast_anim("0");
			myForm.setNabcast_sound("0");
		}

		int idNabcast = myForm.getIdNabcast();
		myForm.setIdNabcast(idNabcast);
		final Nabcast theNabcast = NabcastImpl.find(idNabcast);

		if ((mode != 3) && (mode != 1) && (mode != 4)) {
			if (theNabcast != null) {
				if (theNabcast.getAuthor().getId().equals(theUser.getId())) {
					/* affectation des valeurs dans la Jsp */
					myForm.setNabcast_title(theNabcast.getNabcast_title());
					myForm.setNabcast_categorie(Long.toString(theNabcast.getNabcastCateg().getId()));
					myForm.setNabcast_description(theNabcast.getNabcast_desc());
					myForm.setNabcast_sound(Long.toString(theNabcast.getNabcast_music_sign()));
					myForm.setNabcast_anim(Long.toString(theNabcast.getNabcast_anim_sign()));
					myForm.setColorPicker_value(theNabcast.getNabcast_color_sign());
					myForm.setNabcast_lang((int) theNabcast.getNabcast_lang());
					myForm.setIdNabcast(theNabcast.getId().intValue());

					myForm.setNabcast_shortcut(theNabcast.getNabcast_shortcut());
					if (myForm.getNabcast_shortcut() == null) {
						myForm.setNabcast_shortcut(StringShop.EMPTY_STRING);
					}
				}
			} else {
				myForm.setNabcast_lang(userLang.getId().intValue());
			}
		}

		// NabcastImpl creation
		if (mode == 1) {
			final String nabcast_title = myForm.getNabcast_title();
			final long nabcast_categorie = Long.parseLong(myForm.getNabcast_categorie());
			final String nabcast_description = myForm.getNabcast_description();
			final long nabcast_sound = Long.parseLong(myForm.getNabcast_sound());
			final long nabcast_anim = Long.parseLong(myForm.getNabcast_anim());
			final String colorPicker_value = myForm.getColorPicker_value();
			final int nabcast_lang = myForm.getNabcast_lang();

			String nabcast_shortcut = myForm.getNabcast_shortcut();
			if ((nabcast_shortcut == null) || nabcast_shortcut.equals(StringShop.EMPTY_STRING)) {
				nabcast_shortcut = nabcast_title.replace(' ', '_');
			}

			Nabcast newNabcast = null;
			try {
				newNabcast = new NabcastImpl(nabcast_title, nabcast_categorie, nabcast_description, theUser, colorPicker_value, nabcast_sound, nabcast_anim, Factories.LANG.find(nabcast_lang), nabcast_shortcut);
				theUser.addCreatedNabcast(newNabcast);
			} catch (final SQLException e) {
				MyNabaztalandCreateAction.LOGGER.fatal(e, e);
			}

			// mode nabspy!!!
			idNabcast = 0;
			if (newNabcast != null) {
				idNabcast = newNabcast.getId().intValue();
			}
			final VObject theObject = Factories.VOBJECT.findByName("colacoca");
			if (!NabcastServices.isAbonneUser(theObject, NabcastImpl.find(idNabcast))) {
				NabcastServices.abonneUser(theObject, idNabcast, -1, -1);
			}
			myForm.setIdNabcast(idNabcast);
		}

		// NabcastImpl update
		if (mode == 3) {
			idNabcast = myForm.getIdNabcast();
			final Nabcast nabcast = NabcastImpl.find(idNabcast);
			if ((nabcast != null) && nabcast.getAuthor().getId().equals(theUser.getId())) {
				final String nabcast_title = myForm.getNabcast_title();
				final long nabcast_categorie = Long.parseLong(myForm.getNabcast_categorie());
				final String nabcast_description = myForm.getNabcast_description();
				final long nabcast_sound = Long.parseLong(myForm.getNabcast_sound());
				final long nabcast_anim = Long.parseLong(myForm.getNabcast_anim());
				final String colorPicker_value = myForm.getColorPicker_value();
				final int nabcast_lang = myForm.getNabcast_lang();
				String nabcast_shortcut = myForm.getNabcast_shortcut();

				if ((nabcast_shortcut == null) || nabcast_shortcut.equals(StringShop.EMPTY_STRING)) {
					nabcast_shortcut = nabcast_title.replace(' ', '_');
				}

				nabcast.setNabcastInfo(nabcast_anim, NabcastCategImpl.find(nabcast_categorie), colorPicker_value, nabcast_description, nabcast_lang, nabcast_sound, nabcast_shortcut, nabcast_title);
			}
		}

		// NabcastImpl to delete
		if (mode == 4) {
			final Nabcast nabcast = NabcastImpl.find(myForm.getIdNabcast());
			if ((nabcast != null) && nabcast.getAuthor().getId().equals(theUser.getId())) {
				final List<NabcastValData> mylisteDelete = NabcastValData.findSongsNabcast(nabcast, 0, 0, 1, 0, theUser); // supprimée les messages programmé de ce nabcast
				for (final NabcastValData nd : mylisteDelete) {
					NabcastServices.deleteMsgNabCastInFuture(nd.getNabcastval_id());
					NabcastValImpl.deleteNabcastVal(nd.getNabcastval_id());
				}

				final List<NabcastValData> myNabcastlisteDelete = NabcastValData.findSongsNabcast(nabcast, 0, 0, 0, 0, theUser); // clean le nabcast
				for (final NabcastValData nd : myNabcastlisteDelete) {
					NabcastValImpl.deleteNabcastVal(nd.getNabcastval_id());
				}

				NabcastServices.deleteNabcast(idNabcast, theUser.getId().intValue());
				NabcastServices.desabonneAllUser(idNabcast); // supprime les abonnée
			}
		}

		return mapping.findForward("createoredit");
	}
}
