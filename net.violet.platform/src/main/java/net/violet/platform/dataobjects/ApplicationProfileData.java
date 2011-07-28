package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import net.violet.platform.datamodel.ApplicationProfile;

import org.apache.log4j.Logger;

public class ApplicationProfileData extends RecordData<ApplicationProfile> {

	private static final Logger LOGGER = Logger.getLogger(ApplicationProfileData.class);

	public static ApplicationProfileData getData(ApplicationProfile inProfile) {
		try {
			return RecordData.getData(inProfile, ApplicationProfileData.class, ApplicationProfile.class);
		} catch (final InstantiationException e) {
			ApplicationProfileData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ApplicationProfileData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			ApplicationProfileData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			ApplicationProfileData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Constructeur.
	 */
	protected ApplicationProfileData(ApplicationProfile inApplicationProfile) {
		super(inApplicationProfile);
	}

	public String getTitle() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return theApplicationProfile.getTitle();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getDescription() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return theApplicationProfile.getDescription();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Date getModificationDate() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return theApplicationProfile.getModificationDate();
		}

		return null;
	}

	public boolean isOpenSource() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return theApplicationProfile.isOpenSource();
		}

		return false;
	}

	public FilesData getSettingFile() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return FilesData.getData(theApplicationProfile.getConfigurationSettingFile());
		}
		return FilesData.getData(null);
	}

	public FilesData getSchedulingFile() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return FilesData.getData(theApplicationProfile.getConfigurationSchedulingFile());
		}
		return FilesData.getData(null);
	}

	public FilesData getPictureFile() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return FilesData.getData(theApplicationProfile.getPictureFile());
		}
		return FilesData.getData(null);
	}

	public FilesData getIconFile() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return FilesData.getData(theApplicationProfile.getIconFile());
		}
		return FilesData.getData(null);
	}

	public FilesData getFile() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return FilesData.getData(theApplicationProfile.getAnnounceFile());
		}
		return FilesData.getData(null);
	}

	public String getInstructions() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return theApplicationProfile.getInstructions();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getUrl() {
		final ApplicationProfile theApplicationProfile = getRecord();
		if (theApplicationProfile != null) {
			return theApplicationProfile.getUrl();
		}
		return null;
	}

	public void update(String inTitle, String inDescription, FilesData settings, FilesData schedulings, boolean isOpenSource, String inInstruction, FilesData inPictureFile, FilesData inIconFile, FilesData inAnnounceFile) {
		final ApplicationProfile theRecord = getRecord();

		if (theRecord != null) {
			theRecord.update(inTitle, inDescription, inInstruction, isOpenSource, settings.getRecord(), schedulings.getRecord(), inPictureFile.getRecord(), inIconFile.getRecord(), inAnnounceFile.getRecord());
		}
	}

}
