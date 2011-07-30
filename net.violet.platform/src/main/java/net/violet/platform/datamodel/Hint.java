package net.violet.platform.datamodel;

import java.sql.Timestamp;

import net.violet.db.records.Record;

/**
 * Hints for the web site
 * 
 *
 */
public interface Hint extends Record<Hint> {

	/**
	 * Get title of the hint
	 */
	String getTitle();

	/**
	 * Get content of the hint (this can contain HTML tags)
	 */
	String getContent();

	/**
	 * Get URL to the picture associated with the hint
	 */
	String getPicture();

	/**
	 * Get width of the picture associated with the hint
	 */
	Integer getPictureWidth();

	/**
	 * Get height of the picture associated with the hint
	 */
	Integer getPictureHeight();

	/**
	 * Get link
	 */
	String getLink();

	/**
	 * Get creation date of the hint
	 */
	Timestamp getCreationDate();

	/**
	 * Get modification date of the hint
	 */
	Timestamp getModificationDate();

}
