package net.violet.platform.api.actions.messages;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.MessageData.StatusMessage;

public abstract class AbstractMessageAction extends AbstractAction {

	/**
	 * @param inParam
	 * @return
	 * @throws InvalidParameterException
	 * @throws NoSuchMessageException
	 * @throws APIException
	 */
	protected MessageData getRequestedMessage(ActionParam inParam, StatusMessage inStatus) throws InvalidParameterException, NoSuchMessageException {

		final String messageId = inParam.getMainParamAsString();

		final MessageData message = MessageData.findByAPIId(messageId, inParam.getCaller().getAPIKey(), inStatus);

		if (message == null) {
			throw new NoSuchMessageException(APIErrorMessage.NO_SUCH_MESSAGE);
		}

		return message;
	}

}
