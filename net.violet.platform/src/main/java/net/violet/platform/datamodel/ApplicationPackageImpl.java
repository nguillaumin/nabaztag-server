package net.violet.platform.datamodel;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.NoSuchElementException;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.SQLSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.util.UpdateMap;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

/**
 * An application package contains links to the source files, resources.. and
 * give informations about the script language used
 */
public class ApplicationPackageImpl extends ObjectRecord<ApplicationPackage, ApplicationPackageImpl> implements ApplicationPackage {

	private static final Logger LOGGER = Logger.getLogger(ApplicationPackageImpl.class);

	/**
	 * Specification.
	 */
	public static final SQLObjectSpecification<ApplicationPackageImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationPackageImpl>("application_package", ApplicationPackageImpl.class, new SQLKey[] { new SQLKey("application_id") }, "application_modification_date");

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "application_id", "apilib_language", "apilib_version", "application_source_file_id", "application_modification_date" };

	protected long application_id;
	protected String apilib_language;
	protected String apilib_version;
	protected Long application_source_file_id;
	protected Timestamp application_modification_date;

	/**
	 * Le fichier source
	 */
	private final SingleAssociationNotNull<ApplicationPackage, Files, FilesImpl> mFiles;

	/**
	 * Reference à l'application parente
	 */
	private final SingleAssociationNotNull<ApplicationPackage, Application, ApplicationImpl> mApplication;

	/**
	 * IMPORTANT : This empty constructor is required for the to
	 * Object-Relational Mapping API to work
	 * 
	 * @see AbstractSQLRecord#createObject(SQLSpecification, java.sql.ResultSet)
	 */
	protected ApplicationPackageImpl() {

		this.mApplication = new SingleAssociationNotNull<ApplicationPackage, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);

		this.mFiles = new SingleAssociationNotNull<ApplicationPackage, Files, FilesImpl>(this, "application_source_file_id", FilesImpl.SPECIFICATION);
	}

	/**
	 * @param appId
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	protected ApplicationPackageImpl(long appId) throws NoSuchElementException, SQLException {
		init(appId);

		this.mApplication = new SingleAssociationNotNull<ApplicationPackage, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);
		this.mFiles = new SingleAssociationNotNull<ApplicationPackage, Files, FilesImpl>(this, "application_source_file_id", FilesImpl.SPECIFICATION);
	}

	/**
	 * @param id
	 * @param lang
	 * @param srcFileId
	 * @throws SQLException
	 */
	public ApplicationPackageImpl(Application inApplication, AppLanguages inLanguage, String version, Files inSourceFile) throws SQLException {
		this.application_id = inApplication.getId();
		this.apilib_language = inLanguage.name();
		this.apilib_version = version;
		this.application_source_file_id = inSourceFile.getId();

		this.mApplication = new SingleAssociationNotNull<ApplicationPackage, Application, ApplicationImpl>(this, "application_id", ApplicationImpl.SPECIFICATION);

		this.mFiles = new SingleAssociationNotNull<ApplicationPackage, Files, FilesImpl>(this, "application_source_file_id", FilesImpl.SPECIFICATION);
		init(ApplicationPackageImpl.NEW_COLUMNS);
	}

	@Override
	public SQLObjectSpecification<ApplicationPackageImpl> getSpecification() {
		return ApplicationPackageImpl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.application_id;
	}

	public Timestamp getModificationDate() {
		return this.application_modification_date;
	}

	public AppLanguages getLanguage() {
		return AppLanguages.findByLabel(this.apilib_language);
	}

	public String getApiVersion() {
		return this.apilib_version;
	}

	/**
	 * Get a reference to the source file
	 */
	public Files getSourceFile() {
		return this.mFiles.get(this.application_source_file_id);
	}

	/**
	 * Get the binary data content of the source file
	 * 
	 * @return
	 * @throws IOException
	 */
	private byte[] getByteSource() {
		final Files src = this.mFiles.get(this.application_source_file_id);
		try {
			return FilesManagerFactory.FILE_MANAGER.getFilesContent(src);
		} catch (final IOException e) {
			ApplicationPackageImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Get the text content of the source file
	 * 
	 * @return
	 */
	public String getSource() {
		final byte[] bContent = getByteSource();
		return (bContent == null) ? net.violet.common.StringShop.EMPTY_STRING : new String(bContent);
	}

	/**
	 * @return the parent Application
	 */
	public Application getApplication() {
		return this.mApplication.get(this.application_id);
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationPackage#getApiLib()
	 */
	public ApplicationApiLib getApiLib() {
		return Factories.APPLICATION_API_LIB.findByLanguageAndVersion(getLanguage(), this.apilib_version);
	}

	/**
	 * Update the application source file and optionaly the API version used
	 * 
	 * @see net.violet.platform.datamodel.ApplicationPackage#updateSourceFile(net.violet.platform.datamodel.Files,
	 *      java.sql.Timestamp)
	 */
	public void updateSourceFile(Files newSourceFile, String inApiVersion) {

		final UpdateMap updateMap = new UpdateMap();
		this.mFiles.set(newSourceFile);
		this.apilib_version = updateMap.updateField("apilib_version", this.apilib_version, inApiVersion);
		update(updateMap);
	}

}
