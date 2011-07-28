package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.TtsVoiceImpl;
import net.violet.platform.datamodel.TtsVoice.TtsVoiceCommon;
import net.violet.platform.datamodel.factories.TtsVoiceFactory;

public class TtsVoiceFactoryImpl extends RecordFactoryImpl<TtsVoice, TtsVoiceImpl> implements TtsVoiceFactory {

	public TtsVoiceFactoryImpl() {
		super(TtsVoiceImpl.SPECIFICATION);
	}

	public List<TtsVoice> findAll() {
		return findAll("1=1", Arrays.asList(new Object[] {}), "ttsvoice_lang ASC");
	}

	public TtsVoice findByCommandOrName(String voice, long language) {
		TtsVoice theResult;
		theResult = TtsVoiceCommon.findByName(voice);
		if (theResult == null) {
			theResult = TtsVoiceCommon.findByCommand(voice);
		}
		if (theResult == null) {
			theResult = TtsVoiceCommon.getDefaultVoice(language);
		}
		return theResult;
	}

	public TtsVoice findByCommandOrName(String inVoiceLibOrCommandName) {

		TtsVoice voice = TtsVoiceCommon.findByName(inVoiceLibOrCommandName);
		if (voice == null) {
			voice = TtsVoiceCommon.findByCommand(inVoiceLibOrCommandName);
		}

		return voice;
	}

	public TtsVoice findByName(String inVoice) {
		return TtsVoiceCommon.findByName(inVoice);
	}

	public TtsVoice findByLang(Lang inLang) {
		return findByLang(inLang.getId());
	}

	public TtsVoice findByLang(long langId) {
		return TtsVoiceCommon.getDefaultVoice(langId);
	}

	public List<TtsVoice> findAllByLang(Lang inLang) {
		return TtsVoiceCommon.getAllByLang(inLang);
	}
}
