package net.violet.platform.api.actions.journal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.LoggerEntryMap;
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

public class GetTest extends MockTestBase {

	@Test
	public void getTest() throws APIException {

		final ApplicationData theApplication = ApplicationData.getData(getMyFirstApplication());
		final SubscriptionData theFirstSubscription = SubscriptionData.create(theApplication, VObjectData.getData(getKowalskyObject()));
		final ApplicationCredentials cred = new ApplicationCredentialsMock("public", "private", theApplication.getReference());
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Journal logger = new Journal(theFirstSubscription);
		logger.addEntries(Arrays.asList(new JournalEntry("entry1"), new JournalEntry("entry2"), new JournalEntry("entry3")));
		logger.flush();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", theFirstSubscription.getApiId(caller));

		final Object theResult = new Get().processRequest(new ActionParam(caller, theParams));
		Assert.assertNotNull(theResult);
		final List<Map<String, Object>> theEntries = (List<Map<String, Object>>) theResult;

		Assert.assertEquals(3, theEntries.size());
		Assert.assertEquals("entry1", theEntries.get(0).get(LoggerEntryMap.WHAT));
		Assert.assertEquals("entry2", theEntries.get(1).get(LoggerEntryMap.WHAT));
		Assert.assertEquals("entry3", theEntries.get(2).get(LoggerEntryMap.WHAT));

		logger.clear();
		logger.flush();

	}

}
