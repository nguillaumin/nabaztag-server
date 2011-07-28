package net.violet.platform.api.actions.journal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
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

public class AddEntriesTest extends MockTestBase {

	@Test
	public void addEntriesTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));
		theParams.put("entries", Arrays.asList("an entry", "a second entry"));

		final Object theResult = new AddEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		final Journal logger = new Journal(theFirstSubscription);
		final List<JournalEntry> theEntries = logger.getEntries();
		Assert.assertEquals(2, theEntries.size());
		Assert.assertEquals("an entry", theEntries.get(0).getWhat());
		Assert.assertEquals("a second entry", theEntries.get(1).getWhat());

		logger.clear();
		logger.flush();

	}

	@Test
	public void addLongEntriesTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));

		final StringBuilder entry1 = new StringBuilder();
		final StringBuilder entry2 = new StringBuilder();
		for (int i = 0; i < 200; i++) {
			entry1.append(i);
			entry2.append(-i);
		}

		Assert.assertTrue(entry1.length() > 250);
		Assert.assertTrue(entry2.length() > 250);

		theParams.put("entries", Arrays.asList(entry1.toString(), entry2.toString()));

		final Object theResult = new AddEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		final Journal logger = new Journal(theFirstSubscription);
		final List<JournalEntry> theEntries = logger.getEntries();
		Assert.assertEquals(2, theEntries.size());
		Assert.assertEquals(entry1.substring(0, 250), theEntries.get(0).getWhat());
		Assert.assertEquals(entry2.substring(0, 250), theEntries.get(1).getWhat());

		logger.clear();
		logger.flush();
	}

	@Test
	public void twoApiCallsTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));
		theParams.put("entries", Arrays.asList("an entry", "a second entry"));

		Object theResult = new AddEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));
		theParams.put("entries", Arrays.asList("a third entry", "a fourth entry"));

		theResult = new AddEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		final Journal logger = new Journal(theFirstSubscription);
		final List<JournalEntry> theEntries = logger.getEntries();

		Assert.assertEquals(4, theEntries.size());
		Assert.assertEquals("an entry", theEntries.get(0).getWhat());
		Assert.assertEquals("a second entry", theEntries.get(1).getWhat());
		Assert.assertEquals("a third entry", theEntries.get(2).getWhat());
		Assert.assertEquals("a fourth entry", theEntries.get(3).getWhat());

		logger.clear();
		logger.flush();
	}

	@Test
	public void addTooMuchEntriesTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));
		final List<String> entries = new ArrayList<String>(15);
		for (int i = 0; i < 15; i++) {
			entries.add("entry " + i);
		}
		theParams.put("entries", entries);

		final Object theResult = new AddEntries().processRequest(new ActionParam(caller, theParams));
		Assert.assertNull(theResult);

		final Journal logger = new Journal(theFirstSubscription);
		final List<JournalEntry> theEntries = logger.getEntries();
		Assert.assertEquals(10, theEntries.size());
		Assert.assertEquals("entry 5", theEntries.get(0).getWhat());
		Assert.assertEquals("entry 14", theEntries.get(9).getWhat());

		logger.clear();
		logger.flush();
	}

	@Test(expected = ForbiddenException.class)
	public void badAppliTest() throws APIException {
		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final ApplicationData theOtherApplication = ApplicationData.getData(getMySecondApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theOtherApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));
		theParams.put("entries", Arrays.asList("an entry", "a second entry"));

		new AddEntries().processRequest(new ActionParam(caller, theParams));
	}

}
