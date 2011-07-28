package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.factories.ApplicationSettingFactory;
import net.violet.platform.datamodel.mock.ApplicationSettingMock;

public class ApplicationSettingFactoryMock extends RecordFactoryMock<ApplicationSetting, ApplicationSettingMock> implements ApplicationSettingFactory {

	ApplicationSettingFactoryMock() {
		super(ApplicationSettingMock.class);
	}

	@Override
	public void loadCache() {
		ApplicationSettingMock.BUILDER.generateValuesFromInitFile(4, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/applicationSettingInit");
	}

	public ApplicationSetting create(Application application, String key, String value) {
		return new ApplicationSettingMock(0, application, key, value);
	}

	public List<ApplicationSetting> findAllByApplication(Application application) {
		final List<ApplicationSetting> settings = new LinkedList<ApplicationSetting>();
		for (final ApplicationSetting aSetting : findAll()) {
			if (aSetting.getApplication().equals(application)) {
				settings.add(aSetting);
			}
		}

		return settings;
	}

	public ApplicationSetting findByApplicationAndKey(Application application, String key) {
		for (final ApplicationSetting aSetting : findAll()) {
			if (aSetting.getApplication().equals(application) && aSetting.getKey().equals(key)) {
				return aSetting;
			}
		}

		return null;
	}

}
