package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.ListAssociation;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.applets.interactive.CommonPlayer;
import net.violet.platform.applets.interactive.NathanPlayer;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.util.UpdateMap;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class ApplicationImpl extends ObjectRecord<Application, ApplicationImpl> implements Application {

	private static final Logger LOGGER = Logger.getLogger(ApplicationImpl.class);

	private static final Map<Long, Application> applicationCacheByAppletId = new HashMap<Long, Application>();

	public static final SQLObjectSpecification<ApplicationImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationImpl>("application", ApplicationImpl.class, new SQLKey[] { new SQLKey("application_id") });

	private static final String[] NEW_COLUMNS = new String[] { "user_id", "application_name", "application_class", "application_interactive", "application_category_id", "application_visible", "isRemovable" };

	protected long application_id;
	protected long user_id;
	protected String application_name;
	protected Timestamp application_creation_date;
	protected String application_class;
	protected long application_category_id;
	protected boolean application_interactive;
	protected boolean application_visible;
	protected boolean isRemovable;

	/**
	 * Langs and Tags linked to the application (associatives)
	 */
	private ListAssociation<ApplicationImpl, Lang, LangImpl> mLangs = null; // implements List < Lang >
	private ListAssociation<ApplicationImpl, ApplicationTag, ApplicationTagImpl> mTags = null; // implements List < ApplicationTag >

	/**
	 * Supported hardwares for the application TODO chdes : c'est quand même
	 * vraiment lourdingue ces deux listes à tenir synchronisées..!! A CHANGER
	 */
	private ListAssociation<ApplicationImpl, Hardware, HardwareImpl> mHardwares; // implements List < Hardware >
	private final List<HARDWARE> mHARDWARES = new LinkedList<HARDWARE>();

	/**
	 * Creator/owner of the application
	 */
	private final SingleAssociationNotNull<Application, User, UserImpl> mOwner;

	/**
	 * Application category
	 */
	private final SingleAssociationNotNull<Application, ApplicationCategory, ApplicationCategoryImpl> mCategory;

	private final SingleAssociationNull<Application, ApplicationProfile, ApplicationProfileImpl> mProfile;
	private final SingleAssociationNull<Application, ApplicationPackage, ApplicationPackageImpl> mPackage;
	private final SingleAssociationNull<Application, ApplicationTemp, ApplicationTempImpl> mTemp;

	protected ApplicationImpl() {
		this.mOwner = new SingleAssociationNotNull<Application, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
		this.mProfile = new SingleAssociationNull<Application, ApplicationProfile, ApplicationProfileImpl>(this, "application_id", ApplicationProfileImpl.SPECIFICATION, ApplicationProfileImpl.SPECIFICATION.getTableKeys()[0]);
		this.mPackage = new SingleAssociationNull<Application, ApplicationPackage, ApplicationPackageImpl>(this, "application_id", ApplicationPackageImpl.SPECIFICATION, ApplicationPackageImpl.SPECIFICATION.getTableKeys()[0]);
		this.mTemp = new SingleAssociationNull<Application, ApplicationTemp, ApplicationTempImpl>(this, "application_id", ApplicationTempImpl.SPECIFICATION, ApplicationTempImpl.SPECIFICATION.getTableKeys()[0]);
		this.mCategory = new SingleAssociationNotNull<Application, ApplicationCategory, ApplicationCategoryImpl>(this, "application_category_id", ApplicationCategoryImpl.SPECIFICATION, ApplicationCategoryImpl.SPECIFICATION.getPrimaryKey());
	}

	protected ApplicationImpl(long id) throws NoSuchElementException, SQLException {
		init(id);
		this.mOwner = new SingleAssociationNotNull<Application, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
		this.mProfile = new SingleAssociationNull<Application, ApplicationProfile, ApplicationProfileImpl>(this, "application_id", ApplicationProfileImpl.SPECIFICATION, ApplicationProfileImpl.SPECIFICATION.getTableKeys()[0]);
		this.mPackage = new SingleAssociationNull<Application, ApplicationPackage, ApplicationPackageImpl>(this, "application_id", ApplicationPackageImpl.SPECIFICATION, ApplicationPackageImpl.SPECIFICATION.getTableKeys()[0]);
		this.mTemp = new SingleAssociationNull<Application, ApplicationTemp, ApplicationTempImpl>(this, "application_id", ApplicationTempImpl.SPECIFICATION, ApplicationTempImpl.SPECIFICATION.getTableKeys()[0]);
		this.mCategory = new SingleAssociationNotNull<Application, ApplicationCategory, ApplicationCategoryImpl>(this, "application_category_id", ApplicationCategoryImpl.SPECIFICATION, ApplicationCategoryImpl.SPECIFICATION.getPrimaryKey());
	}

	public ApplicationImpl(String inName, User inUser, Application.ApplicationClass inClass, ApplicationCategory inCategory, boolean isInteractive, boolean isVisible, boolean isRemovable) throws SQLException {

		this.user_id = inUser.getId();
		this.application_name = inName;
		this.application_category_id = inCategory.getId();
		this.application_class = inClass.toString();
		this.application_interactive = isInteractive;
		this.application_visible = isVisible;
		this.isRemovable = isRemovable;

		init(ApplicationImpl.NEW_COLUMNS);

		// create the references
		this.mOwner = new SingleAssociationNotNull<Application, User, UserImpl>(this, "user_id", UserImpl.SPECIFICATION);
		this.mProfile = new SingleAssociationNull<Application, ApplicationProfile, ApplicationProfileImpl>(this, "application_id", ApplicationProfileImpl.SPECIFICATION, ApplicationProfileImpl.SPECIFICATION.getTableKeys()[0]);
		this.mPackage = new SingleAssociationNull<Application, ApplicationPackage, ApplicationPackageImpl>(this, "application_id", ApplicationPackageImpl.SPECIFICATION, ApplicationPackageImpl.SPECIFICATION.getTableKeys()[0]);
		this.mTemp = new SingleAssociationNull<Application, ApplicationTemp, ApplicationTempImpl>(this, "application_id", ApplicationTempImpl.SPECIFICATION, ApplicationTempImpl.SPECIFICATION.getTableKeys()[0]);
		this.mCategory = new SingleAssociationNotNull<Application, ApplicationCategory, ApplicationCategoryImpl>(this, "application_category_id", ApplicationCategoryImpl.SPECIFICATION, ApplicationCategoryImpl.SPECIFICATION.getPrimaryKey());
	}

	@Override
	public SQLObjectSpecification<ApplicationImpl> getSpecification() {
		return ApplicationImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.application_id;
	}

	public Timestamp getCreationDate() {
		return this.application_creation_date;
	}

	public String getName() {
		return this.application_name;
	}

	public String getDescription() {
		return getProfile().getDescription();
	}

	public boolean isInteractive() {
		return this.application_interactive;
	}

	public boolean isVisible() {
		return this.application_visible;
	}

	public String getIcone() {
		return getTemp().getIcone();
	}

	public String getImg() {
		return getTemp().getImage();
	}

	public String getShortcut() {
		return getTemp().getShortcut();
	}

	public String getLink() {
		return getTemp().getLink();
	}

	public User getOwner() {
		return this.mOwner.get(this.user_id);
	}

	/**
	 * @return the application package or NULL
	 * @see net.violet.platform.datamodel.Application#getPackage()
	 */
	public ApplicationPackage getPackage() {
		return this.mPackage.get(this.application_id);
	}

	/**
	 * @return the application profile or NULL
	 * @see net.violet.platform.datamodel.Application#getProfile()
	 */
	public ApplicationProfile getProfile() {
		return this.mProfile.get(this.application_id);
	}

	public ApplicationClass getApplicationClass() {
		return ApplicationClass.getByName(this.application_class);
	}

	public ApplicationCategory getCategory() {
		return this.mCategory.get(this.application_category_id);
	}

	public void setApplicationCategory(ApplicationCategory category) {
		this.application_category_id = category.getId();
		this.mCategory.set(category);
	}

	public ApplicationTemp getTemp() {
		return this.mTemp.get(this.application_id);
	}

	public List<HARDWARE> getSupportedHardwares() {
		getSupportedHardwareList();
		return this.mHARDWARES;
	}

	public void addHardware(HARDWARE inHardware) {
		addHardwares(Collections.singletonList(inHardware));
	}

	public void addHardwares(List<HARDWARE> inHardwares) {
		getSupportedHardwareList();
		synchronized (this.mHARDWARES) {
			for (final Hardware.HARDWARE aHardware : inHardwares) {
				if (!this.mHARDWARES.contains(aHardware)) {
					this.mHardwares.add(aHardware.getHardware());
					this.mHARDWARES.add(aHardware);
				}
			}
		}
	}

	public void updateHardwares(List<HARDWARE> inHardwares) {
		getSupportedHardwareList();
		synchronized (this.mHARDWARES) {
			this.mHARDWARES.clear();
			this.mHARDWARES.addAll(inHardwares);

			final List<Hardware> theList = new LinkedList<Hardware>();
			for (final Hardware.HARDWARE aHardware : inHardwares) {
				theList.add(aHardware.getHardware());
			}

			this.mHardwares.update(theList);
		}
	}

	public void removeHardware(HARDWARE inHardware) {
		getSupportedHardwareList();
		synchronized (this.mHARDWARES) {
			if (this.mHARDWARES.contains(inHardware)) {
				this.mHardwares.remove(inHardware.getHardware());
				this.mHARDWARES.remove(inHardware);
			}
		}
	}

	public boolean hasSupportedHardware(HARDWARE inHardware) {
		return getSupportedHardwares().contains(inHardware);
	}

	private ListAssociation<ApplicationImpl, Hardware, HardwareImpl> getSupportedHardwareList() {

		if (this.mHardwares == null) {
			synchronized (this.mHARDWARES) {
				if (this.mHardwares == null) {
					try {
						this.mHardwares = ListAssociation.createListAssociation(this, HardwareImpl.SPECIFICATION, "application_hardware", "application_id", "hardware_id");

						for (final Hardware aHardware : this.mHardwares) {
							this.mHARDWARES.add(HARDWARE.findById(aHardware.getId()));
						}
					} catch (final SQLException sqle) {
						ApplicationImpl.LOGGER.error(sqle, sqle);
					}
				}
			}
		}

		return this.mHardwares;
	}

	/**
	 * Get the list of languages this application has been translated into
	 */
	public synchronized ListAssociation<ApplicationImpl, Lang, LangImpl> getLangs() {

		if (this.mLangs == null) {
			try {
				this.mLangs = ListAssociation.createListAssociation(this, LangImpl.SPECIFICATION, "application_lang", "application_id", StringShop.LANG_ID);

			} catch (final SQLException sqle) {
				ApplicationImpl.LOGGER.error(sqle, sqle);
			}
		}
		return this.mLangs;
	}

	/**
	 * @see net.violet.platform.datamodel.Application#hasLang(net.violet.platform.datamodel.Lang)
	 */
	public boolean hasLang(Lang inLang) {
		return getLangs().contains(inLang);
	}

	/**
	 * @see net.violet.platform.datamodel.Application#addLang(net.violet.platform.datamodel.Lang)
	 */
	public synchronized void addLang(Lang inLang) {
		if (!getLangs().contains(inLang)) {
			this.mLangs.add(inLang);
		}
	}

	/**
	 * @see net.violet.platform.datamodel.Application#removeLang(net.violet.platform.datamodel.Lang)
	 */
	public void removeLang(Lang inLang) {
		getLangs().remove(inLang);
	}

	/**
	 * @see net.violet.platform.datamodel.Application#updateLangs(java.util.List)
	 */
	public void updateLangs(List<Lang> inUpdatedLangs) {
		getLangs().update(inUpdatedLangs);
	}

	/**
	 * Update the application rank in one specific lang !
	 * @see net.violet.platform.datamodel.Application#updateRankInLang(net.violet.platform.datamodel.Lang, int)
	 */
	public void updateRankInLang(Lang inLang, int inRank) {
		ApplicationLang hasLangSupport = Factories.APPLICATION_LANG.find(this, inLang);
		if (hasLangSupport == null) {
			hasLangSupport = new ApplicationLangImpl(this, inLang, inRank);
			addLang(inLang);
		} else {
			hasLangSupport.setRank(inRank);
		}
	}

	/**
	 * Update the application rank for all supported langs !
	 * @see net.violet.platform.datamodel.Application#updateRankInLang(net.violet.platform.datamodel.Lang, int)
	 */
	public void updateRank(int inRank) {

		for (final Lang appLang : getLangs()) {
			updateRankInLang(appLang, inRank);
		}
	}

	/**
	 * @return List<ApplicationTag>
	 */
	public synchronized ListAssociation<ApplicationImpl, ApplicationTag, ApplicationTagImpl> getTags() {
		if (this.mTags == null) {
			try {
				this.mTags = ListAssociation.createListAssociation(this, ApplicationTagImpl.SPECIFICATION, "application_has_tag", "application_id", "tag_id");
			} catch (final SQLException anException) {
				ApplicationImpl.LOGGER.fatal(anException, anException);
			}
		}
		return this.mTags;
	}

	/**
	 * @see net.violet.platform.datamodel.Application#hasTag(net.violet.platform.datamodel.ApplicationTag)
	 */
	public boolean hasTag(ApplicationTag inTag) {
		return getTags().contains(inTag);
	}

	/**
	 * @see net.violet.platform.datamodel.Application#addTag(net.violet.platform.datamodel.ApplicationTag)
	 */
	public synchronized void addTag(ApplicationTag inTag) {
		if (!getTags().contains(inTag)) {
			this.mTags.add(inTag);
		}
	}

	/**
	 * @see net.violet.platform.datamodel.Application#removeTag(net.violet.platform.datamodel.ApplicationTag)
	 */
	public void removeTag(ApplicationTag inTag) {
		getTags().remove(inTag);
	}

	/**
	 * @see net.violet.platform.datamodel.Application#updateTags(net.violet.platform.datamodel.ApplicationTag)
	 */
	public void updateTags(List<ApplicationTag> inUpdatedTags) {
		getTags().update(inUpdatedTags);
	}

	/**
	 * @see net.violet.platform.datamodel.Application#update(java.lang.String,
	 *      net.violet.platform.datamodel.Application.ApplicationClass, boolean,
	 *      net.violet.platform.datamodel.ApplicationCategory)
	 */
	public void update(String inAppName, ApplicationClass inAppClass, ApplicationCategory inAppCategory, boolean isInteractive, boolean isVisible, boolean inIsRemovable) {

		final UpdateMap updMap = new UpdateMap();
		this.application_name = updMap.updateField("application_name", this.application_name, inAppName);
		this.application_class = updMap.updateField("application_class", this.application_class, inAppClass.name());
		this.mCategory.set(inAppCategory);
		this.application_interactive = updMap.updateField("application_interactive", this.application_interactive, isInteractive);
		this.application_visible = updMap.updateField("application_visible", this.application_visible, isVisible);
		this.isRemovable = updMap.updateField("isRemovable", this.isRemovable, inIsRemovable);

		update(updMap);
	}

	public static int walkRssAndPodcastFree(RecordWalker<Application> inWalker) {

		final List<Object> theValues = Arrays.asList(new Object[] {});
		final String condition = " application_name like 'net.violet.rss.%' or application_name like 'net.violet.podcast.%'";
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(ApplicationImpl.SPECIFICATION, condition, theValues, null, null /* order by*/, 0 /* skip */, inWalker);
		} catch (final SQLException anException) {
			ApplicationImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public static Application findByAppletId(long appletId, Long isbn) {
		final Application theNabLife = ApplicationImpl.applicationCacheByAppletId.get(isbn);
		if (theNabLife != null) {
			return theNabLife;
		}
		final StringBuilder link = new StringBuilder();

		if (appletId == CommonPlayer.APPLET_ID) {
			link.append("../action/srvPlayer.do?appletId=");
		} else if (appletId == NathanPlayer.APPLET_ID) {
			link.append("../action/srvNathan.do?appletId=");
		}

		link.append(appletId);
		link.append("&dispatch=load&isbn=");
		link.append(isbn);

		final ApplicationTemp nabLifeResult = ApplicationTempImpl.findByLink(link.toString());
		if (nabLifeResult != null) {
			ApplicationImpl.applicationCacheByAppletId.put(isbn, nabLifeResult.getApplication());
			return nabLifeResult.getApplication();
		}
		return null;
	}

	public MessageSignature getMessageSignature() {
		return Application.ApplicationCommon.getMessageSignature(this);
	}

	public boolean isRemovable() {
		return this.isRemovable;
	}

	public void setVisible(boolean isVisible) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setVisible(theUpdateMap, isVisible);
		update(theUpdateMap);
	}

	private void setVisible(Map<String, Object> theUpdateMap, boolean isVisible) {
		if (this.application_visible != isVisible) {
			this.application_visible = isVisible;
			theUpdateMap.put("application_visible", isVisible);
		}
	}

	public void setCategory(long inCategId) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setCategory(theUpdateMap, inCategId);
		update(theUpdateMap);
	}

	public void setCategory(Map<String, Object> theUpdateMap, long inCategId) {
		if (this.application_category_id != inCategId) {
			this.application_category_id = inCategId;
			theUpdateMap.put("application_category_id", inCategId);
		}
	}
}
