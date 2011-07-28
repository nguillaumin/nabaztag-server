package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationProfile;
import net.violet.platform.datamodel.ApplicationProfileImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.ApplicationProfileFactory;

import org.apache.log4j.Logger;

public class ApplicationProfileFactoryImpl extends RecordFactoryImpl<ApplicationProfile, ApplicationProfileImpl> implements ApplicationProfileFactory {

	private static Logger LOGGER = Logger.getLogger(ApplicationProfileFactoryImpl.class);

	public ApplicationProfileFactoryImpl() {
		super(ApplicationProfileImpl.SPECIFICATION);
	}

	public boolean usesFiles(Files inFile) {
		final String condition = " configuration_setting_file_id = ? OR configuration_scheduling_file_id = ? OR picture_file_id = ? OR icon_file_id = ? OR application_announce = ? ";
		return count(null, condition, Arrays.asList(new Object[] { inFile.getId(), inFile.getId(), inFile.getId(), inFile.getId(), inFile.getId() }), null) > 0;
	}

	public ApplicationProfile create(Application inApplication, String title, String description, boolean isOpenSource, Files settingFile, Files schedulingFile, Files inPicture, Files inIcon, Files inAnnounce, String inInstructions, String inUrl) {
		try {
			return new ApplicationProfileImpl(inApplication, title, description, isOpenSource, settingFile, schedulingFile, inPicture, inIcon, inAnnounce, inInstructions, inUrl);
		} catch (final SQLException e) {
			ApplicationProfileFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

}
