package net.violet.platform.datamodel.factories.mock;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Files.CATEGORIES;
import net.violet.platform.datamodel.Files.FILE_DELETION_STATE;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.FilesFactory;
import net.violet.platform.datamodel.mock.FilesMock;

public class FilesFactoryMock extends RecordFactoryMock<Files, FilesMock> implements FilesFactory {

	@Override
	public void loadCache() {
		FilesMock.initConfigFiles();
	}

	FilesFactoryMock() {
		super(FilesMock.class);
	}

	public int walk(RecordWalker<Files> filesWalker) {
		int nbProcessed = 0;
		final SortedMap<Long, Files> theFiles = new TreeMap<Long, Files>(findAllMapped());
		final List<Entry<Long, Files>> theList = new LinkedList<Entry<Long, Files>>(theFiles.entrySet());
		final ListIterator<Entry<Long, Files>> theIterator = theList.listIterator(theList.size());

		while (theIterator.hasPrevious()) {
			final Files aFile = theIterator.previous().getValue();
			try {
				filesWalker.process(aFile);
			} finally {
				nbProcessed++;
			}
		}
		return nbProcessed;
	}

	public Files createFile(String inPath, MimeType.MIME_TYPES inType) {
		return new FilesMock(inPath, inType);
	}

	public FILE_DELETION_STATE deleteFiles(Files inFile) {
		throw new UnsupportedOperationException();
	}

	public Files createFile(MIME_TYPES inType, CATEGORIES inCategory) {
		return createFile(inType, inCategory, null);
	}

	public Files createFile(MIME_TYPES inType, CATEGORIES inCategory, String filePath) {
		final Files theNewFile = new FilesMock(0, new Timestamp(System.currentTimeMillis()), inType, net.violet.common.StringShop.EMPTY_STRING);
		return Files.FilesCommon.createFiles(theNewFile, inType, inCategory, filePath);
	}
}
