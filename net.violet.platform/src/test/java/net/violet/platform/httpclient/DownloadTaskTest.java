package net.violet.platform.httpclient;

import java.net.MalformedURLException;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class DownloadTaskTest extends MockTestBase {

	@Test
	public void testDownload1() throws ErrorCodeException, InvalidContentException, FileSizeOutOfRangeException, UnreachableUrlException, MalformedURLException {
		final DownloadTask task = new DownloadTask(true, "http://podcast.downloads.techno.fm/DiamondCast/james.jpg");
		final TmpFile resultFile = task.execute();
		Assert.assertNotNull(resultFile);
		Assert.assertEquals(51503, resultFile.get().length());
		resultFile.delete();
	}

	@Test(expected = FileSizeOutOfRangeException.class)
	public void testTooBigDownload() throws ErrorCodeException, InvalidContentException, FileSizeOutOfRangeException, UnreachableUrlException, MalformedURLException {
		final DownloadTask theTask = new DownloadTask(true, "http://podcast.downloads.techno.fm/DiamondCast/DiamondCast_-_16_-_James_Diamond_Live_2008_05_15.m4a");
		theTask.execute();
	}

	@Test(expected = ErrorCodeException.class)
	public void test404CodeDownload() throws MalformedURLException, ErrorCodeException, InvalidContentException, FileSizeOutOfRangeException, UnreachableUrlException {
		final DownloadTask theTask = new DownloadTask(true, "http://www.dummy.net/404.mp3");
		theTask.execute();
	}

}
