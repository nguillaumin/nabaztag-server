package net.violet.platform.datamodel;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.applets.AppLanguages;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

/**
 * Implémentation pour application_apilib
 */
public class ApplicationApiLibImpl extends ObjectRecord<ApplicationApiLib, ApplicationApiLibImpl> implements ApplicationApiLib {

	private static final Logger LOGGER = Logger.getLogger(ApplicationApiLibImpl.class);

	/**
	 * Specification.
	 */
	public static final SQLObjectSpecification<ApplicationApiLibImpl> SPECIFICATION = new SQLObjectSpecification<ApplicationApiLibImpl>("application_api_lib", ApplicationApiLibImpl.class, new SQLKey[] { new SQLKey("apilib_language", "apilib_version") });

	/**
	 * Fields mapping
	 */
	private static final String[] NEW_COLUMNS = new String[] { "apilib_language", "apilib_version", "apilib_release_date", "apilib_file_id", };

	protected String apilib_language;
	protected String apilib_version;
	protected Timestamp apilib_release_date;
	protected Long apilib_file_id; // can be NULL !!

	/**
	 * References
	 */
	private final SingleAssociationNotNull<ApplicationApiLib, Files, FilesImpl> mCodeFileRef;

	/**
	 * Cache pour le code.
	 */
	private String mCode;
	private byte[] mByteCode;

	@Override
	public SQLObjectSpecification<ApplicationApiLibImpl> getSpecification() {
		return ApplicationApiLibImpl.SPECIFICATION;
	}

	/**
	 * Needed by AbstractRecord.createObject
	 */
	protected ApplicationApiLibImpl() {

		this.mCodeFileRef = new SingleAssociationNotNull<ApplicationApiLib, Files, FilesImpl>(this, "apilib_source_file_id", FilesImpl.SPECIFICATION);
	}

	/**
	 * @param inLanguage
	 * @param inVersion
	 * @param inReleaseNotes
	 * @param inSrcFile
	 * @throws SQLException
	 */
	public ApplicationApiLibImpl(AppLanguages inLanguage, String inVersion, FilesImpl inCodeFile) throws SQLException {

		this.apilib_language = inLanguage.name();
		this.apilib_version = inVersion;
		this.apilib_file_id = inCodeFile.getId();

		this.mCodeFileRef = new SingleAssociationNotNull<ApplicationApiLib, Files, FilesImpl>(this, "apilib_source_file_id", inCodeFile);

		init(ApplicationApiLibImpl.NEW_COLUMNS);
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationApiLib#getLanguage()
	 */
	public AppLanguages getLanguage() {
		return AppLanguages.valueOf(this.apilib_language);
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationApiLib#getApiVersion()
	 */
	public String getApiVersion() {
		return this.apilib_version;
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationApiLib#getReleaseDate()
	 */
	public Date getReleaseDate() {
		return this.apilib_release_date;
	}

	/**
	 * Get the text content of the API library source file
	 * 
	 * @see net.violet.platform.datamodel.ApplicationApiLib#getSources()
	 */
	public String getCode() {
		if (this.mCode == null) {
			final Files srcFile = this.mCodeFileRef.get(this.apilib_file_id);
			// We do not cache here the file content, as underlying Files record
			// may be updated
			this.mCode = FilesManagerFactory.FILE_MANAGER.getTextContent(srcFile);
		}
		return this.mCode;
	}

	public byte[] getByteCode() {
		if (this.mByteCode == null) {
			final Files srcFile = this.mCodeFileRef.get(this.apilib_file_id);
			// We do not cache here the file content, as underlying Files record
			// may be updated
			try {
				this.mByteCode = FilesManagerFactory.FILE_MANAGER.getFilesContent(srcFile);
			} catch (final IOException e) {
				ApplicationApiLibImpl.LOGGER.fatal(e, e);
				this.mByteCode = null;
			}
		}
		return this.mByteCode;
	}

	/**
	 * @see net.violet.platform.datamodel.ApplicationApiLib#getSourceFile()
	 */
	public Files getSourceFile() {
		return this.mCodeFileRef.get(this.apilib_file_id);
	}

	/**
	 * Link a new source file
	 * 
	 * @see net.violet.platform.datamodel.ApplicationApiLib#setSourceFile(net.violet.platform.datamodel.Files)
	 */
	public void setSourceFile(Files inSrcFile, Date inReleaseDate) {
		final Map<String, Object> updateMap = new HashMap<String, Object>();
		setApiLib_file_id(updateMap, inSrcFile.getId());
		setApiLib_release_date(updateMap, inReleaseDate);
		update(updateMap);
	}

	/**
	 * @param updateMap
	 * @param inReleaseDate
	 */
	private void setApiLib_release_date(Map<String, Object> inUpdateMap, Date inReleaseDate) {

		if ((inReleaseDate == null) || !inReleaseDate.equals(this.apilib_release_date)) {
			if (inReleaseDate == null) {
				this.apilib_release_date = null;
			} else {
				this.apilib_release_date = new Timestamp(inReleaseDate.getTime());
			}
			inUpdateMap.put("apilib_release_date", this.apilib_release_date);
		}
	}

	/**
	 * @param updateMap
	 * @param id
	 */
	private void setApiLib_file_id(Map<String, Object> inUpdateMap, long inFileId) {

		if (this.apilib_file_id != inFileId) {
			this.apilib_file_id = inFileId;
			inUpdateMap.put("apilib_file_id", inFileId);
		}
	}
}
