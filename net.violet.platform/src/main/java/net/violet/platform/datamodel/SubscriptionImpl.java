package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.util.UpdateMap;

import org.apache.log4j.Logger;

public class SubscriptionImpl extends ObjectRecord<Subscription, SubscriptionImpl> implements Subscription {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionImpl.class);

	public static final SQLObjectSpecification<SubscriptionImpl> SPECIFICATION = new SQLObjectSpecification<SubscriptionImpl>("subscription", SubscriptionImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "application_id", "object_id", "settings" };

	protected long id;
	protected long application_id;
	protected long object_id;
	protected String settings;

	private final SingleAssociationNotNull<Subscription, Application, ApplicationImpl> mApplication;
	private final SingleAssociationNotNull<Subscription, VObject, VObjectImpl> mObject;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<SubscriptionImpl> getSpecification() {
		return SubscriptionImpl.SPECIFICATION;
	}

	protected SubscriptionImpl(long id) throws SQLException {
		init(id);
		this.mApplication = new SingleAssociationNotNull<Subscription, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.mObject = new SingleAssociationNotNull<Subscription, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
	}

	protected SubscriptionImpl() {
		this.mApplication = new SingleAssociationNotNull<Subscription, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.mObject = new SingleAssociationNotNull<Subscription, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
	}

	public SubscriptionImpl(Application inApplication, VObject inObject) throws SQLException {
		this(inApplication, inObject, Collections.<String, Object> emptyMap());
	}

	public SubscriptionImpl(Application application, VObject object, Map<String, Object> settings) throws SQLException {
		this.mApplication = new SingleAssociationNotNull<Subscription, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.mObject = new SingleAssociationNotNull<Subscription, VObject, VObjectImpl>(this, "object_id", VObjectImpl.SPECIFICATION);
		this.application_id = (application == null) ? 0 : application.getId();
		this.object_id = object.getId();
		this.settings = ConverterFactory.JSON.convertTo(settings);
		init(SubscriptionImpl.NEW_COLUMNS);
	}

	public Application getApplication() {
		return this.mApplication.get(this.application_id);
	}

	public VObject getObject() {
		return this.mObject.get(this.object_id);
	}

	public PojoMap getSettings() {
		try {
			return new PojoMap((Map<String, Object>) ConverterFactory.JSON.convertFrom(this.settings));

		} catch (final ClassCastException ce) {
			SubscriptionImpl.LOGGER.fatal("Settings for subcription " + this.id + " are not a map !", ce);
		} catch (final ConversionException e) {
			SubscriptionImpl.LOGGER.fatal("Settings for subcription " + this.id + " are not in good JSON format !\n" + this.settings, e);
		}

		return PojoMap.EMPTY_MAP;
	}

	public void setSettings(Map<String, ? extends Object> settings) {
		final UpdateMap upMap = new UpdateMap();
		this.settings = upMap.updateField("settings", this.settings, ConverterFactory.JSON.convertTo(settings));
		update(upMap);
	}

	@Override
	protected void doDelete() throws SQLException {

		for (final SubscriptionScheduling aScheduling : Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(this)) {
			aScheduling.delete();
		}
		final SubscriptionLog theLog = Factories.SUBSCRIPTION_LOG.find(this.id);
		if (theLog != null) {
			theLog.delete();
		}

		super.doDelete();

	}

}
