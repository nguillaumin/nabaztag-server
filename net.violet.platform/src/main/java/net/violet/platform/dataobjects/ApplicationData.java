package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.violet.common.StringShop;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationLang;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.AbstractSettingRecordData.AbstractSettingableRecordData;
import net.violet.platform.message.MessageSignature;

import org.apache.log4j.Logger;

public class ApplicationData extends AbstractSettingableRecordData<Application, ApplicationSettingData> {

	private static final Logger LOGGER = Logger.getLogger(ApplicationData.class);

	/**
	 * This constructor MUST be protected, it is used by the RecordData class to
	 * create new instances.
	 * 
	 * @param inApplication
	 */
	protected ApplicationData(Application inApplication) {
		super(inApplication);
	}

	public static ApplicationData getData(Application inApplication) {
		try {
			return RecordData.getData(inApplication, ApplicationData.class, Application.class);
		} catch (final InstantiationException e) {
			ApplicationData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ApplicationData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			ApplicationData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			ApplicationData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Accesseur à partir d'un ID application.
	 * 
	 * @return l'application ou <code>null</code> si l'application n'existe pas.
	 * @throws NoSuchApplicationException 
	 */
	public static ApplicationData findByAPIId(String inAPIId, String inAPIKey, boolean isMandatory) throws NoSuchApplicationException {

		ApplicationData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.APPLICATION, inAPIKey);

		if (theID != 0) {
			final Application app = Factories.APPLICATION.find(theID);
			if (app != null) {
				theResult = ApplicationData.getData(app);
			}
		}

		if (((theResult == null) || !theResult.isValid()) && isMandatory) {
			throw new NoSuchApplicationException();
		}

		return theResult;
	}

	public Long getApplicationId() {
		final Application record = getRecord();
		return (record == null) ? null : record.getId();
	}

	public Application getReference() {
		return getRecord();
	}

	public UserData getOwner() {
		final Application record = getRecord();
		return UserData.getData((record == null) ? null : record.getOwner());
	}

	public ApplicationClass getApplicationClass() {
		final Application record = getRecord();
		return (record == null) ? null : record.getApplicationClass();
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.APPLICATION;
	}

	public List<ObjectType> getSupportedTypes() {
		final List<ObjectType> theResult = new LinkedList<ObjectType>();
		final Application record = getRecord();

		if (record != null) {
			for (final HARDWARE theHardware : record.getSupportedHardwares()) {
				final ObjectType theObjectType = ObjectType.findByHardware(theHardware);
				if (theObjectType != null) {
					theResult.add(theObjectType);
				}
			}
		}
		return theResult;
	}

	public Date getCreationDate() {
		final Application record = getRecord();
		return (record == null) ? null : record.getCreationDate();
	}

	public String getName() {
		final Application record = getRecord();
		return (record == null) ? net.violet.common.StringShop.EMPTY_STRING : record.getName();
	}

	public boolean isInteractive() {
		final Application record = getRecord();
		return (record == null) ? false : record.isInteractive();
	}

	public static List<ApplicationData> findByCategory(ApplicationCategoryData theCategory, int inSkip, int inCount) {
		return ApplicationData.generateList(Factories.APPLICATION.findByCategory(theCategory.getRecord(), inSkip, inCount));
	}

	public static long countByCategory(ApplicationCategoryData theCategory) {
		return Factories.APPLICATION.countByCategory(theCategory.getRecord());
	}

	public static List<ApplicationData> findByCategoryAndObjectType(ApplicationCategoryData theCategory, ObjectType inObjectType, int inSkip, int inCount) {
		return ApplicationData.generateList(Factories.APPLICATION.findByCategoryAndHardware(theCategory.getRecord(), inObjectType.getPrimaryHardware(), inSkip, inCount));
	}

	public static long countByCategoryAndObjectType(ApplicationCategoryData theCategory, ObjectType inObjectType) {
		return Factories.APPLICATION.countByCategoryAndHardware(theCategory.getRecord(), inObjectType.getPrimaryHardware());
	}

	public static List<ApplicationData> findByCategoryAndLang(ApplicationCategoryData theCategory, SiteLangData inLangData, int inSkip, int inCount) {
		return ApplicationData.generateList(Factories.APPLICATION.findByCategoryAndLang(theCategory.getRecord(), inLangData.getReference(), inSkip, inCount));
	}

	/**
	 * Returns a list of applications with the name starting with
	 * <code>name<code> (exemple : net.violet.rss.)
	 * 
	 * @param reference the language of the applications
	 * @param name the beginning of the application name (net.violet.rss.,
	 *            net.violet.podcast., net.violet.webradio.)
	 * @return a list of applications
	 */
	public static List<ApplicationData> findByLangAndApplicationNameStartingWith(SiteLangData inLangData, String name) {
		return ApplicationData.generateList(Factories.APPLICATION.findByLangAndApplicationNameStartingWith(inLangData.getReference(), name));
	}

	public static long countByCategoryAndLang(ApplicationCategoryData theCategory, SiteLangData inLangData) {
		return Factories.APPLICATION.countByCategoryAndLang(theCategory.getRecord(), inLangData.getReference());
	}

	public static List<ApplicationData> findByCategoryAndObjectTypeAndLang(ApplicationCategoryData theCategory, ObjectType inObjectType, SiteLangData inLangData, int inSkip, int inCount) {
		return ApplicationData.generateList(Factories.APPLICATION.findByCategoryAndLangAndHardware(theCategory.getRecord(), inLangData.getReference(), inObjectType.getPrimaryHardware(), inSkip, inCount));
	}

	public static long countByCategoryAndObjectTypeAndLang(ApplicationCategoryData theCategory, ObjectType inObjectType, SiteLangData inLangData) {
		return Factories.APPLICATION.countByCategoryAndLangAndHardware(theCategory.getRecord(), inLangData.getReference(), inObjectType.getPrimaryHardware());
	}

	private static List<ApplicationData> generateList(List<Application> inList) {
		final List<ApplicationData> result = new ArrayList<ApplicationData>();
		for (final Application application : inList) {
			result.add(ApplicationData.getData(application));
		}
		return result;
	}

	public static List<ApplicationData> findAllByTagAndLang(ApplicationTagData theTag, SiteLangData inLang, int inSkip, int inCount) {
		return ApplicationData.generateList(Factories.APPLICATION.findAllByTagAndLang(theTag.getRecord(), inLang.getReference(), inSkip, inCount));
	}

	public static long countByTagAndLang(ApplicationTagData theTag, SiteLangData inLang) {
		return Factories.APPLICATION.countByTagAndLang(theTag.getRecord(), inLang.getReference());
	}

	public static List<ApplicationData> findAllByTagAndLangAndObjectType(ApplicationTagData theTag, SiteLangData inLang, ObjectType inObjectType, int inSkip, int inCount) {
		return ApplicationData.generateList(Factories.APPLICATION.findAllByTagAndLangAndHardware(theTag.getRecord(), inLang.getReference(), inObjectType.getPrimaryHardware(), inSkip, inCount));
	}

	public static List<ApplicationData> findAllSubscribedByObject(VObjectData theObject) {
		final List<ApplicationData> theList = new ArrayList<ApplicationData>();
		final List<SubscriptionData> subscriptions = SubscriptionData.findAllByObject(theObject.getReference());
		for (final SubscriptionData subscription : subscriptions) {
			if (!theList.contains(subscription.getApplication())) {
				theList.add(subscription.getApplication());
			}
		}
		return theList;
	}

	public static long countByTagAndLangAndObjectType(ApplicationTagData theTag, SiteLangData inLang, ObjectType inObjectType) {
		return Factories.APPLICATION.countByTagAndLangAndHardware(theTag.getRecord(), inLang.getReference(), inObjectType.getPrimaryHardware());
	}

	public List<ApplicationTagData> findAllTagByApplication() {
		final List<ApplicationTagData> result = new ArrayList<ApplicationTagData>();
		final Application record = getRecord();
		if (record != null) {
			for (final ApplicationTag theApplicationTag : record.getTags()) {
				result.add(new ApplicationTagData(theApplicationTag));
			}
		}
		return result;
	}

	public static ApplicationData findById(long inId) {
		return ApplicationData.getData(Factories.APPLICATION.find(inId));
	}

	/**
	 * Les applications non Java (qui ont donc un package contenant les sources
	 * externes : JavaScript ) ont un fichier de configuration spécifique.
	 * L'extraction des settings en est donc différente des applications Java
	 * natives.
	 * 
	 * @return TRUE si l'application a un enregistrement dans la table
	 *         application_package
	 */
	public boolean hasExtendedConfigurationFile() {
		return (!getPackage().isEmpty());
	}

	/**
	 * @return TRUE si cette application est une application Java native
	 */
	public boolean isNativeJava() {
		return (getPackage().isEmpty());
	}

	String isVisibleString;

	public ApplicationPackageData getPackage() {
		final Application record = getRecord();
		return (record == null) ? null : new ApplicationPackageData(record.getPackage());
	}

	public ApplicationProfileData getProfile() {
		final Application record = getRecord();
		return new ApplicationProfileData((record == null) ? null : record.getProfile());
	}

	public ApplicationTempData getTemp() {
		final Application record = getRecord();
		return new ApplicationTempData((record == null) ? null : record.getTemp());
	}

	public ApplicationCategoryData getApplicationCategory() {
		final Application record = getRecord();
		return new ApplicationCategoryData((record == null) ? null : record.getCategory());
	}

	@Override
	public String toString() {
		return getName() + " [" + getRecord().getId() + "]";
	}

	public boolean isRemovable() {
		if (getRecord() != null) {
			return getRecord().isRemovable();
		}
		return false;
	}

	public boolean isVisible() {
		if (getRecord() != null) {
			return getRecord().isVisible();
		}
		return false;
	}

	public void setVisible(boolean inVisible) {
		final Application theApplication = getRecord();
		if (theApplication != null) {
			theApplication.setVisible(inVisible);
		}
	}

	public List<SiteLangData> getLangs() {
		final Application theApplication = getRecord();
		if (theApplication == null) {
			return Collections.emptyList();
		}

		final List<SiteLangData> theList = new ArrayList<SiteLangData>();
		for (final Lang aLang : theApplication.getLangs()) {
			final SiteLangData theLang = SiteLangData.get(aLang);
			if ((theLang != null) && theLang.isValid()) {
				theList.add(theLang);
			}
		}

		return theList;
	}

	public static String getRank(Application inApp, Lang inLang) {
		final ApplicationLang theApplicationLang = Factories.APPLICATION_LANG.find(inApp, inLang);
		if (theApplicationLang != null) {
			return String.valueOf(theApplicationLang.getRank());
		}
		return StringShop.EMPTY_STRING;
	}

	public static void setRank(Application inApp, Lang inLang, String inRank) {
		final ApplicationLang theApplicationLang = Factories.APPLICATION_LANG.find(inApp, inLang);
		if (theApplicationLang != null) {
			theApplicationLang.setRank(Long.valueOf(inRank));
		}
	}

	public static ApplicationData findByName(String applicationName) {
		final Application theApplication = Factories.APPLICATION.findByName(applicationName);
		if (theApplication == null) {
			return null;
		}
		return new ApplicationData(theApplication);
	}

	public void addSupportedTypes(List<ObjectType> inTypes) {
		final Application theApplication = getRecord();
		if (theApplication != null) {
			final Set<Hardware.HARDWARE> theHardwares = new HashSet<HARDWARE>();

			for (final ObjectType aType : inTypes) {
				theHardwares.addAll(aType.getHardwares());
			}
			theApplication.addHardwares(new LinkedList<HARDWARE>(theHardwares));
		}
	}

	public void addLang(SiteLangData lang) {
		final Application theApplication = getRecord();
		if (theApplication != null) {
			theApplication.addLang(lang.getRecord());
		}

	}

	@Override
	public ApplicationSettingData createSetting(String inKey, String inValue) {
		return (getRecord() == null) ? null : ApplicationSettingData.getData(Factories.APPLICATION_SETTING.create(getRecord(), inKey, inValue));
	}

	@Override
	protected List<ApplicationSettingData> getAllSettings() {
		return ApplicationSettingData.findAllByApplication(this);
	}

	public void setHardware(List<ObjectType> inObjectType) {
		final Application theApplication = getRecord();
		if (theApplication != null) {
			final List<HARDWARE> inHardwares = new ArrayList<HARDWARE>();
			for (final ObjectType inObjType : inObjectType) {
				inHardwares.add(inObjType.getPrimaryHardware());
			}
			theApplication.updateHardwares(inHardwares);
		}
	}

	public void setLanguages(List<SiteLangData> inLanguages) {
		final Application theApplication = getRecord();
		if (theApplication != null) {
			final List<Lang> theLangs = new ArrayList<Lang>();
			for (final SiteLangData inSiteLang : inLanguages) {
				if (inSiteLang.isValid()) {
					theLangs.add(inSiteLang.getRecord());
				}
			}
			theApplication.updateLangs(theLangs);
		}
	}

	public static class ApplicationBuilder {

		private final UserData owner;
		private final String applicationName;
		private final ApplicationClass applicationClass;
		private final ApplicationCategoryData applicationCategory;
		private boolean isInteractive = false;
		private boolean isRemovable = true;
		private boolean isVisible = true;

		public ApplicationBuilder(UserData owner, String applicationName, ApplicationClass applicationClass, ApplicationCategoryData applicationCategory) {
			this.owner = owner;
			this.applicationName = applicationName;
			this.applicationClass = applicationClass;
			this.applicationCategory = applicationCategory;
		}

		public void setInteractive() {
			this.isInteractive = true;
		}

		public void setUnRemovable() {
			this.isRemovable = false;
		}

		public void setInvisible() {
			this.isVisible = false;
		}

		public ApplicationData build(ProfileBuilder inProfileBuilder) {
			final Application theApplication = Factories.APPLICATION.create(this.owner.getRecord(), this.applicationName, this.applicationClass, this.applicationCategory.getRecord(), this.isInteractive, this.isVisible, this.isRemovable);
			if ((theApplication != null) && (inProfileBuilder != null)) {
				final ApplicationProfileData profile = inProfileBuilder.build(theApplication);
				if ((profile == null) || !profile.isValid()) {
					theApplication.delete();
					return null;
				}
			}

			return ApplicationData.getData(theApplication);
		}

		public class ProfileBuilder {

			private final String title;
			private final String description;
			private final String instructions;
			private final String url;
			private boolean isOpenSource = false;
			private FilesData settingFile = FilesData.getData(null);
			private FilesData schedulingFile = FilesData.getData(null);
			private FilesData picture = FilesData.getData(null);
			private FilesData icon = FilesData.getData(null);
			private FilesData announce = FilesData.getData(null);

			public ProfileBuilder(String inTitle, String inDescription, String inInstructions, String inUrl) {
				this.title = inTitle;
				this.description = inDescription;
				this.instructions = inInstructions;
				this.url = inUrl;
			}

			public void setOpenSource() {
				this.isOpenSource = true;
			}

			public ProfileBuilder addSettingsFile(FilesData inFile) {
				this.settingFile = inFile;
				return this;
			}

			public ProfileBuilder addSchedulingFile(FilesData inFile) {
				this.schedulingFile = inFile;
				return this;
			}

			public ProfileBuilder addPictureFile(FilesData inFile) {
				this.picture = inFile;
				return this;
			}

			public ProfileBuilder addIconFile(FilesData inFile) {
				this.icon = inFile;
				return this;
			}

			public ProfileBuilder addAnnounceFile(FilesData inFile) {
				this.announce = inFile;
				return this;
			}

			private ApplicationProfileData build(Application inApplication) {
				return ApplicationProfileData.getData(Factories.APPLICATION_PROFILE.create(inApplication, this.title, this.description, this.isOpenSource, this.settingFile.getRecord(), this.schedulingFile.getRecord(), this.picture.getRecord(), this.icon.getRecord(), this.announce.getRecord(), this.instructions, this.url));
			}
		}
	}

	public void update(String inName, ApplicationClass inClass, ApplicationCategoryData inCategory, boolean isInteractive, boolean isVisible, boolean isRemovable) {
		final Application theRecord = getRecord();
		if (theRecord != null) {
			theRecord.update(inName, inClass, inCategory.getRecord(), isInteractive, isVisible, isRemovable);
		}
	}

	public MessageSignature getMessageSignature() {
		final Application theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getMessageSignature();
		}
		return null;
	}

	public long getId() {
		return getRecord().getId();
	}

	public static List<ApplicationData> findAllBySettingAndValue(String key, String value) {
		return ApplicationData.generateList(Factories.APPLICATION.findAllBySetting(key, value));
	}
}
