package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.SubscriptionSchedulingFactory;
import net.violet.platform.datamodel.mock.SubscriptionSchedulingMock;

public class SubscriptionSchedulingFactoryMock extends RecordFactoryMock<SubscriptionScheduling, SubscriptionSchedulingMock> implements SubscriptionSchedulingFactory {

	SubscriptionSchedulingFactoryMock() {
		super(SubscriptionSchedulingMock.class);
	}

	public SubscriptionScheduling create(Subscription inSubscription, SchedulingType.SCHEDULING_TYPE inType) {
		return new SubscriptionSchedulingMock(0, inSubscription, inType);
	}

	public List<SubscriptionScheduling> findAllBySubscription(Subscription inSubscription) {
		final List<SubscriptionScheduling> theResult = new LinkedList<SubscriptionScheduling>();

		for (final SubscriptionScheduling theSubsSchedul : findAll()) {
			if (theSubsSchedul.getSubscription().getId().longValue() == inSubscription.getId().longValue()) {
				theResult.add(theSubsSchedul);
			}
		}
		return theResult;
	}

	public List<SubscriptionScheduling> findAllBySubscriptionAndType(Subscription inSubscription, SchedulingType.SCHEDULING_TYPE inType) {
		final List<SubscriptionScheduling> theResult = new LinkedList<SubscriptionScheduling>();

		for (final SubscriptionScheduling theSubsSchedul : findAll()) {
			if ((theSubsSchedul.getSubscription().getId() == inSubscription.getId()) && theSubsSchedul.getType().equals(inType)) {
				theResult.add(theSubsSchedul);
			}
		}
		return theResult;
	}

	public List<SubscriptionScheduling> findAllByObjectAndType(VObject object, SCHEDULING_TYPE type) {
		final List<SubscriptionScheduling> theSchedulings = new LinkedList<SubscriptionScheduling>();
		for (final SubscriptionScheduling aScheduling : findAll()) {
			if (aScheduling.getSubscription().getObject().equals(object) && (aScheduling.getType() == type)) {
				theSchedulings.add(aScheduling);
			}
		}
		return theSchedulings;
	}

}
