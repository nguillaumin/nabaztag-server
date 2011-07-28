//package net.violet.db.records;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import junit.framework.Assert;
//import net.violet.db.utils.test.DBTest;
//
//import org.junit.Test;
//
//public class ListAssociationTest extends DBTest {
//
//	@Test
//	public void testDeletion() throws SQLException {
//		final User theUser = new UserImpl("123", "test_email", Factories.LANG.find(1), "test_country", "test_firstname", "test_lastname", 0);
//
//		// Ajout d'un intérêt.
//		final List<Lang> theUserlang = theUser.getLangs();
//		final Lang theLang = Factories.LANG.find(2);
//		theUserlang.add(theLang);
//
//		Assert.assertEquals(2, theUserlang.size());
//		theLang.delete();
//		Assert.assertEquals(1, theUserlang.size());
//	}
//}
