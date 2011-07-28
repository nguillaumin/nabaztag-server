package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class ScheduledMessageTest extends DBTest {

	@Test
	public void testInsertingRecords() {
		try {
			ScheduledMessageImpl.insert(new Timestamp(Calendar.getInstance().getTimeInMillis()), "Test", 1000L);
			final ScheduledMessage theMessage = Factories.SCHEDULED_MESSAGE.findByMessageId(1000L);
			Assert.assertEquals("Test", theMessage.getPacket());
			theMessage.delete();
		} catch (final SQLException se) {
			System.out.println("Erreur!!! " + se.getMessage());
		}
	}

}
