package net.violet.platform.dataobjects;

import java.util.Date;

import net.violet.platform.datamodel.ApplicationPackage;
import net.violet.platform.util.StringShop;

public class ApplicationPackageData extends RecordData<ApplicationPackage> {

	public ApplicationPackageData(ApplicationPackage inPackage) {
		super(inPackage);
	}

	public Date getModificationDate() {
		final ApplicationPackage record = getRecord();
		return (record == null) ? null : record.getModificationDate();
	}

	public String getApiVersion() {
		final ApplicationPackage record = getRecord();
		return (record == null) ? StringShop.EMPTY_STRING : record.getApiVersion();
	}

	public String getSourceType() {
		final ApplicationPackage record = getRecord();
		return (record == null) ? StringShop.EMPTY_STRING : record.getLanguage().name();
	}

	public String getSource() {
		final ApplicationPackage record = getRecord();
		return (record == null) ? StringShop.EMPTY_STRING : record.getSource();
	}

	public ApplicationPackage getReference() {
		return getRecord();
	}

}
