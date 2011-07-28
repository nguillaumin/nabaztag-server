package net.violet.platform.datamodel.factories.implementations;

import junit.framework.Assert;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.Factories;

import org.junit.Test;

public class ContentFactoryImplTest extends DBTest {

	@Test
	public void findOldestByActionTest() {
		final VAction theAction = Factories.VACTION.find(1);
		final Content theContent = Factories.CONTENT.findOldestByAction(theAction, 2);
		final Content content = Factories.CONTENT.find(21582847);
		Assert.assertEquals(content, theContent);
	}

	@Test
	public void findOldestByActionOutOfBoundTest() {
		final VAction theAction = Factories.VACTION.find(1);
		final Content theContent = Factories.CONTENT.findOldestByAction(theAction, 10);
		final Content content = Factories.CONTENT.find(17973955);
		Assert.assertEquals(content, theContent);
	}

}
