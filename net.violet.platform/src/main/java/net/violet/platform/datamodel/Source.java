package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Sources for crawlers
 * 
 *
 */
public interface Source extends Record<Source> {

	long getSource_id();

	String getSource_path();

	long getSource_val();

	void setVal(int val);

	long getSource_time();

	long getSource_srv();

	String getSource_dico();

}
