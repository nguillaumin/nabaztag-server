package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;

public class ApplicationContentImpl extends ObjectRecord<ApplicationContent, ApplicationContentImpl> implements ApplicationContent {

	public static final SQLObjectSpecification<ApplicationContentImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationContentImpl>("application_contents", ApplicationContentImpl.class, new SQLKey[] { new SQLKey("id"), new SQLKey("application_id", "files_id") });

	private static final String[] NEW_COLUMNS = new String[] { "application_id", "files_id" };

	protected long id;
	protected long application_id;
	protected long files_id;

	private final SingleAssociationNotNull<ApplicationContent, Application, ApplicationImpl> application;
	private final SingleAssociationNotNull<ApplicationContent, Files, FilesImpl> files;

	/**
	 * Constructeur par défaut (enregistrement existant).
	 */
	protected ApplicationContentImpl() {
		this.application = new SingleAssociationNotNull<ApplicationContent, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION, ApplicationImpl.SPECIFICATION.getPrimaryKey());
		this.files = new SingleAssociationNotNull<ApplicationContent, Files, FilesImpl>(this, "files_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur à partir d'un id (enregistrement existant).
	 */
	protected ApplicationContentImpl(long inId) throws SQLException {
		init(inId);
		this.application = new SingleAssociationNotNull<ApplicationContent, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION, ApplicationImpl.SPECIFICATION.getPrimaryKey());
		this.files = new SingleAssociationNotNull<ApplicationContent, Files, FilesImpl>(this, "files_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	/**
	 * Constructeur à partir de valeurs
	 */
	public ApplicationContentImpl(Application inApplication, Files inFiles) throws SQLException {
		this.application_id = inApplication.getId();
		this.files_id = inFiles.getId();

		init(ApplicationContentImpl.NEW_COLUMNS);

		this.application = new SingleAssociationNotNull<ApplicationContent, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION, ApplicationImpl.SPECIFICATION.getPrimaryKey());
		this.files = new SingleAssociationNotNull<ApplicationContent, Files, FilesImpl>(this, "files_id", FilesImpl.SPECIFICATION, FilesImpl.SPECIFICATION.getPrimaryKey());
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<ApplicationContentImpl> getSpecification() {
		return ApplicationContentImpl.SPECIFICATION;
	}

	public Application getApplication() {
		return this.application.get(this.application_id);
	}

	public Files getFiles() {
		return this.files.get(this.files_id);
	}

}
