package net.violet.platform.api.actions.applications;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidDataException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.files.FilesManager;

/**
 *
 * @author christophe - Violet
 */
public class UploadResource extends AbstractAction {

	/**
	 * @throws NoSuchApplicationException 
	 * @throws InvalidParameterException 
	 * @throws InvalidDataException 
	 * @throws InternalErrorException 
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchApplicationException, InternalErrorException {

		final ApplicationData app = getRequestedApplication(inParam, null);
		final String name = inParam.getString("name", true);

		// "body" contains the content of any file uploaded with the PUT method 
		final byte[] fileContent = inParam.getBody();

		final MimeType.MIME_TYPES mimeType = MimeType.MIME_TYPES.findByLabel(inParam.getString("mime-type"));
		final TmpFile theFile;
		try {
			theFile = FilesManager.TMP_MANAGER.new TmpFile(fileContent);
		} catch (final IOException e) {
			throw new InternalErrorException(e.getMessage());
		}
		final FilesData uploadedFileData = FilesData.post(theFile, Files.CATEGORIES.PUBLIC_APPLICATION, mimeType);

		// build the new resource map
		final Map<String, Object> rscMap = new HashMap<String, Object>(8);
		rscMap.put("fileSize", fileContent.length);
		rscMap.put("fileId", uploadedFileData.getApiId(inParam.getCaller()));
		rscMap.put("url", uploadedFileData.getPath());
		rscMap.put("name", name);
		rscMap.put("app", app.getName());

		return rscMap;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.CREATE;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return false;
	}

	/**
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return 0;
	}

}
