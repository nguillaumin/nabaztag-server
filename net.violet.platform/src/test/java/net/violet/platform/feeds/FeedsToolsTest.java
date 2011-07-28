package net.violet.platform.feeds;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class FeedsToolsTest {

	@Test
	public void extractEnclosureTest() {
		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle("my entry");
		final SyndEnclosure invalidEnclosure = new SyndEnclosureImpl();
		invalidEnclosure.setUrl("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.wav");
		invalidEnclosure.setType("audio/mpeg");
		final SyndEnclosure validEnclosure = new SyndEnclosureImpl();
		validEnclosure.setUrl("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3");
		validEnclosure.setType("audio/mpeg");
		entry.setEnclosures(Arrays.asList(invalidEnclosure, validEnclosure));

		Assert.assertEquals("http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3", FeedsTools.extractEnclosureUrl(entry));
	}

	@Test
	public void isFeedValidTest() {
		Assert.assertFalse(FeedsTools.isFeedValid("http://www.google.com", null, null));
		Assert.assertFalse(FeedsTools.isFeedValid("https://mail.google.com/mail/feed/atom", "titi", "tutu"));
		Assert.assertTrue(FeedsTools.isFeedValid("https://mail.google.com/mail/feed/atom", "vnabaztag", "violet123"));
		Assert.assertTrue(FeedsTools.isFeedValid("http://www.lesinrocks.com/xml/rss/podcast.xml", null, null));
	}

	@Test
	public void extractLanguageTest() {
		Assert.assertEquals("fr-FR", FeedsTools.extractFeedLanguage("http://www.lesinrocks.com/xml/rss/podcast.xml").getLang_iso_code());
	}

}
