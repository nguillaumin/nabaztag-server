package net.violet.platform.datamodel.factories.mock;

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

public class FactoryMock implements Factory {

	private final MimeTypeFactory mimeTypeFactory = new MimeTypeFactoryMock();
	private final FilesFactory filesFactory = new FilesFactoryMock();
	private final VActionFactory vactionFactory = new VActionFactoryMock();
	private final ConfigFilesFactory configFilesFactory = new ConfigFilesFactoryMock();
	private final ContentFactory contentFactory = new ContentFactoryMock();
	private final CityFactory cityFactory = new CityFactoryMock();
	private final ContinentFactory continentFactory = new ContinentFactoryMock();
	private final MessageFactory messageFactory = new MessageFactoryMock();
	private final MusicFactory musicFactory = new MusicFactoryMock();
	private final ObjectHasReadContentFactoryMock objectHasReadContentFactoryMock = new ObjectHasReadContentFactoryMock();
	private final EvSeqFactory evSeqFactory = new EvSeqFactoryMock();
	private final MessengerFactory messengerFactory = new MessengerFactoryMock();
	private final ServiceFactory serviceFactory = new ServiceFactoryMock();
	private final AnimFactory animFactory = new AnimFactoryMock();
	private final UserFactory userFactory = new UserFactoryMock();
	private final MessageCounterFactory messageCounterFactory = new MessageCounterFactoryMock();
	private final MessageReceivedFactory messageReceivedFactory = new MessageReceivedFactoryMock();
	private final MessageSentFactory messageSentFactory = new MessageSentFactoryMock();
	private final LangFactory langFactory = new LangFactoryMock();
	private final DicoFactory dicoFactory = new DicoFactoryMock();
	private final VObjectFactory vObjectFactory = new VObjectFactoryMock();
	private final TimezoneFactory timezoneFactory = new TimezoneFactoryMock();
	private final EventFactory eventFactory = new EventFactoryMock();
	private final NathanMp3Factory nathanMp3Factory = new NathanMp3FactoryMock();
	private final NathanTagFactory nathanTagFactory = new NathanTagFactoryMock();
	private final NathanVersionFactory nathanVersionFactory = new NathanVersionFactoryMock();
	private final ScheduledMessageFactory scheduledMessageFactory = new ScheduledMessageFactoryMock();
	private final StatsMessageFactory statsFactory = new StatsMessageFactoryMock();
	private final TtsVoiceFactory ttsVoiceFactory = new TtsVoiceFactoryMock();
	private final UserPrefsFactory userPrefsFactory = new UserPrefsFactoryMock();
	private final TagTmpSiteFactory tagTmpSiteFactory = new TagTmpSiteFactoryMock();
	private final ApplicationCategoryFactory applicationCategoryFactory = new ApplicationCategoryFactoryMock();
	private final ApplicationFactory applicationFactory = new ApplicationFactoryMock();
	private final ApplicationPackageFactory applicationPackageFactory = new ApplicationPackageFactoryMock();
	private final ApplicationApiLibFactory applicationApiLibFactory = new ApplicationApiLibFactoryMock();
	private final ApplicationProfileFactory applicationProfileFactory = new ApplicationProfileFactoryMock();
	private final ApplicationTagFactory applicationTagFactory = new ApplicationTagFactoryMock();
	private final ApplicationCredentialsFactory applicationCredentialsFactory = new ApplicationCredentialsFactoryMock();
	private final ApplicationTempFactory applicationTempFactory = new ApplicationTempFactoryMock();
	private final ApplicationSettingFactory applicationSettingFactory = new ApplicationSettingFactoryMock();
	private final NabcastCategFactory nabcastCategFactory = new NabcastCategFactoryMock();
	private final HardwareFactory hardwareFactory = new HardwareFactoryMock();
	private final SourceFactory sourcefactory = new SourceFactoryMock();
	private final ObjectSleepFactory objectSleepFactory = new ObjectSleepFactoryMock();
	private final AppletSettingsFactory appletSettingsFactory = new AppletSettingsFactoryMock();
	private final FriendsListsFactory friendsListsFactory = new FriendsListsFactoryMock();
	private final CountryFactory paysFactory = new CountryFactoryMock();
	private final ContextFactory contextFactory = new ContextFactoryMock();
	private final HintFactory hintFactory = new HintFactoryMock();
	private final ContactFactory contactFactory = new ContactFactoryMock();
	private final CategFactory categFactory = new CategFactoryMock();
	private final BlackFactory blackFactory = new BlackFactoryMock();
	private final SubscriptionSchedulingSettingsFactory subscriptionSchedulingSettingsFactory = new SubscriptionSchedulingSettingsFactoryMock();
	private final SubscriptionFactory subscriptionFactory = new SubscriptionFactoryMock();
	//private final SubscriptionSettingsFactory subscriptionSettingsFactory = new SubscriptionSettingsFactoryMock();
	private final SubscriptionSchedulingFactory subscriptionSchedulingFactory = new SubscriptionSchedulingFactoryMock();
	private final ObjectProfileFactory objectProfileFactory = new ObjectProfileFactoryMock();
	private final AnnuFactory annuFactory = new AnnuFactoryMock();
	private final MusicStyleFactory musicStyleFactory = new MusicStyleFactoryMock();
	private final UserFriendsAddressFactory userFriendsAddressFactory = new UserFriendsAddressFactoryMock();
	private final UserPwdFactory userPwdFactory = new UserPwdFactoryMock();
	private final SchedulingTypeFactory schedulingTypeFactory = new SchedulingTypeFactoryMock();
	private final ZtampFactory ztampFactory = new ZtampFactoryMock();
	private final ProductFactory productFactory = new ProductFactoryMock();
	private final StoreCityFactory storeCityFactory = new StoreCityFactoryMock();
	private final StoreFactory storeFactory = new StoreFactoryMock();
	private final NewsFactory newsFactory = new NewsFactoryMock();
	private final PressFactory pressFactory = new PressFactoryMock();
	private final ApplicationLangFactory applicationLangFactory = new ApplicationLangFactoryMock();
	private final ApplicationContentFactory applicationContentFactory = new ApplicationContentFactoryMock();
	private final ResourceFactory resourceFactory = new ResourceFactoryMock();
	private final NabcastResourceFactory nabcastResourceFactory = new NabcastResourceFactoryMock();
	private final SignatureFactory signatureFactory = new SignatureFactoryMock();
	private final UserEmailFactory userEmailFactory = new UserEmailFactoryMock();
	private final SubscriptionLogFactory subscriptionLogFactory = new SubscriptionLogFactoryMock();
	private final GroupFactory groupFactory = new GroupFactoryMock();
	private final FeedFactory feedFactory = new FeedFactoryMock();
	private final FeedItemFactory feedItemFactory = new FeedItemFactoryMock();
	private final FeedSubscriptionFactory feedSubscriptionFactory = new FeedSubscriptionFactoryMock();
	private final NotificationFactory notificationFactory = new NotificationFactoryMock();

	public FeedItemFactory getFeedItemFactory() {
		return this.feedItemFactory;
	}

	public FeedFactory getFeedFactory() {
		return this.feedFactory;
	}

	public ZtampFactory getZtampFactory() {
		return this.ztampFactory;
	}

	public FilesFactory getFilesFactory() {
		return this.filesFactory;
	}

	public VActionFactory getVActionFactory() {
		return this.vactionFactory;
	}

	public ConfigFilesFactory getConfigFilesFactory() {
		return this.configFilesFactory;
	}

	public ContentFactory getContentFactory() {
		return this.contentFactory;
	}

	public MessageFactory getMessageFactory() {
		return this.messageFactory;
	}

	public MusicFactory getMusicFactory() {
		return this.musicFactory;
	}

	public ObjectHasReadContentFactory getObjectHasReadContentFactory() {
		return this.objectHasReadContentFactoryMock;
	}

	public EvSeqFactory getEvSeqFactory() {
		return this.evSeqFactory;
	}

	public MessengerFactory getMessenger() {
		return this.messengerFactory;
	}

	public ServiceFactory getServiceFactory() {
		return this.serviceFactory;
	}

	public AnimFactory getAnimFactory() {
		return this.animFactory;
	}

	public UserFactory getUserFactory() {
		return this.userFactory;
	}

	public MessageCounterFactory getMessageCounterFactory() {
		return this.messageCounterFactory;
	}

	public MessageReceivedFactory getMessageReceivedFactory() {
		return this.messageReceivedFactory;
	}

	public MessageSentFactory getMessageSentFactory() {
		return this.messageSentFactory;
	}

	public LangFactory getLangFactory() {
		return this.langFactory;
	}

	public DicoFactory getDicoFactory() {
		return this.dicoFactory;
	}

	public VObjectFactory getVObjectFactory() {
		return this.vObjectFactory;
	}

	public TimezoneFactory getTimezoneFactory() {
		return this.timezoneFactory;
	}

	public EventFactory getEventFactory() {
		return this.eventFactory;
	}

	public NathanMp3Factory getNathanMp3Factory() {
		return this.nathanMp3Factory;
	}

	public NathanTagFactory getNathanTagFactory() {
		return this.nathanTagFactory;
	}

	public NathanVersionFactory getNathanVersionFactory() {
		return this.nathanVersionFactory;
	}

	public ScheduledMessageFactory getScheduledMessageFactory() {
		return this.scheduledMessageFactory;
	}

	public StatsMessageFactory getStatsFactory() {
		return this.statsFactory;
	}

	public TtsVoiceFactory getTtsVoiceFactory() {
		return this.ttsVoiceFactory;
	}

	public UserPrefsFactory getUserPrefsFactory() {
		return this.userPrefsFactory;
	}

	public TagTmpSiteFactory getTagTmpSiteFactory() {
		return this.tagTmpSiteFactory;
	}

	public ApplicationFactory getApplicationFactory() {
		return this.applicationFactory;
	}

	public ApplicationPackageFactory getApplicationPackageFactory() {
		return this.applicationPackageFactory;
	}

	public ApplicationApiLibFactory getApplicationApiLibFactory() {
		return this.applicationApiLibFactory;
	}

	public ApplicationProfileFactory getApplicationProfileFactory() {
		return this.applicationProfileFactory;
	}

	public NabcastCategFactory getNabcastCategFactory() {
		return this.nabcastCategFactory;
	}

	public HardwareFactory getHardwareFactory() {
		return this.hardwareFactory;
	}

	public SourceFactory getSourceFactory() {
		return this.sourcefactory;
	}

	public ObjectSleepFactory getObjectSleepFactory() {
		return this.objectSleepFactory;
	}

	public AppletSettingsFactory getAppletSettingsFactory() {
		return this.appletSettingsFactory;
	}

	public FriendsListsFactory getFriendsListsFactory() {
		return this.friendsListsFactory;
	}

	public CountryFactory getCountryFactory() {
		return this.paysFactory;
	}

	public ContextFactory getContextFactory() {
		return this.contextFactory;
	}

	public HintFactory getHintFactory() {
		return this.hintFactory;
	}

	public ContactFactory getContactFactory() {
		return this.contactFactory;
	}

	public CategFactory getCategFactory() {
		return this.categFactory;
	}

	public ApplicationCategoryFactory getApplicationCategoryFactory() {
		return this.applicationCategoryFactory;
	}

	public ApplicationTagFactory getApplicationTagFactory() {
		return this.applicationTagFactory;
	}

	public ApplicationCredentialsFactory getApplicationCredentialsFactory() {
		return this.applicationCredentialsFactory;
	}

	public CityFactory getCityFactory() {
		return this.cityFactory;
	}

	public ContinentFactory getContinentFactory() {
		return this.continentFactory;
	}

	public BlackFactory getBlackFactory() {
		return this.blackFactory;
	}

	public SubscriptionSchedulingSettingsFactory getSubscriptionSchedulingSettingsFactory() {
		return this.subscriptionSchedulingSettingsFactory;
	}

	public SubscriptionFactory getSubscriptionFactory() {
		return this.subscriptionFactory;
	}

	public SubscriptionSchedulingFactory getSubscriptionSchedulingFactory() {
		return this.subscriptionSchedulingFactory;
	}

	public ObjectProfileFactory getObjectProfileFactory() {
		return this.objectProfileFactory;
	}

	public AnnuFactory getAnnuFactory() {
		return this.annuFactory;
	}

	public MusicStyleFactory getMusicStyleFactory() {
		return this.musicStyleFactory;
	}

	public UserFriendsAddressFactory getUserFriendsAddressFactory() {
		return this.userFriendsAddressFactory;
	}

	public SchedulingTypeFactory getSchedulingTypeFactory() {
		return this.schedulingTypeFactory;
	}

	public ApplicationTempFactory getApplicationTempFactory() {
		return this.applicationTempFactory;
	}

	public ApplicationSettingFactory getApplicationSettingFactory() {
		return this.applicationSettingFactory;
	}

	public ProductFactory getProductFactory() {
		return this.productFactory;
	}

	public StoreCityFactory getStoreCityFactory() {
		return this.storeCityFactory;
	}

	public StoreFactory getStoreFactory() {
		return this.storeFactory;
	}

	public NewsFactory getNewsFactory() {
		return this.newsFactory;
	}

	public PressFactory getPressFactory() {
		return this.pressFactory;
	}

	public ApplicationLangFactory getApplicationLangFactory() {
		return this.applicationLangFactory;
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
		return this.mimeTypeFactory;
	}

	public UserPwdFactory getUserPwdFactory() {
		return this.userPwdFactory;
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
		return this.notificationFactory;
	}
}
