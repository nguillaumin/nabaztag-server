package net.violet.platform.chor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.violet.common.StringShop;
import net.violet.common.utils.io.StreamTools;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.ConverterTest;
import net.violet.content.ScriptConstantes;
import net.violet.content.converters.ContentType;
import net.violet.content.manager.ConvertersManager;
import net.violet.platform.vasm.VasmTestBase;

import org.junit.Assert;
import org.junit.Test;

public class DancegeneratorConverterTest extends ConverterTest {

	static {
		ConvertersManager.register(new DanceGenerator());
	}

	@Test
	public void testEmptyString() throws UnsupportedEncodingException, IOException {
		final TmpFileWrapper theWrapper = new TmpFileWrapper(ConverterTest.TMP_MANAGER.new TmpFile(StringShop.EMPTY_STRING.getBytes("UTF-8")));
		Assert.assertNull(ConvertersManager.convert(ContentType.CHOR_CDL, ContentType.CHOR, theWrapper));
	}

	@Test
	public void testAPIDocMotorSampleBinary() throws UnsupportedEncodingException, IOException {
		final TmpFileWrapper theWrapper = new TmpFileWrapper(ConverterTest.TMP_MANAGER.new TmpFile("10,0,motor,1,20,0,0".getBytes("UTF-8")));
		VasmTestBase.assertVasmEquals(new int[] { 0, 1, 10, 0, 8, 1, 1, 0 }, ConvertersManager.convert(ContentType.CHOR_CDL, ContentType.CHOR, theWrapper).getContent());
	}

	@Test
	public void testAPIDocLedSampleBinary() throws UnsupportedEncodingException, IOException {
		final TmpFileWrapper theWrapper = new TmpFileWrapper(ConverterTest.TMP_MANAGER.new TmpFile("10,0,led,2,0,238,0,2,led,1,250,0,0,3,led,2,0,0,0".getBytes("UTF-8")));
		VasmTestBase.assertVasmEquals(new int[] { 0, 1, 10, 0, 7, 2, 0, -18, 0, 0, 0, 2, 7, 1, -6, 0, 0, 0, 0, 1, 7, 2, 0, 0, 0, 0, 0 }, ConvertersManager.convert(ContentType.CHOR_CDL, ContentType.CHOR, theWrapper).getContent());
	}

	@Test
	public void testAPIDocCombinedSampleBinary() throws UnsupportedEncodingException, IOException {
		final TmpFileWrapper theWrapper = new TmpFileWrapper(ConverterTest.TMP_MANAGER.new TmpFile("10,0,motor,1,20,0,0,0,led,2,0,238,0,2,led,1,250,0,0,3,led,2,0,0,0".getBytes("UTF-8")));
		VasmTestBase.assertVasmEquals(new int[] { 0, 1, 10, 0, 8, 1, 1, 0, 0, 7, 2, 0, 238, 0, 0, 0, 2, 7, 1, -6, 0, 0, 0, 0, 1, 7, 2, 0, 0, 0, 0, 0 }, ConvertersManager.convert(ContentType.CHOR_CDL, ContentType.CHOR, theWrapper).getContent());
	}

	@Test
	public void testAPIDocLedPaletteSampleBinary() throws UnsupportedEncodingException, IOException {
		final TmpFileWrapper theWrapper = new TmpFileWrapper(ConverterTest.TMP_MANAGER.new TmpFile("10,0,palette,2,7,0,palette,2,1,0,palette,2,7".getBytes("UTF-8")));
		VasmTestBase.assertVasmEquals(new int[] { 0, 1, 10, 0, 14, 2, 247, 0, 14, 2, 241, 0, 14, 2, 247 }, ConvertersManager.convert(ContentType.CHOR_CDL, ContentType.CHOR, theWrapper).getContent());
	}

	@Test
	public void mp32chorTest() throws IOException {
		TmpFile theResult = null;
		try {
			final byte[] theMP3File = StreamTools.readBytes(ScriptConstantes.SCRIPT_PATH + "../test/net/violet/content/MP3File.mp3");
			Assert.assertNotNull(theMP3File);
			Assert.assertEquals(2697507, theMP3File.length);

			final TmpFileWrapper theWrapper = new TmpFileWrapper();
			theWrapper.add(ConverterTest.TMP_MANAGER.new TmpFile(theMP3File));

			theResult = ConvertersManager.convert(ContentType.MP3, ContentType.CHOR, theWrapper);

			Assert.assertNotNull(theResult);
			Assert.assertTrue(theResult.get().exists());
			Assert.assertTrue(theResult.get().length() > 0);
		} finally {
			if (theResult != null) {
				theResult.delete();
			}
		}
	}
}
