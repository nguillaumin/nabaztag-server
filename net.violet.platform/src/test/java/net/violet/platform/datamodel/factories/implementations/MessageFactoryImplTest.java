package net.violet.platform.datamodel.factories.implementations;

import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageImpl;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData.StatusMessage;

import org.junit.Assert;
import org.junit.Test;

public class MessageFactoryImplTest extends DBTest {

	@Test
	public void walkInMessageReceived() {

		final User theUserReceived = Factories.USER.findByEmail("usermessagetest1@violet.net");
		final VObject theObject = Factories.VOBJECT.find(63644);
		Assert.assertNotNull(theObject);
		Assert.assertEquals(3, Factories.MESSAGE.walkInMessageReceivedOrArchivedByStatus(StatusMessage.INBOX, theUserReceived, null, 0, 10, new MessageImpl.JoinRecordsWalker<Message, MessageReceived>() {

			public void process(Message inMessage, MessageReceived inMessageReceived) {
			}
		}));
		Assert.assertEquals(3, Factories.MESSAGE.walkInMessageReceivedOrArchivedByStatus(StatusMessage.INBOX, theUserReceived, theObject, 0, 10, new MessageImpl.JoinRecordsWalker<Message, MessageReceived>() {

			public void process(Message inMessage, MessageReceived inMessageReceived) {
			}
		}));
	}

	@Test
	public void walkInMessageArchived() {

		final User theUserReceived = Factories.USER.findByEmail("usermessagetest1@violet.net");
		final VObject theObject = Factories.VOBJECT.find(63644);
		Assert.assertNotNull(theObject);
		Assert.assertEquals(2, Factories.MESSAGE.walkInMessageReceivedOrArchivedByStatus(StatusMessage.ARCHIVED, theUserReceived, null, 0, 10, new MessageImpl.JoinRecordsWalker<Message, MessageReceived>() {

			public void process(Message inMessage, MessageReceived inMessageReceived) {
			}
		}));
		Assert.assertEquals(2, Factories.MESSAGE.walkInMessageReceivedOrArchivedByStatus(StatusMessage.ARCHIVED, theUserReceived, theObject, 0, 10, new MessageImpl.JoinRecordsWalker<Message, MessageReceived>() {

			public void process(Message inMessage, MessageReceived inMessageReceived) {
			}
		}));
	}

	@Test
	public void walkInMessageSent() {

		final User theUserReceived = Factories.USER.findByEmail("usermessagetest1@violet.net");

		Assert.assertEquals(2, Factories.MESSAGE.walkInMessageSent(theUserReceived, 0, 10, new MessageImpl.JoinRecordsWalker<Message, MessageSent>() {

			public void process(Message inMessage, MessageSent inMessageSent) {
			}
		}));

	}
}
