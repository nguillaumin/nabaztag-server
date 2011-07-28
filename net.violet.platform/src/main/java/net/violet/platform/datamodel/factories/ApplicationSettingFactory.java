package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;

public interface ApplicationSettingFactory extends RecordFactory<ApplicationSetting> {

	ApplicationSetting create(Application application, String key, String value);

	List<ApplicationSetting> findAllByApplication(Application application);

	ApplicationSetting findByApplicationAndKey(Application application, String key);

}
