package net.violet.common.utils.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Provides some tools to manipulate streams. In all the methods below, the given streams are NEVER closed.
 */
public abstract class StreamTools {

	private static final Logger LOGGER = Logger.getLogger(StreamTools.class);
	private static final String CHARSET = "UTF-8";

	/**
	 * Opens a connection to the given URL, reads the available content and returns it as a String.
	 * 
	 * null is returned if an error occured.
	 * @throws IOException 
	 */
	public static String readString(URL inURL) throws IOException {
		return new String(readBytes(inURL), CHARSET);
	}

	/**
	 * Opens a connection to the given URL, reads the available content and returns it as a bytes array.
	 * 
	 * Throws an IOException if the connection cannot be opened with the given URL.
	 * Returns null if an other error happend.
	 * 
	 * @param inUrl
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(URL inUrl) throws IOException {
		InputStream theInputStream = null;
		try {
			return IOUtils.toByteArray(inUrl.openStream());
		} finally {
			IOUtils.closeQuietly(theInputStream);
		}
	}

	/**
	 * Reads content from the given file and returns it as bytes array, null if an error occured.
	 * 
	 * @param filePath absolute file path to the file to read.
	 * @return
	 */
	public static byte[] readBytes(String filePath) {
		try {
			return IOUtils.toByteArray(new FileInputStream(filePath));
		} catch (IOException e) {
			LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Reads lenght bytes from the given InputStream.
	 * 
	 * Returned the read bytes in an array, or null if an error occured.
	 * 
	 * @param inInputStream
	 * @param inLength
	 * @return
	 */
	public static byte[] readBytes(InputStream inInputStream, int inLength) {
		if (inLength < 0) {
			return null;
		}

		final byte[] theBuffer = new byte[inLength];
		final int nbRead = readBytes(inInputStream, theBuffer);

		return nbRead < 0 ? null : theBuffer;
	}

	/**
	 * Reads bytes from the given input stream and fills up the provided bytes array.
	 * 
	 * The amount of read bytes is returned, or -1 if an error occured. The amount of read bytes cannot be greater than
	 * the length of the given buffer.
	 * 
	 * @param inInputStream
	 * @param inOutputBuffer
	 * 
	 * @return amount of bytes read
	 */
	public static int readBytes(InputStream inInputStream, byte[] inOutputBuffer) {
		int amountOfReadBytes = 0;
		int localAmount = 0;
		final int theLength = inOutputBuffer.length;

		try {
			while (amountOfReadBytes < theLength && (localAmount = inInputStream.read(inOutputBuffer, amountOfReadBytes, theLength - amountOfReadBytes)) != -1) {
				amountOfReadBytes += localAmount;
			}

			return (amountOfReadBytes == 0 && localAmount == -1) ? -1 : amountOfReadBytes;

		} catch (IOException e) {
			LOGGER.fatal(e, e);
			return -1;
		}

	}

	/**
	 * Writes the give content into the provided outputStream object.
	 * 
	 * Only the bytes from content[inOffset] to content[content.length-1] will be written.
	 * 
	 * @param inOutPutStream
	 * @param content
	 * @param inOffSet
	 * @throws IOException
	 */
	public static void writeTo(OutputStream inOutPutStream, byte[] content, int inOffSet) throws IOException {
		inOutPutStream.write(content, inOffSet, content.length - inOffSet);
		inOutPutStream.flush();
	}

}
