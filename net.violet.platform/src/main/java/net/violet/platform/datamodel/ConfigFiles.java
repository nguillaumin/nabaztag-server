package net.violet.platform.datamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.Application.NativeApplication;

public interface ConfigFiles extends Record<ConfigFiles> {

	static enum SERVICES {
		// TODO: replace mService's type by NativeApplication ASAP
		BOURSE_FREE(NativeApplication.BOURSE_FREE.getApplication(), null),
		BOURSE_FULL(NativeApplication.BOURSE_FULL.getApplication(), null),
		MOODS(NativeApplication.MOOD.getApplication(), null),
		WEATHER(NativeApplication.WEATHER.getApplication(), null),
		CLOCK_NORMAL(NativeApplication.CLOCK.getApplication(), "CLOCK_ALL/"),
		CLOCK_GENERIC(NativeApplication.CLOCK.getApplication(), "CLOCK_GEN/"),
		CLOCK_ABNORMAL(NativeApplication.CLOCK.getApplication(), "CLOCK/"),
		CLOCK_HC(NativeApplication.CLOCK.getApplication(), "CLOCK_HC/"),
		TRAFFIC(NativeApplication.TRAFIC.getApplication(), null),
		BILAN(NativeApplication.BILAN.getApplication(), null),
		RECO(null, null),
		AIR(NativeApplication.AIR.getApplication(), null),
		DIALOG(NativeApplication.EARS_COMMUNION.getApplication(), null),
		MAIL(NativeApplication.MAIL.getApplication(), null);

		private static final Map<Application, SERVICES> APPLICATION_SERVICE;
		static {
			final Map<Application, SERVICES> theMap = new HashMap<Application, SERVICES>();

			for (final SERVICES aService : SERVICES.values()) {
				theMap.put(aService.getApplication(), aService);
			}

			APPLICATION_SERVICE = Collections.unmodifiableMap(theMap);
		}

		private final Application mService;
		private final String mPrefix;

		private SERVICES(Application inService, String inPrefix) {
			this.mService = inService;
			this.mPrefix = inPrefix;
		}

		/**
		 * @return the theService
		 */
		public Application getApplication() {
			return this.mService;
		}

		/**
		 * @return the prefix or <code>null</code>
		 */
		public String getPrefix() {
			return this.mPrefix;
		}

		public static SERVICES findByApplication(Application inApplication) {
			return SERVICES.APPLICATION_SERVICE.get(inApplication);
		}
	}

	Map<SERVICES, Map<Lang, Map<String, List<ConfigFiles>>>> CONFIG_FILES_CACHE = new HashMap<SERVICES, Map<Lang, Map<String, List<ConfigFiles>>>>();

	/**
	 * Return the {@link LangImpl} currently associated with this
	 * {@link ConfigFilesImpl}.
	 * 
	 * @return the {@link LangImpl} or <code>null</code> if there are not
	 *         associated {@link LangImpl}
	 */
	Lang getLang();

	Application getApplication();

	/**
	 * Return the {@link FilesImpl} currently associated with this
	 * {@link ConfigFilesImpl}.
	 * 
	 * @return the {@link FilesImpl} or <code>null</code> if there are not
	 *         associated {@link FilesImpl}
	 */
	Files getFiles();

	/**
	 * @return the index
	 */
	String getIndex();

	/**
	 * @param index the index to set
	 */
	void setIndex(String index);
}
