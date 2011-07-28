package net.violet.platform.feeds.processors;


import com.sun.syndication.feed.synd.SyndEntry;

/**
 * An EntryProcessor is in charge of processing a feed entry (yeah trust me!).
 * 
 * Processing an entry can actually be implemented in many different ways, it's just about taking a feed entry as
 * input and returning  a ProcessedEntry.
 */
public interface EntryProcessor {

	ProcessedEntry processEntry(SyndEntry entry) throws ProcessingException;
}
