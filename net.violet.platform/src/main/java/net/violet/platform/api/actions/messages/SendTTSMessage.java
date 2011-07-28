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
import net.violet.platform.api.exceptions.TTSGenerationFailedException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.StringTools;
import net.violet.vlisp.services.ManageMessageServices;

/**
 * This action is used to send TTS messages from a User to one or several
 * objects.
 */
public class SendTTSMessage extends AbstractSendMessageAction {

	/**
	 * @throws InvalidParameterException
	 * @see net.violet.platform.api.actions.messages.AbstractSendMessageAction#getMessageAsPojoMap(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Map<String, Object> getMessageAsPojoMap(ActionParam inParam) throws InvalidParameterException {

		final Date deliveryDate = inParam.getDate("time_of_delivery", new Date());
		final String tts = inParam.getString("text", true);
		final String title = inParam.getString("title", StringTools.truncate(tts, 200, "..."));
		final String voice = inParam.getString("voice", false);

		// create the tts sequence
		final Map<String, Object> ttsSeqMap = new HashMap<String, Object>(8);
		ttsSeqMap.put("type", "expression");
		ttsSeqMap.put("modality", "net.violet.tts");
		ttsSeqMap.put("voice", voice);
		ttsSeqMap.put("text", tts);

		// and put it in the response message 
		final Map<String, Object> pojoMsgMap = new HashMap<String, Object>(8);
		pojoMsgMap.put("date", deliveryDate);
		pojoMsgMap.put("title", title);
		pojoMsgMap.put("sequence", Arrays.asList((Object) ttsSeqMap));

		return pojoMsgMap;

	}

	@Override
	public Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchObjectException, ParentalFilterException, BlackListedException, NoSuchPersonException, InternalErrorException, TTSGenerationFailedException {

		final UserData senderData = getSender(inParam);

		final List<String> recipients = inParam.getList("recipients", true);

		final String text = inParam.getString("text", true);
		final String title = inParam.getString("title", StringTools.truncate(text, 200, "..."));
		final String voice = inParam.getString("voice", false);
		final boolean saveMp3 = inParam.getBoolean("save", false);
		final Date timeOfDelivery = inParam.getDate("time_of_delivery");
		final Palette colorPalette = AbstractSendMessageAction.getPalette(inParam);
		final String codeLang = inParam.getString("language", null);

		final TtsVoiceData voiceData = TtsVoiceData.getByParams(voice, codeLang, text, senderData.getUserLang());

		final List<VObjectData> theObjectListData = VObjectData.checkObject(recipients, senderData, inParam.getCallerAPIKey());

		final APICaller apiCaller = inParam.getCaller();

		for (final VObjectData theObjectData : theObjectListData) {

			if (theObjectData.getObjectType().instanceOf(ObjectType.RFID)) {

				final Map<String, Object> msgMap = getMessageAsPojoMap(inParam);
				msgMap.put("from", senderData.getApiId(apiCaller));
				msgMap.put("to", Arrays.asList(theObjectData.getApiId(apiCaller))); // must be a list !
				// store the message in its JSON form to be replayed later
				final Files msgBodyContent = AbstractSendMessageAction.postPojoMessage(msgMap);

				if (msgBodyContent == null) {
					throw new InternalErrorException("File creation failed");
				}

				ManageMessageServices.putInbox(msgBodyContent, msgMap, AbstractSendMessageAction.getPalette(inParam), senderData.getReference(), theObjectData.getReference(), title);

			} else {
				final FilesData theFilesTTS = FilesData.postTTS(senderData, text, title, voiceData, saveMp3, true, true);
				ManageMessageServices.sendUserMessageAndNotification(senderData, theObjectData, theFilesTTS, timeOfDelivery, colorPalette, title, false);
			}

		}

		return null;
	}

	@Override
	public ActionType getType() {
		return ActionType.CREATE;
	}

	@Override
	public boolean isCacheable() {
		return false;
	}

	@Override
	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}

}
