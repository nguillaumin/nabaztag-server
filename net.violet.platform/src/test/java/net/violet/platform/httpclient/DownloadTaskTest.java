package net.violet.platform.httpclient;

import java.net.MalformedURLException;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class DownloadTaskTest extends MockTestBase {

	@Test
	public void testDownload1() throws ErrorCodeException, InvalidContentException, FileSizeOutOfRangeException, UnreachableUrlException, MalformedURLException {
		final DownloadTask task = new DownloadTask(true, "http://192.168.1.11/tests_silence/P041_Fin_del_Sueno_Zenon_y_Curie1.mp3");
		final TmpFile resultFile = task.execute();
		Assert.assertNotNull(resultFile);
		Assert.assertEquals(203854, resultFile.get().length());
		resultFile.delete();
	}

	@Test(expected = FileSizeOutOfRangeException.class)
	public void testTooBigDownload() throws ErrorCodeException, InvalidContentException, FileSizeOutOfRangeException, UnreachableUrlException, MalformedURLException {
		final DownloadTask theTask = new DownloadTask(true, "http://192.168.1.11/tests_silence/DiamondCast_-_16_-_James_Diamond_Live_2008_05_15.m4a");
		theTask.execute();
	}

	@Test(expected = ErrorCodeException.class)
	public void test404CodeDownload() throws MalformedURLException, ErrorCodeException, InvalidContentException, FileSizeOutOfRangeException, UnreachableUrlException {
		final DownloadTask theTask = new DownloadTask(true, "http://192.168.1.11/tests_silence/404.mp3");
		theTask.execute();
	}

}
