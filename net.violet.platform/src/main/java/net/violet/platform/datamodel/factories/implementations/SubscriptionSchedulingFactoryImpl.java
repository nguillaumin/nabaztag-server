package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.SubscriptionSchedulingFactory;

import org.apache.log4j.Logger;

public class SubscriptionSchedulingFactoryImpl extends RecordFactoryImpl<SubscriptionScheduling, SubscriptionSchedulingImpl> implements SubscriptionSchedulingFactory {

	private static Logger LOGGER = Logger.getLogger(SubscriptionSchedulingFactoryImpl.class);

	public SubscriptionSchedulingFactoryImpl() {
		super(SubscriptionSchedulingImpl.SPECIFICATION);
	}

	public SubscriptionScheduling create(Subscription inSubscription, SchedulingType.SCHEDULING_TYPE inType) {
		SubscriptionScheduling theResult = null;
		try {
			theResult = new SubscriptionSchedulingImpl(inSubscription, inType);
		} catch (final SQLException e) {
			SubscriptionSchedulingFactoryImpl.LOGGER.fatal(e, e);
		}
		return theResult;
	}

	public List<SubscriptionScheduling> findAllBySubscription(Subscription inSubscription) {
		return findAll("subscription_id = ?", Collections.singletonList((Object) inSubscription.getId()));
	}

	public List<SubscriptionScheduling> findAllBySubscriptionAndType(Subscription inSubscription, SchedulingType.SCHEDULING_TYPE inType) {
		return findAll("subscription_id = ? AND scheduling_type_id = ? ", Arrays.asList(new Object[] { inSubscription.getId(), inType.getRecord().getId() }));
	}

	public List<SubscriptionScheduling> findAllByObjectAndType(VObject object, SCHEDULING_TYPE type) {
		final String condition = " subscription.object_id = ? AND subscription.id = subscription_scheduling.subscription_id AND subscription_scheduling.scheduling_type_id = ? ";
		return findAll(new String[] { "subscription" }, condition, Arrays.asList(new Object[] { object.getId(), type.getRecord().getId() }), null);
	}

}
