package net.violet.platform.journal;

import java.util.List;

import net.violet.platform.dataobjects.SubscriptionData;

/**
 * This interface describes the method that must be implemented by a class which can be used as persistence strategy by a SubscriptionLogger object.
 */
public interface LoggerPersistenceManager {

	/**
	 * Retrieves all the entries stored in the log attached to the given subscription.
	 * @param subscription
	 * @return
	 */
	List<JournalEntry> getEntries(SubscriptionData subscription);

	/**
	 * Writes the given entries in the log of the provided subscription. Actually, the provided entries fully replace the previously
	 * stored entries, therefor, after calling this method the given entries are the new entries stored in the log and the old ones are lost.
	 * @param entries
	 * @param subscription
	 */
	void writeEntries(List<JournalEntry> entries, SubscriptionData subscription);

}
