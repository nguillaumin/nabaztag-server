package net.violet.platform.message.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.message.Sequence;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionTest extends MockTestBase {

	@Test
	public void creationTest() {

		final Map<String, Object> expression = new HashMap<String, Object>();
		expression.put("modality", "net.violet.sound.mp3");
		expression.put("url", "http://composition.chez-alice.fr/rep/1.mp3");

		final Map<String, Object> alt1 = new HashMap<String, Object>();
		alt1.put("modality", "net.violet.tts.fr");
		alt1.put("text", "Bonjour les gens");
		alt1.put("quality", 1.0);

		final Map<String, Object> alt2 = new HashMap<String, Object>();
		alt2.put("modality", "net.violet.tts.en");
		alt2.put("text", "Hello guys");
		alt2.put("quality", 0.8);

		final List<Map<String, Object>> alternativesList = new ArrayList<Map<String, Object>>();
		alternativesList.add(alt1);
		alternativesList.add(alt2);
		expression.put("alt", alternativesList);

		final Expression theExpression = new Expression(expression);

		final List<Sequence> seqList = theExpression.getSequence(getKowalskyObject());
		Assert.assertEquals(1, seqList.size());
		final Sequence seq = seqList.get(0);

		Assert.assertEquals(Sequence.SEQ_MUSIC_STREAMING, seq.getType());
		Assert.assertEquals("http://composition.chez-alice.fr/rep/1.mp3", seq.getData());
	}
}
