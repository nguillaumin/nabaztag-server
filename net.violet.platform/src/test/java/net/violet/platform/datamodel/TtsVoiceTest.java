package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class TtsVoiceTest extends DBTest {

	@Test
	public void testExistingRecords() {
		final TtsVoice myRecord = Factories.TTSVOICE.find(1);
		Assert.assertEquals(1, myRecord.getTtsvoice_id());
		Assert.assertEquals("AU - Colleen", myRecord.getTtsvoice_libelle());
		Assert.assertEquals(28, myRecord.getTtsvoice_lang());
	}

	@Test
	public void testAllDispoLang() {
		final List<TtsVoice> all = TtsVoiceImpl.getAllVoices();
		Assert.assertEquals(58, all.size());
	}

	@Test
	public void testDefaultLang() {
		Assert.assertEquals(33, TtsVoice.DEFAULT_VOICES.size());
	}
}
