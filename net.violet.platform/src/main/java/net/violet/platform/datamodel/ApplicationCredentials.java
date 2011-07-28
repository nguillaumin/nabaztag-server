package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.api.callers.APICaller;

public interface ApplicationCredentials extends Record<ApplicationCredentials> {

	Application getApplication();

	String getPublicKey();

	String getPrivateKey();

	APICaller.CallerClass getCallerClass();

	void setPrivateKey(String inNewPrivateKey);

}
