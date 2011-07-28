package net.violet.platform.datamodel.factories;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Files.CATEGORIES;
import net.violet.platform.datamodel.Files.FILE_DELETION_STATE;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;

public interface FilesFactory extends RecordFactory<Files> {

	/**
	 * Itérateur sur les FilesImpl ayant une date de péremption.
	 * 
	 * @param inWalker itérateur
	 * @return le nombre de contenus traités.
	 */
	int walk(RecordWalker<Files> filesWalker);

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
	FILE_DELETION_STATE deleteFiles(Files inFile);

	Files createFile(MIME_TYPES inType, CATEGORIES inCategory);

	Files createFile(MIME_TYPES inType, CATEGORIES inCategory, String filePath);

	Files createFile(String inPath, MIME_TYPES inType);

}
