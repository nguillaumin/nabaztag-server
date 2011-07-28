package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.Date;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.FilesMock;

import org.junit.Assert;
import org.junit.Test;

public class FilesTest extends DBTest {

	private static long ORPHAN_FILE_ID = 1850109;

	@Test
	public void orphanSubscription() {
		final Files theFilesSettings = Factories.FILES.find(3386949);
		final Files theFiles = Factories.FILES.find(FilesTest.ORPHAN_FILE_ID);

		Assert.assertFalse(theFilesSettings.isOrphan());
		Assert.assertTrue(theFiles.isOrphan());
	}

	@Test
	public void orphanContent() {
		final Files theFilesContent = Factories.FILES.find(1850108);
		final Files theFiles = Factories.FILES.find(FilesTest.ORPHAN_FILE_ID);

		Assert.assertFalse(theFilesContent.isOrphan());
		Assert.assertTrue(theFiles.isOrphan());
	}

	@Test
	public void orphanFeedItem() {
		final Files theFilesContent = Factories.FILES.find(2286959);
		final Files theFiles = Factories.FILES.find(FilesTest.ORPHAN_FILE_ID);

		Assert.assertFalse(theFilesContent.isOrphan());
		Assert.assertTrue(theFiles.isOrphan());
	}

	@Test
	public void orphanMessage() {
		final Files theFilesMessage = Factories.FILES.find(1850117);
		final Files theFiles = Factories.FILES.find(FilesTest.ORPHAN_FILE_ID);

		Assert.assertFalse(theFilesMessage.isOrphan());
		Assert.assertTrue(theFiles.isOrphan());
	}

	@Test
	public void orphanMusic() {
		final Files theFilesMessage = Factories.FILES.find(2257079);
		final Files theFiles = Factories.FILES.find(FilesTest.ORPHAN_FILE_ID);

		Assert.assertFalse(theFilesMessage.isOrphan());
		Assert.assertTrue(theFiles.isOrphan());
	}

	@Test
	public void orphanConfigFiles() {
		final Files theFilesMessage = Factories.FILES.find(2257080);
		final Files theFiles = Factories.FILES.find(FilesTest.ORPHAN_FILE_ID);

		Assert.assertFalse(theFilesMessage.isOrphan());
		Assert.assertTrue(theFiles.isOrphan());
	}

	@Test
	public void createFiles() throws SQLException {
		final Files theFiles = new FilesImpl("/test/test/test.test", MimeType.MIME_TYPES.JPEG);
		Assert.assertNotNull(theFiles);
		theFiles.delete();
	}

	@Test
	public void insertFilesJPG() {
		final Files theFile = Factories.FILES.createFile(MimeType.MIME_TYPES.JPEG, Files.CATEGORIES.PHOTO);
		Assert.assertNotNull(theFile);
		Assert.assertFalse(net.violet.common.StringShop.EMPTY_STRING.equalsIgnoreCase(theFile.getPath()));
		theFile.delete();
	}

	@Test
	public void insertFilesGIF() {
		final Files theFile = Factories.FILES.createFile(MimeType.MIME_TYPES.GIF, Files.CATEGORIES.APPLICATION_PICTURE);
		Assert.assertNotNull(theFile);
		Assert.assertFalse(net.violet.common.StringShop.EMPTY_STRING.equalsIgnoreCase(theFile.getPath()));
		theFile.delete();
	}

	@Test
	public void scheduleDeletionTest() {

		final Files toDelete = new FilesMock("apilib/javascript/api-0.3.js", MimeType.MIME_TYPES.JS);
		toDelete.scheduleDeletion();
		Assert.assertTrue(toDelete.getBestBefore().after(new Date()));

		toDelete.unScheduleDeletion();
		Assert.assertNull(toDelete.getBestBefore());

	}

}
