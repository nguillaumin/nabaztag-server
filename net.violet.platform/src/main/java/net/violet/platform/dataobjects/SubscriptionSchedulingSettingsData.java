package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.SchedulingType.SCHEDULING_TYPE;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class SubscriptionSchedulingSettingsData extends AbstractSettingRecordData<SubscriptionSchedulingSettings> {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionSchedulingSettingsData.class);

	public static SubscriptionSchedulingSettingsData getData(SubscriptionSchedulingSettings inRecord) {
		try {
			return RecordData.getData(inRecord, SubscriptionSchedulingSettingsData.class, SubscriptionSchedulingSettings.class);
		} catch (final InstantiationException e) {
			SubscriptionSchedulingSettingsData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			SubscriptionSchedulingSettingsData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			SubscriptionSchedulingSettingsData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			SubscriptionSchedulingSettingsData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Used by reflection !
	 * 
	 * @param inRecord
	 */
	protected SubscriptionSchedulingSettingsData(SubscriptionSchedulingSettings inRecord) {
		super(inRecord);
	}

	public SubscriptionSchedulingData getScheduling() {
		return SubscriptionSchedulingData.getData(getRecord().getSubscriptionScheduling());
	}

	public SubscriptionData getSubscription() {
		return getScheduling().getSubscription();
	}

	public String getKey() {
		if (getRecord() != null) {
			return getRecord().getKey();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getValue() {
		if (getRecord() != null) {
			return getRecord().getValue();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static List<SubscriptionSchedulingSettingsData> findAllBySubscriptionScheduling(SubscriptionSchedulingData inScheduling) {
		return SubscriptionSchedulingSettingsData.generateList(Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionScheduling(inScheduling.getReference()));
	}

	public static Map<String, SubscriptionSchedulingSettingsData> findAllBySubscriptionSchedulingAsMap(SubscriptionSchedulingData inScheduling) {
		final Map<String, SubscriptionSchedulingSettingsData> theMap = new HashMap<String, SubscriptionSchedulingSettingsData>();

		for (final SubscriptionSchedulingSettings aData : Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionScheduling(inScheduling.getReference())) {
			theMap.put(aData.getKey(), SubscriptionSchedulingSettingsData.getData(aData));
		}

		return theMap;
	}

	public static List<SubscriptionSchedulingSettingsData> generateList(List<SubscriptionSchedulingSettings> inList) {
		final List<SubscriptionSchedulingSettingsData> theList = new LinkedList<SubscriptionSchedulingSettingsData>();
		for (final SubscriptionSchedulingSettings settings : inList) {
			theList.add(SubscriptionSchedulingSettingsData.getData(settings));
		}

		return theList;
	}

	@Override
	public void update(String inKey, String inValue) {
		final SubscriptionSchedulingSettings theRecord = getRecord();
		if (theRecord != null) {
			theRecord.updateKeyAndValue(inKey, inValue);
		}
	}

	public static SubscriptionSchedulingSettingsData findBySubscriptionSchedulingAndKey(SubscriptionSchedulingData scheduling, String key) {
		final SubscriptionSchedulingSettings setting = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionSchedulingAndKey(scheduling.getReference(), key);
		if (setting != null) {
			return SubscriptionSchedulingSettingsData.getData(setting);
		}
		return null;
	}

	public static Map<String, SubscriptionSchedulingSettingsData> findBySubscriptionAndTypeAndKeyword(SubscriptionData inSubscription, SCHEDULING_TYPE inType, String inKeyWord) {
		final Map<String, SubscriptionSchedulingSettingsData> theResult = new HashMap<String, SubscriptionSchedulingSettingsData>();

		for (final SubscriptionSchedulingSettings aSetting : Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findBySubscriptionAndTypeAndKey(inSubscription.getReference(), inType, inKeyWord)) {
			theResult.put(aSetting.getKey(), SubscriptionSchedulingSettingsData.getData(aSetting));
		}

		return theResult;
	}

	public void setValue(String value) {
		if (getRecord() != null) {
			getRecord().setValue(value);
		}

	}

}
