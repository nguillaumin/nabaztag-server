package net.violet.platform.datamodel;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.ServiceFactory.SERVICE;
import net.violet.platform.message.MessageSignature;
import net.violet.vlisp.services.NablifeSrv;

// TODO: remove NablifeSrv
public interface Application extends Record<Application>, NablifeSrv {

	/**
	 * An Enum containing available classes for applications.
	 */
	public enum ApplicationClass {
		WEBUI, HOSTED, EXTERNAL, NATIVE;

		private static final Map<String, ApplicationClass> NAME_APPLICATIONCLASS;

		static {
			final Map<String, ApplicationClass> theMap = new HashMap<String, ApplicationClass>();

			for (final ApplicationClass aClass : ApplicationClass.values()) {
				theMap.put(aClass.name().toUpperCase(), aClass);
			}

			NAME_APPLICATIONCLASS = Collections.unmodifiableMap(theMap);
		}

		public static ApplicationClass getByName(String inName) {
			return (inName != null) ? ApplicationClass.NAME_APPLICATIONCLASS.get(inName.toUpperCase()) : null;
		}

		@Override
		public String toString() {
			return name();
		}

	};

	enum NativeApplication {
		RSS_FULL("net.violet.rssfull"), PODCAST_FULL("net.violet.podcastfull"), WEATHER("net.violet.weather"), AIR("net.violet.air"), BILAN("net.violet.bilan"), BOURSE_FREE("net.violet.bourse"), BOURSE_FULL("net.violet.boursefull"), MOOD("net.violet.mood"), RSS_FREE("net.violet.rss"), TRAFIC("net.violet.trafic"), PODCAST_FREE("net.violet.podcast"), MAIL("net.violet.mail"), FACEBOOK("net.violet.facebook"), GMAIL("net.violet.gmail"), TWITTER("net.violet.twitter"), CLOCK("net.violet.clock"), TAICHI("net.violet.taichi"), WEBRADIO("net.violet.webradio"), ALARM_CLOCK("net.violet.alarm"), REMINDER("net.violet.reminder"), EARS_COMMUNION("net.violet.earscommunion"), INBOX("net.violet.js.mailbox");

		private static final Map<Application, NativeApplication> APPLICATION_NATIVE_APP = new HashMap<Application, NativeApplication>() {

			@Override
			public NativeApplication put(Application key, NativeApplication value) {
				if ((key != null) && (value != null)) {
					return super.put(key, value);
				}
				throw new IllegalArgumentException("Neither key nor value can be null, key = " + key + " & value = " + value);
			}
		};

		private static final Map<String, NativeApplication> NAME_NATIVE_APP = new HashMap<String, NativeApplication>();

		private static final Pattern RSS_PODCAST_WEBRADIO_NAME_PATTERN = Pattern.compile("^(net\\.violet\\.(?:rss|podcast|webradio))\\..*$");

		static {
			for (final NativeApplication aNativeApplication : NativeApplication.values()) {
				if (aNativeApplication.getApplication() != null) {
					NativeApplication.APPLICATION_NATIVE_APP.put(aNativeApplication.getApplication(), aNativeApplication);
				}
				NativeApplication.NAME_NATIVE_APP.put(aNativeApplication.getName(), aNativeApplication);
			}
		}

		private final Application application;
		private final String name;

		NativeApplication(String inName) {
			this.name = inName;
			this.application = Factories.APPLICATION.findByName(inName);
		}

		@Override
		public String toString() {
			return this.application.getName();
		}

		public String getName() {
			return this.name;
		}

		public Application getApplication() {
			return this.application;
		}

		public static NativeApplication findByApplication(Application inApplication) {

			if (inApplication == null) {
				return null;
			}

			final NativeApplication result = NativeApplication.APPLICATION_NATIVE_APP.get(inApplication);
			if (result != null) {
				return result;
			}

			final Matcher theMatcher = NativeApplication.RSS_PODCAST_WEBRADIO_NAME_PATTERN.matcher(inApplication.getName());

			if (theMatcher.matches()) {
				return NativeApplication.NAME_NATIVE_APP.get(theMatcher.group(1));
			}

			return null;
		}
	}

	/**
	 * Some usual combinations of applications classes
	 */
	final List<ApplicationClass> CLASSES_ALL = Arrays.asList(ApplicationClass.values());
	final List<ApplicationClass> CLASSES_UI = Arrays.asList(ApplicationClass.WEBUI);
	final List<ApplicationClass> CLASSES_UI_NATIVES = Arrays.asList(ApplicationClass.WEBUI, ApplicationClass.NATIVE);
	final List<ApplicationClass> CLASSES_NOT_UI = Arrays.asList(ApplicationClass.NATIVE, ApplicationClass.HOSTED, ApplicationClass.EXTERNAL);

	/**
	 * Name of the application (unique)
	 */
	String getName();

	/**
	 * Developer of the application
	 */
	User getOwner();

	Timestamp getCreationDate();

	boolean isInteractive();

	boolean isVisible();

	void setVisible(boolean inVisible);

	ApplicationCategory getCategory();

	ApplicationClass getApplicationClass();

	/**
	 * Application description
	 */
	ApplicationProfile getProfile();

	/**
	 * Application Resource
	 */
	ApplicationTemp getTemp();

	/**
	 * Application developer's package (source files and resources)
	 */
	ApplicationPackage getPackage();

	boolean isRemovable();

	/**
	 * On which objects does the application execute ?
	 */
	List<HARDWARE> getSupportedHardwares();

	boolean hasSupportedHardware(HARDWARE inHardware);

	void addHardware(HARDWARE inHardware);

	void addHardwares(List<HARDWARE> inHardwares);

	void removeHardware(HARDWARE inHardware);

	/**
	 * Languages in which the application has been translated
	 */
	List<Lang> getLangs();

	boolean hasLang(Lang inLang);

	void addLang(Lang inLang);

	void removeLang(Lang inLang);

	void updateLangs(List<Lang> inUpdatedLangs);

	/**
	 * Update the rank of this application in this lang
	 * @param inRefLang
	 * @param inRank (the greatest, the better)
	 */
	void updateRankInLang(Lang inRefLang, int inRank);

	/**
	 * Update the application rank for all supported langs !
	 * @param inRank (the greatest, the better) 
	 */
	void updateRank(int inRank);

	/**
	 * Tags related to the application
	 */
	List<ApplicationTag> getTags();

	boolean hasTag(ApplicationTag inTag);

	void addTag(ApplicationTag inTag);

	void removeTag(ApplicationTag inTag);

	void updateTags(List<ApplicationTag> inUpdatedTags);

	void update(String inAppName, ApplicationClass inAppClass, ApplicationCategory inAppCategory, boolean isInteractive, boolean isVisible, boolean isRemovable);

	MessageSignature getMessageSignature();

	class ApplicationCommon {

		public static MessageSignature getMessageSignature(Application inApplication) {
			if (Application.NativeApplication.AIR.equals(inApplication)) {
				return MessageSignature.AIR_SIGNATURE;
			}
			if (Application.NativeApplication.TRAFIC.equals(inApplication)) {
				return MessageSignature.TRAFFIC_SIGNATURE;
			}
			if (Application.NativeApplication.BOURSE_FREE.equals(inApplication)) {
				return MessageSignature.BOURSE_SIGNATURE;
			}
			if (Application.NativeApplication.WEATHER.equals(inApplication)) {
				return MessageSignature.WEATHER_SIGNATURE;
			}
			if (Application.NativeApplication.MAIL.equals(inApplication)) {
				return MessageSignature.MAIL_SIGNATURE;
			}
			if (Application.NativeApplication.BILAN.equals(inApplication)) {
				return MessageSignature.BILAN_SIGNATURE;
			}
			if (Application.NativeApplication.CLOCK.equals(inApplication)) {
				return MessageSignature.HORLOGE_SIGNATURE;
			}

			final Service service;
			if (inApplication.equals(Application.NativeApplication.RSS_FREE.getApplication()) || inApplication.equals(Application.NativeApplication.RSS_FULL.getApplication())) {
				service = SERVICE.RSS.getService();
			} else if (inApplication.equals(Application.NativeApplication.PODCAST_FREE.getApplication()) || inApplication.equals(Application.NativeApplication.PODCAST_FULL.getApplication())) {
				service = SERVICE.PODCAST.getService();
			} else {
				return null;
			}

			return new MessageSignature(service);
		}

	}

	void setCategory(long inCategId);

	void updateHardwares(List<HARDWARE> inHardwares);

}
