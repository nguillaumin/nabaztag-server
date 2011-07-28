package net.violet.platform.api.actions.contacts;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchContactException;
import net.violet.platform.dataobjects.ContactData;

public abstract class AbstractContactAction extends AbstractAction {

	/**
	 * @param inParam
	 * @return
	 * @throws InvalidParameterException
	 * @throws NoSuchContactException
	 * @throws APIException
	 */
	protected ContactData getRequestedContact(ActionParam inParam) throws InvalidParameterException, NoSuchContactException {

		final String contactId = inParam.getMainParamAsString();

		final ContactData contact = ContactData.findByAPIId(contactId, inParam.getCaller().getAPIKey());

		if (contact == null) {
			throw new NoSuchContactException();
		}

		return contact;
	}

}
