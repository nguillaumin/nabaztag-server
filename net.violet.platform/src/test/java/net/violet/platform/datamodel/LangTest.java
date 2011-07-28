package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class LangTest extends DBTest {

	@Test
	public void testExistingRecords() {
		final Lang myLang2 = Factories.LANG.find(2);
		Assert.assertEquals("English (US)", myLang2.getTitle());
		Assert.assertEquals("en-US", myLang2.getIsoCode());
		final Lang myLang3 = Factories.LANG.find(3);
		Assert.assertEquals("English (UK)", myLang3.getTitle());
		Assert.assertEquals("en-GB", myLang3.getIsoCode());
	}

	@Test
	public void testGetAllSiteLanguages() {

		final List<Lang> theList = Factories.LANG.getAllSiteLanguages();
		Assert.assertEquals(theList.size(), 6);
		Assert.assertEquals(theList.get(0).getIsoCode(), "fr");
		Assert.assertEquals(theList.get(5).getIsoCode(), "pt");

	}

	@Test
	public void testGetAllASRLanguages() {

		final List<Lang> theList = Factories.LANG.getAllASRLanguages();
		Assert.assertEquals(theList.size(), 5);
		Assert.assertEquals(theList.get(0).getIsoCode(), "fr-FR");
		Assert.assertEquals(theList.get(4).getIsoCode(), "es-ES");

	}

	@Test
	public void testGetAllTTSLanguages() {

		final List<Lang> theList = Factories.LANG.getAllTTSLanguages();
		Assert.assertTrue(!theList.isEmpty());
		Assert.assertEquals(theList.get(0).getIsoCode(), "fr-FR");
		Assert.assertEquals(theList.get(4).getIsoCode(), "es-ES");
		Assert.assertEquals(theList.get(5).getIsoCode(), "it-IT");

	}

	@Test
	public void testGetAllObjectLanguages() {

		final List<Lang> theList = Factories.LANG.getAllObjectLanguages();
		Assert.assertEquals(theList.size(), 7);
		Assert.assertEquals(theList.get(0).getIsoCode(), "fr-FR");
		Assert.assertEquals(theList.get(4).getIsoCode(), "es-ES");
		Assert.assertEquals(theList.get(5).getIsoCode(), "it-IT");
		Assert.assertEquals(theList.get(6).getIsoCode(), "pt-BR");

	}
}
