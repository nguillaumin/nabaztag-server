package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;

public interface ApplicationCredentialsFactory extends RecordFactory<ApplicationCredentials> {

	ApplicationCredentials findByPublicKey(String inPublicKey);

	ApplicationCredentials findByApplication(Application application);

}
