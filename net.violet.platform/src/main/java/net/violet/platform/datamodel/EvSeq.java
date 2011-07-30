package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.message.Sequence;

/**
 * Parameters for signal messages of Nabaztag v1
 * 
 *
 */
public interface EvSeq extends Record<EvSeq>, Sequence {

	String getData();

	int getType();
}
