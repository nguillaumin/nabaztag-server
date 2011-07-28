package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class ApplicationSettingData extends AbstractSettingRecordData<ApplicationSetting> {

	private static final Logger LOGGER = Logger.getLogger(ApplicationSettingData.class);

	public static ApplicationSettingData getData(ApplicationSetting setting) {
		try {
			return RecordData.getData(setting, ApplicationSettingData.class, ApplicationSetting.class);
		} catch (final InstantiationException e) {
			ApplicationSettingData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ApplicationSettingData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			ApplicationSettingData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			ApplicationSettingData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected ApplicationSettingData(ApplicationSetting inRecord) {
		super(inRecord);
	}

	@Override
	protected void update(String inKey, String inValue) {
		if (getRecord() != null) {
			getRecord().setKey(inKey);
			getRecord().setValue(inValue);
		}

	}

	private static List<ApplicationSettingData> generateList(List<ApplicationSetting> inList) {
		final List<ApplicationSettingData> theSettings = new LinkedList<ApplicationSettingData>();
		for (final ApplicationSetting aSetting : inList) {
			theSettings.add(ApplicationSettingData.getData(aSetting));
		}

		return theSettings;
	}

	public static List<ApplicationSettingData> findAllByApplication(ApplicationData application) {
		return ApplicationSettingData.generateList(Factories.APPLICATION_SETTING.findAllByApplication(application.getRecord()));
	}

	public static ApplicationSettingData findByApplicationAndKey(ApplicationData application, String key) {
		return ApplicationSettingData.getData(Factories.APPLICATION_SETTING.findByApplicationAndKey(application.getRecord(), key));
	}

	public String getValue() {
		if (getRecord() != null) {
			return getRecord().getValue();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public void setValue(String inValue) {
		getRecord().setValue(inValue);
	}

}
