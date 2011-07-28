package net.violet.platform.datamodel;

import java.util.Iterator;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class MessageSentTest extends DBTest {

	@Test
	public void sendAMessage() {
		final Message message1 = Factories.MESSAGE.find(1);
		final Message message2 = Factories.MESSAGE.find(2);
		final Messenger sender1 = Factories.MESSENGER.getByObject(Factories.VOBJECT.findByName("private"));
		final Messenger sender2 = Factories.MESSENGER.getByObject(Factories.VOBJECT.findByName("kowalsky"));

		Assert.assertEquals(1, sender1.getMessageSent().size());
		Iterator<Message> theIterator = sender1.getMessageSent().keySet().iterator();
		Assert.assertEquals(message2, theIterator.next());

		Assert.assertEquals(1, sender2.getMessageSent().size());
		theIterator = sender2.getMessageSent().keySet().iterator();
		Assert.assertEquals(message1, theIterator.next());

	}
}
