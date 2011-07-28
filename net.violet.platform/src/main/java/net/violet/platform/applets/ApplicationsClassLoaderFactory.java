package net.violet.platform.applets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.violet.common.StringShop;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.applets.js.JsClassLoader;
import net.violet.platform.applets.js.RhinoInteractiveApplication;
import net.violet.platform.applets.tools.SourceClassLoader;

/**
 * ClassLoader factory and cache for the different hosted types of applications
 * 
 * @author christophe - Violet
 */

public class ApplicationsClassLoaderFactory {

	// cache des ClassLoader pour chaque version de l'API
	private static final Map<String, SourceClassLoader> CLASS_LOADER_CACHE = new WeakHashMap<String, SourceClassLoader>();

	private static final List<Class> mJsApplicationInterfaces = Collections.singletonList((Class) RhinoInteractiveApplication.class);

	/**
	 * Find a Class loader for this application
	 * 
	 * @param lang
	 * @return
	 * @throws APIException
	 */
	public static SourceClassLoader getClassLoader(String inAppLanguage, String inApiVersion) throws APIException {

		// class loader depends on the developpement language used by this application
		final AppLanguages language = AppLanguages.findByLabel(inAppLanguage);

		if (language != null) {
			final String classLoaderKey = language.name() + StringShop.HYPHEN + inApiVersion;

			try {
				if (language == AppLanguages.JAVASCRIPT) {

					SourceClassLoader loader = ApplicationsClassLoaderFactory.CLASS_LOADER_CACHE.get(classLoaderKey);

					if (loader == null) {

						synchronized (ApplicationsClassLoaderFactory.CLASS_LOADER_CACHE) {
							loader = ApplicationsClassLoaderFactory.CLASS_LOADER_CACHE.get(classLoaderKey);

							if (loader == null) {

								// TODO load the JS API source for the player version of
								// this application
								final String apiSource = AppResourcesLoader.LOADER.getApiSource(inApiVersion);
								loader = new JsClassLoader(apiSource, ApplicationsClassLoaderFactory.mJsApplicationInterfaces);
								ApplicationsClassLoaderFactory.CLASS_LOADER_CACHE.put(classLoaderKey, loader);
							}
						}
					}

					return loader;
				}

			} catch (final Exception e) {
				throw new APIException(APIErrorMessage.INTERNAL_ERROR, e);
			}
		}

		throw new APIException(APIErrorMessage.UNKNOWN_SCRIPTING_LANGUAGE);
	}

}
