package net.violet.vadmin.actions;

import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

public class Admin {

	// Paramètre d'authentification pour l'application webui
	private static final ApplicationCredentialsData CREDENTIALS = ApplicationCredentialsData.getData(Factories.APPLICATION_CREDENTIALS.findByPublicKey("VAdmin")); // new applica admin
	protected static final APICaller CALLER = new ApplicationAPICaller(Admin.CREDENTIALS);

	private Admin() {
		//	This space for rent		
	}

	public static Object processRequest(Action inAction, Map<String, Object> inMap) throws APIException {
		final ActionParam theActionParam = new ActionParam(Admin.CALLER, inMap);
		return inAction.processRequest(theActionParam);

	}
}
