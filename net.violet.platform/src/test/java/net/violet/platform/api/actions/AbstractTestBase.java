package net.violet.platform.api.actions;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;

public abstract class AbstractTestBase extends ApiActionTestBase {

	/**
	 * Generate a session with a 1 hour ttl for another user.
	 * 
	 * @param inApplication
	 *            current application
	 * @return a session ID valid for 60 minutes.
	 */
	public String generateSessionAlterUser(APICaller inCaller) {
		final User alterUser = new UserMock(0, "altersessionuser", "myPassword", "myEmail@gmail.com", getFrLang(), "France", "myFirstName", "myLastName", getParisTimezone(), "H", "Zip", "Paris", 1);

		return generateSession(UserData.getData(alterUser), inCaller);
	}

	/**
	 * Generate a session with a 1 hour ttl for another application.
	 * 
	 * @param inApplication
	 *            current application
	 * @return a session ID valid for 60 minutes.
	 */
	public String generateSessionAlterApplication(UserData inUser) {
		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("alter_session_app", "alter_session_ap", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		return generateSession(inUser, caller);
	}

}
