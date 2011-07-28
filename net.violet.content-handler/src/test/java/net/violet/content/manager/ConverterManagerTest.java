package net.violet.content.manager;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import net.violet.common.utils.io.StreamTools;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.ConverterTest;
import net.violet.content.ScriptConstantes;
import net.violet.content.converters.ContentType;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ConverterManagerTest extends ConverterTest {

	private static final Logger LOGGER = Logger.getLogger(ConverterManagerTest.class);

	private static final URL FLV;

	static {
		URL theURL = null;
		try {
			theURL = new URL("http://192.168.1.11/tests_silence/105595_1213776753551.flv");
		} catch (final MalformedURLException e) {
			ConverterManagerTest.LOGGER.fatal(e, e);
		}

		FLV = theURL;
	}

	@Test
	public void flv2Mp3Test() {
		TmpFile theResult = null;
		try {
			Assert.assertTrue(ConverterManagerTest.FLV.openStream().available() > 0);
			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(TMP_MANAGER.new TmpFile(ConverterManagerTest.FLV.openStream()));
			theResult = ConvertersManager.convert(ContentType.FLV, ContentType.MP3_32, theWrapper);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(22208, theResult.get().length());

		} catch (final IOException e) {
			ConverterManagerTest.LOGGER.fatal(e, e);
			Assert.fail(e.getMessage());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

	@Test
	public void aac2Mp3Test() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] theFaadFile = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/faad_test_material.m4a");
			Assert.assertNotNull(theFaadFile);
			Assert.assertEquals(17737, theFaadFile.length);

			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(TMP_MANAGER.new TmpFile(theFaadFile));

			theResult = ConvertersManager.convert(ContentType.AAC, ContentType.MP3_32, theWrapper);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(7808, theResult.get().length());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

	@Test
	public void mp32wav16Test() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] theMP3File = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/MP3File.mp3");
			Assert.assertNotNull(theMP3File);
			Assert.assertEquals(2697507, theMP3File.length);

			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(TMP_MANAGER.new TmpFile(theMP3File));

			theResult = ConvertersManager.convert(ContentType.MP3, ContentType.WAV_16, theWrapper);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(1440024, theResult.get().length());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

	@Test
	public void mp32AdpTest() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] theMP3File = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/MP3File.mp3");
			Assert.assertNotNull(theMP3File);
			Assert.assertEquals(2697507, theMP3File.length);

			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(TMP_MANAGER.new TmpFile(theMP3File));

			theResult = ConvertersManager.convert(ContentType.MP3, ContentType.ADP, theWrapper);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(360011, theResult.get().length());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

	@Test
	public void mp32mp3_22Test() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] theMP3File = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/MP3File.mp3");
			Assert.assertNotNull(theMP3File);
			Assert.assertEquals(2697507, theMP3File.length);

			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(TMP_MANAGER.new TmpFile(theMP3File));

			theResult = ConvertersManager.convert(ContentType.MP3, ContentType.MP3_32, theWrapper);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(674384, theResult.get().length());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

	@Test
	public void mp32mp3_128Test() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] theMP3File = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/MP3File.mp3");
			Assert.assertNotNull(theMP3File);
			Assert.assertEquals(2697507, theMP3File.length);

			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(TMP_MANAGER.new TmpFile(theMP3File));

			theResult = ConvertersManager.convert(ContentType.MP3, ContentType.MP3_128, theWrapper);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(2697540, theResult.get().length());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

	@Test
	public void pcm_8ToMp3_22Test() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] thePCMFile = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/PCM_8File");
			Assert.assertNotNull(thePCMFile);
			Assert.assertEquals(10240, thePCMFile.length);

			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(TMP_MANAGER.new TmpFile(thePCMFile));

			theResult = ConvertersManager.convert(ContentType.PCM_8, ContentType.MP3_32, theWrapper);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(2768, theResult.get().length());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

	@Test
	public void readZeroByte() {
		final ByteArrayInputStream theStream = new ByteArrayInputStream(new byte[2]);
		final byte[] theResult = StreamTools.readBytes(theStream, 0);
		IOUtils.closeQuietly(theStream);
		Assert.assertNotNull(theResult);
	}

	@Test
	public void splitTest() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] theMP3File = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/MP3File.mp3");
			Assert.assertNotNull(theMP3File);
			Assert.assertEquals(2697507, theMP3File.length);

			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(TMP_MANAGER.new TmpFile(theMP3File));

			theResult = ConvertersManager.split(theWrapper, ContentType.MP3, ContentType.MP3, 0, 5);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(80280, theResult.get().length());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

	@Test
	public void mergeTest() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] theMP3File = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/MP3File.mp3");
			final byte[] thePCMFile = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/PCM_8File");

			Assert.assertTrue(ConverterManagerTest.FLV.openStream().available() > 0);
			Assert.assertNotNull(thePCMFile);
			Assert.assertEquals(10240, thePCMFile.length);
			Assert.assertNotNull(theMP3File);
			Assert.assertEquals(2697507, theMP3File.length);

			final Map<TmpFile, ContentType> theFiles = new LinkedHashMap<TmpFile, ContentType>();
			theFiles.put(TMP_MANAGER.new TmpFile(theMP3File), ContentType.MP3);
			theFiles.put(TMP_MANAGER.new TmpFile(thePCMFile), ContentType.PCM_8);
			theFiles.put(TMP_MANAGER.new TmpFile(ConverterManagerTest.FLV.openStream()), ContentType.FLV);
			theResult = ConvertersManager.join(ContentType.MP3_32, theFiles);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertEquals(698720, theResult.get().length());
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}

}
