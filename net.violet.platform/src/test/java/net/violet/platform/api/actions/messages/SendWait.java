package net.violet.platform.api.actions.messages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

/**
 * This is not a real JUnit test. This class uses the information stored in the
 * database and does not use mock. It is just an easy way to test sending
 * message with the API.
 */
public class SendWait {

	public static void main(String[] args) throws APIException {
		final UserData theSender = UserData.getData(Factories.VOBJECT.findByName("kowalsky").getOwner());
		final VObjectData theReceiver = VObjectData.getData(Factories.VOBJECT.find(61717));

		final Action theAction = new SendMessage();

		final Application theApplication = Factories.APPLICATION.find(2);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final Map<String, Object> theSequence = new HashMap<String, Object>();
		theSequence.put("modality", "net.violet.wait");
		theSequence.put("time_ms", 3000);
		theSequence.put("type", "expression");

		theParams.put(ActionParam.MAIN_PARAM_KEY, theSender.getApiId(caller));

		final Map<String, Object> theMessage = new HashMap<String, Object>();
		theMessage.put("from", theSender.getApiId(caller));
		theMessage.put("to", Arrays.asList(new String[] { theReceiver.getApiId(caller) }));

		theMessage.put("sequence", Arrays.asList(new Object[] { theSequence }));

		theParams.put("message", theMessage);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);
	}

}
