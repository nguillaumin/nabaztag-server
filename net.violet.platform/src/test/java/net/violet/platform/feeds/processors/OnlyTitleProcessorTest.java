package net.violet.platform.feeds.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.TtsVoiceMock;
import net.violet.platform.voice.TTSServices;

import org.junit.Assert;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class OnlyTitleProcessorTest extends MockTestBase {

	@Test
	public void getTextProcessorTest() throws ProcessingException {
		final OnlyTitleProcessor processor = OnlyTitleProcessor.getProcessor((TtsVoice) null);

		final AtomicBoolean isOk = new AtomicBoolean(false);
		final List<Files> expectedContent = new ArrayList<Files>();

		processor.setTTSEngine(new TTSServices(null) {

			@Override
			@SuppressWarnings("unused")
			public Files postTTS(String text, boolean chor, boolean adp, TtsVoice inVoice) throws IllegalArgumentException {
				if (text.equals("my title")) {
					isOk.set(true);
				}

				final Files files = new FilesMock("file", MIME_TYPES.A_MPEG);
				expectedContent.add(files);
				return files;
			}
		});

		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle("my title");
		final ProcessedEntry procEntry = processor.processEntry(entry);

		Assert.assertTrue(isOk.get());
		Assert.assertNotNull(procEntry);
		Assert.assertEquals(entry, procEntry.getEntry());
		Assert.assertEquals(expectedContent, procEntry.getContents());
	}

	@Test
	public void langCacheTest() {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Lang usLang = Factories.LANG.findByIsoCode("en-US");
		Assert.assertNotNull(frLang);
		Assert.assertNotNull(usLang);

		new TtsVoiceMock(0, "my voice fr", "command", frLang, "str", true, false);
		new TtsVoiceMock(0, "my voice us", "command", usLang, "str", true, false);

		final OnlyTitleProcessor processor1 = OnlyTitleProcessor.getProcessor(usLang);
		final OnlyTitleProcessor processor2 = OnlyTitleProcessor.getProcessor(frLang);
		final OnlyTitleProcessor processor3 = OnlyTitleProcessor.getProcessor(usLang);
		final OnlyTitleProcessor processor4 = OnlyTitleProcessor.getProcessor(frLang);

		Assert.assertSame(processor1, processor3);
		Assert.assertSame(processor2, processor4);
		Assert.assertNotSame(processor1, processor2);
	}

	@Test
	public void voiceCacheTest() {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Lang usLang = Factories.LANG.findByIsoCode("en-US");
		Assert.assertNotNull(frLang);
		Assert.assertNotNull(usLang);

		final TtsVoice frVoice = new TtsVoiceMock(0, "my voice fr", "command", frLang, "str", true, false);
		final TtsVoice usVoice = new TtsVoiceMock(0, "my voice us", "command", usLang, "str", true, false);

		final OnlyTitleProcessor processor1 = OnlyTitleProcessor.getProcessor(usVoice);
		final OnlyTitleProcessor processor2 = OnlyTitleProcessor.getProcessor(frVoice);
		final OnlyTitleProcessor processor3 = OnlyTitleProcessor.getProcessor(usVoice);
		final OnlyTitleProcessor processor4 = OnlyTitleProcessor.getProcessor(frVoice);

		Assert.assertSame(processor1, processor3);
		Assert.assertSame(processor2, processor4);
		Assert.assertNotSame(processor1, processor2);
	}

	@Test
	public void bothCachesTest() {
		final Lang frLang = Factories.LANG.findByIsoCode("fr-FR");
		final Lang usLang = Factories.LANG.findByIsoCode("en-US");
		Assert.assertNotNull(frLang);
		Assert.assertNotNull(usLang);

		final TtsVoice frVoice = new TtsVoiceMock(0, "my voice fr", "command", frLang, "str", true, false);
		final TtsVoice usVoice = new TtsVoiceMock(0, "my voice us", "command", usLang, "str", true, false);

		final OnlyTitleProcessor processor1 = OnlyTitleProcessor.getProcessor(usVoice);
		final OnlyTitleProcessor processor2 = OnlyTitleProcessor.getProcessor(frVoice);
		final OnlyTitleProcessor processor3 = OnlyTitleProcessor.getProcessor(usLang);
		final OnlyTitleProcessor processor4 = OnlyTitleProcessor.getProcessor(frLang);

		Assert.assertSame(processor1, processor3);
		Assert.assertSame(processor2, processor4);
		Assert.assertNotSame(processor1, processor2);
	}

}
