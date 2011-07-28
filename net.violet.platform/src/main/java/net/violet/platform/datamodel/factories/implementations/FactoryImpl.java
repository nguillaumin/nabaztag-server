package net.violet.platform.datamodel.factories.implementations;

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
import net.violet.platform.datamodel.factories.common.Factory;

public class FactoryImpl implements Factory {

	private final MimeTypeFactory mMimeTypeFactory = new MimeTypeFactoryImpl();
	private final FilesFactory mFilesFactory = new FilesFactoryImpl();
	private final VActionFactory mVactionFactory = new VActionFactoryImpl();
	private final CityFactory mCityFactory = new CityFactoryImpl();
	private final ContinentFactory mContinentFactory = new ContinentFactoryImpl();
	private final ContentFactory mContentFactory = new ContentFactoryImpl();
	private final MessageFactory mMessageFactory = new MessageFactoryImpl();
	private final MusicFactory mMusicFactory = new MusicFactoryImpl();
	private final ObjectHasReadContentFactory mObjectHasReadContentFactory = new ObjectHasReadContentFactoryImpl();
	private final ConfigFilesFactory mConfigFilesFactory = new ConfigFilesFactoryImpl();
	private final EvSeqFactory mEvSeqFactory = new EvSeqFactoryImpl();
	private final ServiceFactory mServiceFactory = new ServiceFactoryImpl();
	private final MessengerFactory mMessengerFactory = new MessengerFactoryImpl();
	private final MessageCounterFactory mMessageCounterFactory = new MessageCounterFactoryImpl();
	private final AnimFactory mAnimFactory = new AnimFactoryImpl();
	private final UserFactory mUserFactory = new UserFactoryImpl();
	private final MessageReceivedFactory mMessageReceivedFactory = new MessageReceivedFactoryImpl();
	private final MessageSentFactory mMessageSentFactory = new MessageSentFactoryImpl();
	private final LangFactory mLangFactory = new LangFactoryImpl();
	private final DicoFactory mDicoFactory = new DicoFactoryImpl();
	private final VObjectFactory mVObjectFactory = new VObjectFactoryImpl();
	private final TimezoneFactory mTimezoneFactory = new TimezoneFactoryImpl();
	private final EventFactory mEventFactory = new EventFactoryImpl();
	private final NathanMp3Factory mNathanMp3Factory = new NathanMp3FactoryImpl();
	private final NathanTagFactory mNathanTagFactory = new NathanTagFactoryImpl();
	private final NathanVersionFactory mNathanVersionFactory = new NathanVersionFactoryImpl();
	private final ScheduledMessageFactory mScheduledMessageFactory = new ScheduledMessageFactoryImpl();
	private final StatsMessageFactory mStatsFactory = new StatsMessageFactoryImpl();
	private final TtsVoiceFactory mTtsVoiceFactory = new TtsVoiceFactoryImpl();
	private final UserPrefsFactory mUserPrefsFactory = new UserPrefsFactoryImpl();
	private final TagTmpSiteFactory mTagTmpSiteFactory = new TagTmpSiteFactoryImpl();
	private final ApplicationFactory mApplicationFactory = new ApplicationFactoryImpl();
	private final ApplicationProfileFactory mApplicationProfileFactory = new ApplicationProfileFactoryImpl();
	private final ApplicationPackageFactory mApplicationPackageFactory = new ApplicationPackageFactoryImpl();
	private final ApplicationApiLibFactory mApplicationApiLibFactory = new ApplicationApiLibFactoryImpl();
	private final NabcastCategFactory mNabcastCategFactory = new NabcastCategFactoryImpl();
	private final HardwareFactory mHardwareFactory = new HardwareFactoryImpl();
	private final SourceFactory mSourcefactory = new SourceFactoryImpl();
	private final ObjectSleepFactory mObjectSleepFactory = new ObjectSleepFactoryImpl();
	private final AppletSettingsFactory mAppletSettingsFactory = new AppletSettingsFactoryImpl();
	private final FriendsListsFactory mFriendsListsFactory = new FriendsListsFactoryImpl();
	private final CountryFactory mPaysFactory = new CountryFactoryImpl();
	private final ContextFactory mContextFactory = new ContextFactoryImpl();
	private final HintFactory mHintFactory = new HintFactoryImpl();
	private final ContactFactory mContactFactory = new ContactFactoryImpl();
	private final CategFactory mCategFactory = new CategFactoryImpl();
	private final ApplicationCategoryFactory mApplicationCategoryFactory = new ApplicationCategoryFactoryImpl();
	private final ApplicationTagFactory mApplicationTagFactory = new ApplicationTagFactoryImpl();
	private final ApplicationCredentialsFactory mApplicationCredentialsFactory = new ApplicationCredentialsFactoryImpl();
	private final ApplicationLangFactory mApplicationLangFactory = new ApplicationLangFactoryImpl();
	private final BlackFactory mBlackFactory = new BlackFactoryImpl();
	private final SubscriptionSchedulingSettingsFactory mSubscriptionSchedulingSettingsFactory = new SubscriptionSchedulingSettingsFactoryImpl();
	private final SubscriptionFactory mSubscriptionFactory = new SubscriptionFactoryImpl();
	private final SubscriptionSchedulingFactory mSubscriptionSchedulingFactory = new SubscriptionSchedulingFactoryImpl();
	private final ObjectProfileFactory mObjectProfileFactory = new ObjectProfileFactoryImpl();
	private final AnnuFactory mAnnuFactory = new AnnuFactoryImpl();
	private final MusicStyleFactory mMusicStyleFactory = new MusicStyleFactoryImpl();
	private final UserFriendsAddressFactory mUserFriendsAddressFactory = new UserFriendsAddressFactoryImpl();
	private final UserPwdFactory mUserPwdFactory = new UserPwdFactoryImpl();
	private final SchedulingTypeFactory mSchedulingTypeFactory = new SchedulingTypeFactoryImpl();
	private final ApplicationTempFactory mApplicationTempFactory = new ApplicationTempFactoryImpl();
	private final ZtampFactory mZtampFactory = new ZtampFactoryImpl();
	private final ApplicationSettingFactory mApplicationSettingFactory = new ApplicationSettingFactoryImpl();
	private final ProductFactory mProductFactory = new ProductFactoryImpl();
	private final StoreCityFactory mStoreCityFactory = new StoreCityFactoryImpl();
	private final StoreFactory mStoreFactory = new StoreFactoryImpl();
	private final NewsFactory mNewsFactory = new NewsFactoryImpl();
	private final PressFactory mPressFactory = new PressFactoryImpl();
	private final ApplicationContentFactory applicationContentFactory = new ApplicationContentFactoryImpl();
	private final ResourceFactory resourceFactory = new ResourceFactoryImpl();
	private final NabcastResourceFactory nabcastResourceFactory = new NabcastResourceFactoryImpl();
	private final SignatureFactory signatureFactory = new SignatureFactoryImpl();
	private final UserEmailFactory userEmailFactory = new UserEmailFactoryImpl();
	private final SubscriptionLogFactory subscriptionLogFactory = new SubscriptionLogFactoryImpl();
	private final GroupFactory groupFactory = new GroupFactoryImpl();
	private final FeedFactory feedFactory = new FeedFactoryImpl();
	private final FeedItemFactory feedItemFactory = new FeedItemFactoryImpl();
	private final FeedSubscriptionFactory feedSubscriptionFactory = new FeedSubscriptionFactoryImpl();
	private final NotificationFactory mNotificationFactory = new NotificationFactoryImpl();

	public FeedItemFactory getFeedItemFactory() {
		return this.feedItemFactory;
	}

	public ZtampFactory getZtampFactory() {
		return this.mZtampFactory;
	}

	public FeedFactory getFeedFactory() {
		return this.feedFactory;
	}

	public FilesFactory getFilesFactory() {
		return this.mFilesFactory;
	}

	public VActionFactory getVActionFactory() {
		return this.mVactionFactory;
	}

	public ConfigFilesFactory getConfigFilesFactory() {
		return this.mConfigFilesFactory;
	}

	public ContentFactory getContentFactory() {
		return this.mContentFactory;
	}

	public MessageFactory getMessageFactory() {
		return this.mMessageFactory;
	}

	public MusicFactory getMusicFactory() {
		return this.mMusicFactory;
	}

	public ObjectHasReadContentFactory getObjectHasReadContentFactory() {
		return this.mObjectHasReadContentFactory;
	}

	public EvSeqFactory getEvSeqFactory() {
		return this.mEvSeqFactory;
	}

	public ServiceFactory getServiceFactory() {
		return this.mServiceFactory;
	}

	public MessengerFactory getMessenger() {
		return this.mMessengerFactory;
	}

	public MessageCounterFactory getMessageCounterFactory() {
		return this.mMessageCounterFactory;
	}

	public AnimFactory getAnimFactory() {
		return this.mAnimFactory;
	}

	public UserFactory getUserFactory() {
		return this.mUserFactory;
	}

	public MessageReceivedFactory getMessageReceivedFactory() {
		return this.mMessageReceivedFactory;
	}

	public MessageSentFactory getMessageSentFactory() {
		return this.mMessageSentFactory;
	}

	public LangFactory getLangFactory() {
		return this.mLangFactory;
	}

	public DicoFactory getDicoFactory() {
		return this.mDicoFactory;
	}

	public VObjectFactory getVObjectFactory() {
		return this.mVObjectFactory;
	}

	public TimezoneFactory getTimezoneFactory() {
		return this.mTimezoneFactory;
	}

	public EventFactory getEventFactory() {
		return this.mEventFactory;
	}

	public NathanMp3Factory getNathanMp3Factory() {
		return this.mNathanMp3Factory;
	}

	public NathanTagFactory getNathanTagFactory() {
		return this.mNathanTagFactory;
	}

	public NathanVersionFactory getNathanVersionFactory() {
		return this.mNathanVersionFactory;
	}

	public ScheduledMessageFactory getScheduledMessageFactory() {
		return this.mScheduledMessageFactory;
	}

	public StatsMessageFactory getStatsFactory() {
		return this.mStatsFactory;
	}

	public TtsVoiceFactory getTtsVoiceFactory() {
		return this.mTtsVoiceFactory;
	}

	public UserPrefsFactory getUserPrefsFactory() {
		return this.mUserPrefsFactory;
	}

	public TagTmpSiteFactory getTagTmpSiteFactory() {
		return this.mTagTmpSiteFactory;
	}

	public ApplicationFactory getApplicationFactory() {
		return this.mApplicationFactory;
	}

	public ApplicationPackageFactory getApplicationPackageFactory() {
		return this.mApplicationPackageFactory;
	}

	public ApplicationApiLibFactory getApplicationApiLibFactory() {
		return this.mApplicationApiLibFactory;
	}

	public ApplicationProfileFactory getApplicationProfileFactory() {
		return this.mApplicationProfileFactory;
	}

	public NabcastCategFactory getNabcastCategFactory() {
		return this.mNabcastCategFactory;
	}

	public HardwareFactory getHardwareFactory() {
		return this.mHardwareFactory;
	}

	public SourceFactory getSourceFactory() {
		return this.mSourcefactory;
	}

	public ObjectSleepFactory getObjectSleepFactory() {
		return this.mObjectSleepFactory;
	}

	public AppletSettingsFactory getAppletSettingsFactory() {
		return this.mAppletSettingsFactory;
	}

	public FriendsListsFactory getFriendsListsFactory() {
		return this.mFriendsListsFactory;
	}

	public CountryFactory getCountryFactory() {
		return this.mPaysFactory;
	}

	public ContextFactory getContextFactory() {
		return this.mContextFactory;
	}

	public HintFactory getHintFactory() {
		return this.mHintFactory;
	}

	public ContactFactory getContactFactory() {
		return this.mContactFactory;
	}

	public CategFactory getCategFactory() {
		return this.mCategFactory;
	}

	public ApplicationCategoryFactory getApplicationCategoryFactory() {
		return this.mApplicationCategoryFactory;
	}

	public ApplicationTagFactory getApplicationTagFactory() {
		return this.mApplicationTagFactory;
	}

	public ApplicationCredentialsFactory getApplicationCredentialsFactory() {
		return this.mApplicationCredentialsFactory;
	}

	public CityFactory getCityFactory() {
		return this.mCityFactory;
	}

	public ContinentFactory getContinentFactory() {
		return this.mContinentFactory;
	}

	public BlackFactory getBlackFactory() {
		return this.mBlackFactory;
	}

	public SubscriptionSchedulingSettingsFactory getSubscriptionSchedulingSettingsFactory() {
		return this.mSubscriptionSchedulingSettingsFactory;
	}

	public SubscriptionFactory getSubscriptionFactory() {
		return this.mSubscriptionFactory;
	}

	public SubscriptionSchedulingFactory getSubscriptionSchedulingFactory() {
		return this.mSubscriptionSchedulingFactory;
	}

	public ObjectProfileFactory getObjectProfileFactory() {
		return this.mObjectProfileFactory;
	}

	public AnnuFactory getAnnuFactory() {
		return this.mAnnuFactory;
	}

	public MusicStyleFactory getMusicStyleFactory() {
		return this.mMusicStyleFactory;
	}

	public UserFriendsAddressFactory getUserFriendsAddressFactory() {
		return this.mUserFriendsAddressFactory;
	}

	public SchedulingTypeFactory getSchedulingTypeFactory() {
		return this.mSchedulingTypeFactory;
	}

	public ApplicationTempFactory getApplicationTempFactory() {
		return this.mApplicationTempFactory;
	}

	public ApplicationSettingFactory getApplicationSettingFactory() {
		return this.mApplicationSettingFactory;
	}

	public ApplicationLangFactory getApplicationLangFactory() {
		return this.mApplicationLangFactory;
	}

	public ProductFactory getProductFactory() {
		return this.mProductFactory;
	}

	public StoreCityFactory getStoreCityFactory() {
		return this.mStoreCityFactory;
	}

	public StoreFactory getStoreFactory() {
		return this.mStoreFactory;
	}

	public NewsFactory getNewsFactory() {
		return this.mNewsFactory;
	}

	public PressFactory getPressFactory() {
		return this.mPressFactory;
	}

	public ApplicationContentFactory getApplicationContentFactory() {
		return this.applicationContentFactory;
	}

	public ResourceFactory getResourceFactory() {
		return this.resourceFactory;
	}

	public NabcastResourceFactory getNabcastResourceFactory() {
		return this.nabcastResourceFactory;
	}

	public SignatureFactory getSignatureFactory() {
		return this.signatureFactory;
	}

	public MimeTypeFactory getMimeTypeFactory() {
		return this.mMimeTypeFactory;
	}

	public UserPwdFactory getUserPwdFactory() {
		return this.mUserPwdFactory;
	}

	public UserEmailFactory getUserEmailFactory() {
		return this.userEmailFactory;
	}

	public SubscriptionLogFactory getSubscriptionLogFactory() {
		return this.subscriptionLogFactory;
	}

	public GroupFactory getGroupFactory() {
		return this.groupFactory;
	}

	public FeedSubscriptionFactory getFeedSubscriptionFactory() {
		return this.feedSubscriptionFactory;
	}

	public NotificationFactory getNotificationFactory() {
		return this.mNotificationFactory;
	}
}
