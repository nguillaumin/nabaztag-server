package net.violet.platform.datamodel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.db.records.SgbdConnection;
import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class UserTest extends DBTest {

	@Test
	public void testExistingRecords() {
		final User myUser1 = Factories.USER.find(1);
		Assert.assertTrue(myUser1.checkPasswordPlain("ouessant1900"));
		Assert.assertEquals("mpuyfaucher@violet.net", myUser1.getUser_email());
		Assert.assertEquals(Factories.ANIM.find(9), myUser1.getColor());
		Assert.assertEquals(Factories.MUSIC.find(14532), myUser1.getMusic());
		Assert.assertEquals(0, myUser1.getUser_authmsg());
		Assert.assertEquals(false, myUser1.getNotifyMessagePlayed());
		Assert.assertEquals(1, myUser1.getUser_image());
		Assert.assertEquals(false, myUser1.getNotifyMessageReceived());
		Assert.assertEquals(113827693421946L, myUser1.getUser_extconnect());
		Assert.assertEquals(new Date(1212593040000L), new Date(myUser1.getCreationDate() * 1000));
		Assert.assertEquals(1, myUser1.getUser_good());
		Assert.assertEquals(null, myUser1.getUser_comment());
		Assert.assertEquals("0000FF", myUser1.getColorSign());
		Assert.assertEquals(new Integer(0), myUser1.getUser_newsletter());
		Assert.assertEquals(Factories.TIMEZONE.find(30), myUser1.getTimezone());
		Assert.assertEquals(1, myUser1.getUser_24h());
		Assert.assertEquals(net.violet.common.StringShop.EMPTY_STRING, myUser1.getUser_firstName());
		Assert.assertEquals(net.violet.common.StringShop.EMPTY_STRING, myUser1.getUser_lastName());

		final List<Lang> theLangs = myUser1.getLangs();
		Assert.assertEquals(theLangs.size(), 2);
		Assert.assertTrue(theLangs.contains(Factories.LANG.find(1)));
		Assert.assertTrue(theLangs.contains(Factories.LANG.find(2)));
	}

	@Test
	public void testExistingAndDeleteRecords() {
		final SgbdConnection theConnection = new SgbdConnection();
		final User myUser2 = Factories.USER.find(26112);
		Assert.assertEquals("delete@gmail.com", myUser2.getUser_email());
		int theIntVal;
		theIntVal = theConnection.doQueryIntV("select count(*) from user where user_id = 26112");
		Assert.assertEquals(1, theIntVal);
		myUser2.delete();
		Assert.assertTrue(myUser2.isDeleted());
		theIntVal = theConnection.doQueryIntV("select count(*) from user where user_id = 26112");
		Assert.assertEquals(0, theIntVal);
		theConnection.close();
	}

	@Test
	public void testLangsAssociation() {
		final SgbdConnection theConnection = new SgbdConnection();
		final User myUser1 = Factories.USER.find(1);
		final List<Lang> theLangs = myUser1.getLangs();
		theLangs.remove(Factories.LANG.find(1));
		int theIntVal;
		theIntVal = theConnection.doQueryIntV("select count(*) from userLang where user_id = 1");
		Assert.assertEquals(1, theIntVal);
		theLangs.add(Factories.LANG.find(3));
		theIntVal = theConnection.doQueryIntV("select count(*) from userLang where user_id = 1");
		Assert.assertEquals(2, theIntVal);
		theLangs.add(Factories.LANG.find(1));
		theIntVal = theConnection.doQueryIntV("select count(*) from userLang where user_id = 1");
		Assert.assertEquals(3, theIntVal);
		theLangs.remove(Factories.LANG.find(3));
		theIntVal = theConnection.doQueryIntV("select count(*) from userLang where user_id = 1");
		Assert.assertEquals(2, theIntVal);
		theConnection.close();
	}

	@Test
	public void testCache() {
		final User myUser1 = Factories.USER.find(1);
		final User myUser1bis = Factories.USER.find(1);
		Assert.assertNotNull(myUser1);
		Assert.assertTrue(myUser1 == myUser1bis);
	}

	@Test
	public void testFindByPseudo() {
		final User myUser1 = Factories.USER.find(1L);//findByPseudo("violet22");
		final User myUser1bis = Factories.USER.find(1);
		Assert.assertNotNull(myUser1);
		Assert.assertTrue(myUser1 == myUser1bis);
	}

	@Test
	public void testFriends() {
		final User myUser = Factories.USER.find(1);
		final List<User> friends = myUser.getFriends();
		if (friends != null) {
			System.out.println("Nombre d'amis : " + friends.size());
			Assert.assertEquals(friends.size(), 1);
		} else {
			System.out.println("friends est null");
		}
	}

	@Test
	public void testBlackList() {
		final User myUser = Factories.USER.find(1);
		final Map<User, Black> friends = myUser.getBlackList();
		System.out.println("Nombre de blacklistés : " + friends.size());
		Assert.assertTrue(friends.size() > 0);
	}

	@Test
	public void testGetFriendsWithObjects() {
		final User iAmWhoIam = Factories.USER.find(1);
		final UserImpl myFriend = (UserImpl) Factories.USER.find(84887);
		myFriend.user_main = 0;
		Factories.CONTACT.createContact(iAmWhoIam, myFriend, Contact.STATUS.ACCEPTED);

		Assert.assertEquals(1, iAmWhoIam.getFriendsWithObject().size());
	}

	@Test
	public void searchUsersTest() {
		List<User> res = Factories.USER.searchUsers(null, null, 25, 29, null, null, null, 0, 10);
		Assert.assertEquals(2, res.size());
		Assert.assertEquals("mpuyfaucher@violet.net", res.get(0).getUser_email());
		Assert.assertEquals("sylvain.huet@ambermind.com", res.get(1).getUser_email());

		res = Factories.USER.searchUsers(null, null, 23, null, null, null, "UK", 0, 10);
		Assert.assertEquals(1, res.size());
		Assert.assertEquals("sylvain.huet@ambermind.com", res.get(0).getUser_email());

		res = Factories.USER.searchUsers(null, null, null, 25, null, null, null, 0, 10);
		Assert.assertEquals(1, res.size());
		Assert.assertEquals("benjamincornic@gmail.com", res.get(0).getUser_email());

		res = Factories.USER.searchUsers(null, null, 30, null, null, null, null, 0, 10);
		Assert.assertEquals(1, res.size());
		Assert.assertEquals("lp@violet.net", res.get(0).getUser_email());

	}

	@Test
	public void getAnnuUserTest() {
		final User theUser = Factories.USER.find(1);
		final Annu theAnnuUser = theUser.getAnnu();
		Assert.assertNotNull(theAnnuUser);
	}

	@Test
	public void testSetPictureFile() {
		final User theUser = Factories.USER.find(1);
		final Annu theAnnuUser = theUser.getAnnu();
		Assert.assertNotNull(theAnnuUser);
		final Files theFile1 = Factories.FILES.find(5609430);
		theAnnuUser.setPictureFile(theFile1);
		final SgbdConnection theConnection = new SgbdConnection();
		int theIntVal;
		theIntVal = theConnection.doQueryIntV("select picture_file_id from annu where annu_user = 1");
		Assert.assertEquals(5609430, theIntVal);
		Assert.assertEquals(theFile1, theAnnuUser.getPictureFile());
		final Files theFile2 = Factories.FILES.find(5609431);
		theAnnuUser.setPictureFile(theFile2);
		theIntVal = theConnection.doQueryIntV("select picture_file_id from annu where annu_user = 1");
		Assert.assertEquals(5609431, theIntVal);
		Assert.assertEquals(theFile2, theAnnuUser.getPictureFile());
		theConnection.close();
	}

	@Test
	public void createUser() {
		final Timezone inTimeZone = Factories.TIMEZONE.findByJavaId("Europe/Paris");
		final Lang inLang = Factories.LANG.findByIsoCode("fr-FR");
		final User theUser = Factories.USER.createNewUser("aeffacer@email.ae", "aa", inLang, net.violet.common.StringShop.EMPTY_STRING, "inFirstName", "inLastName", inTimeZone.getId());

		Assert.assertEquals(theUser.getAnnu().getLangPreferences().getIsoCode(), "fr");
		theUser.getAnnu().delete();
		theUser.delete();
	}
}
