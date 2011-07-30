package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.NoSuchElementException;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SQLSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.util.UpdateMap;

import org.apache.log4j.Logger;

public class ApplicationProfileImpl extends ObjectRecord<ApplicationProfile, ApplicationProfileImpl> implements ApplicationProfile {

	static final Logger LOGGER = Logger.getLogger(ApplicationProfile.class);

	/**
	 * Specification.
	 */
	public static final SQLObjectSpecification<ApplicationProfileImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationProfileImpl>("application_profile", ApplicationProfileImpl.class, new SQLKey[] { new SQLKey("application_id") }, "application_modification_date");

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "application_id", "application_title", "application_description", "application_instructions", "application_open_source", "configuration_setting_file_id", "configuration_scheduling_file_id", "picture_file_id", "icon_file_id", "application_announce", "url" };

	protected long application_id;
	protected String application_title;
	protected String application_description;
	protected boolean application_open_source;
	protected Timestamp application_modification_date;
	protected Long configuration_setting_file_id;
	protected Long configuration_scheduling_file_id;
	protected Long application_announce;
	protected Long picture_file_id;
	protected Long icon_file_id;
	protected String application_instructions;
	protected String url;

	private final SingleAssociationNotNull<ApplicationProfile, Application, ApplicationImpl> mApplication;

	private final SingleAssociationNull<ApplicationProfile, Files, FilesImpl> mConfigurationSettingFile;

	private final SingleAssociationNull<ApplicationProfile, Files, FilesImpl> mConfigurationSchedulingFile;

	private final SingleAssociationNull<ApplicationProfile, Files, FilesImpl> mPictureFile;

	private final SingleAssociationNull<ApplicationProfile, Files, FilesImpl> mIconFile;

	private final SingleAssociationNull<ApplicationProfile, Files, FilesImpl> mAnnounceFile;

	/**
	 * IMPORTANT : This empty constructor is required for the to
	 * Object-Relational Mapping API to work
	 * 
	 * @see AbstractSQLRecord#createObject(SQLSpecification, java.sql.ResultSet)
	 */
	protected ApplicationProfileImpl() {
		this.mApplication = new SingleAssociationNotNull<ApplicationProfile, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.mConfigurationSettingFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "configuration_setting_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mConfigurationSchedulingFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "configuration_scheduling_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mPictureFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "picture_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mIconFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "icon_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mAnnounceFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "application_announce", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * @param appId
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	protected ApplicationProfileImpl(long appId) throws NoSuchElementException, SQLException {
		init(appId);
		this.mApplication = new SingleAssociationNotNull<ApplicationProfile, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.mConfigurationSettingFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "configuration_setting_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mConfigurationSchedulingFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "configuration_scheduling_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mPictureFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "picture_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mIconFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "icon_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mAnnounceFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "application_announce", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());

	}

	/**
	 * @param inApplication
	 * @param inTitle
	 * @param inDescription
	 * @param isOpenSource
	 * @param inConfigSettingFile
	 * @param inConfigSchedulingFile
	 * @throws SQLException
	 */
	public ApplicationProfileImpl(Application inApplication, String inTitle, String inDescription, boolean isOpenSource, Files inConfigSettingFile, Files inConfigSchedulingFile, Files inPicture, Files inIcon, Files inAnnounce, String inInstructions, String inUrl) throws SQLException {
		this.application_id = inApplication.getId();
		this.application_title = inTitle;
		this.application_description = inDescription;
		this.application_instructions = inInstructions;
		this.application_open_source = isOpenSource;
		this.configuration_setting_file_id = (inConfigSettingFile == null) ? null : inConfigSettingFile.getId();
		this.configuration_scheduling_file_id = (inConfigSchedulingFile == null) ? null : inConfigSchedulingFile.getId();
		this.picture_file_id = (inPicture == null) ? null : inPicture.getId();
		this.icon_file_id = (inIcon == null) ? null : inIcon.getId();
		this.application_announce = (inAnnounce == null) ? null : inAnnounce.getId();
		this.url = inUrl;

		init(ApplicationProfileImpl.NEW_COLUMNS);
		this.mApplication = new SingleAssociationNotNull<ApplicationProfile, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);

		this.mConfigurationSettingFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "configuration_setting_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mConfigurationSchedulingFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "configuration_scheduling_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mPictureFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "picture_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mIconFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "icon_file_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
		this.mAnnounceFile = new SingleAssociationNull<ApplicationProfile, Files, FilesImpl>(this, "application_announce", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * @see net.violet.platform.datamodel.ObjectRecord#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ApplicationProfileImpl> getSpecification() {
		return ApplicationProfileImpl.SPECIFICATION;
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationProfile#getDescription()
	 */
	public String getDescription() {
		return this.application_description;
	}

	public String getInstructions() {
		return this.application_instructions;
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationProfile#getModificationDate()
	 */
	public Date getModificationDate() {
		return this.application_modification_date;
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationProfile#getTitle()
	 */
	public String getTitle() {
		return this.application_title;
	}

	public String getUrl() {
		return this.url;
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationProfile#isOpenSource()
	 */
	public boolean isOpenSource() {
		return this.application_open_source;
	}

	public Application getApplication() {
		return this.mApplication.get(this.application_id);
	}

	public Files getConfigurationSettingFile() {
		return this.mConfigurationSettingFile.get(this.configuration_setting_file_id);
	}

	public Files getConfigurationSchedulingFile() {
		return this.mConfigurationSchedulingFile.get(this.configuration_scheduling_file_id);
	}

	public Files getPictureFile() {
		return this.mPictureFile.get(this.picture_file_id);
	}

	public Files getIconFile() {
		return this.mIconFile.get(this.icon_file_id);
	}

	public Files getAnnounceFile() {
		return this.mAnnounceFile.get(this.application_announce);
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationProfile#update(java.lang.String,
	 *      java.lang.String, boolean, net.violet.platform.datamodel.Files,
	 *      net.violet.platform.datamodel.Files)
	 */
	public void update(String inTitleKey, String inDescrKey, String inInstrKey, boolean isOpenSource, Files inConfigSettingFile, Files inSchedulingFile, Files inPictureFile, Files inIconFile, Files inAnnounceFile) {

		final UpdateMap updateMap = new UpdateMap();
		this.application_title = updateMap.updateField("application_title", this.application_title, inTitleKey);
		this.application_description = updateMap.updateField("application_description", this.application_description, inDescrKey);
		this.application_instructions = updateMap.updateField("application_instructions", this.application_instructions, inInstrKey);
		this.application_open_source = updateMap.updateField("application_open_source", this.application_open_source, isOpenSource);
		update(updateMap);
		this.mConfigurationSchedulingFile.set(inSchedulingFile);
		this.mConfigurationSettingFile.set(inConfigSettingFile);
		update(inPictureFile, inIconFile, inAnnounceFile);
	}

	public void update(Files inPictureFile, Files inIconFile, Files inAnnounceFile) {
		this.mIconFile.set(inIconFile);
		this.mPictureFile.set(inPictureFile);
		this.mAnnounceFile.set(inAnnounceFile);
	}

}
