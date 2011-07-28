package net.violet.vadmin.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.api.actions.ActionParam;
import net.violet.vadmin.exceptions.InvalidFile;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.struts.upload.FormFile;

public class UploadTools {

	private static Pattern csv = Pattern.compile("\\{\"success\":true,\"fileId\":\"(.*)\"\\}");

	private static final String URL_UPLOAD = "http://"+AdminConstantes.OS_SERVER+"/OS/upload";
	
	public static String uploadFile(FormFile inFile, String inSessionId) throws FileNotFoundException, IOException, InvalidFile {

		// the file must be smaller than 10Mo
		if (inFile.getFileSize() > 10485760) {
			throw new InvalidFile();
		}

		final HttpClient client = new HttpClient();
		final PostMethod put = new PostMethod(URL_UPLOAD);
		final InputStream input = inFile.getInputStream();
		final Part[] parts = { new StringPart(ActionParam.SESSION_PARAM_KEY, inSessionId), new FilePart(inFile.getFileName(), new InputStreamPartSource(input, inFile.getFileName(), inFile.getFileSize()), "image/jpeg", null) };
		put.setRequestEntity(new MultipartRequestEntity(parts, put.getParams()));

		client.executeMethod(put);
		final Matcher theMatcher = csv.matcher(put.getResponseBodyAsString());
		if (theMatcher.matches()) {
			return theMatcher.group(1);
		}
		return null;
	}

	private static class InputStreamPartSource implements PartSource {

		InputStream stream = null;
		String filename = null;
		int length = 0;

		public InputStreamPartSource(InputStream stream, String filename, int length) {
			this.stream = stream;
			this.filename = filename;
			this.length = length;
		}

		public InputStream createInputStream() {
			return this.stream;
		}

		public String getFileName() {
			return this.filename;
		}

		public long getLength() {
			return this.length;
		}
	}
}
