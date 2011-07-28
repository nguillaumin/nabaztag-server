package net.violet.platform.journal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.maps.LoggerEntryMap;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionLogData;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.cache.Cache;
import net.violet.platform.util.cache.GenerationException;
import net.violet.platform.util.cache.ValueGenerator;

import org.apache.log4j.Logger;

public class FilesPersistenceManager implements LoggerPersistenceManager {

	private static final Logger LOGGER = Logger.getLogger(FilesPersistenceManager.class);

	private static final FilesPersistenceManager MANAGER = new FilesPersistenceManager();

	public static LoggerPersistenceManager getInstance() {
		return FilesPersistenceManager.MANAGER;
	}

	private static final Cache<SubscriptionData, List<JournalEntry>> LOGS_CACHE = new Cache<SubscriptionData, List<JournalEntry>>(new ValueGenerator<SubscriptionData, List<JournalEntry>>() {

		public List<JournalEntry> generateValue(SubscriptionData key) throws GenerationException {
			final SubscriptionLogData theLog = SubscriptionLogData.getBySubscription(key);
			if ((theLog == null) || !theLog.isValid() || (theLog.getLogFile() == null) || !theLog.getLogFile().isValid()) {
				throw new GenerationException("Failed to create the SubscriptionLog object");
			}

			List<Map<String, Object>> theLogContent = null;
			try {
				theLogContent = (List<Map<String, Object>>) ConverterFactory.JSON.convertFrom(FilesManagerFactory.FILE_MANAGER.getReaderFor(theLog.getLogFile().getReference()));
			} catch (final ConversionException e) {
				FilesPersistenceManager.LOGGER.fatal(e, e);
				throw new GenerationException(e.getMessage(), e);
			} catch (final IOException e) {
				FilesPersistenceManager.LOGGER.fatal(e, e);
				throw new GenerationException(e.getMessage(), e);
			}

			final List<JournalEntry> theEntries = new ArrayList<JournalEntry>(theLogContent.size());
			for (final Map<String, Object> anEntry : theLogContent) {
				theEntries.add(new JournalEntry((Long) anEntry.get(LoggerEntryMap.WHEN), (String) anEntry.get(LoggerEntryMap.WHAT)));
			}
			return theEntries;
		}
	});

	public List<JournalEntry> getEntries(SubscriptionData subscription) {
		try {
			return FilesPersistenceManager.LOGS_CACHE.get(subscription);
		} catch (final GenerationException e) {
			FilesPersistenceManager.LOGGER.fatal(e, e);
		}

		return null;
	}

	public void writeEntries(List<JournalEntry> entries, SubscriptionData subscription) {
		final SubscriptionLogData theLog = SubscriptionLogData.getBySubscription(subscription);
		final FilesData theLogFile = theLog.getLogFile();

		final List<LoggerEntryMap> theEntries = new ArrayList<LoggerEntryMap>(entries.size());
		for (final JournalEntry anEntry : entries) {
			theEntries.add(new LoggerEntryMap(anEntry, false));
		}

		try {
			final String content = ConverterFactory.JSON.convertTo(theEntries, false);
			FilesManagerFactory.FILE_MANAGER.writeContentTo(new ByteArrayInputStream(content.getBytes("UTF-8")), theLogFile.getReference());
		} catch (final IOException e) {
			FilesPersistenceManager.LOGGER.fatal(e, e);
		} catch (final ConversionException e) {
			FilesPersistenceManager.LOGGER.fatal(e, e);
		}

		FilesPersistenceManager.LOGS_CACHE.put(subscription, entries);
	}

}
