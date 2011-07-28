package net.violet.platform.api.actions.subscriptions;

import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.maps.applications.ApplicationSubscriptionMap;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;

import org.apache.log4j.Logger;

public class Create extends AbstractSubscriptionAction {

	private static final Logger LOGGER = Logger.getLogger(Create.class);

	@APIMethodParam
	public static final String OBJECT_ID = "object_id";
	@APIMethodParam
	public static final String APPLICATION_ID = "application_id";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchApplicationException, NoSuchObjectException {

		final ApplicationData theApplication = getRequestedApplication(inParam, Create.APPLICATION_ID);
		final VObjectData theObject = getRequestedObject(inParam, Create.OBJECT_ID);

		final Map<String, Object> settings = inParam.getMap("settings", true);
		final List<Map<String, Object>> schedulings = inParam.getList("scheduling", true);

		SubscriptionData theSubscription;
		try {
			theSubscription = SubscriptionManager.createSubscription(theApplication, theObject, settings, schedulings, inParam.getCallerAPIKey());
			return new ApplicationSubscriptionMap(inParam.getCaller(), theSubscription);
		} catch (final InvalidSettingException e) {
			Create.LOGGER.fatal(e, e);
		} catch (final InvalidSchedulingsException e) {
			Create.LOGGER.fatal(e, e);
		} catch (final MissingSettingException e) {
			Create.LOGGER.fatal(e, e);
		}

		throw new InvalidParameterException("invalid");

	}

	public long getExpirationTime() {
		return 0L;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

}
