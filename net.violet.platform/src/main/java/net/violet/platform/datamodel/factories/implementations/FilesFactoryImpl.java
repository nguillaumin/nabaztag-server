package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FilesImpl;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Files.CATEGORIES;
import net.violet.platform.datamodel.Files.FILE_DELETION_STATE;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.FilesFactory;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class FilesFactoryImpl extends RecordFactoryImpl<Files, FilesImpl> implements FilesFactory {

	private static final Logger LOGGER = Logger.getLogger(FilesFactoryImpl.class);

	public FilesFactoryImpl() {
		super(FilesImpl.SPECIFICATION);
	}

	public int walk(RecordWalker<Files> filesWalker) {
		return walk("`bestBefore` IS NOT NULL AND `bestBefore` < NOW() ", null, StringShop.ID_DESC, 0, filesWalker);
	}

	public Files createFile(String inPath, MimeType.MIME_TYPES inType) {
		try {
			return new FilesImpl(inPath, inType);

		} catch (final SQLException e) {
			FilesFactoryImpl.LOGGER.error("Insertion of Files " + inPath + " (" + inType + ") has failed.", e);
		}
		return null;
	}

	public Files createFile(MIME_TYPES inType, CATEGORIES inCategory) {
		return createFile(inType, inCategory, null);
	}

	public Files createFile(MIME_TYPES inType, CATEGORIES inCategory, String filePath) {
		Files theNewFile;
		try {
			theNewFile = new FilesImpl(net.violet.common.StringShop.EMPTY_STRING, inType);
			return Files.FilesCommon.createFiles(theNewFile, inType, inCategory, filePath);
		} catch (final SQLException e) {
			FilesFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	/**
	 * Removes a file from the DB and the FS. Iff the file is orphan.
	 * 
	 * @param inFile the File to remove
	 * @return <ul>
	 *         <li>FILE_DELETION_STATE.REFERRED_TO</code> : if the file is not
	 *         orphan</li>
	 *         <li><code>FILE_DELETION_STATE.ERROR</code> : if the file could
	 *         not be remove for some reasons</li>
	 *         <li><code>FILE_DELETION_STATE.DELETED</code> : if the file has
	 *         been removed</li>
	 *         </ul>
	 */
	public FILE_DELETION_STATE deleteFiles(Files inFile) {
		if (!inFile.isOrphan()) {
			return FILE_DELETION_STATE.REFERRED_TO;
		}
		if (FILE_DELETION_STATE.DELETED.equals(FilesManagerFactory.FILE_MANAGER.deleteFiles(inFile))) {
			inFile.delete();
			return FILE_DELETION_STATE.DELETED;
		}

		return FILE_DELETION_STATE.ERROR;
	}

}
