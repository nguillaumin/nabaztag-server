package net.violet.platform.voice;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LanguageIdentificationTest extends MockTestBase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testIdentification() {
		final String frLang = "fr";
		final String enLang = "en";

		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("j'ai une chanson toute pourrite dans la tête, qui parle d'une route, je me demande d'où ça vient...."));
		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("DONNE MOI MES TWEET SALE GOOGLE TALK"));
		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("j'ai plus les alertes twitter sur gtalk, la vie devient soudainement insuportable..."));
		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("des pommes. des poires. et des scoubidous bidous bidous ah."));
		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("la la la la la la.... suuuuur la routeuhhh la la la la la"));
		Assert.assertEquals(enLang, LanguageIdentification.identifyLanguage("less than 10 minutes to reach the office.... supervelib' can do that. go go go go !"));
		Assert.assertEquals(enLang, LanguageIdentification.identifyLanguage("late again"));
		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("jvais me coucher a 23h00, promis juré"));
		Assert.assertEquals(enLang, LanguageIdentification.identifyLanguage("Safari invents : random weird nasty fucking javascript bugs"));
		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("je vais m'étendre sur l'asphalte"));
		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("pas de velib : hyper a la bourre..."));
		Assert.assertEquals(frLang, LanguageIdentification.identifyLanguage("je crois que je suis a la bourre..."));
	}
}
