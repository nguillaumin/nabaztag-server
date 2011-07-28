package net.violet.platform.message.elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;

import org.apache.log4j.Logger;

/**
 * An Annotation is a classic map.
 */
public class Annotation implements SequencePart {

	private static final Logger LOGGER = Logger.getLogger(Annotation.class);

	private final Map<String, Object> annotationMap;

	public Annotation(Map<String, Object> pojoMap) {
		this.annotationMap = new HashMap<String, Object>(pojoMap);
	}

	public List<Sequence> getSequence(VObject inReceiver) {
		final List<Sequence> result = new ArrayList<Sequence>();
		try {
			result.add(new SequenceImpl(Sequence.SEQ_SETTING, "cookie=" + URLEncoder.encode(ConverterFactory.JSON.convertTo(this.annotationMap), "UTF-8")));
		} catch (final Exception e) {
			Annotation.LOGGER.fatal(e, e);
		}
		return result;
	}

	public Map<String, Object> getPojo() {
		final Map<String, Object> result = new HashMap<String, Object>(this.annotationMap);
		result.put("type", "annotation");
		return result;
	}

}
