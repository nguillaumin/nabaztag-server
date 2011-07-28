package net.violet.platform.api.actions.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.StatusMessage;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.Templates;

import org.apache.log4j.Logger;

public class MarkAsRead extends AbstractMessageAction {

	private static final Logger LOGGER = Logger.getLogger(MarkAsRead.class);

	/**
	 * Read the content of multiple inbox messages 
	 * Notify the message sender of their lecture
	 * Optionally archive the messages
	 * 
	 * @return the list of POJO content of each message
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchMessageException, NoSuchObjectException {

		final APICaller caller = inParam.getCaller();
		final String readerId = inParam.getMainParamAsString();
		final VObjectData readerObject = VObjectData.findByAPIId(readerId, caller.getAPIKey());
		if ((readerObject == null) || !readerObject.isValid()) {
			throw new NoSuchObjectException();
		}

		final List<MessageData> messages = getMessagesLike(inParam, StatusMessage.INBOX);

		final boolean archive = inParam.getBoolean("archive", true);

		final Messenger theReceiver = Factories.MESSENGER.getByObject(readerObject.getReference());

		final List<Map<String, Object>> messagesList = new ArrayList<Map<String, Object>>(messages.size());

		for (final MessageData message : messages) {
			final Files messageBody = message.getMessage().getBody();
			MarkAsRead.LOGGER.debug("Fichier JSON contenant le corps du message (" + messageBody.getId() + ") : " + messageBody.getPath());

			// message body is stored in JSON text format (POJO)
			final String jsonBody = FilesManagerFactory.FILE_MANAGER.getTextContent(messageBody);
			try {
				final Map<String, Object> pojoMsgMap = ConverterFactory.JSON.convertFrom(jsonBody);
				pojoMsgMap.put("to", readerId);
				messagesList.add(pojoMsgMap);
			} catch (final ConversionException e) {
				MarkAsRead.LOGGER.error("POJO message found didn't conform to message structure : " + jsonBody, e);
			}

			// On archive
			if (archive) {
				message.getMessage().setCount(message.getMessage().getCount() + 1);
				final MessageReceived messageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(message.getMessage().getId());
				Factories.MESSAGE_COUNTER.get(Factories.MESSENGER.getByObject(message.getRecipient().getReference()).getId()).invalidateDecrement();
				messageReceived.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED);
			}

			// Email message a été joué
			final User sender = message.getSender().getReference();
			if (sender.getNotifyMessageReceived()) {
				Templates.messagePlayed(theReceiver.getObject(), sender, message.getTitle(), (int) message.getNbPlayed());
			}
		}

		return messagesList;
	}

	/**
	 * @param inParam
	 * @param inStatus
	 * @return
	 * @throws InvalidParameterException
	 * @throws NoSuchMessageException
	 */
	private List<MessageData> getMessagesLike(ActionParam inParam, StatusMessage inStatus) throws InvalidParameterException, NoSuchMessageException {

		final List<String> messageIds = inParam.getList("messageIds", true);
		final List<MessageData> messages = new ArrayList<MessageData>(messageIds.size());

		for (final String messageId : messageIds) {
			final MessageData message = MessageData.findByAPIId(messageId, inParam.getCaller().getAPIKey(), inStatus);
			if (message == null) {
				throw new NoSuchMessageException(APIErrorMessage.NO_SUCH_MESSAGE);
			}
			messages.add(message);
		}

		return messages;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

}
