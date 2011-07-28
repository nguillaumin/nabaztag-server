package net.violet.platform.api.actions.messages;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class SendMessageTest extends AbstractTestBase {

	@Test
	public void testSend() throws APIException {

		final Action theAction = new SendMessage();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(getPrivateUser()), theCalendar.getTime()));

		final Map<String, Object> thePojoMessageParams = new HashMap<String, Object>();

		thePojoMessageParams.put("from", UserData.getData(getKowalskyUser()).getApiId(caller));
		thePojoMessageParams.put("to", Arrays.asList(VObjectData.getData(getPrivateObject()).getApiId(caller)));
		thePojoMessageParams.put("title", "SendMessageTest");

		final Map<String, Object> theSequanceMap = new HashMap<String, Object>();
		theSequanceMap.put("type", "expression");
		theSequanceMap.put("modality", "net.violet.tts.en");
		theSequanceMap.put("text", "Yes we can");

		thePojoMessageParams.put("sequence", Collections.singletonList(theSequanceMap));

		theParams.put("message", thePojoMessageParams);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		try {
			final Object theResult = theAction.processRequest(theActionParam);

			Assert.assertNull(theResult);
		} catch (final IllegalStateException e) {}
	}

}
