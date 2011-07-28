package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.factories.ApplicationCredentialsFactory;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;

public class ApplicationCredentialsFactoryMock extends RecordFactoryMock<ApplicationCredentials, ApplicationCredentialsMock> implements ApplicationCredentialsFactory {

	public ApplicationCredentialsFactoryMock() {
		super(ApplicationCredentialsMock.class);
	}

	public ApplicationCredentials findByPublicKey(String inPublicKey) {

		for (final ApplicationCredentials cred : findAll()) {
			if (cred.getPublicKey().equals(inPublicKey)) {
				return cred;
			}
		}

		return null;
	}

	public ApplicationCredentials findByApplication(Application application) {
		for (final ApplicationCredentials cred : findAll()) {
			if (cred.getApplication().equals(application)) {
				return cred;
			}
		}
		return null;
	}

}
