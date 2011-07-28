package net.violet.platform.message.elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.message.Sequence;
import net.violet.platform.util.StringShop;

import org.junit.Assert;
import org.junit.Test;

public class DirectiveTest extends MockTestBase {

	@Test
	public void createTest() {

		final Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("aSetting", "settingValue");

		final Map<String, Object> directive = new HashMap<String, Object>();
		directive.put("action", "stop-interactive");
		directive.put("settings", settings);

		final Directive theDirective = new Directive(directive, null, null);

		directive.put("type", "directive");

		Assert.assertEquals(directive, theDirective.getPojo());

		final List<Sequence> seqList = theDirective.getSequence(null);
		Assert.assertEquals(2, seqList.size());

		Sequence seq = seqList.get(0);
		Assert.assertEquals(Sequence.SEQ_END_INTERACTIVE_MODE, seq.getType());
		Assert.assertEquals(StringShop.EMPTY_STRING, seq.getData());

		seq = seqList.get(1);
		Assert.assertEquals(Sequence.SEQ_SETTING, seq.getType());
		Assert.assertEquals("aSetting=settingValue", seq.getData());
	}
}
