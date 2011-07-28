package net.violet.platform.api.actions.items;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.converters.ContentType;
import net.violet.content.manager.ConvertersManager;
import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidFileFormatException;
import net.violet.platform.api.exceptions.InvalidMimeTypeException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchFileException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;

import org.apache.log4j.Logger;

public class Generate extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(Generate.class);

	public static final String FORMATS = "formats";

	public enum GENERATION_TYPES {
		ADP(ContentType.ADP, MimeType.MIME_TYPES.ADP, ObjectType.NABAZTAG_V1, ObjectType.DALDAL),
		CHOR(ContentType.CHOR, MimeType.MIME_TYPES.CHOR, ObjectType.NABAZTAG_V1, ObjectType.DALDAL),
		MP3_32(ContentType.MP3_32, MimeType.MIME_TYPES.A_MPEG, ObjectType.NABAZTAG_V2),
		MP3_128(ContentType.MP3_128, MimeType.MIME_TYPES.A_MPEG, ObjectType.MIRROR);

		private static final Map<String, GENERATION_TYPES> TYPES;

		static {
			final Map<String, GENERATION_TYPES> theTypes = new HashMap<String, GENERATION_TYPES>();
			for (final GENERATION_TYPES aType : GENERATION_TYPES.values()) {
				theTypes.put(aType.getLabel(), aType);
			}
			TYPES = Collections.unmodifiableMap(theTypes);
		}

		public static final GENERATION_TYPES findByLabel(String inLabel) {
			return GENERATION_TYPES.TYPES.get(inLabel.toLowerCase());
		}

		private final ContentType contentType;
		private final MimeType.MIME_TYPES mimeType;
		private final ObjectType[] types;

		private GENERATION_TYPES(ContentType inCType, MimeType.MIME_TYPES inMType, ObjectType... inTypes) {
			this.contentType = inCType;
			this.mimeType = inMType;
			this.types = inTypes;
		}

		public ObjectType[] getObjectTypes() {
			return this.types;
		}

		public ContentType getContentType() {
			return this.contentType;
		}

		public MimeType.MIME_TYPES getMimeType() {
			return this.mimeType;
		}

		public String getLabel() {
			return toString().toLowerCase();
		}

	}

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, InvalidMimeTypeException, InternalErrorException, InvalidFileFormatException {

		final String inMimeType = inParam.getMainParamAsString();
		final MimeType.MIME_TYPES theMimeType = MimeType.MIME_TYPES.findByLabel(inMimeType);

		if ((theMimeType == null) || (theMimeType.getContentType() == null)) {
			throw new InvalidMimeTypeException(inMimeType);
		}

		final List<GENERATION_TYPES> theFormats = new LinkedList<GENERATION_TYPES>();
		for (final String aTypeName : inParam.<String> getList(Generate.FORMATS, true)) {
			GENERATION_TYPES theType;
			if ((theType = GENERATION_TYPES.findByLabel(aTypeName)) == null) {
				throw new InvalidFileFormatException(aTypeName);
			}
			theFormats.add(theType);
		};

		final Map<String, String> theResult = new HashMap<String, String>();

		for (final GENERATION_TYPES aType : theFormats) {
			TmpFile theTmpFile = null;
			try {
				try {
					theTmpFile = FilesManager.TMP_MANAGER.new TmpFile(inParam.getBody());
				} catch (final IOException e) {
					Generate.LOGGER.fatal(e, e);
					emergencyExit(theResult, inParam);
					throw new InternalErrorException();
				}

				final FilesData theResultFile = FilesData.getData(FilesManagerFactory.FILE_MANAGER.post(ConvertersManager.convert(theMimeType.getContentType(), aType.getContentType(), new TmpFileWrapper(theTmpFile)), Files.CATEGORIES.BROAD, aType.getMimeType()));
				if ((theResultFile != null) && theResultFile.isValid()) {
					theResult.put(aType.getLabel(), theResultFile.getApiId(inParam.getCaller()));
				}
			} finally {
				if (theTmpFile != null) {
					theTmpFile.delete();
				}
			}
		}

		return theResult;
	}

	private void emergencyExit(Map<String, String> inFiles, ActionParam inParam) {
		for (final String fileId : inFiles.values()) {
			try {
				final FilesData theFile = FilesData.getFilesData(fileId, inParam.getCallerAPIKey());
				theFile.delete();
			} catch (final NoSuchFileException e) {
				Generate.LOGGER.fatal(e, e);
			}
		}
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

}
