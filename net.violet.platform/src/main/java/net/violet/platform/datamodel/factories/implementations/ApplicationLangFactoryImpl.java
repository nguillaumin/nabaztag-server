package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationLang;
import net.violet.platform.datamodel.ApplicationLangImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.ApplicationLangFactory;

/**
 *
 * @author christophe - Violet
 */
public class ApplicationLangFactoryImpl implements ApplicationLangFactory {

	/* (non-Javadoc)
	 * @see net.violet.platform.datamodel.factories.common.RecordFactory#findAllMapped()
	 */
	public ApplicationLang find(Application inApp, Lang inLang) {
		try {
			return AbstractSQLRecord.find(ApplicationLangImpl.SPECIFICATION, "application_id=? and lang_id=?", Arrays.asList((Object) inApp.getId(), inLang.getId()));
		} catch (final SQLException e) {
			
			e.printStackTrace();
		}
		// TODO throw a framework exception
		return null;
	}

}
