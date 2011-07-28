package net.violet.platform.feeds.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class FullTTSProcessorTest extends MockTestBase {

	@Test
	public void getTextProcessorTest() throws ProcessingException {
		final FullTTSProcessor processor = FullTTSProcessor.getProcessor((TtsVoice) null);

		final AtomicBoolean isOk = new AtomicBoolean(false);
		final AtomicReference<String> message = new AtomicReference<String>();
		final List<Files> expectedContent = new ArrayList<Files>();

		processor.setTTSEngine(new TTSServices(null) {

			@Override
			public Files postTTS(String text, @SuppressWarnings("unused") boolean chor, @SuppressWarnings("unused") boolean adp, @SuppressWarnings("unused") TtsVoice inVoice) throws IllegalArgumentException {
				if (text.equals("my title. a nice description!")) {
					isOk.set(true);
				} else {
					message.set("value was : [" + text + "]");
				}

				final Files f = new FilesMock("file", MIME_TYPES.A_MPEG);
				expectedContent.add(f);
				return f;
			}
		});

		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle("my title");
		final SyndContent content = new SyndContentImpl();
		content.setValue("a nice description!");
		entry.setDescription(content);

		final ProcessedEntry procEntry = processor.processEntry(entry);

		if (isOk.get()) {
			Assert.assertTrue(isOk.get());
		} else {
			Assert.fail(message.get());
		}

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

		final FullTTSProcessor processor1 = FullTTSProcessor.getProcessor(usLang);
		final FullTTSProcessor processor2 = FullTTSProcessor.getProcessor(frLang);
		final FullTTSProcessor processor3 = FullTTSProcessor.getProcessor(usLang);
		final FullTTSProcessor processor4 = FullTTSProcessor.getProcessor(frLang);

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

		final FullTTSProcessor processor1 = FullTTSProcessor.getProcessor(usVoice);
		final FullTTSProcessor processor2 = FullTTSProcessor.getProcessor(frVoice);
		final FullTTSProcessor processor3 = FullTTSProcessor.getProcessor(usVoice);
		final FullTTSProcessor processor4 = FullTTSProcessor.getProcessor(frVoice);

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

		final FullTTSProcessor processor1 = FullTTSProcessor.getProcessor(usVoice);
		final FullTTSProcessor processor2 = FullTTSProcessor.getProcessor(frVoice);
		final FullTTSProcessor processor3 = FullTTSProcessor.getProcessor(usLang);
		final FullTTSProcessor processor4 = FullTTSProcessor.getProcessor(frLang);

		Assert.assertSame(processor1, processor3);
		Assert.assertSame(processor2, processor4);
		Assert.assertNotSame(processor1, processor2);
	}

}
