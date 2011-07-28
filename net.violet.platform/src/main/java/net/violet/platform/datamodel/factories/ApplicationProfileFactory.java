package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationProfile;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

/**
 * Defines the DB or memory access methods to the aplpication_profile table
 */
public interface ApplicationProfileFactory extends RecordFactory<ApplicationProfile>, FilesAccessor {

	ApplicationProfile create(Application inApplication, String title, String description, boolean isOpenSource, Files settingFile, Files schedulingFile, Files picture, Files icon, Files announce, String instructions, String inUrl);

}
