package net.violet.platform.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.Converter;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchPersonException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.interactif.SecureStream;
import net.violet.platform.message.elements.Directive;
import net.violet.platform.message.elements.Expression;
import net.violet.platform.message.elements.SecurePart;
import net.violet.platform.message.elements.SequencePart;
import net.violet.platform.message.elements.SequencePartFactory;
import net.violet.platform.message.elements.SignatureAnimWrapper;
import net.violet.platform.message.elements.SignatureMusicWrapper;
import net.violet.platform.message.elements.StartInteractiveWrapper;
import net.violet.platform.message.elements.StreamWrapper;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.voice.TTSServices;

/**
 * A Message object which can be modified.
 */
public class MessageDraft extends AddressedMessage {

	/**
	 * Regex for the TTS command
	 */
	private static final Pattern TTS_COMMAND_REGEX = Pattern.compile("TTS ([^\\s]+)\\s+(.*)");

	/**
	 * @param inPojo
	 * @param inRfid
	 * @param inCaller
	 * @return
	 * @throws NoSuchPersonException
	 * @throws InvalidParameterException
	 */
	public static List<MessageDraft> createFromPojo(Map<String, Object> inPojo, VObject inRfid, String inAPIKey) throws NoSuchPersonException, InvalidParameterException {

		// Retrieves the involved sender
		final String theFromID = (String) inPojo.get("from");
		final User sender;
		if (theFromID != null) {
			final UserData senderData = UserData.findByAPIId(theFromID, inAPIKey);
			if (senderData == null) {
				throw new NoSuchPersonException(APIErrorMessage.INVALID_SENDER);
			}
			sender = senderData.getReference();
		} else {
			sender = null;
		}

		// Builds the list of receivers
		if (inPojo.get("to") == null) {
			throw new InvalidParameterException(APIErrorMessage.MISSING_PARAMETER, "to");
		}
		if (!(inPojo.get("to") instanceof List)) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_LIST, "to");
		}

		final List<VObjectData> receiversList = new ArrayList<VObjectData>();
		for (final String aReceiver : (List<String>) inPojo.get("to")) {
			final VObjectData receiver = VObjectData.findByAPIId(aReceiver, inAPIKey);
			if (receiver != null) {
				receiversList.add(receiver);
			}
		}

		// The title (optional)
		final String msgTitle = (String) inPojo.get("title");

		// The date (optional)
		CCalendar theDeliveryDate = null;
		if ((inPojo.get("date") != null) && (sender != null)) {
			theDeliveryDate = new CCalendar(TimeZone.getTimeZone(sender.getTimezone().getTimezone_javaId()));
			theDeliveryDate.setTime((Date) inPojo.get("date"));
		}

		// The application
		final String applicationName = (String) inPojo.get("application");
		final Application app = Factories.APPLICATION.findByName(applicationName);

		// The sequences
		final List<Map<String, Object>> theSequenceArray = (List<Map<String, Object>>) inPojo.get("sequence");
		if (theSequenceArray == null) {
			throw new InvalidParameterException(APIErrorMessage.MISSING_PARAMETER, net.violet.common.StringShop.EMPTY_STRING);
		}

		final List<SequencePart> theSequence = SequencePartFactory.buildSequence(theSequenceArray, inRfid, app);

		// creates MessageDraft objects for each receiver using the specified
		// sequences
		final List<MessageDraft> theMessages = new ArrayList<MessageDraft>();
		for (final VObjectData receiver : receiversList) {
			final MessageDraft aMessage = new MessageDraft(receiver.getReference());
			aMessage.setSender(sender);
			aMessage.setTitle(msgTitle);
			aMessage.setApplication(app);
			aMessage.mPartsList.addAll(theSequence);
			aMessage.setDeliveryDate(theDeliveryDate);
			theMessages.add(aMessage);
		}

		return theMessages;
	}

	private final List<SequencePart> mIntro;
	private final List<SequencePart> mOutro;
	private final List<SequencePart> mPartsList;

	/**
	 * Creates a MessageDraft with the given object as receiver.
	 * 
	 * @param inDest the receiver
	 */
	public MessageDraft(VObject inDest) {
		super(inDest);
		this.mPartsList = new ArrayList<SequencePart>();
		this.mIntro = new ArrayList<SequencePart>();
		this.mOutro = new ArrayList<SequencePart>();
	}

	/**
	 * Adds an audio file to play. The url refers to the sound to play. The
	 * boolean parameters are used to describe the reading object behavior :
	 * should it stream and/or move its ears.
	 * 
	 * @param url
	 * @param streaming
	 * @param movingEars
	 */
	public void addAudio(String url, boolean streaming, boolean movingEars) {
		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("modality", "net.violet.sound.mp3");
		pojo.put("url", url);
		pojo.put("streaming", streaming);
		pojo.put("withEar", movingEars);

		this.mPartsList.add(new Expression(pojo));
	}

	public void addWebRadio(String url) {
		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("modality", "net.violet.webradio");
		pojo.put("url", url);
		this.mPartsList.add(new Expression(pojo));
	}

	/**
	 * Adds a serie of TTS messages indexed by their language
	 * 
	 * @param inTTSMap
	 */
	public void addTTS(Map<Lang, String> inTTSMap) {

		final Map<String, Object> mainModality = new HashMap<String, Object>();
		final List<Map<String, Object>> alternatives = new ArrayList<Map<String, Object>>();
		int count = 0;

		for (final Lang lang : inTTSMap.keySet()) {

			if (count == 0) {
				mainModality.put("modality", "net.violet.tts." + lang.getIETFCode());
				mainModality.put("text", inTTSMap.get(lang));
				count++;

			} else { // c'est une alternative
				final Map<String, Object> alternative = new HashMap<String, Object>();
				alternative.put("modality", "net.violet.tts." + lang.getIETFCode());
				alternative.put("text", inTTSMap.get(lang));
				alternatives.add(alternative);
			}
		}

		if (alternatives.size() > 0) {
			mainModality.put("alt", alternatives);
		}

		this.mPartsList.add(new Expression(mainModality));
	}

	public void addPage2Browse(String inUrl) {
		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("modality", "net.violet.browser");
		pojo.put("url", inUrl);
		this.mPartsList.add(new Expression(pojo));
	}

	/**
	 * Adds a secure audio content to the message.
	 * 
	 * @param inUrl url to the content to add.
	 */
	public void addAudioStreamSecure(String inName, long inPosition) {
		final String theURL = SecureStream.getStreamUrl(inName, inPosition);
		this.mPartsList.add(new SecurePart(theURL));
	}

	/**
	 * Ajoute une chorégraphie au message.
	 * 
	 * @param url chemin/url vers le contenu à ajouter.
	 */
	public void addChoreography(String url, boolean streaming) {
		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("modality", "net.violet.choreography");
		pojo.put("url", url);
		pojo.put("streaming", streaming);
		this.mPartsList.add(new Expression(pojo));
	}

	/**
	 * Ajoute une palette pour la chorégraphie d'un message.
	 * 
	 * @param palette
	 */
	public void addPalette(Palette inPalette) {

		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("modality", "net.violet.palette");
		pojo.put("name", inPalette.getName());
		this.mPartsList.add(new Expression(pojo));
	}

	/**
	 * Lancement du mode interactif
	 * 
	 * @param inUrl chemin/url vers le contenu à ajouter.
	 */
	public void addStartInteractive(String inUrl) {
		this.mPartsList.add(new StartInteractiveWrapper(inUrl));
	}

	/**
	 * Adds an instruction to start the interactive mode. Caution : the
	 * MessageDraft object MUST have an application to correctly start the
	 * interactive mode.
	 * 
	 * @param rfid
	 */
	public void addStartInteractive(VObject rfid) {
		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("action", "start-interactive");

		this.mPartsList.add(new Directive(pojo, rfid, getApplication()));
	}

	/**
	 * Arret du mode interactif
	 */
	public void addEndInteractive() {
		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("action", "stop-interactive");

		this.mPartsList.add(new Directive(pojo, null, getApplication()));
	}

	/**
	 * Identification d'un flux de streaming.
	 * 
	 * @param inStreamId id du stream.
	 */
	public void addStreamId(String inStreamId) {
		this.mPartsList.add(new StreamWrapper(inStreamId, true));
	}

	/**
	 * Fin d'un flux de streaming.
	 * 
	 * @param inStreamId id du stream.
	 */
	public void addStreamStop(String inStreamId) {
		this.mPartsList.add(new StreamWrapper(inStreamId, false));
	}

	/**
	 * Ajoute un setting
	 * 
	 * @param inSetting : ex => <setting>=<value>
	 */
	public void addSetting(String inSetting) {
		final Map<String, Object> pojo = new HashMap<String, Object>();
		final Map<String, String> settings = new HashMap<String, String>();
		settings.put(inSetting.split("=")[0], inSetting.split("=")[1]);
		pojo.put("settings", settings);

		this.mPartsList.add(new Directive(pojo, null, getApplication()));
	}

	/**
	 * Ajoute une instruction pour attendre un certain temps sans rien faire
	 * 
	 * @param nbMs le nombre de milisecondes qu'on veut que le lapin attende
	 */
	public void addWaitMs(int nbMs) {

		final Map<String, Object> pojo = new HashMap<String, Object>();
		pojo.put("modality", "net.violet.wait");
		pojo.put("time_ms", nbMs);

		this.mPartsList.add(new Expression(pojo));
	}

	public void addSignature(MessageSignature inSignature) {
		this.mIntro.clear();
		this.mOutro.clear();

		if (inSignature == null) {
			return;
		}

		final String theIntroMusicUrl = inSignature.getIntroMusicUrl();
		if (theIntroMusicUrl != null) {
			this.mIntro.add(new SignatureMusicWrapper(theIntroMusicUrl));
		}

		final String theIntroAnimChoregraphyUrl = inSignature.getIntroChoregraphyUrl();
		if (theIntroAnimChoregraphyUrl != null) {
			this.mIntro.add(new SignatureAnimWrapper(inSignature.getColor4Message(), theIntroAnimChoregraphyUrl));
		}

		final String theOutroMusicUrl = inSignature.getOutroMusicUrl();
		if (theOutroMusicUrl != null) {
			this.mOutro.add(new SignatureMusicWrapper(theOutroMusicUrl));
		}

		final String theOutroAnimChoregraphyUrl = inSignature.getOutroChoregraphyUrl();
		if (theOutroAnimChoregraphyUrl != null) {
			this.mOutro.add(new SignatureAnimWrapper(inSignature.getColor4Message(), theOutroAnimChoregraphyUrl));
		}
	}

	/**
	 * Adds a Pojo object to the message draft. The given string should be
	 * encoded according to the provided format, and once decoded it must be a
	 * valid pojo according to the VioletOS Specification.
	 * 
	 * @param inPojo
	 * @param format
	 * @throws ConversionException
	 * @throws InvalidParameterException
	 */
	public void addPojo(String inPojo, MimeType.MIME_TYPES format) throws ConversionException, InvalidParameterException {

		final Converter converter = ConverterFactory.getConverter(format);
		if (converter == null) {
			return;
		}

		this.mPartsList.addAll(SequencePartFactory.buildSequence(Collections.singletonList(converter.<Map<String, Object>> convertFrom(inPojo)), null, getApplication()));
	}

	/**
	 * Retourne la liste des séquences.
	 */
	public final List<Sequence> getSequenceList() {
		final List<Sequence> result = new ArrayList<Sequence>();
		final VObject dest = getReceiver();

		for (final SequencePart part : this.mIntro) {
			result.addAll(part.getSequence(dest));
		}

		for (final SequencePart part : this.mPartsList) {
			result.addAll(part.getSequence(dest));
		}

		for (final SequencePart part : this.mOutro) {
			result.addAll(part.getSequence(dest));
		}

		return result;
	}

	public final List<SequencePart> getSequencePart() {
		final List<SequencePart> result = new ArrayList<SequencePart>();
		result.addAll(this.mIntro);
		result.addAll(this.mPartsList);
		result.addAll(this.mOutro);
		return result;
	}

	public void fillMessageDraft(String inTitle, MessageSignature inSignature, MessageServices.Body[] inBody) {
		setTitle(inTitle);

		if (inSignature != null) {
			addSignature(inSignature);
		}

		fillMessageDraft(inBody);
	}

	public void fillMessageDraft(MessageServices.Body... inBody) {

		for (int i = 0; i < inBody.length; i++) {
			final Files bodyElement = inBody[i].getFile();
			final Palette paletteElement = inBody[i].getPalette();

			if ((bodyElement.getPath() != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(bodyElement.getPath())) {
				addAudio(bodyElement.getPath(), inBody[i].isStream(), true);
			} else if ((bodyElement.getPath2midi() != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(bodyElement.getPath2midi())) {
				addAudio(bodyElement.getPath2midi(), inBody[i].isStream(), true);
			}

			final VObjectData theReceiver = VObjectData.getData(getReceiver());

			if ((theReceiver != null) && theReceiver.isValid() && (theReceiver.getObjectType().instanceOf(ObjectType.NABAZTAG_V1) || theReceiver.getObjectType().instanceOf(ObjectType.DALDAL)) && (bodyElement.getPath2chor() != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(bodyElement.getPath2chor())) {
				addPalette(paletteElement);
				addChoreography(bodyElement.getPath2chor(), inBody[i].isStream());
			}
		}
	}

	/**
	 * Ajoute des séquences à partir d'une chaîne dans un langage intermédiaire.
	 */
	public void addSequences(String[] inSequences, boolean isSecure) {

		for (final String sequence : inSequences) {

			if (sequence.startsWith("PLAY ")) { // PLAY son -> addAudio
				final String theUrl = sequence.replaceAll("PLAY ", net.violet.common.StringShop.EMPTY_STRING);
				addAudio(theUrl, false, true);

			} else if (sequence.startsWith("STREAM ")) {// STREAM [palette] url
				// -> addAudio
				final StringTokenizer theStreamOption = new StringTokenizer(sequence, net.violet.common.StringShop.SPACE);
				final int nb = theStreamOption.countTokens();
				theStreamOption.nextToken(); // exit STREAM
				if (nb == 2) {
					addAudio(theStreamOption.nextToken(), true, true);
				} else if (nb == 3) {
					final Integer paletteNum = Integer.parseInt(theStreamOption.nextToken());
					final Palette palette = Palette.findPaletteByNum(paletteNum);
					addPalette(palette);
					addAudio(theStreamOption.nextToken(), true, true);
				}

			} else if (sequence.startsWith("STREAM_ID ")) {// STREAM_ID foo ->
				// addStreamId
				final String theID = sequence.replaceAll("STREAM_ID ", net.violet.common.StringShop.EMPTY_STRING);
				addStreamId(theID);

			} else if (sequence.startsWith("STREAM_STOP ")) {// STREAM_STOP foo
				// ->
				// addStreamStop
				final String theID = sequence.replaceAll("STREAM_STOP ", net.violet.common.StringShop.EMPTY_STRING);
				addStreamStop(theID);

			} else if (sequence.startsWith("SSTREAM ") && isSecure) {// SSTREAM
				// [
				// palette
				// ] url
				// position
				// ->
				// addAudio
				final StringTokenizer theStreamOption = new StringTokenizer(sequence, net.violet.common.StringShop.SPACE);
				final int nb = theStreamOption.countTokens();
				theStreamOption.nextToken(); // exit SSTREAM
				if (nb == 3) {
					addAudioStreamSecure(theStreamOption.nextToken(), ConvertTools.atol(theStreamOption.nextToken()));
				} else if (nb == 4) {
					final int paletteNum = ConvertTools.atoi(theStreamOption.nextToken());
					final Palette palette = Palette.findPaletteByNum(paletteNum);
					addPalette(palette);
					addAudioStreamSecure(theStreamOption.nextToken(), ConvertTools.atol(theStreamOption.nextToken()));
				}

			} else if (sequence.startsWith("CHOR")) { // CHOR [palette] url -> addChoregraphy
				final StringTokenizer theChorOption = new StringTokenizer(sequence, net.violet.common.StringShop.SPACE);
				final int nb = theChorOption.countTokens();
				theChorOption.nextToken(); // exit CHOR
				if (nb == 2) {
					addChoreography(theChorOption.nextToken(), false);
				} else if (nb == 3) {
					final Integer paletteNum = Integer.parseInt(theChorOption.nextToken());
					final Palette palette = Palette.findPaletteByNum(paletteNum);
					final String theUrl = theChorOption.nextToken();
					addPalette(palette);
					addChoreography(theUrl, false);
				}

			} else if (sequence.startsWith("TTS")) {// TTS voice message ->
				// addAudio
				final Matcher theMatcher = MessageDraft.TTS_COMMAND_REGEX.matcher(sequence);
				if (theMatcher.matches()) {
					final String voice = theMatcher.group(1);
					final String message = theMatcher.group(2);
					final TtsVoice theTts = Factories.TTSVOICE.findByCommandOrName(voice, 0);
					final Files theFile = TTSServices.getDefaultInstance().postTTS(message, false, true, theTts);

					if (theFile != null) {
						addAudio(theFile.getPath(), false, true);
					}
				}

			} else if (sequence.startsWith("END")) { // END -> addEndInteractiveMode
				addEndInteractive();
			} else if (sequence.startsWith("START_INTERACTIVE") && isSecure) { // START_INTERACTIVE url - > addStartInteractive ( que si
				// isSecure
				// )
				final String theUrl = sequence.replaceAll("START_INTERACTIVE ", net.violet.common.StringShop.EMPTY_STRING);
				addStartInteractive(theUrl);
			}
		}
	}
}
