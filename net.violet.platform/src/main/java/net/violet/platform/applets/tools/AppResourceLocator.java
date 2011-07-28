/**
 *
 */
package net.violet.platform.applets.tools;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.applets.api.ScriptableApplet;

import org.apache.log4j.Logger;

/**
 * a class Utility to resolve the resources of an application A resource is
 * identified by a logical name that has a path structure Resources can be of
 * different types : - text - urls - sub resources
 * 
 * @author chdes - Violet
 */
public class AppResourceLocator {

	private static final String RESOURCE_TAG_NAME = "resource";
	private static final Logger LOGGER = Logger.getLogger(AppResourceLocator.class);

	/**
	 * @param pojo
	 * @return TRUE if this map contains a resource
	 */

	public static boolean hasResource(Object pojo) {

		if (!(pojo instanceof Map)) {
			return false;
		}

		try {
			final Map<String, Object> map = (Map<String, Object>) pojo;
			return map.containsKey(AppResourceLocator.RESOURCE_TAG_NAME);

		} catch (final ClassCastException e) {
			return false;
		}
	}

	/**
	 * An expression may contain resources identificated with logical names
	 * 
	 * @param app
	 * @param expression
	 */
	public static void resolveResources(ScriptableApplet app, ModalityOfExpression expression) {

		AppResourceLocator.LOGGER.debug("Resolving resources for expression : " + expression);

		if (AppResourceLocator.hasResource(expression)) {

			Map<String, Object> resource = null;

			try {
				// 1st case : the resource name has to be resolved in to a URL
				// or text value
				final String resourceName = (String) expression.get(AppResourceLocator.RESOURCE_TAG_NAME);
				resource = AppResourceLocator.resolve(app, resourceName);

			} catch (final ClassCastException cce1) { // resource is not a logical name
				try {
					// 2nd case : the resource has already been resolved but is
					// still encapsulated
					// inside the resource tag : "resource":
					// {"url":"htpp://blah.url", "quality": 1}
					// >> merge it
					resource = (Map<String, Object>) expression.get(AppResourceLocator.RESOURCE_TAG_NAME);

				} catch (final ClassCastException cce2) {
					AppResourceLocator.LOGGER.warn("Resource key in expression " + expression + " doesn't conform to a resource structure !");
				}
			}

			// replace the resource by its resolved value
			expression.putAll(resource);
			expression.remove(AppResourceLocator.RESOURCE_TAG_NAME);
		}

		/*
		 * Do the same for the alternate choices
		 */
		for (final ModalityOfExpression alt : expression.getAlternateChoices()) {
			AppResourceLocator.resolveResources(app, alt);
		}

		AppResourceLocator.LOGGER.debug("After resource resolution : " + expression);

	}

	/**
	 * @param app
	 * @param resourceName
	 * @return
	 */
	public static Map<String, Object> resolve(ScriptableApplet app, String resourceName) {

		final Map<String, Object> resource = new HashMap<String, Object>(2);
		final String rscFileName = resourceName.substring(resourceName.lastIndexOf('/') + 1);

		// TODO
		if (resourceName.indexOf("movie") != -1) {
			resource.put("modality", "net.violet.video");
			resource.put("value", "http://www.youtube.com/play?video=" + rscFileName);

		} else if (resourceName.indexOf("sound") != -1) {
			resource.put("modality", "net.violet.sound");
			resource.put("value", rscFileName);

		} else if (resourceName.indexOf("music") != -1) {
			resource.put("modality", "net.violet.music");
			resource.put("value", "http://www.nabaztag.com/play?music=" + rscFileName);

		} else {
			resource.put("value", "Alea Jacta Est..");
		}

		return resource;
	}

}
