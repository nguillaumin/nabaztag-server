package net.violet.platform.api.actions.messages;

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
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.MessageReceived.MESSAGE_RECEIVED_STATES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MessageMock;
import net.violet.platform.datamodel.mock.MessageReceivedMock;
import net.violet.platform.datamodel.mock.MessageSentMock;
import net.violet.platform.datamodel.mock.MessengerMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.dataobjects.MessageData.StatusMessage;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

public class DeleteTest extends AbstractTestBase {

	@Test
	public void messageInboxOrArchivedDeleteTest() throws APIException {
		final User theRecipientUser = getPrivateUser();
		final VObject theRecipientObject = getPrivateObject();
		final Messenger recipient = new MessengerMock(0, null, theRecipientObject, theRecipientUser.getUser_email());

		final User theSendingUser = getKowalskyUser();
		final Messenger sender = new MessengerMock(0, theSendingUser, null, theSendingUser.getUser_email());

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Message theMessageInbox = new MessageMock(0L, theFile, "messageInbox", new CCalendar(true), 1, Palette.VIOLET);
		final Message theMessageArchived = new MessageMock(0L, theFile, "messageArchived", new CCalendar(true), 2, Palette.VIOLET);

		recipient.getMessageReceived().put(theMessageInbox, new MessageReceivedMock(0L, theMessageInbox, sender, recipient, MESSAGE_RECEIVED_STATES.INBOX));
		recipient.getMessageReceived().put(theMessageArchived, new MessageReceivedMock(0L, theMessageArchived, sender, recipient, MESSAGE_RECEIVED_STATES.ARCHIVED));

		sender.getMessageSent().put(theMessageArchived, new MessageSentMock(0L, theMessageArchived, sender, recipient));
		sender.getMessageSent().put(theMessageInbox, new MessageSentMock(0L, theMessageInbox, sender, recipient));

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		// suppress inbox message
		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageInbox, StatusMessage.INBOX).getApiId(caller));
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theRecipientUser), theCalendar.getTime()));
		theParams.put(Delete.STATUS_PARAM, StatusMessage.INBOX);

		ActionParam theActionParam = new ActionParam(caller, theParams);

		final Action theAction = new Delete();
		Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertFalse(recipient.getMessageReceived().keySet().contains(theMessageInbox));
		Assert.assertNull(Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theMessageInbox.getId()));

		//Archived message
		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageArchived, StatusMessage.ARCHIVED).getApiId(caller));
		theParams.put(Delete.STATUS_PARAM, StatusMessage.ARCHIVED);

		theActionParam = new ActionParam(caller, theParams);

		theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertFalse(recipient.getMessageReceived().keySet().contains(theMessageArchived));
		Assert.assertNull(Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theMessageArchived.getId()));
	}

	@Test
	public void messageSentDeleteTest() throws APIException {
		final User theRecipientUser = getPrivateUser();
		final VObject theRecipientObject = getPrivateObject();
		final Messenger recipient = new MessengerMock(0, null, theRecipientObject, theRecipientUser.getUser_email());

		final User theSendingUser = getKowalskyUser();
		final Messenger sender = new MessengerMock(0, theSendingUser, null, theSendingUser.getUser_email());

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Message theMessageInbox = new MessageMock(0L, theFile, "messageInbox", new CCalendar(true), 1, Palette.VIOLET);

		recipient.getMessageReceived().put(theMessageInbox, new MessageReceivedMock(0L, theMessageInbox, sender, recipient, MESSAGE_RECEIVED_STATES.INBOX));

		sender.getMessageSent().put(theMessageInbox, new MessageSentMock(0L, theMessageInbox, sender, recipient));

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		final Action theAction = new Delete();
		// Suppress sent messages
		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageInbox, StatusMessage.SENT).getApiId(caller));
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theSendingUser), theCalendar.getTime()));
		theParams.put(Delete.STATUS_PARAM, StatusMessage.SENT);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertFalse(sender.getMessageSent().keySet().contains(theMessageInbox));
		Assert.assertNull(Factories.MESSAGE_SENT.findMessageSentByMessageId(theMessageInbox.getId()));
	}

	@Test
	public void messageFutureDelete() throws APIException {

		final User theRecipientUser = getPrivateUser();
		final VObject theRecipientObject = getPrivateObject();
		final Messenger recipient = new MessengerMock(0, null, theRecipientObject, theRecipientUser.getUser_email());

		final User theSendingUser = getKowalskyUser();
		final Messenger sender = new MessengerMock(0, theSendingUser, null, theSendingUser.getUser_email());

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Message theMessageSentInFuture = new MessageMock(0L, theFile, "messageSentInTheFuture", new CCalendar(System.currentTimeMillis() + 120000L), 0, Palette.VIOLET);

		recipient.getMessageReceived().put(theMessageSentInFuture, new MessageReceivedMock(0L, theMessageSentInFuture, sender, recipient, MESSAGE_RECEIVED_STATES.PENDING));

		sender.getMessageSent().put(theMessageSentInFuture, new MessageSentMock(0L, theMessageSentInFuture, sender, recipient));

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theSendingUser), theCalendar.getTime()));
		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageSentInFuture, StatusMessage.SENT).getApiId(caller));
		theParams.put(Delete.STATUS_PARAM, StatusMessage.SENT);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Action theAction = new Delete();
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertFalse(sender.getMessageSent().keySet().contains(theMessageSentInFuture));
		Assert.assertFalse(recipient.getMessageReceived().keySet().contains(theMessageSentInFuture));
		Assert.assertNull(Factories.MESSAGE_SENT.findMessageSentByMessageId(theMessageSentInFuture.getId()));
		Assert.assertNull(Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theMessageSentInFuture.getId()));
		Assert.assertNull(Factories.MESSAGE.find(theMessageSentInFuture.getId()));
	}

	@Test(expected = ForbiddenException.class)
	public void testDeleteBadUser() throws APIException {
		final User theRecipientUser = getPrivateUser();
		final VObject theRecipientObject = getPrivateObject();
		final Messenger recipient = new MessengerMock(0, null, theRecipientObject, theRecipientUser.getUser_email());

		final User theSendingUser = getKowalskyUser();
		final Messenger sender = new MessengerMock(0, theSendingUser, null, theSendingUser.getUser_email());

		final Files theFile = new FilesMock("message", MimeType.MIME_TYPES.A_MPEG);
		final Message theMessageInbox = new MessageMock(0L, theFile, "messageInbox", new CCalendar(true), 1, Palette.VIOLET);

		recipient.getMessageReceived().put(theMessageInbox, new MessageReceivedMock(0L, theMessageInbox, sender, recipient, MESSAGE_RECEIVED_STATES.INBOX));

		sender.getMessageSent().put(theMessageInbox, new MessageSentMock(0L, theMessageInbox, sender, recipient));

		final Application theApplication = new ApplicationMock(12, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();

		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		final Action theAction = new Delete();
		// Suppress sent messages
		theParams.put(ActionParam.MAIN_PARAM_KEY, new MessageData(theMessageInbox, StatusMessage.SENT).getApiId(caller));
		theParams.put(ActionParam.SESSION_PARAM_KEY, SessionManager.generateSessionId(caller, UserData.getData(theRecipientUser), theCalendar.getTime()));
		theParams.put(Delete.STATUS_PARAM, StatusMessage.SENT);

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNull(theResult);
		Assert.assertFalse(sender.getMessageSent().keySet().contains(theMessageInbox));
		Assert.assertNull(Factories.MESSAGE_SENT.findMessageSentByMessageId(theMessageInbox.getId()));

	}

}
