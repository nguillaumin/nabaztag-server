package net.violet.platform.feeds.processors;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.cache.Cache;
import net.violet.platform.util.cache.GenerationException;
import net.violet.platform.util.cache.ValueGenerator;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * Pretty close from the OnlyTitleProcessor except that the description will be concatened at the end
 * of the title.
 */
public class FullTTSProcessor extends OnlyTitleProcessor {

	// a local cache to avoid creating several processor instances for the same ttsVoice, FullTTSProcessor objects are thread-safe so it's ok.
	private static final Cache<TtsVoice, FullTTSProcessor> CACHE = new Cache<TtsVoice, FullTTSProcessor>(new ValueGenerator<TtsVoice, FullTTSProcessor>() {

		public FullTTSProcessor generateValue(TtsVoice key) {
			return new FullTTSProcessor(key);
		}

	});

	/**
	 * Creates an OnlyTitleProcessor object with the given voice. The provided voice will be used to synthetize the text.
	 * @param voice
	 */
	public static FullTTSProcessor getProcessor(TtsVoice voice) {
		try {
			return FullTTSProcessor.CACHE.get(voice);
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
	public static FullTTSProcessor getProcessor(Lang language) {
		final TtsVoice theVoice = Factories.TTSVOICE.findByLang(language);
		if (theVoice == null) {
			throw new IllegalArgumentException(language == null ? "given language is null" : "no voice for this language : " + language.getIsoCode());
		}

		return FullTTSProcessor.getProcessor(theVoice);
	};

	protected FullTTSProcessor(TtsVoice voice) {
		super(voice);
	}

	/**
	 * Returns the title and the description wrapped in the given entry.
	 * A dot and a space are added at the end of the title.
	 */
	@Override
	protected String getTextToConvert(SyndEntry entry) {
		return super.getTextToConvert(entry) + ". " + entry.getDescription().getValue();
	}

}
