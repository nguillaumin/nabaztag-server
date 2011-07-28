package net.violet.platform.datamodel.factories.implementations;

import java.util.List;

import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.AnnuData;

import org.junit.Assert;
import org.junit.Test;

public class UserFactoryImplTest extends DBTest {

	@Test
	public void searchUsersTest() {
		final VObject myVObject = Factories.VOBJECT.find(16952);
		Assert.assertNotNull(myVObject);
		final User myUser = Factories.USER.find(103);
		Assert.assertNotNull(myUser);
		Assert.assertTrue(myVObject.getPreferences().isVisible());
		final List<User> Users = Factories.USER.searchUsers("Janek", "Kowalski", 0, 99, AnnuData.Gender.MALE, "Varsovie", "PL", 0, 10);
		Assert.assertEquals(1, Users.size());
		final Long id1 = Users.get(0).getId();
		Assert.assertTrue(id1.equals(103L));
		Assert.assertEquals("Janek", Users.get(0).getAnnu().getAnnu_prenom());

		final VObject myVObject2 = Factories.VOBJECT.find(16953);
		Assert.assertNotNull(myVObject2);
		final User myUser2 = Factories.USER.find(103);
		Assert.assertNotNull(myUser2);
		Assert.assertTrue(!(myVObject2.getPreferences().isVisible()));
		final List<User> Users2 = Factories.USER.searchUsers("Izabella", "Czartoryska", 0, 99, AnnuData.Gender.FEMALE, "Paris", "FR", 0, 10);
		Assert.assertEquals(1, Users2.size());
	}
}
