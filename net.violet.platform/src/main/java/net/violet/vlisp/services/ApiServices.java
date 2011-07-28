package net.violet.vlisp.services;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.violet.common.utils.DigestTools;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.content.converters.ContentType;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.FriendsListsImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.NabcastValImpl;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.TtsVoiceImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageReceivedData;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.object.EarMng;
import net.violet.platform.util.Api;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.StringShop;
import net.violet.platform.util.ThrottleManager;
import net.violet.platform.util.ThrottleManager.ThrottleProfile;
import net.violet.platform.voice.TTSServices;
import net.violet.platform.xmpp.JabberMessageFactory;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public final class ApiServices {

	private static final Logger LOGGER = Logger.getLogger(ApiServices.class);

	private static final String NORABBIT = "<message>NORABBIT</message><comment>You don't have a rabbit</comment>";
	private static final String ABUSE_RESPONSE = "<message>ABUSESENDING</message> <comment>Too many messages sent in the allowed time, try again later please</comment>";
	private static final String END_TAG = "\"/>";
	private static final ThrottleManager<User> SENDMSG_THROTTLE = ThrottleManager.getRessourcesThrottle(new ThrottleProfile(10, 1000));
	private final User mSender;
	private final VObject mReceiver;

	/**
	 * Constructeur à partir d'un user et d'un object.
	 */
	private ApiServices(User inUser, VObject inObject) {
		this.mSender = inUser;
		this.mReceiver = inObject;
	}

	// TODO Il faudrait le mettre dans une table si cela deviens gros
	// list des ip autorisé pour utiliser l'api_friend , TODO futur le mettre en
	// cache
	private static final List<String> IPS_FRIEND = new LinkedList<String>();
	private static final List<String> IPS_NABME = new LinkedList<String>();
	// list des clé autorisé pour utiliser l'api_nabme et l'api_srv_nabme en
	// correspondance avec leur user , TODO futur le mettre en cache
	private static final Map<String, String> KEY_USERS = new HashMap<String, String>();
	static {
		ApiServices.IPS_FRIEND.add("213.193.235.194"); // watchmouse
		ApiServices.IPS_FRIEND.add("213.193.235.195");
		ApiServices.IPS_FRIEND.add("216.139.225.125");
		ApiServices.IPS_FRIEND.add("83.138.163.29");
		ApiServices.IPS_FRIEND.add("216.139.225.253");
		ApiServices.IPS_FRIEND.add("216.139.225.254");
		ApiServices.IPS_FRIEND.add("217.128.109.105");// violetTest Tests
		ApiServices.IPS_FRIEND.add("192.168.1.171");// machine de gérard IPS_FRIEND.add("127.0.0.1"); list des ip autorisé pour utiliser l'api_nabme , TODO futur le mettre en cache
		ApiServices.IPS_NABME.add("193.149.120.38"); // arvensis
		ApiServices.IPS_NABME.add("10.11.1.143"); // arvensis, back
		ApiServices.IPS_NABME.add("192.168.1.242"); // machine de Nico
		ApiServices.IPS_NABME.add("192.168.1.171"); // machine de gérard
		ApiServices.IPS_NABME.add("192.168.1.191"); // machine d'LP IPS_NABME.add("127.0.0.1"); // machine de gérard test
		ApiServices.IPS_NABME.add("217.128.109.105"); // violetTest
		ApiServices.IPS_NABME.add("212.241.216.198"); // simoncross.com, pour le nabme facebook

		ApiServices.KEY_USERS.put("123", "colacoca"); // arvensis
		ApiServices.KEY_USERS.put("nab_facebook2287", "FacebookAlert@FacebooAlert.com"); // machine de facebook
		ApiServices.KEY_USERS.put("MySpaceAlert", "MySpaceAlert@MySpaceAlert.com"); // myspace
		ApiServices.KEY_USERS.put("colacoca", "colacoca"); // colacoca
		ApiServices.KEY_USERS.put("nab_violetteam_568", "omevel"); // omevel
		ApiServices.KEY_USERS.put("msn", "MSNMessenger@MSNMessenger.com"); // omevel
		ApiServices.KEY_USERS.put("poweo", "poweo@poweo.com");
		ApiServices.KEY_USERS.put("goojet", "goojet@goojet.com");
		ApiServices.KEY_USERS.put("facebook", "facebook@facebook.com");
		ApiServices.KEY_USERS.put("MSGroove", "MSGroove@MSGroove.com");
	}

	/**
	 * Vérifie si IP est ok afin d'utiliser l'api_nabme
	 * 
	 * @param ipaddress : contient adresse ip
	 * @return response
	 */
	public static boolean isGoodIP(String ipaddress) {
		return ApiServices.IPS_FRIEND.contains(ipaddress);
	}

	/**
	 * Vérifie si IP est ok pour l'api nabme.
	 * 
	 * @param ipaddress : contient adresse ip
	 * @return response
	 */
	private static String BADACCESS = "<message>BADACCESS</message><comment>You don't have permission to access /vl/FR/api_nabme.jsp on this server.</comment>";

	public static void checkGoodIPNabMe(String ipaddress) throws APIException {
		if (!ApiServices.IPS_NABME.contains(ipaddress)) {
			throw new APIException(ApiServices.BADACCESS + " ipaddress " + ipaddress);
		}
	}

	/**
	 * Check si le token et le sn correspondent afin d'utiliser l'api
	 * 
	 * @param parameters : contient tous les parametres passés dans l'url
	 * @return response
	 */
	private static String NOGOODTOKENORSERIAL = "<message>NOGOODTOKENORSERIAL</message><comment>Your token or serial number are not correct !</comment>";

	public static ApiServices getBySnToken(Map<String, String> parameters) throws APIException {
		ApiServices theResult = null;
		final String tokenStr = parameters.get("token");

		if (tokenStr != null) {
			try {
				final long theToken = Long.parseLong(tokenStr);

				final VObject theObject = Factories.VOBJECT.findBySerial(parameters.get("sn"));

				if (theObject != null) {
					final User theUser = theObject.getOwner();

					if ((theUser != null) && (theToken == theUser.getUser_extconnect())) {
						theResult = new ApiServices(theUser, theObject);
					}

				}

			} catch (final NumberFormatException anException) {
				// Do nothing
			}
		}

		if (theResult == null) {
			throw new APIException(ApiServices.NOGOODTOKENORSERIAL);
		}

		return theResult;
	}

	/**
	 * Check si le token et le sn correspond afin d'utilisé l'api
	 * 
	 * @param parameters : contient tout les parametres passé dans l'url
	 * @return response
	 */
	public static ApiServices getBySnTokenKey(Map<String, String> parameters) throws APIException {
		ApiServices theResult = null;
		final String tokenStr = parameters.get("token");
		long theToken;
		if (tokenStr != null) {
			try {
				theToken = Long.parseLong(tokenStr);
			} catch (final NumberFormatException anException) {
				theToken = 0;
			}
		} else {
			theToken = 0;
		}

		VObject theObject = null;
		User theUser = null;
		if (theToken != 0) {
			theObject = Factories.VOBJECT.findBySerial(parameters.get("sn"));
			if (theObject != null) {
				theUser = theObject.getOwner();
			}
		}

		if ((theObject == null) || (theUser == null) || (theToken == 0) || (theToken != theUser.getUser_extconnect())) {
			throw new APIException(ApiServices.NOGOODTOKENORSERIAL);
		}

		final User theSender = ApiServices.getSenderByKey(parameters);
		theResult = new ApiServices(theSender, theObject);

		return theResult;
	}

	/**
	 * Check si le login et le pass sont ok afin d'utilisé l'api_widget
	 * 
	 * @param parameters : contient tout les parametres passé dans l'url
	 * @return response
	 */
	public static ApiServices getByObjectPass(Map<String, String> parameters) {
		final String pseudoSender = parameters.get("pseudoSender");
		final String passSender = parameters.get("passSender");
		ApiServices theResult = null;

		if ((pseudoSender != null) && (passSender != null)) {
			final VObject theObject = Factories.VOBJECT.findByNameAndMD5Password(pseudoSender, DigestTools.digest(passSender, DigestTools.Algorithm.MD5));

			if (theObject != null) {
				theResult = new ApiServices(theObject.getOwner(), theObject);
			}
		}

		return theResult;
	}

	/**
	 * Check si le login et le pass sont ok afin d'utilisé l'api_widget
	 * 
	 * @param parameters : contient tout les parametres passé dans l'url
	 * @return response
	 */
	private static String INVALIDPASSWORD = "<message>INVALIDPASSWORD</message> <comment>The password or user supplied is invalid.</comment>";

	public static ApiServices getByUserPassMD5Key(Map<String, String> parameters) throws APIException {
		final String pseudoDest = parameters.get("pseudoDest");
		final String passDest = parameters.get("passwordDest");

		if ((pseudoDest != null) && (passDest != null)) {
			final VObject theObject = Factories.VOBJECT.findByName(pseudoDest);
			if (theObject != null) {
				final User theUser = theObject.getOwner();
				if (theUser != null) {
					if (theUser.checkPasswordMD5Sum(passDest)) {
						final User theSender = ApiServices.getSenderByKey(parameters);
						return new ApiServices(theSender, theObject);
					}
				}
			}
		}

		throw new APIException(ApiServices.INVALIDPASSWORD);
	}

	/**
	 * Check si le pseudo est ok
	 * 
	 * @param parameters : contient tout les parametres passé dans l'url
	 * @return response
	 */

	private static final String NOGOODPSEUDO = "<message>NOGOODPSEUDO</message><comment>Your pseudo is not correct!</comment>";

	public static ApiServices getByPseudo(Map<String, String> parameters) throws APIException {
		final String pseudo = parameters.get("pseudoSender");

		if (pseudo != null) {
			final VObject theObject = Factories.VOBJECT.findByName(pseudo);
			if (theObject != null) {
				return new ApiServices(theObject.getOwner(), theObject);
			}
		}

		throw new APIException(ApiServices.NOGOODPSEUDO);
	}

	/**
	 * Check si le pseudo est ok
	 * 
	 * @param parameters : contient tout les parametres passé dans l'url
	 * @return response
	 */
	public static ApiServices getByPseudoKey(Map<String, String> parameters) throws APIException {
		final String pseudo = parameters.get("pseudoDest");

		if (pseudo != null) {
			final VObject theObject = Factories.VOBJECT.findByName(pseudo);

			if (theObject != null) {
				final User theSender = ApiServices.getSenderByKey(parameters);
				return new ApiServices(theSender, theObject);
			}
		}

		throw new APIException(ApiServices.NOGOODPSEUDO);
	}

	/**
	 * Accesseur sur l'expéditeur à partir de la clé.
	 */
	private static final String NOGOODKEY = "<message>NOGOODKEY</message><comment>Your key is not correct !</comment>";

	private static User getSenderByKey(Map<String, String> parameters) throws APIException {
		// Vérification de la clé.
		final String theSenderPseudoKey = parameters.get("key");

		if (theSenderPseudoKey != null) {
			final String theKeyUser = ApiServices.KEY_USERS.get(theSenderPseudoKey);

			if (theKeyUser != null) {
				final User theUser = Factories.USER.findByEmail(theKeyUser);

				if (theUser != null) {
					return theUser;
				}
			}
		}

		throw new APIException(ApiServices.NOGOODKEY);

	}

	/**
	 * Envoi un mp3 perso ou un clin ou une biblio sur son lapin ou sur son
	 * nabcast
	 * 
	 * @param parameters
	 * @return response
	 */
	private static final String MESSAGENOTSENT = "<message>MESSAGENOTSENT</message><comment>Your message id is not correct or is private</comment>";
	private static final String MESSAGE_NABCASTNOTSENT = "<message>NABCASTNOTSENT</message>";
	private static final String NABCASTNOTSENT = ApiServices.MESSAGE_NABCASTNOTSENT + "<comment>Your nabcast id is not correct or is private</comment>";

	public String sendMessageOrNabcastById(Map<String, String> parameters, String inNabcastParam) throws APIException {

		final String rsp;

		final int idmusic;
		try {
			idmusic = Integer.parseInt(parameters.get("idmessage"));
		} catch (final NumberFormatException e) {
			return ApiServices.MESSAGENOTSENT;
		}

		final Music theMusic = Factories.MUSIC.findByIdOrUser(idmusic, this.mSender);

		// bonne id de music perso ou biblio ou clin
		if (theMusic != null) {

			if (inNabcastParam != null) {// envoi sur son nabcast
				final int theNabcastId = ConvertTools.atoi_safe(inNabcastParam);

				if (theNabcastId <= 0) {
					return ApiServices.MESSAGE_NABCASTNOTSENT + "<comment>Your nabcast id is not correct : " + inNabcastParam + "</comment>";
				}

				if ((theMusic.getMusic_type() == Music.TYPE_MP3_USER_LIBRARY) && NabcastServices.isNabcastAuthor(this.mSender.getId().intValue(), theNabcastId)) {// type
					// mp3Perso
					rsp = sendSimpleMsgByNabcast(parameters, theMusic, theNabcastId);
				} else {
					rsp = ApiServices.NABCASTNOTSENT;
				}
			} else { // envoi sur son lapin
				rsp = sendMeSimpleMsgOrListen(parameters, theMusic.getFile(), theMusic.getMusicShortName(), 1);
			}
		} else {
			rsp = ApiServices.MESSAGENOTSENT;
		}

		return rsp;
	}

	/**
	 * Permet de faire un update des positions de l'oreille et renvoi la
	 * nouvelle position si demandé
	 * 
	 * @param parameters
	 * @return response
	 */
	private static final String EARPOSITIONSENT = "<message>EARPOSITIONSENT</message><comment>Your ears command has been sent</comment>";
	private static final String EARPOSITIONNOTSENT = "<message>EARPOSITIONNOTSENT</message><comment>Your ears command could not be sent</comment>";

	public String sendNewPositionEarsInfo(Map<String, String> parameters) {

		if (!ApiServices.SENDMSG_THROTTLE.isOperationAllowed(this.mReceiver.getOwner())) {
			return ApiServices.ABUSE_RESPONSE;
		}

		final StringBuilder rsp = new StringBuilder();
		if (hasARabbit()) { // check si il a un lapin

			int flag = 0; // permet de savoir si ya un réel update à faire
			final int posleft;
			final int posright;
			final String posLeftParam = parameters.get("posleft");
			final String posRightParam = parameters.get("posright");

			if (posLeftParam == null) {
				posleft = this.mReceiver.getObject_left();
				flag += 1;
			} else {
				posleft = ConvertTools.atoi_safe(posLeftParam);
			}

			if (posRightParam == null) {
				posright = this.mReceiver.getObject_right();
				flag += 1;
			} else {
				posright = ConvertTools.atoi_safe(posRightParam);
			}

			// il a eu une demande de changement de position
			if ((flag < 2) && (posleft >= 0) && (posleft <= 16) && (posright >= 0) && (posright <= 16)) {
				EarMng.earNotify(this.mReceiver, posleft, posright);

				rsp.append(ApiServices.EARPOSITIONSENT);
			} else if (flag < 2) { // position incorrecte
				rsp.append(ApiServices.EARPOSITIONNOTSENT);
			}

			// information sur les position d'oreille
			if (parameters.containsKey("ears")) {
				rsp.append("<message>POSITIONEAR</message>");
				rsp.append("<leftposition>");
				rsp.append(posleft);
				rsp.append("</leftposition>");
				rsp.append("<rightposition>");
				rsp.append(posright);
				rsp.append("</rightposition>");
			}

		} else {
			return ApiServices.NORABBIT;
		}

		return rsp.toString();
	}

	/**
	 * Envoi un message TTS sur son lapin ou sur son nabcast
	 * 
	 * @param parameters
	 * @param inNabcast
	 * @return response
	 */
	public String sendMessageOrNabcastByTTS(Map<String, String> parameters, String inNabcast, String inMessageTTS) throws APIException {
		String voix = parameters.get("voice");

		if (voix == null) {
			voix = net.violet.common.StringShop.EMPTY_STRING;
		}

		// check si la commande de tts existe sinon prend default
		final TtsVoice theTts = TtsVoiceImpl.findByCommandOrName(voix, ObjectLangData.getDefaultObjectLanguage(this.mSender.getAnnu().getLangPreferences().getIsoCode()).getId());

		return sendMessageOrNabcastByTTS(parameters, inNabcast, theTts, inMessageTTS);
	}

	private static final String TTSNOTSEND = "<message>TTSNOTSENT</message><comment>Your text could not be sent</comment>";

	public String sendMessageOrNabcastByTTS(Map<String, String> parameters, String inNabcast, TtsVoice inTTSVoice, String inMessageTTS) throws APIException {

		final String rsp;
		final String messageTmp;

		if (net.violet.common.StringShop.EMPTY_STRING.equals(inMessageTTS)) {
			throw new APIException(ApiServices.TTSNOTSEND);
		}

		if (inMessageTTS.length() > 200) {
			messageTmp = inMessageTTS.substring(0, 200) + "...";
		} else {
			messageTmp = inMessageTTS;
		}

		if (inNabcast == null) {
			final Files theFile = TTSServices.getDefaultInstance().postTTS(inMessageTTS, true, true, inTTSVoice);
			if (theFile != null) { // TTS créé
				rsp = sendMeSimpleMsgOrListen(parameters, theFile, messageTmp, 2);
			} else {
				throw new APIException(ApiServices.TTSNOTSEND);
			}
		} else {
			final int nabcastId = ConvertTools.atoi_safe(inNabcast);
			final User theSender;
			if (NabcastServices.isNabcastAuthor(this.mSender.getId().intValue(), nabcastId)) { // monté le ttl à deux mois en cas
				// de
				// nabcast
				// et
				// l
				// 'enregistre
				// dans
				// ces
				// mp3
				// perso
				theSender = this.mSender;
			} else {
				throw new APIException(ApiServices.NABCASTNOTSENT);
			}

			final Music theMusic;

			final Files theFiles = TTSServices.getDefaultInstance().postTTS(inMessageTTS, true, true, inTTSVoice);

			if (theFiles != null) {
				theMusic = Factories.MUSIC.createNewMusic(theFiles, messageTmp, theSender, MusicStyle.CATEGORIE_TTS_PERSO, 0);
				if (theMusic == null) {
					throw new APIException(ApiServices.TTSNOTSEND);
				}
			} else {
				throw new APIException(ApiServices.TTSNOTSEND);
			}
			rsp = sendSimpleMsgByNabcast(parameters, theMusic, nabcastId);

		}

		return rsp;
	}

	/**
	 * Envoi d'un message par upload.
	 * 
	 * @param parameters paramètres de la requête
	 * @param request requête
	 * @return response réponse
	 * @throws APIException
	 */
	private static final String MP3NOTSENT = "<message>MP3NOTSENT</message><comment>Your mp3 could not be sent</comment>";

	public String sendMessageByUpload(Map<String, String> parameters, HttpServletRequest request) throws APIException {

		InputStream theStream = null;
		Files theFiles = null;
		try {
			final TmpFile theFile = FilesManager.TMP_MANAGER.new TmpFile(theStream = request.getInputStream());
			theFiles = FilesManagerFactory.FILE_MANAGER.post(theFile, ContentType.MP3, ContentType.MP3_32, Files.CATEGORIES.BROAD, true, true, MimeType.MIME_TYPES.A_MPEG);
		} catch (final IOException e) {
			ApiServices.LOGGER.fatal(e, e);
		} finally {
			IOUtils.closeQuietly(theStream);
		}

		// on a pu récupérer le fichier
		if (theFiles != null) {
			String theTitle = parameters.get("title");

			if (theTitle == null) {
				theTitle = "upload";
			}

			return sendMeSimpleMsgOrListen(parameters, theFiles, theTitle, 2);
		}
		throw new APIException(ApiServices.MP3NOTSENT);
	}

	/**
	 * Envoi un mp3 perso ou un clin ou une biblio ou tts sur un lapin Sinon
	 * ecoute d'un son
	 * 
	 * @param parameters
	 * @param infoZik (url,name,type :1,2) 1=mp3 ;2=tts
	 * @return response
	 */
	private static final String TTSSENT = "<message>TTSSENT</message><comment>Your text has been sent</comment>";
	private static final String MESSAGESENT = "<message>MESSAGESENT</message><comment>Your message has been sent</comment>";

	private String sendMeSimpleMsgOrListen(Map<String, String> parameters, Files inFile, String inMusicName, int inType) throws APIException {

		if (!ApiServices.SENDMSG_THROTTLE.isOperationAllowed(this.mSender)) {
			return ApiServices.ABUSE_RESPONSE;
		}

		final StringBuilder rsp = new StringBuilder();

		VObject theReceiver = this.mReceiver;
		if (((Integer.parseInt(parameters.get("identifier")) == Api.API_WIDGET) || (Integer.parseInt(parameters.get("identifier")) == Api.API_NABME) || (Integer.parseInt(parameters.get("identifier")) == Api.API_SRV_NABME)) && parameters.containsKey("pseudoDest")) {
			final String pseudodest = parameters.get("pseudoDest"); // pour envoi sur un autre lapin
			theReceiver = Factories.VOBJECT.findByName(pseudodest);
		}

		if (theReceiver == null) {
			throw new APIException(ApiServices.NOGOODPSEUDO);
		}

		rsp.append(checkListen(parameters, inFile));

		if (rsp.length() == 0) { // ne veut pas d'ecoute envoi direct
			final Files[] filesToSend = new Files[1];
			filesToSend[0] = inFile;
			// sends the message without storing it in the inbox
			if (Integer.parseInt(parameters.get("identifier")) == Api.API_GENERAL) {
				MessageServices.sendServiceMessage(filesToSend, theReceiver, inMusicName, null, Constantes.QUEUE_TTL_FIFTEEN_MINUTES, Palette.RANDOM, new MessageSignature(this.mSender), true, JabberMessageFactory.IDLE_MODE);
			} else {
				MessageServices.sendUserMessage(this.mSender, inFile, theReceiver, inMusicName, null, Palette.RANDOM, true);
			}
			// stats pour franck
			Factories.STATS.insert(this.mSender, theReceiver, "API");

			if (inType == 1) {
				rsp.append(ApiServices.MESSAGESENT);
			} else {
				rsp.append(ApiServices.TTSSENT);
			}

		}

		return rsp.toString();
	}

	/**
	 * Envoi un mp3 perso ou un clin ou une biblio sur son nabcast
	 * 
	 * @param parameters
	 * @param infoZik
	 * @return response
	 */
	private static final String NABCASTSENT = "<message>NABCASTSENT</message><comment>Your nabcast has been sent</comment>";

	private String sendSimpleMsgByNabcast(Map<String, String> parameters, Music inMusic, int inNabcast) {

		String title = parameters.get("nabcasttitle");

		if (title == null) {
			title = "Nabcast";
		}

		try {
			final NabcastData nd = NabcastData.find(inNabcast);
			final NabcastValImpl theNabcastValCreate = new NabcastValImpl(title, inMusic.getId().intValue(), nd.getId(), CCalendar.getCurrentTimeInSecond());

			// update count post under one week
			NabcastServices.nbrNabcastInOneWeek((int) nd.getNabcast_id(), nd.getNabcast_categ());

			NabcastServices.sendNabcastToMySubscriber((int) nd.getNabcast_id(), null, inMusic.getId().intValue(), this.mSender.getId().intValue(), title, nd, 0, 0, theNabcastValCreate.getId().intValue());

			return ApiServices.NABCASTSENT;
		} catch (final SQLException e) {
			ApiServices.LOGGER.fatal(e, e);
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * Envoi un message chor sur son lapin
	 * 
	 * @param parameters
	 * @return response
	 */
	private static final String CHORSENT = "<message>CHORSENT</message><comment>Your chor has been sent</comment>";
	private static final String CHORNOTSENT = "<message>CHORNOTSENT</message><comment>Your chor could not be sent (bad chor)</comment>";

	public String sendMessageChor(Map<String, String> parameters) {

		if (!ApiServices.SENDMSG_THROTTLE.isOperationAllowed(getSender())) {
			return ApiServices.ABUSE_RESPONSE;
		}

		final String rsp;

		if (hasARabbit()) { // check si il a un lapin
			String theTitle = parameters.get("chortitle");

			if (theTitle == null) {
				theTitle = "Chor";
			}

			final int theTTLInSecs = Integer.parseInt(parameters.get("ttlive"));
			final Files theFile = ManageMessageServices.createChorByApi(parameters.get("chor"));

			if (theFile != null) { // chor crée
				MessageServices.sendChoregraphy(this.mReceiver, theFile, theTitle, theTTLInSecs);
				rsp = ApiServices.CHORSENT;
			} else {
				rsp = ApiServices.CHORNOTSENT;
			}
		} else {
			rsp = ApiServices.NORABBIT;
		}

		return rsp;
	}

	/**
	 * Envoi liste d'urls sur un lapin V2
	 * 
	 * @param parameters
	 * @return response
	 */
	private static final String WEBRADIOSENT = "<message>WEBRADIOSENT</message><comment>Your webradio has been sent</comment>";
	private static final String NOCORRECTPARAMETERSURL = "<message>NOCORRECTPARAMETERS</message><comment>Your url is too long</comment>";
	private static final String NOCORRECTPARAMETERSURLLIST = "<message>NOCORRECTPARAMETERS</message><comment>Please check urlList parameter !</comment>";
	private static final String NOTV2RABBIT = "<message>NOTV2RABBIT</message><comment>V2 rabbit can use this action</comment>";

	public String sendMeSimpleURL(Map<String, String> parameters) {

		if (!ApiServices.SENDMSG_THROTTLE.isOperationAllowed(getSender())) {
			return ApiServices.ABUSE_RESPONSE;
		}

		final String rsp;
		final String urls = parameters.get("urlList");

		if (urls != null) { // bon paramètres

			if (HARDWARE.V2.is(this.mReceiver)) { // c'est bien un V2
				boolean checkGoodSizeUrl = true;
				final String[] values = urls.split("\\|");

				for (final String single_url : values) {
					if (single_url.length() > 250) {
						checkGoodSizeUrl = false;
						break;
					}
				}

				if (checkGoodSizeUrl) {

					try {
						final int theTTL = Integer.parseInt(parameters.get("ttlive"));
						MessageServices.sendStreamingUrls(values, this.mReceiver, theTTL);
					} catch (final NumberFormatException anException) {
						ApiServices.LOGGER.fatal(anException, anException);
					} catch (final IllegalArgumentException e) {
						ApiServices.LOGGER.fatal(e, e);
					}
					rsp = ApiServices.WEBRADIOSENT;
				} else {
					rsp = ApiServices.NOCORRECTPARAMETERSURL;
				}
			} else {
				rsp = ApiServices.NOTV2RABBIT;
			}
		} else {
			rsp = ApiServices.NOCORRECTPARAMETERSURLLIST;
		}

		return rsp;
	}

	/**
	 * Check si destinataire exist,si tout est correct entre le sender et le
	 * dest
	 * 
	 * @param parameters
	 * @return
	 */
	private static final String BADNABAZTAG = "<message>BADNABAZTAG</message><comment>Nabaztag's name supplied is wrong, check pseudoDest</comment>";
	private static final String FILTREPARENTAL = "<message>FILTREPARENTAL</message><comment>Your message has not been sent</comment>";
	private static final String BLACKLIST = "<message>BLACKLIST</message><comment>Your message has not been sent</comment>";

	public String checkDestinataire(Map<String, String> parameters) {
		final String rsp;
		final String pseudoDestParam = parameters.get("pseudoDest");

		if (pseudoDestParam != null) { // evite requete et check destinataire
			final VObject theDestObject = Factories.VOBJECT.findByName(pseudoDestParam);

			if (theDestObject != null) {
				final User userDest = theDestObject.getOwner();

				final FriendsLists friendsLists = FriendsListsImpl.findByUser(userDest);

				// test filtre parental
				final long filtre = (friendsLists == null) ? 0 : friendsLists.getFriendslists_filter();

				if (filtre == 1) { // filtre parental
					if ((!this.mSender.existFriend(userDest)) && (!(parameters.get("pseudoSender").equalsIgnoreCase(pseudoDestParam)))) {
						return ApiServices.FILTREPARENTAL;
					}
				}

				if (this.mSender.existInBlackliste(userDest) > 0) {
					return ApiServices.BLACKLIST;
				}

				rsp = StringShop.EMPTY_STRING;

			} else {
				rsp = ApiServices.BADNABAZTAG;
			}
		} else {
			rsp = ApiServices.BADNABAZTAG;
		}

		return rsp;
	}

	/**
	 * check si on veux l'ecoute
	 * 
	 * @return response
	 */
	private StringBuilder checkListen(Map<String, String> parameters, Files inFile) {
		final StringBuilder rsp = new StringBuilder();

		if ((parameters.get("action") != null) && (Integer.parseInt(parameters.get("action")) == 1)) // ecoute de message
		{
			final String urlclean = inFile.getPath().replace("broadcast/broad", "broad");
			rsp.append("<message>LINKPREVIEW</message>");
			rsp.append("<comment>");
			rsp.append("<embed id=\"mymovie\" width=\"135\" height=\"135\" type=\"application/x-shockwave-flash\" src=\"http://broad.violet.net/broad/CDLEditor.swf\" name=\"mymovie\" quality=\"high\" flashvars=\"cdll=long,3,none,");
			rsp.append(urlclean);
			rsp.append(ApiServices.END_TAG);
			rsp.append("</comment>");
		}

		return rsp;
	}

	/**
	 * check si il a un lapin
	 * 
	 * @return
	 */
	public boolean hasARabbit() {
		return this.mReceiver != null;
	}

	/**
	 * fait s'endormir ou reveiller le lapin
	 * 
	 * @param status : 1 dors;2 lever
	 */
	private static final String COMMANDSENT = "<message>COMMANDSENT</message> <comment>You rabbit will change status</comment>";

	private String changeStatusRabbit(int status) {
		this.mReceiver.changeStatus(status);
		return ApiServices.COMMANDSENT;
	}

	/**
	 * Accesseur sur l'objet.
	 */
	public VObject getObject() {
		return this.mReceiver;
	}

	/**
	 * Récupération des info sur le compte
	 * 
	 * @param parameters
	 * @return response
	 */
	public String getInfo(Map<String, String> parameters, int inAction) {

		final StringBuilder rsp = new StringBuilder();

		if (inAction == 2) { // list mes amis
			final List<User> friend = this.mSender.getFriendBlackList(0, 1);

			rsp.append("<listfriend nb=\"");
			rsp.append(friend.size());
			rsp.append(ApiServices.END_TAG);

			for (final User od : friend) {
				rsp.append("<friend name=\"");
				rsp.append(od.getAnnu().getAnnu_prenom());
				rsp.append(ApiServices.END_TAG);
			}
		} else if (inAction == 3) { // list derniers messages recu

			if (hasARabbit()) {// check si il a un lapin

				final List<MessageReceivedData> messageReceivedDataList = MessageReceivedData.findAllMessagesReceivedByObject(this.mReceiver, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, true, 0, 50);

				rsp.append("<listreceivedmsg nb=\"");
				rsp.append(messageReceivedDataList.size());
				rsp.append(ApiServices.END_TAG);

				for (final MessageReceivedData md : messageReceivedDataList) {
					final String url = md.getUrl().replace("broadcast/broad", "broad").replace("193.149.120.33/z0", "z0");
					rsp.append("<msg from=\"");
					rsp.append(md.getSender_name());
					rsp.append("\" title=\"");
					rsp.append(md.getTitle());
					rsp.append("\" date=\"");
					rsp.append(md.getDateOfDelivery());
					rsp.append("\" url=\"");
					rsp.append(url);
					rsp.append(ApiServices.END_TAG);

				}
			} else {
				rsp.append(ApiServices.NORABBIT);
			}
		} else if (inAction == 4) { // affiche timezone du user
			final Timezone timeZone = this.mSender.getTimezone();
			rsp.append("<timezone>");
			rsp.append(DicoTools.dico_if(this.mSender.getAnnu().getLangPreferences(), timeZone.getTimezone_name()));
			rsp.append("</timezone>");
		} else if (inAction == 5) {// affiche la signature du user
			final String signature = this.mSender.getSignature(false);

			rsp.append("<signature><embed id=\"mymovie\" width=\"135\" height=\"135\" type=\"application/x-shockwave-flash\" src=\"http://broad.violet.net/broad/CDLEditor.swf\" name=\"mymovie\" quality=\"high\" flashvars=\"cdll=");
			rsp.append(signature);
			rsp.append(ApiServices.END_TAG);
			rsp.append("</signature>");
		} else if (inAction == 6) {// list mes blacklisté
			final List<User> blacklist = this.mSender.getFriendBlackList(1, 1);

			rsp.append("<blacklist nb=\"");
			rsp.append(+blacklist.size());
			rsp.append(ApiServices.END_TAG);

			for (final User od : blacklist) {
				rsp.append("<pseudo name=\"");
				rsp.append(od.getAnnu().getAnnu_prenom());
				rsp.append(ApiServices.END_TAG);
			}
		} else if (inAction == 7) {// veille ou pas

			if (hasARabbit()) {// check si il a un lapin
				if (isSleep()) {
					rsp.append("<rabbitSleep>YES</rabbitSleep>");
				} else {
					rsp.append("<rabbitSleep>NO</rabbitSleep>");
				}
			} else {
				rsp.append(ApiServices.NORABBIT);
			}
		} else if (inAction == 8) {// v1 ou v2

			if (hasARabbit()) {// check si il a un lapin
				final HARDWARE hardware = this.mReceiver.getHardware();
				rsp.append("<rabbitVersion>" + hardware.getType().toUpperCase() + "</rabbitVersion>");
			} else {
				rsp.append(ApiServices.NORABBIT);
			}
		} else if (inAction == 9) {

			rsp.append("<voiceListTTS nb=\"");
			rsp.append(TtsVoice.ALL_VOICES_BY_STR.size());
			rsp.append(ApiServices.END_TAG);

			for (final TtsVoice theEntry : TtsVoice.ALL_VOICES_BY_STR.values()) {
				rsp.append("<voice lang=\"");
				rsp.append(Factories.LANG.find(theEntry.getTtsvoice_lang()).getIsoCode());
				rsp.append("\" command=\"");
				rsp.append(theEntry.getTtsvoice_str());
				rsp.append(ApiServices.END_TAG);
			}
		} else if (inAction == 10) {// récupération du nom du lapin

			if (hasARabbit()) {// check si il a un lapin
				rsp.append("<rabbitName>");
				rsp.append(this.mReceiver.getObject_login());
				rsp.append("</rabbitName>");
			} else {
				rsp.append(ApiServices.NORABBIT);
			}
		} else if (inAction == 11) {// récupération liste des langues choisi pas
			// le user
			final List<Lang> userLang = this.mSender.getLangs();

			rsp.append("<langListUser nb=\"");
			rsp.append(userLang.size());
			rsp.append(ApiServices.END_TAG);

			for (final Lang lang : userLang) {
				rsp.append("<myLang lang=\"");
				rsp.append(lang.getIsoCode());
				rsp.append(ApiServices.END_TAG);
			}
		} else if (inAction == 12) {// lis une url de type
			// broad/001/384/530/000.mp3 dans le player
			// lapin
			final String urlPlayParam = parameters.get("urlPlay");

			if (urlPlayParam != null) {
				rsp.append("<message>LINKPREVIEW</message>");
				rsp.append("<comment>");
				rsp.append("<embed id=\"mymovie\" width=\"135\" height=\"135\" type=\"application/x-shockwave-flash\" src=\"http://broad.violet.net/broad/CDLEditor.swf\" name=\"mymovie\" quality=\"high\" flashvars=\"cdll=long,3,none,");
				rsp.append(urlPlayParam);
				rsp.append(ApiServices.END_TAG);
				rsp.append("</comment>");
			} else {
				rsp.append("<message>NOCORRECTPARAMETERS</message>");
				rsp.append("<comment>Please check urlPlay parameter !</comment>");
			}
		} else if (inAction == 13) {// lapin endort toi

			if (hasARabbit()) {// check si il a un lapin
				rsp.append(changeStatusRabbit(VObject.STATUS_FORCE_VEILLE));
			} else {
				rsp.append(ApiServices.NORABBIT);
			}
		} else if (inAction == 14) {// lapin leve toi

			if (hasARabbit()) {// check si il a un lapin
				rsp.append(changeStatusRabbit(VObject.STATUS_FORCE_ACTIF));
			} else {
				rsp.append(ApiServices.NORABBIT);
			}
		} else if (inAction == 15) {// recupère image du user
			rsp.append("<picture>");
			rsp.append(Constantes.SRV_PATH);
			
			if ((this.mSender.getUser_image() == 1) && (this.mSender.getAnnu() != null) && (this.mSender.getAnnu().getPictureFile() != null) && (this.mSender.getAnnu().getPictureFile().getPath() != null)) {
				rsp.append(this.mSender.getAnnu().getPictureFile().getPath().replaceAll("broadcast/broad/", net.violet.common.StringShop.EMPTY_STRING));
			} else {
				rsp.append("default_S.jpg");
			}

			rsp.append("</picture>");
		}

		return rsp.toString();
	}

	private boolean isSleep() {

		final VObjectData theCurrentObject = VObjectData.getData(this.mReceiver);
		return theCurrentObject.isAsleep();
	}

	private static final String SPACER = StringShop.SPACE;
	private static final String SEMICOLUMN = " : ";

	public static String formatTexte(Map<String, String> parameters) {
		final Lang lang = Factories.LANG.find(ConvertTools.atoi_safe(parameters.get("lang"))); // default US
		final StringBuilder texte = new StringBuilder(DicoTools.dico(lang, "api_srv_nabme/messageFrom"));

		texte.append(ApiServices.SPACER);
		texte.append(parameters.get("from"));
		texte.append(ApiServices.SEMICOLUMN);
		texte.append(parameters.get("msg"));

		return texte.toString();
	}

	public static TtsVoice getVoiceById(Map<String, String> parameters) {
		return Factories.TTSVOICE.findByLang(Factories.LANG.find(ConvertTools.atoi_safe(parameters.get("lang"))));
	}

	/**
	 * @return the mSender
	 */
	public User getSender() {
		return this.mSender;
	}

	/**
	 * @return the mReceiver
	 */
	public VObject getReceiver() {
		return this.mReceiver;
	}

}
