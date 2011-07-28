package net.violet.platform.applets.api;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.maps.applications.ApplicationPackageMap;
import net.violet.platform.applets.AppResourcesLoader;
import net.violet.platform.applets.js.InterpretedJsApplicationWrapper;
import net.violet.platform.applets.js.JsScriptFactory;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Script;

public class ScriptableAppletFactory {

	private static final Logger LOGGER = Logger.getLogger(ScriptableAppletFactory.class);

	private static class ScriptableAppletCache {

		private static final ConcurrentMap<String, ScriptableApplet> CACHE_MAP = new ConcurrentHashMap<String, ScriptableApplet>(128);

		public ScriptableApplet get(String inKey, Date refresh) throws APIException {

			ScriptableApplet app = ScriptableAppletCache.CACHE_MAP.get(inKey);

			if ((app == null) || app.needsRefresh(refresh)) {
				ScriptableAppletFactory.LOGGER.debug("Loading application package " + inKey);
				final ApplicationPackageMap appPackageMap = AppResourcesLoader.LOADER.getApplicationPackage(inKey);

				final String appName = appPackageMap.getName();
				final String src = appPackageMap.getSourceCode(); // AppResourcesLoader.LOADER.getApplicationSources(inKey);

				final Script appScript = JsScriptFactory.prepareScript(appName + ".js", src);
				//TODO avoid creating a second instance and returning it
				app = new InterpretedJsApplicationWrapper(appName, inKey, appPackageMap.getReleaseDate(), true, appScript, appPackageMap.getApiVersion());
				ScriptableAppletCache.CACHE_MAP.putIfAbsent(inKey, app);
				return ScriptableAppletCache.CACHE_MAP.get(inKey);
			}

			return app;

		};

		public void clearCache() {
			ScriptableAppletCache.CACHE_MAP.clear();
		}

	};

	private static final ScriptableAppletCache CACHE = new ScriptableAppletCache();

	public static void clearCache() {
		ScriptableAppletFactory.CACHE.clearCache();
	}

	public static ScriptableApplet getApplet(String apiKey, Date date) throws APIException {
		return ScriptableAppletFactory.CACHE.get(apiKey, date);
	}

}
