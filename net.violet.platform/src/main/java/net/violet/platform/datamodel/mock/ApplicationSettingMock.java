package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.factories.Factories;

public class ApplicationSettingMock extends AbstractMockRecord<ApplicationSetting, ApplicationSettingMock> implements ApplicationSetting {

	public static final MockBuilder<ApplicationSetting> BUILDER = new MockBuilder<ApplicationSetting>() {

		@Override
		protected ApplicationSetting build(String[] inParamValues) {
			return new ApplicationSettingMock(Long.parseLong(inParamValues[0]), Long.parseLong(inParamValues[1]), inParamValues[2], inParamValues[3]);
		}
	};

	private String mKey;
	private String mValue;
	private final long applicationId;
	private Application mApplication;

	public ApplicationSettingMock(long inId, Application inApplication, String inKey, String inValue) {
		super(inId);
		this.mKey = inKey;
		this.mValue = inValue;
		this.mApplication = inApplication;
		this.applicationId = inApplication.getId();
	}

	public ApplicationSettingMock(long inId, long inApplicationId, String inKey, String inValue) {
		super(inId);
		this.applicationId = inApplicationId;
		this.mKey = inKey;
		this.mValue = inValue;
	}

	public Application getApplication() {
		if (this.mApplication == null) {
			this.mApplication = Factories.APPLICATION.find(this.applicationId);
		}
		return this.mApplication;
	}

	public String getKey() {
		return this.mKey;
	}

	public String getValue() {
		return this.mValue;
	}

	public void setKey(String key) {
		this.mKey = key;
	}

	public void setValue(String value) {
		this.mValue = value;
	}

}
