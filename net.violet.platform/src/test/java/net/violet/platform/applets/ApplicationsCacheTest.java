package net.violet.platform.applets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.clients.APIClient;
import net.violet.platform.api.clients.RESTClient;
import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.applets.api.ScriptableApplet;
import net.violet.platform.applets.api.ScriptableAppletFactory;
import net.violet.platform.applets.js.JSProxy;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * IMPORTANT : this Test requires the Tomcat server to be launched as it
 * performs an API call to retrieve the application package..
 * 
 */
public class ApplicationsCacheTest /* extends MockTestBase */{

	private static final Logger LOGGER = Logger.getLogger(ApplicationsCacheTest.class);

	private static final APIClient mApiClient = new RESTClient(APIConstants.REST_SERVICE, AppResourcesLoader.LOADER);

	/**
	 * Dummy test to please JUNIT continuum integration
	 */
	@Test
	public void doPleaseJUNIT() {
		Assert.assertTrue(true);
	}

	// @Test
	public void doTwitterPostTest() {

		try {
			//
			initClientsForLocalTests();

			long startTime = System.currentTimeMillis();
			final ScriptableApplet app = ScriptableAppletFactory.getApplet("TwitterPost", null);
			long endTime = System.currentTimeMillis();
			ApplicationsCacheTest.LOGGER.debug("Created application instance in " + (endTime - startTime) + "ms");

			// Creating a pseudo event and emetter
			final Map<String, Object> eventMap = new HashMap<String, Object>(2);
			eventMap.put("type", "TriggerEvent");

			final Map<String, Object> contextMap = new HashMap<String, Object>(3);
			contextMap.put("login", "tnabaztag");
			contextMap.put("pwd", "violet");
			contextMap.put("greetingMsg", "Hi, i'm %1%. I live in %2%. Current time is %3% hours %4% minutes and %5% seconds.");

			eventMap.put("context", contextMap);

			// let the application process the event..
			startTime = System.currentTimeMillis();
			final List<Object> lstResult = app.processEvent(eventMap); // id of Aleister for TwitterPost
			endTime = System.currentTimeMillis();
			ApplicationsCacheTest.LOGGER.debug("Event processed in " + (endTime - startTime) + "ms");

			ApplicationsCacheTest.LOGGER.debug("Received response :\n" + lstResult);

		} catch (final Exception e) {
			
			e.printStackTrace();
			org.junit.Assert.fail(e.getMessage());

		}

		Assert.assertTrue(true);
	}

	/**
	 * Make an application event call
	 * 
	 * @param useScriptableInstance
	 * @throws ClassCastException
	 * @throws ConversionException
	 */
	/*private void doTest(boolean useScriptableInstance) {

		try {

			// Creating new instance of application helloworld
			final long startTime = System.currentTimeMillis();
			final ScriptableApplet helloWorldApp = ApplicationsCache.getInstance(ApplicationsCacheTest.APP_KEY, useScriptableInstance);
			final long endTime = System.currentTimeMillis();
			ApplicationsCacheTest.LOGGER.debug("Created " + (useScriptableInstance ? "scriptable" : "compiled") + " application instance in " + (endTime - startTime) + "ms");

			// Creating a pseudo event and emetter
			// final Map<String, Object> eventMap = new HashMap<String,
			// Object>(2);
			// eventMap.put("type", "StartInteractiveEvent");
			//
			// final Map<String, Object> contextMap = new HashMap<String,
			// Object>(2);
			// contextMap.put("countWait", 5);
			// contextMap.put("startedOn", "Aleister");
			// eventMap.put("context", contextMap);
			//
			// eventMap.put("keyword", "Earth is a lonely planet from here..");
			//
			//
			// // let the application process the event.. List<Object> lstResult = helloWorldApp.processEvent("theid", eventMap); // LOGGER.debug("Received response :\n" + lstResult); Create another event startTime = System.currentTimeMillis(); for (int i = 1; i < 20; i++) { updateEvent(eventMap, contextMap);
			// try {
			// lstResult = helloWorldApp.processEvent("theid", eventMap);
			// LOGGER.debug("Received response :\n" + lstResult);
			// } catch (Exception e) {
			// LOGGER.debug("Response error for event : " + eventMap);
			// }
			// }
			// endTime = System.currentTimeMillis();
			// LOGGER.debug("Processes 1000 ButtonEvent for " +
			// (useScriptableInstance?"scriptable":"compiled") +
			// " application instance in " + (endTime - startTime) + "ms");

		} catch (final Exception e) {
			
			e.printStackTrace();
			org.junit.Assert.fail(e.getMessage());

		}

		Assert.assertTrue(true);
	}*/

	/**
	 * for local tests only : use the REST clients instead of the XMPP
	 * components
	 */
	private void initClientsForLocalTests() {
		AppResourcesLoader.LOADER.setAPIClient(ApplicationsCacheTest.mApiClient);
		JSProxy.useRestMode(true);
		ScriptableAppletFactory.clearCache();

	}

}
