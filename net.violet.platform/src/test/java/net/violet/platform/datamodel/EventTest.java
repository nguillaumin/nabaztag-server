package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class EventTest extends DBTest {

	@Test
	public void testDelete() {
		final VObject myRecord = Factories.VOBJECT.find(31);
		try {
			final long event_id = Factories.EVENT.insert(myRecord.getId().intValue(), 100, 100, 100);
			Event theEvent = Factories.EVENT.find(event_id);
			theEvent.delete();
			theEvent = Factories.EVENT.find(event_id);
			Assert.assertNull(theEvent);
		} catch (final SQLException e) {
			e.printStackTrace();
		}

	}

}
