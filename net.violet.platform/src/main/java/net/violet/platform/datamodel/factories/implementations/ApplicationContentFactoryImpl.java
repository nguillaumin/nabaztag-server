package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.ApplicationContentImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.factories.ApplicationContentFactory;

import org.apache.log4j.Logger;

public class ApplicationContentFactoryImpl extends RecordFactoryImpl<ApplicationContent, ApplicationContentImpl> implements ApplicationContentFactory {

	private static final Logger LOGGER = Logger.getLogger(ApplicationContentFactoryImpl.class);

	public ApplicationContentFactoryImpl() {
		super(ApplicationContentImpl.SPECIFICATION);
	}

	public ApplicationContent create(Application inApplication, Files inFile) {
		try {
			return new ApplicationContentImpl(inApplication, inFile);
		} catch (final SQLException e) {
			ApplicationContentFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public boolean usesFiles(Files inFile) {
		return count(null, " files_id = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

}
