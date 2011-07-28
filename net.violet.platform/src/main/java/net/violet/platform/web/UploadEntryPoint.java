package net.violet.platform.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.endpoints.HTTPEndpoint;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.formats.EnumResponsesFormats;
import net.violet.platform.api.formats.HttpResponseHelper;
import net.violet.platform.datamodel.Application;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.UserData;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

public class UploadEntryPoint extends HTTPEndpoint {

	// Logger.
	private static final Logger LOGGER = Logger.getLogger(UploadEntryPoint.class);

	private static final String FILE_ID_EQUAL = "file_id=";

	private static final String CALLBACKURL = "callbackurl";

	private static final Pattern MP3_EXTENSION = Pattern.compile(".+mp3$", Pattern.CASE_INSENSITIVE);

	//TODO désactiver, en attendant le nouveau cache!!
	//private static final String TOKEN = "token";

	// Paramètre d'authentification pour l'application webui
	private static final ApplicationCredentialsData CREDENTIALS = ApplicationCredentialsData.findByPublicKey(Application.ApplicationClass.WEBUI.toString());
	private static final APICaller CALLER = new ApplicationAPICaller(UploadEntryPoint.CREDENTIALS);

	/**
	 * Points d'entrée de la servlet pour l'upload.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (final UnsupportedEncodingException ignore) {}

		UploadEntryPoint.LOGGER.info("Processing Upload request : " + req.getRequestURI() + "?" + req.getQueryString());

		EnumResponsesFormats format;

		// The first thing to know is the expected response format
		// because even an error response must be in this format
		try {
			format = getResponseFormat(req);
		} catch (final APIException e) {
			writeErrorMessage(resp, e, EnumResponsesFormats.XML, null);
			return;
		}
		// now try to process or catch the API Exception..
		try {
			process(req, resp, format);
		} catch (final APIException e) {
			UploadEntryPoint.LOGGER.error("APIException", e);
			writeErrorMessage(resp, e, format, getCallbackParam(req));
		}
	}

	/**
	 * Génère la réponse suite à l'upload Rétourne id du file généré sinon si le
	 * paramètre callbackurl existe on le concatene.
	 * 
	 * @param req
	 * @param resp
	 * @throws APIException
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp, EnumResponsesFormats format) throws APIException {

		FilesData theFileData = null;
		String callbackurl = null;
		//TODO désactiver, en attendant le nouveau cache!!
		//String token = null;
		UserData theUserData = null;
		String contentType = null;
		byte[] theContent = null;

		try {
			final MultipartParser mp = new MultipartParser(req, 10 * 1024 * 1024); // 10 MB
			Part part;

			while ((part = mp.readNextPart()) != null) {
				final String name = part.getName();
				if (part.isParam()) {
					// it's a parameter part
					final ParamPart paramPart = (ParamPart) part;
					final String value = paramPart.getStringValue();
					if (UploadEntryPoint.CALLBACKURL.equals(name)) {
						callbackurl = value;
						//TODO désactiver, en attendant le nouveau cache!!
						/*} else if (UploadEntryPoint.TOKEN.equals(name)) {
							token = value;*/
					} else if (ActionParam.SESSION_PARAM_KEY.equals(name)) {
						theUserData = SessionManager.getUserFromValidSessionId(value);
					}

				} else if (part.isFile()) {
					// it's a file part
					final FilePart filePart = (FilePart) part;
					final String fileName = filePart.getFileName();
					if (fileName != null) {
						InputStream bufferedStream = null;
						try {
							bufferedStream = new BufferedInputStream(filePart.getInputStream());
							theContent = IOUtils.toByteArray(bufferedStream);
						} finally {
							IOUtils.closeQuietly(bufferedStream);
						}
						contentType = filePart.getContentType(); // note : FilePart allways return content-type in lowercase
						if (contentType.equals("application/octet-stream") && UploadEntryPoint.MP3_EXTENSION.matcher(fileName).matches()) {
							// Un drole de bug du webkit intégré à QT..
							contentType = "audio/mpeg";
						}
					} else {
						// the field did not contain a file
						throw new InternalErrorException("file is empty!!!");
					}
				}
			}
		} catch (final IOException ioe) {
			throw new InternalErrorException(ioe.getMessage());
		}

		// theUserData is already check in SessionManager.getUserFromSessionId()
		//TODO désactiver, en attendant le nouveau cache!!
		//			final String getToken = GetToken.TOKEN_CACHE.remove(theUserData);
		//
		//			if ((getToken == null) || !getToken.equals(token)) {
		//				throw new ForbiddenException();
		//			}
		if (theUserData == null) {
			throw new ForbiddenException();
		}

		theFileData = FilesData.postLibraryItem(contentType, new ByteArrayInputStream(theContent));

		if ((theFileData == null) || theFileData.isEmpty()) {
			throw new InternalErrorException("file is empty or null!!!");
		}

		final String theFileId = theFileData.getApiId(UploadEntryPoint.CALLER);

		UploadEntryPoint.LOGGER.info("Response file id generate: " + theFileId);

		// special callback for upload fonction
		if (callbackurl != null) {

			final String theParam = UploadEntryPoint.FILE_ID_EQUAL + theFileId;
			if (callbackurl.contains(net.violet.common.StringShop.QUESTION_MARK)) {
				callbackurl = callbackurl.trim() + net.violet.common.StringShop.AMPERSAND + theParam;
			} else {
				callbackurl = callbackurl.trim() + net.violet.common.StringShop.QUESTION_MARK + theParam;
			}

			try {
				resp.sendRedirect(callbackurl);
			} catch (final IOException e) {
				final String strErrMsg = "The call of url " + callbackurl + " has failed .";
				UploadEntryPoint.LOGGER.error(strErrMsg, e);
				throw new InternalErrorException(strErrMsg);
			}
		}

		String formatedResponse;
		try {
			// Create a response map
			final Map<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("success", true);
			respMap.put("fileId", theFileId);
			formatedResponse = (String) HttpResponseHelper.formatResp(respMap, format, getCallbackParam(req));

		} catch (final java.lang.RuntimeException e) {
			// Unexpected Runtime exception !
			final String strErrMsg = "format response has failed.";
			UploadEntryPoint.LOGGER.error(strErrMsg, e);
			throw new InternalErrorException(strErrMsg);
		}

		writeResponse(resp, null, format, formatedResponse);
	}
}
