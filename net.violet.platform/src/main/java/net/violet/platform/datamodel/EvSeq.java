package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.message.Sequence;

public interface EvSeq extends Record<EvSeq>, Sequence {

	String getData();

	int getType();
}
