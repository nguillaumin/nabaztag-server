package net.violet.platform.feeds.senders;

import java.util.List;

import net.violet.platform.feeds.processors.ProcessedEntry;

/**
 * An EntrySender has to implement a treatment one wants to apply to a list of ProcessedEntry object.
 */
public interface EntrySender {

	/**
	 * Performes specific treatment on the given entries. The entries have to be ordered, the eldest one is the first one in the list.
	 * @param entries
	 */
	void performTreatment(List<ProcessedEntry> entries);

}
