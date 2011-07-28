package net.violet.platform.datamodel.factories;

import java.sql.SQLException;
import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.EvSeq;
import net.violet.platform.message.Sequence;

public interface EvSeqFactory extends RecordFactory<EvSeq> {

	public void insert(long eventId, int order, int type, String val) throws SQLException;

	public List<Sequence> findByEvent(long id);

	public void deleteByEventID(long theEventId);

}
