package net.violet.platform.api.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SafeBase64;

import org.junit.Assert;
import org.junit.Test;

public class YAMLConverterTest extends MockTestBase {

	private static final String CRYPTIC_MESSAGE = "Hello SpaceBoy ! This is Ground Control. Transmission test : 0123456789. You'll get it on base 64.";
	private static final byte[] CRYPTIC_MESSAGE_AS_BYTES = YAMLConverterTest.CRYPTIC_MESSAGE.getBytes();
	private static final String CRYPTIC_MESSAGE_AS_BASE64 = new String(SafeBase64.encode(YAMLConverterTest.CRYPTIC_MESSAGE_AS_BYTES, true));

	//private static final String strYAMLTest = "date: !!timestamp 2002-02-02T22:22:22Z\n" + "version: 1\n" + "stateful: !!bool true\n" + "pi: 3.14116\n" + "empty: \"\"\n" + "null: \n" + "mainactor: \n" + "    - id: \"007\"\n" + "actors: \n" + "    - \"laurel\"\n" + "    - \"hardy\"\n" + "    - \"john\"\n" + "bdata: !!binary |\n" + "  SGVsbG8gU3BhY2VCb3kgISBUcmFuc21pc3Npb24gd\n" + "  GVzdCA6IDAxMjM0NTY3ODk=\n" + "frac: 0.3333333333333333\n" + "hex: 255\n" + "int_val: 7927940\n" + "null_vall: ~\n" + "sequence: \n" + "    - annotation: {state: \"speaking\"}\n" + "    - directive: {settings: {snd.btn.2: \"blonk\"}, disable: [\"earMove\"]}\n" + "    - scene: {genre: net.violet.tts.en, text: \"\\tHello \\\"Space Boy\\\"\\nCome Here !\", options: {voice: \"\"}}\n";
	private static final String strYAMLTest = "date: !!timestamp 2002-02-02T22:22:22Z\n" + "version: 1\n" + "stateful: !!bool true\n" + "pi: 3.14116\n" + "empty: \"\"\n" + "null: \n" + "mainactor: \n" + "    - id: \"007\"\n" + "actors: \n" + "    - \"laurel\"\n" + "    - \"hardy\"\n" + "    - \"john\"\n" + "bdata: !!binary SGVsbG8gU3BhY2VCb3kgISBUaGlzIGlzIEdyb3VuZCBDb250cm9sLiBUcmFuc21pc3Npb24gdGVzdCA6IDAxMjM0NTY3ODkuIFlvdSdsbCBnZXQgaXQgb24gYmFzZSA2NC4=\n" + "frac: 0.3333333333333333\n" + "hex: 255\n" + "int_val: 7927940\n" + "null_vall: ~\n" + "sequence: \n" + "    - annotation: {state: \"speaking\"}\n" + "    - directive: {settings: {snd.btn.2: \"blonk\"}, disable: [\"earMove\"]}\n"
			+ "    - scene: {genre: net.violet.tts.en, text: \"\\tHello \\\"Space Boy\\\"\\nCome Here !\", options: {voice: \"\"}}\n";

	private static final String YAML_ACTORS_LIST = "- \"laurel\"\n" + "- \"hardy\"\n" + "- \"john\"\n";

	private class TestMap extends AbstractAPIMap {

		private static final long serialVersionUID = 1236734855387882747L;

		public TestMap() {
			super();
		}

	}

	/**
	 * Parse a YAML string and look through its content
	 * 
	 * @throws APIException
	 * @throws ConversionException
	 */
	@Test
	public void testconvertFromYAML() {
		// test date is found within the YAML string
		final Date testDate = CCalendar.parseISODate("2002-02-02T22:22:22 UTC").getTime();

		final Map<String, Object> map;
		try {
			map = ConverterFactory.YAML.convertFrom(YAMLConverterTest.strYAMLTest);
		} catch (final ConversionException e) {
			Assert.fail(e.getMessage());
			return;
		}

		if (map != null) {
			// get the date under "date"
			Assert.assertTrue(map.containsKey("date"));

			Assert.assertEquals(testDate, map.get("date"));

			// get the actors list
			Assert.assertTrue(map.containsKey("actors"));

			final List actors = (List) map.get("actors");
			Assert.assertEquals(3, actors.size());

			Assert.assertEquals("laurel", actors.get(0));
			Assert.assertEquals(StringShop.EMPTY_STRING, map.get("empty"));
			Assert.assertNull(map.get("null"));

			// get the version number
			Assert.assertEquals(new Integer(1), map.get("version"));

			// get the binary data
			final byte[] chars = (byte[]) map.get("bdata");
			System.out.println(YAMLConverterTest.CRYPTIC_MESSAGE_AS_BASE64 + " Decoded base64 chars :" + new String(chars));
			Assert.assertEquals(YAMLConverterTest.CRYPTIC_MESSAGE, new String(chars));
		}

	}

	/**
	 * Parse a YAML string and look through its content
	 * @throws ConversionException 
	 * 
	 * @throws APIException en cas d'erreur.
	 * @throws ConversionException en cas d'erreur.
	 */
	@Test
	public void testconvertFromYAMLList() throws ConversionException {
		final Object parsed = ConverterFactory.YAML.convertFrom(YAMLConverterTest.YAML_ACTORS_LIST);

		Assert.assertNotNull(parsed);
		Assert.assertTrue(parsed instanceof List);
	}

	/**
	 * Build some objects and look at their YAML representation
	 */

	@Test
	public void testTextFailed() throws ConversionException {

		final String[] testQuotes = { "'sfsdf sdfsdfds 'sdfdf' dfdfd \"zefsdf\" dfdf'dfffff'", "'sfsdf sdfsdfds \"zefsdf\" dfdf'dfffff'", "  -'-'\r\n\t\"zed\" iz'ded'baby, z is ded,(..) \\yeah, az ''dead'' as 'led zep' or zat thinn lizzy thing..(\"*-*\")" };

		final List<Map<String, Object>> yamlListOfQuotes = new ArrayList<Map<String, Object>>();

		final Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("text", testQuotes[0]);

		yamlListOfQuotes.add(map1);

		final Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("text", testQuotes[1]);

		yamlListOfQuotes.add(map2);

		final Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("text", testQuotes[2]);

		yamlListOfQuotes.add(map3);

		final Converter yamlConverter = ConverterFactory.YAML;
		final String yamlJam = yamlConverter.convertTo(yamlListOfQuotes);

		final List canIDigestThatYaml = (List) yamlConverter.convertFrom(yamlJam);

		for (int i = 0; i < 3; i++) {
			final Map map = (Map) canIDigestThatYaml.get(i);
			final String digestedText = (String) map.get("text");
			Assert.assertEquals(testQuotes[i], digestedText);
		}
	}

	@Test
	public void testconvertBinaryToYAML() throws ConversionException {
		final Converter yamlConverter = ConverterFactory.YAML;
		final String YAMLBinary = yamlConverter.convertTo(YAMLConverterTest.CRYPTIC_MESSAGE_AS_BYTES);
		System.out.println(YAMLBinary);
	}

	@Test
	public void testconvertPojoMapToYAML() throws ConversionException {
		final Converter yamlConverter = ConverterFactory.YAML;
		final Map<String, Object> testMap = new TestMap();
		testMap.put("string", "\tthat's all wright baby !\nwe''ll get there !");
		testMap.put("int", 1);
		testMap.put("double", 3.1416);

		final String strYamlMap = yamlConverter.convertTo(testMap);
		System.out.println(strYamlMap);
	}

	@Test
	public void testconvertToYAML() throws ConversionException {

		final Converter yamlConverter = ConverterFactory.YAML;

		// String Tests
		final String hello = "Blackie says:\r\n\t-\"Hello World ! It's 'Blackie'\"";

		try {
			final String yamlHello = yamlConverter.convertTo(hello);
			System.out.println("Hello World in YAML :\n" + yamlHello);
			final String digested = yamlConverter.convertFrom(yamlHello);
			System.out.println("Hello World digested :\n" + digested);
			Assert.assertEquals("Hello World digested :\n" + digested, hello, digested);
		} catch (final Exception e) {
			Assert.fail("Conversion Exception !" + e.getMessage());
		}

		// Number Tests
		final double pi = 3.1416;
		final String YAMLPie = yamlConverter.convertTo(pi);
		final Object piDigested = yamlConverter.convertFrom(YAMLPie);
		System.out.println("PI digested : " + piDigested);
		Assert.assertEquals(pi, piDigested);

		// Null Tests
		final String YAMLnull = yamlConverter.convertTo(null);
		final Object nullDigested = yamlConverter.convertFrom(YAMLnull);
		System.out.println("Null digested : " + nullDigested);
		Assert.assertNull(nullDigested);

		// Map structure with lists
		final Map<String, Object> myTown = new HashMap<String, Object>(5);

		Map<String, Object> x = new HashMap<String, Object>(8);
		x.put("first name", "John");
		x.put("family name", "DOE");
		final Date birthday = CCalendar.parseTimestamp("1988-10-14 00:00:00", "America/Anchorage").getTime();
		System.out.println("John's birthday : " + birthday);
		x.put("birthday", birthday);
		x.put("age", 20);
		x.put("driver licence", false);
		x.put("diplomes", null);
		myTown.put("JD", x);

		x = new HashMap<String, Object>(8);
		x.put("first name", "Homer");
		x.put("family name", "SIMPSON");
		x.put("birthday", CCalendar.parseTimestamp("1967-10-07 03:50:00", "America/Detroit").getTime());
		x.put("age", 40);
		x.put("driver licence", true);

		// now add binary data
		x.put("bdata", YAMLConverterTest.CRYPTIC_MESSAGE_AS_BYTES);

		final List<Object> lstHobbies = new ArrayList<Object>(3);
		lstHobbies.add("beer");
		lstHobbies.add("television");
		lstHobbies.add(69);
		x.put("hobbies", lstHobbies);
		myTown.put("HS", x);

		try {
			final String yamltown = yamlConverter.convertTo(myTown);
			System.out.println("SmallTown :\n" + yamltown);

			// Read back the YAML
			final PojoMap digestedTown = new PojoMap(yamlConverter.<Map<String, Object>> convertFrom(yamltown));

			Assert.assertEquals(new Integer(40), digestedTown.getInt("HS.age", true));
			Assert.assertEquals("television", digestedTown.getString("HS.hobbies[1]", true));
			Assert.assertEquals("television", digestedTown.getString("HS.hobbies[1]", true));
			Assert.assertTrue(digestedTown.getBoolean("HS.driver licence"));
			Assert.assertNull(digestedTown.getList("JS.diplomes"));

			final byte[] chars = digestedTown.getBytes("HS.bdata");
			System.out.println("Decoded base64 chars :" + new String(chars));
			Assert.assertEquals(YAMLConverterTest.CRYPTIC_MESSAGE, new String(chars));

		} catch (final Exception e) {
			Assert.fail("Conversion Exception !" + e.getMessage());
		}

		x = new HashMap<String, Object>();
		x.put("family name", "株式会社リコー 新着情報");
		try {
			System.out.println(yamlConverter.convertTo(x));
		} catch (final ConversionException e) {
			Assert.fail("Conversion Exception !" + e.getMessage());
		}
	}
}
