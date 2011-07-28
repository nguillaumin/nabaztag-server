package net.violet.platform.journal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.violet.platform.dataobjects.SubscriptionData;

/**
 * A very simple LoggerPersistenceManager which only stored information in the application memory.
 */
public class MemoryPersistenceManager implements LoggerPersistenceManager {

	private static final MemoryPersistenceManager MANAGER = new MemoryPersistenceManager();

	public static LoggerPersistenceManager getInstance() {
		return MemoryPersistenceManager.MANAGER;
	}

	private static final Map<SubscriptionData, List<JournalEntry>> ENTRIES_BY_SUBSCRIPTION = new ConcurrentHashMap<SubscriptionData, List<JournalEntry>>();

	public List<JournalEntry> getEntries(SubscriptionData subscription) {
		List<JournalEntry> theEntries = MemoryPersistenceManager.ENTRIES_BY_SUBSCRIPTION.get(subscription);
		if (theEntries == null) {
			theEntries = new ArrayList<JournalEntry>();
		}

		return theEntries;
	}

	public void writeEntries(List<JournalEntry> entries, SubscriptionData subscription) {
		MemoryPersistenceManager.ENTRIES_BY_SUBSCRIPTION.put(subscription, entries);
	}

}
