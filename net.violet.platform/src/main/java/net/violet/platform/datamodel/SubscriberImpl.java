package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.associations.AssociationRecord;
import net.violet.db.records.associations.SQLAssociationSpecification;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class SubscriberImpl extends AssociationRecord<Nabcast, Subscriber, SubscriberImpl> implements Subscriber {


	private static final Logger LOGGER = Logger.getLogger(SubscriberImpl.class);

	static final SQLAssociationSpecification<SubscriberImpl> SPECIFICATION = new SQLAssociationSpecification<SubscriberImpl>("subscriber", SubscriberImpl.class, new SQLKey("subscriber_user", "subscriber_nabcast"));

	protected long subscriber_user;
	protected long subscriber_nabcast;
	protected int subscriber_heure;
	protected int subscriber_min;

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Subscriber#getSpecification()
	 */
	@Override
	public SQLAssociationSpecification<SubscriberImpl> getSpecification() {
		return SubscriberImpl.SPECIFICATION;
	}

	public <T extends ObjectRecord> SubscriberImpl(VObject object, Nabcast nabcast, int heure, int min) {
		this.subscriber_user = object.getId();
		this.subscriber_nabcast = nabcast.getId();
		this.subscriber_heure = heure;
		this.subscriber_min = min;
	}

	public SubscriberImpl() {
		super();

	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Subscriber#getSubscriber_user()
	 */
	public long getSubscriber_user() {
		return this.subscriber_user;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Subscriber#getSubscriber_nabcast()
	 */
	public long getSubscriber_nabcast() {
		return this.subscriber_nabcast;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Subscriber#getSubscriber_heure()
	 */
	public int getSubscriber_heure() {
		return this.subscriber_heure;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Subscriber#getVObjectSubscriber()
	 */
	public VObject getVObjectSubscriber() {
		return Factories.VOBJECT.find(this.subscriber_user);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Subscriber#setTime(int, int)
	 */
	public void setTime(int heure, int min) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setSubscriber_heure(theUpdateMap, heure);
		setSubscriber_min(theUpdateMap, min);
		update(theUpdateMap);

	}

	private void setSubscriber_heure(Map<String, Object> inUpdateMap, int subscriber_heure) {
		if (this.subscriber_heure != subscriber_heure) {
			this.subscriber_heure = subscriber_heure;
			inUpdateMap.put("subscriber_heure", subscriber_heure);
		}
	}

	private void setSubscriber_min(Map<String, Object> inUpdateMap, int subscriber_min) {
		if (this.subscriber_min != subscriber_min) {
			this.subscriber_min = subscriber_min;
			inUpdateMap.put("subscriber_min", subscriber_min);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Subscriber#getSubscriber_min()
	 */
	public int getSubscriber_min() {
		return this.subscriber_min;
	}

	public static List<Subscriber> findByNabcast(Nabcast nabcast) {
		List<Subscriber> list = new ArrayList<Subscriber>();
		try {
			list = AbstractSQLRecord.findAll(SubscriberImpl.SPECIFICATION, "subscriber_nabcast=?", Collections.singletonList((Object) nabcast.getId()));
		} catch (final SQLException se) {
			SubscriberImpl.LOGGER.fatal(se, se);
		}
		return list;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
