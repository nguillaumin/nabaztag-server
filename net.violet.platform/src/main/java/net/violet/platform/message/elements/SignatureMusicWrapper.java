package net.violet.platform.message.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;

public class SignatureMusicWrapper implements SequencePart {

	private final String url;

	public SignatureMusicWrapper(String inUrl) {
		this.url = inUrl;
	}

	public List<Sequence> getSequence(VObject inReceiver) {
		final List<Sequence> result = new ArrayList<Sequence>();
		result.add(new SequenceImpl(Sequence.SEQ_MUSICSIGN, this.url));
		return result;
	}

	public Map<String, Object> getPojo() {
		final Map<String, Object> aPojo = new HashMap<String, Object>();
		aPojo.put("modality", "net.violet.sound.mp3");
		aPojo.put("url", this.url);
		aPojo.put("type", "expression");
		return aPojo;
	}

}
