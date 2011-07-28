package net.violet.platform.dataobjects;

import java.sql.SQLException;

import net.violet.platform.datamodel.ApplicationTemp;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class ApplicationTempData extends RecordData<ApplicationTemp> {

	private static final Logger LOGGER = Logger.getLogger(ApplicationTempData.class);

	/**
	 * Constructeur.
	 */
	public ApplicationTempData(ApplicationTemp inApplicationProfile) {
		super(inApplicationProfile);
	}

	public String getLink() {
		final ApplicationTemp theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getLink();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getShortcut() {
		final ApplicationTemp theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getShortcut();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getImage() {
		final ApplicationTemp theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getImage();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getIcone() {
		final ApplicationTemp theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getIcone();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static ApplicationTempData createTemp(ApplicationData application, String inTypeName, String inShortcut, String inLink) {
		try {
			return new ApplicationTempData(Factories.APPLICATION_TEMP.create(application.getRecord(), inTypeName, inShortcut, inLink));
		} catch (final SQLException e) {
			ApplicationTempData.LOGGER.fatal(e, e);
			return null;
		}
	}

	public void update(String inShortcut, String inLink) {
		setShortcut(inShortcut);
		setLink(inLink);

	}

	public void setShortcut(String inShortcut) {
		final ApplicationTemp theApplicationTemp = getRecord();
		if (theApplicationTemp != null) {
			theApplicationTemp.setShorcut(inShortcut);
		}
	}

	public void setType(String inShortcut) {
		final ApplicationTemp theApplicationTemp = getRecord();
		if (theApplicationTemp != null) {
			theApplicationTemp.setShorcut(inShortcut);
		}
	}

	private void setLink(String inLink) {
		final ApplicationTemp theApplicationTemp = getRecord();
		if (theApplicationTemp != null) {
			theApplicationTemp.setLink(inLink);
		}
	}

	public void update(String inShortcut) {
		setShortcut(inShortcut);
	}

}
