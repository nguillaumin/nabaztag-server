package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.SgbdConnection;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest extends DBTest {

	private static final String TEST_MESSAGE = "testMessage";

	@Test
	public void insertMessage() {
		final SgbdConnection myConnection = new SgbdConnection();
		try {
			final Files myFile = new FilesImpl("broadcast/broad/" + MessageTest.TEST_MESSAGE, MimeType.MIME_TYPES.A_MPEG);
			final Message myMessage = new MessageImpl(myFile, MessageTest.TEST_MESSAGE, new CCalendar(false), 0, Palette.VIOLET);
			Assert.assertNotNull(Factories.MESSAGE.find(myMessage.getId()));

			Assert.assertEquals(MessageTest.TEST_MESSAGE, myConnection.doQueryString("SELECT text From message where body = " + myFile.getId()));
		} catch (final SQLException e) {
			Assert.assertTrue(false);
		}

		try {
			final Files myFile = new FilesImpl("broadcast/broad/" + MessageTest.TEST_MESSAGE, MimeType.MIME_TYPES.A_MPEG);
			final Message myMessage = new MessageImpl(myFile, StringShop.EMPTY_STRING, new CCalendar(false), 0, Palette.FLASH);

			Assert.assertTrue(myMessage.getId() != 0);
			myMessage.setAllInformation(MessageTest.TEST_MESSAGE, new Integer(1), myFile, 1, new Long(1), new Long(1), new CCalendar(false));

			Assert.assertNotNull(Factories.MESSAGE.find(myMessage.getId()));

			Assert.assertEquals(MessageTest.TEST_MESSAGE, myConnection.doQueryString("SELECT text From message where body = " + myFile.getId()));
		} catch (final SQLException e) {
			Assert.assertTrue(false);
		} finally {
			myConnection.close();
		}

	}

	@Test
	public void insertMessagefailing() {

		// If we do not provide a file it should tell us that we were supposed
		// to
		try {
			new MessageImpl(null, MessageTest.TEST_MESSAGE, new CCalendar(false), 0, Palette.VIOLET);
		} catch (final IllegalArgumentException e) {
			Assert.assertTrue(true);
		} catch (final SQLException e) {
			Assert.assertTrue(false);
		}

		// We provide a file with no id => argument is ok but SQL should fail
		// try{
		// new MessageImpl(new FilesImpl(), TEST_MESSAGE, new CCalendar(false),
		// 0, new Long(1));
		// }catch (IllegalArgumentException e){
		// assertTrue(false);
		// }catch (SQLException e){
		// assertTrue(true);
		// }
	}

	@Test
	public void findId1() {
		final Message myMessage = Factories.MESSAGE.find(1);

		Assert.assertNotNull(myMessage);
		Assert.assertEquals("testMessage", myMessage.getText());
		final Files myFile = myMessage.getBody();
		Assert.assertEquals("/testMessage/", myFile.getPath());
	}

	@Test
	public void findId0() {
		final Message myMessage = Factories.MESSAGE.find(1154);
		Assert.assertTrue(myMessage == null);
	}
}
