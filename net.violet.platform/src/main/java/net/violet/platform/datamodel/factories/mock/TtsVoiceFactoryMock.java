package net.violet.platform.datamodel.factories.mock;

import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.TtsVoice.TtsVoiceCommon;
import net.violet.platform.datamodel.factories.TtsVoiceFactory;
import net.violet.platform.datamodel.mock.TtsVoiceMock;

public class TtsVoiceFactoryMock extends RecordFactoryMock<TtsVoice, TtsVoiceMock> implements TtsVoiceFactory {

	TtsVoiceFactoryMock() {
		super(TtsVoiceMock.class);
	}

	public TtsVoice findByCommandOrName(String inVoiceName, long inDefaultLangID) {
		TtsVoice voice = TtsVoiceCommon.findByName(inVoiceName);
		if (voice == null) {
			voice = TtsVoiceCommon.findByCommand(inVoiceName);
		}
		if (voice == null) {
			voice = TtsVoiceCommon.getDefaultVoice(inDefaultLangID);
		}
		return voice;
	}

	public TtsVoice findByCommandOrName(String inVoiceName) {
		TtsVoice voice = TtsVoiceCommon.findByName(inVoiceName);
		if (voice == null) {
			voice = TtsVoiceCommon.findByCommand(inVoiceName);
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
