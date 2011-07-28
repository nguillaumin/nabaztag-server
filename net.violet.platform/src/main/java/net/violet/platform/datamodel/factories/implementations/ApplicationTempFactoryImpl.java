package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationTemp;
import net.violet.platform.datamodel.ApplicationTempImpl;
import net.violet.platform.datamodel.factories.ApplicationTempFactory;

public class ApplicationTempFactoryImpl extends RecordFactoryImpl<ApplicationTemp, ApplicationTempImpl> implements ApplicationTempFactory {

	public ApplicationTempFactoryImpl() {
		super(ApplicationTempImpl.SPECIFICATION);
	}

	public List<ApplicationTemp> findAll() {
		return findAll("1=1", Arrays.asList(new Object[] {}));
	}

	public ApplicationTemp create(Application application, String inTypeName, String inShortcut, String inLink) throws SQLException {
		final String theLink = inLink;
		final String thePicture = "../include_img/services/visuels_services/" + inTypeName + ".gif";
		final String theIcon = "../include_img/services/visuels_services/icon_" + inTypeName + ".gif";
		return new ApplicationTempImpl(application, theLink, inShortcut, thePicture, theIcon);
	}
}
