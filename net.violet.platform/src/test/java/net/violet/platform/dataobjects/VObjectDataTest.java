package net.violet.platform.dataobjects;

import java.util.List;

import junit.framework.Assert;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.junit.Test;

public class VObjectDataTest extends MockTestBase {

	@Test
	public void checkNameTest() {
		final String tooShort = StringShop.EMPTY_STRING;
		final String tooLong = "azerttyyuuiopmljkjhfssqsfgfdbvcnbvcnsfghsghsfghsdfgsdfgdgddgzrhzthsdfgsdfertezryrturyiytjdfghsdfqdqfgzerdfbsxcvbsdfsdrh";

		Assert.assertFalse(VObjectData.isNameValid(tooLong));
		Assert.assertFalse(VObjectData.isNameValid(tooShort));
		Assert.assertFalse(VObjectData.isNameValid("roger.")); // ends with a dot
		Assert.assertFalse(VObjectData.isNameValid("pompéi")); // contains invalid extended chars
		Assert.assertFalse(VObjectData.isNameValid("john..doe")); // contains 2 successive dots

		Assert.assertTrue(VObjectData.isNameValid("marilyn.monroe"));
		Assert.assertTrue(VObjectData.isNameValid("45DarthVader45"));
		Assert.assertTrue(VObjectData.isNameValid("45DarthVader"));
		Assert.assertTrue(VObjectData.isNameValid("DarthVader-45"));
	}

	@Test
	public void findByOwnerTest() {
		final UserData theUser = UserData.getData(getKowalskyUser());
		VObjectData.createObject(ObjectType.NABAZTAG_V1, "serialV1", "lapin1", theUser, "Paris");
		VObjectData.createObject(ObjectType.NABAZTAG_V2, "serialV2", "lapin2", theUser, "Paris");
		VObjectData.createObject(ObjectType.MIRROR, "serialMirror", "mirror", theUser, "Paris");

		final List<VObjectData> theObjects = VObjectData.findByOwner(theUser);
		Assert.assertEquals(3, theObjects.size());
	}

	@Test
	public void deleteObjectTest() {
		final User theUser = getPrivateUser();
		final VObject kowalsky = getKowalskyObject();
		final Messenger theMessenger = new MessengerMock(0, null, kowalsky, kowalsky.getObject_login());
		final Messenger theSecondMessenger = new MessengerMock(0, theUser, null, theUser.getUser_email());
		final Messenger privateMessenger = new MessengerMock(0, null, getPrivateObject(), theUser.getUser_email());

		final Message firstMessage = Factories.MESSAGE.create(null, "text1", new CCalendar(false), Palette.FLASH);
		final Message secondMessage = Factories.MESSAGE.create(null, "text2", new CCalendar(false), Palette.ORIENTAL);
		final Message thirdMessage = Factories.MESSAGE.create(null, "text3", new CCalendar(false), Palette.PASTEL);
		final Message fourthMessage = Factories.MESSAGE.create(null, "text4", new CCalendar(false), Palette.PASTEL);

		theMessenger.getMessageReceived().put(firstMessage, Factories.MESSAGE_RECEIVED.create(firstMessage, theMessenger, theSecondMessenger));
		theSecondMessenger.getMessageSent().put(firstMessage, Factories.MESSAGE_SENT.create(firstMessage, theMessenger, theSecondMessenger));

		theMessenger.getMessageReceived().put(secondMessage, Factories.MESSAGE_RECEIVED.create(secondMessage, theMessenger, theSecondMessenger));

		theMessenger.getMessageSent().put(thirdMessage, Factories.MESSAGE_SENT.create(thirdMessage, privateMessenger, theMessenger));
		privateMessenger.getMessageReceived().put(thirdMessage, Factories.MESSAGE_RECEIVED.create(thirdMessage, privateMessenger, theMessenger));

		theMessenger.getMessageSent().put(fourthMessage, Factories.MESSAGE_SENT.create(fourthMessage, privateMessenger, theMessenger));

		final VObjectData theObject = VObjectData.getData(kowalsky);
		theObject.delete();

		Assert.assertEquals(null, Factories.MESSENGER.findByObject(kowalsky));
		Assert.assertEquals(0, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipientOrSender(theMessenger).size());
		Assert.assertEquals(0, Factories.MESSAGE_SENT.findMessageSentBySenderOrRecipient(theMessenger).size());

		Assert.assertEquals(null, Factories.MESSAGE.find(firstMessage.getId()));
		Assert.assertEquals(null, Factories.MESSAGE.find(secondMessage.getId()));
		Assert.assertEquals(null, Factories.MESSAGE.find(thirdMessage.getId()));
		Assert.assertEquals(null, Factories.MESSAGE.find(fourthMessage.getId()));
	}
}
