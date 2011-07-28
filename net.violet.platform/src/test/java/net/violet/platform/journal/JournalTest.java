package net.violet.platform.journal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.violet.platform.dataobjects.SubscriptionData;

import org.junit.Assert;
import org.junit.Test;

public class JournalTest {

	// a fake persistence manager which does not persist anything actually.
	private static final LoggerPersistenceManager MANAGER = new LoggerPersistenceManager() {

		public List<JournalEntry> getEntries(@SuppressWarnings("unused") SubscriptionData subscription) {
			return new ArrayList<JournalEntry>();
		}

		public void writeEntries(@SuppressWarnings("unused") List<JournalEntry> entries, @SuppressWarnings("unused") SubscriptionData subscription) {
		}

	};

	@Test
	public void addAndGetEntriesTest() {
		final Journal logger = new Journal(null, JournalTest.MANAGER);

		final JournalEntry entry1 = new JournalEntry(1234567890, "my first entry");
		final JournalEntry entry2 = new JournalEntry(1234597880, "my second entry");

		logger.addEntries(Arrays.asList(entry1, entry2));

		final List<JournalEntry> theEntries = logger.getEntries();
		Assert.assertEquals(2, theEntries.size());
		Assert.assertEquals(entry1, theEntries.get(0));
		Assert.assertEquals(entry2, theEntries.get(1));
	}

	@Test
	public void clearTest() {
		final Journal logger = new Journal(null, JournalTest.MANAGER);

		final JournalEntry entry1 = new JournalEntry(1234567890, "my first entry");
		final JournalEntry entry2 = new JournalEntry(1234597880, "my second entry");

		logger.addEntries(Arrays.asList(entry1, entry2));

		final List<JournalEntry> theEntries = logger.getEntries();
		Assert.assertEquals(2, theEntries.size());

		logger.clear();
		Assert.assertEquals(0, logger.getEntries().size());
	}

	@Test
	public void addMoreThanMaxSizeTest() {
		final Journal logger = new Journal(null, JournalTest.MANAGER);

		final List<JournalEntry> entriesToAdd = new ArrayList<JournalEntry>(Journal.MAX_ENTRIES * 2);
		final List<JournalEntry> remainingEntries = new ArrayList<JournalEntry>(Journal.MAX_ENTRIES);

		for (int i = 0; i < Journal.MAX_ENTRIES * 2; i++) {
			final JournalEntry theEntry = new JournalEntry("my " + i + " entry");
			entriesToAdd.add(theEntry);
			if (Journal.MAX_ENTRIES * 2 - i <= Journal.MAX_ENTRIES) {
				remainingEntries.add(theEntry);
			}
		}

		logger.addEntries(entriesToAdd);
		final List<JournalEntry> loggedEntry = logger.getEntries();
		Assert.assertEquals(remainingEntries.size(), loggedEntry.size());
		for (int i = 0; i < loggedEntry.size(); i++) {
			Assert.assertEquals(remainingEntries.get(i), loggedEntry.get(i));
		}
	}

	@Test
	public void removeEntryTest() {
		final Journal logger = new Journal(null, JournalTest.MANAGER);

		final List<JournalEntry> entriesToAdd = new ArrayList<JournalEntry>(5);
		for (int i = 0; i < 5; i++) {
			entriesToAdd.add(new JournalEntry("my " + i + " entry"));
		}

		final List<JournalEntry> remainingEntries = new ArrayList<JournalEntry>(entriesToAdd);
		logger.addEntries(entriesToAdd);

		logger.removeEntry(2);
		remainingEntries.remove(2);

		final List<JournalEntry> loggedEntries = logger.getEntries();

		Assert.assertEquals(4, loggedEntries.size());
		Assert.assertEquals(remainingEntries.size(), loggedEntries.size());
		for (int i = 0; i < remainingEntries.size(); i++) {
			Assert.assertEquals(remainingEntries.get(i), loggedEntries.get(i));
		}

		Assert.assertFalse(loggedEntries.contains(entriesToAdd.get(2)));

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void removeOutOfBoundsTest() {
		final Journal logger = new Journal(null, JournalTest.MANAGER);
		logger.removeEntry(3);
	}
}
