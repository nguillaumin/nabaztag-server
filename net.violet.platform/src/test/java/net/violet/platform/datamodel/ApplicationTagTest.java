package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class ApplicationTagTest extends DBTest {

	@Test
	public void findByLanguageTest() {
		Lang theLang = Factories.LANG.find(1);
		List<ApplicationTag> theList = Factories.APPLICATION_TAG.findAllByLanguage(theLang, 0, 10);

		Assert.assertNotNull(theList);
		Assert.assertEquals(3, theList.size());

		Assert.assertEquals("tagTrois", theList.get(0).getName());
		Assert.assertEquals("tagUn", theList.get(1).getName());
		Assert.assertEquals("tagDeux", theList.get(2).getName());

		theLang = Factories.LANG.find(2);
		theList = Factories.APPLICATION_TAG.findAllByLanguage(theLang, 0, 10);

		Assert.assertNotNull(theList);
		Assert.assertEquals(2, theList.size());

		theLang = Factories.LANG.find(3);
		theList = Factories.APPLICATION_TAG.findAllByLanguage(theLang, 0, 10);

		Assert.assertNotNull(theList);
		Assert.assertEquals(0, theList.size());
	}

	@Test
	public void getTagTest() {
		Lang theLang = Factories.LANG.find(1);
		ApplicationTag theTag = Factories.APPLICATION_TAG.getTag("tagUn", theLang);

		Assert.assertNotNull(theTag);
		Assert.assertEquals(new Long(1), theTag.getId());

		theLang = Factories.LANG.find(3);
		theTag = Factories.APPLICATION_TAG.getTag("tagUn", theLang);

		Assert.assertNull(theTag);
	}

	@Test
	public void createTagTest() {
		final Lang theLang = Factories.LANG.find(1);
		final ApplicationTag theTag = Factories.APPLICATION_TAG.createTag("newTag", theLang);
		Assert.assertNotNull(theTag);
		Assert.assertEquals("newTag", theTag.getName());
		final ApplicationTag otherTag = Factories.APPLICATION_TAG.createTag("newTag", theLang);
		Assert.assertEquals(theTag, otherTag);
	}

	@Test
	public void addRemoveTagTest() {
		final Lang theLang = Factories.LANG.find(1);
		final Application theApplication = Factories.APPLICATION.find(1);
		final Application otherApplication = Factories.APPLICATION.find(2);
		final ApplicationTag theTag = Factories.APPLICATION_TAG.createTag("newTag", theLang);

		Factories.APPLICATION_TAG.addTagToApplication(theApplication, theTag);

		Assert.assertEquals(50, theTag.getSize());
		Assert.assertTrue(theApplication.getTags().contains(theTag));

		Factories.APPLICATION_TAG.addTagToApplication(otherApplication, theTag);
		Assert.assertEquals(100, theTag.getSize());

		Factories.APPLICATION_TAG.removeTagFromApplication(theApplication, theTag);
		Assert.assertEquals(50, theTag.getSize());
	}

}
