package net.violet.platform.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.application.factories.AbstractMessageFactory;
import net.violet.platform.message.application.factories.WebRadioMessageFactory;
import net.violet.platform.message.application.factories.AbstractMessageFactory.MESSAGE_TYPE;
import net.violet.vlisp.services.ManageMessageServices.WEATHER_OPTIONS;

import org.apache.log4j.Logger;

public abstract class AbstractRecoService {

	public static final String RADIO = "radio";
	public static final String WEATHER = "weather";
	public static final String MARKET = "market";
	public static final String TRAFIC = "trafic";
	public static final String SMOG = "smog";
	public static final String BAD_RECO = "bad";

	private static final Logger LOGGER = Logger.getLogger(AbstractRecoService.class);

	public static Map<String, AbstractRecoService> RECO_SERVICES;

	static {
		final Map<String, AbstractRecoService> theMap = new HashMap<String, AbstractRecoService>();

		theMap.put(AbstractRecoService.RADIO, new WebRadio());
		theMap.put(AbstractRecoService.WEATHER, new Weather());
		theMap.put(AbstractRecoService.MARKET, new Trade());
		theMap.put(AbstractRecoService.TRAFIC, new Traffic());
		theMap.put(AbstractRecoService.SMOG, new Air());
		theMap.put(AbstractRecoService.BAD_RECO, new BadReco());
		// theMap.put("sleep", new Sleep());
		// theMap.put("wake", new Wake());

		AbstractRecoService.RECO_SERVICES = Collections.unmodifiableMap(theMap);
	}

	private static Map<Lang, Files> getDefaultResponsesByServices(SERVICES inService, String inOption) {
		final Map<Lang, Files> theMap = new HashMap<Lang, Files>();

		for (final ObjectLangData aLangData : ObjectLangData.getAllObjectLanguages()) {
			final Lang aLang = aLangData.getReference();
			theMap.put(aLang, Factories.CONFIG_FILES.findAllByServiceAndLang(inService, aLang).get(inOption).get(0).getFiles());
		}

		return Collections.unmodifiableMap(theMap);

	}

	public static class Weather extends AbstractRecoService {

		private static final Map<Lang, Files> DEFAULT_RESPONSE = AbstractRecoService.getDefaultResponsesByServices(SERVICES.WEATHER, WEATHER_OPTIONS.METEO.toString());
		private static final Application APPLICATION = Application.NativeApplication.WEATHER.getApplication();

		@Override
		protected Files getDefaultResponse(Lang inLang) {
			return Weather.DEFAULT_RESPONSE.get(inLang);
		}

		@Override
		protected String getLabel() {
			return Weather.APPLICATION.getName();
		}

		@Override
		protected Application getApplication() {
			return Weather.APPLICATION;
		}
	}

	public static class WebRadio extends AbstractRecoService {

		@Override
		public void process(VObject inObject, String inValue) {
			final String music_url;
			if (inValue.contains("radio_")) {// radio précisé par le user
				music_url = RadioList.ALL_RADIOS.get(inValue);
			} else { // radio aléatoire
				music_url = RadioList.getRandomWebradio(inObject.getPreferences().getLangPreferences().getId());
			}

			WebRadioMessageFactory.sendMessage(music_url, inObject, null);
		}

		@Override
		protected Files getDefaultResponse(Lang inLang) {
			return null;
		}

		@Override
		protected String getLabel() {
			return "webradio";
		}

		@Override
		protected Application getApplication() {
			return null;
		}
	}

	public static class Trade extends AbstractRecoService {

		private static final Map<Lang, Files> DEFAULT_RESPONSE = AbstractRecoService.getDefaultResponsesByServices(SERVICES.BOURSE_FREE, "BOURSE");
		private static final Application APPLICATION = Application.NativeApplication.BOURSE_FREE.getApplication();

		@Override
		protected Files getDefaultResponse(Lang inLang) {
			return Trade.DEFAULT_RESPONSE.get(inLang);
		}

		@Override
		protected String getLabel() {
			return Trade.APPLICATION.getName();
		}

		@Override
		protected Application getApplication() {
			return Trade.APPLICATION;
		}

	}

	public static class Traffic extends AbstractRecoService {

		private static final Map<Lang, Files> DEFAULT_RESPONSE = AbstractRecoService.getDefaultResponsesByServices(SERVICES.TRAFFIC, SERVICES.TRAFFIC.toString());
		private static final Application APPLICATION = Application.NativeApplication.TRAFIC.getApplication();

		@Override
		protected Files getDefaultResponse(Lang inLang) {
			return Traffic.DEFAULT_RESPONSE.get(inLang);
		}

		@Override
		protected String getLabel() {
			return Traffic.APPLICATION.getName();
		}

		@Override
		protected Application getApplication() {
			return Traffic.APPLICATION;
		}
	}

	public static class Air extends AbstractRecoService {

		private static final Map<Lang, Files> DEFAULT_RESPONSE = AbstractRecoService.getDefaultResponsesByServices(SERVICES.AIR, SERVICES.AIR.toString());
		private static final Application APPLICATION = Application.NativeApplication.AIR.getApplication();

		@Override
		protected Application getApplication() {
			return Air.APPLICATION;
		}

		@Override
		protected Files getDefaultResponse(Lang inLang) {
			return Air.DEFAULT_RESPONSE.get(inLang);
		}

		@Override
		protected String getLabel() {
			return Air.APPLICATION.getName();
		}
	}

	public static class Sleep extends AbstractRecoService {

		@Override
		public void process(VObject inObject, String inValue) {

			if (inObject != null) {
				inObject.setState(VObject.STATUS_FORCE_VEILLE);
				AbstractRecoService.LOGGER.debug("sleep");
			}
		}

		@Override
		protected Application getApplication() {
			return null;
		}

		@Override
		protected Files getDefaultResponse(Lang inLang) {
			return null;
		}

		@Override
		protected String getLabel() {
			return null;
		}
	}

	public static class Wake extends AbstractRecoService {

		@Override
		public void process(VObject inObject, String inValue) {

			if (inObject != null) {
				inObject.setState(VObject.STATUS_FORCE_ACTIF);
				AbstractRecoService.LOGGER.debug("wake up");
			}
		}

		@Override
		protected Application getApplication() {
			return null;
		}

		@Override
		protected Files getDefaultResponse(Lang inLang) {
			return null;
		}

		@Override
		protected String getLabel() {
			return null;
		}
	}

	public static class BadReco extends AbstractRecoService {

		private static final Random RANDOM_GENERATOR = new Random();

		@Override
		public void process(VObject inObject, String inValue) {

			if (inObject != null) {
				final Files musicFile = BadReco.getRandomBadreco(inObject.getPreferences().getLangPreferences());
				try {
					MessageServices.sendAudioMessage(inObject, musicFile);
				} catch (final IllegalArgumentException e) {
					AbstractRecoService.LOGGER.fatal(e, e);
				}
				AbstractRecoService.LOGGER.debug("reco not found");
			}
		}

		private static Files getRandomBadreco(Lang inLang) { // avoir tjs 5 son par langue Cas particulier pour le francais plus de son
			final int range = (inLang.getId().intValue() == 1) ? 18 : 5;

			return Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.RECO, inLang).get(String.valueOf(BadReco.RANDOM_GENERATOR.nextInt(range))).get(0).getFiles();
		}

		@Override
		protected Application getApplication() {
			return null;
		}

		@Override
		protected Files getDefaultResponse(Lang inLang) {
			return null;
		}

		@Override
		protected String getLabel() {
			return null;
		}
	}

	protected abstract Files getDefaultResponse(Lang inLang);

	public void process(VObject inObject, String inValue) { // inValue only used by the webradios

		if (inObject == null) {
			return;
		}

		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(getApplication(), inObject);

		if (!subscriptionsList.isEmpty()) {
			final List<SubscriptionSchedulingData> theSchedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscriptionsList.get(0), SchedulingType.SCHEDULING_TYPE.VoiceTrigger);
			if (!theSchedulings.isEmpty()) {
				AbstractMessageFactory.sendMessage(theSchedulings.get(0), MESSAGE_TYPE.FILES_MESSAGE);
				AbstractRecoService.LOGGER.debug(getLabel() + " ok");
				return;
			}
		}

		try {
			MessageServices.sendAudioMessage(inObject, getDefaultResponse(inObject.getPreferences().getLangPreferences()));
		} catch (final IllegalArgumentException e) {
			AbstractRecoService.LOGGER.fatal(e, e);
		}
		AbstractRecoService.LOGGER.debug(getLabel() + " not actived");

	}

	protected abstract String getLabel();

	protected abstract Application getApplication();

	public static final class RadioList {

		private static final Random RANDOM_GENERATOR = new Random();

		public static final String RADIO_KEY_WORD = "radio";
		// France Inter : http://mp3.live.tv-radio.com/franceinter/all/franceinterhautdebit.mp3
		// France Info : http://str2.creacast.com/france_info_64
		// F.I.P : http://213.205.96.91:9903
		// Frequence 3 : http://firewall.frequence3.net:80
		// France Culture : http://mp3.live.tv-radio.com/franceculture/all/franceculturehautdebit.mp3 http://213.205.96.91:9911

		//private static final String[] radioFR = { "http://213.205.96.91:9915", "http://213.205.96.91:9929", "http://213.205.96.91:9903", "http://213.205.96.91:9923", "http://213.205.96.91:9911", };
		private static final String[] radioFR = { "http://mp3.live.tv-radio.com/franceinter/all/franceinterhautdebit.mp3", "http://str2.creacast.com/france_info_64", "http://213.205.96.91:9903", "http://firewall.frequence3.net:80", "http://mp3.live.tv-radio.com/franceculture/all/franceculturehautdebit.mp3", };


		private static final String[] radioUK = { "http://wnycfm.streamguys.com", "http://128.125.204.54:2345/kuscaudio96.mp3", "http://kpcc.streamguys.com:80", "http://130.166.72.1:8002", "http://scfire-dll0l-2.stream.aol.com:80/stream/1046", };

		private static final String[] radioUS = { "http://wnycfm.streamguys.com", "http://128.125.204.54:2345/kuscaudio96.mp3", "http://kpcc.streamguys.com:80", "http://130.166.72.1:8002", "http://scfire-dll0l-2.stream.aol.com:80/stream/1046", };

		private static final String[] radioDE = { "http://a-k-server.de:7500/", "http://a-k-server.de:7500/", "http://217.18.179.34:8000/streammp3.mp3", "http://217.18.179.34:8000/streammp3.mp3", "http://217.18.179.34:8000/streammp3.mp3", };

		private static final Map<String, String> ALL_RADIOS;
		private static final List<String[]> LANGS_RADIOS = Arrays.asList(RadioList.radioFR, RadioList.radioUS, RadioList.radioUK, RadioList.radioDE);

		static {
			final Map<String, String> theMap = new HashMap<String, String>();
			// Please add your radio in alphebatical order !
			// inside the destination country.

			// Put your French Radio station here :
			theMap.put("radio_bfm", "http://vipicecast.yacast.net/bfm");
			theMap.put("radio_bide_et_musique", "http://relay2.bide-et-musique.com:9200");
			theMap.put("radio_extreme", "http://stream03.radioxtreme.fr:8024");
			theMap.put("radio_fg_vintage", "http://live2.impek.tv:9000");
			theMap.put("radio_frequence_3", "http://firewall.frequence3.net:80");
			theMap.put("radio_generation_fm", "http://broadcast.infomaniak.ch/generationfm-low.mp3");
			theMap.put("radio_kiss_fm", "http://shout01.tv-radio.com:8080");
			theMap.put("radio_m_plus_m", "http://shout01.tv-radio.com:8060");
			theMap.put("radio_mfm", "http://str0.creacast.com:443");
			theMap.put("radio_fg", "http://fgd.impek.tv:80");
			theMap.put("radio_fip", "http://mp3.live.tv-radio.com/fip/all/fip-32k.mp3");
			theMap.put("radio_flemme", "http://str15.streamakaci.com:8014");
			theMap.put("radio_franceculture", "http://mp3.live.tv-radio.com/franceculture/all/franceculturehautdebit.mp3");
			theMap.put("radio_franceinfo", "http://str2.creacast.com/france_info_64");
			theMap.put("radio_franceinter", "http://mp3.live.tv-radio.com/franceinter/all/franceinterhautdebit.mp3");
			//theMap.put("radio_gayane", "http://213.246.51.78:9410");
			theMap.put("radio_junior", "http://213.186.61.62:8080");
			//theMap.put("radio_lemouv", "http://213.205.96.91:9923");
			theMap.put("radio_nova", "http://radionova.ovh.net:8000");
			theMap.put("radio_rmc", "http://vipicecast.yacast.net/rmc");


			// Put your US radio station (don't put UK radio) here :
			theMap.put("radio_aural_moon", "http://live.str3am.com:2010");
			theMap.put("radio_beat_basement", "http://serv1.beatbasement.com:9622");
			theMap.put("radio_boulevard_rock", "http://8346.str.ovh.net:8346");
			theMap.put("radio_box", "http://213.251.169.28:8010");
			theMap.put("radio_CBC", "http://icecast1.cbcradio3.com:80/r3");
			theMap.put("radio_sky_classic_rock", "http://160.79.128.40:7734");
			theMap.put("radio_classic_soul_network", "http://213.73.255.244:2026");
			theMap.put("radio_dainbramage", "http://stream1.dainbramage.com:8000");
			theMap.put("radio_datempo_lounge", "http://38.119.49.140:8000");
			theMap.put("radio_disney", "http://129.79.9.25:8000");
			theMap.put("radio_lynx_eighties_rock", "http://server4.highband.lynxradionetwork.com:9000");
			theMap.put("radioetherbeat", "http://sc5.spacialnet.com:17892");
			theMap.put("radio_sky_flamenco", "http://205.188.215.226:8020");
			theMap.put("radio_kexp", "http://kexp-mp3-128k.cac.washington.edu:8000");
			theMap.put("radio_kiddo", "http://sc6.spacialnet.com:13486");
			theMap.put("radio_lynx_hits", "http://server1.highband.lynxradionetwork.com:9000");
			theMap.put("radio_matha_vineyard", "http://sc1.abacast.com:8005");
			theMap.put("radio_martini_in_the_morning", "http://lc1.sc.liquidcompass.cc:9028");
			theMap.put("radio_nigel", "http://209.126.212.130:8060");
			theMap.put("radio_npr_kcrw", "http://scfire-dll0l-2.stream.aol.com:80/stream/1046");
			theMap.put("radio_npr_kcsn", "http://130.166.72.1:8002");
			theMap.put("radio_npr_kpcc", "http://kpcc.streamguys.com:80");
			theMap.put("radio_npr_kusc", "http://128.125.204.54:2345/kuscaudio96.mp3");
			theMap.put("radio_npr_wnyc", "http://wnycfm.streamguys.com");
			theMap.put("radio_radiomaxx", "http://209.9.238.5:8214");
			theMap.put("radio_sky_fm_country", "http://64.236.98.50:80/stream/1019");
			theMap.put("radio_sky_fm_oldschool", "http://160.79.128.61:7694");
			theMap.put("radio_sky_jams", "http://160.79.128.61:7704");
			theMap.put("radio_sky_modern_jazz", "http://205.188.215.227:8008");
			theMap.put("radio_sky_oldies", "http://160.79.128.61:7686");
			theMap.put("radio_sky_pop", "http://64.236.98.50:80/stream/1014");
			theMap.put("radio_sky_smooth_jazz", "http://82.149.227.171:8000");
			theMap.put("radio_lynx_super_eighties", "http://server3.highband.lynxradionetwork.com:9000");
			theMap.put("radio_lynx_super_seventies", "http://server2.highband.lynxradionetwork.com:9000");
			theMap.put("radio_the_current", "http://currentstream1.publicradio.org:80");
			theMap.put("radio_wfuv", "http://wfuv.ic.llnwd.net/stream/wfuv_aac2");
			theMap.put("radio_wgbh", "http://64.71.145.107:8004");
			theMap.put("radio_woxy_dotcom", "http://streamer.lala.com/woxy/mp3/64");
			theMap.put("radio_woxy_vintage", "http://streamer.lala.com/woxy-vintage/mp3/64");

			// Put your UK Radion station here :
			theMap.put("radio_one_word", "http://radio.oneword.co.uk:8000/");

			// Put your Spanish Radion station here :

			// Put your Italian Radion station here :

			// Put your German Radion station here :

			ALL_RADIOS = Collections.unmodifiableMap(theMap);

		}

		private static String getRandomWebradio(Long lang) { // avoir tjs 5 radio par langue
			final int theLangId = (lang.intValue() < RadioList.LANGS_RADIOS.size()) ? lang.intValue() : Lang.LANG_US_ID.intValue();
			final String[] randradio = RadioList.LANGS_RADIOS.get(theLangId);
			return randradio[RadioList.RANDOM_GENERATOR.nextInt(5)];
		}

	}

}
