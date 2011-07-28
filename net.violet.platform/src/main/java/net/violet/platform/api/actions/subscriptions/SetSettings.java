package net.violet.platform.api.actions.subscriptions;

import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.dataobjects.SubscriptionData;

import org.apache.log4j.Logger;

public class SetSettings extends AbstractSubscriptionAction {

	private static final Logger LOGGER = Logger.getLogger(SetSettings.class);

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchSubscriptionException {

		final SubscriptionData subscr = getRequestedSubscription(inParam);

		final Map<String, Object> settings = inParam.getMap("settings", true);

		//FIXME
		settings.remove("length");

		try {
			if (SetSettings.LOGGER.isDebugEnabled()) {
				SetSettings.LOGGER.debug("Update subscription " + subscr.getId() + " with settings " + settings);
			}
			SubscriptionManager.updateSubscription(subscr, settings, null, inParam.getCaller());

		} catch (final InvalidSettingException e) {
			SetSettings.LOGGER.fatal(e, e);
			throw new InvalidParameterException(e.getMessage());
		} catch (final MissingSettingException e) {
			SetSettings.LOGGER.fatal(e, e);
			throw new InvalidParameterException(e.getMessage());
		} catch (final InvalidSchedulingsException e) {
			SetSettings.LOGGER.fatal(e, e);
			throw new InvalidParameterException(e.getMessage());
		}

		return null;
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

}
