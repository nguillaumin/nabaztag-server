package net.violet.platform.api.actions.subscriptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.AbstractAction.APIMethodParam.Level;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.api.maps.applications.ApplicationSubscriptionMap;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

public class Get extends AbstractSubscriptionAction {

	@APIMethodParam(Level.OPTIONAL)
	public static final String APPLICATION_ID = "application_id";
	@APIMethodParam(Level.OPTIONAL)
	public static final String SUBSCRIPTION_ID = "subscription_id";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchApplicationException, NoSuchObjectException, NoSuchSubscriptionException {
		final VObjectData theObject = getRequestedObject(inParam, null);

		final Collection<SubscriptionData> theSubscriptions;
		if (inParam.hasParam(Get.SUBSCRIPTION_ID)) {
			final SubscriptionData theSubscription = getRequestedSubscription(inParam, Get.SUBSCRIPTION_ID);
			theSubscriptions = Collections.singletonList(theSubscription);
		} else if (inParam.hasParam(Get.APPLICATION_ID)) {
			final ApplicationData theApplication = getRequestedApplication(inParam, Get.APPLICATION_ID);
			theSubscriptions = SubscriptionData.findByApplicationAndObject(theApplication.getReference(), theObject.getReference());
		} else {
			theSubscriptions = SubscriptionData.findAllByObject(theObject.getReference());
		}

		final List<ApplicationSubscriptionMap> theResult = new ArrayList<ApplicationSubscriptionMap>(theSubscriptions.size());
		for (final SubscriptionData theSubscription : theSubscriptions) {
			try {
				theResult.add(new ApplicationSubscriptionMap(inParam.getCaller(), theSubscription));
			} catch (final Exception e) {
				// this subscription is not properly retrievable, it has been corrupted so we DELETE it !
				SubscriptionManager.deleteSubscription(theSubscription);
			}
		}
		return theResult;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return false;
	}

	/**
	 * TODO : Applications may access their OWN subscriptions !
	 * 
	 * @see net.violet.platform.api.actions.AbstractAction#getAuthorizedApplicationClasses()
	 */
	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
