package net.violet.platform.api.actions;

import java.util.Map;

import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;

import org.junit.Test;

public abstract class AbstractPeopleWithSessionBase extends AbstractTestBase {

	@Test(expected = InvalidParameterException.class)
	public abstract void testNoSession() throws APIException;

	@Test(expected = ForbiddenException.class)
	public abstract void testOtherUserSession() throws APIException;

	@Test(expected = InvalidSessionException.class)
	public abstract void testOtherApplicationSession() throws APIException;

	@Test
	public abstract void testValidSession() throws APIException;

	protected void setSessionParam(Map<String, Object> inParams, String inSessionId) {
		if (inSessionId != null) {
			inParams.put(ActionParam.SESSION_PARAM_KEY, inSessionId);
		}
	}

}
