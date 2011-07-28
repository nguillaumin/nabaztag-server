package net.violet.platform.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.StringShop;
import net.violet.common.utils.io.StreamTools;
import net.violet.content.converters.ContentType;
import net.violet.platform.applications.EarsCommunionHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.InterruptionLogImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.InterruptionLogImpl.WHAT_OPTION;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.interactif.EntryPoint;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.AbstractRecoService;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.AbstractRecoService.RadioList;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Classe pour record.jsp. traitement de la reconnaissance vocale.
 */
public class VoiceHandler extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(EntryPoint.class);

	private static enum COMMAND {
		RECO(WHAT_OPTION.BUTTON_RECO) {

			private final int MIN_RECO_CONFIDENCE = 30;
			private final Lang DEFAULT = Factories.LANG.find(Lang.LANG_US_ID);
			private final Set<Lang> TELISMA_LANGS;
			private final Pattern INTERPRETATION = Pattern.compile("interpretation confidence=\"([0-9]+)\"");
			private final Pattern INSTANCE = Pattern.compile("<xf:instance>([\\w]+)</xf:instance>");

			{
				final Set<Lang> theLangs = new HashSet<Lang>();
				for (final String anIsoCode : new String[] { "fr-FR", "en-GB", "de-DE", "es-ES" }) {
					theLangs.add(Factories.LANG.findByIsoCode(anIsoCode));
				}

				this.TELISMA_LANGS = Collections.unmodifiableSet(theLangs);
			}

			private String getValueFromXML(Pattern inPattern, String inXml, String inDefault) {

				final Matcher theMatcher = inPattern.matcher(Matcher.quoteReplacement(inXml));
				if (theMatcher.find()) {
					return theMatcher.group(1);
				}
				return inDefault;
			}

			@Override
			public void process(VObject inObject, byte[] inFile) {
				String xml = StringShop.EMPTY_STRING;

				if (inObject != null) {
					final Lang objectLang = inObject.getPreferences().getLangPreferences();
					final File theFile = FilesManagerFactory.FILE_MANAGER.saveTelismaFile(inObject.getObject_serial(), inFile);
					final StringBuilder theBuilder = new StringBuilder(Constantes.RECO_PATH).append(theFile.getName());
					theBuilder.append("&language=").append(((this.TELISMA_LANGS.contains(objectLang)) ? objectLang : this.DEFAULT).getIsoCode());
					try {
						xml = StreamTools.readString(new URL(theBuilder.toString()));
					} catch (final IOException e) {
						VoiceHandler.LOGGER.fatal(e, e);
						xml = StringShop.EMPTY_STRING;
					}
					theFile.delete();

					// clean xml
					xml = xml.replaceAll("\n|\t", StringShop.EMPTY_STRING);

					final int theConfidence = Integer.parseInt(getValueFromXML(this.INTERPRETATION, xml, "0"));

					final String theWord;
					if (theConfidence >= this.MIN_RECO_CONFIDENCE) {
						theWord = getValueFromXML(this.INSTANCE, xml, AbstractRecoService.BAD_RECO);
					} else {
						theWord = AbstractRecoService.BAD_RECO;
					}

					final String serviceKey;

					if (theWord.contains(RadioList.RADIO_KEY_WORD)) {
						serviceKey = RadioList.RADIO_KEY_WORD;
					} else if (AbstractRecoService.RECO_SERVICES.containsKey(theWord)) {
						serviceKey = theWord;
					} else {
						serviceKey = null;
					}

					if (serviceKey != null) {
						AbstractRecoService.RECO_SERVICES.get(serviceKey).process(inObject, theWord);
					}
				}

				sendLog(inObject, xml);
			}
		},
		PUSH2TALK(WHAT_OPTION.BUTTON_PTT) {

			private static final String BAD_COMMUNION = "BADCOMMUNION";

			@Override
			public void process(VObject inObject, byte[] inFile) {
				boolean isMarried = false;
				final Application earsCommunion = Application.NativeApplication.EARS_COMMUNION.getApplication();
				final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(earsCommunion, inObject);

				for (final SubscriptionData aSubscription : subscriptions) {
					final Object objectSubscriptionSetting = aSubscription.getSettings().get(EarsCommunionHandler.STATUS);
					if (NOTIFICATION_STATUS.ACCEPTED.toString().equals(objectSubscriptionSetting.toString())) {

						final VObject friendObject = Factories.VOBJECT.find(Long.parseLong(aSubscription.getSettings().get(EarsCommunionHandler.FRIEND_OBJECT_ID).toString()));
						final User theuser = friendObject.getOwner();
						final boolean needsTreatment = VObjectData.getData(friendObject).getObjectType().instanceOf(ObjectType.NABAZTAG_V1);
						final Files theFile = FilesManagerFactory.FILE_MANAGER.post(inFile, ContentType.WAV, ContentType.MP3_32, Files.CATEGORIES.BROAD, needsTreatment, needsTreatment, MimeType.MIME_TYPES.A_MPEG);

						if (theFile != null) { // url crée donc on peut envoyer le message non vide.
							MessageServices.sendUserMessage(theuser, theFile, friendObject, DicoTools.dico(theuser.getAnnu().getLangPreferences(), "PushToTalk/message"), null, Palette.RANDOM, false);
							VoiceHandler.LOGGER.debug("Message ptt send");
						}
						isMarried = true;
					}
				}

				if (!isMarried) {
					final Files musicFile = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.RECO, inObject.getPreferences().getLangPreferences()).get(BAD_COMMUNION).get(0).getFiles();
					try {
						MessageServices.sendAudioMessage(inObject, musicFile);
					} catch (final IllegalArgumentException e) {
						VoiceHandler.LOGGER.fatal(e, e);
					}
				}

				sendLog(inObject, net.violet.common.StringShop.EMPTY_STRING);
			}
		};

		private static final Map<String, COMMAND> COMMANDS;
		static {
			final Map<String, COMMAND> theMap = new HashMap<String, COMMAND>();
			theMap.put("0", RECO);
			theMap.put("1", PUSH2TALK);
			COMMANDS = Collections.unmodifiableMap(theMap);
		}

		private final WHAT_OPTION mOption;

		private COMMAND(WHAT_OPTION inOption) {
			this.mOption = inOption;
		}

		public abstract void process(VObject inObject, byte[] inFile);

		public void sendLog(VObject inObject, String inLog) {
			InterruptionLogImpl.insert(inObject, this.mOption, inLog);
		}

		public static COMMAND findById(String inId) {
			return COMMAND.COMMANDS.get(inId);
		}
	}

	/**
	 * Point d'entrée de la servlet.
	 */
	@Override
	public void doPost(HttpServletRequest inRequest, HttpServletResponse inResponse) {

		InputStream theStream = null;
		try {
			final String sn = inRequest.getParameter("sn");

			final COMMAND theCommand = COMMAND.findById(inRequest.getParameter("m")); // ptt = 1 ou reco = 0 ;

			final byte[] resultFile = StreamTools.readBytes(theStream = inRequest.getInputStream(), inRequest.getContentLength());

			final VObject theObject = Factories.VOBJECT.findBySerial(sn);

			if (resultFile != null) { // on a pu récupérer la reco
				theObject.setLastActivityTime();
				theCommand.process(theObject, resultFile);
			} else {
				theCommand.sendLog(theObject, "Failure : no source");
			}

		} catch (final SocketTimeoutException e) {
			// this happen frequently when the user push the button
			// for a voice recognition, but don't say anything..

		} catch (final Throwable e) {
			VoiceHandler.LOGGER.fatal(e, e);

		} finally {
			IOUtils.closeQuietly(theStream);
		}
	}

}
