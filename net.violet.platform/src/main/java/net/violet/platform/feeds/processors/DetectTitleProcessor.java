package net.violet.platform.feeds.processors;

import java.util.Collections;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.voice.TTSServices;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * This is a very simple EntryProcessor. It only generates one file which is created by giving the title to the
 * TTS engine. The title is synthetized as an audio file which is returned.
 */
public class DetectTitleProcessor implements EntryProcessor {

	private static final DetectTitleProcessor INSTANCE = new DetectTitleProcessor();

	public static DetectTitleProcessor getInstance() {
		return DetectTitleProcessor.INSTANCE;
	}

	protected DetectTitleProcessor() {}

	/**
	 * Detects the language thanks to text, who will be synthetized, sends it to the TTS engine and returned
	 * the generated file.
	 * If an error happend while calling the TTS Engine a ProcessingException is thrown.
	 */
	public ProcessedEntry processEntry(SyndEntry entry) throws ProcessingException {
		final String textToConvert = entry.getTitle();

		if ((textToConvert != null) && (textToConvert.length() > 0)) {
			final TtsVoice theVoice = Factories.TTSVOICE.findByLang(TtsLangData.getIdentifyLanguage(textToConvert).getReference());
			if (theVoice == null) {
				throw new IllegalArgumentException(" This given text is not recognized : " + textToConvert);
			}
			final Files resultFiles = TTSServices.getDefaultInstance().postTTS(textToConvert, true, true, theVoice);
			if (resultFiles != null) {
				return new ProcessedEntry(entry, Collections.singletonList(resultFiles));
			}
			throw new ProcessingException("Failed to synthetize : [" + textToConvert + "] with voice : " + theVoice.getTtsvoice_libelle());
		}

		throw new ProcessingException("Failed to synthetize : [" + textToConvert + "]");
	}
}
