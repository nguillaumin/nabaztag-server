package net.violet.platform.api.callers;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;

public class ObjectAPICaller implements APICaller {

	private final String apiKey;

	public ObjectAPICaller(VObject object, String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAPIKey() {
		return this.apiKey;
	}

	public String getAPIPassword() {
		return null;
	}

	public ApplicationData getApplication() {
		return null;
	}

	public ApplicationClass getApplicationClass() {
		return null;
	}

	public CallerClass getCallerClass() {
		return CallerClass.OBJECT;
	}

	public boolean isApplication() {
		return false;
	}

	public boolean isObject() {
		return true;
	}

	public boolean isPlatformComponent() {
		return false;
	}

}
