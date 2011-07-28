package net.violet.db.records.factories;

import java.util.Map;

import net.violet.db.records.Record;

public interface RecordFactory<T extends Record<T>> {

	T find(long id);

	Map<Long, T> findAllMapped();

}
