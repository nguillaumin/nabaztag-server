package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.AnnuImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.AnnuFactory;

import org.apache.log4j.Logger;

public class AnnuFactoryImpl extends RecordFactoryImpl<Annu, AnnuImpl> implements AnnuFactory {

	AnnuFactoryImpl() {
		super(AnnuImpl.SPECIFICATION);
	}

	private static final Logger LOGGER = Logger.getLogger(AnnuFactoryImpl.class);

	public boolean usesFiles(Files inFile) {
		return count(null, " picture_file_id = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

	public Annu create(User user, String country, Lang lang) {
		try {
			return new AnnuImpl(user, country, lang);
		} catch (final SQLException e) {
			AnnuFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

}
