package net.violet.platform.api.actions.events;

import java.util.List;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.events.Event;
import net.violet.platform.events.EventsFactory;
import net.violet.platform.events.handlers.EventsDispatcher;

import org.apache.log4j.Logger;

public class Send extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(Send.class);

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws APIException {

		final String eventName = inParam.getMainParamAsString();
		final PojoMap eventProperties = inParam.getPojoMap("event_properties", true);

		try {
			final Event event = EventsFactory.create(eventName, eventProperties);
			EventsDispatcher.processEvent(event);

		} catch (final Exception ettoetto) {
			Send.LOGGER.fatal("Error when sending custom event " + eventName + ":" + eventProperties, ettoetto);
			throw (ettoetto instanceof APIException) ? (APIException) ettoetto : new APIException(ettoetto.getMessage());
		}

		return true;
	}

	public ActionType getType() {
		return ActionType.GET;
	}

	public long getExpirationTime() {
		return 0;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_ALL;
	}
}
