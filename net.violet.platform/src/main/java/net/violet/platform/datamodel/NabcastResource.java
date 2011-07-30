package net.violet.platform.datamodel;

import java.sql.Timestamp;

import net.violet.db.records.Record;
import net.violet.platform.dataobjects.ObjectType;

/**
 * Nabcast new system, unused for now ?
 *
 */
public interface NabcastResource extends Record<NabcastResource> {

	ApplicationContent getContent();

	Timestamp getExpirationDate();

	Timestamp getReleaseDate();

	ObjectType getObjectReader();

	String getLabel();
}
