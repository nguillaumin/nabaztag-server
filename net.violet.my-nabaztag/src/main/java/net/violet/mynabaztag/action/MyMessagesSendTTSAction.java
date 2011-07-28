package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyMessagesSendTTSForm;
import net.violet.mynabaztag.services.ManageMessageServices;
import net.violet.platform.api.exceptions.BlackListedException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.ParentalFilterException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;
import net.violet.platform.voice.TTSServices;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesSendTTSAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyMessagesSendTTSAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final User user = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		final MyMessagesSendTTSForm myForm = (MyMessagesSendTTSForm) form;

		final String forward = "messages";

		// on rcupre mes infos du formulaire
		int idVoice = myForm.getIdVoice();
		final int paletteColor = myForm.getColor();
		final Palette palette = Palette.findPaletteByNum(paletteColor);
		final int choixJourDiff = myForm.getChoixJourDiff();
		final int choixMoisDiff = myForm.getChoixMoisDiff();
		final int choixAnneeDiff = myForm.getChoixAnneeDiff();
		final int choixHeureDiff = myForm.getChoixHeureDiff();
		final int choixMinuteDiff = myForm.getChoixMinuteDiff();
		String destName = myForm.getDestName(); // nom du lapin destinataire
		String saveMp3Perso = myForm.getSaveMp3Perso();
		String messageTTS = myForm.getMessageTTS();
		String sendLater = myForm.getSendLater();
		final String friendDestName = myForm.getFriendObjectName();

		if (destName.equals(StringShop.EMPTY_STRING)) {
			destName = StringShop.EMPTY_STRING;
		}

		if (saveMp3Perso.equals(StringShop.EMPTY_STRING)) {
			saveMp3Perso = StringShop.EMPTY_STRING;
		}

		if (messageTTS.equals(StringShop.EMPTY_STRING)) {
			messageTTS = StringShop.EMPTY_STRING;
		}

		if (sendLater.equals(StringShop.EMPTY_STRING)) {
			sendLater = StringShop.EMPTY_STRING;
		}

		String message_erreur = "OK";
		final TtsVoice theVoice = Factories.TTSVOICE.find(idVoice);

		// si l'utilisateur a un compte et fais un envoi
		if (myForm.getSend() == 1) {

			final VObject objectDest = ManageMessageServices.checkDest(friendDestName, destName);

			if (objectDest == null) {
				message_erreur = "no_id_rabbit"; // on a pas réussi a récupérer le destinataire
			} else {
				final User userDest = objectDest.getOwner();

				if (userDest == null) {
					message_erreur = "no_id_rabbit"; // on a pas réussi a récupérer le destinataire
				} else {
					try {
						if (ManageMessageServices.checkSendMessage(user, userDest)) {
							message_erreur = "OK";
							// On doit rentrer dans ce cas si on ne précise pas d'id de
							// voix, c'est à dire le nab them!!
							if (idVoice == 0) {
								final TtsLangData textLang = TtsLangData.getIdentifyLanguage(messageTTS, SiteLangData.get(user.getLang()));// si on arrive pas a définir la langue
								// , on prend celui de l'envoyeur
								idVoice = textLang.getReference().getId().intValue();
							}

							String libelle = messageTTS;
							// création du TTS
							Files theTTS = TTSServices.getDefaultInstance().postTTS(messageTTS, true, true, theVoice);
							if (libelle.length() > 200) {
								libelle = libelle.substring(0, 200) + "...";
							}
							// on regarde si l'utilisateur souhaite enregistrer son mp3
							if (saveMp3Perso.equals("save_mp3") && (theTTS != null)) {
								final Music theMusic = Factories.MUSIC.createNewMusic(theTTS, libelle, user, MusicStyle.CATEGORIE_TTS_PERSO, 0);
								if (theMusic == null) {
									theTTS.scheduleDeletion();
									theTTS = null;
								}
							}

							if (theTTS == null) {
								message_erreur = "create_tts_error"; // problème lors de la création du TTS sur le serveur
								MyMessagesSendTTSAction.LOGGER.debug("Problème lors de la création du TTS");

							} else {
								// on regarde s'il s'agit d'un envoi différé
								final CCalendar theDeliveryDate;
								if (sendLater.equals("send_later")) { // message envoi différé construction du nexttime
									theDeliveryDate = new CCalendar(user.getTimezone().getJavaTimeZone());
									theDeliveryDate.set(choixAnneeDiff, choixMoisDiff - 1, choixJourDiff, choixHeureDiff, choixMinuteDiff);
								} else {
									theDeliveryDate = null;
								}
								try {
									ManageMessageServices.sendUserMessageAndNotification(user, objectDest, theTTS, theDeliveryDate, palette, libelle);
								} catch (final InternalErrorException e) {
									message_erreur = "server_error";
								}
							}
						}
					} catch (final ParentalFilterException e) {
						message_erreur = "parental_error";
					} catch (final BlackListedException e) {
						message_erreur = "black_list_error";
					}

				}
			}
		} else { // pour l'ecoute du message
			final Files theTTS = TTSServices.getDefaultInstance().postTTS(messageTTS, false, false, theVoice);
			myForm.setUrl(theTTS.getPath());

			return mapping.findForward("playIt");
		}

		myForm.setErreur(message_erreur);

		return mapping.findForward(forward);
	}

}
