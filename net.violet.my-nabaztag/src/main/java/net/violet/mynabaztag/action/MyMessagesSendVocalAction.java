package net.violet.mynabaztag.action;

import java.net.URL;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.converters.ContentType;
import net.violet.mynabaztag.form.MyMessagesSendVocalForm;
import net.violet.mynabaztag.services.ManageMessageServices;
import net.violet.platform.api.exceptions.BlackListedException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.ParentalFilterException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesSendVocalAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyMessagesSendVocalAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final User user = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		final MyMessagesSendVocalForm myForm = (MyMessagesSendVocalForm) form;

		final String forward = "messages";
		String message_erreur = "OK";

		// on récupère mes infos du formulaire
		final int paletteColor = myForm.getColor();
		final Palette palette = Palette.findPaletteByNum(paletteColor);
		final int choixJourDiff = myForm.getChoixJourDiff();
		final int choixMoisDiff = myForm.getChoixMoisDiff();
		final int choixAnneeDiff = myForm.getChoixAnneeDiff();
		final int choixHeureDiff = myForm.getChoixHeureDiff();
		final int choixMinuteDiff = myForm.getChoixMinuteDiff();
		String music_name = myForm.getMusicName();
		if ((music_name == null) || StringShop.EMPTY_STRING.equals(music_name)) {
			music_name = DicoTools.dico(user.getLang(), "PushToTalk/message");
		}
		String destName = myForm.getDestName(); // nom du lapin destinataire
		String sendLater = myForm.getSendLater();
		final String idMp3 = myForm.getIdMp3();
		String saveMp3Perso = myForm.getSaveMp3Perso();
		final String friendDestName = myForm.getFriendObjectName();

		if (destName.equals(StringShop.EMPTY_STRING)) {
			destName = StringShop.EMPTY_STRING;
		}

		if (sendLater.equals(StringShop.EMPTY_STRING)) {
			sendLater = StringShop.EMPTY_STRING;
		}
		if (saveMp3Perso.equals(StringShop.EMPTY_STRING)) {
			saveMp3Perso = StringShop.EMPTY_STRING;
		}

		if (idMp3.equals(StringShop.EMPTY_STRING)) {
			message_erreur = "server_error"; // problème le mp3 n'a pas été créé
		} else {
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

					boolean mp3Save = false;
					if (saveMp3Perso.equals("save_mp3")) {
						mp3Save = true;
					}

					Files theFiles = null;
					final TmpFileWrapper theWrapper = new TmpFileWrapper();

					try {
						// conversion flv en mp3 par ffmpeg
						if (mp3Save) {
							theFiles = FilesManagerFactory.FILE_MANAGER.post(FilesManager.TMP_MANAGER.new TmpFile(new URL(FilesData.VOCAL_RECORDER + idMp3 + StringShop.FLV_EXT).openStream()), ContentType.FLV, ContentType.MP3_32, Files.CATEGORIES.BROAD, true, true, MIME_TYPES.A_MPEG);;
							if (theFiles != null && !MusicData.createMp3(FilesData.getData(theFiles), music_name, user, 0).isValid())
								theFiles = null;
						} else {
							final boolean is4V1 = VObjectData.getData(objectDest).getObjectType().instanceOf(ObjectType.NABAZTAG_V1);
							theFiles = FilesManagerFactory.FILE_MANAGER.post(FilesManager.TMP_MANAGER.new TmpFile(new URL(FilesData.VOCAL_RECORDER + idMp3 + StringShop.FLV_EXT).openStream()), ContentType.FLV, ContentType.MP3_32, Files.CATEGORIES.BROAD, is4V1, is4V1, MIME_TYPES.A_MPEG);
						}

					} catch (final Exception e) {
						message_erreur = "server_error"; // problème le mp3 n'a pas été créé
						MyMessagesSendVocalAction.LOGGER.fatal(e, e);
						theWrapper.clean();
					}

					if (theFiles == null) {
						message_erreur = "server_error"; // problème le mp3 n'a pas été créé
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
								ManageMessageServices.sendUserMessageAndNotification(user, objectDest, theFiles, theDeliveryDate, palette, music_name);
							} catch (final InternalErrorException e) {
								message_erreur = "server_error";
							}
						}
					}
				}
			} else {
				myForm.setUrl(Files.CATEGORIES.VOCAL_RECORDER.getPath() + idMp3 + StringShop.FLV_EXT);
				return mapping.findForward("playIt");
			}
		}
		myForm.setErreur(message_erreur);

		return mapping.findForward(forward);
	}
}
