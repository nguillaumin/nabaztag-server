package net.violet.platform.api.actions.messages;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.BlackListedException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.exceptions.ParentalFilterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.MessageServices.Body;
import net.violet.platform.util.DicoTools;
import net.violet.platform.voice.TTSServices;
import net.violet.platform.xmpp.JabberMessageFactory;
import net.violet.vlisp.services.ManageMessageServices;

public class SendMedia extends AbstractSendMessageAction {

	/**
	 * @param inParam
	 * @return
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 */
	private MusicData getMusicData(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException {

		final UserData userData = getSender(inParam);
		final String itemAPIId = inParam.getMainParamAsString();
		final APICaller caller = inParam.getCaller();

		final MusicData musicData = MusicData.findByAPIId(itemAPIId, caller.getAPIKey());
		if ((musicData.getMusic_share() == 0) && !userData.equals(musicData.getOwner())) {
			throw new ForbiddenException();
		}

		return musicData;
	}

	protected Map<String, Object> getPojoMessageClone(ActionParam inParam, Lang inLang) throws InvalidParameterException, ForbiddenException, InvalidSessionException {

		final Date deliveryDate = inParam.getDate("time_of_delivery", new Date());

		/*
		 * Récupère le fichier à jouer et vérifie que c'est bien un mp3 perso de
		 * l'utilisateur
		 */
		final MusicData musicData = getMusicData(inParam);

		final Files files = musicData.getFile().getReference();

		final String title = inParam.getString("title", musicData.getLabel());

		/*
		 * construit le message pojo
		 */
		final Map<String, Object> seqMap = AbstractSendMessageAction.getPOJOSequenceMap(files);

		final Map<String, Object> altMap = AbstractSendMessageAction.getUnsupportedContent(inLang);

		final Map<String, Object> thePojoMessage = new HashMap<String, Object>();

		thePojoMessage.put("date", deliveryDate);
		thePojoMessage.put("title", title);

		thePojoMessage.put("sequence", Arrays.asList((Object) seqMap));

		thePojoMessage.put("alt", altMap);

		return thePojoMessage;

	}

	@Override
	public Object doProcessRequest(ActionParam inParam) throws NoSuchObjectException, InvalidParameterException, ForbiddenException, InvalidSessionException, ParentalFilterException, BlackListedException, NoSuchPersonException, InternalErrorException {

		final UserData senderUserData = getSender(inParam);
		final List<VObjectData> recipients = getRecipients(inParam);
		final APICaller apiCaller = inParam.getCaller();

		final MusicData musicData = getMusicData(inParam);

		final FilesData filesData = musicData.getFile();

		final Palette theColorPalette = AbstractSendMessageAction.getPalette(inParam);
		final Date theDeliveryDate = inParam.getDate("time_of_delivery");

		for (final VObjectData destObjectData : recipients) {

			final VObject destObject = destObjectData.getReference();

			final boolean supportedContent = destObjectData.getObjectType().isMimeTypeSupported(filesData.getReference().getType());

			if (destObjectData.getObjectType().instanceOf(ObjectType.RFID)) {

				if (!supportedContent) {
					final Map<String, Object> msgMap = getPojoMessageClone(inParam, destObject.getPreferences().getLangPreferences());
					msgMap.put("from", senderUserData.getApiId(apiCaller));
					msgMap.put("to", Arrays.asList(destObjectData.getApiId(apiCaller))); // must be a list !

					// store the message in its JSON form to be replayed later
					final Files msgBodyContent = AbstractSendMessageAction.postPojoMessage(msgMap);

					if (msgBodyContent == null) {
						throw new InternalErrorException("File creation failed");
					}

					ManageMessageServices.putInbox(msgBodyContent, msgMap, theColorPalette, senderUserData.getReference(), destObject, musicData.getLabel());
				}

			} else {

				if (supportedContent) {
					ManageMessageServices.sendUserMessageAndNotification(senderUserData, destObjectData, filesData, theDeliveryDate, theColorPalette, musicData.getLabel(), AbstractSendMessageAction.isStreamFile(musicData));
				} else {
					// On envois un msg TTS pour dire que ce n'est pas supporté
					final Lang lang = destObjectData.getReference().getPreferences().getLangPreferences();
					final String title = DicoTools.dico(lang, Files.UNSUPPORTED_CONTENT);
					final TtsVoice inTTSVoice = TtsVoiceData.findTtsVoiceByLang(TtsLangData.getDefaultTtsLanguage(lang.getIsoCode())).getReference();
					final boolean needsTreatment = destObjectData.getObjectType().instanceOf(ObjectType.NABAZTAG_V1);
					final Files theFile = TTSServices.getDefaultInstance().postTTS(title, needsTreatment, needsTreatment, inTTSVoice);
					final Body[] bodies = new Body[] { new Body(theFile, null, false) };
					final MessageDraft msgDraft = MessageServices.createMessageDraft(destObjectData.getReference(), title, bodies, Application.NativeApplication.INBOX.getApplication().getMessageSignature());
					MessageServices.sendUsingXmpp(msgDraft, JabberMessageFactory.IDLE_MODE);

				}
			}

		}

		return null;
	}

	@Override
	protected Map<String, Object> getMessageAsPojoMap(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException {
		return getPojoMessageClone(inParam, null);
	}
}
