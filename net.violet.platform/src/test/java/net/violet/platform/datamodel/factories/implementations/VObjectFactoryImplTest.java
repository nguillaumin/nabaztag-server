package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class VObjectFactoryImplTest extends DBTest {

	@Test
	public void searchObjectsTest() {
		final Set<Hardware.HARDWARE> theHardwares = new HashSet<Hardware.HARDWARE>(Arrays.asList(Hardware.HARDWARE.V1, Hardware.HARDWARE.V2));

		final List<VObject> theObjects = Factories.VOBJECT.searchObjects("ObjFactImpl", theHardwares, "Varsovie", "PL", 0, 10);
		Assert.assertNotNull(theObjects);
		Assert.assertEquals(2, theObjects.size());
		Assert.assertEquals("ObjFactImpl", theObjects.get(0).getObject_login());

		final List<VObject> theObjects2 = Factories.VOBJECT.searchObjects(null, null, "Varsovie", "PL", 0, 10);
		Assert.assertEquals(3, theObjects2.size());
	}
}
