package net.violet.platform.dataobjects;

import java.util.List;

import junit.framework.Assert;
import net.violet.platform.datamodel.MockTestBase;

import org.junit.Test;

public class AbstractLangDataTest extends MockTestBase {

	@Test
	public void findAllByLanguageCodeTest() {

		List<TtsLangData> theLangs = AbstractLangData.getAllByLanguageCode("fr", TtsLangData.getAllTtsLanguages());
		Assert.assertEquals(3, theLangs.size());
		Assert.assertTrue(theLangs.contains(TtsLangData.findByISOCode("fr-FR")));
		Assert.assertTrue(theLangs.contains(TtsLangData.findByISOCode("fr-CA")));
		Assert.assertTrue(theLangs.contains(TtsLangData.findByISOCode("fr-BE")));

		theLangs = AbstractLangData.getAllByLanguageCode("sv-se", TtsLangData.getAllTtsLanguages());
		Assert.assertEquals(1, theLangs.size());
		Assert.assertTrue(theLangs.contains(TtsLangData.findByISOCode("sv-SE")));

		theLangs = AbstractLangData.getAllByLanguageCode("sv-RE", TtsLangData.getAllTtsLanguages());
		Assert.assertTrue(theLangs.isEmpty());

	}
}
