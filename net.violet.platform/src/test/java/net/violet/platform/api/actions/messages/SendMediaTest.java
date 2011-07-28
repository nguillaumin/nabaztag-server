package net.violet.platform.api.actions.messages;

import java.util.Arrays;
import java.util.Calendar;
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
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.HardwareMock;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;

import org.junit.Assert;
import org.junit.Test;

public class SendMediaTest extends AbstractTestBase {

	@Test
	//
	public void testSend() throws APIException {

		final Action theAction = new SendMedia();

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Music theMusic = new MusicMock(123L, theFile, "test", getPrivateUser(), Music.TYPE_MP3_LIBRARY);

		new MessengerMock(0, getKowalskyUser(), getKowalskyObject(), "");
		new MessengerMock(0, getPrivateUser(), getPrivateObject(), "");
		final HardwareMock theHardwareMock = (HardwareMock) Factories.HARDWARE.find(4);
		theHardwareMock.addMimeType(MimeType.MIME_TYPES.A_MPEG);

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(getPrivateUser()), theCalendar.getTime()));

		theParams.put(ActionParam.MAIN_PARAM_KEY, MusicData.getData(theMusic).getApiId(caller));
		theParams.put("recipients", Arrays.asList((Object) VObjectData.getData(getKowalskyObject()).getApiId(caller)));

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
	}

}
