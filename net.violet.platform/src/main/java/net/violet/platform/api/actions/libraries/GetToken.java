package net.violet.platform.api.actions.libraries;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

/**
 * Used to generate a token in order to upload it on our servers
 */
public class GetToken extends AbstractItemAction {

	public static final Map<UserData, String> TOKEN_CACHE = new WeakHashMap<UserData, String>();

	//TODO temporary desactivate, don't change exception throws in order to match with specification  
	
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException {
		//final UserData theUserData = SessionManager.getUserFromSessionParam(inParam);

		final String theToken = StringShop.EMPTY_STRING + System.currentTimeMillis() + Math.random();

		//TODO désactiver, en attendant le nouveau cache!! 
		//GetToken.TOKEN_CACHE.put(theUserData, theToken);

		return theToken;
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

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}
}
