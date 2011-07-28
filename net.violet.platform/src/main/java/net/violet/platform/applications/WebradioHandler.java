package net.violet.platform.applications;

import java.util.Map;

import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;

public class WebradioHandler extends DefaultHandler {

	protected WebradioHandler(ApplicationData application) {
		super(application);
	}

	@Override
	public void checkSettings(VObjectData object, Map<String, Object> settings) throws InvalidSettingException {
		if (object.getObjectType().instanceOf(ObjectType.NABAZTAG_V1)) {
			throw new InvalidSettingException("type", "v1");
		}

	}

}
