package net.violet.platform.datamodel;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class BlackTest extends DBTest {

	@Test
	public void createBlackTest() {
		final User mainUser = Factories.USER.find(1);
		final User blackUser = Factories.USER.find(2);

		Factories.BLACK.createNewBlack(mainUser, blackUser);

		Assert.assertTrue(mainUser.getBlackList().containsKey(blackUser));
	}

	@Test
	public void removeBlackTest() {
		final User mainUser = Factories.USER.find(1);
		final User blackUser = Factories.USER.find(2);
		final User blackUser2 = Factories.USER.find(84887);

		Factories.BLACK.createNewBlack(mainUser, blackUser);
		Factories.BLACK.createNewBlack(mainUser, blackUser2);

		Assert.assertEquals(2, mainUser.getBlackList().size());

		Factories.BLACK.removeFromBlackList(mainUser, blackUser2);

		Assert.assertEquals(1, mainUser.getBlackList().size());
		Assert.assertTrue(mainUser.getBlackList().containsKey(blackUser));
		Assert.assertFalse(mainUser.getBlackList().containsKey(blackUser2));
	}

}
