package net.violet.platform.journal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.violet.platform.dataobjects.SubscriptionData;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

/**
 * This is the main class of the application journal module. A Journal is created with a subscription and contains
 * all the entries stored in this subscription log.
 * 
 * The storage strategy can be specified by providing a LoggerPersistenceManager to the Journal.
 * 
 * Some important things :
 * - after updating the entries in the Journal, the close method MUST be called for the updates to be stored in the
 * persistence layer.
 * - Journal instances are independant. If you create two Journal objects based on the same subscription, modifications
 * performed on the first object will NOT be reflected on the other one, and the last object calling the close method will FULLY populate 
 * the new log in the persistence layer.
 * @author Vincent - Violet
 */
public class Journal {

	protected static final int MAX_ENTRIES = 10;

	private final LoggerPersistenceManager persistenceManager;
	private final SubscriptionData subscription;
	private final CircularFifoBuffer entries = new CircularFifoBuffer(Journal.MAX_ENTRIES);

	/**
	 * Creates a Journal linked to the given SubscriptionData and which will used the provided LoggerPersistenceManager
	 * as persistence strategy.
	 * @param subscription
	 * @param persistenceManager
	 */
	public Journal(SubscriptionData subscription, LoggerPersistenceManager persistenceManager) {
		this.subscription = subscription;
		this.persistenceManager = persistenceManager;
		this.entries.addAll(this.persistenceManager.getEntries(subscription));
	}

	/**
	 * Creates a Journal linked to the given SubscriptionData and which will used the default persistence strategy.
	 * @param subscription
	 */
	public Journal(SubscriptionData subscription) {
		this(subscription, FilesPersistenceManager.getInstance());
	}

	/**
	 * Adds the given entries to the log. If the maximum amount of entries has been reached, the eldest entries are silently removed.
	 * @param entries
	 */
	public void addEntries(List<JournalEntry> newEntries) {
		this.entries.addAll(newEntries);
	}

	/**
	 * Returns the entries stored in the log. The returned list is a copy, updates made on this list will not affect the entries stored
	 * in the log. The returned list is sorted, the eldest entry is the first one.
	 * @return
	 */
	public List<JournalEntry> getEntries() {
		return new ArrayList<JournalEntry>(this.entries);
	}

	/**
	 * Removes the entry at the specified position (0 is the index of the first and eldest entry)
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	public void removeEntry(int index) throws IndexOutOfBoundsException {
		if (index >= this.entries.size()) {
			throw new IndexOutOfBoundsException(index + " > " + this.entries.size());
		}

		final Iterator<JournalEntry> iterator = this.entries.iterator();
		for (int i = 0; i <= index; i++) {
			iterator.next();
		}
		iterator.remove();
	}

	/**
	 * Deletes all the entries.
	 */
	public void clear() {
		this.entries.clear();
	}

	/**
	 * Calls the persistence manager to stored the new entries. This method HAS to be called or all updates performed on the SubscriptionLogger
	 * could be lost.
	 */
	public void flush() {
		this.persistenceManager.writeEntries(getEntries(), this.subscription);
	}

}
