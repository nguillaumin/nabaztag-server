package net.violet.platform.daemons.crawlers.gestion;

import java.sql.SQLException;
import java.sql.Timestamp;

import net.violet.db.records.SgbdConnection;
import net.violet.platform.datamodel.DBTest;
import net.violet.platform.datamodel.ScheduledMessage;
import net.violet.platform.datamodel.ScheduledMessageImpl;
import net.violet.platform.datamodel.factories.mock.FactoryMock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CrawlerCheckScheduledMessageTest extends DBTest {

	@Before
	public void setUp() {
		System.setProperty("net.violet.platform.datamodel.factories.impl", FactoryMock.class.getName());
	}

	private static class TestCrawlerScheduledMessage extends CrawlerScheduledMessage {

		TestCrawlerScheduledMessage() {
			super(new String[0]);
		}

		@Override
		void processScheduledMessage(ScheduledMessage inScheduledMessage) {
			inScheduledMessage.delete();
		}
	}

	private void checkMessageInFuture(long inTimeMillis, int nbrFinal, boolean isDelete) throws SQLException {
		final SgbdConnection theConnection = new SgbdConnection();

		ScheduledMessageImpl.insert(new Timestamp(inTimeMillis), "<message id=\"message-41\" to=\"0013d3849de1@xmpp.nabaztag.com/idle\" from=\"net.violet.platform@xmpp.nabaztag.com\"><amp xmlns='http://jabber.org/protocol/amp'><rule action='store' condition='match-resource' value='other'/><rule action='notify' condition='deliver' value='direct'/></amp><packet xmlns='violet:packet' format='1.0'>fwoAAEEAxLtPEfny7vv6xsCbhg358u77ZqxIgc08zNCsY0BJqolpskP4ZD2w7vkspIGaCiibNj2wf379k0z5gH/ClYgkRQMAAAF4/w==</packet></message>", (long) 1000);

		final TestCrawlerScheduledMessage thetest = new TestCrawlerScheduledMessage();

		Assert.assertEquals(nbrFinal, thetest.processMessageInFuture());
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {}
		final int count = theConnection.doQueryIntV("select count(id) from scheduled_message where message_id = 1000");

		theConnection.doQueryUpdate("delete from scheduled_message where message_id = 1000");

		Assert.assertEquals(isDelete, count == 0);
	}

	@Test
	public void fixMeTest() {
		Assert.assertTrue(true);
	}

	// @Test TODO FIXME Passer en mock
	public void checkScheduledMessage() throws SQLException {
		final long now = System.currentTimeMillis();
		checkMessageInFuture(now + 900000, 0, false);
		checkMessageInFuture(now - 100000, 1, true);
	}
}
