package net.violet.platform.api.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.violet.platform.api.converters.pojo.BinaryDataWrapper;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SafeBase64;

import org.junit.Assert;
import org.junit.Test;

public class JSONConverterTest extends MockTestBase {

	private static final byte[] binaryTestData = "Hello SpaceBoy ! Transmission test : 0123456789".getBytes();
	private static final String str64TestData = new String(SafeBase64.encode(JSONConverterTest.binaryTestData, true));

	private static final String strJSONTest = "{\"date\":\"2002-02-02T22:22:22Z\",\"version\":1,\"stateful\":true,\"pi\":3.14116,\"mainactor\":{\"id\":\"007\"},\"actors\":[\"laurel\",\"hardy\",\"john\"],\"bdata\":{\"_Base64Encoded\":\"" + JSONConverterTest.str64TestData + "\"},\"frac\":0.3333333333333333,\"hex\":255,\"long_val\":72057594037927940,\"null_vall\":null,\"sequence\":[{\"annotation\":{\"state\":\"speaking\"}},{\"directive\":{\"settings\":{\"snd.btn.2\":\"blonk\"},\"disable\":[\"earMove\"]}},{\"scene\":{\"genre\":\"net.violet.tts.en\",\"text\":\"\\tHello \\\"Space Boy\\\"\\nCome Here !\",\"options\":{\"voice\":\"\"}}}]}";

	/**
	 * Parse a JSON string and look through its content
	 */
	@Test
	public void testconvertFromJSON() {

		System.out.println("JSON Test string :\n" + JSONConverterTest.strJSONTest);

		// test date is found within the JSON string
		final Date testDate = CCalendar.parseISODate("2002-02-02T22:22:22 UTC").getTime();

		final Map<String, Object> map;

		try {
			map = ConverterFactory.JSON.convertFrom(JSONConverterTest.strJSONTest);
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail("Parse Exception !");
			return;
		}

		Object value;

		// get the date under "date"
		Assert.assertTrue(map.containsKey("date"));

		value = map.get("date");
		Assert.assertEquals(testDate, value);

		// get the actors list
		Assert.assertTrue(map.containsKey("actors"));

		final List actors = (List) map.get("actors");
		Assert.assertEquals(3, actors.size());

		Assert.assertEquals("laurel", actors.get(0));

		// get the version number
		Assert.assertEquals(new Integer(1), map.get("version"));

		// get the binary data
		final String strDecodedBinaryData = new String((byte[]) map.get("bdata"));
		System.out.println("Decoded base64 chars :" + strDecodedBinaryData);
		Assert.assertEquals(new String(JSONConverterTest.binaryTestData), strDecodedBinaryData);

	}

	/**
	 * Build some objects and look at their JSON representation
	 */

	@Test
	public void testconvertToJSON() {

		// String Tests
		final String hello = "Blackie says:\n\t-\"Hello World !\"";

		final String jsonHello = ConverterFactory.JSON.convertTo(hello);
		System.out.println("Hello World in JSON :\n" + jsonHello);

		String digested = null;
		try {
			digested = (String) ConverterFactory.JSON.convertFrom(jsonHello);
		} catch (final Exception e) {
			Assert.fail("Conversion Exception !" + e.getMessage());
		}

		System.out.println("Hello World digested :\n" + digested);
		Assert.assertEquals("Hello World digested :\n" + digested, hello, digested);

		// Number Tests
		final Double pi = 3.1416;

		String jsonPie = null;
		jsonPie = ConverterFactory.JSON.convertTo(pi);
		System.out.println("PI in JSON :\n" + jsonPie);
		Assert.assertEquals(jsonPie, "3.1416");

		Number piDigested = null;
		try {
			piDigested = (Number) ConverterFactory.JSON.convertFrom(jsonPie);
		} catch (final Exception e) {
			Assert.fail("Conversion Exception !" + e.getMessage());
		}

		System.out.println("PI digested :\n" + piDigested);
		Assert.assertEquals(pi, piDigested);

		// Null Tests
		Assert.assertEquals("null", ConverterFactory.JSON.convertTo(null));

		try {
			Assert.assertNull(ConverterFactory.JSON.convertFrom("null"));
		} catch (final Exception e) {
			Assert.fail("Conversion Exception !" + e.getMessage());
		}

		Map<String, Object> myTown = new HashMap<String, Object>(5);

		Map<String, Object> x = new HashMap<String, Object>(3);
		x.put("firstName", "John");
		x.put("familyName", "DOE");
		final Date birthday = CCalendar.parseTimestamp("1988-10-14 00:00:00", "America/Anchorage").getTime();
		System.out.println("John's birthday : " + birthday);
		x.put("birthday", birthday);
		x.put("age", 20);
		x.put("driverLicence", false);
		x.put("diplomes", null);
		myTown.put("jd", x);

		x = new HashMap<String, Object>(3);
		x.put("firstName", "Homer");
		x.put("familyName", "SIMPSON");
		x.put("birthday", CCalendar.parseTimestamp("1967-10-07 03:50:00", "America/Detroit").getTime());
		x.put("age", 40);
		x.put("driverLicence", true);
		// 2 different binary data
		final byte[] aBytes = new byte[] { 0, 5, 8, 6, 7, 5, 125, -124 };
		x.put("bdata1", new BinaryDataWrapper(aBytes)); // wrap it already
		x.put("bdata2", aBytes); // don't wrap it (let the converter do it)

		final List<Object> lstHobbies = new ArrayList<Object>(3);
		lstHobbies.add("beer");
		lstHobbies.add("television");
		lstHobbies.add(69);
		x.put("hobbies", lstHobbies);
		myTown.put("hs", x);

		String smalltown = net.violet.common.StringShop.EMPTY_STRING;
		try {
			BinaryDataWrapper.wrapBinaries(myTown);
			smalltown = ConverterFactory.JSON.convertTo(myTown, false);
		} catch (final ConversionException e1) {
			e1.printStackTrace();
		}
		System.out.println("SmallTown :\n" + smalltown);

		Assert.assertTrue(smalltown.indexOf("\"driverLicence\":true") > -1); // Homer has one licence
		Assert.assertTrue(smalltown.indexOf("\"driverLicence\":false") > -1); // John DOE hasn 't Now put a forbidden type inside the map
		myTown.put("localeTimeZone", TimeZone.getDefault());

		try {
			smalltown = ConverterFactory.JSON.convertTo(myTown, false); // silent mode = false to catch an exception
		} catch (final ConversionException e) {
			System.out.println("Detected Conversion Exception ! that's OK :\n" + e.getMessage());
			Assert.assertTrue("Conversion Exception !", true);
		}

		smalltown = ConverterFactory.JSON.convertTo(myTown); // silent mode = true
		Assert.assertTrue(smalltown.indexOf("\"localeTimeZone\"") == -1); // we don 't find the localeTimeZone member in
		// our
		// map

		try {
			myTown = ConverterFactory.JSON.convertFrom(smalltown);
		} catch (final Exception e) {
			System.out.println("Reinjecting serialized data caused an exception ! :\n" + e.getMessage());
			Assert.assertFalse("Conversion Exception !", true);
		}

		final Map<String, Object> homer = (Map<String, Object>) myTown.get("hs");
		Assert.assertEquals(homer.get("driverLicence"), true);

		homer.get("bdata1");

		homer.get("bdata2");

	}
}
