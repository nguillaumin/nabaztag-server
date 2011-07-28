package net.violet.mynabaztag.action;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNabaztalandSubscribeForm;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.Subscriber;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.NabcastValData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;
import net.violet.vlisp.services.NabcastServices;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class MyNabaztalandSubscribeAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyNabaztalandSubscribeAction.class);

	@Override
	protected ActionForward doExecute(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);
		final VObject object = SessionTools.getRabbitFromSession(session);

		// if( user == null || object == null )
		// return mapping.findForward("login");

		final MyNabaztalandSubscribeForm myForm = (MyNabaztalandSubscribeForm) form;

		int mode = 0;
		String forward = StringShop.EMPTY_STRING;
		int heures = -1;
		int minutes = -1;
		int isReg = 0;

		RequestDispatcher reqDispatcher = null;

		final int idNabcast = myForm.getIdNabcast();
		// recuperation du Nabcastdata
		final NabcastData nd = NabcastData.find(idNabcast);
		final Nabcast theNabcast = NabcastImpl.find(idNabcast);

		if (theNabcast == null) {
			return mapping.findForward("nabcastBadNabcastId");
		}

		// INFOS DE SESSION
		if (user != null) {
			myForm.setUser_id(user.getId().intValue());

			if (object != null) {
				myForm.setUser_main(object.getId().intValue());
				forward = "subscribe";
			} else {
				reqDispatcher = request.getRequestDispatcher("/include_jsp/myNablife/nabcastNeedARabbit.jsp?idNabcast=" + idNabcast);
				// je set en tant qu'attribut de request
				request.setAttribute("nabcastData", nd);
				// je forwarde
				try {
					reqDispatcher.forward(request, response);
				} catch (final Exception e) {
					MyNabaztalandSubscribeAction.LOGGER.fatal(e, e);
				}
			}
		} else {
			// on redispatch en passant aussi l'url de destination
			if (idNabcast == 0) {
				reqDispatcher = request.getRequestDispatcher("/include_jsp/myNablife/nabcastNeedUserLogged.jsp");
			} else {
				reqDispatcher = request.getRequestDispatcher("/include_jsp/myNablife/nabcastNeedUserLogged.jsp?goTo=/action/myNablife.do@idNabcast=" + idNabcast);
			}
			// je set en tant qu'attribut de request
			request.setAttribute("nabcastData", nd);
			// je forwarde
			try {
				reqDispatcher.forward(request, response);
			} catch (final Exception e) {
				MyNabaztalandSubscribeAction.LOGGER.fatal(e, e);
			}
		}

		mode = myForm.getMode();
		if ((idNabcast == 0) || ((mode != 0) && (mode != 1) && (mode != 2))) {
			return mapping.findForward("error");
		}

		// Elements commun
		if ((object != null) && NabcastServices.isAbonneUser(object, NabcastImpl.find(idNabcast))) {
			isReg = 1;
		}

		myForm.setIsReg(isReg);

		myForm.setNabcastData(nd);

		if (mode == 0) { // Affichage du choix des horraires
			if (object != null) {
				if (isReg == 1) {
					final String[] time = NabcastServices.getMinuteHourNabcast(object, idNabcast);
					myForm.setMinutes(time[0]);
					myForm.setHeures(time[1]);
				}
			}
		} else if (mode == 1) { // desabonnement suppression des events
			final Nabcast nabcast = NabcastImpl.find(idNabcast);
			if ((object != null) && (nabcast != null)) {
				final int idusernabcast = (int) nd.getNabcast_author();
				final List<NabcastValData> mylisteDelete = NabcastValData.findSongsNabcast(nabcast, 0, 0, 1, 0, user); // supprime tous les message a diffusé du nabcast
				final User userSender = Factories.USER.find(idusernabcast);
				NabcastServices.deleteAllMsgNabCastSubscribe(mylisteDelete, object, userSender);

				// suppression de la liste des inscrits
				if (isReg == 1) {
					object.deabonne2Nabcast(nabcast);
				}
			}
		} else if (mode == 2) { // Recuperation des infos d'horraires + maj de la diffusion
			final String heuresS = myForm.getHeures();
			if (heuresS.equals(StringShop.EMPTY_STRING)) {
				heures = -1;
			} else {
				heures = Integer.parseInt(myForm.getHeures());
			}

			final String minutesS = myForm.getMinutes();
			if (minutesS.equals(StringShop.EMPTY_STRING)) {
				minutes = -1;
			} else {
				minutes = Integer.parseInt(myForm.getMinutes());
			}

			if ((user != null) && (object != null)) {

				// 26/02/2007 Excecuted both in the if... and in the else... So
				// I took it out.
				if (heures == -1) {
					minutes = -1;
				}
				if ((heures > 0) && (minutes == -1)) {
					minutes = 0;
				}
				final Nabcast inNabcast = NabcastImpl.find(idNabcast);

				if (inNabcast != null) {

					if (isReg == 1) { // update l'horaire sur ce nabcast
						final Map<Nabcast, Subscriber> nabcasts = object.getSubscribedNabcast();
						final Subscriber inSubscriber = nabcasts.get(inNabcast);

						if (inSubscriber != null) {
							inSubscriber.setTime(heures, minutes);

							final int idusernabcast = (int) nd.getNabcast_author();
							// supprime les messages qui sont programmé de ce
							// nabcast
							final List<NabcastValData> listesong = NabcastValData.findSongsNabcast(inNabcast, 0, 0, 1, 0, user);
							final User userSender = Factories.USER.find(idusernabcast);
							NabcastServices.deleteAllMsgNabCastSubscribe(listesong, object, userSender);

							// rajoute les messages programmés en updatant les
							// horaire de ce nabcast sur le lapin
							NabcastServices.addMsgNabCastSubscribe(listesong, user, object, heures, minutes, idusernabcast, String.valueOf(nd.getNabcast_anim_sign()), String.valueOf(nd.getNabcast_music_sign()), String.valueOf(nd.getNabcast_color_sign()));
						}

					} else { // s'abonne la toute première fois
						NabcastServices.abonneUser(object, idNabcast, heures, minutes);
						final int idusernabcast = (int) nd.getNabcast_author();
						final List<NabcastValData> listesong = NabcastValData.findSongsNabcast(inNabcast, 0, 0, 1, 0, user); // rajoute les messages programmés de ce nabcast sur
						// le
						// lapin
						NabcastServices.addMsgNabCastSubscribe(listesong, user, object, heures, minutes, idusernabcast, String.valueOf(nd.getNabcast_anim_sign()), String.valueOf(nd.getNabcast_music_sign()), String.valueOf(nd.getNabcast_color_sign()));
					}
				}
			}

		}
		return mapping.findForward(forward);
	}
}
