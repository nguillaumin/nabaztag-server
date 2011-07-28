package net.violet.platform.message.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;

public class StreamWrapper implements SequencePart {

	private final String streamId;
	private final boolean start;

	public StreamWrapper(String inStreamId, boolean inStart) {
		this.streamId = inStreamId;
		this.start = inStart;
	}

	public List<Sequence> getSequence(VObject inReceiver) {
		final List<Sequence> result = new ArrayList<Sequence>();
		if (this.start) {
			result.add(new SequenceImpl(Sequence.SEQ_STREAMING_ID, this.streamId));
		} else {
			result.add(new SequenceImpl(Sequence.SEQ_STREAMING_STOP, this.streamId));
		}

		return result;
	}

	// FIXME not specified as POJO
	public Map<String, Object> getPojo() {
		return new HashMap<String, Object>();
	}

}
