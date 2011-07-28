package net.violet.platform.api.clients;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.Action.ActionType;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.applets.AppResourcesLoader;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * These JUnits test will failed if Tomcat is not launched
 * 
 * @author christophe - Violet
 */
public class RESTClientTest extends MockTestBase {

	// nabdev
	// private final static String REST_TEST_URL = "192.168.1.11/vl/rest";
	// localhost
	private static final String REST_TEST_URL = "127.0.0.1:8080/vl/rest";

	@Test
	public void doTest() {
		Assert.assertTrue(true);
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.clients.RESTClient#executeMethodCall(java.lang.String, net.violet.platform.api.actions.ActionParam)}
	 * .
	 * 
	 * @throws MalformedURLException
	 */

	// @Test // uncomment to test
	public void testExecuteMethodCall() {

		final APICaller emitter = AppResourcesLoader.LOADER;
		final RESTClient client = new RESTClient(RESTClientTest.REST_TEST_URL, emitter);
		Object result;
		try {
			result = client.executeMethodCall("violet.objects.findByName", "Aleister");

		} catch (final Exception e) {
			
			Assert.fail(e.getMessage());
			return;
		}

		final Date birthdayDate = CCalendar.parseISODate("1968-10-07T04:00:00 CET").getTime();
		try {
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put("japaneseTitle", "株式会社リコー 新着情報");
			// List<String> countries = new ArrayList<String>();
			// countries.add("France");
			// countries.add("Japan");
			// countries.add("Eire");
			// params.put("countries", countries);
			// Map<String, Object> man = new HashMap<String, Object>();
			params.put("name", "DOE");
			params.put("surname", "John");
			params.put("male", true);
			params.put("age", 38);
			params.put("negpie", -3.14159);
			params.put("birthday", birthdayDate);
			// params.put("man", man);

			result = client.executeMethodCall(ActionType.GET, "violet.test.echo", params);

		} catch (final Exception e) {
			
			Assert.fail(e.getMessage());
			return;
		}

		final Map<String, Object> resultMap = (Map<String, Object>) result;

		Assert.assertEquals("株式会社リコー 新着情報", resultMap.get("japaneseTitle"));
		Assert.assertEquals(true, resultMap.get("male"));
		Assert.assertEquals(birthdayDate, resultMap.get("birthday"));
		Assert.assertEquals(Double.valueOf("-3.14159"), resultMap.get("negpie"));
		Assert.assertEquals(Integer.valueOf(38), resultMap.get("age"));

	}

}
