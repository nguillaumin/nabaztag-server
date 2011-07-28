package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationTemp;
import net.violet.platform.datamodel.factories.ApplicationTempFactory;
import net.violet.platform.datamodel.mock.ApplicationTempMock;

public class ApplicationTempFactoryMock extends RecordFactoryMock<ApplicationTemp, ApplicationTempMock> implements ApplicationTempFactory {

	ApplicationTempFactoryMock() {
		super(ApplicationTempMock.class);
	}

	public ApplicationTemp create(Application theApplication, String inTypeName, String inShortcut, String inLink) {
		final String thePicture = "../include_img/services/visuels_services/" + inTypeName + ".gif";
		final String theIcon = "../include_img/services/visuels_services/icon_" + inTypeName + ".gif";
		final ApplicationTemp theTemp = new ApplicationTempMock(theApplication, inLink, thePicture, theIcon, inShortcut);
		return theTemp;
	}

}
