package net.violet.platform.datamodel;

import net.violet.db.records.Record;
import net.violet.platform.api.callers.APICaller;

/**
 * Store access keys to the applications.
 * 
 * Applications that uses the APIs must be authorized.
 *
 */
public interface ApplicationCredentials extends Record<ApplicationCredentials> {

	Application getApplication();

	String getPublicKey();

	String getPrivateKey();

	APICaller.CallerClass getCallerClass();

	void setPrivateKey(String inNewPrivateKey);

}
