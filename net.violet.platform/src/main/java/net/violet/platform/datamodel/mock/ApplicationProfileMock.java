package net.violet.platform.datamodel.mock;

import java.util.Date;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationProfile;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;

public class ApplicationProfileMock extends AbstractMockRecord<ApplicationProfile, ApplicationProfileMock> implements ApplicationProfile {

	//private static final Logger LOGGER = Logger.getLogger(ApplicationProfileMock.class);
	public static final MockBuilder<ApplicationProfile> BUILDER = new MockBuilder<ApplicationProfile>() {

		@Override
		protected ApplicationProfile build(String[] inParamValues) {
			return new ApplicationProfileMock(Long.parseLong(inParamValues[0]), inParamValues[1], inParamValues[2], Integer.parseInt(inParamValues[3]) == 1, inParamValues[4] == null ? null : CCalendar.parseTimestamp(inParamValues[4]), parseLong(inParamValues[5]), parseLong(inParamValues[6]), parseLong(inParamValues[7]), parseLong(inParamValues[8]), parseLong(inParamValues[9]), inParamValues[10], inParamValues[11]);
		}
	};

	private String application_title;
	private String application_description;
	private String application_instructions;
	private final String url;
	private boolean application_open_source;
	private final Date application_modification_date;
	private Long settingsFileId;
	private Long schedulingsFileId;
	private Long pictureFileId = null;
	private Long iconFileId = null;
	private Long announceFileId;

	public ApplicationProfileMock(long inId, String inTitle, String inDescription, boolean inIsOpenSource, Date inModificationDate, Long inSettingsFileId, Long inSchedulingsFileId, Long inPictureFileId, Long inIconFileId, Long inAnnounceFileId, String inInstruction, String inUrl) {
		super(inId);
		this.application_title = inTitle;
		this.application_description = inDescription;
		this.application_open_source = inIsOpenSource;
		this.application_modification_date = inModificationDate;
		this.settingsFileId = inSettingsFileId;
		this.schedulingsFileId = inSchedulingsFileId;
		this.pictureFileId = inPictureFileId;
		this.iconFileId = inIconFileId;
		this.announceFileId = inAnnounceFileId;
		this.application_instructions = inInstruction;
		this.url = inUrl;
	}

	public Application getApplication() {
		return null;
	}

	public String getDescription() {
		return this.application_description;
	}

	public String getUrl() {
		return this.url;
	}

	public String getTitle() {
		return this.application_title;
	}

	public boolean isOpenSource() {
		return this.application_open_source;
	}

	public Date getModificationDate() {
		return this.application_modification_date;
	}

	public Files getConfigurationSchedulingFile() {
		return Factories.FILES.find(this.settingsFileId);
	}

	public Files getConfigurationSettingFile() {
		return Factories.FILES.find(this.schedulingsFileId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.ApplicationProfile#update(java.lang.String,
	 * java.lang.String, boolean, net.violet.platform.datamodel.Files,
	 * net.violet.platform.datamodel.Files)
	 */
	public void update(String inTitleKey, String inDescrKey, String inInstrKey, boolean isOpenSource, Files inConfigFile, Files inSchedulingFile, Files inPictureFile, Files inIconFile, Files inAnnounceFile) {
		this.application_title = inTitleKey;
		this.application_description = inDescrKey;
		this.application_instructions = inInstrKey;
		this.application_open_source = isOpenSource;
		this.schedulingsFileId = inSchedulingFile.getId();
		this.settingsFileId = inConfigFile.getId();
		update(inPictureFile, inIconFile, inAnnounceFile);
	}

	public void update(Files inPictureFile, Files inIconFile, Files inAnnounceFile) {
		this.announceFileId = (inAnnounceFile != null) ? inAnnounceFile.getId() : null;
		this.pictureFileId = (inPictureFile != null) ? inPictureFile.getId() : null;
		this.iconFileId = (inIconFile != null) ? inIconFile.getId() : null;
	}

	public Files getIconFile() {
		if (this.iconFileId != null) {
			return Factories.FILES.find(this.iconFileId);
		}
		return null;
	}

	public Files getPictureFile() {
		if (this.pictureFileId != null) {
			return Factories.FILES.find(this.pictureFileId);
		}
		return null;
	}

	public Files getAnnounceFile() {
		return Factories.FILES.find(this.announceFileId);
	}

	public String getInstructions() {
		return this.application_instructions;
	}

	public Long getSchedulingsFileId() {
		return this.schedulingsFileId;
	}

	public Long getSettingsFileId() {
		return this.settingsFileId;
	}

}
