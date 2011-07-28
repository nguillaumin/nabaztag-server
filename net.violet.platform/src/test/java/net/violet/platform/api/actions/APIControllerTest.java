package net.violet.platform.api.actions;

import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;

import org.junit.Assert;
import org.junit.Test;

public class APIControllerTest {

	@Test
	public void testGetName() throws APIException {
		Assert.assertEquals(APIConstants.GET_PACKAGE_ACTION, APIController.getAction(APIConstants.GET_PACKAGE_ACTION).getName());
	}

	private final String bad_action = "violet.applications123.getPackage";

	@Test(expected = InvalidParameterException.class)
	public void testInvalidAction() throws APIException {
		Assert.assertEquals(this.bad_action, APIController.getAction(this.bad_action));
		//On vérifie si on relache bien le thread précèdent qui est parti en exception
		Assert.assertEquals(this.bad_action, APIController.getAction(this.bad_action));
	}
}
