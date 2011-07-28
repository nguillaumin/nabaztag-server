//package net.violet.db.records;
//
//import java.sql.SQLException;
//
//import junit.framework.Assert;
//import net.violet.db.utils.test.DBTest;
//
//import org.junit.Test;
//
//public class SingleAssociationTest extends DBTest {
//
//	@Test
//	public void testAccess() throws SQLException {
//		final User theUser = new UserImpl("123", "test_email", Factories.LANG.find(1), "test_country", "test_firstname", "test_lastname", 0);
//
//		Assert.assertEquals(Factories.LANG.find(1), theUser.getAnnu().getLangPreferences());
//	}
//
//	@Test
//	public void testSet() throws SQLException {
//		final Lang theLang = Factories.LANG.find(2046);
//		final Lang theGeneralLang = Factories.LANG.find(2048);
//		Assert.assertNotNull(theLang);
//		Assert.assertNotNull(theGeneralLang);
//		final User theUser = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//		Assert.assertEquals(theGeneralLang, theUser.getAnnu().getLangPreferences());
//		theUser.setLang(Factories.LANG.find(1));
//		Assert.assertEquals(Factories.LANG.find(1), theUser.getAnnu().getLangPreferences());
//	}
//}
