package net.violet.platform.datamodel;

import java.util.Date;

import net.violet.db.records.Record;
import net.violet.platform.applets.AppLanguages;

/**
 * An application package contains links to the source files, resources.. and
 * give informations about the script language used
 */
public interface ApplicationPackage extends Record<ApplicationPackage> {

	AppLanguages getLanguage();

	String getApiVersion();

	Files getSourceFile();

	String getSource();

	Date getModificationDate();

	ApplicationApiLib getApiLib();

	/**
	 * @param newSourceFile
	 */
	void updateSourceFile(Files inNewSourceFile, String inApiVersion);

}
