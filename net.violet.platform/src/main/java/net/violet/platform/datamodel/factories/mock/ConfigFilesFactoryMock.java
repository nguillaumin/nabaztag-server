package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.ConfigFilesImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.ConfigFilesFactory;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ConfigFilesMock;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

public class ConfigFilesFactoryMock extends RecordFactoryMock<ConfigFiles, ConfigFilesMock> implements ConfigFilesFactory {

	{
		ConfigFilesMock.BUILDER.generateValuesFromInitFile(5, net.violet.platform.util.Constantes.OS_PATH + "/net/violet/platform/datamodel/mock/configFilesInit");
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
				final Map<String, List<ConfigFiles>> theLangConfigFiles = new HashMap<String, List<ConfigFiles>>();

				final String thePrefix = inService.getPrefix();
				int prefixLen;
				if (thePrefix != null) {
					prefixLen = thePrefix.length();
				} else {
					prefixLen = 0;
				}

				for (final ConfigFiles theConfigFile : findAllByServiceLangAndIndex(inService, aLang.getId().longValue(), thePrefix)) {

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
						/*
						 * File checkfile = new File(Constantes.RSC_PATH +
						 * theConfigFile.getFiles().getPath());
						 * if(checkfile.exists()){//fichier existe
						 * theList.add(theConfigFile); } else {
						 * LOGGER.info(theConfigFile.getFiles().getPath()+
						 * " does not exist therefor we get delete it");
						 * theConfigFile.getFiles().delete(); }
						 */

						final String checkFile = theConfigFile.getFiles().getPath();
						if (FilesManagerFactory.FILE_MANAGER.fileExists(checkFile)) {
							theList.add(theConfigFile);
						} else {
							this.LOGGER.info(checkFile + " does not exist therefor we get delete it");
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

	@Override
	public void loadCache() {
		ConfigFilesMock.BUILDER.generateValuesFromInitFile(5, net.violet.platform.util.Constantes.OS_PATH + "/net/violet/platform/datamodel/mock/configFilesInit");
	}

	public ConfigFilesFactoryMock() {
		super(ConfigFilesMock.class);
	}

	public boolean usesFiles(Files inFile) {

		for (final ConfigFiles aConfigFiles : findAllMapped().values()) {
			if (((ConfigFilesMock) aConfigFiles).getFile_id().equals(inFile.getId())) {
				return true;
			}
		}
		return false;
	}

	private SortedSet<ConfigFiles> findAllByServiceLangAndIndex(SERVICES inService, long inLangId, String inIndex) {
		final SortedSet<ConfigFiles> theSet = new TreeSet<ConfigFiles>(new Comparator<ConfigFiles>() {

			public int compare(ConfigFiles o1, ConfigFiles o2) {
				return o1.getId().compareTo(o2.getId());
			}

		});

		for (final ConfigFiles aConfigFile : findAllMapped().values()) {
			if (((inIndex == null) || ((aConfigFile.getIndex() != null) && aConfigFile.getIndex().startsWith(inIndex))) && (((aConfigFile.getApplication() == null) && (aConfigFile.getApplication() == inService.getApplication())) || ((aConfigFile.getApplication() != null) && aConfigFile.getApplication().getId().equals(inService.getApplication().getId()))) && (inLangId == aConfigFile.getLang().getId().longValue())) {
				theSet.add(aConfigFile);
			}
		}

		return theSet;
	}

	public List<ConfigFiles> findDefaultPicturesByHardware(HARDWARE inHardware) {
		final List<ConfigFiles> theResult = new LinkedList<ConfigFiles>();
		for (final ConfigFiles aConfigFile : findAll()) {
			if ((aConfigFile.getLang() == null) && aConfigFile.getIndex().equals(inHardware.getId().toString())) {
				theResult.add(aConfigFile);
			}
		}

		return theResult;
	}

	public ConfigFiles findPicture(HARDWARE inHardware, long inVisual) {
		ConfigFiles theResult = null;
		for (final ConfigFiles aConfigFile : findAll()) {
			if ((aConfigFile.getLang() == null) && aConfigFile.getIndex().equals(inHardware.getId() + net.violet.common.StringShop.UNDERSCORE + inVisual)) {
				theResult = aConfigFile;
				break;
			}
		}

		return theResult;
	}

	public Map<String, List<ConfigFiles>> findAllByServiceAndLang(SERVICES inService, Lang inLang) {
		return this.mFactory.findAllByServiceAndLang(inService, inLang);
	}
}
