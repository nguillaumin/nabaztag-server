package net.violet.platform.datamodel.factories.mock;

import java.util.Date;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationProfile;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.ApplicationProfileFactory;
import net.violet.platform.datamodel.mock.ApplicationProfileMock;

public class ApplicationProfileFactoryMock extends RecordFactoryMock<ApplicationProfile, ApplicationProfileMock> implements ApplicationProfileFactory {

	@Override
	public void loadCache() {
		ApplicationProfileMock.BUILDER.generateValuesFromInitFile(12, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/applicationProfileInit");
	}

	public ApplicationProfileFactoryMock() {
		super(ApplicationProfileMock.class);
	}

	public boolean usesFiles(Files inFile) {
		for (final ApplicationProfile aProfile : findAllMapped().values()) {
			if (aProfile.getConfigurationSchedulingFile().equals(inFile) || aProfile.getConfigurationSettingFile().equals(inFile)) {
				return true;
			}
		}
		return false;
	}

	public ApplicationProfile create(Application inApplication, String title, String description, boolean isOpenSource, Files settingFile, Files schedulingFile, Files picture, Files icon, Files announce, String instructions, String inUrl) {
		return new ApplicationProfileMock(inApplication.getId(), title, description, isOpenSource, new Date(), settingFile.getId(), schedulingFile.getId(), (picture != null) ? picture.getId() : null, (icon != null) ? icon.getId() : null, announce != null ? announce.getId() : null, instructions, inUrl);
	}

}
