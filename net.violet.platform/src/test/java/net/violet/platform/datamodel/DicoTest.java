package net.violet.platform.datamodel;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class DicoTest extends DBTest {

	@Test
	public void testExistingRecords() {
		final Dico myRecord = Factories.DICO.find(1);
		Assert.assertEquals("source_meteo/AFGHANISTAN_KABOUL", myRecord.getDico_key());
		Assert.assertEquals("AFGHANISTAN.Kaboul", myRecord.getDico_text());
		Assert.assertEquals("source_meteo", myRecord.getDico_page());
	}
}
