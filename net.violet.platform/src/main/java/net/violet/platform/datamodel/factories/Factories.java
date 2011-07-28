package net.violet.platform.datamodel.factories;

import net.violet.platform.datamodel.factories.common.Factory;
import net.violet.platform.datamodel.factories.implementations.FactoryImpl;

public final class Factories {

	private static Factory createFactory() {
		String theImplName = System.getProperty("net.violet.platform.datamodel.factories.impl");
		if (theImplName == null) {
			theImplName = FactoryImpl.class.getName();
		}
		Factory theResult = null;
		try {
			final Class theImplClass = Class.forName(theImplName);
			theResult = (Factory) theImplClass.newInstance();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		return theResult;
	}

	// Par défaut, on a la version DB.
	private static final Factory FACTORY = Factories.createFactory();

	public static final MimeTypeFactory MIME_TYPE = Factories.FACTORY.getMimeTypeFactory();

	public static final FilesFactory FILES = Factories.FACTORY.getFilesFactory();

	public static final VActionFactory VACTION = Factories.FACTORY.getVActionFactory();

	public static final ConfigFilesFactory CONFIG_FILES = Factories.FACTORY.getConfigFilesFactory();

	public static final CategFactory CATEG = Factories.FACTORY.getCategFactory();

	public static final ContentFactory CONTENT = Factories.FACTORY.getContentFactory();

	public static final MessageFactory MESSAGE = Factories.FACTORY.getMessageFactory();

	public static final FeedFactory FEED = Factories.FACTORY.getFeedFactory();

	public static final FeedItemFactory FEED_ITEM = Factories.FACTORY.getFeedItemFactory();

	public static final MusicFactory MUSIC = Factories.FACTORY.getMusicFactory();

	public static final MusicStyleFactory MUSIC_STYLE = Factories.FACTORY.getMusicStyleFactory();

	public static final ObjectHasReadContentFactory OBJECT_HAS_READ_CONTENT = Factories.FACTORY.getObjectHasReadContentFactory();

	public static final EvSeqFactory EVSEQ = Factories.FACTORY.getEvSeqFactory();

	public static final ServiceFactory SERVICE = Factories.FACTORY.getServiceFactory();

	public static final MessengerFactory MESSENGER = Factories.FACTORY.getMessenger();

	public static final MessageCounterFactory MESSAGE_COUNTER = Factories.FACTORY.getMessageCounterFactory();

	public static final AnimFactory ANIM = Factories.FACTORY.getAnimFactory();

	public static final UserFactory USER = Factories.FACTORY.getUserFactory();

	public static final MessageReceivedFactory MESSAGE_RECEIVED = Factories.FACTORY.getMessageReceivedFactory();

	public static final MessageSentFactory MESSAGE_SENT = Factories.FACTORY.getMessageSentFactory();

	public static final LangFactory LANG = Factories.FACTORY.getLangFactory();

	public static final DicoFactory DICO = Factories.FACTORY.getDicoFactory();

	public static final VObjectFactory VOBJECT = Factories.FACTORY.getVObjectFactory();

	public static final TimezoneFactory TIMEZONE = Factories.FACTORY.getTimezoneFactory();

	public static final EventFactory EVENT = Factories.FACTORY.getEventFactory();

	public static final NathanMp3Factory NATHAN_MP3 = Factories.FACTORY.getNathanMp3Factory();

	public static final NathanTagFactory NATHAN_TAG = Factories.FACTORY.getNathanTagFactory();

	public static final NathanVersionFactory NATHAN_VERSION = Factories.FACTORY.getNathanVersionFactory();

	public static final ScheduledMessageFactory SCHEDULED_MESSAGE = Factories.FACTORY.getScheduledMessageFactory();

	public static final StatsMessageFactory STATS = Factories.FACTORY.getStatsFactory();

	public static final TtsVoiceFactory TTSVOICE = Factories.FACTORY.getTtsVoiceFactory();

	public static final UserFriendsAddressFactory USER_FRIENDS_ADDRESS = Factories.FACTORY.getUserFriendsAddressFactory();

	public static final UserPrefsFactory USER_PREFS = Factories.FACTORY.getUserPrefsFactory();

	public static final UserPwdFactory USER_PWD = Factories.FACTORY.getUserPwdFactory();

	public static final TagTmpSiteFactory TAG_TMP_SITE = Factories.FACTORY.getTagTmpSiteFactory();

	/* BEWARE INITIALIZE BEFORE APPLICATION */
	public static final ApplicationCategoryFactory APPLICATION_CATEGORY = Factories.FACTORY.getApplicationCategoryFactory();

	public static final ApplicationFactory APPLICATION = Factories.FACTORY.getApplicationFactory();

	public static final ApplicationTempFactory APPLICATION_TEMP = Factories.FACTORY.getApplicationTempFactory();

	public static final ApplicationProfileFactory APPLICATION_PROFILE = Factories.FACTORY.getApplicationProfileFactory();

	public static final ApplicationPackageFactory APPLICATION_PACKAGE = Factories.FACTORY.getApplicationPackageFactory();

	public static final ApplicationApiLibFactory APPLICATION_API_LIB = Factories.FACTORY.getApplicationApiLibFactory();

	public static final ApplicationCredentialsFactory APPLICATION_CREDENTIALS = Factories.FACTORY.getApplicationCredentialsFactory();

	public static final ApplicationLangFactory APPLICATION_LANG = Factories.FACTORY.getApplicationLangFactory();

	public static final NabcastCategFactory NABCAST_CATEG = Factories.FACTORY.getNabcastCategFactory();

	public static final HardwareFactory HARDWARE = Factories.FACTORY.getHardwareFactory();

	public static final SourceFactory SOURCE = Factories.FACTORY.getSourceFactory();

	public static final ObjectSleepFactory OBJECT_SLEEP = Factories.FACTORY.getObjectSleepFactory();

	public static final AppletSettingsFactory APPLET_SETTINGS = Factories.FACTORY.getAppletSettingsFactory();

	public static final FriendsListsFactory FRIENDS_LISTS = Factories.FACTORY.getFriendsListsFactory();

	public static final CountryFactory COUNTRIES = Factories.FACTORY.getCountryFactory();

	public static final CityFactory CITY = Factories.FACTORY.getCityFactory();

	public static final ContinentFactory CONTINENT = Factories.FACTORY.getContinentFactory();

	public static final ContextFactory CONTEXT = Factories.FACTORY.getContextFactory();

	public static final HintFactory HINT = Factories.FACTORY.getHintFactory();

	public static final ContactFactory CONTACT = Factories.FACTORY.getContactFactory();

	public static final ApplicationTagFactory APPLICATION_TAG = Factories.FACTORY.getApplicationTagFactory();

	public static final BlackFactory BLACK = Factories.FACTORY.getBlackFactory();

	public static final SubscriptionSchedulingSettingsFactory SUBSCRIPTION_SCHEDULING_SETTINGS = Factories.FACTORY.getSubscriptionSchedulingSettingsFactory();

	public static final SubscriptionFactory SUBSCRIPTION = Factories.FACTORY.getSubscriptionFactory();

	public static final SubscriptionSchedulingFactory SUBSCRIPTION_SCHEDULING = Factories.FACTORY.getSubscriptionSchedulingFactory();

	public static final AnnuFactory ANNU = Factories.FACTORY.getAnnuFactory();

	public static final ObjectProfileFactory OBJECT_PROFILE = Factories.FACTORY.getObjectProfileFactory();

	public static final SchedulingTypeFactory SCHEDULING_TYPE = Factories.FACTORY.getSchedulingTypeFactory();

	public static final ZtampFactory ZTAMP = Factories.FACTORY.getZtampFactory();

	public static final ApplicationSettingFactory APPLICATION_SETTING = Factories.FACTORY.getApplicationSettingFactory();

	public static final ProductFactory PRODUCT = Factories.FACTORY.getProductFactory();

	public static final StoreCityFactory STORE_CITY = Factories.FACTORY.getStoreCityFactory();

	public static final StoreFactory STORE = Factories.FACTORY.getStoreFactory();

	public static final PressFactory PRESS = Factories.FACTORY.getPressFactory();

	public static final NewsFactory NEWS = Factories.FACTORY.getNewsFactory();

	public static final ApplicationContentFactory APPLICATION_CONTENT = Factories.FACTORY.getApplicationContentFactory();

	public static final ResourceFactory RESOURCE = Factories.FACTORY.getResourceFactory();

	public static final NabcastResourceFactory NABCAST_RESOURCE = Factories.FACTORY.getNabcastResourceFactory();

	public static final SignatureFactory SIGNATURE = Factories.FACTORY.getSignatureFactory();

	public static final UserEmailFactory USER_EMAIL = Factories.FACTORY.getUserEmailFactory();

	public static final SubscriptionLogFactory SUBSCRIPTION_LOG = Factories.FACTORY.getSubscriptionLogFactory();

	public static final GroupFactory GROUP = Factories.FACTORY.getGroupFactory();

	public static final FeedSubscriptionFactory FEED_SUBSCRIPTION = Factories.FACTORY.getFeedSubscriptionFactory();

	public static final NotificationFactory NOTIFICATION = Factories.FACTORY.getNotificationFactory();
}
