package net.violet.platform.feeds.processors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class DownloadingProcessorTest extends MockTestBase {

	@Test
	public void downloadWithValidEnclosureTest() throws ProcessingException {

		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle("my entry");
		final SyndEnclosure enclosure = new SyndEnclosureImpl();
		enclosure.setUrl("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3");
		enclosure.setType("audio/mpeg");
		entry.setEnclosures(Collections.singletonList(enclosure));

		final DownloadingProcessor processor = new DownloadingProcessor(true);
		final ProcessedEntry result = processor.processEntry(entry);

		Assert.assertNotNull(result);
		Assert.assertEquals(entry, result.getEntry());
		final List<Files> contents = result.getContents();
		Assert.assertEquals(1, contents.size());
	}

	@Test(expected = ProcessingException.class)
	public void downloadWithInvalidFormatTest() throws ProcessingException {

		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle("my entry");
		final SyndEnclosure invalidEnclosure = new SyndEnclosureImpl();
		invalidEnclosure.setUrl("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.wav");
		invalidEnclosure.setType("audio/mpeg");
		entry.setEnclosures(Arrays.asList(invalidEnclosure));

		final DownloadingProcessor processor = new DownloadingProcessor(true);
		processor.processEntry(entry);
	}

	@Test(expected = ProcessingException.class)
	public void downloadWithInvalidTypeTest() throws ProcessingException {

		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle("my entry");
		final SyndEnclosure invalidEnclosure = new SyndEnclosureImpl();
		invalidEnclosure.setUrl("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3");
		invalidEnclosure.setType("video/avi");
		entry.setEnclosures(Arrays.asList(invalidEnclosure));

		final DownloadingProcessor processor = new DownloadingProcessor(true);
		processor.processEntry(entry);
	}

	@Test
	public void downloadWithValidAndInvalidEnclosuresTest() throws ProcessingException {

		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle("my entry");
		final SyndEnclosure invalidEnclosure = new SyndEnclosureImpl();
		invalidEnclosure.setUrl("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.wav");
		invalidEnclosure.setType("audio/mpeg");
		final SyndEnclosure validEnclosure = new SyndEnclosureImpl();
		validEnclosure.setUrl("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3");
		validEnclosure.setType("audio/mpeg");
		entry.setEnclosures(Arrays.asList(invalidEnclosure, validEnclosure));

		final DownloadingProcessor processor = new DownloadingProcessor(true);
		final ProcessedEntry result = processor.processEntry(entry);

		Assert.assertNotNull(result);
		Assert.assertEquals(entry, result.getEntry());
		final List<Files> contents = result.getContents();
		Assert.assertEquals(1, contents.size());
	}

}
