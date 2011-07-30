package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Nabcast categories
 * 
 *
 */
public interface NabcastCateg extends Record<NabcastCateg> {

	long getNabcastcateg_id();

	String getNabcastcateg_val();

	long getNabcastcateg_lang();

	String getNabcastcateg_desc();

}
