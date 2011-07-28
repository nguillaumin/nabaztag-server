package net.violet.platform.datamodel.factories.mock;

import java.util.List;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class UserFactoryMockTest extends AbstractTestBase {

	@Test
	public void searchUsersTest() {
		final Lang frLang = getFrLang();
		final CCalendar theCal = new CCalendar(true);
		final UserMock myUser = new UserMock(1, "user1", "pass", "email1", frLang, "FR", "firstName1", "lastName1", getParisTimezone(), "M", "Zip", "Paris", 1);
		theCal.set(1980, 05, 12);
		myUser.getAnnu().setDateBirth(new java.sql.Date(theCal.getTimeInMillis()));
		final VObject theObject = new VObjectMock(61009, "F00004000001", "test42", myUser, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), frLang);

		theObject.getPreferences().setPreferences(true, true, null);
		List<User> Users = Factories.USER.searchUsers("firstName1", "lastName1", 1, 99, AnnuData.Gender.MALE, "Paris", "FR", 0, 10);
		Assert.assertEquals(1, Users.size());
		Assert.assertEquals("firstName1", Users.get(0).getAnnu().getAnnu_prenom());
		Assert.assertEquals("email1", Users.get(0).getUser_email());

		theObject.getPreferences().setPreferences(false, true, null);
		Users = Factories.USER.searchUsers("firstName1", "lastName1", 1, 99, AnnuData.Gender.MALE, "Paris", "FR", 0, 10);
		Assert.assertEquals(1, Users.size());
	}
}
