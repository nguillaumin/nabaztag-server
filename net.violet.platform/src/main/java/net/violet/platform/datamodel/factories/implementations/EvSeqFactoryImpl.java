package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.EvSeq;
import net.violet.platform.datamodel.EvSeqImpl;
import net.violet.platform.datamodel.factories.EvSeqFactory;
import net.violet.platform.message.Sequence;

public class EvSeqFactoryImpl extends RecordFactoryImpl<EvSeq, EvSeqImpl> implements EvSeqFactory {

	public EvSeqFactoryImpl() {
		super(EvSeqImpl.SPECIFICATION);
	}

	public void insert(long eventId, int order, int type, String val) throws SQLException {
		EvSeqImpl.insert(eventId, order, type, val);
	}

	public List<Sequence> findByEvent(long id) {

		final SortedMap<EvSeq, Sequence> theSortedMap = new TreeMap<EvSeq, Sequence>(new Comparator<EvSeq>() {

			public int compare(EvSeq o1, EvSeq o2) {
				final long delta = o1.getId() - o2.getId();
				final int theResult;
				if (delta < 0) {
					theResult = -1;
				} else if (delta > 0) {
					theResult = 1;
				} else {
					theResult = 0;
				}
				return theResult;
			}

		});

		for (final EvSeq anEvSeq : findAll("evseq_event=?", Arrays.asList(new Object[] { id }))) {
			theSortedMap.put(anEvSeq, anEvSeq);
		}
		return new LinkedList<Sequence>(theSortedMap.values());

	}

	public void deleteByEventID(long theEventId) {
		final List<Sequence> theList = findByEvent(theEventId);

		for (final Sequence aEvseq : theList) {
			final EvSeq theEvent = (EvSeq) aEvseq;
			theEvent.delete();
		}
	}

}
