package net.violet.platform.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.violet.common.utils.net.NetTools;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.util.ThrottleManager.ThrottleProfile;
import net.violet.vlisp.services.ApiServices;

import org.apache.log4j.Logger;

public class Api {

	private static final Logger LOGGER = Logger.getLogger(Api.class);

	/**
	 * Identifiant pour l'API générale.
	 */
	public static final int API_GENERAL = 1;

	/**
	 * Identifiant pour l'API widget.
	 */
	public static final int API_WIDGET = 2;

	/**
	 * Identifiant pour l'API stream.
	 */
	public static final int API_STREAM = 3;

	/**
	 * Identifiant pour l'API friend.
	 */
	public static final int API_FRIEND = 4;

	/**
	 * Identifiant pour l'API nabme.
	 */
	public static final int API_NABME = 5;

	/**
	 * Identifiant pour l'API srv_nabme.
	 */
	public static final int API_SRV_NABME = 6;

	private static final ThrottleManager<String> API_THROTTLE = ThrottleManager.getRessourcesThrottle(new ThrottleProfile(50, 1000));
	private static final String ABUSE_RESPONSE = "<message>ABUSESENDING</message> <comment>Too many messages sent in the allowed time, try again later please</comment>";
	private final int identifier;
	private final Map<String, String> parameters = new HashMap<String, String>();

	public static final String MESSAGE_ERROR_SN = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rsp><message>NOGOODSERIAL</message>" + "<comment>Your serial number is not correct !</comment></rsp>";

	/**
	 * permet de savoir sur quel api on travaille
	 * (général,partenaire,stream,etc)
	 * 
	 * @param id : identifiant de l'api
	 */
	public Api(int id) {
		this.identifier = id;
	}

	/**
	 * permet de recupèrer les parametre passé par le user
	 * 
	 * @param request
	 */
	private void setParameter(HttpServletRequest request) {

		try {
			request.setCharacterEncoding("UTF-8");

			final Enumeration t = request.getParameterNames();
			this.parameters.put("identifier", String.valueOf(this.identifier));

			while (t.hasMoreElements()) {
				final String tmp = t.nextElement().toString();
				if (tmp.equals("voicemyTTS")) {
					this.parameters.put("voice", request.getParameter(tmp)); // cas particulier dans api_widget
				} else if (tmp.equals("pseudo")) {
					this.parameters.put("pseudoSender", request.getParameter(tmp)); // cas particulier dans api_friend
				} else if (tmp.equals("tts") || tmp.equals("chortitle") || tmp.equals("nabcasttitle") || tmp.equals("from") || tmp.equals("msg")) {
					this.parameters.put(tmp, URLDecoder.decode(request.getParameter(tmp), "UTF-8")); // clean parameter
				} else {
					this.parameters.put(tmp, request.getParameter(tmp));
				}
			}

		} catch (final UnsupportedEncodingException e) {
			Api.LOGGER.debug("Api.java / setParameter / Decode Exception:" + e);
		}

	}

	/**
	 * Permet de borner le ttl minimun 5 minutes et max param update le nouveau
	 * ttl
	 * 
	 * @param param max ttl
	 * @param force si 1 ne prend plus en compte le ttl max
	 */
	private void setNewTTLive(int param, int force) {
		final int time = param;
		int ttlive = ConvertTools.atoi_safe(this.parameters.get("ttlive"));

		if ((force == 0) && ((ttlive == 0) || (ttlive > time))) {
			ttlive = time;
		}
		if ((force == 1) && (ttlive == 0)) {
			ttlive = param; // on prend le ttl par default si le param ttl n'existe pas
		}
		if (ttlive < 300) {
			ttlive = 300; // temps de vie minimun 5 minutes
		}

		if (this.parameters.containsKey("ttlive")) {
			this.parameters.remove("ttlive");
		}
		this.parameters.put("ttlive", String.valueOf(ttlive));
	}

	private void setNewTTLive(int param) {
		setNewTTLive(param, 0);
	}

	/**
	 * Permet de faire l'action de l'api selon les param récupérer et son
	 * identifiant
	 * 
	 * @param request
	 * @return format xml rsp
	 */
	public String action(HttpServletRequest request) {
		String xmlValue = net.violet.common.StringShop.EMPTY_STRING;
		try {
			final String ipaddress = NetTools.getIP(request);

			if (Api.API_THROTTLE.isOperationAllowed(ipaddress)) // ok to use api,no spam
			{
				setParameter(request);
				switch (this.identifier) {
				case API_GENERAL: // api.jsp
					xmlValue = api();
					Api.LOGGER.info("api.jsp : " + ipaddress + " : sn =" + this.parameters.get("sn"));
					break;

				case API_WIDGET: // api_widget.jsp
					xmlValue = api_widget();
					Api.LOGGER.info("api_widget.jsp : " + ipaddress + " : sn =" + this.parameters.get("sn"));
					break;

				case API_STREAM: // api_stream.jsp
					xmlValue = api_stream();
					Api.LOGGER.info("api_stream.jsp : " + ipaddress + " : sn =" + this.parameters.get("sn"));
					break;

				case API_FRIEND: // api_friend.jsp
					xmlValue = api_friend(ipaddress);
					Api.LOGGER.info("api_friend.jsp : " + ipaddress + " : pseudo =" + this.parameters.get("pseudo"));
					break;

				case API_NABME:
					xmlValue = api_nabme(ipaddress, request);
					Api.LOGGER.info("api_nabme.jsp : " + ipaddress + " : pseudo =" + this.parameters.get("pseudo"));
					break;

				case API_SRV_NABME:
					xmlValue = api_srv_nabme();
					Api.LOGGER.info("api_srv_nabme.jsp : " + ipaddress + " : pseudo =" + this.parameters.get("pseudoSender"));
					break;

				default:
					break;
				}
			} else { // logguer au futur les pseudo
				xmlValue = Api.ABUSE_RESPONSE;
				Api.LOGGER.warn("rejectapi : " + ipaddress + " : sn =" + request.getParameter("sn"));
			}
		} catch (final APIException anException) {
			xmlValue = anException.getMessage();
		} catch (final NumberFormatException anException) {
			xmlValue = "<error>IllegalArgument</error> <comment>" + anException.getMessage() + "</comment>";
		} catch (final IllegalArgumentException anException) {
			xmlValue = "<error>IllegalArgument</error> <comment>" + anException.getMessage() + "</comment>";
		}

		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rsp>" + xmlValue + "</rsp>";
	}

	private String fct_api(ApiServices inApi) throws APIException {

		String rsp = net.violet.common.StringShop.EMPTY_STRING;
		final String nabcastParam = this.parameters.get("nabcast");
		final int action = ConvertTools.atoi_safe(this.parameters.get("action"));
		final String theText = this.parameters.get("tts");

		// envoi de message MP3 ou midi sur son lapin ou sur son nabcast /ou
		// ecoute du message
		if (this.parameters.containsKey("idmessage")) {
			rsp += inApi.sendMessageOrNabcastById(this.parameters, nabcastParam);
		}
		if (this.parameters.containsKey("ears") || this.parameters.containsKey("posright") || this.parameters.containsKey("posleft")) {// envoi
			// de
			// nouvelle
			// position
			// d
			// 'oreille
			// et
			// /
			// ou
			// info
			// sur
			// la
			// position
			// des
			// oreilles
			rsp += inApi.sendNewPositionEarsInfo(this.parameters);
		}
		if (theText != null) {
			// envoi de message TTS sur son lapin ou sur son nabcast / ou ecoute
			// du tts
			if ((this.parameters.get("action") != null) && (Integer.parseInt(this.parameters.get("action")) == 1)) {
				// si ecoute baisse le temps de vie
				setNewTTLive(Constantes.TIMEOUT_ONEHOUR);
			}
			rsp += inApi.sendMessageOrNabcastByTTS(this.parameters, nabcastParam, theText);
		}

		if (this.parameters.containsKey("chor")) { // envoi de message chorégraphique sur son lapin
			rsp += inApi.sendMessageChor(this.parameters);
		}
		if (action > 1) { // récupération d'info
			rsp += inApi.getInfo(this.parameters, action);
		}

		return rsp;
	}

	/**
	 *Permet d'envoyer un message sur un lapin à partir de son # de série mis
	 * dans parameters rmq : les différents params possibles : token, sn,
	 * idmessage, tts (voice,speed,pitch), ttlive, posright, posleft, ears,
	 * chor(chortitle), nabcast (nabcasttitle), action. // * TODO : vérifier si
	 * toutes ces options sont encore d'actualité
	 * 
	 * @return réponse
	 * @throws SQLException
	 */
	private String api() throws APIException {// appel de l'api
		// par la page
		// api.jsp
		final ApiServices theApi = ApiServices.getBySnToken(this.parameters);
		setNewTTLive(Constantes.TIMEOUT_ONEDAY);// mise a jour du ttl si incorrect et temps de vie max 1 day
		return fct_api(theApi);
	}

	/**
	 * permet d'envoyer sur son propre lapin ou un autre lapin des messages
	 * tts,mp3 permet de récupérer des infos de son lapin permet l'envoi de
	 * nabcast MessageImpl crédité param obligatoire pseudo et password rmq :
	 * les différents params possibles : pseudoSender, passSender,pseudoDest,
	 * idmessage, tts (voice,speed,pitch), ttlive, posright, posleft, ears,
	 * chor(chortitle), nabcast (nabcasttitle), action
	 * 
	 * @return réponse
	 * @throws SQLException
	 * @throws APIException
	 */
	private String api_widget() throws APIException {// appel de
		// l'api par
		// la page
		// api_widget
		// .jsp
		final StringBuilder rsp = new StringBuilder();
		final int actionId = ConvertTools.atoi_safe(this.parameters.get("action"));
		final String nabcastParam = this.parameters.get("nabcast");
		final boolean nabcastParamExist = nabcastParam != null;
		final String theText = this.parameters.get("tts");

		final ApiServices theApi = ApiServices.getByObjectPass(this.parameters);
		if (theApi == null) {
			rsp.append("<message>NOGOODUSER</message><comment>your pseudo or your pass is incorrect</comment>");
		} else {
			// envoi de message MP3 ou midi sur son lapin ou sur son nabcast;
			// ecoute
			if (this.parameters.containsKey("idmessage")) {
				if ((actionId == 1) || nabcastParamExist) {
					rsp.append(theApi.sendMessageOrNabcastById(this.parameters, nabcastParam));
				} else {
					if (this.parameters.get("pseudoSender") == null) {
						return "<message>Invalid parameter, please specify the parameter : pseudoSender</message>";
					}
					rsp.append(theApi.checkDestinataire(this.parameters)); // check destinataire et droit
					if (rsp.length() == 0) {
						rsp.append(theApi.sendMessageOrNabcastById(this.parameters, nabcastParam));
					}
				}
			} else if (theText != null) { // envoi de message TTS sur un lapin ou/et ecoute
				if ((actionId == 1) || nabcastParamExist) {
					setNewTTLive(Constantes.TIMEOUT_ONEHOUR);
					rsp.append(theApi.sendMessageOrNabcastByTTS(this.parameters, nabcastParam, theText));
				} else {
					if (this.parameters.get("pseudoSender") == null) {
						return "<message>Invalid parameter, please specify the parameter : pseudoSender</message>";
					}
					rsp.append(theApi.checkDestinataire(this.parameters)); // check destinataire et droit

					if (rsp.length() == 0) {
						rsp.append(theApi.sendMessageOrNabcastByTTS(this.parameters, nabcastParam, theText));
					}
				}
			} else if (this.parameters.containsKey("ears") || this.parameters.containsKey("posright") || this.parameters.containsKey("posleft")) { // envoi de nouvelle position d 'oreille et /
				// ou
				// info
				// sur
				// la
				// position
				// des
				// oreilles
				rsp.append(theApi.sendNewPositionEarsInfo(this.parameters));
			}

			// récupération d'info
			if (actionId > 1) {
				rsp.append(theApi.getInfo(this.parameters, actionId));
			}
		}

		return rsp.toString();
	}

	/**
	 * permet d'envoyer sur son lapin V2 des url MessageImpl non crédité param
	 * obligatoire token et serial rmq : les différents params possibles :
	 * token, sn, idmessage, urlList, ttlive
	 * 
	 * @return réponse
	 */
	private String api_stream() throws APIException {// appel de l'api par la
		// page api_stream.jsp
		final ApiServices theApi = ApiServices.getBySnToken(this.parameters);
		setNewTTLive(600);
		return theApi.sendMeSimpleURL(this.parameters); // envoi liste url sur le lapin V2
	}

	/**
	 * permet d'envoyer sur son propre lapin des messages tts,chor,mp3 permet de
	 * récupérer des infos de celui-ci permet l'envoi de nabcast MessageImpl non
	 * crédité param obligatoire nabname + filtre sur l'ip du serveur rmq : les
	 * différents params possibles : pseudoSender, idmessage, tts
	 * (voice,speed,pitch), ttlive, posright, posleft, ears, chor(chortitle),
	 * nabcast (nabcasttitle), action
	 * 
	 * @return réponse
	 * @throws SQLException
	 */
	private String api_friend(String ipaddress) throws APIException {// appel
		// de
		// l
		// 'api_friend
		// par
		// la
		// page
		// api_friend
		// .
		// jsp
		final String rsp;

		if (ApiServices.isGoodIP(ipaddress)) // ok to use api_friend,good IP
		{
			setNewTTLive(Constantes.TIMEOUT_ONEDAY);// mise a jour du ttl si incorrect et temps de vie max 1 day
			final ApiServices theApi = ApiServices.getByPseudo(this.parameters);
			rsp = fct_api(theApi);
		} else {
			rsp = "<message>BADACCESS</message><comment>You don't have permission to access /vl/FR/api_friend.jsp on this server.</comment>";
		}

		return rsp;
	}

	/**
	 * Permet d'envoyer un message sur un lapin juste avec son nom Il y a un
	 * filtre sur l'ip et sur l'envoyeur (key) * rmq : les différents params
	 * possibles : pseudoDest, passwordDest, key, ttlive, tts
	 * (voice,speed,pitch)
	 * 
	 * @param ipaddress
	 * @return
	 * @throws SQLException
	 */
	private String api_nabme(String ipaddress, HttpServletRequest request) throws APIException {// appel
		// de
		// l
		// 'api_nabme
		// par
		// la
		// page
		// api_nabme
		// .
		// jsp
		String rsp = net.violet.common.StringShop.EMPTY_STRING;

		if (this.parameters.get("pseudoSender") == null) {
			return "<message>Invalid parameter, please specify the parameter : pseudoSender</message>";
		}

		ApiServices theApi = null;
		// Si on a le mot de passe (md5)
		if (this.parameters.containsKey("passwordDest")) {
			theApi = ApiServices.getByUserPassMD5Key(this.parameters);
		} else {
			ApiServices.checkGoodIPNabMe(ipaddress);
			// L'IP est correcte.
			theApi = ApiServices.getByPseudoKey(this.parameters);
		}
		final int actionId = ConvertTools.atoi_safe(this.parameters.get("action"));
		rsp += theApi.checkDestinataire(this.parameters); // check juste destinataire
		if (rsp.length() == 0) {
			// envoi unique de message TTS sur un lapin ( pas de nabcast)
			final String theText = this.parameters.get("tts");
			if ((theText != null) && !this.parameters.containsKey("nabcast")) {
				rsp += theApi.sendMessageOrNabcastByTTS(this.parameters, null, theText);
			} else if (this.parameters.containsKey("title") && request.getMethod().equals("POST")) {
				rsp += theApi.sendMessageByUpload(this.parameters, request);
			}

			if (actionId > 1) { // récupération d'info
				rsp += theApi.getInfo(this.parameters, actionId);
			}

			if (rsp.length() == 0) {
				rsp += "<message>Invalid parameters</message>";
			}
		}

		return rsp;
	}

	/**
	 * permet d'envoyer sur un lapin grace a son sn et token et déterminé
	 * l'envoyeur l'envoyeur est filtré de notre coté rmq : les différents
	 * params possibles : sn, token, key, ttlive, tts (voice,speed,pitch)
	 * 
	 * @return
	 * @throws SQLException
	 */
	private String api_srv_nabme() throws APIException {// appel
		// de
		// l'api
		// par
		// la
		// page
		// api_srv_nabme
		// .jsp
		final ApiServices theApi = ApiServices.getBySnTokenKey(this.parameters);

		final String TheTextFormated = ApiServices.formatTexte(this.parameters); // ex : MessageImpl from < from > :
		// <
		// msg
		// >
		final TtsVoice theVoice = ApiServices.getVoiceById(this.parameters); // set voice tts envoi unique de message TTS sur un lapin ( pas de nabcast)
		if (this.parameters.containsKey("msg") && !this.parameters.containsKey("nabcast")) {
			return theApi.sendMessageOrNabcastByTTS(this.parameters, null, theVoice, TheTextFormated);
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}
}
