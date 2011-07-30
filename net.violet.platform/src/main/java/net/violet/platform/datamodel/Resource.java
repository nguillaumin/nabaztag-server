package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Unused ?
 */
public interface Resource extends Record<Resource> {

	String getPath();

	ApplicationContent getContent();

}
