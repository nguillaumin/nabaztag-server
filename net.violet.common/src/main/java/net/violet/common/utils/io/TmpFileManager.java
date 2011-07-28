package net.violet.common.utils.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Pattern;

import net.violet.common.StringShop;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class TmpFileManager {

	private static final Logger LOGGER = Logger.getLogger(TmpFileManager.class);
	private static final Pattern SPLIT_MD5 = Pattern.compile(StringShop.SPACE);

	private final File tempDirectory;
	private final String tempDirectoryPath;

	public TmpFileManager(String inPath) throws FileNotFoundException {
		tempDirectory = new File(inPath);
		if (!tempDirectory.exists())
			throw new FileNotFoundException(tempDirectory.getAbsolutePath());
		tempDirectoryPath = tempDirectory.getAbsolutePath() + File.separator;
	}

	public class TmpFile {

		private final File file;

		public TmpFile(byte[] inFile) throws IOException {
			this(new ByteArrayInputStream(inFile));

		}

		public TmpFile(InputStream inStream) throws IOException {
			this();
			write(inStream);
		}

		public TmpFile(InputStream inStream, String name) throws IOException {
			file = File.createTempFile(name, StringShop.EMPTY_STRING, tempDirectory);
			write(inStream);
		}

		public TmpFile() throws IOException {
			this((String) null);
		}

		public TmpFile(TmpFile inFiles) throws IOException {
			final File theFile = inFiles.get();
			if (theFile == null || !theFile.exists())
				throw new FileNotFoundException();

			file = File.createTempFile(String.valueOf(System.currentTimeMillis()), StringShop.EMPTY_STRING, tempDirectory);
			write(new FileInputStream(theFile));
		}

		public TmpFile(String inFilesPath) throws IOException {
			if (inFilesPath == null) {
				file = File.createTempFile(String.valueOf(System.currentTimeMillis()), StringShop.EMPTY_STRING, tempDirectory);
			} else
				file = getFile(inFilesPath);
		}

		public byte[] getContent() {
			try {
				if (file.exists())
					return IOUtils.toByteArray(new FileInputStream(file));
			} catch (IOException e) {
				LOGGER.fatal(e, e);
			}

			return null;
		}

		public void write(byte[] inContent) {
			write(new ByteArrayInputStream(inContent));
		}

		public void write(InputStream inStream) {

			if (file != null) {
				FileOutputStream ouputStream = null;
				try {
					ouputStream = new FileOutputStream(file);
					IOUtils.copy(inStream, ouputStream);
				} catch (final IOException e) {
					LOGGER.fatal(e, e);
				} finally {
					IOUtils.closeQuietly(ouputStream);
				}
			}
		}

		public boolean delete() {
			return !file.exists() || file.delete();
		}

		public String getMD5Sum() {
			if (file.exists()) {
				return getMd5Sum(file.getAbsolutePath());
			}

			return null;
		}

		public String getName() {
			if (file != null && file.exists())
				return file.getName();
			return null;
		}

		public String getPath() {
			return file.getAbsolutePath();
		}

		public File get() {
			return file;
		}

		public long length() {
			return file.length();
		}

		public boolean exists() {
			return file.exists();
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof TmpFile && ((file != null && file.equals(((TmpFile) obj).file)) || (file == null && file == ((TmpFile) obj).file));
		}

		@Override
		public int hashCode() {
			return file.hashCode();
		}
	}

	public static String getMd5Sum(String inPath) {
		InputStream theInputStream = null;
		OutputStream theOutputStream = null;
		InputStream theErrStream = null;
		try {
			final Process proc = Runtime.getRuntime().exec("/usr/bin/md5sum " + inPath);
			theErrStream = proc.getErrorStream();
			theOutputStream = proc.getOutputStream();
			theInputStream = proc.getInputStream();
			final BufferedReader theReader = new BufferedReader(new InputStreamReader(theInputStream));
			proc.waitFor();
			return SPLIT_MD5.split(theReader.readLine(), 0)[0];
		} catch (Exception e) {
			LOGGER.fatal(e, e);
		} finally {
			IOUtils.closeQuietly(theInputStream);
			IOUtils.closeQuietly(theOutputStream);
			IOUtils.closeQuietly(theErrStream);
		}

		return null;
	}

	public byte[] getContent(String inName) {
		try {
			final File theFile = getFile(inName);
			if (theFile.exists())
				return IOUtils.toByteArray(new FileInputStream(theFile));
		} catch (IOException e) {
			LOGGER.fatal(e, e);
		}

		return null;
	}

	public boolean delete(String inName) {
		final File theFile = getFile(inName);
		return !theFile.exists() || theFile.delete();
	}

	private File getFile(String inFileName) {
		return new File(tempDirectoryPath + inFileName);
	}

}
