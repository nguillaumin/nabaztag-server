package net.violet.platform.files;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class FilesManagerTest extends MockTestBase {

	@Test
	public void rscManagerTest() {
		final RscManager theManager = FilesManagerFactory.getRscManager();

		String path = theManager.getFilesPath("application/appli", theManager);
		Assert.assertEquals("application/appli", path);

		path = theManager.getFilesPath("apilib/apiLib", theManager);
		Assert.assertEquals("apilib/apiLib", path);

		path = theManager.getFilesPath("broadcast/broad/001/202/file.mp3", theManager);
		Assert.assertEquals("broadcast/broad/001/202/file.mp3", path);

		path = theManager.getFilesPath("broadcast/broad/config/conf", theManager);
		Assert.assertEquals("broadcast/broad/config/conf", path);

		path = theManager.getFilesPath("broadcast/broad/config/admin/adm", theManager);
		Assert.assertEquals("broadcast/broad/config/admin/adm", path);

		path = theManager.getFilesPath("broadcast/broad/photo/pic.jpg", theManager);
		Assert.assertEquals("broadcast/broad/photo/pic.jpg", path);

		path = theManager.getFilesPath("broadcast/broad/finished/nath.mp3", theManager);
		Assert.assertEquals("broadcast/broad/finished/nath.mp3", path);

		path = theManager.getFilesPath("broadcast/broad/podcast/pod", theManager);
		Assert.assertEquals("broadcast/broad/podcast/pod", path);

		path = theManager.getFilesPath("broadcast/data/001/202/file.mp3", theManager);
		Assert.assertEquals("broadcast/broad/001/202/file.mp3", path);

		path = theManager.getFilesPath("broadcast/data/config/conf", theManager);
		Assert.assertEquals("broadcast/broad/config/conf", path);

		path = theManager.getFilesPath("broadcast/data/config/admin/adm", theManager);
		Assert.assertEquals("broadcast/broad/config/admin/adm", path);

		path = theManager.getFilesPath("broadcast/data/photo/pic.jpg", theManager);
		Assert.assertEquals("broadcast/broad/photo/pic.jpg", path);

		path = theManager.getFilesPath("broadcast/data/finished/nath.mp3", theManager);
		Assert.assertEquals("broadcast/broad/finished/nath.mp3", path);

		path = theManager.getFilesPath("broadcast/data/podcast/pod", theManager);
		Assert.assertEquals("broadcast/broad/podcast/pod", path);
	}

	// @Test
	public void hadoopManagerTest() {
		final HadoopManager theManager = FilesManagerFactory.getHadoopManager();

		String path = theManager.getFilesPath("application/appli", theManager);
		Assert.assertEquals("application/appli", path);

		path = theManager.getFilesPath("apilib/apiLib", theManager);
		Assert.assertEquals("apilib/apiLib", path);

		path = theManager.getFilesPath("broadcast/broad/001/202/file.mp3", theManager);
		Assert.assertEquals("broadcast/data/001/202/file.mp3", path);

		path = theManager.getFilesPath("broadcast/broad/config/conf", theManager);
		Assert.assertEquals("broadcast/data/config/conf", path);

		path = theManager.getFilesPath("broadcast/broad/config/admin/adm", theManager);
		Assert.assertEquals("broadcast/data/config/admin/adm", path);

		path = theManager.getFilesPath("broadcast/broad/photo/pic.jpg", theManager);
		Assert.assertEquals("broadcast/data/photo/pic.jpg", path);

		path = theManager.getFilesPath("broadcast/broad/finished/nath.mp3", theManager);
		Assert.assertEquals("broadcast/data/finished/nath.mp3", path);

		path = theManager.getFilesPath("broadcast/broad/podcast/pod", theManager);
		Assert.assertEquals("broadcast/data/podcast/pod", path);

		path = theManager.getFilesPath("broadcast/data/001/202/file.mp3", theManager);
		Assert.assertEquals("broadcast/data/001/202/file.mp3", path);

		path = theManager.getFilesPath("broadcast/data/config/conf", theManager);
		Assert.assertEquals("broadcast/data/config/conf", path);

		path = theManager.getFilesPath("broadcast/data/config/admin/adm", theManager);
		Assert.assertEquals("broadcast/data/config/admin/adm", path);

		path = theManager.getFilesPath("broadcast/data/photo/pic.jpg", theManager);
		Assert.assertEquals("broadcast/data/photo/pic.jpg", path);

		path = theManager.getFilesPath("broadcast/data/finished/nath.mp3", theManager);
		Assert.assertEquals("broadcast/data/finished/nath.mp3", path);

		path = theManager.getFilesPath("broadcast/data/podcast/pod", theManager);
		Assert.assertEquals("broadcast/data/podcast/pod", path);
	}

	@Test
	public void httpManagerTest() {
		final HttpManager theManager = FilesManagerFactory.getHttpManager();

		String path = theManager.getFilesPath("application/appli", theManager);
		Assert.assertEquals("application/appli", path);

		path = theManager.getFilesPath("apilib/apiLib", theManager);
		Assert.assertEquals("apilib/apiLib", path);

		path = theManager.getFilesPath("broadcast/broad/001/202/file.mp3", theManager);
		Assert.assertEquals("broadcast/data/001/202/file.mp3", path);

		path = theManager.getFilesPath("broadcast/broad/config/conf", theManager);
		Assert.assertEquals("broadcast/data/config/conf", path);

		path = theManager.getFilesPath("broadcast/broad/config/admin/adm", theManager);
		Assert.assertEquals("broadcast/data/config/admin/adm", path);

		path = theManager.getFilesPath("broadcast/broad/photo/pic.jpg", theManager);
		Assert.assertEquals("broadcast/data/photo/pic.jpg", path);

		path = theManager.getFilesPath("broadcast/broad/finished/nath.mp3", theManager);
		Assert.assertEquals("broadcast/data/finished/nath.mp3", path);

		path = theManager.getFilesPath("broadcast/broad/podcast/pod", theManager);
		Assert.assertEquals("broadcast/data/podcast/pod", path);

		path = theManager.getFilesPath("broadcast/data/001/202/file.mp3", theManager);
		Assert.assertEquals("broadcast/data/001/202/file.mp3", path);

		path = theManager.getFilesPath("broadcast/data/config/conf", theManager);
		Assert.assertEquals("broadcast/data/config/conf", path);

		path = theManager.getFilesPath("broadcast/data/config/admin/adm", theManager);
		Assert.assertEquals("broadcast/data/config/admin/adm", path);

		path = theManager.getFilesPath("broadcast/data/photo/pic.jpg", theManager);
		Assert.assertEquals("broadcast/data/photo/pic.jpg", path);

		path = theManager.getFilesPath("broadcast/data/finished/nath.mp3", theManager);
		Assert.assertEquals("broadcast/data/finished/nath.mp3", path);

		path = theManager.getFilesPath("broadcast/data/podcast/pod", theManager);
		Assert.assertEquals("broadcast/data/podcast/pod", path);
	}

}
