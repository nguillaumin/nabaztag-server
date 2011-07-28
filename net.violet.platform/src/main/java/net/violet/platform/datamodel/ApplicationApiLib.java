package net.violet.platform.datamodel;

import java.util.Date;

import net.violet.db.records.Record;
import net.violet.platform.applets.AppLanguages;

/**
 * The JavaScript or Ruby API used by hosted applications to run
 */
public interface ApplicationApiLib extends Record<ApplicationApiLib> {

	/**
	 * the development language used by this API library
	 */
	AppLanguages getLanguage();

	/**
	 * the internal API version (language+version=unique key)
	 */
	String getApiVersion();

	/**
	 * last code update
	 */
	Date getReleaseDate();

	/**
	 * the source code
	 */
	String getCode();

	/**
	 * the byte code
	 */
	byte[] getByteCode();

	Files getSourceFile();

	void setSourceFile(Files inSrcFile, Date inReleaseDate);

}
