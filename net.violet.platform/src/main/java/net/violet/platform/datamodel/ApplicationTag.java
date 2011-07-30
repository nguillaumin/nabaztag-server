package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Allows to classify applications by tags
 *
 *
 */
public interface ApplicationTag extends Record<ApplicationTag> {

	/**
	 * The name of the tag
	 * 
	 * @return
	 */
	String getName();

	/**
	 * The lang of the tag
	 * 
	 * @return
	 */
	Lang getLang();

	/**
	 * The tag percentage, meaning the percentage of applications linked to this
	 * tag. E.g. if the value is 0 there is no application using this tag, if
	 * it's 100 all the applications are using this tag, if it's 50 half the
	 * applications are using this tag, clear enough ?
	 * 
	 * @return an integer between 0 and 100.
	 */
	int getSize();

	/**
	 * Sets the new tag size.
	 * 
	 * @param inSize
	 */
	void setSize(int inSize);
}
