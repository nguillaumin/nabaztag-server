package net.violet.platform.daemons.crawlers.processor.TTSGenerator;

import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.common.utils.concurrent.units.AbstractProcessUnit;
import net.violet.platform.daemons.crawlers.processor.JobProcessor;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.concurrent.units.TTSProcessUnit;
import net.violet.platform.voice.TTSFilter;
import net.violet.platform.voice.TTSServices;

import org.apache.log4j.Logger;

public class TTSJobProcessor extends JobProcessor<TTSProcessUnit, Worker<TTSProcessUnit>> {

	private static final Logger LOGGER = Logger.getLogger(TTSJobProcessor.class);

	public TTSJobProcessor(int inNbProcessingThreads, String inService) {
		super(inNbProcessingThreads, inService, new Worker<TTSProcessUnit>() {

			public void process(TTSProcessUnit inUnit) {
				TTSJobProcessor.processTTSUnit(inUnit);
			}
		});
	}

	/**
	 * Returns the text to send to the TTS given a {@link AbstractProcessUnit}.
	 * This method is to be overloaded when needed.
	 * 
	 * @param inParam
	 * @return
	 */
	protected static String getTextToGenerate(AbstractProcessUnit inParam) {
		return TTSFilter.filterNewsTitle((String) inParam.get());
	}

	private static void processTTSUnit(TTSProcessUnit inUnit) {
		try {
			inUnit.processing();
			final String newsTitle = TTSJobProcessor.getTextToGenerate(inUnit);
			TTSJobProcessor.LOGGER.info("Proccessing : " + newsTitle + " Link = " + inUnit.getLink());
			final Lang theLang = inUnit.getProcessConditioner();

			// IF there is text to be given to the TTS generator
			if (null != newsTitle) {
				final TtsVoice theTtsVoice = Factories.TTSVOICE.findByLang(theLang);
				final Files theFile = TTSServices.getDefaultInstance().postTTS(newsTitle, true, true, theTtsVoice);

				// IF we do not get any URL back then it means something went wrong
				if (null == theFile) {
					inUnit.setError();
					TTSJobProcessor.LOGGER.fatal("ERROR while generating TTS : " + newsTitle + " with voice : " + theTtsVoice.getTtsvoice_str());
				} else {
					inUnit.setResult(theFile);
				}
			}
		} catch (final Exception e) {
			inUnit.setError();
			TTSJobProcessor.LOGGER.fatal(e, e);
		} finally {
			inUnit.stopWatching();
			if (!inUnit.isError()) {
				inUnit.processed();
			}
		}
	}

	@Override
	protected void runWhenAddingElement(TTSProcessUnit inUnit) {
		// Nothing to do here
	}
}
