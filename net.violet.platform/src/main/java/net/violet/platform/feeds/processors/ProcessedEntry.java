package net.violet.platform.feeds.processors;

import java.util.List;

import net.violet.platform.datamodel.Files;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * A ProcessedEntry is produced by EntryProcessor object, it wraps the SyndEntry which has been processed and the contents
 * which were created by the process.
 */
public class ProcessedEntry {

	private final SyndEntry entry;
	private final List<Files> contents;
	private final String concretLink;

	// public visibility because of the tests using it.
	public ProcessedEntry(SyndEntry entry, List<Files> contents) {
		this(entry, contents, entry.getLink());
	}

	protected ProcessedEntry(SyndEntry entry, List<Files> contents, String concretLink) {
		this.entry = entry;
		this.contents = contents;
		this.concretLink = concretLink;
	}

	public SyndEntry getEntry() {
		return this.entry;
	}

	public List<Files> getContents() {
		return this.contents;
	}

	public String getConcretLink() {
		return this.concretLink;
	}

}
