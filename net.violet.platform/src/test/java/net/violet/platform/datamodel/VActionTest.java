package net.violet.platform.datamodel;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class VActionTest extends DBTest {

	@Test
	public void testFindByUrl() {
		final VAction action = Factories.VACTION.find(1);
		final VAction actionsList = Factories.VACTION.findByUrl("http://www.lesinrocks.com/xml/rss/podcast.xml");

		Assert.assertNotNull(actionsList);
		Assert.assertTrue(action.getUrl().equals(actionsList.getUrl()));
	}

}
