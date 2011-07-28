package net.violet.platform.api.actions.subscriptions;

import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ApplicationCantBeRemovedException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.SubscriptionData;

public class Delete extends AbstractSubscriptionAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchSubscriptionException, ApplicationCantBeRemovedException {

		final SubscriptionData theSubscriptionData = getRequestedSubscription(inParam);

		if (!theSubscriptionData.getApplication().isRemovable()) {
			throw new ApplicationCantBeRemovedException();
		}

		SubscriptionManager.deleteSubscription(theSubscriptionData);

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.DELETE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
