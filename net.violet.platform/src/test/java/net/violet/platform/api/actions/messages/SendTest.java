package net.violet.platform.api.actions.messages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ApiActionTestBase;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.TtsVoiceMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.elements.Expression;
import net.violet.platform.message.elements.SequencePart;

import org.junit.Assert;
import org.junit.Test;

public class SendTest extends ApiActionTestBase {

	@Test
	public void sendTTSTest() throws APIException {

		final Lang frLang = getSiteFrLang();
		new TtsVoiceMock(1, "Heather English United States F HQ", "heather22k", frLang, "Heather", true, false);
		final Timezone tzParis = Factories.TIMEZONE.findByJavaId("Europe/Paris");
		final User theUser = new UserMock(90483, "TestUser", "123", "testuser@violet.invalid", frLang, "France", "Joe", "User", tzParis, Annu.MALE, "75011", "Paris", 0);
		final User theUser2 = new UserMock(90482, "TestUser2", "123", "testuser2@violet.invalid", frLang, "France", "Joe", "User", tzParis, Annu.MALE, "75011", "Paris", 0);

		final APICaller caller = getPublicApplicationAPICaller();

		final UserData theSender = UserData.getData(theUser);
		final VObject theReceiverObject = new VObjectMock(0, "123456789012", "Wifoo", theUser2, HARDWARE.V2, tzParis, frLang);
		final VObjectData theReceiver = VObjectData.getData(theReceiverObject);

		final Map<String, Object> theSequence = new HashMap<String, Object>();
		theSequence.put("modality", "net.violet.tts.en");
		theSequence.put("text", "Hello world !");
		theSequence.put("type", "expression");

		final Map<String, Object> alt = new HashMap<String, Object>();
		alt.put("modality", "net.violet.tts.fr");
		alt.put("text", "Bonjour les gens");
		theSequence.put("alt", Arrays.asList((Object) alt));

		final Map<String, Object> theMessage = new HashMap<String, Object>();
		theMessage.put("from", theSender.getApiId(caller));
		theMessage.put("to", Arrays.asList(new String[] { theReceiver.getApiId(caller) }));
		theMessage.put("sequence", Arrays.asList(new Object[] { theSequence }));

		final List<MessageDraft> theResult = MessageDraft.createFromPojo(theMessage, null, caller.getAPIKey());

		Assert.assertEquals(1, theResult.size());
		final MessageDraft message = theResult.get(0);

		Assert.assertEquals(theUser, message.getSender());
		Assert.assertEquals(theReceiverObject, message.getReceiver());
		final List<SequencePart> parts = message.getSequencePart();
		Assert.assertEquals(1, parts.size());

		final SequencePart part = parts.get(0);
		Assert.assertTrue(part instanceof Expression);
		final Expression expression = (Expression) part;
		Assert.assertEquals("net.violet.tts.en", expression.getModality());
		Assert.assertEquals(1, expression.getAlternatives().size());
		final Expression alternative = expression.getAlternatives().get(0);
		Assert.assertEquals("net.violet.tts.fr", alternative.getModality());
	}

	@Test
	public void sendMp3Test() throws APIException {
		final Timezone tzParis = Factories.TIMEZONE.findByJavaId("Europe/Paris");
		final Lang frLang = getSiteFrLang();
		final User theUser = new UserMock(90483, "TestUser", "123", "testuser@violet.invalid", frLang, "France", "Joe", "User", tzParis, Annu.MALE, "75011", "Paris", 0);
		final User theUser2 = new UserMock(90482, "TestUser2", "123", "testuser2@violet.invalid", frLang, "France", "Joe", "User", tzParis, Annu.MALE, "75011", "Paris", 0);

		final APICaller caller = getPublicApplicationAPICaller();
		final UserData theSender = UserData.getData(theUser);
		final VObject theReceiverObject = new VObjectMock(0, "123456789012", "Wifoo", theUser2, HARDWARE.V2, tzParis, frLang);
		final VObjectData theReceiver = VObjectData.getData(theReceiverObject);

		final Map<String, Object> theSequence = new HashMap<String, Object>();
		theSequence.put("modality", "net.violet.sound.mp3");
		theSequence.put("url", "http://composition.chez-alice.fr/rep/1.mp3");
		theSequence.put("type", "expression");

		final Map<String, Object> alt = new HashMap<String, Object>();
		alt.put("modality", "net.violet.tts.fr");
		alt.put("text", "Bonjour les gens");
		theSequence.put("alt", Arrays.asList((Object) alt));

		final Map<String, Object> theMessage = new HashMap<String, Object>();
		theMessage.put("from", theSender.getApiId(caller));
		theMessage.put("to", Arrays.asList(new String[] { theReceiver.getApiId(caller) }));
		theMessage.put("sequence", Arrays.asList(new Object[] { theSequence }));

		final List<MessageDraft> theResult = MessageDraft.createFromPojo(theMessage, null, caller.getAPIKey());

		Assert.assertEquals(1, theResult.size());
		final MessageDraft message = theResult.get(0);
		Assert.assertEquals(theUser, message.getSender());
		Assert.assertEquals(theReceiverObject, message.getReceiver());

		final List<SequencePart> parts = message.getSequencePart();
		Assert.assertEquals(1, parts.size());
		final SequencePart part = parts.get(0);
		Assert.assertTrue(part instanceof Expression);
		final Expression expression = (Expression) part;
		Assert.assertEquals("net.violet.sound.mp3", expression.getModality());
		Assert.assertEquals(1, expression.getAlternatives().size());
		final Expression alternative = expression.getAlternatives().get(0);
		Assert.assertEquals("net.violet.tts.fr", alternative.getModality());
	}

}
