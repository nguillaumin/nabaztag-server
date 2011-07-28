package net.violet.platform.api.actions.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.subscriptions.Get;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.applications.ApplicationSubscriptionMap;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Test;

public class GetServices {

	@Test
	public void getServices() {

		final ApplicationCredentialsData CREDENTIALS = ApplicationCredentialsData.getData(Factories.APPLICATION_CREDENTIALS.findByPublicKey("VAdmin")); // new applica admin
		final APICaller CALLER = new ApplicationAPICaller(CREDENTIALS);

		//final Action theAction = new Get();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", "12627Oe20d23a5");
		theParams.put("application_id", "81fA324dc3a5");
		final ActionParam theActionParam = new ActionParam(CALLER, theParams);

		try {
			final List<ApplicationSubscriptionMap> subscriptions = (List<ApplicationSubscriptionMap>) new Get().processRequest(theActionParam);
			System.err.println("------------------------------------------------------------------------------->SIZE :" + subscriptions.size());
			for (final ApplicationSubscriptionMap aSubscription : subscriptions) {
				final Map<String, Object> subscriptionSettings = (Map<String, Object>) aSubscription.get("settings");
				for (final Entry<String, Object> aSetting : subscriptionSettings.entrySet()) {
					System.err.println("setting= " + aSetting.getKey());
				}
				if (subscriptionSettings.containsKey("url")) {
					System.err.println("------------------------------------------------------------------------------->I HAVE ONE");
					final String actionId = (String) subscriptionSettings.get("url");
					System.err.println(actionId);
//					final VAction theVAction = Factories.VACTION.find(Long.parseLong(actionId));
//					System.err.println(theVAction.getId().toString() + " " + theVAction.getLang().getId().toString() + " " + theVAction.getUrl());
//					break;
				}
			}
		} catch (final APIException e) {
			System.err.println("------------------------------------------------------------------------------->CRASH : " + e.getMessage());
		}
	}
}
