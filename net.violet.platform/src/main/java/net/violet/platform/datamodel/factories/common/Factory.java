package net.violet.platform.datamodel.factories.common;

import net.violet.platform.datamodel.factories.AnimFactory;
import net.violet.platform.datamodel.factories.AnnuFactory;
import net.violet.platform.datamodel.factories.AppletSettingsFactory;
import net.violet.platform.datamodel.factories.ApplicationApiLibFactory;
import net.violet.platform.datamodel.factories.ApplicationCategoryFactory;
import net.violet.platform.datamodel.factories.ApplicationContentFactory;
import net.violet.platform.datamodel.factories.ApplicationCredentialsFactory;
import net.violet.platform.datamodel.factories.ApplicationFactory;
import net.violet.platform.datamodel.factories.ApplicationLangFactory;
import net.violet.platform.datamodel.factories.ApplicationPackageFactory;
import net.violet.platform.datamodel.factories.ApplicationProfileFactory;
import net.violet.platform.datamodel.factories.ApplicationSettingFactory;
import net.violet.platform.datamodel.factories.ApplicationTagFactory;
import net.violet.platform.datamodel.factories.ApplicationTempFactory;
import net.violet.platform.datamodel.factories.BlackFactory;
import net.violet.platform.datamodel.factories.CategFactory;
import net.violet.platform.datamodel.factories.CityFactory;
import net.violet.platform.datamodel.factories.ConfigFilesFactory;
import net.violet.platform.datamodel.factories.ContactFactory;
import net.violet.platform.datamodel.factories.ContentFactory;
import net.violet.platform.datamodel.factories.ContextFactory;
import net.violet.platform.datamodel.factories.ContinentFactory;
import net.violet.platform.datamodel.factories.CountryFactory;
import net.violet.platform.datamodel.factories.DicoFactory;
import net.violet.platform.datamodel.factories.EvSeqFactory;
import net.violet.platform.datamodel.factories.EventFactory;
import net.violet.platform.datamodel.factories.FeedFactory;
import net.violet.platform.datamodel.factories.FeedItemFactory;
import net.violet.platform.datamodel.factories.FeedSubscriptionFactory;
import net.violet.platform.datamodel.factories.FilesFactory;
import net.violet.platform.datamodel.factories.FriendsListsFactory;
import net.violet.platform.datamodel.factories.GroupFactory;
import net.violet.platform.datamodel.factories.HardwareFactory;
import net.violet.platform.datamodel.factories.HintFactory;
import net.violet.platform.datamodel.factories.LangFactory;
import net.violet.platform.datamodel.factories.MessageCounterFactory;
import net.violet.platform.datamodel.factories.MessageFactory;
import net.violet.platform.datamodel.factories.MessageReceivedFactory;
import net.violet.platform.datamodel.factories.MessageSentFactory;
import net.violet.platform.datamodel.factories.MessengerFactory;
import net.violet.platform.datamodel.factories.MimeTypeFactory;
import net.violet.platform.datamodel.factories.MusicFactory;
import net.violet.platform.datamodel.factories.MusicStyleFactory;
import net.violet.platform.datamodel.factories.NabcastCategFactory;
import net.violet.platform.datamodel.factories.NabcastResourceFactory;
import net.violet.platform.datamodel.factories.NathanMp3Factory;
import net.violet.platform.datamodel.factories.NathanTagFactory;
import net.violet.platform.datamodel.factories.NathanVersionFactory;
import net.violet.platform.datamodel.factories.NewsFactory;
import net.violet.platform.datamodel.factories.NotificationFactory;
import net.violet.platform.datamodel.factories.ObjectHasReadContentFactory;
import net.violet.platform.datamodel.factories.ObjectProfileFactory;
import net.violet.platform.datamodel.factories.ObjectSleepFactory;
import net.violet.platform.datamodel.factories.PressFactory;
import net.violet.platform.datamodel.factories.ProductFactory;
import net.violet.platform.datamodel.factories.ResourceFactory;
import net.violet.platform.datamodel.factories.ScheduledMessageFactory;
import net.violet.platform.datamodel.factories.SchedulingTypeFactory;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.datamodel.factories.SignatureFactory;
import net.violet.platform.datamodel.factories.SourceFactory;
import net.violet.platform.datamodel.factories.StatsMessageFactory;
import net.violet.platform.datamodel.factories.StoreCityFactory;
import net.violet.platform.datamodel.factories.StoreFactory;
import net.violet.platform.datamodel.factories.SubscriptionFactory;
import net.violet.platform.datamodel.factories.SubscriptionLogFactory;
import net.violet.platform.datamodel.factories.SubscriptionSchedulingFactory;
import net.violet.platform.datamodel.factories.SubscriptionSchedulingSettingsFactory;
import net.violet.platform.datamodel.factories.TagTmpSiteFactory;
import net.violet.platform.datamodel.factories.TimezoneFactory;
import net.violet.platform.datamodel.factories.TtsVoiceFactory;
import net.violet.platform.datamodel.factories.UserEmailFactory;
import net.violet.platform.datamodel.factories.UserFactory;
import net.violet.platform.datamodel.factories.UserFriendsAddressFactory;
import net.violet.platform.datamodel.factories.UserPrefsFactory;
import net.violet.platform.datamodel.factories.UserPwdFactory;
import net.violet.platform.datamodel.factories.VActionFactory;
import net.violet.platform.datamodel.factories.VObjectFactory;
import net.violet.platform.datamodel.factories.ZtampFactory;

public interface Factory {

	FilesFactory getFilesFactory();

	VActionFactory getVActionFactory();

	ConfigFilesFactory getConfigFilesFactory();

	ContentFactory getContentFactory();

	MessageFactory getMessageFactory();

	MusicFactory getMusicFactory();

	MusicStyleFactory getMusicStyleFactory();

	ObjectHasReadContentFactory getObjectHasReadContentFactory();

	EvSeqFactory getEvSeqFactory();

	ServiceFactory getServiceFactory();

	MessengerFactory getMessenger();

	MessageCounterFactory getMessageCounterFactory();

	AnimFactory getAnimFactory();

	UserFactory getUserFactory();

	MessageReceivedFactory getMessageReceivedFactory();

	MessageSentFactory getMessageSentFactory();

	LangFactory getLangFactory();

	DicoFactory getDicoFactory();

	VObjectFactory getVObjectFactory();

	TimezoneFactory getTimezoneFactory();

	EventFactory getEventFactory();

	NathanMp3Factory getNathanMp3Factory();

	NathanTagFactory getNathanTagFactory();

	NathanVersionFactory getNathanVersionFactory();

	ScheduledMessageFactory getScheduledMessageFactory();

	StatsMessageFactory getStatsFactory();

	TtsVoiceFactory getTtsVoiceFactory();

	UserPrefsFactory getUserPrefsFactory();

	TagTmpSiteFactory getTagTmpSiteFactory();

	ApplicationFactory getApplicationFactory();

	ApplicationProfileFactory getApplicationProfileFactory();

	ApplicationPackageFactory getApplicationPackageFactory();

	ApplicationApiLibFactory getApplicationApiLibFactory();

	ApplicationLangFactory getApplicationLangFactory();

	NabcastCategFactory getNabcastCategFactory();

	HardwareFactory getHardwareFactory();

	SourceFactory getSourceFactory();

	ObjectSleepFactory getObjectSleepFactory();

	AppletSettingsFactory getAppletSettingsFactory();

	FriendsListsFactory getFriendsListsFactory();

	CountryFactory getCountryFactory();

	ContextFactory getContextFactory();

	HintFactory getHintFactory();

	ContactFactory getContactFactory();

	CategFactory getCategFactory();

	ApplicationCategoryFactory getApplicationCategoryFactory();

	ApplicationTagFactory getApplicationTagFactory();

	ApplicationCredentialsFactory getApplicationCredentialsFactory();

	CityFactory getCityFactory();

	ContinentFactory getContinentFactory();

	BlackFactory getBlackFactory();

	SubscriptionSchedulingSettingsFactory getSubscriptionSchedulingSettingsFactory();

	SubscriptionFactory getSubscriptionFactory();

	SubscriptionSchedulingFactory getSubscriptionSchedulingFactory();

	ObjectProfileFactory getObjectProfileFactory();

	AnnuFactory getAnnuFactory();

	UserFriendsAddressFactory getUserFriendsAddressFactory();

	UserPwdFactory getUserPwdFactory();

	SchedulingTypeFactory getSchedulingTypeFactory();

	ApplicationTempFactory getApplicationTempFactory();

	ZtampFactory getZtampFactory();

	ApplicationSettingFactory getApplicationSettingFactory();

	ProductFactory getProductFactory();

	StoreCityFactory getStoreCityFactory();

	StoreFactory getStoreFactory();

	PressFactory getPressFactory();

	NewsFactory getNewsFactory();

	ApplicationContentFactory getApplicationContentFactory();

	ResourceFactory getResourceFactory();

	NabcastResourceFactory getNabcastResourceFactory();

	SignatureFactory getSignatureFactory();

	MimeTypeFactory getMimeTypeFactory();

	UserEmailFactory getUserEmailFactory();

	SubscriptionLogFactory getSubscriptionLogFactory();

	GroupFactory getGroupFactory();

	FeedFactory getFeedFactory();

	FeedItemFactory getFeedItemFactory();

	FeedSubscriptionFactory getFeedSubscriptionFactory();

	NotificationFactory getNotificationFactory();

}
