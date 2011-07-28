package net.violet.platform.datamodel.factories.implementations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionLog;
import net.violet.platform.datamodel.SubscriptionLogImpl;
import net.violet.platform.datamodel.factories.SubscriptionLogFactory;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

public class SubscriptionLogFactoryImpl extends RecordFactoryImpl<SubscriptionLog, SubscriptionLogImpl> implements SubscriptionLogFactory {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionLogFactoryImpl.class);

	private static byte[] EMPTY_FILE;

	static {
		try {
			SubscriptionLogFactoryImpl.EMPTY_FILE = "[]".getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e) {
			SubscriptionLogFactoryImpl.LOGGER.fatal(e, e);
			SubscriptionLogFactoryImpl.EMPTY_FILE = "[]".getBytes();
		}
	}

	SubscriptionLogFactoryImpl() {
		super(SubscriptionLogImpl.SPECIFICATION);
	}

	public SubscriptionLog create(Subscription subscription) {
		try {
			final SubscriptionLog theLog = new SubscriptionLogImpl(subscription);
			FilesManagerFactory.FILE_MANAGER.writeContentTo(new ByteArrayInputStream(SubscriptionLogFactoryImpl.EMPTY_FILE), theLog.getLogFile());
		} catch (final SQLException e) {
			SubscriptionLogFactoryImpl.LOGGER.fatal(e, e);
		} catch (final IOException e) {
			SubscriptionLogFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public boolean usesFiles(Files inFile) {
		return count(null, "log_file_id = ?", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

}
