package net.violet.platform.api.callers;

import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.ApplicationData;

public abstract class AbstractComponentAPICaller implements APICaller {

	/**
	 * @see net.violet.platform.api.callers.APICaller
	 */

	private final String privateKey = "componentKey";

	public CallerClass getCallerClass() {
		return APICaller.CallerClass.COMPONENT;
	}

	public boolean isPlatformComponent() {
		return true;
	}

	public boolean isApplication() {
		return false;
	}

	public boolean isObject() {
		return false;
	}

	public ApplicationData getApplication() {
		return null;
	}

	public ApplicationClass getApplicationClass() {
		return null;
	}

	public String getAPIPassword() {
		return this.privateKey;
	}
}
