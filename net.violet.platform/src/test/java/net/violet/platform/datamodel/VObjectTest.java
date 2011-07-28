package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.db.records.SgbdConnection;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class VObjectTest extends DBTest {

	@Test
	public void testExistingRecords() {
		final VObject myRecord = Factories.VOBJECT.find(31);
		Assert.assertEquals(31, myRecord.getId().intValue());
		Assert.assertEquals("00039D402EAA", myRecord.getObject_serial());
		Assert.assertEquals("lapinou", myRecord.getObject_login());
		Assert.assertEquals(Factories.USER.find(195), myRecord.getOwner());
		Assert.assertEquals(1110907546, myRecord.getObject_creation());
		Assert.assertEquals(HARDWARE.V1, myRecord.getHardware());
		Assert.assertEquals(0, myRecord.getObject_test());
		Assert.assertEquals(0, myRecord.getObject_left());
		Assert.assertEquals(0, myRecord.getObject_right());
		Assert.assertEquals(0, myRecord.getObject_n1());
		Assert.assertEquals(0, myRecord.getObject_n2());
		Assert.assertEquals(0, myRecord.getObject_mode());
		Assert.assertEquals("Europe/Paris", myRecord.getTimeZone().getTimezone_javaId());
		Assert.assertEquals(1, myRecord.getPreferences().getLangPreferences().getId().intValue());
		Assert.assertEquals(120, myRecord.getObject_delay());
		Assert.assertEquals("fr/Les Landes", myRecord.getObject_loc());
		Assert.assertEquals(0, myRecord.getObject_state());
	}

	@Test
	public void testFindByName() {
		final VObject theObject31 = Factories.VOBJECT.findByName("lapinou");
		final VObject theObject31bis = Factories.VOBJECT.find(31);
		Assert.assertNotNull(theObject31);
		Assert.assertTrue(theObject31 == theObject31bis);
	}

	@Test
	public void testFindBySerial() {
		final VObject theObject31 = Factories.VOBJECT.findBySerial("00039D402EAA");
		final VObject theObject31bis = Factories.VOBJECT.find(31);
		Assert.assertNotNull(theObject31);
		Assert.assertTrue(theObject31 == theObject31bis);
	}

	@Test
	public void testFindLastCreatedByType() {
		final List<VObject> last = Factories.VOBJECT.findLastCreatedByHardwares(2, Collections.singleton(HARDWARE.V2));
		Assert.assertEquals(2, last.size());
		Assert.assertEquals("private", last.get(0).getObject_login());
		Assert.assertEquals("violet22", last.get(1).getObject_login());
	}

	@Test
	public void objectProfileTest() {
		final Timestamp beginningTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final VObject theObject = Factories.VOBJECT.findByName("private");
		final ObjectProfile theProfile = theObject.getProfile();
		Assert.assertEquals("private_description", theProfile.getDescription());
		theProfile.setDescription("newDescription");
		final ObjectProfile newProfile = theObject.getProfile();
		Assert.assertEquals("newDescription", newProfile.getDescription());
		Assert.assertTrue(beginningTimestamp.before(newProfile.getUpdateTime()));
	}

	@Test
	public void searchObjectsTest() {
		final Set<Hardware.HARDWARE> theHardwares = new HashSet<Hardware.HARDWARE>(Arrays.asList(HARDWARE.V1, HARDWARE.V2));
		final String theCountry = "FR";
		List<VObject> result = Factories.VOBJECT.searchObjects(null, theHardwares, null, theCountry, 0, 10);
		Assert.assertEquals(2, result.size());
		result = Factories.VOBJECT.searchObjects(null, null, "Paris", null, 0, 10);
		Assert.assertEquals(1, result.size());
		result = Factories.VOBJECT.searchObjects(null, null, "Issy les Moulineaux", null, 0, 10);
		Assert.assertEquals(2, result.size());
		result = Factories.VOBJECT.searchObjects(null, null, "Londres", null, 0, 10);
		Assert.assertEquals(1, result.size());
	}

	public void testFindRandomObject() {
		final VObject theFirstObject = Factories.VOBJECT.findRandomObject();
		Assert.assertNotNull(theFirstObject);
		while (true) {
			final VObject theOtherObject = Factories.VOBJECT.findRandomObject();
			if (theOtherObject != theFirstObject) {
				break;
			}
		}
	}

	public void testInsertV1() throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection();
		final User theUser = Factories.USER.find(195);

		final VObject theNewObject = new VObjectImpl("TESTINSERTV1", "TestInsertV1", theUser, HARDWARE.V1, 0, Factories.TIMEZONE.find(1L), Factories.LANG.find(1L), "Unknown/Unknown", 0, 0, null);

		final int nbObject = theConnection.doQueryIntV("select count(*) from object where object_login = '" + theNewObject.getObject_login() + "'");
		Assert.assertEquals(1, nbObject);

		final int theObjectId = theConnection.doQueryIntV("select object_id from object where object_login = '" + theNewObject.getObject_login() + "'");

		Assert.assertTrue(theObjectId != 0);
		theConnection.close();
	}

	public void testInsertV2() throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection();
		final User theUser = Factories.USER.find(195);

		// On force les auto incréments à être différents.
		final int maxIdObject = theConnection.doQueryIntV("select max(object_id) from object");
		theConnection.doQueryUpdate("ALTER TABLE object AUTO_INCREMENT = " + (maxIdObject + 1042));

		final VObject theNewObject = new VObjectImpl("TESTINSERTV2", "TestInsertV2", theUser, HARDWARE.V2, 0, Factories.TIMEZONE.find(1L), Factories.LANG.find(1L), "Unknown/Unknown", 0, 0, null);

		final int nbObject = theConnection.doQueryIntV("select count(*) from object where object_login = '" + theNewObject.getObject_login() + "'");
		Assert.assertEquals(1, nbObject);

		final int theObjectId = theConnection.doQueryIntV("select object_id from object where object_login = '" + theNewObject.getObject_login() + "'");

		Assert.assertTrue(theObjectId != 0);

		Assert.assertEquals(theNewObject.getPreferences().getLangPreferences().getIsoCode(), "fr-FR");
		theConnection.close();
	}

	public void testAssociationRecord() {
		final SgbdConnection theConnection = new SgbdConnection();
		final VObject myObject = Factories.VOBJECT.find(16951);
		final Map<Nabcast, Subscriber> map = myObject.getSubscribedNabcast();
		Assert.assertEquals(17, map.size());
		final Nabcast theFirstNabcast = map.keySet().iterator().next();
		final Subscriber theFirstSub = map.get(theFirstNabcast);
		map.remove(theFirstNabcast);
		Assert.assertEquals(16, map.size());
		int theIntVal = theConnection.doQueryIntV("select count(*) from subscriber where subscriber_user=16951");
		Assert.assertEquals(16, theIntVal);
		map.put(theFirstNabcast, theFirstSub);
		Assert.assertEquals(17, map.size());
		theIntVal = theConnection.doQueryIntV("select count(*) from subscriber where subscriber_user=16951");
		Assert.assertEquals(17, theIntVal);
		theConnection.close();
	}

	public void testupdateDecoratedAssociation() {
		final SgbdConnection theConnection = new SgbdConnection();
		final VObject myObject = Factories.VOBJECT.find(16951);
		final Nabcast inNabcast = NabcastImpl.find(107);
		final Map<Nabcast, Subscriber> map = myObject.getSubscribedNabcast();
		final Subscriber theSubscriber = map.get(inNabcast);

		int theIntVal;

		Assert.assertNotNull(theSubscriber);
		Assert.assertEquals(theSubscriber.getSubscriber_heure(), -1);
		Assert.assertEquals(theSubscriber.getSubscriber_min(), -1);
		theIntVal = theConnection.doQueryIntV("select subscriber_heure from subscriber where subscriber_user=16951 and subscriber_nabcast=107");
		Assert.assertEquals(-1, theIntVal);
		theSubscriber.setTime(10, 15);
		theIntVal = theConnection.doQueryIntV("select subscriber_heure from subscriber where subscriber_user=16951 and subscriber_nabcast=107");
		Assert.assertEquals(10, theIntVal);

		final VObject myObject2 = Factories.VOBJECT.find(16951);
		final Map<Nabcast, Subscriber> newMap = myObject2.getSubscribedNabcast();
		final Subscriber newInSubscriber = newMap.get(inNabcast);

		Assert.assertNotNull(newInSubscriber);
		Assert.assertEquals(10, newInSubscriber.getSubscriber_heure());
		Assert.assertEquals(15, newInSubscriber.getSubscriber_min());
		theConnection.close();
	}

	@Test
	public void testCreateObjectv2AndAssociateWithUser() throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection();
		final User theUser = Factories.USER.find(778);
		final VObjectImpl theObjectImpl = new VObjectImpl("aaaaaaaaaaab", "nabname", theUser, HARDWARE.V2, (System.currentTimeMillis()) / 1000, theUser.getTimezone(), theUser.getAnnu().getLangPreferences(), net.violet.common.StringShop.EMPTY_STRING, 0, 0, null);

		theUser.setHasObject();// associe l'object à l'utilisateur

		String theStringVal = theConnection.doQueryString("select object_login from object where object_owner=" + theUser.getId());
		Assert.assertEquals("nabname", theStringVal);

		theStringVal = theConnection.doQueryString("select object_login from object where object_owner=" + theUser.getId());
		Assert.assertEquals("nabname", theStringVal);

		final int theObjectId = theConnection.doQueryIntV("select object_id from object where object_owner=" + theUser.getId());
		Assert.assertTrue(theObjectId != 0);
		final int theUserMain = theConnection.doQueryIntV("select user_main from user where user_id=" + theUser.getId());
		Assert.assertEquals(theUserMain, 1);

		Assert.assertEquals(123123, theObjectImpl.getProfile().getPicture().getId().intValue());

		theConnection.close();
	}

	@Test
	public void testCreateObjectWithPicture() throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection();
		final User theUser = Factories.USER.find(778);
		final Files theFiles = Factories.FILES.find(1850108);
		final VObjectImpl theObjectImpl = new VObjectImpl("aaaaaaaaaaac", "nabnamec", theUser, HARDWARE.V2, (System.currentTimeMillis()) / 1000, theUser.getTimezone(), theUser.getAnnu().getLangPreferences(), net.violet.common.StringShop.EMPTY_STRING, 0, 0, theFiles);

		Assert.assertEquals(1850108, theConnection.doQueryIntV("select object_profile_picture from object_profile where object_id=" + theObjectImpl.getId()));

		theConnection.close();
	}
}
