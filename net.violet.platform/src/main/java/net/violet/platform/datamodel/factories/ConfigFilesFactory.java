package net.violet.platform.datamodel.factories;

import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.ConfigFilesImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface ConfigFilesFactory extends RecordFactory<ConfigFiles>, FilesAccessor {

	/**
	 * Finds all the {@link ConfigFiles} by index for the given {@link SERVICES}
	 * and {@link Lang}
	 * 
	 * @param inService
	 * @param inLang
	 * @return
	 */
	Map<String, List<ConfigFiles>> findAllByServiceAndLang(SERVICES inService, Lang inLang);

	/**
	 * Returns a list of ConfigFiles, each one wrapping a default picture file for the given hardware.
	 * @param inHardware the hardware
	 * @return a list of configFiles, cannot be null but may be empty.
	 */
	List<ConfigFiles> findDefaultPicturesByHardware(HARDWARE inHardware);

	/**
	 * Find a ConfigFiles, contains picture file for the given hardware and visual Id.
	 * @param inHardware the hardware
	 * * @param inVisual visual id between 1 and 33 ( this value is stored in table ztamp)
	 * @return a configFiles or null.
	 */
	ConfigFiles findPicture(HARDWARE inHardware, long inVisual);

	abstract class ConfigFilesCommon {

		public Map<String, List<ConfigFiles>> findAllByServiceAndLang(SERVICES inService, Lang inLang) {
			synchronized (ConfigFiles.CONFIG_FILES_CACHE) {
				if (!ConfigFiles.CONFIG_FILES_CACHE.containsKey(inService)) {
					final Map<Lang, Map<String, List<ConfigFiles>>> configFilesByLang = createConfigCache(inService);
					ConfigFiles.CONFIG_FILES_CACHE.put(inService, configFilesByLang);
				}
			}

			final Map<String, List<ConfigFiles>> theResult;
			final Map<Lang, Map<String, List<ConfigFiles>>> configFilesByLang = ConfigFiles.CONFIG_FILES_CACHE.get(inService);
			if (configFilesByLang != null) {
				theResult = configFilesByLang.get(inLang);
			} else {
				theResult = null;
			}

			return theResult;
		}

		/**
		 * Creates the {@link ConfigFilesImpl} cache upon use
		 * 
		 * @param inService
		 * @return
		 */
		protected abstract Map<Lang, Map<String, List<ConfigFiles>>> createConfigCache(SERVICES inService);

	}

}
