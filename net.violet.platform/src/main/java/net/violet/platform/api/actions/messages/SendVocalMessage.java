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
import net.violet.platform.api.exceptions.VocalMessageConversionFailedException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.StringShop;
import net.violet.vlisp.services.ManageMessageServices;

/**
 * This action is used to send vocal messages from a User to one or several
 * objects.
 */
public class SendVocalMessage extends AbstractSendMessageAction {

	@Override
	public Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchObjectException, ParentalFilterException, BlackListedException, NoSuchPersonException, InternalErrorException, VocalMessageConversionFailedException {

		final APICaller apiCaller = inParam.getCaller();
		final UserData senderUserData = getSender(inParam);
		final List<VObjectData> recipients = getRecipients(inParam);

		String theTitle = inParam.getString("title", false);

		if (theTitle == null) {
			theTitle = net.violet.common.StringShop.EMPTY_STRING;
		}

		final Date theDeliveryDate = inParam.getDate("time_of_delivery");

		final Palette theColorPalette = AbstractSendMessageAction.getPalette(inParam);

		final FilesData theFilesMp3Vocal = getFilesMp3Vocal(inParam);

		for (final VObjectData destObjectData : recipients) {

			if (destObjectData.getObjectType().instanceOf(ObjectType.RFID)) {

				final Map<String, Object> msgMap = getMessageAsPojoMap(inParam);
				msgMap.put("from", senderUserData.getApiId(apiCaller));
				msgMap.put("to", Arrays.asList(destObjectData.getApiId(apiCaller)));

				// store the message in its JSON form to be replayed later
				final Files msgBodyContent = AbstractSendMessageAction.postPojoMessage(msgMap);

				if (msgBodyContent == null) {
					throw new InternalErrorException("File creation failed");
				}

				ManageMessageServices.putInbox(msgBodyContent, msgMap, AbstractSendMessageAction.getPalette(inParam), senderUserData.getReference(), destObjectData.getReference(), "Vocal Delivery");

			} else {

				ManageMessageServices.sendUserMessageAndNotification(senderUserData, destObjectData, theFilesMp3Vocal, theDeliveryDate, theColorPalette, theTitle, true);
			}

		}

		return null;
	}

	private FilesData getFilesMp3Vocal(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException, InternalErrorException, VocalMessageConversionFailedException {
		final UserData theSessionUser = getSender(inParam);
		final String theTitle = inParam.getString("title", true);
		//TODO : changer la façon de recevoir le fichier flv, on doit recevoir le lien direct et 
		// non le nom unique ( renommer le paramètre aussi, en attente que Nuxos soit dispo) 
		final String flvName = inParam.getString("flv_name", true);
		final boolean saveMp3 = inParam.getBoolean("save", false);
		final String theUrl = FilesData.VOCAL_RECORDER + flvName + StringShop.FLV_EXT;
		return FilesData.postFLV(((saveMp3) ? theSessionUser : null), theTitle, theUrl);
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

	@Override
	protected Map<String, Object> getMessageAsPojoMap(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, InternalErrorException {

		final Date deliveryDate = inParam.getDate("time_of_delivery", new Date());
		final String title = inParam.getString("title", "Music Delivery");

		final FilesData filesData;

		try {
			filesData = getFilesMp3Vocal(inParam);
		} catch (final VocalMessageConversionFailedException e) {
			throw new InternalErrorException(e);
		}

		/*
		 * construit le message pojo
		 */
		final Map<String, Object> seqMap = new HashMap<String, Object>();
		seqMap.put("type", "expression");
		seqMap.put("modality", "net.violet.sound.mp3");
		seqMap.put("url", filesData.getPath());
		seqMap.put("streaming", true);

		final Map<String, Object> thePojoMessage = new HashMap<String, Object>();

		thePojoMessage.put("date", deliveryDate);
		thePojoMessage.put("title", title);

		thePojoMessage.put("sequence", Arrays.asList((Object) seqMap));

		return thePojoMessage;

	}

}
