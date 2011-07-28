package net.violet.platform.dataobjects;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import net.violet.platform.datamodel.SubscriptionLog;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class SubscriptionLogData extends RecordData<SubscriptionLog> implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionLogData.class);

	public static SubscriptionLogData getData(SubscriptionLog inSubscriptionLog) {
		try {
			return RecordData.getData(inSubscriptionLog, SubscriptionLogData.class, SubscriptionLog.class);
		} catch (final InstantiationException e) {
			SubscriptionLogData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			SubscriptionLogData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			SubscriptionLogData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			SubscriptionLogData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected SubscriptionLogData(SubscriptionLog subscriptionLog) {
		super(subscriptionLog);
	}

	public static SubscriptionLogData getBySubscription(SubscriptionData subscription) {
		SubscriptionLog theLog = Factories.SUBSCRIPTION_LOG.find(subscription.getId());
		if (theLog == null) {
			theLog = Factories.SUBSCRIPTION_LOG.create(subscription.getRecord());
		}

		return SubscriptionLogData.getData(theLog);
	}

	public FilesData getLogFile() {
		final SubscriptionLog theLog = getRecord();
		if ((theLog != null) && (theLog.getLogFile() != null)) {
			return FilesData.getData(theLog.getLogFile());
		}

		return null;
	}
}
