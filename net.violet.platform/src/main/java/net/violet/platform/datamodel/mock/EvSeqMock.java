package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.EvSeq;

public class EvSeqMock extends AbstractMockRecord<EvSeq, EvSeqMock> implements EvSeq {

	protected EvSeqMock(long inId) {
		super(inId);
	}

	public String getData() {
		throw new UnsupportedOperationException();
	}

	public int getType() {
		throw new UnsupportedOperationException();
	}
}
