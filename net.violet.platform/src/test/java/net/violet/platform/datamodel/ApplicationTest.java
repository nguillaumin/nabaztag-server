package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class ApplicationTest extends DBTest {

	@Test
	public void categoryTest() {
		final Application theApplication = Factories.APPLICATION.find(1);
		final ApplicationCategory theCategory = Factories.APPLICATION_CATEGORY.find(1);

		Assert.assertNotNull(theApplication);
		Assert.assertNotNull(theCategory);

		Assert.assertEquals(theCategory, theApplication.getCategory());
	}

	@Test
	public void findByCategoryTest() {
		ApplicationCategory theCategory = Factories.APPLICATION_CATEGORY.find(1);
		List<Application> list = Factories.APPLICATION.findByCategory(theCategory, 0, 10);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(new Long(1), list.get(0).getId());

		theCategory = Factories.APPLICATION_CATEGORY.find(3);
		list = Factories.APPLICATION.findByCategory(theCategory, 0, 10);
		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void getTagsTest() {
		final Application application1 = Factories.APPLICATION.find(1);
		Assert.assertNotNull(application1);
		final List<ApplicationTag> tagsList = application1.getTags();
		Assert.assertNotNull(tagsList);
		Assert.assertEquals(2, tagsList.size());

		final Application application2 = Factories.APPLICATION.find(2);
		Assert.assertNotNull(application2);
		final List<ApplicationTag> tagsList2 = application2.getTags();
		Assert.assertNotNull(tagsList2);
		Assert.assertEquals(3, tagsList2.size());
	}

	@Test
	public void getByTag() {
		final ApplicationTag theTag = Factories.APPLICATION_TAG.find(2);
		final List<Application> appliList = Factories.APPLICATION.findAllByTag(theTag);

		Assert.assertNotNull(appliList);
		Assert.assertEquals(2, appliList.size());
	}

	@Test
	public void getLangs() {
		final Application application1 = Factories.APPLICATION.find(1);
		final Lang l1 = Factories.LANG.find(1);
		final Lang l2 = Factories.LANG.find(2);
		final Lang l3 = Factories.LANG.find(3);

		Assert.assertEquals(1, application1.getLangs().size());
		Assert.assertTrue(application1.getLangs().contains(l1));

		application1.getLangs().add(l2);
		Assert.assertEquals(2, application1.getLangs().size());

		Assert.assertFalse(application1.getLangs().contains(l3));
	}

	@Test
	public void createTest() {
		final User theUser = Factories.USER.find(1);
		final Files theFile = Factories.FILES.find(1850108);
		final ApplicationCategory theCateg = Factories.APPLICATION_CATEGORY.find(1);
		final Application theApplication = Factories.APPLICATION.create(theUser, "appliName", Application.ApplicationClass.HOSTED, theCateg, false, false, true);
		final ApplicationProfile theProfile = Factories.APPLICATION_PROFILE.create(theApplication, "appliTitle", "appliDesc", false, theFile, theFile, null, null, null, net.violet.common.StringShop.EMPTY_STRING, "http://www.violet.net");

		Assert.assertNotNull(theApplication);

		Assert.assertEquals(theUser, theApplication.getOwner());
		Assert.assertEquals("appliName", theApplication.getName());
		Assert.assertEquals(Application.ApplicationClass.HOSTED, theApplication.getApplicationClass());
		Assert.assertFalse(theApplication.isInteractive());
		Assert.assertEquals(theCateg, theApplication.getCategory());
		Assert.assertFalse(theApplication.isVisible());
		Assert.assertTrue(theApplication.isRemovable());

		final ApplicationProfile profile = theApplication.getProfile();
		Assert.assertEquals(theProfile.getTitle(), profile.getTitle());
		Assert.assertEquals(theProfile.getDescription(), profile.getDescription());
		Assert.assertEquals(theProfile.isOpenSource(), profile.isOpenSource());

		theProfile.delete();
		theApplication.delete();
	}

}
