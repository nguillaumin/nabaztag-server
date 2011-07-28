package net.violet.platform.api.actions.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.authentication.SessionManager;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.BlackListedException;
import net.violet.platform.api.exceptions.ForbiddenException;
import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSessionException;
import net.violet.platform.api.exceptions.NoSuchItemException;
import net.violet.platform.api.exceptions.NoSuchObjectException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.api.exceptions.ParentalFilterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.files.FilesManager;
import net.violet.platform.message.AddressedMessage;
import net.violet.platform.util.DicoTools;

import org.apache.log4j.Logger;

/**
 * @author christophe - Violet
 */
public abstract class AbstractSendMessageAction extends AbstractMessageAction {

	private static final Logger LOGGER = Logger.getLogger(AbstractSendMessageAction.class);

	private enum MODALITY {
		SOUND("net.violet.sound", Boolean.TRUE), PICTURE("net.violet.picture", Boolean.FALSE), DEFAULT("net.violet.launch.file", Boolean.FALSE);

		private static final Map<MimeType.MIME_TYPES, MODALITY> MIME_TYPE_MODALITY;

		static {
			final Map<MimeType.MIME_TYPES, MODALITY> theMap = new HashMap<MimeType.MIME_TYPES, MODALITY>();

			for (final MimeType.MIME_TYPES aType : MimeType.MIME_TYPES.AUDIO) {
				theMap.put(aType, SOUND);
			}

			for (final MimeType.MIME_TYPES aType : MimeType.MIME_TYPES.PICTURE) {
				theMap.put(aType, PICTURE);
			}

			MIME_TYPE_MODALITY = Collections.unmodifiableMap(theMap);
		}

		private final String mModality;
		private final Boolean mIsStreaming;

		private MODALITY(String inModality, Boolean isStreaming) {
			this.mModality = inModality;
			this.mIsStreaming = isStreaming;
		}

		private Map<String, Object> getPojoModality() {
			final Map<String, Object> theMap = new HashMap<String, Object>();
			theMap.put("modality", this.mModality);
			theMap.put("streaming", this.mIsStreaming);

			return theMap;
		}

		private static MODALITY find(MimeType.MIME_TYPES inType) {
			final MODALITY theModality = MODALITY.MIME_TYPE_MODALITY.get(inType);

			if (theModality == null) {
				return DEFAULT;
			}

			return theModality;
		}

		public static Map<String, Object> getPOJOSequence(Files inFile) {
			final Map<String, Object> seqMap = new HashMap<String, Object>();
			seqMap.put("type", "expression");

			final MODALITY theModality = MODALITY.find(inFile.getType());

			seqMap.putAll(theModality.getPojoModality());

			seqMap.put("url", inFile.getPath());

			return seqMap;
		}

	}

	protected abstract Map<String, Object> getMessageAsPojoMap(ActionParam inParam) throws ForbiddenException, InvalidSessionException, InvalidParameterException, ConversionException, NoSuchItemException, NoSuchPersonException, InternalErrorException;

	protected void afterMessageSent(AddressedMessage inMsgSent) {

	}

	protected List<VObjectData> getRecipients(ActionParam inParam) throws InvalidParameterException, NoSuchObjectException, ParentalFilterException, BlackListedException, NoSuchPersonException, ForbiddenException, InvalidSessionException {
		final List<String> theObjectList = inParam.getList("recipients", true);
		return VObjectData.checkObject(theObjectList, getSender(inParam), inParam.getCallerAPIKey());
	}

	protected UserData getSender(ActionParam inParam) throws InvalidParameterException, ForbiddenException, InvalidSessionException {
		return SessionManager.getUserFromSessionParam(inParam);
	}

	public ActionType getType() {
		return ActionType.CREATE;
	}

	public boolean isCacheable() {
		return false;
	}

	public long getExpirationTime() {
		return 0;
	}

	@Override
	public List<ApplicationClass> getAuthorizedApplicationClasses() {
		return Application.CLASSES_UI;
	}

	protected static Palette getPalette(ActionParam inParam) throws InvalidParameterException {

		final String paletteName = inParam.getString("name", MessageData.Palette.RANDOM.getName()); //défault palette tiré aléatoirement
		final Palette palette = MessageData.Palette.findPaletteByName(paletteName);

		if (palette == null) {
			throw new InvalidParameterException(APIErrorMessage.NO_VALID_PALETTE, paletteName);
		}

		return palette;
	}

	public static Files postPojoMessage(Map<String, Object> inPojoMessage) {
		try {
			final TmpFile theFile = FilesManager.TMP_MANAGER.new TmpFile(ConverterFactory.JSON.convertTo(inPojoMessage).getBytes("UTF-8"));
			return FilesData.post(theFile, Files.CATEGORIES.MESSAGES, MimeType.MIME_TYPES.JSON).getReference();
		} catch (final Exception e) {
			AbstractSendMessageAction.LOGGER.fatal(e, e);
		}

		return null;
	}

	public static Map<String, Object> getUnsupportedContent(Lang inLang) {
		final Map<String, Object> altMap = new HashMap<String, Object>();
		altMap.put("type", "expression");
		if (inLang != null) {
			altMap.put("modality", "net.violet.tts." + inLang.getIETFCode());
			altMap.put("text", DicoTools.dico(inLang, Files.UNSUPPORTED_CONTENT));
		} else {
			altMap.put("modality", "net.violet.tts");
			altMap.put("text", Files.UNSUPPORTED_CONTENT);
		}
		return altMap;
	}

	public static Map<String, Object> getPOJOSequenceMap(Files inFile) {
		return MODALITY.getPOJOSequence(inFile);
	}

	public static boolean isStreamFile(MusicData theMusic) {
		return ((theMusic.getMusic_type() != Music.TYPE_MP3_LITTLE_WORDS) && (theMusic.getMusic_type() != Music.TYPE_MP3_LIBRARY));
	}

}
