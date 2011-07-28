package net.violet.platform.feeds.processors;

import java.util.Collections;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.cache.Cache;
import net.violet.platform.util.cache.GenerationException;
import net.violet.platform.util.cache.ValueGenerator;
import net.violet.platform.voice.TTSServices;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * This is a very simple EntryProcessor. It only generates one file which is created by giving the title to the
 * TTS engine. The title is synthetized as an audio file which is returned.
 */
public class OnlyTitleProcessor implements EntryProcessor {

	// a local cache to avoid creating several processor instances for the same ttsVoice, OnlyTitleProcessor objects are thread-safe so it's ok.
	private static final Cache<TtsVoice, OnlyTitleProcessor> CACHE = new Cache<TtsVoice, OnlyTitleProcessor>(new ValueGenerator<TtsVoice, OnlyTitleProcessor>() {

		public OnlyTitleProcessor generateValue(TtsVoice key) {
			return new OnlyTitleProcessor(key);
		}

	});

	/**
	 * Creates an OnlyTitleProcessor object with the given voice. The provided voice will be used to synthetize the text.
	 * @param voice
	 */
	public static OnlyTitleProcessor getProcessor(TtsVoice voice) {
		try {
			return OnlyTitleProcessor.CACHE.get(voice);
		} catch (final GenerationException e) {
			// is not supposed to happen, the generator above does not throw any exception.
		}
		// should not happen !
		return null;
	};

	/**
	 * The given language will be used to find the default voice associated to this language. If the provided language is null
	 * or if no voice can be found an IllegalArgumentException is thrown. 
	 * @param language
	 */
	public static OnlyTitleProcessor getProcessor(Lang language) {
		final TtsVoice theVoice = Factories.TTSVOICE.findByLang(language);
		if (theVoice == null) {
			throw new IllegalArgumentException(language == null ? "given language is null" : "no voice for this language : " + language.getIsoCode());
		}

		return OnlyTitleProcessor.getProcessor(theVoice);
	};

	private final TtsVoice voice;
	private TTSServices ttsEngine;

	protected OnlyTitleProcessor(TtsVoice voice) {
		this.voice = voice;
		this.ttsEngine = TTSServices.getDefaultInstance();
	}

	/**
	 * Only used by the tests !
	 * @param engine
	 */
	void setTTSEngine(TTSServices engine) {
		this.ttsEngine = engine;
	}

	/**
	 * Uses the protected getTextToConvert method to retrieve the text to be synthetized, sends it to the TTS engine and returned
	 * the generated file.
	 * If an error happend while calling the TTS Engine a ProcessingException is thrown.
	 */
	public ProcessedEntry processEntry(SyndEntry entry) throws ProcessingException {
		final String textToConvert = getTextToConvert(entry);

		if ((textToConvert != null) && (textToConvert.length() > 0)) {
			final Files resultFiles = this.ttsEngine.postTTS(getTextToConvert(entry), true, true, this.voice);
			if (resultFiles != null) {
				return new ProcessedEntry(entry, Collections.singletonList(resultFiles));
			}
		}

		throw new ProcessingException("Failed to synthetize : [" + textToConvert + "] with voice : " + this.voice.getTtsvoice_libelle());

	}

	/**
	 * Extracts the title from the given entry and returns it. 
	 * @param entry
	 * @return
	 */
	protected String getTextToConvert(SyndEntry entry) {
		return entry.getTitle();
	};
}
