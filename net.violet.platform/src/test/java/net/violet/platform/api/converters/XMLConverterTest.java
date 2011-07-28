package net.violet.platform.api.converters;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SafeBase64;

import org.junit.Assert;
import org.junit.Test;

public class XMLConverterTest extends MockTestBase {

	@Test
	public void testConvertFromXML() {

		final String data = "I'll be back.";
		final String base64Data = SafeBase64.encode(data.getBytes());

		final String content = "<map><to>\"Chuck Norris\"</to><from><string>Jack Bauer</string></from><date>2008-01-20T08:00:00-0000</date>" + "<places><list><item>\"Los Angeles\"</item><item><string>Las Vegas</string></item></list></places><info><data>" + base64Data + "</data></info></map>";

		final Map<String, Object> resultMap;
		try {
			resultMap = ConverterFactory.XML.convertFrom(content);
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail("Parse Exception !");
			return;
		}

		Assert.assertTrue(resultMap.containsKey("to"));
		Assert.assertEquals("Chuck Norris", resultMap.get("to"));

		Assert.assertTrue(resultMap.containsKey("from"));
		Assert.assertEquals("Jack Bauer", resultMap.get("from"));

		final Date testDate = CCalendar.parseISODate("2008-01-20T08:00:00 UTC").getTime();
		Assert.assertTrue(resultMap.containsKey("date"));
		Assert.assertEquals(testDate, resultMap.get("date"));

		Assert.assertTrue(resultMap.containsKey("places"));
		final List<Object> locList = (List<Object>) resultMap.get("places");

		Assert.assertEquals(2, locList.size());
		Assert.assertTrue(locList.contains("Los Angeles"));
		Assert.assertTrue(locList.contains("Las Vegas"));

		Assert.assertTrue(resultMap.containsKey("info"));
		final byte[] bytesArray = (byte[]) resultMap.get("info");
		Assert.assertEquals(data, new String(bytesArray));
	}

	@Test
	public void testConvertToXML() {

		// Careful : \r\n are added by the parser for windows compatibility
		final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<map>\r\n  <account>1205.12</account>" + "\r\n  <activated>true</activated>\r\n  <age>30</age>\r\n  <date>2008-01-20T08:00:00+0000</date>" + "\r\n  <info>\r\n    <data>ZGF0YSBjb250ZW50</data>\r\n  </info>" + "\r\n  <options>null</options>\r\n  <user>\r\n    <string>John Doe</string>\r\n  </user>\r\n  <weapons>" + "\r\n    <list>\r\n      <item>\r\n        <string>gun</string>\r\n      </item>\r\n      <item>" + "\r\n        <string>knife</string>\r\n      </item>\r\n    </list>\r\n  </weapons>\r\n</map>\r\n\r\n";

		final Map<String, Object> theMap = new TreeMap<String, Object>();

		theMap.put("user", "John Doe");
		theMap.put("date", CCalendar.parseISODate("2008-01-20T08:00:00 UTC").getTime());
		theMap.put("activated", true);
		theMap.put("options", null);
		theMap.put("age", 30);
		theMap.put("account", 1205.12);

		final Object[] array = { "gun", "knife" };
		theMap.put("weapons", Arrays.asList(array));

		final String dataString = "data content";
		theMap.put("info", dataString.getBytes());

		String result;
		try {
			result = ConverterFactory.XML.convertTo(theMap, true);
		} catch (final ConversionException e) {
			e.printStackTrace();
			Assert.fail("Parse Exception !");
			return;
		}

		Assert.assertEquals(expected, result);

	}

	@Test
	public void testCompatibility() {

		// Builds the initial POJO
		final Map<String, Object> theMap = new TreeMap<String, Object>();

		theMap.put("user", "John Doe");
		theMap.put("date", CCalendar.parseISODate("2008-01-20T08:00:00 UTC").getTime());
		theMap.put("activated", true);
		theMap.put("options", null);
		theMap.put("age", 30);
		theMap.put("account", 1205.12);

		final Object[] array = { "gun", "knife" };
		theMap.put("weapons", Arrays.asList(array));

		final String dataString = "data content";
		theMap.put("info", dataString.getBytes());

		// Converts the POJO in XML
		String result;
		try {
			result = ConverterFactory.XML.convertTo(theMap, true);
		} catch (final ConversionException e) {
			e.printStackTrace();
			Assert.fail("Parse Exception !");
			return;
		}

		// Creates POJO from XML
		final Map<String, Object> resultMap;
		try {
			resultMap = ConverterFactory.XML.convertFrom(result);
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail("Parse Exception !");
			return;
		}

		Assert.assertTrue(resultMap.containsKey("user"));
		Assert.assertEquals("John Doe", resultMap.get("user"));

		final Date testDate = CCalendar.parseISODate("2008-01-20T08:00:00 UTC").getTime();
		Assert.assertTrue(resultMap.containsKey("date"));
		Assert.assertEquals(testDate, resultMap.get("date"));

		Assert.assertTrue(resultMap.containsKey("activated"));
		Assert.assertEquals(true, resultMap.get("activated"));

		Assert.assertTrue(resultMap.containsKey("options"));
		Assert.assertNull(resultMap.get("options"));

		Assert.assertTrue(resultMap.containsKey("age"));
		Assert.assertEquals(30, resultMap.get("age"));

		Assert.assertTrue(resultMap.containsKey("account"));
		Assert.assertEquals(1205.12, resultMap.get("account"));

		Assert.assertTrue(resultMap.containsKey("weapons"));
		final List<Object> locList = (List<Object>) resultMap.get("weapons");

		Assert.assertEquals(2, locList.size());
		Assert.assertTrue(locList.contains("gun"));
		Assert.assertTrue(locList.contains("knife"));

		Assert.assertTrue(resultMap.containsKey("info"));
		final byte[] bytesArray = (byte[]) resultMap.get("info");
		Assert.assertEquals(dataString, new String(bytesArray));

	}

	public void testWithHeader() {
		final String expected = "<map>\r\n  <account>1205.12</account>" + "\r\n  <activated>true</activated>\r\n  <age>30</age>\r\n  <date>2008-01-20T08:00:00+0000</date>" + "\r\n  <info>\r\n    <data>ZGF0YSBjb250ZW50</data>\r\n  </info>" + "\r\n  <options>null</options>\r\n  <user>\r\n    <string>John Doe</string>\r\n  </user>\r\n  <weapons>" + "\r\n    <list>\r\n      <item>\r\n        <string>gun</string>\r\n      </item>\r\n      <item>" + "\r\n        <string>knife</string>\r\n      </item>\r\n    </list>\r\n  </weapons>\r\n</map>\r\n\r\n";

		final Map<String, Object> theMap = new TreeMap<String, Object>();

		theMap.put("user", "John Doe");
		theMap.put("date", CCalendar.parseISODate("2008-01-20T08:00:00 UTC").getTime());
		theMap.put("activated", true);
		theMap.put("options", null);
		theMap.put("age", 30);
		theMap.put("account", 1205.12);

		final Object[] array = { "gun", "knife" };
		theMap.put("weapons", Arrays.asList(array));

		final String dataString = "data content";
		theMap.put("info", dataString.getBytes());

		String result;
		try {
			result = ConverterFactory.XML.convertTo(theMap, false);
		} catch (final ConversionException e) {
			e.printStackTrace();
			Assert.fail("Parse Exception !");
			return;
		}

		Assert.assertEquals(expected, result);
	}

}
