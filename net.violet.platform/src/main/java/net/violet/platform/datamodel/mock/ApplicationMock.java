package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationPackage;
import net.violet.platform.datamodel.ApplicationProfile;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.ApplicationTemp;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.CCalendar;

public class ApplicationMock extends AbstractMockRecord<Application, ApplicationMock> implements Application {

	public static final MockBuilder<Application> BUILDER = new MockBuilder<Application>() {

		@Override
		protected Application build(String[] inParamValues) {
			return new ApplicationMock(Long.parseLong(inParamValues[0]), inParamValues[2], Long.parseLong(inParamValues[1]), inParamValues[3] == null ? null : CCalendar.parseTimestamp(inParamValues[3]), ApplicationClass.getByName(inParamValues[4]), Long.parseLong(inParamValues[6]), (Integer.parseInt(inParamValues[5]) != 0), (Integer.parseInt(inParamValues[8]) != 0), (Integer.parseInt(inParamValues[9]) != 0));
		}
	};

	private String application_name;
	private final Timestamp application_creation_date;
	private String application_class;
	private boolean application_interactive;
	private boolean application_visible;
	private boolean isRemovable;

	private ApplicationProfile mProfile;
	private final ApplicationPackage mPackage;
	private long categId;
	private final long mUserId;
	private Files thePicture;

	private final Set<HARDWARE> mHardwares = new HashSet<HARDWARE>();
	private final List<ApplicationTag> mTags = new ArrayList<ApplicationTag>();
	private final List<Lang> mLangs = new ArrayList<Lang>();

	/**
	 * @param inId
	 * @param inName
	 * @param inUserId
	 * @param inDate
	 * @param inClass
	 */
	private ApplicationMock(long inId, String inName, long inUserId, Date inDate, ApplicationClass inClass, long categoryId, boolean inInteractive, boolean inVisible, boolean inIsRemovable) {
		super(inId);
		this.application_name = inName;
		this.mUserId = inUserId;
		this.mPackage = null;
		this.application_creation_date = new Timestamp(inDate.getTime());
		this.application_class = inClass.toString();
		this.application_interactive = inInteractive;
		this.application_visible = inVisible;
		this.categId = categoryId;
		this.isRemovable = inIsRemovable;
	}

	public ApplicationMock(long inId, String inName, User inUser, Date inDate, ApplicationCategory inCategory) {
		this(inId, inName, inUser.getId(), inDate, ApplicationClass.WEBUI, inCategory.getId(), false, true, true);
	}

	public ApplicationMock(long inId, String inName, User inUser, Date inDate, ApplicationCategory inCategory, ApplicationClass inClass) {
		this(inId, inName, inUser.getId(), inDate, inClass, inCategory.getId(), false, true, true);
	}

	public ApplicationMock(long inId, String inName, User inUser, Date inDate) {
		this(inId, inName, inUser.getId(), inDate, ApplicationClass.WEBUI, 1, false, true, true);
	}

	public ApplicationMock(long inId) {
		this(inId, net.violet.common.StringShop.EMPTY_STRING, 0, new Date(), ApplicationClass.WEBUI, 1, false, true, true);
	}

	public ApplicationMock(long inId, User inAuthor, String inName, ApplicationClass inClass, ApplicationCategory inCategory, boolean isInteractive, boolean isVisible, boolean isRemovable) {
		this(inId, inName, inAuthor.getId(), new Date(), inClass, inCategory.getId(), isInteractive, isVisible, isRemovable);
	}

	public void update(String inAppName, ApplicationClass inAppClass, ApplicationCategory inAppCategory, boolean isInteractive, boolean isVisible, boolean inIsRemovable) {
		this.application_name = inAppName;
		this.application_class = inAppClass.toString();
		this.application_interactive = isInteractive;
		this.application_visible = isVisible;
		this.categId = inAppCategory.getId();
		this.isRemovable = inIsRemovable;
	}

	public Timestamp getCreationDate() {
		return this.application_creation_date;
	}

	public String getName() {
		return this.application_name;
	}

	public User getOwner() {
		return Factories.USER.find(this.mUserId);
	}

	public String getDescription() {
		return getProfile().getDescription();
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

	public boolean isInteractive() {
		return this.application_interactive;
	}

	public boolean isVisible() {
		return this.application_visible;
	}

	public ApplicationProfile getProfile() {
		if (this.mProfile == null) {
			this.mProfile = Factories.APPLICATION_PROFILE.find(getId());
		}

		return this.mProfile;
	}

	public void setProfile(ApplicationProfile inApplicationProfile) {
		this.mProfile = inApplicationProfile;
	}

	public ApplicationPackage getPackage() {
		return this.mPackage;
	}

	public ApplicationClass getApplicationClass() {
		return ApplicationClass.getByName(this.application_class);
	}

	public ApplicationCategory getCategory() {
		return Factories.APPLICATION_CATEGORY.find(this.categId);
	}

	public ApplicationTemp getTemp() {
		return Factories.APPLICATION_TEMP.find(this.getId());
	}

	public List<HARDWARE> getSupportedHardwares() {
		return new ArrayList<HARDWARE>(this.mHardwares);
	}

	public boolean hasSupportedHardware(HARDWARE inHardware) {
		return this.mHardwares.contains(inHardware);
	}

	public void addHardware(HARDWARE inHardWare) {
		addHardwares(Collections.singletonList(inHardWare));
	}

	public void addHardwares(List<HARDWARE> inHardwares) {
		synchronized (this.mHardwares) {
			this.mHardwares.addAll(inHardwares);
		}
	}

	public void updateHardwares(List<HARDWARE> inHardwares) {
		synchronized (this.mHardwares) {
			this.mHardwares.clear();
			this.mHardwares.addAll(inHardwares);
		}
	}

	public void removeHardware(HARDWARE inHardware) {
		synchronized (this.mHardwares) {
			this.mHardwares.remove(inHardware);
		}
	}

	public List<ApplicationTag> getTags() {
		return this.mTags;
	}

	public boolean hasTag(ApplicationTag inTag) {
		return this.mTags.contains(inTag);
	}

	public void addTag(ApplicationTag inTag) {
		this.mTags.add(inTag);
	}

	public void removeTag(ApplicationTag inTag) {
		this.mTags.remove(inTag);
	}

	public void updateTags(List<ApplicationTag> inUpdatedTags) {
		final List<ApplicationTag> currentTags = getTags();

		// supprime ceux qui ne sont plus dans la nouvelle liste
		for (final ApplicationTag cur : currentTags) {
			if (!inUpdatedTags.contains(cur)) {
				currentTags.remove(cur);
			}
		}

		// ajoute les nouveaux tags
		for (final ApplicationTag newtag : inUpdatedTags) {
			addTag(newtag);
		}
	}

	public List<Lang> getLangs() {
		return this.mLangs;
	}

	public boolean hasLang(Lang inLang) {
		return this.mLangs.contains(inLang);
	}

	public void addLang(Lang inLang) {
		this.mLangs.add(inLang);
	}

	public void removeLang(Lang inLang) {
		this.mLangs.remove(inLang);
	}

	public void updateLangs(List<Lang> inUpdatedLangs) {

		final Iterator it = this.mLangs.iterator();
		while (it.hasNext()) {
			it.next();
			if (!inUpdatedLangs.contains(it)) {
				it.remove();
			}
		}

		// ajoute les nouvelles langues
		for (final Lang newLang : inUpdatedLangs) {
			addLang(newLang);
		}
	}

	public void updateRankInLang(Lang inRefLang, int inRank) {
		throw new UnsupportedOperationException();
	}

	public void updateRank(int inRank) {
		throw new UnsupportedOperationException();
	}

	public MessageSignature getMessageSignature() {
		return Application.ApplicationCommon.getMessageSignature(this);
	}

	public boolean isRemovable() {
		return this.isRemovable;
	}

	public void setVisible(boolean isVisible) {
		this.application_visible = isVisible;

	}

	public void setCategory(long inCategId) {
		this.categId = inCategId;
	}

	public void setThePicture(Files thePicture) {
		this.thePicture = thePicture;
	}

	public Files getThePicture() {
		return this.thePicture;
	}
}
