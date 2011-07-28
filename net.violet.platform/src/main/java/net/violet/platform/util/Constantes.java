package net.violet.platform.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.violet.common.utils.PropertiesTools;

public class Constantes extends net.violet.common.Constantes {

	static {
		net.violet.common.Constantes.PROPERTIES.load("constante.properties");
		if (System.getProperty("hostname") != null) {
			net.violet.common.Constantes.PROPERTIES.load("constante-" + System.getProperty("hostname") + ".properties", false);
			net.violet.common.Constantes.PROPERTIES.load("constante-" + System.getProperty("hostname") + net.violet.common.StringShop.UNDERSCORE + System.getProperty("port") + ".properties", false);
		}
		if (System.getProperty("crawler") != null) {
			net.violet.common.Constantes.PROPERTIES.load("constante-" + System.getProperty("crawler") + ".properties", false);
		}

	}

	// http://192.168.1.11/OS, http://my-pp.nabaztag.com/OS, http://api.violet.net/OS etc.
	public static final String OS_SERVLET_ROOT = net.violet.common.Constantes.PROPERTIES.getProperties("OS_SERVLET_ROOT");

	// http://192.168.1.11/vl, http://my.nabaztag.com/vl, etc.
	public static final String SRV_PATH = net.violet.common.Constantes.PROPERTIES.getProperties("SRV_PATH");

	// 192.168.1.11, .nabaztag.com
	public static final String DOMAIN = net.violet.common.Constantes.PROPERTIES.getProperties("DOMAIN");

	// 192.168.1.11, 192.168.1.95, r.nabaztag.com
	public static final String STREAM_SERVER = net.violet.common.Constantes.PROPERTIES.getProperties("STREAM_SERVER");

	public static final String RED5_STREAM_SERVER = net.violet.common.Constantes.PROPERTIES.getProperties("RED5_STREAM_SERVER");

	public static final String BROAD = net.violet.common.Constantes.PROPERTIES.getProperties("BROAD");

	// 192.168.1.11, 192.168.1.160 , 193.149.120.33
	public static final String ZONE_PING = net.violet.common.Constantes.PROPERTIES.getProperties("ZONE_PING");

	// @nabaztag.net ou @nabaztag.com
	public static final String POPMAIL = net.violet.common.Constantes.PROPERTIES.getProperties("POPMAIL");

	public static final String IMG_PATH = Constantes.SRV_PATH;

	// 192.168.1.11 ,193.149.120.38
	public static final String IP_ADMIN = net.violet.common.Constantes.PROPERTIES.getProperties("IP_ADMIN");

	// PROD =
	// http://10.11.1.138:8080/tasr/process?file=//10.11.1.137/telisma/sounds/
	// PP =
	// http://192.168.1.12:8080/tasr/process?file=//192.168.1.94/telisma/sounds/
	// DEV =
	// http://192.168.1.12:8080/tasr/process?file=//192.168.1.11/share/ASR/
	// sounds/
	public static final String RECO_PATH = net.violet.common.Constantes.PROPERTIES.getProperties("RECO_PATH");

	// not send email in Pre-prod and dev (except at @violet.net)
	public static final int FILTER_EMAIL = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("FILTER_EMAIL");

	// Permet de recevoir de la prod des mails d'alertes lors de la connection
	// en xmpp
	public static final int SEND_MAIL_ALERT_XMPP = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("SEND_MAIL_ALERT_XMPP");

	// Constantes BDD de test
	public static final String URL_DBTESTCORE = net.violet.common.Constantes.PROPERTIES.getProperties("URL_DBTESTCORE");

	// Chemins.
	// Projet OS (/usr/local/tomcat/violet/OS/).
	public static final String OS_PATH = net.violet.common.Constantes.PROPERTIES.getProperties("OS_PATH");
	public static final String NAS_PATH = net.violet.common.Constantes.PROPERTIES.getProperties("NAS_PATH");
	// rsc. (/usr/local/violet/rsc/).
	public static final String RSC_PATH = Constantes.NAS_PATH + net.violet.common.Constantes.PROPERTIES.getProperties("RSC_PATH");
	// Tools. (/usr/local/violet/tools/).
	public static final String TOOLS_PATH = Constantes.NAS_PATH + net.violet.common.Constantes.PROPERTIES.getProperties("TOOLS_PATH");
	// TTS. (/usr/local/violet/TTS/).
	public static final String TTS_PATH = Constantes.NAS_PATH + net.violet.common.Constantes.PROPERTIES.getProperties("TTS_PATH");
	// tmp. (/usr/local/violet/tmp/).
	public static final String TMP_PATH = Constantes.NAS_PATH + net.violet.common.Constantes.PROPERTIES.getProperties("TMP_PATH");
	// tmp. (/usr/local/tomcat/tmp/).
	public static final String LOCAL_TMP_PATH = net.violet.common.Constantes.PROPERTIES.getProperties("LOCAL_TMP_PATH");
	// telisma. (/usr/local/violet/telisma/).
	public static final String TELISMA_PATH = Constantes.NAS_PATH + net.violet.common.Constantes.PROPERTIES.getProperties("TELISMA_PATH");
	// asm. (/usr/local/violet/asm/).
	public static final String ASM_PATH = Constantes.NAS_PATH + net.violet.common.Constantes.PROPERTIES.getProperties("ASM_PATH");
	// audiooki. (/usr/local/violet/audiooki/).
	public static final String AUDIOOKI_PATH = Constantes.NAS_PATH + net.violet.common.Constantes.PROPERTIES.getProperties("AUDIOOKI_PATH");
	// mail. (/usr/local/tomcat/violet/OS/tools/mail/).
	public static final String MAIL_PATH = Constantes.OS_PATH + net.violet.common.Constantes.PROPERTIES.getProperties("MAIL_PATH");
	// (/usr/local/violet/).

	public static final String MAIL_HOST = net.violet.common.Constantes.PROPERTIES.getProperties("MAIL_HOST");

	// dans le futur chaque serveur aura une priorité . Pour l'instant c'est 0
	// pour tous
	public static final int PRIORITY_ACCESS_TTS = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("PRIORITY_ACCESS_TTS");

	// dans le futur chaque serveur aura un nombre de TTS concurrant différents
	// . Pour l'instant c'est 4 pour tous
	public static final int NB_CONCURRENT_TTS = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("NB_CONCURRENT_TTS");

	// Liste des serveurs pour les clients XMPP

	public static List<String> getJabberServerList() {

		// server1;server2;server3
		final String listServersString = net.violet.common.Constantes.PROPERTIES.getProperties("XMPP_SERVER");
		return Arrays.asList(listServersString.split(";"));
	}

	public static final List<String> XMPP_SERVER_LIST = Constantes.getJabberServerList(); // PROPERTIES . getProperties ( "XMPP_SERVER" ) ; xmpp.nabaztag.com
	public static final String XMPP_NABAZTAG_DOMAIN = net.violet.common.Constantes.PROPERTIES.getProperties("XMPP_NABAZTAG_DOMAIN");

	// xmpp.mirror.violet.net
	public static final String XMPP_MIRROR_DOMAIN = net.violet.common.Constantes.PROPERTIES.getProperties("XMPP_MIRROR_DOMAIN");

	// Mot de passe de la plateforme sur le serveur jabber.
	// (violet en dev).
	public static final String XMPP_PLATFORM_PASSWORD = net.violet.common.Constantes.PROPERTIES.getProperties("XMPP_PLATFORM_PASSWORD");

	// Point d'entrée de l'API publique : (devrait être api.violet.net à terme)
	public static final String API_SERVICE = net.violet.common.Constantes.PROPERTIES.getProperties("API_SERVICE");

	// Adresse IP du serveur de TTS.
	public static final String TTS_SERVER = net.violet.common.Constantes.PROPERTIES.getProperties("TTS_SERVER");

	// Temps d'attente entre les nettoyages du Cache
	public static final int TIME_TO_CLEAN = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("TIME_TO_CLEAN");

	public static final int MAX_CACHE_ABSTRACT_RECORD_ENTRIES = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("MAX_CACHE_ABSTRACT_RECORD_ENTRIES");
	public static final int MAX_CACHE_DB_COLLECTION_ENTRIES = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("MAX_CACHE_DB_COLLECTION_ENTRIES");

	// Adresse IP du serveur JABBERRPC.
	public static final String JABBERRPC_SERVER = net.violet.common.Constantes.PROPERTIES.getProperties("JABBERRPC_SERVER");

	// Url du serveur red5
	public static final String RED5_URL_SERVER = net.violet.common.Constantes.PROPERTIES.getProperties("RED5_URL_SERVER");

	/*CONNECTEURS*/
	public static final String USER_DBEJABBERD = "ejabberdjava";
	public static final String PASSWORD_DBEJABBERD = "1b8d750b411a2a51640aef79ac6beeaa";

	public static final String USER_STATS = "dev";
	public static final String PASSWORD_STATS = "123";

	public static final String USER_DB = "dev";
	public static final String PASSWORD_DB = "123";
	public static final String CLASS_DB = "com.mysql.jdbc.Driver";

	public static final String USER_DB_W = "dev";
	public static final String PASSWORD_DB_W = "123";

	public static final String USER_DB_R = "dev";
	public static final String PASSWORD_DB_R = "123";

	public static final String CONTEXT_DB_R = "jdbc/bdCoreRead";
	public static final String CONTEXT_DB_W = "jdbc/bdCoreWrite";

	// URLs en local pour les test junit.
	public static final String URL_DB_CORE_LOCAL = "jdbc:mysql://127.0.0.1/bdCore?useUnicode=true&characterEncoding=UTF-8";

	/* FIN DES CONNECTEURS */

	public static final int SERVICE_ID = 21;
	public static final int SERVICEID_FACEBOOK = 93162;

	public static final int EVACK_UNREAD = 0;
	public static final int EVACK_REREAD = 1;
	public static final int EVACK_READ = 2;
	public static final int EVACK_ACKED = 3;
	public static final int EVACK_WAIT = 4;

	// timeout pour tester si un lapin est ok pour etre servie
	public static final int TIMEOUT_PING_IN_SEC = 1800;

	public static final int EVENT_PARAM_FILE = 1;

	public static final String STAT_WEB = "WEB";

	// Temps inter-crawler (service mail)
	public static final int HOWLONG = 1200000;

	/* FIN DES SERVICES */

	/* CONFIG */

	public static final String GEOIPDAT = Constantes.TOOLS_PATH + "GeoIP.dat";
	public static final String RSCMNG_MP3SPLT = Constantes.TOOLS_PATH + "mp3splt";
	public static final String RSCMNG_LAME = Constantes.OS_PATH + "scripts/lame.sh";
	public static final String JOIN_MP3 = Constantes.OS_PATH + "scripts/joinmp3.sh";
	public static final String RSCMNG_FAAD = Constantes.OS_PATH + "scripts/faad_v2.sh";
	public static final String RSCMNG_CLIPPING = Constantes.OS_PATH + "scripts/clipping_wav_v2.sh";
	public static final String RSCMNG_WAV2ADP = Constantes.OS_PATH + "scripts/wav2adp.sh";
	public static final String BYTECODE_PATH = Constantes.TOOLS_PATH + "bin/";
	public static final String URI_BIN = Constantes.TOOLS_PATH + "bin";
	public static final String COM_CLOCK_PATH = Constantes.TMP_PATH + "com_clock/";
	public static final String PHOTOPATH = Constantes.NAS_PATH + "photo/";
	public static final String CDLLPATH = Constantes.TOOLS_PATH + "files/cdll";
	public static final String TELISMA_TMP_PATH = Constantes.TELISMA_PATH + "tmp/";
	public static final String TELISMA_SOUND_PATH = Constantes.TELISMA_PATH + "sounds/";
	public static final String STREAM_FILE_PATH = Constantes.RSC_PATH + "streaming/";
	public static final String JS_COMPILED_CLASSES_PATH = Constantes.TMP_PATH + "applications/js/";
	public static final String JSP_SECURESTREAM_ACCESS = Constantes.STREAM_SERVER + "/vl/secureStreaming.jsp";
	public static final String SNDFILE_PATH = Constantes.OS_PATH + "scripts/sndfile-convert.sh";
	public static final String FFMPEG = Constantes.OS_PATH + "scripts/ffmpeg.sh";
	// public static final String RED5_FILE_PATH = RSC_PATH +
	// Files.CATEGORIES.VOCAL_RECORDER.getPath();

	// hdfs://192.168.1.11:54310
	public static final String HADOOP_URI = net.violet.common.Constantes.PROPERTIES.getProperties("HADOOP_URI");
	/* FIN CONFIG */

	/* TTL DE MESSAGE ET TIMEOUT */

	@Deprecated
	public static final int TIMEOUT_ONEDAY = 86400;
	@Deprecated
	public static final int TIMEOUT_ONEHOUR = 3600;

	public static final int TIMEOUT_ONEMINUTE = 60000;

	public static final int QUEUE_TTL_FIVE_MINUTES = 300;
	public static final int QUEUE_TTL_FIFTEEN_MINUTES = 1500;
	public static final int QUEUE_TTL_ONE_DAY = 86400;
	public static final int QUEUE_TTL_ONE_WEEK = 7 * 86400;

	public static final int QUEUE_TTL_STATUS = 40;
	public static final int QUEUE_TTL_CLOCK = Constantes.QUEUE_TTL_FIVE_MINUTES;
	public static final int QUEUE_TTL_SOURCES = Constantes.QUEUE_TTL_FIVE_MINUTES;
	public static final int QUEUE_TTL_SERVICE = Constantes.QUEUE_TTL_FIFTEEN_MINUTES;
	public static final int QUEUE_TTL_DEFAULT = Constantes.QUEUE_TTL_ONE_WEEK;
	public static final int QUEUE_TTL_USER_MESSAGE = Constantes.QUEUE_TTL_ONE_WEEK;

	public static final long ONE_WEEK_IN_MS = 7 * 86400000;

	public static final long TWO_HOURS_IN_S = 7200;
	public static final int ONE_DAY_IN_S = 86400;
	public static final int ONE_WEEK_IN_S = 86400 * 7;
	public static final int FIFTEEN_MINUTES_IN_S = 900;

	//don't change this value, it's MemCache max value
	public static final long ONE_MONTH_IN_S = (86400 * 30) - 1;

	public static final int ONE_YEAR_IN_S = 86400 * 365;

	/* MAIL POUR SUPPORT */

	public static final String SUPPORT_MAILTO = "franck@violet.net";
	public static final String SUPPORT_MAILFROM = "gerard@violet.net";
	public static final String SUPPORT_SUBJECT = "Erreur plateforme";

	public static final String IMG_RSS = "rss.gif";
	public static final String IMG_PODCAST = "podcast.gif";

	// Identifiant de la plateforme sur le serveur jabber.
	public static final String XMPP_PLATFORM_ID = "net.violet.platform";
	public static final String XMPP_XMLRPC_ID = "net.violet.xmlrpc";

	// Adresse de la plateforme sur le serveur jabber.
	public static final String XMPP_PLATFORM_ADDRESS = Constantes.XMPP_PLATFORM_ID + "@" + Constantes.XMPP_NABAZTAG_DOMAIN;

	// Port du serveur XMPP.
	public static final int XMPP_SERVER_PORT = 5222;

	// Fichier de configuration jgroups.
	public static final String JGROUPS_CHANNEL_CONFIGURATION = "/jgroups-config.xml";

	// Liste des composants

	public static Map<String, Triplet<List<Pair<String, Integer>>, String, String>> getJabberComponentsConfigList() {

		final Map<String, Triplet<List<Pair<String, Integer>>, String, String>> components = new HashMap<String, Triplet<List<Pair<String, Integer>>, String, String>>();

		//{[componenthostName:componentPort],[componenthostName:componentPort]};
		// componentName;componentPassword
		final PropertiesTools pTools = new PropertiesTools();
		pTools.load("jabber_components_list.properties");
		for (final Entry<Object, Object> anEntry : pTools.getAllProperties().entrySet()) {
			final String[] theValues = ((String) anEntry.getValue()).split(";");
			if (theValues.length == 3) {
				final List<Pair<String, Integer>> pairs = new ArrayList<Pair<String, Integer>>();
				final String[] hosts = theValues[0].split(net.violet.common.StringShop.COMMA);
				for (String host : hosts) {
					host = host.replaceAll("(\\[|\\]|\\{|\\}|,)", net.violet.common.StringShop.EMPTY_STRING);
					final String[] hostInfos = host.split(":");
					if (hostInfos.length == 2) {
						pairs.add(new Pair<String, Integer>(hostInfos[0], Integer.parseInt(hostInfos[1])));
					}
				}
				if (pairs.size() > 0) {
					components.put((String) anEntry.getKey(), new Triplet<List<Pair<String, Integer>>, String, String>(pairs, theValues[1], theValues[2]));
				}
			}
		}

		return components;
	}

	public static final Map<String, Triplet<List<Pair<String, Integer>>, String, String>> XMPP_COMPONENTS_CONFIG_LIST = Constantes.getJabberComponentsConfigList();

	public static final String XMPP_API_COMPONENT = net.violet.common.Constantes.PROPERTIES.getProperties("XMPP_API_COMPONENT");

	public static final String XMPP_APPLET_COMPONENT = net.violet.common.Constantes.PROPERTIES.getProperties("XMPP_APPLET_COMPONENT");

	public static final String XMPP_OBJECTS_COMPONENT = net.violet.common.Constantes.PROPERTIES.getProperties("XMPP_OBJECTS_COMPONENT");

	public static final String XMPP_PLATFORM_COMPONENT = Constantes.PROPERTIES.getProperties("XMPP_PLATFORM_COMPONENT");

	public static final String XMPP_RESOURCES_COMPONENT = Constantes.PROPERTIES.getProperties("XMPP_RESOURCES_COMPONENT");

	// / Constantes Crawlers ///
	public static final long RUN_CYCLE = 30000;
	public static final int SEND_CYCLE = 1200000;
	public static final long TIMEOUT_SLEEP_CYCLE = 10000;
	public static final long PID_CYCLE = 240000;
	public static final int WAIT_CYCLE = 300;
	public static final int WAIT_2_PROCESS_CYCLE = 180000;
	public static final int MAX_NB_NEWS_READ = 30;
	public static final int MAX_PROCESSING_ITEMS_DOWNLOAD = 3;
	public static final int MAX_PROCESSING_ITEMS_TTS = 2;
	public static final int MAX_PROCESSING_CHANNELS = 3;
	public static final int MAX_DOWNLOADING_THREADS = 5;

	public static final int NB_COMPOSANT_API_THREADS = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("NB_COMPOSANT_API_THREADS");
	public static final int NB_COMPOSANT_APPLET_THREADS = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("NB_COMPOSANT_APPLET_THREADS");
	public static final int NB_COMPOSANT_OBJECT_THREADS = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("NB_COMPOSANT_OBJECT_THREADS");
	public static final int NB_COMPOSANT_PLATFORM_THREADS = net.violet.common.Constantes.PROPERTIES.<Integer> getProperties("NB_COMPOSANT_PLATFORM_THREADS");

}
