package net.violet.platform.schedulers;

import java.util.HashMap;
import java.util.Map;

import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.maps.SchedulingInformationMap;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.message.application.factories.AbstractMessageFactory;
import net.violet.platform.message.application.factories.AbstractMessageFactory.ACTION;
import net.violet.platform.message.application.factories.AbstractMessageFactory.MESSAGE_TYPE;

public class AmbiantHandler implements SchedulingHandler {

	public static final String LAST_TIME = "last_time";

	public void deleteElements(SubscriptionSchedulingData scheduling) {
		final ThreadWatcher theWatcher = new ThreadWatcher();

		final MessageProcessUnit theMessage = new MessageProcessUnit(scheduling, null, theWatcher, ACTION.REMOVE) {

			@Override
			public void runWhenSuccessful() {
				stopWatching();
				processed();
			}
		};

		theMessage.startWatching();
		theMessage.processing();
		AbstractMessageFactory.sendMessage(theMessage, MESSAGE_TYPE.SOURCE_MESSAGE);
		theWatcher.await();
	}

	public Map<String, String> generateSettings(VObjectData object, Map<String, Object> settings, String callerKey) {
		final Map<String, String> theResult = new HashMap<String, String>();

		if (settings.containsKey(AmbiantHandler.LAST_TIME)) {
			theResult.put(AmbiantHandler.LAST_TIME, settings.get(AmbiantHandler.LAST_TIME).toString());
		} else {
			theResult.put(AmbiantHandler.LAST_TIME, "0");
		}

		return theResult;
	}

	public void checkSettings(VObjectData object, Map<String, Object> settings, String callerKey) throws MissingSettingException {
		// nothing to do here
	}

	public void executeWhenDone(SubscriptionSchedulingData scheduling) {
		final ThreadWatcher theWatcher = new ThreadWatcher();

		final MessageProcessUnit theMessage = new MessageProcessUnit(scheduling, null, theWatcher, ACTION.ADD) {

			@Override
			public void runWhenSuccessful() {
				stopWatching();
				processed();
			}
		};

		theMessage.startWatching();
		theMessage.processing();
		AbstractMessageFactory.sendMessage(theMessage, MESSAGE_TYPE.SOURCE_MESSAGE);
		theWatcher.await();
	}

	public SchedulingInformationMap getSchedulingInformation(SubscriptionSchedulingData scheduling, APICaller caller) {
		return new SchedulingInformationMap(scheduling);
	}

}
