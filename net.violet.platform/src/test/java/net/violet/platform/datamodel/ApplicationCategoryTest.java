package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class ApplicationCategoryTest extends DBTest {

	@Test
	public void findAllTest() {
		final List<ApplicationCategory> theResult = Factories.APPLICATION_CATEGORY.getAllCategories();
		Assert.assertEquals(12, theResult.size());
	}

	@Test
	public void findByNameTest() {
		ApplicationCategory theResult = Factories.APPLICATION_CATEGORY.findByName("LOC_srv_category_news/title");
		Assert.assertNotNull(theResult);
		Assert.assertEquals(new Long(2), theResult.getId());

		theResult = Factories.APPLICATION_CATEGORY.findByName("notExistingCategory");
		Assert.assertNull(theResult);
	}

}
