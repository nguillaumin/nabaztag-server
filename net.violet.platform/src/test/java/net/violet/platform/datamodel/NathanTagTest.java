package net.violet.platform.datamodel;

import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class NathanTagTest extends DBTest {

	@Test
	public void testRecords() {

		final Map<Integer, List<NathanTag>> allTags = Factories.NATHAN_TAG.findAllTagsSorted();

		Assert.assertEquals(4, allTags.size());

		Assert.assertEquals(3, allTags.get(0).size());
		Assert.assertEquals(4, allTags.get(1).size());
		Assert.assertEquals(4, allTags.get(2).size());
		Assert.assertEquals(3, allTags.get(3).size());

	}

}
