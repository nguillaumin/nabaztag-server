package net.violet.platform.api.actions.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.message.AddressedMessage;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.vlisp.services.ManageMessageServices;

import org.apache.log4j.Logger;

/**
 * This action is used to send messages from a User to one or several objects.
 */
public class SendMessage extends AbstractSendMessageAction {

	private static final Logger LOGGER = Logger.getLogger(SendMessage.class);

	/**
	 * @param inParam
	 * @return
	 * @throws InvalidParameterException
	 * @throws ConversionException
	 */
	@Override
	public Map<String, Object> getMessageAsPojoMap(ActionParam inParam) throws InvalidParameterException {
		try {
			final Map<String, Object> msgMap = inParam.getMap("message", true);
			return ConverterFactory.JSON.convertFrom(ConverterFactory.JSON.convertTo(msgMap));
		} catch (final ConversionException e) {
			SendMessage.LOGGER.error(e, e);
			throw new InvalidParameterException(APIErrorMessage.INVALID_MESSAGE, e);
		}
	}

	/**
	 * 
	 * @see net.violet.platform.api.actions.messages.AbstractSendMessageAction#getRecipients(com.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected List<VObjectData> getRecipients(ActionParam inParam) throws NoSuchObjectException, InvalidParameterException {

		final List<Object> recipientsIds = inParam.getList("message.to", true);

		final String callerAPIKey = inParam.getCallerAPIKey();
		final List<VObjectData> recipients = new ArrayList<VObjectData>();

		for (final Object id : recipientsIds) {
			final VObjectData anObject = VObjectData.findByAPIId((String) id, callerAPIKey);
			if ((anObject == null) || !anObject.isValid()) {
				throw new NoSuchObjectException();
			}
			recipients.add(anObject);
		}
		return recipients;
	}

	/**
	 * @throws InvalidParameterException
	 * @see net.violet.platform.api.actions.messages.AbstractSendMessageAction#
	 *      getSender(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected UserData getSender(ActionParam inParam) throws InvalidParameterException {
		final APICaller apiCaller = inParam.getCaller();
		return UserData.findByAPIId(inParam.getString("message.from", true), apiCaller.getAPIKey());
	}

	@Override
	public Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchPersonException, InternalErrorException, NoSuchObjectException {

		final List<VObjectData> recipients = getRecipients(inParam);
		final UserData theSessionUser = getSender(inParam);
		final APICaller apiCaller = inParam.getCaller();

		for (final VObjectData destObjectData : recipients) {
			final Map<String, Object> msgMap = getMessageAsPojoMap(inParam);
			msgMap.put("to", Arrays.asList(destObjectData.getApiId(apiCaller))); // must be a list !

			if (inParam.getBoolean("mailboxmessage", false)) {
				// store the message in its JSON form to be replayed later
				final Files msgBodyContent = AbstractSendMessageAction.postPojoMessage(msgMap);

				if (msgBodyContent == null) {
					SendMessage.LOGGER.error("Action SendMessage : " + msgMap + " caused an unexpected error");
					throw new InternalErrorException("Your message couldn't be sent.");
				}

				ManageMessageServices.putInbox(msgBodyContent, msgMap, AbstractSendMessageAction.getPalette(inParam), theSessionUser.getReference(), destObjectData.getReference(), (String) msgMap.get("title"));
			}

			if (!destObjectData.getObjectType().instanceOf(ObjectType.RFID)) {
				// Sends the messages immediately
				for (final AddressedMessage aMessage : MessageDraft.createFromPojo(msgMap, null, inParam.getCaller().getAPIKey())) {
					MessageServices.send(aMessage);
					afterMessageSent(aMessage);
				}
			}

		}

		return null;
	}

}
