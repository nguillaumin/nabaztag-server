package net.violet.platform.dataobjects;


//public class SubscriptionSettingsData extends AbstractSettingRecordData<SubscriptionSettings> {
//
//	private static final Logger LOGGER = Logger.getLogger(UserData.class);
//
//	public static SubscriptionSettingsData getData(SubscriptionSettings inSubscriptionSettings) {
//		try {
//			return RecordData.getData(inSubscriptionSettings, SubscriptionSettingsData.class, SubscriptionSettings.class);
//		} catch (final InstantiationException e) {
//			SubscriptionSettingsData.LOGGER.fatal(e, e);
//		} catch (final IllegalAccessException e) {
//			SubscriptionSettingsData.LOGGER.fatal(e, e);
//		} catch (final InvocationTargetException e) {
//			SubscriptionSettingsData.LOGGER.fatal(e, e);
//		} catch (final NoSuchMethodException e) {
//			SubscriptionSettingsData.LOGGER.fatal(e, e);
//		}
//
//		return null;
//	}
//
//	/**
//	 * USED BY REFLECTION
//	 * 
//	 * @param inRecord
//	 */
//	protected SubscriptionSettingsData(SubscriptionSettings inRecord) {
//		super(inRecord);
//	}
//
//	public SubscriptionData getSubscription() {
//		if (getRecord() != null) {
//			return SubscriptionData.getData(getRecord().getSubscription());
//		}
//
//		return null;
//	}
//
//	public String getKey() {
//		return isEmpty() ? net.violet.common.StringShop.EMPTY_STRING : getRecord().getKey();
//	}
//
//	public String getValue() {
//		return isEmpty() ? net.violet.common.StringShop.EMPTY_STRING : getRecord().getValue();
//	}
//
//	public static Map<String, String> findAllBySubscriptionAsPojo(SubscriptionData inSubscription) {
//		return SubscriptionSettingsData.generatePojoMap(Factories.SUBSCRIPTION_SETTINGS.findAllBySubscription(inSubscription.getReference()));
//	}
//
//	public static Map<String, SubscriptionSettingsData> findAllBySubscriptionAsMap(SubscriptionData inSubscription) {
//		return SubscriptionSettingsData.generateMap(Factories.SUBSCRIPTION_SETTINGS.findAllBySubscription(inSubscription.getReference()));
//	}
//
//	private static Map<String, SubscriptionSettingsData> generateMap(List<SubscriptionSettings> inList) {
//		final Map<String, SubscriptionSettingsData> theSettings = new HashMap<String, SubscriptionSettingsData>();
//		for (final SubscriptionSettings aSetting : inList) {
//			theSettings.put(aSetting.getKey(), SubscriptionSettingsData.getData(aSetting));
//		}
//
//		return theSettings;
//	}
//
//	public static List<SubscriptionSettingsData> findAllBySubscription(SubscriptionData inSubscription) {
//		return SubscriptionSettingsData.generateList(Factories.SUBSCRIPTION_SETTINGS.findAllBySubscription(inSubscription.getReference()));
//	}
//
//	private static List<SubscriptionSettingsData> generateList(List<SubscriptionSettings> inSettings) {
//		final List<SubscriptionSettingsData> settingsData = new ArrayList<SubscriptionSettingsData>(inSettings.size());
//		for (final SubscriptionSettings aSetting : inSettings) {
//			settingsData.add(SubscriptionSettingsData.getData(aSetting));
//		}
//		return settingsData;
//	}
//
//	private static Map<String, String> generatePojoMap(List<SubscriptionSettings> inList) {
//		final Map<String, String> theSettings = new HashMap<String, String>();
//		for (final SubscriptionSettings aSetting : inList) {
//			theSettings.put(aSetting.getKey(), aSetting.getValue());
//		}
//
//		return theSettings;
//	}
//
//	public static SubscriptionSettingsData findBySubscriptionAndKey(SubscriptionData inSubscription, String inKey) {
//		return (inSubscription == null) ? null : SubscriptionSettingsData.getData(Factories.SUBSCRIPTION_SETTINGS.findBySubscriptionAndKey(inSubscription.getReference(), inKey));
//	}
//
//	public void setValue(String inValue) {
//		final SubscriptionSettings theRecord = getRecord();
//		if (theRecord != null) {
//			theRecord.setValue(inValue);
//		}
//	}
//
//	public SubscriptionSettings getReference() {
//		return getRecord();
//	}
//
//	@Override
//	protected void update(String key, String value) {
//		if (getRecord() != null) {
//			getRecord().setKey(key);
//			getRecord().setValue(value);
//		}
//	}
//
//	@Override
//	public String toString() {
//		return getKey() + "=" + getValue();
//	}
//
//}
