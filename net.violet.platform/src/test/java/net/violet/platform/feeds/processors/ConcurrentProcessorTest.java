package net.violet.platform.feeds.processors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class ConcurrentProcessorTest extends MockTestBase {

	private SyndEntry getEntry(String uri, String title) {
		final SyndEntry entry = new SyndEntryImpl();
		entry.setUri(uri);
		entry.setTitle(title);
		return entry;
	}

	@Test
	public void processEntriesTest() {
		final EntryProcessor processor = new EntryProcessor() {

			public ProcessedEntry processEntry(SyndEntry entry) {
				if (entry.getUri().equals("2") || entry.getUri().equals("3")) {
					try {
						Thread.sleep(2000); // fake long treatment to test sorting
					} catch (final InterruptedException e) {}
				}

				return new ProcessedEntry(entry, Collections.<Files> emptyList());
			}
		};

		final ConcurrentProcessor concProc = new ConcurrentProcessor(processor);

		final List<SyndEntry> entries = new ArrayList<SyndEntry>();
		for (int i = 0; i < 5; i++) {
			entries.add(getEntry(String.valueOf(i), "title" + i));
		}

		final List<ProcessedEntry> procEntries = concProc.processEntries(entries);
		Assert.assertEquals(5, procEntries.size());
		for (int i = 0; i < 5; i++) {
			Assert.assertEquals(String.valueOf(i), procEntries.get(i).getEntry().getUri());
		}

	}

	@Test
	public void processEntriesWithOneFailureTest() {
		final EntryProcessor processor = new EntryProcessor() {

			public ProcessedEntry processEntry(SyndEntry entry) throws ProcessingException {
				if (entry.getUri().equals("0")) {
					throw new ProcessingException("Failure");
				}

				if (entry.getUri().equals("2") || entry.getUri().equals("3")) {
					try {
						Thread.sleep(2000); // fake long treatment to test sorting
					} catch (final InterruptedException e) {}
				}

				return new ProcessedEntry(entry, Collections.<Files> emptyList());
			}
		};

		final ConcurrentProcessor concProc = new ConcurrentProcessor(processor);

		final List<SyndEntry> entries = new ArrayList<SyndEntry>();
		for (int i = 0; i < 5; i++) {
			entries.add(getEntry(String.valueOf(i), "title" + i));
		}

		final List<ProcessedEntry> procEntries = concProc.processEntries(entries);
		Assert.assertEquals(4, procEntries.size());
		Assert.assertEquals("1", procEntries.get(0).getEntry().getUri());
		Assert.assertEquals("2", procEntries.get(1).getEntry().getUri());
		Assert.assertEquals("3", procEntries.get(2).getEntry().getUri());
		Assert.assertEquals("4", procEntries.get(3).getEntry().getUri());

	}

}
