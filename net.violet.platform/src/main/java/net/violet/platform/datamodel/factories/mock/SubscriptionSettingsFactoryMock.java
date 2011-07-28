package net.violet.platform.datamodel.factories.mock;

//public class SubscriptionSettingsFactoryMock extends RecordFactoryMock<SubscriptionSettings, SubscriptionSettingsMock> implements SubscriptionSettingsFactory {
//
//	SubscriptionSettingsFactoryMock() {
//		super(SubscriptionSettingsMock.class);
//	}
//
//	public SubscriptionSettings create(Subscription inSubscription, String inKey, String inValue) {
//		return new SubscriptionSettingsMock(0, inSubscription, inKey, inValue);
//	}
//
//	public List<SubscriptionSettings> findAllBySubscription(Subscription inSubscription) {
//		final List<SubscriptionSettings> theResult = new LinkedList<SubscriptionSettings>();
//		for (final SubscriptionSettings theSubsSettings : findAll()) {
//			if (theSubsSettings.getSubscription().getId().equals(inSubscription.getId())) {
//				theResult.add(theSubsSettings);
//			}
//		}
//		return theResult;
//	}
//
//	public SubscriptionSettings findBySubscriptionAndKey(Subscription inSubscription, String key) {
//		for (final SubscriptionSettings theSubsSettings : findAll()) {
//			if ((theSubsSettings.getSubscription() != null) && theSubsSettings.getSubscription().getId().equals(inSubscription.getId()) && (theSubsSettings.getKey() != null) && theSubsSettings.getKey().equals(key)) {
//				return theSubsSettings;
//			}
//		}
//		return null;
//	}
//
//	public long countByKeyAndValue(String inKey, String inValue) {
//		long count = 0;
//		for (final SubscriptionSettings settings : findAll()) {
//			if (settings.getKey().equals(inKey) && settings.getValue().equals(inValue)) {
//				count++;
//			}
//		}
//		return count;
//	}
//
//}
