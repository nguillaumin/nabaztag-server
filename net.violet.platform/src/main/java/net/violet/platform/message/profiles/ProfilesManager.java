package net.violet.platform.message.profiles;

import java.io.File;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.cache.Cache;
import net.violet.platform.util.cache.GenerationException;
import net.violet.platform.util.cache.ValueGenerator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * The ProfilesManager is used to retrieve the {@link Profile} object of a
 * {@link VObject}. Presently, this class only retrieves information from files,
 * so it's very close to the ConfigurationManager.
 */
public class ProfilesManager {

	private static final Logger LOGGER = Logger.getLogger(ProfilesManager.class);

	private static final String[] CONFIG_PATHS = new String[] { Constantes.OS_PATH + "build/WEB-INF/conf/", Constantes.OS_PATH + "build/WEB-INF/classes/", };

	private static final Cache<String, Profile> CACHE = new Cache<String, Profile>(new ValueGenerator<String, Profile>() {

		public Profile generateValue(String key) throws GenerationException {
			File confFile = null;
			for (final String configurationPath : ProfilesManager.CONFIG_PATHS) {
				confFile = new File(configurationPath, key + ".properties");

				if (confFile.exists()) {
					break;
				}
			}

			if ((confFile == null) || !confFile.exists()) {
				throw new GenerationException("Configuration file : " + key + ".properties could not be found !");
			}

			try {
				final Configuration objectConf = new PropertiesConfiguration(confFile);
				return new Profile(objectConf);
			} catch (final ConfigurationException e) {
				ProfilesManager.LOGGER.fatal(e, e);
				throw new GenerationException(e.getMessage(), e);
			}
		}

	});

	private static final Profile EMPTY_PROFILE = new Profile();

	/**
	 * Force the reloading of properties (clear cache)
	 */
	public static void reload() {
		ProfilesManager.CACHE.clearCache();
	}

	/**
	 * Returns a Profile object for the provided VObject. Currently, information
	 * are retrieved from a properties file. 
	 * If the file couldn't be loaded or if the analyze failed 
	 * an empty profile is returned and can be safely used, it describes a profile
	 * that has 0% support with any kind of modalities. 
	 * 
	 * @param inObject the interactive object.
	 * @return the profile (empty profile if retrieval failed).
	 */
	public static Profile getObjectProfile(VObject inObject) {
		final String modelName = inObject.getHardware().getModelName();
		try {
			return ProfilesManager.CACHE.get(modelName);
		} catch (final GenerationException e) {
			ProfilesManager.LOGGER.fatal(e, e);
		}

		return ProfilesManager.EMPTY_PROFILE;
	}

}
