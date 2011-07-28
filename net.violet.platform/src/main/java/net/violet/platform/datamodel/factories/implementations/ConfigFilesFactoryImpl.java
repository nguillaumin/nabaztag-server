package net.violet.platform.datamodel.factories.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.ConfigFilesImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FilesImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.ConfigFilesFactory;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class ConfigFilesFactoryImpl extends RecordFactoryImpl<ConfigFiles, ConfigFilesImpl> implements ConfigFilesFactory {

	protected ConfigFilesFactoryImpl() {
		super(ConfigFilesImpl.SPECIFICATION);
	}

	private final ConfigFilesCommon mFactory = new ConfigFilesCommon() {

		private final Logger LOGGER = Logger.getLogger(this.getClass());

		/**
		 * Creates the {@link ConfigFilesImpl} cache upon use
		 * 
		 * @param inService
		 * @return
		 */
		@Override
		protected Map<Lang, Map<String, List<ConfigFiles>>> createConfigCache(SERVICES inService) {
			final Map<Lang, Map<String, List<ConfigFiles>>> configFilesByLang = new HashMap<Lang, Map<String, List<ConfigFiles>>>();
			for (final Lang aLang : Factories.LANG.findAll()) { // findAll cached
				final List<Object> theValues = new LinkedList<Object>();
				final Map<String, List<ConfigFiles>> theLangConfigFiles = new HashMap<String, List<ConfigFiles>>();

				String condition;
				if (inService.getApplication() == null) {
					condition = StringShop.APPLICATION_IS_NULL;
				} else {
					condition = StringShop.APPLICATION_ID_CONDITION;
					theValues.add(inService.getApplication().getId());
				}

				condition += StringShop.CONDITION_ACCUMULATOR + StringShop.LANG_ID_CONDITION;
				theValues.add(aLang.getId());

				final String thePrefix = inService.getPrefix();
				int prefixLen;
				if (thePrefix != null) {
					prefixLen = thePrefix.length();
					condition += StringShop.CONDITION_ACCUMULATOR + StringShop.INDEX_BACK_QUOTED + StringShop.CONDITION_LIKE + net.violet.common.StringShop.SIMPLE_QUOTE + thePrefix + net.violet.common.StringShop.PERCENT + net.violet.common.StringShop.SIMPLE_QUOTE;
					this.LOGGER.info(condition);
				} else {
					prefixLen = 0;
				}

				for (final ConfigFiles theConfigFile : ConfigFilesFactoryImpl.this.findAll(condition, theValues, StringShop.ID_ASC)) {

					if ((theConfigFile.getIndex() == null) || net.violet.common.StringShop.EMPTY_STRING.equals(theConfigFile.getIndex())) {
						theConfigFile.setIndex(inService.name().toUpperCase());
					}
					String theIndex = theConfigFile.getIndex().substring(prefixLen);

					if (SERVICES.WEATHER == inService) {
						final int endIndex = theIndex.indexOf(net.violet.common.StringShop.UNDERSCORE);

						if (endIndex > 0) {
							theIndex = theConfigFile.getIndex().substring(0, endIndex);
						}
					}

					List<ConfigFiles> theList = theLangConfigFiles.get(theIndex);
					if (theList == null) {
						theList = new ArrayList<ConfigFiles>();
						theLangConfigFiles.put(theIndex, theList);
					}

					if (SERVICES.CLOCK_HC == inService) {

						final String checkFile = theConfigFile.getFiles().getPath();
						if (FilesManagerFactory.FILE_MANAGER.fileExists(checkFile)) {
							theList.add(theConfigFile);
						} else {
							this.LOGGER.info(checkFile + " does not exist therefor we get to delete it");
							theConfigFile.getFiles().delete();
						}

					} else {
						theList.add(theConfigFile);
					}
				}

				configFilesByLang.put(aLang, theLangConfigFiles);
			}

			return configFilesByLang;
		}
	};

	/**
	 * Counts the amount of {@link ConfigFiles}s associated with the given
	 * {@link FilesImpl}
	 * 
	 * @param inFiles
	 * @return the amount of {@link ConfigFiles}s associated.
	 */
	public boolean usesFiles(Files inFile) {
		return count(null, StringShop.FILE_ID_CONDITION, Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

	/**
	 * See interface documentation for more information.
	 * The picture files are stored in the configfiles table and have some special properties to be found : they do not have any languages (so the field value is
	 * null) and their index column contains the hardware id.
	 */
	public List<ConfigFiles> findDefaultPicturesByHardware(HARDWARE inHardware) {
		final String theCondition = " lang_id IS NULL AND `index` = ? ";
		final List<Object> theValues = Collections.singletonList((Object) inHardware.getId());
		return findAll(theCondition, theValues, null);
	}

	/**
	 * See interface documentation for more information.
	 * The picture file by hardware and visual is stored in the configfiles table and have some special properties to be found : they do not have any languages (so the field value is
	 * null) and their index column contains the hardware id and the concatenation of underscore and visual id.
	 */
	public ConfigFiles findPicture(HARDWARE inHardware, long inVisual) {
		final String theCondition = " lang_id IS NULL AND `index` = ? ";
		final List<Object> theValues = Collections.singletonList((Object) (inHardware.getId() + net.violet.common.StringShop.UNDERSCORE + inVisual));
		return find(theCondition, theValues);
	}

	public Map<String, List<ConfigFiles>> findAllByServiceAndLang(SERVICES inService, Lang inLang) {
		return this.mFactory.findAllByServiceAndLang(inService, inLang);
	}

}
