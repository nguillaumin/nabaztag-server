package net.violet.platform.datamodel.factories.mock;

import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.EvSeq;
import net.violet.platform.datamodel.factories.EvSeqFactory;
import net.violet.platform.datamodel.mock.EvSeqMock;
import net.violet.platform.message.Sequence;

public class EvSeqFactoryMock extends RecordFactoryMock<EvSeq, EvSeqMock> implements EvSeqFactory {

	public EvSeqFactoryMock() {
		super(EvSeqMock.class);
	}

	public void deleteByEventID(long inEventId) {
		throw new UnsupportedOperationException();
	}

	public List<Sequence> findByEvent(long id) {
		throw new UnsupportedOperationException();
	}

	public void insert(long eventId, int order, int type, String val) {
		throw new UnsupportedOperationException();
	}
}
