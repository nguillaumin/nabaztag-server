package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.util.StringShop;

public class ConfigFilesImpl extends ObjectRecord<ConfigFiles, ConfigFilesImpl> implements ConfigFiles {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<ConfigFilesImpl> SPECIFICATION = new SQLObjectSpecification<ConfigFilesImpl>("config_files", ConfigFilesImpl.class, new SQLKey("id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { StringShop.LANG_ID, StringShop.APPLICATION_ID, StringShop.FILE_ID, "index", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long id;
	protected Integer lang_id;
	protected Long application_id;
	protected long file_id;
	protected String index;

	private final SingleAssociationNull<ConfigFiles, Lang, LangImpl> mLang;
	private final SingleAssociationNull<ConfigFiles, Application, ApplicationImpl> mApplication;
	private final SingleAssociationNotNull<ConfigFiles, Files, FilesImpl> mFiles;

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ConfigFiles#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<ConfigFilesImpl> getSpecification() {
		return ConfigFilesImpl.SPECIFICATION;
	}

	protected ConfigFilesImpl() {
		this.mLang = new SingleAssociationNull<ConfigFiles, Lang, LangImpl>(this, StringShop.LANG_ID, LangImpl.SPECIFICATION);
		this.mApplication = new SingleAssociationNull<ConfigFiles, Application, ApplicationImpl>(this, StringShop.APPLICATION_ID, ApplicationImpl.SPECIFICATION);
		this.mFiles = new SingleAssociationNotNull<ConfigFiles, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
	}

	public ConfigFilesImpl(Lang inLang, Application inService, Files inFile, String inIndex) throws SQLException {
		this.lang_id = (inLang != null) ? inLang.getId().intValue() : null;
		this.application_id = (inService != null) ? inService.getId() : null;
		this.file_id = inFile.getId();
		this.index = inIndex;
		this.mLang = new SingleAssociationNull<ConfigFiles, Lang, LangImpl>(this, StringShop.LANG_ID, LangImpl.SPECIFICATION);
		this.mApplication = new SingleAssociationNull<ConfigFiles, Application, ApplicationImpl>(this, StringShop.APPLICATION_ID, ApplicationImpl.SPECIFICATION);
		this.mFiles = new SingleAssociationNotNull<ConfigFiles, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);

		init(ConfigFilesImpl.NEW_COLUMNS);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ConfigFiles#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ConfigFiles#getLang()
	 */
	public Lang getLang() {
		if ((this.lang_id != null) && (this.lang_id != 0)) {
			return this.mLang.get(this.lang_id);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ConfigFiles#getService()
	 */
	public Application getApplication() {
		if ((this.application_id != null) && (this.application_id != 0)) {
			return this.mApplication.get(this.application_id);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ConfigFiles#getFiles()
	 */
	public Files getFiles() {
		if (this.file_id != 0) {
			return this.mFiles.get(this.file_id);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ConfigFiles#getIndex()
	 */
	public String getIndex() {
		return this.index;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.ConfigFiles#setIndex(java.lang.String)
	 */
	public void setIndex(String index) {
		this.index = index;
	}
}
