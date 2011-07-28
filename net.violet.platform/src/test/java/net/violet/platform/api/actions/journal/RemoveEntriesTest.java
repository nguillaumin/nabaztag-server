package net.violet.platform.api.actions.journal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.journal.Journal;
import net.violet.platform.journal.JournalEntry;

import org.junit.Assert;
import org.junit.Test;

public class RemoveEntriesTest extends MockTestBase {

	@Test
	public void removeEntryTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		Journal journal = new Journal(theFirstSubscription);
		journal.addEntries(Arrays.asList(new JournalEntry("entry1"), new JournalEntry("entry2")));
		journal.flush();

		final Map<String, Object> theParams = new HashMap<String, Object>(4);
		theParams.put("id", theFirstSubscription.getApiId(caller));
		theParams.put("index", 0);

		final Object theResult = new RemoveEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		journal = new Journal(theFirstSubscription);
		final List<JournalEntry> theEntries = journal.getEntries();
		Assert.assertEquals(1, theEntries.size());
		Assert.assertEquals("entry2", theEntries.get(0).getWhat());

		journal.clear();
		journal.flush();

	}

	@Test(expected = InvalidParameterException.class)
	public void outOfBoundsTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));
		theParams.put("index", 4);

		new RemoveEntries().processRequest(new ActionParam(caller, theParams));
	}

	@Test
	public void twoApiCallsTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		Journal logger = new Journal(theFirstSubscription);
		logger.addEntries(Arrays.asList(new JournalEntry("entry1"), new JournalEntry("entry2"), new JournalEntry("entry3"), new JournalEntry("entry4")));
		logger.flush();

		Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));
		theParams.put("index", 1);

		Object theResult = new RemoveEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));
		theParams.put("index", 2);

		theResult = new RemoveEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		logger = new Journal(theFirstSubscription);
		final List<JournalEntry> theEntries = logger.getEntries();

		Assert.assertEquals(2, theEntries.size());
		Assert.assertEquals("entry1", theEntries.get(0).getWhat());
		Assert.assertEquals("entry3", theEntries.get(1).getWhat());

		logger.clear();
		logger.flush();
	}

	@Test
	public void clearTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		Journal logger = new Journal(theFirstSubscription);
		logger.addEntries(Arrays.asList(new JournalEntry("entry1"), new JournalEntry("entry2")));
		logger.flush();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));

		final Object theResult = new RemoveEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		logger = new Journal(theFirstSubscription);
		final List<JournalEntry> theEntries = logger.getEntries();
		Assert.assertTrue(theEntries.isEmpty());

		logger.clear();
		logger.flush();
	}

}
