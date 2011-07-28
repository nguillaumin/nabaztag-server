package net.violet.platform.feeds.senders;

import java.util.List;

import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.processors.ProcessedEntry;

/**
 * The FullSender is actually a convenient EntrySender which will use both DBSender and InstantSender to persist the entry in the 
 * database and send it to the recipients.
 */
public class FullSender implements EntrySender {

	private final DBSender dbSender;
	private final InstantSender instantSender;

	public FullSender(FeedData feed, int maxEntriesPerFeed, List<VObjectData> recipients) {
		this.dbSender = new DBSender(feed, maxEntriesPerFeed);
		this.instantSender = new InstantSender(recipients, null, "empty title");
	}

	public void performTreatment(List<ProcessedEntry> entries) {
		this.dbSender.performTreatment(entries);
		this.instantSender.performTreatment(entries);
	}
}
