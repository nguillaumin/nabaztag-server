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
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.exceptions.ParentalFilterException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Music;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.vlisp.services.ManageMessageServices;

/**
 * This action is used to send little words, library , personal or shared mp3
 * messages from a User to one or several objects.
 */
public class SendMusicMessage extends AbstractSendMessageAction {

	/**
	 * @param inParam
	 * @return
	 */
	private MusicData getMusicData(ActionParam inParam) throws InvalidParameterException, NoSuchItemException {

		final String itemAPIId = inParam.getMainParamAsString();
		final APICaller caller = inParam.getCaller();

		final MusicData musicData = MusicData.findByAPIId(itemAPIId, caller.getAPIKey());
		if (musicData == null) {
			throw new NoSuchItemException();
		}
		return musicData;
	}

	/**
	 * @throws InvalidParameterException
	 * @throws InvalidSessionException
	 * @throws ForbiddenException
	 * @throws NoSuchItemException
	 * @see net.violet.platform.api.actions.messages.AbstractSendMessageAction#getMessageAsPojoMap(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Map<String, Object> getMessageAsPojoMap(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, NoSuchItemException {

		final UserData userData = getSender(inParam);

		final Date deliveryDate = inParam.getDate("time_of_delivery", new Date());
		final String title = inParam.getString("title", "Music Delivery");

		/*
		 * Récupère le fichier à jouer et vérifie que c'est bien un mp3 perso de
		 * l'utilisateur
		 */
		final MusicData musicData = getMusicData(inParam);
		if ((musicData.getMusic_type() == Music.TYPE_MP3_USER_LIBRARY) && (musicData.getMusic_share() == 0) && !userData.equals(musicData.getOwner())) {
			throw new ForbiddenException();
		}

		/*
		 * construit le message pojo
		 */
		final Map<String, Object> seqMap = new HashMap<String, Object>();
		seqMap.put("type", "expression");
		seqMap.put("modality", "net.violet.sound.mp3");
		seqMap.put("url", musicData.getMusic_url());
		seqMap.put("streaming", true);

		final Map<String, Object> thePojoMessage = new HashMap<String, Object>();

		thePojoMessage.put("date", deliveryDate);
		thePojoMessage.put("title", title);
		thePojoMessage.put("sequence", Arrays.asList((Object) seqMap));

		return thePojoMessage;

	}

	@Override
	public Object doProcessRequest(ActionParam inParam) throws NoSuchObjectException, InvalidParameterException, ForbiddenException, InvalidSessionException, ParentalFilterException, BlackListedException, NoSuchPersonException, NoSuchItemException, InternalErrorException {

		final APICaller apiCaller = inParam.getCaller();
		final UserData senderUserData = getSender(inParam);
		final List<VObjectData> recipients = getRecipients(inParam);

		for (final VObjectData destObjectData : recipients) {

			if (destObjectData.getObjectType().instanceOf(ObjectType.RFID)) {

				final Map<String, Object> msgMap = getMessageAsPojoMap(inParam);
				msgMap.put("from", senderUserData.getApiId(apiCaller));
				msgMap.put("to", Arrays.asList(destObjectData.getApiId(apiCaller))); // must be a list !

				// store the message in its JSON form to be replayed later
				final Files msgBodyContent = AbstractSendMessageAction.postPojoMessage(msgMap);

				if (msgBodyContent == null) {
					throw new InternalErrorException("File creation failed");
				}

				ManageMessageServices.putInbox(msgBodyContent, msgMap, AbstractSendMessageAction.getPalette(inParam), senderUserData.getReference(), destObjectData.getReference(), "Music Delivery");

			} else {

				final MusicData theMusicData = getMusicData(inParam);

				final Date theDeliveryDate = inParam.getDate("time_of_delivery");
				final Palette theColorPalette = AbstractSendMessageAction.getPalette(inParam);

				// permet de ne pas envoyer des mp3 perso d'autre utilisateur
				if ((theMusicData.getMusic_type() == Music.TYPE_MP3_USER_LIBRARY) && (theMusicData.getMusic_share() == 0) && !senderUserData.equals(theMusicData.getOwner())) {
					throw new ForbiddenException();
				}

				ManageMessageServices.sendUserMessageAndNotification(senderUserData, destObjectData, theMusicData.getFile(), theDeliveryDate, theColorPalette, theMusicData.getMusic_name(), AbstractSendMessageAction.isStreamFile(theMusicData));

			}

		}

		return null;
	}

}
