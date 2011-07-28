package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface Agenda extends Record<Agenda> {

	long getAgenda_id();

	String getAgenda_key();

}
