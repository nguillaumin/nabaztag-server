package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class SubscriptionData extends APIData<Subscription> {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionData.class);

	/** Represents a box for nablife */
	private final long srvId;
	private String srvName;
	private final String srvTypeName;
	private String srvUrl;
	private final long srvTypeId;
	private String srvImg;
	private final int type;

	public static SubscriptionData getData(Subscription inSubscription) {
		try {
			return RecordData.getData(inSubscription, SubscriptionData.class, Subscription.class);
		} catch (final InstantiationException e) {
			SubscriptionData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			SubscriptionData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			SubscriptionData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			SubscriptionData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * used by reflection !
	 * 
	 * @param inSubscription
	 */
	protected SubscriptionData(Subscription inSubscription) {
		super(inSubscription);

		final Application app = inSubscription.getApplication();

		if (app != null) {
			if (app.getTemp() == null) {
				this.srvUrl = net.violet.common.StringShop.EMPTY_STRING;
			} else {
				if (app.equals(Application.NativeApplication.RSS_FULL.getApplication()) || app.equals(Application.NativeApplication.PODCAST_FULL.getApplication())) {
					this.srvUrl = app.getTemp().getLink() + "load&subscriptionId=" + inSubscription.getId();
				} else {
					this.srvUrl = app.getTemp().getLink() + "&subscriptionId=" + inSubscription.getId();
				}
			}
		} else {
			this.srvUrl = net.violet.common.StringShop.EMPTY_STRING;
		}

		this.srvId = inSubscription.getId();

		final Object titleSetting = inSubscription.getSettings().get(VActionFullHandler.LABEL);
		String theTitle = (titleSetting != null) ? titleSetting.toString() : null;
		if ((theTitle == null) && ((app != null) && (app.getProfile() != null))) {
			theTitle = app.getProfile().getTitle();
		}
		if (theTitle == null) {
			theTitle = net.violet.common.StringShop.EMPTY_STRING;
		}
		this.srvName = theTitle;

		this.srvTypeName = (app == null) ? net.violet.common.StringShop.EMPTY_STRING : app.getCategory().getName();
		this.srvTypeId = (app == null) ? 0 : app.getId();
		this.type = 2;
	}

	/**
	 * Construct a SubscriptionData
	 * 
	 * @param inScenario
	 * @param inScenariiFree
	 */
	private SubscriptionData(NabcastData inNbacastData) {
		super(null);
		this.type = 1;
		this.srvTypeId = 1;
		this.srvTypeName = "nabcast";
		this.srvImg = net.violet.common.StringShop.EMPTY_STRING;
		if (inNbacastData != null) {
			this.srvId = inNbacastData.getId();
			this.srvName = inNbacastData.getNabcast_title();
			this.srvUrl = inNbacastData.getLink();
		} else {
			this.srvId = 0;
			this.srvName = net.violet.common.StringShop.EMPTY_STRING;
			this.srvUrl = net.violet.common.StringShop.EMPTY_STRING;
		}
	}

	/**
	 * return all available SrvListData for the given parameters sorted by
	 * alphabetical order
	 * 
	 * @param idCateg
	 * @param index
	 * @param nbr
	 * @param inObject
	 * @param langCategorie
	 * @return
	 * @throws SQLException
	 */
	public static Set<SubscriptionData> findListServicesByObject(VObject inObject) {
		final SortedSet<SubscriptionData> subscriptionDataList = new TreeSet<SubscriptionData>(new Comparator<SubscriptionData>() {

			public int compare(SubscriptionData o1, SubscriptionData o2) {
				return o1.getSrv_name().compareTo(o2.getSrv_name());
			}
		});

		subscriptionDataList.addAll(SubscriptionData.generateListNabcastData(NabcastData.findAllSubscribedByObject(inObject)));
		subscriptionDataList.addAll(SubscriptionData.findAllByObject(inObject));

		return subscriptionDataList;
	}

	public static List<SubscriptionData> findAllByObject(VObject inObject) {
		if (inObject == null) {
			return Collections.emptyList();
		}

		return SubscriptionData.generateListSubscriptionData(Factories.SUBSCRIPTION.findAllByObject(inObject));
	}

	public static List<SubscriptionData> findByObjectAndSchedulingType(VObject inObject, SchedulingType.SCHEDULING_TYPE inSchedulingType) {
		if (inObject == null) {
			return Collections.emptyList();
		}

		return SubscriptionData.generateListSubscriptionData(Factories.SUBSCRIPTION.findAllByObjectAndSchedulingType(inObject, inSchedulingType));
	}

	public static List<SubscriptionData> findByApplicationAndObject(ApplicationData inApplication, VObjectData inObject) {
		return SubscriptionData.findByApplicationAndObject(inApplication.getRecord(), inObject.getRecord());
	}

	public static List<SubscriptionData> findByApplicationAndObject(Application inApplication, VObject inObject) {

		if ((inObject == null) || (inApplication == null)) {
			return Collections.emptyList();
		}
		return SubscriptionData.generateListSubscriptionData(Factories.SUBSCRIPTION.findByApplicationAndObject(inApplication, inObject));
	}

	private static List<SubscriptionData> generateListSubscriptionData(List<Subscription> inList) {
		if (inList == null) {
			return Collections.emptyList();
		}
		final List<SubscriptionData> result = new ArrayList<SubscriptionData>(inList.size());
		for (final Subscription aSubscription : inList) {
			result.add(SubscriptionData.getData(aSubscription));
		}
		return result;
	}

	/**
	 * Generates a list of ServicesData with the given SourceImpl list
	 * 
	 * @param inLang
	 * @return
	 */
	private static List<SubscriptionData> generateListNabcastData(List<NabcastData> inNabcastData) {
		if (inNabcastData == null) {
			return Collections.emptyList();
		}
		final List<SubscriptionData> servicesDataList = new ArrayList<SubscriptionData>(inNabcastData.size());
		for (final NabcastData tempSrvListData : inNabcastData) {
			servicesDataList.add(new SubscriptionData(tempSrvListData));
		}

		return servicesDataList;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		return this.srvId;
	}

	/**
	 * @return the attribute id
	 */
	public long getSrv_id() {
		return getId();
	}

	/**
	 * @return the attribute srv_name
	 */
	public String getSrv_name() {
		return this.srvName;
	}

	public void setSrv_name(String inName) {
		this.srvName = inName;
	}

	/**
	 * @return the attribute srv_name
	 */
	public String getName() {
		return getSrv_name();
	}

	/**
	 * @return the attribute url
	 */
	public String getSrv_url() {
		return this.srvUrl;
	}

	public void setSrv_url(String inUrl) {
		this.srvUrl = inUrl;
	}

	/**
	 * @return the attribute url
	 */
	public String getUrl() {
		return getSrv_url();
	}

	/**
	 * @return the attribute type (id)
	 */
	public long getTypeId() {
		return this.srvTypeId;
	}

	/**
	 * @return the attribute type (id)
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * @return the attribute srv_nametype
	 */
	public String getSrv_nametype() {
		return this.srvTypeName;
	}

	/**
	 * @return the attribute srv_img
	 */
	public String getSrv_img() {
		return this.srvImg;
	}

	public Subscription getReference() {
		return getRecord();
	}

	public static SubscriptionData find(long id) {
		final Subscription theSubscription = Factories.SUBSCRIPTION.find(id);
		if (theSubscription == null) {
			return null;
		}
		return SubscriptionData.getData(theSubscription);
	}

	public static SubscriptionData findByAPIId(String apiId, String apiKey) {
		SubscriptionData theResult = null;

		final long theID = APIData.fromObjectID(apiId, ObjectClass.SUBSCRIPTION, apiKey);
		if (theID != 0) {
			final Subscription theSubscription = Factories.SUBSCRIPTION.find(theID);
			if (theSubscription != null) {
				theResult = SubscriptionData.getData(theSubscription);
			}
		}

		return theResult;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.SUBSCRIPTION;
	}

	public String getDescription() {
		return getRecord().getApplication().getProfile().getDescription();
	}

	public static SubscriptionData create(ApplicationData inApplication, VObjectData inObject) {
		return SubscriptionData.getData(Factories.SUBSCRIPTION.create(inApplication.getReference(), inObject.getReference()));
	}

	public static SubscriptionData create(ApplicationData application, VObjectData object, Map<String, Object> settings) {
		return SubscriptionData.getData(Factories.SUBSCRIPTION.create(application.getRecord(), object.getRecord(), settings));
	}

	public VObjectData getObject() {
		final Subscription theRecord = getRecord();
		if (theRecord != null) {
			return VObjectData.getData(theRecord.getObject());
		}
		return null;
	}

	public ApplicationData getApplication() {
		final Subscription theRecord = getRecord();
		if (theRecord != null) {
			return ApplicationData.getData(theRecord.getApplication());
		}
		return null;
	}

	public PojoMap getSettings() {
		return getRecord().getSettings();
	}

	public void setSettings(Map<String, Object> settings) {
		getRecord().setSettings(settings);
	}

	public void setSetting(String key, Object value) {
		final Map<String, Object> settings = getSettings();
		settings.put(key, value);
		setSettings(settings);
	}

	public void removeSetting(String key) {
		final Map<String, Object> settings = getSettings();
		settings.remove(key);
		setSettings(settings);

	}

}
