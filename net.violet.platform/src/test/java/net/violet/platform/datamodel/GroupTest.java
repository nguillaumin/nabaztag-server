package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class GroupTest extends DBTest {

	@Test
	public void findByMembersTest() {
		final VObject[] objects = new VObject[6];
		for (int i = 1; i <= 5; i++) {
			objects[i] = Factories.VOBJECT.find(i);
		}

		final Group[] groups = new Group[4];
		for (int i = 1; i <= 3; i++) {
			groups[i] = Factories.GROUP.find(i);
		}

		for (int i = 1; i <= 5; i++) {
			final List<Group> theGroups = Factories.GROUP.findAllByMember(objects[i]);
			if (i == 1) {
				Assert.assertEquals(3, theGroups.size());
				Assert.assertTrue(theGroups.contains(groups[1]) && theGroups.contains(groups[2]) && theGroups.contains(groups[3]));
			} else if (i == 2) {
				Assert.assertTrue(theGroups.isEmpty());
			} else if (i == 3) {
				Assert.assertEquals(1, theGroups.size());
				Assert.assertTrue(theGroups.contains(groups[2]));

			} else if (i == 4) {
				Assert.assertEquals(2, theGroups.size());
				Assert.assertTrue(theGroups.contains(groups[1]) && theGroups.contains(groups[2]));

			} else if (i == 5) {
				Assert.assertEquals(2, theGroups.size());
				Assert.assertTrue(theGroups.contains(groups[1]) && theGroups.contains(groups[3]));
			}
		}
	}
}
