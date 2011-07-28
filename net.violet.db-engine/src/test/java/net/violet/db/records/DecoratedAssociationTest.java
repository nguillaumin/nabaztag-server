//package net.violet.db.records;
//
//import java.sql.SQLException;
//import java.util.Map;
//
//import junit.framework.Assert;
//import net.violet.common.StringShop;
//import net.violet.db.utils.test.DBTest;
//
//import org.junit.Test;
//
//public class DecoratedAssociationTest extends DBTest {
//
//	@Test
//	public void testDeletion() throws SQLException {
//		// On crée deux users.
//		final Lang theLang = Factories.LANG.find(1);
//		final User theUser1 = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//		final User theUser2 = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//
//		final Map<User, Black> theBlacked = theUser1.getBlackList();
//		Assert.assertEquals(0, theBlacked.size());
//		final Black theBlackRel1 = new BlackImpl(StringShop.EMPTY_STRING);
//		theBlacked.put(theUser2, theBlackRel1);
//		Assert.assertEquals(1, theBlacked.size());
//		theBlackRel1.delete();
//		Assert.assertEquals(0, theBlacked.size());
//		theBlacked.put(theUser2, new BlackImpl(StringShop.EMPTY_STRING));
//		Assert.assertEquals(1, theBlacked.size());
//		theUser2.delete();
//		Assert.assertEquals(0, theBlacked.size());
//	}
//
//	// Le remplacement ne fonctionne pas.
//	@Test
//	public void testReplacement() throws SQLException {
//		// On crée deux users.
//		final Lang theLang = Factories.LANG.find(1);
//		final User theUser3 = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//		final User theUser4 = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//
//		final Map<User, Black> theBlacked = theUser3.getBlackList();
//		Assert.assertEquals(0, theBlacked.size());
//		final Black theBlackRel1 = new BlackImpl("1");
//		final Black theBlackRel2 = new BlackImpl("2");
//		theBlacked.put(theUser4, theBlackRel1);
//		Assert.assertEquals(theUser3.getId(), theBlackRel1.getId());
//		Assert.assertEquals(1, theBlacked.size());
//		Assert.assertEquals(theBlackRel1, theBlacked.get(theUser4));
//		boolean gotException = false;
//		try {
//			theBlacked.put(theUser4, theBlackRel2);
//		} catch (final RuntimeException anException) {
//			gotException = true;
//		}
//		Assert.assertTrue(gotException);
//	}
//
//	@Test
//	public void testUpdate() throws SQLException {
//		final SgbdConnection theConnection = new SgbdConnection();
//		// On crée deux users.
//		final Lang theLang = Factories.LANG.find(1);
//		final User theUser5 = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//		final User theUser6 = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//
//		final Map<User, Black> theBlacked = theUser5.getBlackList();
//		Assert.assertEquals(0, theBlacked.size());
//		final Black theBlackRel1 = new BlackImpl("therel1");
//		theBlacked.put(theUser6, theBlackRel1);
//		Assert.assertEquals("therel1", theBlackRel1.getComment());
//		String theCommentFromSQL;
//		theCommentFromSQL = theConnection.doQueryString("SELECT black_comment FROM black where black_user = " + theUser5.getId());
//		Assert.assertEquals("therel1", theCommentFromSQL);
//		theBlackRel1.setComment("therel2");
//		Assert.assertEquals("therel2", theBlackRel1.getComment());
//		theCommentFromSQL = theConnection.doQueryString("SELECT black_comment FROM black where black_user = " + theUser5.getId());
//		Assert.assertEquals("therel2", theCommentFromSQL);
//		theConnection.close();
//	}
//
//	@Test
//	public void testCreate() throws SQLException {
//		final SgbdConnection theConnection = new SgbdConnection();
//		// On crée deux users.
//		final Lang theLang = Factories.LANG.find(1);
//		final User theUser7 = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//		final User theUser8 = new UserImpl("123", "test_email", theLang, "test_country", "test_firstname", "test_lastname", 0);
//
//		final Map<User, Black> theBlacked = theUser7.getBlackList();
//		Assert.assertEquals(0, theBlacked.size());
//		final Black theBlackRel1 = new BlackImpl("therel1");
//		theBlacked.put(theUser8, theBlackRel1);
//		Assert.assertEquals("therel1", theBlackRel1.getComment());
//		int theIdFromSQL;
//		theIdFromSQL = theConnection.doQueryIntV("SELECT black_blacked FROM black where black_user = " + theUser7.getId());
//		Assert.assertEquals(theUser8.getId().intValue(), theIdFromSQL);
//		String theCommentFromSQL;
//		theCommentFromSQL = theConnection.doQueryString("SELECT black_comment FROM black where black_user = " + theUser7.getId());
//		Assert.assertEquals("therel1", theCommentFromSQL);
//		theConnection.close();
//	}
//}
