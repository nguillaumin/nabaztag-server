package net.violet.platform.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Map;

import net.violet.platform.util.Constantes;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import sun.misc.SoftCache;

/**
 * @author christophe - Violet
 */
public class ConfigurationFileManager {

	// a (soft) cache for the loaded property files
	private static Map<String, Configuration> myProperties = new SoftCache(10);

	// different path for the property files
	private static String INTERNATIONALIZATION = Constantes.OS_PATH + "i18n/";
	private static String[] myConfigurationPaths = new String[] { Constantes.OS_PATH + "build/WEB-INF/conf/", };

	public enum I18N_RESOURCES {
		API_ERROR_MESSAGES;
	}

	/**
	 * Force the reloading of properties (clear cache)
	 */
	public static void reload() {
		ConfigurationFileManager.myProperties.clear();
	}

	/**
	 * Returns a property file for the given Locale and Currently, information
	 * are retrieved from a properties file.
	 * 
	 * @param inObject the interactive object.
	 * @return the profile.
	 * @throws FileNotFoundException if the configuration file is not found.
	 * @throws ConfigurationException if the file is not a proper property file.
	 */
	public static Configuration getConfiguration(String inFileName) throws FileNotFoundException, ConfigurationException {

		// first look in the cache
		if (ConfigurationFileManager.myProperties.containsKey(inFileName)) {
			return ConfigurationFileManager.myProperties.get(inFileName);
		}

		File confFile = null;
		// find the configuration file
		for (final String configurationPath : ConfigurationFileManager.myConfigurationPaths) {
			confFile = new File(configurationPath, inFileName + ".properties");

			if (confFile.exists()) {
				break;
			}
		}

		if ((confFile == null) || !confFile.exists()) {
			throw new FileNotFoundException("Configuration file : " + inFileName + ".properties could not be found !");
		}

		// get the Configuration and store it for next call
		final Configuration conf = new PropertiesConfiguration(confFile);

		ConfigurationFileManager.myProperties.put(inFileName, conf);

		return conf;
	}

	/**
	 * Returns a translation file for the given Locale and resource name
	 * Translation files are in fact property files where the key of an entry
	 * has a translated message for value
	 * 
	 * @param locale
	 * @param inRscName
	 * @return
	 * @throws FileNotFoundException
	 * @throws ConfigurationException
	 */
	public static Configuration getTranslation(Locale locale, I18N_RESOURCES inRscName) throws FileNotFoundException, ConfigurationException {

		final String rscKey = inRscName.name() + locale.getCountry();

		// first look in the cache
		if (ConfigurationFileManager.myProperties.containsKey(rscKey)) {
			return ConfigurationFileManager.myProperties.get(rscKey);
		}

		// find the localized file
		final File localizedRoot = new File(ConfigurationFileManager.INTERNATIONALIZATION, locale.getCountry());

		if (!localizedRoot.exists() || !localizedRoot.isDirectory()) {
			localizedRoot.mkdir();
		}

		final File localizedFile = new File(localizedRoot, inRscName.name() + ".txt");

		if (!localizedFile.exists()) {
			throw new FileNotFoundException("Configuration file : " + inRscName + ".properties could not be found !");
		}

		// get the Configuration and store it for next call
		final Configuration conf = new PropertiesConfiguration(localizedFile);

		ConfigurationFileManager.myProperties.put(rscKey, conf);

		return conf;
	}
}
