package net.violet.platform.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.UserData;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.junit.Test;

public class UploadTest {

	@Test
	public void dummyTest() {

	}

	/**
	 * Test upload de file a travers l'api , le test est commenté car il ne
	 * marchera pas sur silence cependant il permet en local de tester si
	 * l'upload de fichier ce passe bien avec nabdev!!!
	 * 
	 * @throws FileNotFoundException
	 */
	//@Test
	public void testUpload() throws FileNotFoundException {
		/*
		 * final URL apiBaseURL; try { apiBaseURL = new
		 * URL("http://192.168.1.11:8080/vl/upload"); } catch
		 * (MalformedURLException e) { throw new
		 * IllegalArgumentException("Service URL is malformed "); } final String
		 * mRestServiceUrl = apiBaseURL.toExternalForm(); //
		 * restServiceUrl.endsWith("/") ? restServiceUrl : restServiceUrl + "/";
		 * final Credentials creds = new UsernamePasswordCredentials("webui",
		 * "private"); final AuthScope apiAuthScope = new
		 * AuthScope(apiBaseURL.getHost(), apiBaseURL.getPort(),
		 * "api.violet.net", HttpServletRequest.DIGEST_AUTH);
		 * 
		 * 
		 * final HttpState state = new HttpState();
		 * state.setCredentials(apiAuthScope, creds); client.setState(state);
		 * client.getParams().setAuthenticationPreemptive(true);
		 */

		final User theUser = Factories.USER.find(63643);

		final ApplicationData theApplication = ApplicationData.findById(12L);
		final ApplicationCredentialsData theAC = ApplicationCredentialsData.findByApplication(theApplication);
		final APICaller caller = new ApplicationAPICaller(theAC);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);
		theCalendar.add(Calendar.YEAR, +100);
		final String theSession = SessionManager.generateSessionId(caller, UserData.getData(theUser), theCalendar.getTime());
		//		

		final HttpClient client = new HttpClient();
		final String actionUri = "http://127.0.0.1:8080/OS/upload";

		final PostMethod put = new PostMethod(actionUri);
		final File input = new File("/home/loic/Desktop/Japan.jpg");

		final Part[] parts = { new StringPart(ActionParam.SESSION_PARAM_KEY, theSession), new FilePart(input.getName(), input, "image/jpeg", null) };

		put.setRequestEntity(new MultipartRequestEntity(parts, put.getParams()));

		try {
			client.executeMethod(put);
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		}
	}
}
