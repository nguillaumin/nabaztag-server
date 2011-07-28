package net.violet.platform.journal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.SubscriptionMock;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionLogData;
import net.violet.platform.files.FilesManagerFactory;

import org.junit.Assert;
import org.junit.Test;

public class FilesPersistenceManagerTest extends MockTestBase {

	@Test
	public void getEmptyFileTest() {
		final SubscriptionData theSubscription = SubscriptionData.getData(new SubscriptionMock(0, getMyFirstApplication(), getKowalskyObject()));

		final LoggerPersistenceManager manager = FilesPersistenceManager.getInstance();
		final List<JournalEntry> theEntries = manager.getEntries(theSubscription);
		Assert.assertTrue(theEntries.isEmpty());
		Assert.assertNotNull(SubscriptionLogData.getBySubscription(theSubscription).getLogFile());
	}

	private static final Pattern RESULT = Pattern.compile("\\[\\{.+:\"first\",.+:\"second\",.+:\"third\",.+$");

	@Test
	public void writeEntriesTest() throws UnsupportedEncodingException, IOException {
		final SubscriptionData theSubscription = SubscriptionData.getData(new SubscriptionMock(0, getMyFirstApplication(), getKowalskyObject()));

		final LoggerPersistenceManager manager = FilesPersistenceManager.getInstance();
		final List<JournalEntry> entriesToAdd = Arrays.asList(new JournalEntry("first"), new JournalEntry("second"), new JournalEntry("third"));

		manager.writeEntries(entriesToAdd, theSubscription);

		final FilesData theLogFile = SubscriptionLogData.getBySubscription(theSubscription).getLogFile();
		Assert.assertNotNull(theLogFile);
		final String content = new String(FilesManagerFactory.FILE_MANAGER.getFilesContent(theLogFile.getReference()), "UTF-8");
		Assert.assertTrue(FilesPersistenceManagerTest.RESULT.matcher(content).matches());
	}

	@Test
	public void writeSeveralTimesEntriesTest() {
		final SubscriptionData theSubscription = SubscriptionData.getData(new SubscriptionMock(0, getMyFirstApplication(), getKowalskyObject()));

		final LoggerPersistenceManager manager = FilesPersistenceManager.getInstance();
		final List<JournalEntry> entriesToAdd = Arrays.asList(new JournalEntry("first"), new JournalEntry("second"), new JournalEntry("third"));

		manager.writeEntries(entriesToAdd, theSubscription);

		final FilesData theLogFile = SubscriptionLogData.getBySubscription(theSubscription).getLogFile();

		manager.writeEntries(entriesToAdd, theSubscription);

		final FilesData theOtherFile = SubscriptionLogData.getBySubscription(theSubscription).getLogFile();
		Assert.assertEquals(theLogFile, theOtherFile);
	}

	@Test
	public void writeAndReadEntriesTest() {
		final SubscriptionData theSubscription = SubscriptionData.getData(new SubscriptionMock(0, getMyFirstApplication(), getKowalskyObject()));

		final LoggerPersistenceManager manager = FilesPersistenceManager.getInstance();
		final List<JournalEntry> entriesToAdd = Arrays.asList(new JournalEntry("first"), new JournalEntry("second"), new JournalEntry("third"));

		manager.writeEntries(entriesToAdd, theSubscription);

		final List<JournalEntry> theEntries = manager.getEntries(theSubscription);
		Assert.assertEquals(3, theEntries.size());
		Assert.assertEquals("first", theEntries.get(0).getWhat());
		Assert.assertEquals("second", theEntries.get(1).getWhat());
		Assert.assertEquals("third", theEntries.get(2).getWhat());
	}
}
