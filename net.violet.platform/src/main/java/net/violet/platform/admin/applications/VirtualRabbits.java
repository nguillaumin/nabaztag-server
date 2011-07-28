package net.violet.platform.admin.applications;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.clients.APIClient;
import net.violet.platform.api.clients.RESTClient;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.applets.AppResourcesLoader;
import net.violet.platform.applets.api.ScriptableApplet;
import net.violet.platform.applets.api.ScriptableAppletFactory;
import net.violet.platform.applets.js.JSProxy;

import org.apache.log4j.Logger;

/**
 * @author christophe - Violet
 */
public class VirtualRabbits {

	private static final Logger LOGGER = Logger.getLogger(VirtualRabbits.class);
	private static final String[] mEventTypes = { "TriggerEvent", "ButtonEvent", "EndOfSequenceEvent", "VoiceRecognitionEvent", "ZtampDetectionEvent", "StartInteractiveEvent", "EarMovementEvent" };

	private static final String APP_KEY = "HelloWorld-v3"; // HELLOWORLD JS Application

	private static final APIClient mApiClient = new RESTClient(APIConstants.REST_SERVICE, AppResourcesLoader.LOADER);

	private static final String[] mParams = new String[] { "number_of_threads", "number_of_events_call", "delay_between_calls" };

	/**
	 * Create a number of thread to send event requests to Interactive
	 * Applications
	 * 
	 * @throws ClassCastException
	 * @throws ConversionException
	 */
	public static void main(String[] args) {

		// Read parameters
		final int len = VirtualRabbits.mParams.length; // expected number of params

		if (args.length != len) {
			VirtualRabbits.printHelp();
			return;
		}

		final int nbThreads = Integer.parseInt(args[0]);
		final int nbCalls = Integer.parseInt(args[1]);
		final int delayMs = Integer.parseInt(args[2]);

		// For local tests only : use the REST client
		AppResourcesLoader.LOADER.setAPIClient(VirtualRabbits.mApiClient);
		JSProxy.useRestMode(true);
		ScriptableAppletFactory.clearCache();

		final VirtualRabit[] rabbits = new VirtualRabit[nbThreads];

		// initialize the virtual rabbits
		for (int i = 0; i < nbThreads; i++) {
			rabbits[i] = new VirtualRabit(VirtualRabbits.APP_KEY, true, nbCalls, delayMs);
			rabbits[i].run();
		}
	}

	/**
	 * @author christophe - Violet
	 */
	static class VirtualRabit implements Runnable {

		private final String mAppKey;
		private final boolean mUseScriptableInstance;
		private final int mEventCount;
		private final int mDelay;

		VirtualRabit(String inAppKey, boolean useScriptableInstance, int howManyEvents, int delayBetweenCalls) {
			this.mAppKey = inAppKey;
			this.mUseScriptableInstance = useScriptableInstance;
			this.mEventCount = howManyEvents;
			this.mDelay = delayBetweenCalls;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {

			try {
				long startTime = System.currentTimeMillis();
				final ScriptableApplet helloWorldApp = ScriptableAppletFactory.getApplet(this.mAppKey, null);
				long endTime = System.currentTimeMillis();
				VirtualRabbits.LOGGER.debug("Created " + (this.mUseScriptableInstance ? "scriptable" : "compiled") + " application instance in " + (endTime - startTime) + "ms");

				// Creating a pseudo event and emetter
				final Map<String, Object> eventMap = new HashMap<String, Object>(2);
				eventMap.put("keyword", "Earth is a lonely planet from here..");

				final Map<String, Object> contextMap = new HashMap<String, Object>(2);
				contextMap.put("startedOn", "109dfO99bd9e67");
				eventMap.put("context", contextMap);

				List<Object> lstResult = null;

				for (int i = 0; i < this.mEventCount; i++) {
					// update some random event values
					VirtualRabbits.updateEvent(eventMap, contextMap);

					// let the application process the event..
					startTime = System.currentTimeMillis();
					try {
						lstResult = helloWorldApp.processEvent(eventMap);
						endTime = System.currentTimeMillis();
						VirtualRabbits.LOGGER.debug("Received response :\n" + lstResult + " in " + (endTime - startTime) + "ms");

					} catch (final Exception e) {
						VirtualRabbits.LOGGER.debug("Response error for event : " + eventMap);
					}

					// wait to generate a new event
					Thread.sleep(this.mDelay);
				}

			} catch (final Exception e) {
				VirtualRabbits.LOGGER.error(e.getMessage());
			}
		}
	}

	/**
	 * Creates a random event type
	 * 
	 * @param inEventMap
	 * @param inContextMap
	 */
	private static void updateEvent(Map<String, Object> inEventMap, Map<String, Object> inContextMap) {
		final int rndVal = Math.abs((int) System.currentTimeMillis());
		inEventMap.put("type", VirtualRabbits.mEventTypes[rndVal % VirtualRabbits.mEventTypes.length]);
		inEventMap.put("when", new Date());

		inContextMap.put("countWait", rndVal % 20); // 0..20
	}

	/**
	 * List the expected arguments for the command line
	 */
	private static void printHelp() {
		System.out.print("Usage : > java PublishJavaScriptAPIRelease ");
		for (int i = 0; i < VirtualRabbits.mParams.length; i++) {
			System.out.print("%" + VirtualRabbits.mParams[i] + " ");
		}
	}

}
