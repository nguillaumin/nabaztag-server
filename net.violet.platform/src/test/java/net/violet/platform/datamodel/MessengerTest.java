package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.SgbdConnection;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class MessengerTest extends DBTest {


	private static final Logger LOGGER = Logger.getLogger(MessengerTest.class);
	private static final String MESSENGER_NAME_INSERT = "private";
	private static final String MESSENGER_NAME_SELECT = "kowalsky";

	@Test
	public void insertMessenger() {
		final VObject myObject = Factories.VOBJECT.findByName("private");
		final User myUser = myObject.getOwner();
		final SgbdConnection myConnection = new SgbdConnection();

		try {
			final Messenger myMessenger = new MessengerImpl(myObject, MessengerTest.MESSENGER_NAME_INSERT);

			Assert.assertTrue(myMessenger.getId() != 0);

			Assert.assertEquals(MessengerTest.MESSENGER_NAME_INSERT, myConnection.doQueryString("SELECT name From messenger where object_id = " + myObject.getId()));
			Assert.assertEquals(myUser, myMessenger.getUser());
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(false);
		} catch (final SQLException e) {
			MessengerTest.LOGGER.fatal(e, e);
			Assert.assertTrue(false);
		} finally {
			myConnection.close();
		}
	}

	@Test
	public void findFromVObject() {
		final VObject myObject = Factories.VOBJECT.findByName(MessengerTest.MESSENGER_NAME_SELECT);
		final Messenger messenger = Factories.MESSENGER.getByObject(myObject);

		Assert.assertNotNull(messenger);
		Assert.assertEquals(MessengerTest.MESSENGER_NAME_SELECT, messenger.getName());
		Assert.assertEquals(myObject, messenger.getObject());
		Assert.assertEquals(myObject.getOwner(), messenger.getUser());
	}

	@Test
	public void updateFromVObject() {
		final SgbdConnection myConnection = new SgbdConnection();
		final VObject myObject = Factories.VOBJECT.findByName(MessengerTest.MESSENGER_NAME_SELECT);
		final Messenger messenger = Factories.MESSENGER.getByObject(myObject);

		Assert.assertNotNull(messenger);
		messenger.setName(MessengerTest.MESSENGER_NAME_SELECT + "2");
		Assert.assertEquals(MessengerTest.MESSENGER_NAME_SELECT + "2", myConnection.doQueryString("SELECT name From messenger where id = " + messenger.getId()));
	}

	@Test
	public void GetMessages() {
		final VObject myObject = Factories.VOBJECT.findByName(MessengerTest.MESSENGER_NAME_SELECT);
		final User myUser = myObject.getOwner();
		final Messenger messenger = Factories.MESSENGER.findByUser(myUser);

		Assert.assertNotNull(messenger);
		Assert.assertTrue(messenger.getMessageReceived().isEmpty());
		Assert.assertTrue(messenger.getMessageSent().isEmpty());
	}

}
