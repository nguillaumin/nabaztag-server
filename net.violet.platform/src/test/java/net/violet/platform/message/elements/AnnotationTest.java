package net.violet.platform.message.elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.message.Sequence;

import org.junit.Assert;
import org.junit.Test;

public class AnnotationTest extends MockTestBase {

	@Test
	public void annotationTest() throws UnsupportedEncodingException {

		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("number", 42);
		pojo.put("url", "http://www.google.com");
		final String encoded = "cookie=" + URLEncoder.encode(ConverterFactory.JSON.convertTo(pojo), "UTF-8");
		final Annotation theAnnotation = new Annotation(pojo);

		pojo.put("type", "annotation");

		final Map<String, Object> retrievedPojo = theAnnotation.getPojo();
		Assert.assertEquals(pojo, retrievedPojo);

		final List<Sequence> sequenceList = theAnnotation.getSequence(null);
		Assert.assertEquals(1, sequenceList.size());
		final Sequence seq = sequenceList.get(0);
		Assert.assertEquals(Sequence.SEQ_SETTING, seq.getType());
		Assert.assertEquals(encoded, seq.getData());
	}
}
