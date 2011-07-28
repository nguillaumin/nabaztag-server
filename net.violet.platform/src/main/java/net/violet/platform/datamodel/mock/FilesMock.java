package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;
import java.util.Date;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.util.CCalendar;

public class FilesMock extends AbstractMockRecord<Files, FilesMock> implements Files {

	private static FilesCommon FILES_COMMON = new FilesCommon();

	public static final MockBuilder<Files> BUILDER = new MockBuilder<Files>() {

		@Override
		protected Files build(String[] inParamValues) {
			return new FilesMock(Long.parseLong(inParamValues[0]), inParamValues[4] == null ? null : new Timestamp(CCalendar.parseTimestamp(inParamValues[4]).getTime()), MimeType.MIME_TYPES.findByLabel(inParamValues[2]), inParamValues[1]);
		}
	};

	public static void initConfigFiles() {
		FilesMock.init("filesInit");
	}

	public static void initSignatureFiles() {
		FilesMock.init("signatureInit");
	}

	public static void initAction80() {
		FilesMock.init("files80Init");
	}

	private static void init(String fileName) {
		final CCalendar now = new CCalendar(false);
		final Timestamp theTimestamp = new Timestamp(now.getTimeInMillis());
		final String path2AnnounceGmail = "broadcast/broad/config/gmail/gmail-annonce.mp3";
		final String path2AnnounceTwitter = "broadcast/broad/config/twitter/twitter-annonce.mp3";

		new FilesMock(Files.NEW_CONTENT_FILE_ID, theTimestamp, MimeType.MIME_TYPES.A_MPEG, net.violet.common.StringShop.EMPTY_STRING);
		new FilesMock(69841, null, MimeType.MIME_TYPES.A_MPEG, path2AnnounceGmail);
		new FilesMock(65284, null, MimeType.MIME_TYPES.A_MPEG, path2AnnounceTwitter);

		FilesMock.BUILDER.generateValuesFromInitFile(5, net.violet.platform.util.Constantes.OS_PATH + "/net/violet/platform/datamodel/mock/" + fileName);
	}

	protected Long id;
	protected Timestamp bestBefore;
	protected Timestamp creation_date;
	protected String path;
	protected MimeType.MIME_TYPES mimeType;

	public FilesMock(String inPath, MimeType.MIME_TYPES inType) {
		this(0L, inPath, inType);
	}

	public FilesMock(Long id, String inPath, MimeType.MIME_TYPES inType) {
		this(id, inPath, inType, new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * @param id
	 * @param bestBefore
	 * @param path2adp
	 * @param path2chor
	 * @param inPath
	 */
	public FilesMock(long id, Timestamp bestBefore, MimeType.MIME_TYPES inType, String inPath) {
		this(id, bestBefore, inType, inPath, null);
	}

	public FilesMock(Long id, String inPath, MimeType.MIME_TYPES inType, Date inDate) {
		this(id, null, inType, inPath, inDate);
	}

	public FilesMock(long id, Timestamp bestBefore, MimeType.MIME_TYPES inType, String inPath, Date inCreationDate) {
		super(id);
		this.bestBefore = bestBefore;
		this.path = inPath;
		this.mimeType = inType;
		this.creation_date = new Timestamp((inCreationDate == null) ? System.currentTimeMillis() : inCreationDate.getTime());

	}

	public void scheduleDeletion() {
		setBestBefore(FilesMock.FILES_COMMON.generateBestBefore());
	}

	private void setBestBefore(CCalendar inBestBefore) {
		this.bestBefore = (inBestBefore == null) ? null : new Timestamp(inBestBefore.getTimeInMillis());
	}

	public Timestamp getBestBefore() {
		return this.bestBefore;
	}

	public void unScheduleDeletion() {
		setBestBefore(null);
	}

	public final String getPath2chor() {
		return FilesMock.FILES_COMMON.getPath2chor(this);
	}

	public final String getPath2adp() {
		return FilesMock.FILES_COMMON.getPath2adp(this);
	}

	public final String getPath2midi() {
		return FilesMock.FILES_COMMON.getPath2midi(this);
	}

	public void setPath(String inPath) {
		this.path = inPath;
	}

	public String getPath() {
		return this.path;
	}

	public void setMp3Path(String inPath2Mp3) {
		setType(MimeType.MIME_TYPES.A_MPEG);
		setPath(inPath2Mp3);
	}

	public void setChorPath(String chor) {
		setType(MimeType.MIME_TYPES.CHOR);
		setPath(chor);
	}

	public boolean isOrphan() {
		return FilesMock.FILES_COMMON.isFileOrphan(this);
	}

	public MimeType.MIME_TYPES getType() {
		return this.mimeType;
	}

	public void setType(MimeType.MIME_TYPES type) {
		this.mimeType = type;
	}

	public Timestamp getCreationDate() {
		return this.creation_date;
	}

	public String getMd5Sum() {
		return FilesMock.FILES_COMMON.getMd5Sum(this);
	}
}
