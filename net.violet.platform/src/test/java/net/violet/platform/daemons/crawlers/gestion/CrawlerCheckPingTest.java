package net.violet.platform.daemons.crawlers.gestion;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.SgbdConnection;
import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;

import org.junit.Assert;
import org.junit.Test;

public class CrawlerCheckPingTest extends DBTest {

	private static class TestCrawlerCheckPing extends CrawlerCheckPing {

		TestCrawlerCheckPing() {
			super(new String[0]);
		}
	}

	private void checkWithModeAndLastPing(int inMode, long inTimePing, int nbrFinal, int modeFinal) throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection();
		final List<Object> theValues = Arrays.asList(new Object[] { inTimePing, inMode });
		theConnection.doQueryUpdatePS("update object set object_lastping = ?, object_mode = ? where object_id = 31", theValues);

		final TestCrawlerCheckPing thetest = new TestCrawlerCheckPing();

		Assert.assertEquals(nbrFinal, thetest.processCheckPing());

		final int mode = theConnection.doQueryIntV("select object_mode from object where object_id=31");

		Assert.assertEquals(modeFinal, mode);
	}

	@Test
	public void checkPingTest() throws SQLException {
		final long timeout = CCalendar.getCurrentTimeInSecond() - Constantes.TIMEOUT_PING_IN_SEC - 10;
		final long now = CCalendar.getCurrentTimeInSecond();
		checkWithModeAndLastPing(VObject.MODE_PING, now + 900, 0, VObject.MODE_PING);
		checkWithModeAndLastPing(VObject.MODE_PING, now - 900, 0, VObject.MODE_PING);
		// INFO: si ce test plante, il faut vérifier qu'ETH1 est bien activé sur
		// la machine
		checkWithModeAndLastPing(VObject.MODE_PING, timeout, 1, VObject.MODE_PING_INACTIVE);
		checkWithModeAndLastPing(VObject.MODE_XMPP, timeout, 0, VObject.MODE_XMPP);
		checkWithModeAndLastPing(VObject.MODE_XMPP_INACTIVE, timeout, 0, VObject.MODE_XMPP_INACTIVE);
		checkWithModeAndLastPing(VObject.MODE_XMPP_TR, timeout, 0, VObject.MODE_XMPP_TR);
		checkWithModeAndLastPing(VObject.MODE_PING_INACTIVE, now, 0, VObject.MODE_PING_INACTIVE);
	}
}
