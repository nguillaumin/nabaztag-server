package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.ApplicationSettingImpl;
import net.violet.platform.datamodel.factories.ApplicationSettingFactory;

import org.apache.log4j.Logger;

public class ApplicationSettingFactoryImpl extends RecordFactoryImpl<ApplicationSetting, ApplicationSettingImpl> implements ApplicationSettingFactory {

	private static Logger LOGGER = Logger.getLogger(ApplicationSettingFactoryImpl.class);

	public ApplicationSettingFactoryImpl() {
		super(ApplicationSettingImpl.SPECIFICATION);
	}

	public ApplicationSetting create(Application application, String key, String value) {
		try {
			return new ApplicationSettingImpl(application, key, value);
		} catch (final SQLException e) {
			ApplicationSettingFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public List<ApplicationSetting> findAllByApplication(Application application) {
		final String condition = " application_id = ? ";
		final List<Object> values = Collections.singletonList((Object) application.getId());
		return findAll(condition, values);
	}

	public ApplicationSetting findByApplicationAndKey(Application application, String key) {
		final String condition = " application_id = ? AND `key` = ? ";
		final List<Object> values = Arrays.asList((Object) application.getId(), key);
		return find(condition, values);
	}

}
