package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.factories.Factories;

public class SubscriptionSchedulingImpl extends ObjectRecord<SubscriptionScheduling, SubscriptionSchedulingImpl> implements SubscriptionScheduling {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<SubscriptionSchedulingImpl> SPECIFICATION = new SQLObjectSpecification<SubscriptionSchedulingImpl>("subscription_scheduling", SubscriptionSchedulingImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "subscription_id", "scheduling_type_id", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected long subscription_id;
	protected long scheduling_type_id;

	/**
	 * Association 1-1 pour la subscription.
	 */
	private final SingleAssociationNotNull<SubscriptionScheduling, Subscription, SubscriptionImpl> mSubscription;

	public Subscription getSubscription() {
		return this.mSubscription.get(this.subscription_id);
	}

	@Override
	public SQLObjectSpecification<SubscriptionSchedulingImpl> getSpecification() {
		return SubscriptionSchedulingImpl.SPECIFICATION;
	}

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected SubscriptionSchedulingImpl(long id) throws SQLException {
		init(id);
		this.mSubscription = new SingleAssociationNotNull<SubscriptionScheduling, Subscription, SubscriptionImpl>(this, "subscription_id", SubscriptionImpl.SPECIFICATION);
	}

	protected SubscriptionSchedulingImpl() {
		this.mSubscription = new SingleAssociationNotNull<SubscriptionScheduling, Subscription, SubscriptionImpl>(this, "subscription_id", SubscriptionImpl.SPECIFICATION);
	}

	/**
	 * Constructeur à partir de nouvelles colonnes.
	 */
	public SubscriptionSchedulingImpl(Subscription inSubscription, SchedulingType.SCHEDULING_TYPE inType) throws SQLException {
		this.mSubscription = new SingleAssociationNotNull<SubscriptionScheduling, Subscription, SubscriptionImpl>(this, "subscription_id", (SubscriptionImpl) inSubscription);
		this.subscription_id = inSubscription.getId();
		this.scheduling_type_id = inType.getRecord().getId();
		init(SubscriptionSchedulingImpl.NEW_COLUMNS);
	}

	public SchedulingType.SCHEDULING_TYPE getType() {
		return SchedulingType.SCHEDULING_TYPE.find((int) this.scheduling_type_id);
	}

	public void setType(SchedulingType.SCHEDULING_TYPE inType) {
		if (inType != null) {
			final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
			setScheduling_type(inType.getRecord().getId(), theUpdateMap);
			update(theUpdateMap);
		}
	}

	private void setScheduling_type(long type, Map<String, Object> inUpdateMap) {
		if (this.scheduling_type_id != type) {
			this.scheduling_type_id = type;
			inUpdateMap.put("scheduling_type_id", this.scheduling_type_id);
		}
	}

	@Override
	protected void doDelete() throws SQLException {
		for (final SubscriptionSchedulingSettings aSetting : Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionScheduling(this)) {
			aSetting.delete();
		}
		super.doDelete();
	}
}
