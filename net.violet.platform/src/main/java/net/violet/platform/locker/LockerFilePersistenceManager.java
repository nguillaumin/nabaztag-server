package net.violet.platform.locker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author christophe - Violet
 */
public class LockerFilePersistenceManager implements LockerPersistenceManager {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(LockerService.class);

	// path to the locker file in the File Manager
	private static final String LOCKERS_FILE_PATH = "applications/services/locker.json";
	private static final long LOCKERS_FILE_ID = 87659151;

	// singleton (TERRACOTTA ROOT)
	private static LockerFilePersistenceManager mSingleInstance;

	// instances variables
	private final ConcurrentMap<String, LockerEntry> mSecretsMap;
	private final Files mSecretsFile;

//  STATIC SINGLETON ACCESSOR -----------------------------------------------80

	public static synchronized LockerPersistenceManager getInstance() {

		if (LockerFilePersistenceManager.mSingleInstance == null) {
			LockerFilePersistenceManager.mSingleInstance = new LockerFilePersistenceManager();
		}
		return LockerFilePersistenceManager.mSingleInstance;
	}

//  PRIVATE CONSTRUCTOR (SINGLETON PATTERN) ---------------------------------80

	/**
	 * Load the content of the serialized json file
	 * and build the locker's map {key:content} 
	 */
	private LockerFilePersistenceManager() {

		this.mSecretsFile = Factories.FILES.find(LockerFilePersistenceManager.LOCKERS_FILE_ID);
		this.mSecretsMap = new ConcurrentHashMap<String, LockerEntry>(128);

		boolean mustUpdateFile = false;

		try {
			final String jsonLockerContent = FilesManagerFactory.FILE_MANAGER.getTextContent(this.mSecretsFile);

			if (StringUtils.isEmpty(jsonLockerContent)) {
				// the file desn't yet exists
				mustUpdateFile = true;

			} else {
				final Map<String, Object> restoredSecrets = ConverterFactory.JSON.convertFrom(jsonLockerContent);

				for (final String key : restoredSecrets.keySet()) {
					final Map<String, Object> secretMap = (Map<String, Object>) restoredSecrets.get(key);
					final LockerEntry restoredSecret = new LockerEntry(secretMap);
					if (!restoredSecret.hasExpired()) {
						this.mSecretsMap.put(key, restoredSecret);
					} else {
						// we have detected expired content on loading
						mustUpdateFile = true;
					}
				}
			}

		} catch (final Exception e) {
			LockerFilePersistenceManager.LOGGER.fatal("Unable to restore locker from file " + LockerFilePersistenceManager.LOCKERS_FILE_PATH, e);
			// rewriting the file should correct errors
			mustUpdateFile = true;
		}

		if (mustUpdateFile) {
			updateStorage();
		}
	}

	/**
	 * Save the locker's secret content back to persistence file
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	private void updateStorage() {
		try {
			final String serialized = ConverterFactory.JSON.convertTo(this.mSecretsMap, false);
			FilesManagerFactory.FILE_MANAGER.writeContentTo(new ByteArrayInputStream(serialized.getBytes("UTF-8")), this.mSecretsFile);

		} catch (final Exception e) {
			LockerFilePersistenceManager.LOGGER.fatal("Writing to locker's file (" + LockerFilePersistenceManager.LOCKERS_FILE_PATH + ")resulted in error !", e);
		}
	}

//  IMPLEMENTATION OF LockerPersistenceManager METHODS-----------------------80

	/**
	 * @see net.violet.platform.locker.LockerPersistenceManager#get(java.lang.String)
	 */
	public LockerEntry get(String inKey) {

		return this.mSecretsMap.get(inKey);
	}

	/**
	 * @see net.violet.platform.locker.LockerPersistenceManager#remove(java.lang.String)
	 */
	public LockerEntry remove(String inKey) {

		final LockerEntry entry = this.mSecretsMap.remove(inKey);
		updateStorage();
		return entry;
	}

	/**
	 * @see net.violet.platform.locker.LockerPersistenceManager#update(java.lang.String, net.violet.platform.locker.LockerEntry)
	 */
	public void update(String inKey, LockerEntry inEntry) {

		this.mSecretsMap.put(inKey, inEntry);
		updateStorage();
	}

}
