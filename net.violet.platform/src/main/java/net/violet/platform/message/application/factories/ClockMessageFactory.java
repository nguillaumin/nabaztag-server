package net.violet.platform.message.application.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.platform.applications.ClockHandler;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.util.Constantes;

public class ClockMessageFactory extends AbstractMessageFactory {

	static final String TITLE = "Service Horloge";

	public enum CLOCK_TYPE {
		NORMAL("1") {

			@Override
			protected Files getFiles(Lang inLang, String inHour) {
				return Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.CLOCK_NORMAL, inLang).get(inHour).get(0).getFiles();
			}

		},
		FUNNY("2") {

			@Override
			protected Files getFiles(Lang inLang, String inHour) {
				final Map<Short, List<ConfigFiles>> theFiles = CLOCK_TYPE.mConfigFilesListByLangAndHourMap.get(inLang);
				return AbstractSQLRecord.randomElementFromList(theFiles.get(Short.valueOf(inHour))).getFiles();
			}
		},
		SHARE("3") {

			@Override
			protected Files getFiles(Lang inLang, String inHour) {
				final List<ConfigFiles> theFiles = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.CLOCK_HC, inLang).get(inHour);

				final ConfigFiles theConfigFiles;

				if ((theFiles == null) || theFiles.isEmpty()) {
					theConfigFiles = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.CLOCK_NORMAL, inLang).get(inHour).get(0);
				} else {
					theConfigFiles = AbstractSQLRecord.randomElementFromList(theFiles);
				}

				return theConfigFiles.getFiles();
			}
		};

		private static final Map<Lang, Map<Short, List<ConfigFiles>>> mConfigFilesListByLangAndHourMap = new HashMap<Lang, Map<Short, List<ConfigFiles>>>();

		static {
			for (final ObjectLangData aLangData : ObjectLangData.getAllObjectLanguages()) {
				final Lang aLang = aLangData.getReference();
				final List<ConfigFiles> genericList = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.CLOCK_GENERIC, aLang).get("ALL");
				final Map<Short, List<ConfigFiles>> mapByHour = new HashMap<Short, List<ConfigFiles>>();

				for (short i = 0; i <= 23; i++) {
					final List<ConfigFiles> timeList = new ArrayList<ConfigFiles>();

					final List<ConfigFiles> theListForHour = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.CLOCK_ABNORMAL, aLang).get(String.valueOf(i));
					if (theListForHour != null) {
						timeList.addAll(theListForHour);
					}
					timeList.addAll(genericList);
					mapByHour.put(i, timeList);
				}
				CLOCK_TYPE.mConfigFilesListByLangAndHourMap.put(aLang, mapByHour);
			}
		}

		private final static Map<String, CLOCK_TYPE> TYPE_MAP;

		private final String id;

		private CLOCK_TYPE(String inId) {
			this.id = inId;
		}

		static {
			final Map<String, CLOCK_TYPE> theMap = new HashMap<String, CLOCK_TYPE>();
			for (final CLOCK_TYPE type : CLOCK_TYPE.values()) {
				theMap.put(type.getId(), type);
			}
			TYPE_MAP = Collections.unmodifiableMap(theMap);
		}

		public String getId() {
			return this.id;
		}

		protected abstract Files getFiles(Lang inLang, String inHour);

		private static CLOCK_TYPE findById(String inId) {
			return CLOCK_TYPE.TYPE_MAP.get(inId);
		}
	}

	@Override
	public List<Message2Send> getMessage(MessageProcessUnit inUnit) {

		return Collections.singletonList((Message2Send) new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

			@Override
			protected Files[] getBody() {

				final Map<String, Object> theSettings = getSubscription().getSettings();
				final Object languagesSetting = theSettings.get(ClockHandler.LANGUAGES);
				final Object typesSetting = theSettings.get(ClockHandler.TYPES);

				final List<String> languages = (List<String>) languagesSetting;
				final List<String> types = (List<String>) typesSetting;

				final Lang langRand = ObjectLangData.getDefaultObjectLanguage(AbstractMessageFactory.randomElementFromArray(languages.toArray(new String[0]))).getReference();
				final CLOCK_TYPE type = CLOCK_TYPE.findById(AbstractMessageFactory.randomElementFromArray(types.toArray(new String[0])));

				return new Files[] { type.getFiles(langRand, Integer.toString(getDeliveryDate().getHour())) };
			}

			@Override
			protected MessageSignature getSignature() {
				return MessageSignature.HORLOGE_SIGNATURE;
			}

			@Override
			protected String getTitle() {
				return ClockMessageFactory.TITLE;
			}

			@Override
			protected int getTTL() {
				return Constantes.QUEUE_TTL_CLOCK;
			}
		});

	}

}
