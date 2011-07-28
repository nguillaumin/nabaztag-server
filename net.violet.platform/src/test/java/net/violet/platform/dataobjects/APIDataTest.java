package net.violet.platform.dataobjects;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.APIData.ObjectClass;

import org.junit.Assert;
import org.junit.Test;

public class APIDataTest extends MockTestBase {

	private static final String API_KEY = "6992873d28d86925325dc52d15d6feec30bb2da5";

	@Test
	public void testFromObjectID() {
		Assert.assertEquals(61009, APIData.fromObjectID("ee51Od4a5fab1", ObjectClass.VOBJECT, APIDataTest.API_KEY));
	}

	@Test
	public void testToObjectID() {
		final VObjectData theObject = VObjectData.getData(new VObjectMock(61009, "F00004000001", "test42", null, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), null));
		final String theObjectID = theObject.getApiId(APIDataTest.API_KEY);
		Assert.assertEquals("ee51Od4a5fab1", theObjectID);
	}

	@Test
	public void fullTest() {
		final VObjectData theObject = VObjectData.getData(new VObjectMock(61009, "F00004000001", "test42", null, HARDWARE.V2, Factories.TIMEZONE.findByJavaId("Europe/Paris"), null));
		final String encryptedId = theObject.getApiId(APIDataTest.API_KEY);
		final VObjectData theRetrievedObject = VObjectData.findByAPIId(encryptedId, APIDataTest.API_KEY);
		Assert.assertEquals(theObject, theRetrievedObject);
	}
}
