package net.violet.platform.datamodel.factories.mock;

import java.util.List;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class VObjectFactoryMockTest extends AbstractTestBase {

	@Test
	public void searchUsersTest() {
		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		final CCalendar theCal = new CCalendar(true);
		final UserMock myUser = new UserMock(1, "userski", "passmot", "email1@emails.eu", frLang, "FR", "firstName1", "lastName1", getParisTimezone(), "H", "Zip", "Paris", 1);
		theCal.set(1980, 05, 12);
		myUser.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));
		final VObject theObject = new VObjectMock(61009, "F00004000001", "ObjFacMoTe", myUser, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang);

		theObject.getPreferences().setPreferences(true, true, null);
		final List<VObject> theObjects = Factories.VOBJECT.searchObjects(null, null, null, "FR", 0, 10);
		Assert.assertNotNull(theObjects);
		Assert.assertEquals(1, theObjects.size());
		Assert.assertEquals(theObject, theObjects.get(0));

		theObject.getPreferences().setPreferences(false, true, null);
		final List<VObject> theObjects1 = Factories.VOBJECT.searchObjects(null, null, null, "FR", 0, 10);
		Assert.assertNotNull(theObjects1);
		Assert.assertTrue(theObjects1.isEmpty());
	}
}
