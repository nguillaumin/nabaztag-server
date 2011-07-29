package net.violet.platform.datamodel;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.SQLSpecification;
import net.violet.db.records.SgbdConnection;
import net.violet.db.records.SgbdResult;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test de confirmité du schéma SQL avec les classes du datamodel.
 */
public class SchemaTest extends DBTest {

	private static final Pattern BOOLEAN_REGEX = Pattern.compile("tinyint\\(1\\)");
	private static final Pattern TINYINT_REGEX = Pattern.compile("tinyint\\([0-9]+\\)");
	private static final Pattern TINYINT_UNSIGNED_REGEX = Pattern.compile("tinyint\\([0-9]+\\) unsigned");
	private static final Pattern INT_REGEX = Pattern.compile("int\\([0-9]+\\)");
	private static final Pattern INT_UNSIGNED_REGEX = Pattern.compile("int\\([0-9]+\\) unsigned");
	private static final Pattern VARCHAR_REGEX = Pattern.compile("varchar\\([0-9]+\\)");
	private static final Pattern CHAR_REGEX = Pattern.compile("char\\([0-9]+\\)");
	private static final Pattern LONGTEXT_REGEX = Pattern.compile("longtext");
	private static final Pattern TEXT_REGEX = Pattern.compile("text");
	private static final Pattern DATE_REGEX = Pattern.compile("date");
	private static final Pattern DATETIME_REGEX = Pattern.compile("datetime");
	private static final Pattern TIME_REGEX = Pattern.compile("time");
	private static final Pattern TIMESTAMP_REGEX = Pattern.compile("timestamp");
	private static final Pattern ENUM_REGEX = Pattern.compile("enum\\('.*'\\)");

	private static Class[] NON_CONFORMING_CLASSES_ARRAY = new Class[] { UserImpl.class, ScheduledMessageImpl.class, StoreCityImpl.class, TagTmpSiteImpl.class, AnimImpl.class, MessageCounterImpl.class, LangImpl.class, TtsVoiceImpl.class, CountryImpl.class, ObjectPreferencesImpl.class, PresencesImpl.class };

	private static Set<Class> NON_CONFORMING_CLASSES = new HashSet<Class>(Arrays.asList(SchemaTest.NON_CONFORMING_CLASSES_ARRAY));

	/**
	 * Test à partir des fichiers de tests externes
	 * (probes/api/methods.get_*.test) qui sont dérivés de la spécification.
	 * Vérifie que toutes les méthodes implémentées ont un test.
	 * 
	 * @throws SQLException
	 */

	@Test
	public void schemaTest() throws ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException, SQLException {
		// Itération sur les fichiers source
		SgbdConnection theConnection = null;
		try {
			final File theSourceRootDir = new File("src/main/java/net/violet/platform/datamodel/");
			for (final File theSourceFile : theSourceRootDir.listFiles()) {
				if (theSourceFile.isFile()) {
					final String theSourceFileName = theSourceFile.getName();
					if (theSourceFileName.endsWith("Impl.java")) {
						final int theLen = theSourceFileName.length();
						final String theClassName = "net.violet.platform.datamodel." + theSourceFileName.substring(0, theLen - 5);
						final Class theClass = Class.forName(theClassName);
						Assert.assertTrue(AbstractSQLRecord.class.isAssignableFrom(theClass));
						if (SchemaTest.NON_CONFORMING_CLASSES.contains(theClass)) {
							System.out.println("SKIPPING " + theClass);
						} else {
							final SQLSpecification<AbstractSQLRecord> theSpec;
							try {
								final Field theSpecField = theClass.getDeclaredField("SPECIFICATION");
								theSpec = (SQLSpecification<AbstractSQLRecord>) theSpecField.get(null);
							} catch (final NoSuchFieldException anException) {
								Assert.fail("SPECIFICATION field not present in " + theClass);
								throw anException;
							} catch (final IllegalAccessException anException) {
								Assert.fail("SPECIFICATION field not accessible in " + theClass);
								throw anException;
							}
							theConnection = new SgbdConnection();
							final String[] theColumns = AbstractSQLRecord.getColumns(theClass);
							final String theTableName = theSpec.getTableName();
							final SgbdResult theResult = theConnection.doQuery("show columns from " + theTableName);

							int nbColumns;
							try {
								nbColumns = theColumns.length;
								final ResultSet theResultSet = theResult.getResultSet();
								while (theResultSet.next()) {
									nbColumns--;
									final String theColumnName = (String) theResultSet.getObject(1);
									final String theColumnType = (String) theResultSet.getObject(2);
									final boolean theColumnNull = theResultSet.getObject(3).equals("YES");
									final Field theField;
									try {
										theField = theClass.getDeclaredField(theColumnName);
									} catch (final NoSuchFieldException anException) {
										Assert.fail(theColumnName + " field not present in " + theClass);
										throw anException;
									}
									SchemaTest.assertColumnType(theClass, theField, theColumnType, theColumnNull);
								}
							} finally {
								theResult.close();
							}
							Assert.assertEquals("Invalid number of columns in specification for " + theClass, 0, nbColumns);
						}
					}
				}
			}
		} finally {
			if (theConnection != null) {
				theConnection.close();
			}
		}
	}

	private static void assertColumnType(Class inClass, Field inField, String inSQLColumnType, boolean inSQLColumnNull) {
		final Class<?> theType = inField.getType();
		final String theErrorStr = inClass + " field " + inField.getName() + " (java type: " + theType + ", sql type: " + inSQLColumnType + " " + (inSQLColumnNull ? "NULL" : "NOT NULL") + ")";

		if (SchemaTest.INT_REGEX.matcher(inSQLColumnType).matches()) {
			if (inSQLColumnNull) {
				Assert.assertEquals(theErrorStr, Integer.class, theType);
			} else {
				Assert.assertEquals(theErrorStr, int.class, theType);
			}
		} else if (SchemaTest.INT_UNSIGNED_REGEX.matcher(inSQLColumnType).matches()) {
			if (inSQLColumnNull) {
				Assert.assertEquals(theErrorStr, Long.class, theType);
			} else {
				Assert.assertEquals(theErrorStr, long.class, theType);
			}
		} else if (SchemaTest.BOOLEAN_REGEX.matcher(inSQLColumnType).matches()) {
			if (inSQLColumnNull) {
				Assert.assertEquals(theErrorStr, Boolean.class, theType);
			} else {
				Assert.assertEquals(theErrorStr, boolean.class, theType);
			}
		} else if (SchemaTest.TINYINT_REGEX.matcher(inSQLColumnType).matches()) {
			if (inSQLColumnNull) {
				Assert.assertEquals(theErrorStr, Byte.class, theType);
			} else {
				Assert.assertEquals(theErrorStr, byte.class, theType);
			}
		} else if (SchemaTest.TINYINT_UNSIGNED_REGEX.matcher(inSQLColumnType).matches()) {
			if (inSQLColumnNull) {
				Assert.assertEquals(theErrorStr, Short.class, theType);
			} else {
				Assert.assertEquals(theErrorStr, short.class, theType);
			}
		} else if (SchemaTest.VARCHAR_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, String.class, theType);
		} else if (SchemaTest.CHAR_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, String.class, theType);
		} else if (SchemaTest.LONGTEXT_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, String.class, theType);
		} else if (SchemaTest.TEXT_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, String.class, theType);
		} else if (SchemaTest.DATE_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, java.sql.Date.class, theType);
		} else if (SchemaTest.DATETIME_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, java.util.Date.class, theType);
		} else if (SchemaTest.TIME_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, Time.class, theType);
		} else if (SchemaTest.TIMESTAMP_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, Timestamp.class, theType);
		} else if (SchemaTest.ENUM_REGEX.matcher(inSQLColumnType).matches()) {
			Assert.assertEquals(theErrorStr, String.class, theType);
		} else {
			//Assert.fail("Unknown SQL type " + inSQLColumnType + " for " + theErrorStr + " (java type: " + theType + ")");
		}
	}
}
