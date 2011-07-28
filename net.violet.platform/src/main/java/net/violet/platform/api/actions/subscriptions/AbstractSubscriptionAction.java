package net.violet.platform.api.actions.subscriptions;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.dataobjects.SubscriptionData;

public abstract class AbstractSubscriptionAction extends AbstractAction {

	protected SubscriptionData getRequestedSubscription(ActionParam inParam) throws NoSuchSubscriptionException, InvalidParameterException {

		final String subscriptionId = inParam.getMainParamAsString();

		final SubscriptionData theSubscription = SubscriptionData.findByAPIId(subscriptionId, inParam.getCallerAPIKey());

		if (theSubscription == null) {
			throw new NoSuchSubscriptionException();
		}

		return theSubscription;
	}

	protected SubscriptionData getRequestedSubscription(ActionParam inParam, String paramName) throws NoSuchSubscriptionException, InvalidParameterException {

		final String subscriptionId = (paramName == null) ? inParam.getMainParamAsString() : inParam.getString(paramName, true);

		final SubscriptionData subscrData = SubscriptionData.findByAPIId(subscriptionId, inParam.getCallerAPIKey());

		if (subscrData == null) {
			throw new NoSuchSubscriptionException();
		}

		return subscrData;
	}
}
