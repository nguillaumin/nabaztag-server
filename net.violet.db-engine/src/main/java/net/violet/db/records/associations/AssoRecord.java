package net.violet.db.records.associations;

import net.violet.db.records.Record;

public interface AssoRecord<R extends Record<R>, A extends AssoRecord<R, A>> extends Record<A> {
	// This space for rent.

}
