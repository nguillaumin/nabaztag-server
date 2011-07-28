package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface Presences extends Record<Presences> {

	String getResource();

	String getUsername();

}
