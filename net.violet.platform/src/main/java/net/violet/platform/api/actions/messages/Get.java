package net.violet.platform.api.actions.messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.StatusMessage;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

public class Get extends AbstractMessageAction {

	private static final Logger LOGGER = Logger.getLogger(Get.class);

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException, NoSuchObjectException {

		final APICaller caller = inParam.getCaller();
		final UserData userData = SessionManager.getUserFromSessionParam(inParam);

		// Message status filter : INBOX, ARCHIVED or SENT
		final StatusMessage msgStatusFilter = StatusMessage.getStatusByName(inParam.getMainParamAsString());

		final int skip = inParam.getInt("skip", 0);
		final int count = inParam.getInt("count", 10);

		final String objectID = inParam.getString("object", null);

		VObjectData objectData = VObjectData.getData(null);
		if (objectID != null) {
			objectData = VObjectData.findByAPIId(objectID, inParam.getCallerAPIKey());
			if ((objectData == null) || !objectData.isValid()) {
				throw new NoSuchObjectException();
			}
		}

		// Look in the correct mail box
		final List<MessageData> messagesData;

		if ((msgStatusFilter == StatusMessage.ARCHIVED) || (msgStatusFilter == StatusMessage.INBOX)) {
			messagesData = MessageData.findAllMessageReceivedOrArchivedByStatus(msgStatusFilter, userData, objectData, skip, count);
		} else {
			messagesData = MessageData.findAllMessageSent(userData, skip, count); // messages envoyés
		}

		/*
		 * Create MailboxMessageInformationMap with the messages data note : we
		 * add the pojo content only for native applications
		 */
		final List<MailboxMessageInformationMap> messagesInformationMap = new ArrayList<MailboxMessageInformationMap>(messagesData.size());
		final boolean loadPojoContent = (caller.isPlatformComponent() || (caller.getApplicationClass() == Application.ApplicationClass.NATIVE));
		if (Get.LOGGER.isDebugEnabled()) {
			Get.LOGGER.debug("Get messages for caller " + caller + ". Load POJO content : " + loadPojoContent);
		}

		for (final MessageData msgData : messagesData) {
			if ((msgData.getSender() != null) && (msgData.getRecipient() != null)) {
				messagesInformationMap.add(new MailboxMessageInformationMap(msgData, caller, loadPojoContent));
			}
		}

		return messagesInformationMap;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	/**
	 * Informations expires in instant
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return 0;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	private static class MailboxMessageInformationMap extends AbstractAPIMap {

		public MailboxMessageInformationMap(MessageData inMessage, APICaller inCaller, boolean withContent) {
			super();

			put("id", inMessage.getApiId(inCaller));
			put("from", inMessage.getSender().getApiId(inCaller));

			// TODO Changer lorsqu'on aura la possibilité d'envoyer un message sur
			// plusieurs objets
			put("to", Collections.singletonList((Object) inMessage.getRecipient().getApiId(inCaller)));
			put("title", inMessage.getTitle());

			put("preview", net.violet.common.StringShop.EMPTY_STRING); // TODO information ???

			put("count", inMessage.getNbPlayed());
			put("time_of_delivery", inMessage.getDateOfDelivery());

			if (withContent) {
				final Files messageBody = inMessage.getMessage().getBody();
				final String jsonBody = FilesManagerFactory.FILE_MANAGER.getTextContent(messageBody);
				try {
					put("content", ConverterFactory.JSON.<Map<String, Object>> convertFrom(jsonBody));
				} catch (final ConversionException e) {}
			}
		}

	}
}
