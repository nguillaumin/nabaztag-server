package net.violet.platform.api.actions.messages;

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
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageCounter;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.MessageReceived.MESSAGE_RECEIVED_STATES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MessageCounterMock;
import net.violet.platform.datamodel.mock.MessageMock;
import net.violet.platform.datamodel.mock.MessageReceivedMock;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class ArchiveTest extends AbstractTestBase {

	@Test
	public void testArchive() throws APIException {

		final User theUserReceived = new UserMock(0, "private2", "test", "usermessagetest1@violet.net", getFrLang(), "test", "test", "test", getParisTimezone());
		final User theUserSender = new UserMock(0, "kowalsky", "test", "usermessagetest2@violet.net", getFrLang(), "test", "test", "test", getParisTimezone());

		final VObject theObjectReceived = new VObjectMock(0, "serial1", "private2", theUserReceived, HARDWARE.V1, getParisTimezone(), getFrLang());
		final VObject theObjectSender = new VObjectMock(0, "serial2", "kowalsky", theUserSender, HARDWARE.V1, getParisTimezone(), getFrLang());

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Message theMessageInbox = new MessageMock(1L, theFile, "testMessage 1", new CCalendar(true), 1, Palette.VIOLET);
		final Message theMessageArchived = new MessageMock(3L, theFile, "testMessage 3", new CCalendar(true), 2, Palette.VIOLET);

		final Messenger theMessengerSender = new MessengerMock(1, theUserSender, theObjectSender, "kowalsky");
		final Messenger theMessengerReceiver = new MessengerMock(1, theUserReceived, theObjectReceived, "private2");

		new MessageReceivedMock(3L, theMessageArchived, theMessengerSender, theMessengerReceiver, MESSAGE_RECEIVED_STATES.ARCHIVED);
		final MessageReceived theMessageReceivedInbox = new MessageReceivedMock(1L, theMessageInbox, theMessengerSender, theMessengerReceiver, MESSAGE_RECEIVED_STATES.INBOX);

		final MessageCounter thecounter = new MessageCounterMock(theMessengerReceiver.getId(), 0, null);

		thecounter.setRabbit_state(MessageCounter.RABBIT_STATE.ZERO);

		final Action theAction = new Archive();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		/**
		 * Message recu
		 */

		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageInbox, null).getApiId(caller));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theUserReceived), theCalendar.getTime()));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertEquals(thecounter.getRabbit_state(), MessageCounter.RABBIT_STATE.INVALIDE);
		Assert.assertEquals(theMessageReceivedInbox.getMessageState(), MESSAGE_RECEIVED_STATES.ARCHIVED);

	}

	@Test
	public void multiArchiveTest() throws APIException {
		final User theUserReceived = new UserMock(0, "private2", "test", "usermessagetest1@violet.net", getFrLang(), "test", "test", "test", getParisTimezone());
		final User theUserSender = new UserMock(0, "kowalsky", "test", "usermessagetest2@violet.net", getFrLang(), "test", "test", "test", getParisTimezone());

		final VObject theObjectReceived = new VObjectMock(0, "serial1", "private2", theUserReceived, HARDWARE.V1, getParisTimezone(), getFrLang());
		final VObject theObjectSender = new VObjectMock(0, "serial2", "kowalsky", theUserSender, HARDWARE.V1, getParisTimezone(), getFrLang());

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Message theMessageInbox = new MessageMock(1L, theFile, "testMessage 1", new CCalendar(true), 1, Palette.VIOLET);
		final Message theMessageInbox2 = new MessageMock(2L, theFile, "testMessage 2", new CCalendar(true), 2, Palette.VIOLET);
		final Message theMessageInbox3 = new MessageMock(3L, theFile, "testMessage 3", new CCalendar(true), 3, Palette.VIOLET);
		final Message theMessageArchived = new MessageMock(4L, theFile, "testMessage 4", new CCalendar(true), 4, Palette.VIOLET);

		final Messenger theMessengerSender = new MessengerMock(1, theUserSender, theObjectSender, "kowalsky");
		final Messenger theMessengerReceiver = new MessengerMock(1, theUserReceived, theObjectReceived, "private2");

		new MessageReceivedMock(3L, theMessageArchived, theMessengerSender, theMessengerReceiver, MESSAGE_RECEIVED_STATES.ARCHIVED);
		final MessageReceived theMessageReceivedInbox = new MessageReceivedMock(1L, theMessageInbox, theMessengerSender, theMessengerReceiver, MESSAGE_RECEIVED_STATES.INBOX);
		final MessageReceived theMessageReceivedInbox2 = new MessageReceivedMock(2L, theMessageInbox2, theMessengerSender, theMessengerReceiver, MESSAGE_RECEIVED_STATES.INBOX);
		final MessageReceived theMessageReceivedInbox3 = new MessageReceivedMock(3L, theMessageInbox3, theMessengerSender, theMessengerReceiver, MESSAGE_RECEIVED_STATES.INBOX);

		final MessageCounter thecounter = new MessageCounterMock(theMessengerReceiver.getId(), 0, null);

		thecounter.setRabbit_state(MessageCounter.RABBIT_STATE.ZERO);

		final Action theAction = new Archive();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageInbox, null).getApiId(caller));
		theParams.put("ids", Collections.singletonList(new MessageData(theMessageInbox2, null).getApiId(caller)));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theUserReceived), theCalendar.getTime()));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertEquals(thecounter.getRabbit_state(), MessageCounter.RABBIT_STATE.INVALIDE);
		Assert.assertEquals(theMessageReceivedInbox.getMessageState(), MESSAGE_RECEIVED_STATES.ARCHIVED);
		Assert.assertEquals(theMessageReceivedInbox2.getMessageState(), MESSAGE_RECEIVED_STATES.ARCHIVED);
		Assert.assertEquals(theMessageReceivedInbox3.getMessageState(), MESSAGE_RECEIVED_STATES.INBOX);
	}

	@Test(expected = InvalidParameterException.class)
	public void testAlreadyArchive() throws APIException {
		final User theUserReceived = new UserMock(0, "private2", "test", "usermessagetest1@violet.net", getFrLang(), "test", "test", "test", getParisTimezone());
		final User theUserSender = new UserMock(0, "kowalsky", "test", "usermessagetest2@violet.net", getFrLang(), "test", "test", "test", getParisTimezone());
		final VObject theObjectReceived = new VObjectMock(0, "serial1", "private2", theUserReceived, HARDWARE.V1, getParisTimezone(), getFrLang());
		final VObject theObjectSender = new VObjectMock(0, "serial2", "kowalsky", theUserSender, HARDWARE.V1, getParisTimezone(), getFrLang());

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Message theMessageArchived = new MessageMock(3L, theFile, "testMessage 3", new CCalendar(true), 2, Palette.VIOLET);

		final Messenger theMessengerSender = new MessengerMock(1, theUserSender, theObjectSender, "kowalsky");
		final Messenger theMessengerReceiver = new MessengerMock(1, theUserReceived, theObjectReceived, "private2");

		new MessageReceivedMock(3L, theMessageArchived, theMessengerSender, theMessengerReceiver, MESSAGE_RECEIVED_STATES.ARCHIVED);

		final MessageCounter thecounter = new MessageCounterMock(theMessengerReceiver.getId(), 0, null);

		thecounter.setRabbit_state(MessageCounter.RABBIT_STATE.ZERO);

		final Action theAction = new Archive();

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		/**
		 * Message archivé
		 */

		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageArchived, null).getApiId(caller));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theUserReceived), theCalendar.getTime()));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = ForbiddenException.class)
	public void testArchiveBadUser() throws APIException {
		final User theUserReceived = new UserMock(97238, "private2", "test", "usermessagetest1@violet.net", getFrLang(), "test", "test", "test", getParisTimezone());
		final User theUserSender = new UserMock(90481, "kowalsky", "test", "usermessagetest2@violet.net", getFrLang(), "test", "test", "test", getParisTimezone());
		final VObject theObjectReceived = new VObjectMock(63644, "serial1", "private2", theUserReceived, HARDWARE.V1, getParisTimezone(), getFrLang());
		final VObject theObjectSender = new VObjectMock(60463, "serial2", "kowalsky", theUserSender, HARDWARE.V1, getParisTimezone(), getFrLang());

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Message theMessageInbox = Factories.MESSAGE.create(theFile, "testMessage1", new CCalendar(true), Palette.VIOLET);

		final Messenger theMessengerSender = new MessengerMock(1, theUserSender, theObjectSender, "kowalsky");
		final Messenger theMessengerReceiver = new MessengerMock(1, theUserReceived, theObjectReceived, "private2");

		new MessageReceivedMock(3L, theMessageInbox, theMessengerSender, theMessengerReceiver, MESSAGE_RECEIVED_STATES.INBOX);

		final Action theAction = new Archive();

		final Application theApplication = getMyFirstApplication();

		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		/**
		 * Message recu
		 */

		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageInbox, null).getApiId(caller));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theUserSender), theCalendar.getTime()));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);

	}
}
