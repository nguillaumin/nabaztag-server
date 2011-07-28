package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.api.authentication.AuthenticationManager;
import net.violet.platform.api.callers.APICaller.CallerClass;
import net.violet.platform.datamodel.util.UpdateMap;

public class ApplicationCredentialsImpl extends ObjectRecord<ApplicationCredentials, ApplicationCredentialsImpl> implements ApplicationCredentials {

	/**
	 * Specification.
	 */
	public static final SQLObjectSpecification<ApplicationCredentialsImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationCredentialsImpl>("application_credentials", ApplicationCredentialsImpl.class, new SQLKey[] { new SQLKey("public_key") });

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "public_key", "private_key", "digested_key", "application_id", };

	protected String public_key;
	protected long application_id;
	protected String private_key;
	protected String application_role;
	protected String digested_key;

	private final SingleAssociationNotNull<ApplicationCredentials, Application, ApplicationImpl> mApplication;

	protected ApplicationCredentialsImpl() {
		this.mApplication = new SingleAssociationNotNull<ApplicationCredentials, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	protected ApplicationCredentialsImpl(long id) throws NoSuchElementException, SQLException {
		init(id);
		this.mApplication = new SingleAssociationNotNull<ApplicationCredentials, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	public ApplicationCredentialsImpl(String publicKey, String privateKey, Application inApplication) throws SQLException {

		this.public_key = publicKey;
		this.private_key = privateKey;
		this.digested_key = AuthenticationManager.getDigestedKey(publicKey, privateKey);
		this.application_id = inApplication.getId();

		init(ApplicationCredentialsImpl.NEW_COLUMNS);

		this.mApplication = new SingleAssociationNotNull<ApplicationCredentials, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
	}

	@Override
	public SQLObjectSpecification<ApplicationCredentialsImpl> getSpecification() {
		return ApplicationCredentialsImpl.SPECIFICATION;
	}

	public Application getApplication() {
		return this.mApplication.get(this.application_id);
	}

	public String getPublicKey() {
		return this.public_key;
	}

	public CallerClass getCallerClass() {
		return CallerClass.getByName(this.application_role);
	}

	public String getPrivateKey() {
		return this.private_key;
	}

	public void setPrivateKey(String inNewValue) {

		final UpdateMap updateMap = new UpdateMap();
		this.private_key = updateMap.updateField("private_key", this.private_key, inNewValue);
		final String newDigested_key = AuthenticationManager.getDigestedKey(this.public_key, inNewValue);
		this.digested_key = updateMap.updateField("digested_key", this.digested_key, newDigested_key);

		update(updateMap);
	}

}
