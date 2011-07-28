package net.violet.platform.message.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;

public class SignatureAnimWrapper implements SequencePart {

	private final int color;
	private final String url;

	public SignatureAnimWrapper(int inColor, String inUrl) {
		this.color = inColor;
		this.url = inUrl;
	}

	public List<Sequence> getSequence(VObject inReceiver) {
		final List<Sequence> result = new ArrayList<Sequence>();
		result.add(new SequenceImpl(Sequence.SEQ_COLOR, Integer.toString(this.color)));
		result.add(new SequenceImpl(Sequence.SEQ_CHORDOWNLOAD, this.url));
		return result;
	}

	// FIXME not specified as POJO
	public Map<String, Object> getPojo() {
		return new HashMap<String, Object>();
	}

}
