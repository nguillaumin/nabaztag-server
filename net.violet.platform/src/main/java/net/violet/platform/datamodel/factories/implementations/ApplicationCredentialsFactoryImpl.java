package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.ApplicationCredentialsImpl;
import net.violet.platform.datamodel.factories.ApplicationCredentialsFactory;

public class ApplicationCredentialsFactoryImpl extends RecordFactoryImpl<ApplicationCredentials, ApplicationCredentialsImpl> implements ApplicationCredentialsFactory {

	public ApplicationCredentialsFactoryImpl() {
		super(ApplicationCredentialsImpl.SPECIFICATION);
	}

	public ApplicationCredentials findByPublicKey(String inPublicKey) {
		return find(" public_key = ? ", Arrays.asList((Object) inPublicKey));
	}

	public ApplicationCredentials findByApplication(Application inApplication) {
		return find(" application_id = ? ", Arrays.asList((Object) inApplication.getId()));
	}

}
