package net.violet.platform.message.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;

public class StartInteractiveWrapper implements SequencePart {

	private final String url;

	public StartInteractiveWrapper(String inUrl) {
		this.url = inUrl;
	}

	public List<Sequence> getSequence(VObject inReceiver) {
		final List<Sequence> result = new ArrayList<Sequence>();
		result.add(new SequenceImpl(Sequence.SEQ_BEGIN_INTERACTIVE_MODE, this.url));
		return result;
	}

	public Map<String, Object> getPojo() {
		final Map<String, Object> result = new HashMap<String, Object>();
		result.put("type", "directive");
		result.put("action", "start-interactive");
		return result;
	}

}
