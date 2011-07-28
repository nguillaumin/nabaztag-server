package net.violet.platform.dataobjects;

import junit.framework.Assert;
import net.violet.common.StringShop;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserFriendsAddress;
import net.violet.platform.datamodel.UserPrefs;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.AnnuMock;
import net.violet.platform.datamodel.mock.ContactMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.FriendsListsMock;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.MusicStyleMock;
import net.violet.platform.datamodel.mock.UserFriendsAddressMock;
import net.violet.platform.datamodel.mock.UserPrefsMock;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.CCalendar;

import org.junit.Test;

public class UserDataTest extends MockTestBase {

	@Test
	public void deleteObjectTest() {
		final User theUser = getPrivateUser();
		final User kowalskyUser = getKowalskyUser();
		final VObject kowalsky = getKowalskyObject();

//		User
		final Annu theAnnu = new AnnuMock(theUser, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 0, null, StringShop.EMPTY_STRING, null);
		Factories.BLACK.createNewBlack(theUser, kowalskyUser);
		final Music theMusic = new MusicMock(1, StringShop.EMPTY_STRING, new FilesMock("GetForSignatureTest/1.mpeg", MimeType.MIME_TYPES.A_MPEG), theUser, new MusicStyleMock(MusicStyle.CATEGORIE_MP3_PERSO, "cat. MP3_PERSO").getId().intValue(), 0, 0, 0);

		final Contact theContact = new ContactMock(0, theUser, kowalskyUser, Contact.STATUS.ACCEPTED);
		final FriendsLists theFriendsLists = new FriendsListsMock(theUser.getId(), 0, 0, 0);

		final UserPrefs theUserPrefs = new UserPrefsMock(theUser.getId());
		final UserFriendsAddress theUserFriendAddress = new UserFriendsAddressMock(theUser, StringShop.EMPTY_STRING);

//		Object & Messenger
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

//		Extermination
		final UserData theUserData = UserData.getData(theUser);
		theUserData.delete();

		Assert.assertEquals(null, Factories.ANNU.find(theAnnu.getId()));

		Assert.assertEquals(0, Factories.BLACK.findAllMapped().size());
		Assert.assertEquals(null, Factories.MUSIC.find(theMusic.getId()));

		Assert.assertEquals(null, Factories.CONTACT.find(theContact.getId()));
		Assert.assertEquals(null, Factories.FRIENDS_LISTS.find(theFriendsLists.getId()));

		Assert.assertEquals(null, Factories.USER_PREFS.find(theUserPrefs.getId()));
		Assert.assertEquals(null, Factories.USER_FRIENDS_ADDRESS.find(theUserFriendAddress.getId()));

		Assert.assertEquals(null, Factories.MESSENGER.findByObject(getPrivateObject()));
		Assert.assertEquals(0, Factories.MESSAGE_RECEIVED.findMessageReceivedByRecipientOrSender(theMessenger).size());
		Assert.assertEquals(0, Factories.MESSAGE_SENT.findMessageSentBySenderOrRecipient(theMessenger).size());

		Assert.assertEquals(null, Factories.MESSAGE.find(firstMessage.getId()));
		Assert.assertEquals(null, Factories.MESSAGE.find(secondMessage.getId()));
		Assert.assertEquals(null, Factories.MESSAGE.find(thirdMessage.getId()));
		Assert.assertEquals(null, Factories.MESSAGE.find(fourthMessage.getId()));

		Assert.assertEquals(null, Factories.VOBJECT.find(getPrivateObject().getId()));
		Assert.assertEquals(null, Factories.USER.find(theUser.getId()));
	}
}
