package net.violet.platform.datamodel;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.util.UpdateMap;

import org.apache.log4j.Logger;

public class FilesImpl extends ObjectRecord<Files, FilesImpl> implements Files {

	private static final Logger LOGGER = Logger.getLogger(FilesImpl.class);
	private static FilesCommon FILES_COMMON = new FilesCommon();

	public static final SQLObjectSpecification<FilesImpl> SPECIFICATION = new SQLObjectSpecification<FilesImpl>("files", FilesImpl.class, new SQLKey("id"));

	private static final String[] NEW_COLUMNS = new String[] { "path", "bestBefore", "mime_type_id" };

	protected long id;
	protected String path;
	protected Timestamp creation_date;
	protected Timestamp bestBefore;
	protected long mime_type_id;

	private final SingleAssociationNotNull<Files, MimeType, MimeTypeImpl> mimeType;

	protected FilesImpl(long inId) throws SQLException {
		init(inId);

		this.mimeType = new SingleAssociationNotNull<Files, MimeType, MimeTypeImpl>(this, "mime_type_id", MimeTypeImpl.SPECIFICATION);
	}

	protected FilesImpl() {
		this.mimeType = new SingleAssociationNotNull<Files, MimeType, MimeTypeImpl>(this, "mime_type_id", MimeTypeImpl.SPECIFICATION);
	}

	/**
	 * Construtor to create new insert in the database. This method must not be
	 * called, it is for the HadoopManager usage only. To create a new Files
	 * object use a method from the HadoopManager class.
	 * 
	 * @param inPath path to the {@link File}
	 * @param inType mime type of the {@link Files}
	 * @throws SQLException
	 */
	public FilesImpl(String inPath, MimeType.MIME_TYPES inType) throws SQLException {
		this.path = inPath;
		this.mime_type_id = inType.getId();

		this.bestBefore = new Timestamp(System.currentTimeMillis() + Files.FilesCommon.TIME_BEFORE_DELETION);

		init(FilesImpl.NEW_COLUMNS);

		this.mimeType = new SingleAssociationNotNull<Files, MimeType, MimeTypeImpl>(this, "mime_type_id", MimeTypeImpl.SPECIFICATION);
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public SQLObjectSpecification<FilesImpl> getSpecification() {
		return FilesImpl.SPECIFICATION;
	}

	public final String getPath2chor() {
		return FilesImpl.FILES_COMMON.getPath2chor(this);
	}

	public final String getPath2adp() {
		return FilesImpl.FILES_COMMON.getPath2adp(this);
	}

	public final String getPath2midi() {
		return FilesImpl.FILES_COMMON.getPath2midi(this);
	}

	public Timestamp getBestBefore() {
		return this.bestBefore;
	}

	public void setMp3Path(String inPath2Mp3) {
		final UpdateMap updateMap = new UpdateMap();
		this.mime_type_id = updateMap.updateField("mime_type_id", this.mime_type_id, MimeType.MIME_TYPES.A_MPEG.getId());
		this.path = updateMap.updateField("path", this.path, inPath2Mp3);
		update(updateMap);
	}

	public void setChorPath(String inPath2Chor) {
		final UpdateMap updateMap = new UpdateMap();
		this.mime_type_id = updateMap.updateField("mime_type_id", this.mime_type_id, MimeType.MIME_TYPES.CHOR.getId());
		this.path = updateMap.updateField("path", this.path, inPath2Chor);
		update(updateMap);
	}

	/**
	 * @param bestBefore the bestBefore to set
	 */

	public void scheduleDeletion() {
		final UpdateMap updateMap = new UpdateMap();
		this.bestBefore = updateMap.updateField("bestBefore", this.bestBefore, new Timestamp(System.currentTimeMillis() + Files.FilesCommon.TIME_BEFORE_DELETION));
		update(updateMap);
	}

	public void unScheduleDeletion() {
		final UpdateMap updateMap = new UpdateMap();
		this.bestBefore = updateMap.updateField("bestBefore", this.bestBefore, null);
		update(updateMap);
	}

	public boolean isOrphan() {
		return FilesImpl.FILES_COMMON.isFileOrphan(this);
	}

	public static int walkRsc(RecordWalker<Files> inWalker) {
		final String condition = " path <> \"\" AND path LIKE \"broadcast/broad%\"";

		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(FilesImpl.SPECIFICATION, condition, null, null, 0, inWalker);
		} catch (final SQLException anException) {
			FilesImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String inPath) {
		final UpdateMap updateMap = new UpdateMap();
		this.path = updateMap.updateField("path", this.path, inPath);
		update(updateMap);
	}

	public MimeType.MIME_TYPES getType() {
		return MIME_TYPES.find(this.mimeType.get(this.mime_type_id));
	}

	public void setType(MIME_TYPES inMimeType) {
		final UpdateMap updateMap = new UpdateMap();
		this.mime_type_id = updateMap.updateField("mime_type_id", this.mime_type_id, inMimeType.getId());
		update(updateMap);
	}

	public Timestamp getCreationDate() {
		return this.creation_date;
	}

	public String getMd5Sum() {
		return FilesImpl.FILES_COMMON.getMd5Sum(this);
	}

}
