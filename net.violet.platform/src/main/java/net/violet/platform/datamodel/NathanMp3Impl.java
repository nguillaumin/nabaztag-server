package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.util.StringShop;

public class NathanMp3Impl extends ObjectRecord<NathanMp3, NathanMp3Impl> implements NathanMp3 {

	/**
	 * SQL Specification
	 */
	public static final SQLObjectSpecification<NathanMp3Impl> SPECIFICATION = new SQLObjectSpecification<NathanMp3Impl>("nathan_mp3", NathanMp3Impl.class, new SQLKey[] { new SQLKey("fragment_id") });

	/**
	 * SQL Fields
	 */
	protected long fragment_id;
	protected long version_id;
	protected long file_id;
	protected int fragment_offset;

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "version_id", "file_id", "fragment_offset", };

	private final SingleAssociationNotNull<NathanMp3, Files, FilesImpl> file;
	private final SingleAssociationNotNull<NathanMp3, NathanVersion, NathanVersionImpl> mNathanVersion;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected NathanMp3Impl(long id) throws SQLException {
		init(id);

		this.file = new SingleAssociationNotNull<NathanMp3, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
		this.mNathanVersion = new SingleAssociationNotNull<NathanMp3, NathanVersion, NathanVersionImpl>(this, "version_id", NathanVersionImpl.SPECIFICATION);
	}

	protected NathanMp3Impl() {
		this.file = new SingleAssociationNotNull<NathanMp3, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
		this.mNathanVersion = new SingleAssociationNotNull<NathanMp3, NathanVersion, NathanVersionImpl>(this, "version_id", NathanVersionImpl.SPECIFICATION);
	}

	/**
	 * New mp3 creation
	 * 
	 * @param inVersion new mp3 version
	 * @param inFile file linked to this new mp3
	 * @throws SQLException
	 */
	public NathanMp3Impl(NathanVersionImpl inVersion, FilesImpl inFile, int offset) throws SQLException {
		this.version_id = inVersion.getId();
		this.file_id = inFile.getId();
		this.fragment_offset = offset;
		this.file = new SingleAssociationNotNull<NathanMp3, Files, FilesImpl>(this, StringShop.FILE_ID, inFile);
		this.mNathanVersion = new SingleAssociationNotNull<NathanMp3, NathanVersion, NathanVersionImpl>(this, "version_id", inVersion);

		init(NathanMp3Impl.NEW_COLUMNS);
	}

	@Override
	public SQLObjectSpecification<NathanMp3Impl> getSpecification() {
		return NathanMp3Impl.SPECIFICATION;
	}

	@Override
	public Long getId() {
		return this.fragment_id;
	}

	public NathanVersion getNathanVersion() {
		return this.mNathanVersion.get(this.version_id);
	}

	public final Files getFile() {
		return this.file.get(this.file_id);
	}

	public int getOffset() {
		return this.fragment_offset;
	}

	@Override
	protected void doDelete() throws SQLException {
		super.doDelete();
		getFile().scheduleDeletion(); // marks the associated file to be deleted
	}

}
