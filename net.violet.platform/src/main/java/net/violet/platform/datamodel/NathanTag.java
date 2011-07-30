package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Tags associated with a nathan audio book
 * 
 *
 */
public interface NathanTag extends Record<NathanTag> {

	/**
	 * The number of categories, can help.
	 */
	long NB_CATEG = 4;

	/**
	 * @return a Long object containing the NathanTag id.
	 */
	Long getId();

	/**
	 * @return a Long object containing the category id.
	 */
	Long getCateg();

	/**
	 * @return the NathanTag label.
	 */
	String getLabel();

}
