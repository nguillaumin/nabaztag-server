package net.violet.mynabaztag.action;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesSendClinForm;
import net.violet.mynabaztag.services.ManageMessageServices;
import net.violet.platform.api.exceptions.BlackListedException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.ParentalFilterException;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesSendClinAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final User user = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		final MyMessagesSendClinForm myForm = (MyMessagesSendClinForm) form;

		final String forward = "messages";

		// on récupère mes infos du formulaire
		final int paletteColor = myForm.getColor();
		final Palette palette = Palette.findPaletteByNum(paletteColor);
		final int choixJourDiff = myForm.getChoixJourDiff();
		final int choixMoisDiff = myForm.getChoixMoisDiff();
		final int choixAnneeDiff = myForm.getChoixAnneeDiff();
		final int choixHeureDiff = myForm.getChoixHeureDiff();
		final int choixMinuteDiff = myForm.getChoixMinuteDiff();
		int idClin = myForm.getIdClin();
		final String param = SessionTools.getSessionUserpref(session, "page");
		final String clin = request.getParameter("idClin");
		if (!(param.equals(StringShop.EMPTY_STRING)) && param.equals("home") && (clin != null)) {
			idClin = Integer.parseInt(clin);
		}

		String destName = myForm.getDestName(); // nom du lapin destinataire
		String sendLater = myForm.getSendLater();
		final String friendDestName = myForm.getFriendObjectName();

		if (destName.equals(StringShop.EMPTY_STRING)) {
			destName = StringShop.EMPTY_STRING;
		}

		if (sendLater.equals(StringShop.EMPTY_STRING)) {
			sendLater = StringShop.EMPTY_STRING;
		}

		String message_erreur = "OK";

		// si l'utilisateur a un lapin
		if (myForm.getSend() == 1) {

			final VObject objectDest = ManageMessageServices.checkDest(friendDestName, destName);

			if (objectDest == null) {
				message_erreur = "no_id_rabbit"; // on a pas réussi a récupérer le destinataire
			} else {

				final User userDest = objectDest.getOwner();
				try {
					if (ManageMessageServices.checkSendMessage(user, userDest)) {
						message_erreur = "OK";
					}
				} catch (final ParentalFilterException e) {
					message_erreur = "parental_error";
				} catch (final BlackListedException e) {
					message_erreur = "black_list_error";
				}

				// on regarde s'il s'agit d'un envoi différé
				boolean envoiDiff = false;
				if (sendLater.equals("send_later")) {
					envoiDiff = true;
				}

				Music theMusic = Factories.MUSIC.find(idClin);

				// On doit rentrer dans ce cas si on ne précise d'id de clin
				// d'oeil,c'est à dire le nab them!!
				if (theMusic == null) {
					final int categId = myForm.getCategId();
					if (categId > 0) {
						theMusic = Factories.MUSIC.findRandomClin(categId, userDest.getLang());
					} else {
						// on recup un clin d'oeil aléatoire dans la catégorie
						// Bonjour en fonction de la langue du destinataire
						theMusic = Factories.MUSIC.findRandomClin(MusicStyle.CATEGORIE_CLIN_BONJOUR, userDest.getLang());
					}
				}

				if (theMusic == null) {
					message_erreur = "server_error"; // problème le clin d'oeil n'existe pas
				} else {

					if (message_erreur.equals("OK")) {
						CCalendar theDeliveryDate = null;
						if (envoiDiff) { // message envoi différé constuction du nexttime
							final String time_zone = user.getTimezone().getTimezone_javaId();
							final TimeZone tz = TimeZone.getTimeZone(time_zone);

							theDeliveryDate = new CCalendar(tz);
							theDeliveryDate.set(choixAnneeDiff, choixMoisDiff - 1, choixJourDiff, choixHeureDiff, choixMinuteDiff);
						}
						try {
							ManageMessageServices.sendUserMessageAndNotification(user, objectDest, theMusic.getFile(), theDeliveryDate, palette, theMusic.getMusic_name());
						} catch (final InternalErrorException e) {
							message_erreur = "server_error";
						}
					}
				}
			}
		} else { // ecoute du clin d'oeil
			String url = StringShop.EMPTY_STRING;
			final Music theMusic = Factories.MUSIC.find(idClin);
			if ((theMusic != null) && (theMusic.getFile() != null)) {
				url = theMusic.getFile().getPath();
			}
			myForm.setUrl(url);
			return mapping.findForward("playIt");
		}
		myForm.setErreur(message_erreur);

		return mapping.findForward(forward);
	}

}
