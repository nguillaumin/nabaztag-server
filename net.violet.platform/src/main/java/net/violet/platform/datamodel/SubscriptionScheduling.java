package net.violet.platform.datamodel;

import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.Record;

/**
 * Scheduling type for installed applications
 * 
 *
 */
public interface SubscriptionScheduling extends Record<SubscriptionScheduling> {

	enum SchedulingPossibility {
		UNSCHEDULABLE("Unschedulable"),
		AT_MOST_ONCE("AtMostOnce"),
		AT_MOST_ONCE_PER_INSTANCE("AtMostOncePerInstance"),
		AT_MOST_TWICE_PER_INSTANCE("AtMostTwicePerInstance"),
		UNCONFIGURABLE("Unconfigurable");

		private final String mName;

		private SchedulingPossibility(String inName) {
			this.mName = inName;
		}

		public String getName() {
			return this.mName;
		}

		private static final Map<String, SchedulingPossibility> SCHEDULING_POSSIBILITY = new HashMap<String, SchedulingPossibility>();
		static {
			for (final SchedulingPossibility aType : SchedulingPossibility.values()) {
				SchedulingPossibility.SCHEDULING_POSSIBILITY.put(aType.name(), aType);
			}
		}

		public static SchedulingPossibility findByName(String inName) {
			return SchedulingPossibility.SCHEDULING_POSSIBILITY.get(inName);
		}

	};

	Subscription getSubscription();

	SchedulingType.SCHEDULING_TYPE getType();

	void setType(SchedulingType.SCHEDULING_TYPE inType);

}
