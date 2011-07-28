package net.violet.platform.xmpp.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.message.elements.Expression;
import net.violet.platform.message.elements.SequencePart;

import org.junit.Assert;
import org.junit.Test;

public class ContentNegociatorTest extends MockTestBase {

	@Test
	public void testNegociate() {

		final Timezone tzParis = Factories.TIMEZONE.findByJavaId("Europe/Paris");
		final Lang frLang = getFrLang();
		final User theUser = new UserMock(90481, "TestUser", "123", "testuser@violet.invalid", frLang, "France", "Joe", "User", tzParis, Annu.MALE, "75011", "Paris", 0);

		final VObject theObject = new VObjectMock(42, "123456789012", "jack", theUser, HARDWARE.V2, tzParis, frLang);

		final Map<String, Object> expression = new HashMap<String, Object>();
		expression.put("type", SequencePart.EXPRESSION);
		expression.put("modality", "net.violet.video");
		expression.put("url", "videoUrl");

		final List<Object> alts = new ArrayList<Object>();
		final Map<String, Object> alt1 = new HashMap<String, Object>();
		alt1.put("modality", "net.violet.tts");
		alt1.put("text", "Hello Worl");
		alts.add(alt1);

		expression.put("alt", alts);

		final Expression theExpression = new Expression(expression);

		final Expression negociatedResult = ContentNegociator.negociate(theObject, theExpression);

		Assert.assertEquals("net.violet.tts", negociatedResult.getModality());

	}

}
