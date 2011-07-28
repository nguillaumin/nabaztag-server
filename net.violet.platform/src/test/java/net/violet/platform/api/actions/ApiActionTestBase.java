package net.violet.platform.api.actions;

import java.util.Calendar;
import java.util.Date;

import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.web.ServletTestBase;

public class ApiActionTestBase extends ServletTestBase {

	private ApplicationAPICaller mPublicApplication;

	/**
	 * Generate a session with a 1 hour ttl.
	 * 
	 * @param inUser
	 *            user for the session.
	 * @param inApplication
	 *            current application
	 * @return a session ID valid for 60 minutes.
	 */
	public String generateSession(UserData inUser, APICaller inCaller) {
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		return SessionManager.generateSessionId(inCaller, inUser, theCalendar.getTime());
	}

	public ApplicationAPICaller getPublicApplicationAPICaller() {
		if (this.mPublicApplication == null) {
			final Date now = new Date();
			final Application theApplication = new ApplicationMock(42, "My first application", getKowalskyUser(), now);
			final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication);
			this.mPublicApplication = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		}
		return this.mPublicApplication;
	}
}
