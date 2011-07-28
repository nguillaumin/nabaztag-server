package net.violet.platform.datamodel.factories.mock;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionLog;
import net.violet.platform.datamodel.factories.SubscriptionLogFactory;
import net.violet.platform.datamodel.mock.SubscriptionLogMock;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

public class SubscriptionLogFactoryMock extends RecordFactoryMock<SubscriptionLog, SubscriptionLogMock> implements SubscriptionLogFactory {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionLogFactoryMock.class);

	private static byte[] EMPTY_FILE;

	static {
		try {
			SubscriptionLogFactoryMock.EMPTY_FILE = "[]".getBytes("UTF-8");
		} catch (final UnsupportedEncodingException e) {
			SubscriptionLogFactoryMock.LOGGER.fatal(e, e);
			SubscriptionLogFactoryMock.EMPTY_FILE = "[]".getBytes();
		}
	}

	SubscriptionLogFactoryMock() {
		super(SubscriptionLogMock.class);
	}

	public SubscriptionLog create(Subscription subscription) {
		try {
			final SubscriptionLog theLog = new SubscriptionLogMock(subscription);
			FilesManagerFactory.FILE_MANAGER.writeContentTo(new ByteArrayInputStream(SubscriptionLogFactoryMock.EMPTY_FILE), theLog.getLogFile());
			return theLog;
		} catch (final IOException e) {
			SubscriptionLogFactoryMock.LOGGER.fatal(e, e);
		}

		return null;

	}

	public boolean usesFiles(Files inFile) {

		for (final SubscriptionLog aLog : findAllMapped().values()) {
			if (aLog.getLogFile().getId().equals(inFile.getId())) {
				return true;
			}
		}
		return false;
	}
}
