package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.api.callers.APICaller.CallerClass;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;

public class ApplicationCredentialsMock extends AbstractMockRecord<ApplicationCredentials, ApplicationCredentialsMock> implements ApplicationCredentials {

	private final Application mApplication;
	private String mPrivateKey;
	private final String mPublicKey;
	private final String mRole;

	public ApplicationCredentialsMock(String inPublicKey, String inPrivateKey, Application inApplication) {
		super(inApplication.getId());
		this.mApplication = inApplication;
		this.mPrivateKey = inPrivateKey;
		this.mPublicKey = inPublicKey;
		this.mRole = "APPLICATION";
	}

	public Application getApplication() {
		return this.mApplication;
	}

	public String getPrivateKey() {
		return this.mPrivateKey;
	}

	public String getPublicKey() {
		return this.mPublicKey;
	}

	public CallerClass getCallerClass() {
		return CallerClass.getByName(this.mRole);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.ApplicationCredentials#setPrivateKey(java
	 * .lang.String)
	 */
	public void setPrivateKey(String inNewPrivateKey) {
		this.mPrivateKey = inNewPrivateKey;
	}

}
