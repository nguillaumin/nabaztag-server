package net.violet.platform.message.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;

/**
 * This class is mainly used as an adapter.
 */
public class SecurePart implements SequencePart {

	private final String url;

	public SecurePart(String inUrl) {
		this.url = inUrl;
	}

	public List<Sequence> getSequence(VObject inReceiver) {
		final List<Sequence> result = new ArrayList<Sequence>();
		result.add(new SequenceImpl(Sequence.SEQ_SECURESTREAMING, this.url));
		return result;
	}

	// FIXME not specified as Pojo
	public Map<String, Object> getPojo() {
		return new HashMap<String, Object>();
	}

}
